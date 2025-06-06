package com.aipan.exception;

import com.aipan.utils.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author caixr
 * @date 2025/6/1 18:48
 */
@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonData handler(Exception e) {
        if (e instanceof BizException bizException) {
            log.error("[业务异常]",e);
            return JsonData.buildCodeAndMsg(bizException.getCode(), bizException.getMsg());
        }else {
            log.error("[系统异常]",e);
            return JsonData.buildError("系统异常，请稍后重试");
        }
    }

}
