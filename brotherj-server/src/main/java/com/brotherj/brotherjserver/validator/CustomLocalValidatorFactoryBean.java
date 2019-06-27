package com.brotherj.brotherjserver.validator;

import com.brotherj.brotherjutil.util.ResultCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.lang.Nullable;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.metadata.ConstraintDescriptor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * description：
 *
 * @author wangjie
 */
public class CustomLocalValidatorFactoryBean extends LocalValidatorFactoryBean {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    private final Map<Class<?>, Map<String,String>> errorCodeMap = new ConcurrentHashMap<>(16);


    @Override
    public void validate(Object target, Errors errors) {
        refreshErrorCode(target.getClass());
        super.validate(target, errors);
    }

    @Override
    public void validate(Object target, Errors errors, Object... validationHints) {
        refreshErrorCode(target.getClass());
        super.validate(target, errors, validationHints);
    }

    private void refreshErrorCode(Class<?> targetClass) {
        if ( errorCodeMap.containsKey(targetClass)){
            errorCodeMap.get(targetClass);
        }else {
            //如果缓存中没有，从当前类取出
            Map<String, String> fieldCodeMap=new HashMap<>();
            ReflectionUtils.doWithFields(targetClass, field->{
                String name = field.getName();
                if (field.isAnnotationPresent(AssertCode.class)){
                    ResultCodeEnum codeEnum = field.getAnnotation(AssertCode.class).codeEnum();
                    fieldCodeMap.put(name,codeEnum.getResultCode()+"-"+codeEnum.getResultMsg());
                }
            });
            errorCodeMap.put(targetClass,fieldCodeMap);
        }

    }

    @Override
    protected void processConstraintViolations(Set<ConstraintViolation<Object>> violations, Errors errors) {
        for (ConstraintViolation<Object> violation : violations) {
            String field = determineField(violation);
            FieldError fieldError = errors.getFieldError(field);
            if (fieldError == null || !fieldError.isBindingFailure()) {
                try {
                    ConstraintDescriptor<?> cd = violation.getConstraintDescriptor();
                    String codeEnum = errorCodeMap.get(violation.getRootBeanClass()).get(field);
                    String errorCode =determineErrorCode(cd);
                    Object[] errorArgs = getArgumentsForConstraint(errors.getObjectName(), field, cd);
                    if (errors instanceof BindingResult) {
                        // Can do custom FieldError registration with invalid value from ConstraintViolation,
                        // as necessary for Hibernate Validator compatibility (non-indexed set path in field)
                        BindingResult bindingResult = (BindingResult) errors;
                        String nestedField = bindingResult.getNestedPath() + field;
                        if (nestedField.isEmpty()) {
                            String[] errorCodes = bindingResult.resolveMessageCodes(errorCode);
                            ObjectError error = new ObjectError(
                                    errors.getObjectName(), errorCodes, errorArgs, codeEnum) {
                                @Override
                                public boolean shouldRenderDefaultMessage() {
                                    return false;
                                }
                            };
                            error.wrap(violation);
                            bindingResult.addError(error);
                        }
                        else {
                            Object rejectedValue = getRejectedValue(field, violation, bindingResult);
                            String[] errorCodes = bindingResult.resolveMessageCodes(errorCode, field);
                            FieldError error = new FieldError(errors.getObjectName(), nestedField,
                                    rejectedValue, false, errorCodes, errorArgs, codeEnum) {
                                @Override
                                public boolean shouldRenderDefaultMessage() {
                                    return false;
                                }
                            };
                            error.wrap(violation);
                            bindingResult.addError(error);
                        }
                    }
                    else {
                        // got no BindingResult - can only do standard rejectValue call
                        // with automatic extraction of the current field value
                        errors.rejectValue(field, errorCode, errorArgs, violation.getMessage());
                    }
                }
                catch (NotReadablePropertyException ex) {
                    throw new IllegalStateException("JSR-303 validated property '" + field +
                            "' does not have a corresponding accessor for Spring data binding - " +
                            "check your DataBinder's configuration (bean property versus direct field access)", ex);
                }
            }
        }
    }

    //    @Override
//    public void validate(@Nullable Object target, Errors errors, @Nullable Object... validationHints) {
//        Set<Validator> concreteValidators = new LinkedHashSet<>();
//        Set<Class<?>> interfaceGroups = new LinkedHashSet<>();
//        extractConcreteValidatorsAndInterfaceGroups(concreteValidators, interfaceGroups, validationHints);
//        proccessConcreteValidators(target, errors, concreteValidators);
//        processConstraintViolations(super.validate(target, interfaceGroups.toArray(new Class<?>[0])), errors);
//    }

    private void proccessConcreteValidators(Object target, Errors errors, Set<Validator> concreteValidators) {
        for (Validator validator : concreteValidators) {
            validator.validate(target, errors);
        }
    }

    private void extractConcreteValidatorsAndInterfaceGroups(Set<Validator> concreteValidators, Set<Class<?>> groups, Object... validationHints) {
        if (validationHints != null) {
            for (Object hint : validationHints) {
                if (hint instanceof Class) {
                    if (((Class<?>) hint).isInterface()) {
                        groups.add((Class<?>) hint);
                    } else {
                        Optional<Validator> validatorOptional = getValidatorFromGenericClass(hint);
                        validatorOptional.ifPresent(concreteValidators::add);
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private Optional<Validator> getValidatorFromGenericClass(Object hint) {
        try {
            Class<Validator> clazz = (Class<Validator>) Class.forName(((Class<?>) hint).getName());
            return Optional.of(clazz.newInstance());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            logger.info("There is a problem with the class that you passed to "
                    + " @Validated annotation in the controller, we tried to "
                    + " cast to org.springframework.validation.Validator and we cant do this");
        }
        return Optional.empty();
    }

}