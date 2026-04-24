package io.github.freesoulcode.product.domain.model;

import io.github.freesoulcode.common.interfaces.BusinessException;
import io.github.freesoulcode.product.domain.BizErrorCode;
import lombok.Builder;
import lombok.Data;

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
     * 商品状态
     */
    private ProductStatus status;
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
}
