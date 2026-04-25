package io.github.freesoulcode.product.domain;

import io.github.freesoulcode.common.interfaces.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BizErrorCode implements ErrorCode {
    PRODUCT_NOT_FOUND(500, "product.not.found", "商品不存在"),
    PRODUCT_CANNOT_SUBMIT_AUDIT(500, "product.cannot.submit.audit", "当前状态不能提交审核"),
    PRODUCT_STATUS_INVALID(500, "product.status.invalid", "商品状态异常"),
    PRODUCT_SKU_ATTRIBUTES_EMPTY(500, "product.sku.attributes.empty", "SKU属性不能为空");

    private final int code;
    private final String messageKey;
    private final String defaultMessage;
}
