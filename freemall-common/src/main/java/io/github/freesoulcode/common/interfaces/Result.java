package io.github.freesoulcode.common.interfaces;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 成功响应，无数据
     */
    public static <T> Result<T> success() {
        return Result.<T>builder()
                .code(SystemErrorCode.SUCCESS.getCode())
                .message(SystemErrorCode.SUCCESS.getDefaultMessage())
                .build();
    }

    /**
     * 成功响应，带数据
     */
    public static <T> Result<T> success(T data) {
        return Result.<T>builder()
                .code(SystemErrorCode.SUCCESS.getCode())
                .message(SystemErrorCode.SUCCESS.getDefaultMessage())
                .data(data)
                .build();
    }

    /**
     * 失败响应
     */
    public static Result<String> error(ErrorCode errorCode) {
        return Result.<String>builder()
                .code(errorCode.getCode())
                .message(errorCode.getDefaultMessage())
                .build();
    }

    /**
     * 失败响应
     */
    public static Result<String> error(Integer code, String message) {
        return Result.<String>builder()
                .code(code)
                .message(message)
                .build();
    }
}
