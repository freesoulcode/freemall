package io.github.freesoulcode.common.interfaces;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SystemErrorCode implements ErrorCode{
    SUCCESS(200, "success", "success"),
    SYSTEM_ERROR(500, "system.error", "system error"),
    PARAM_ERROR(400, "param.error", "param error"),
    UNAUTHORIZED(401, "unauthorized", "unauthorized"),
    FORBIDDEN(403, "forbidden", "forbidden"),
    NOT_FOUND(404, "not.found", "not found");

    private final int code;
    private final String messageKey;
    private final String defaultMessage;
}
