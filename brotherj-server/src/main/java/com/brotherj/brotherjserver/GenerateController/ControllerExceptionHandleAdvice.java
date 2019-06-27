package com.brotherj.brotherjserver.GenerateController;

import com.brotherj.brotherjutil.util.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 类描述： controller统一异常处理
 *
 * @author 王洁
 * @version 1.0.0
 */

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandleAdvice {

    @ExceptionHandler
    public JsonResult<String> handler(Exception e) {

        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exception= (MethodArgumentNotValidException) e;
            List<String> codeList = exception.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            String code=codeList.stream().map(x->x.split("-")[0]).collect(Collectors.joining(","));
            String message=codeList.stream().map(x->x.split("-")[1]).collect(Collectors.joining(","));
//            log.warn(LinkUtil.link("参数校验失败", "code: ", exception.getCode(), ", message: ", e.getMessage()), e);
            return JsonResult.fail(code,message);
        }

        log.error(e.getMessage(), e);

        return JsonResult.fail("数据交换平台系统忙，请稍后再试");
    }
}