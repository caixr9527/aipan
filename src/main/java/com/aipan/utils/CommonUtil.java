package com.aipan.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.PrintWriter;

/**
 * @author caixr
 * @date 2025/6/1 17:58
 */
@Slf4j
public class CommonUtil {
    public static void sendJsonMessage(HttpServletResponse response, Object obj) {
        response.setContentType("application/json;charset=utf-8");
        try (PrintWriter printWriter = response.getWriter()) {
            printWriter.print(JsonUtil.obj2Json(obj));
            response.flushBuffer();
        } catch (Exception e) {
            log.error("响应异常：", e);
        }
    }

    public static String getFileSuffix(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    public static String getFilePath(String filename) {
        String fileSuffix = getFileSuffix(filename);
        return StrUtil.format("{}/{}/{}/{}.{}",
                DateUtil.thisYear(),
                DateUtil.thisMonth() + 1,
                DateUtil.thisDayOfMonth(),
                IdUtil.randomUUID(),
                fileSuffix);

    }
}
