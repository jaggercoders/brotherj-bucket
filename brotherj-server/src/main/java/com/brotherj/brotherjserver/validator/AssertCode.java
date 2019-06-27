package com.brotherj.brotherjserver.validator;

import com.brotherj.brotherjutil.util.ResultCodeEnum;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * description：
 *
 * @author wangjie
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD,ElementType.FIELD})
@Documented
public @interface AssertCode {


    ResultCodeEnum codeEnum() default ResultCodeEnum.exchangeRequestFail;

}
