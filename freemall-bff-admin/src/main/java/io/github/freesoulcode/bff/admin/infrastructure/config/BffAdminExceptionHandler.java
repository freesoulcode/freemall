package io.github.freesoulcode.bff.admin.infrastructure.config;

import io.github.freesoulcode.common.interfaces.Result;
import io.github.freesoulcode.common.interfaces.SystemErrorCode;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BffAdminExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public Result<String> handleBadCredentialsException(BadCredentialsException e) {
        return Result.error(SystemErrorCode.UNAUTHORIZED.getCode(), "用户名或密码错误");
    }

    @ExceptionHandler(AuthenticationException.class)
    public Result<String> handleAuthenticationException(AuthenticationException e) {
        return Result.error(SystemErrorCode.UNAUTHORIZED.getCode(), "认证失败: " + e.getMessage());
    }
}
