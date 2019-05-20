package com.brotherj.brotherjutil.controller;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.ParameterAnnotationsAttribute;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.EnumMemberValue;
import javassist.bytecode.annotation.StringMemberValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * description：
 *
 * @author wangjie
 */
public class ControllerBuilder {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {


        public static final String RETURN_TYPE="com.sinochem.energy.technology.dataexchange.api.domain.JsonResult";

        private ClassPool pool;

        private CtClass clazz;

        private ClassFile ccFile;

        private ConstPool constpool;

        private Class<?> target;

        public Builder init(Class<?> clazz) {
            this.pool = ClassPool.getDefault();
            this.pool.insertClassPath(new ClassClassPath(clazz));
            return this;
        }

        public Builder makeClass(String className) {
            if (StringUtils.isEmpty(className)) {
                throw new RuntimeException("className must not be null！");
            }
            this.clazz = this.pool.makeClass(className);
            this.ccFile = this.clazz.getClassFile();
            this.constpool = this.ccFile.getConstPool();
            return this;
        }

        public Builder addControllerAnnotation(Map<String, Object> param) {
            AnnotationsAttribute classAttr = addAnnotation(RestController.class);
            classAttr.addAnnotation(addRequestMapping(param));
            this.ccFile.addAttribute(classAttr);
            return this;
        }

        private AnnotationsAttribute addAnnotation(Class<?> class1) {
            AnnotationsAttribute classAttr = new AnnotationsAttribute(this.constpool, AnnotationsAttribute.visibleTag);
            Annotation annotation = new Annotation(class1.getCanonicalName(), this.constpool);
            classAttr.addAnnotation(annotation);
            return classAttr;
        }

        private Annotation addRequestMapping(Map<String, Object> param) {
            Annotation requestMapping = new Annotation(RequestMapping.class.getCanonicalName(), this.constpool);
            if (!StringUtils.isEmpty(param.get("value"))) {
                ArrayMemberValue valueArray = new ArrayMemberValue(this.constpool);
                valueArray.setValue(new StringMemberValue[]{new StringMemberValue((String) param.get("value"), this.constpool)});
                requestMapping.addMemberValue("value", valueArray);
            }
            if (!StringUtils.isEmpty(param.get("method"))) {
                ArrayMemberValue methodArray = new ArrayMemberValue(constpool);
                EnumMemberValue enumMemberValue = new EnumMemberValue(constpool);
                enumMemberValue.setType(RequestMethod.class.getCanonicalName());
                enumMemberValue.setValue(String.valueOf(param.get("method")));
                methodArray.setValue(new EnumMemberValue[]{enumMemberValue});
                requestMapping.addMemberValue("method", methodArray);
            }
            return requestMapping;
        }

        public Builder autowiredFiled(Class<?> target) throws NotFoundException, CannotCompileException {
            this.target = target;
            CtClass filed = this.pool.get(target.getCanonicalName());
            String fieldName = target.getSimpleName();
            // 增加字段
            CtField field = new CtField(filed, fieldName, this.clazz);
            field.setModifiers(Modifier.PRIVATE);
            FieldInfo fieldInfo = field.getFieldInfo();
            // 属性附上注解
            AnnotationsAttribute fieldAttr = addAnnotation(Autowired.class);
            fieldInfo.addAttribute(fieldAttr);
            this.clazz.addField(field);
            return this;
        }

        public Builder addMethod() throws NotFoundException, CannotCompileException {
            Method[] declaredMethods = this.target.getDeclaredMethods();
            for (Method method : declaredMethods) {
                boolean isGetRequest = isGetRequest(method);
                Map<String, Object> annotationValueMap;
                if (!method.isAnnotationPresent(ProxyMapping.class)) {
                    annotationValueMap = defaultMakeMethodValue(method, isGetRequest);
                } else {
                    annotationValueMap = makeMethodValue(method);
                }

                CtMethod ctMethod = makeMethodBody(method, this.target.getSimpleName());

                //方法附上注解
                AnnotationsAttribute methodAttr = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
                methodAttr.addAnnotation(addRequestMapping(annotationValueMap));
                MethodInfo info = ctMethod.getMethodInfo();
                addParameterAnnotation(info, method, isGetRequest);
                info.addAttribute(methodAttr);
                this.clazz.addMethod(ctMethod);
            }
            return this;
        }

