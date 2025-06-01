package com.aipan.exception;

import com.aipan.enums.BizCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author caixr
 * @date 2025/6/1 18:51
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BizException extends RuntimeException{
    private final int code;
    private final String msg;
    private String detail;
    public BizException(int code,String message) {
        super(message);
        this.code =code;
        this.msg = message;
    }

    public BizException(BizCodeEnum bizCodeEnum) {
        super(bizCodeEnum.getMessage());
        this.code = bizCodeEnum.getCode();
        this.msg = bizCodeEnum.getMessage();
    }

    public BizException(BizCodeEnum bizCodeEnum, Exception e) {
        super(bizCodeEnum.getMessage());
        this.code = bizCodeEnum.getCode();
        this.msg = bizCodeEnum.getMessage();
        this.detail = e.getMessage();
    }
}
