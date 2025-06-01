package com.aipan.utils;

import com.aipan.enums.BizCodeEnum;
import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author caixr
 * @date 2025/6/1 17:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonData implements Serializable {

    private Integer code;

    private Object data;

    private String msg;

    public <T> T getData(Class<T> clazz) {
        return JSON.parseObject(JSON.toJSONString(data), clazz);
    }

    public static JsonData buildSuccess() {
        return new JsonData(0, null, null);
    }

    public static JsonData buildSuccess(Object data) {
        return new JsonData(0, data, null);
    }

    public static JsonData buildError(String msg) {
        return new JsonData(-1, null, msg);
    }

    public static JsonData buildCodeAndMsg(Integer code, String msg) {
        return new JsonData(code, null, msg);
    }

    public static JsonData buildResult(BizCodeEnum bizCodeEnum) {
        return JsonData.buildCodeAndMsg(bizCodeEnum.getCode(), bizCodeEnum.getMessage());
    }

    public boolean isSuccess() {
        return code == 0;
    }
}