        //参数上加注解

        /***
         *   参数注解生成流程：
         1、 创建注解二维数组Annotation[][]：第一维对应参数序列，第二维对应注解序列；
         2、 参数注解属性ParameterAnnotationsAttribute添加注解二维数组Annotation[][]；
         3、 方法信息CtMethod.getMethodInfo()添加参数注解属性ParameterAnnotationsAttribute。
         */
        private void addParameterAnnotation(MethodInfo info, Method method, boolean isGetRequest) {
            Parameter[] parameters = method.getParameters();
            Annotation[][] paramAnnotanArr = new Annotation[parameters.length][1];
            for (int i = 0; i < parameters.length; i++) {
                Annotation paramAnno;
                if (isGetRequest) {
                    paramAnno = new Annotation(RequestParam.class.getCanonicalName(), this.constpool);
                    paramAnno.addMemberValue("value", new StringMemberValue(parameters[i].getName(), this.constpool));
                } else {
                    paramAnno = new Annotation(RequestBody.class.getCanonicalName(), this.constpool);
                }
                paramAnnotanArr[i][0] = paramAnno;

            }
            ParameterAnnotationsAttribute paramAnnoTan = new ParameterAnnotationsAttribute(this.constpool, ParameterAnnotationsAttribute.visibleTag);
            paramAnnoTan.setAnnotations(paramAnnotanArr);
            info.addAttribute(paramAnnoTan);
        }

        private boolean isGetRequest(Method method) {
            if (method.isAnnotationPresent(ProxyMapping.class)) {
                return method.getAnnotation(ProxyMapping.class).method().equals(RequestMethod.GET);
            } else {
                return Arrays.stream(method.getParameterTypes()).allMatch(BasicTypeEnum::containThis);
            }
        }

        private CtMethod makeMethodBody(Method method, String filedName) throws CannotCompileException, NotFoundException {
            String methodName = method.getName();
            Class<?>[] parameters = method.getParameterTypes();
            CtClass[] ctClasses = new CtClass[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                ctClasses[i] = pool.get(parameters[i].getCanonicalName());
            }
            Class<?> returnType = method.getReturnType();
            CtClass response = pool.get(RETURN_TYPE);
            StringBuilder methodBody = new StringBuilder();
            CtMethod ctMethod = new CtMethod(response, methodName, ctClasses, clazz);
            ctMethod.setModifiers(Modifier.PUBLIC);
            methodBody.append("{return ").append(RETURN_TYPE).append(".ok(").append(filedName).append(".").append(methodName).append("(");
            StringBuilder returnMethod = new StringBuilder();
            for (int i = 1; i <= parameters.length; i++) {
                returnMethod.append("$").append(i).append(",");
            }
            methodBody.append(returnMethod.toString(), 0, returnMethod.length() - 1);
            methodBody.append("));}");
            ctMethod.setBody(methodBody.toString());
            return ctMethod;
        }

        private Map<String, Object> defaultMakeMethodValue(Method method, boolean isGetRequest) {
            Map<String, Object> map = new HashMap<>();
            map.put("value", method.getName());
            map.put("method", isGetRequest ? RequestMethod.GET : RequestMethod.POST);
            return map;
        }

        private Map<String, Object> makeMethodValue(Method method) {
            Map<String, Object> map = new HashMap<>();
            ProxyMapping dataExchangeClient = method.getAnnotation(ProxyMapping.class);
            map.put("value", dataExchangeClient.path());
            map.put("method", dataExchangeClient.method());
            return map;
        }

        public Class<?> toClass() throws CannotCompileException, IOException {
            try {
//                String writePath=Thread.currentThread().getContextClassLoader().getResource("").getPath();
//                clazz.writeFile(writePath);
                // 通过类加载器加载该CtClass
                return this.clazz.toClass();
            } finally {
                // 将该class从ClassPool中删除
                this.clazz.detach();
            }
        }
    }
}
