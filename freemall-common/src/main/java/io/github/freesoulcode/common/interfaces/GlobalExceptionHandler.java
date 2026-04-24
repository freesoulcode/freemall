package io.github.freesoulcode.common.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(BusinessException.class)
    public Result<String> handleBusinessException(BusinessException e) {
        Locale locale = LocaleContextHolder.getLocale();

        String message;

        // 优先级1：自定义消息
        if (e.getCustomMessage() != null) {
            message = e.getCustomMessage();
        }
        // 优先级2：使用国际化消息
        else {
            try {
                message = messageSource.getMessage(
                        e.getMessageKey(),
                        e.getArgs(),
                        locale
                );
            } catch (NoSuchMessageException ex) {
                // 找不到消息key时的降级处理
                message = e.getMessageKey();
            }
        }

        return Result.error(e.getErrorCode().getCode(), message);
    }
}
