package io.github.freesoulcode.common.interfaces;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;
    private final Object[] args;  // 用于消息中的占位符
    private final String customMessage;  // 自定义消息（覆盖国际化）

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessageKey());
        this.errorCode = errorCode;
        this.args = null;
        this.customMessage = null;
    }

    public BusinessException(ErrorCode errorCode, Object... args) {
        super(errorCode.getMessageKey());
        this.errorCode = errorCode;
        this.args = args;
        this.customMessage = null;
    }

    public BusinessException(ErrorCode errorCode, String customMessage) {
        super(customMessage);
        this.errorCode = errorCode;
        this.args = null;
        this.customMessage = customMessage;
    }

    public String getMessageKey() {
        return errorCode.getMessageKey();
    }
}
