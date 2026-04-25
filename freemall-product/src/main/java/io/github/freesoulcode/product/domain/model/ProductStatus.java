package io.github.freesoulcode.product.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductStatus {
    DRAFT(0, "草稿", "编辑中，不可见，不可售"),
    PENDING_AUDIT(1, "待审核", "已提交，等待审核"),
    AUDIT_REJECTED(2, "审核驳回", "审核未通过，需修改"),
    PENDING_SHELF(3, "待上架", "审核通过，等待上架"),
    ON_SHELF(4, "在售", "正常销售中"),
    OFF_SHELF(5, "已下架", "手动下架，停止销售"),
    SOLD_OUT(6, "售罄", "库存为0，自动下架"),
    PAUSED(7, "暂停销售", "临时暂停，可恢复"),
    DELETED(8, "已删除", "逻辑删除，不可恢复");

    private final Integer code;
    private final String name;
    private final String description;

    public static ProductStatus fromCode(Integer code) {
        if (code == null) return null;
        for (ProductStatus status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        return null;
    }
}
