package io.github.freesoulcode.common.interfaces;

public interface ErrorCode {
    int getCode();
    String getMessageKey();
    String getDefaultMessage();
}
