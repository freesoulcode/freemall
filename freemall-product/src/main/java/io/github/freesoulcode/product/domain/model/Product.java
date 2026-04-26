package io.github.freesoulcode.product.domain.model;

import io.github.freesoulcode.common.interfaces.BusinessException;
import io.github.freesoulcode.product.domain.BizErrorCode;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class Product {
    /**
     * 商品id
     */
    private Long id;
    /**
     * 商家id
     */
    private Long merchantId;
    /**
     * 店铺id
     */
    private Long shopId;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品副标题
     */
    private String subTitle;
    /**
     * 商品描述
     */
    private String description;
    /**
     * 主图
     */
    private String mainImage;
    /**
     * 商品状态
     */
    private ProductStatus status;
    /**
     * 创建时间（UTC）
     */
    private Instant createTime;
    /**
     * 更新时间（UTC）
     */
    private Instant updateTime;
    /**
     * sku列表
     */
    private List<Sku> skus ;

    /**
     * 提交审核
     */
    public void submitAudit() {
        if (this.status != ProductStatus.DRAFT && this.status != ProductStatus.AUDIT_REJECTED){
            throw new BusinessException(BizErrorCode.PRODUCT_CANNOT_SUBMIT_AUDIT, status.getName());
        }
        this.status = ProductStatus.PENDING_AUDIT;
    }

    public void onShelf() {
        //
        this.status = ProductStatus.ON_SHELF;
    }

    public void offShelf() {
        if (this.status != ProductStatus.ON_SHELF) {
            throw new BusinessException(BizErrorCode.PRODUCT_STATUS_INVALID, "当前状态不允许下架");
        }
        this.status = ProductStatus.OFF_SHELF;
    }

    public void update(String name, String subTitle, String description, List<Sku> skus) {
        if (this.status == ProductStatus.ON_SHELF) {
            throw new BusinessException(BizErrorCode.PRODUCT_STATUS_INVALID, "上架商品不能修改");
        }
        this.name = name;
        this.subTitle = subTitle;
        this.description = description;
        this.skus = skus;
    }
}
