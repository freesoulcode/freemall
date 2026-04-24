package io.github.freesoulcode.merchant.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MerchantStatus {
    PENDING_VERIFY(0, "待验证"),
    VERIFIED(1, "已验证"),
    PENDING_AUDIT(2, "待审核"),
    AUDITED(3, "已审核"),
    REJECTED(4, "已驳回"),
    DISABLED(5, "已禁用");

    private final int code;
    private final String description;

    public static MerchantStatus fromCode(int code) {
        for (MerchantStatus status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        return PENDING_VERIFY;
    }
}
