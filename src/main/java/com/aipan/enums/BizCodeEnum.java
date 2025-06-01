package com.aipan.enums;

import lombok.Getter;

/**
 * @author caixr
 * @date 2025/6/1 17:30
 */
@Getter
public enum BizCodeEnum {
    ACCOUNT_REPEAT(250001, "账号已存在"),
    ACCOUNT_UNREGISTER(250002, "账号不存在"),
    ACCOUNT_PWD_ERROR(250003, "密码错误"),
    ACCOUNT_UNLOGIN(250003, "账号未登录"),

    FILE_NOT_EXISTS(220404, "文件不存在"),
    FILE_RENAME_REPEAT(220405, "文件名重复"),
    FILE_DEL_BATCH_ILLEGAL(220406, "文件删除参数错误"),
    FILE_TYPE_ERROR(220407, "文件类型错误"),
    FILE_CHUNK_TASK_NOT_EXISTS(230408, "分片任务不存在"),
    FILE_CHUNK_NOT_ENOUGH(230409, "分片败量不匹配，合并不够"),
    FILE_STORAGE_NOT_ENOUGH(240403, "存储空间不足"),
    FILE_TARGET_PARENT_ILLEGAL(250403, "目标父级目录不合法"),
    SHARE_CANCEL_ILLEGAL(260403, "取消分享失数，参数不会法"),
    SHARE_CODE_ILLEGAL(260404, "分享提不合法"),
    SHARE_NOT_EXIST(260405, "分享不存在"),
    SHARE_CANCEL(260406, "分享已取消"),
    SHARE_EXPIRED(260407, "分享已过期"),
    SHARE_FILE_ILLEGAL(260408, "分享的文件不合线");
    private final Integer code;

    private final String message;

    BizCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
