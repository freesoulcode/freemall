package io.github.freesoulcode.product.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Sku {
    /**
     * SKU id
     */
    private Long id;
    /**
     * 商品id
     */
    private Long productId;
    /**
     * SKU名称
     */
    private String name;
    /**
     * SKU 编码
     */
    private String skuCode;
    /**
     * 属性
     */
    private SkuAttributes skuAttributes;
    /**
     * 规格图片
     */
    private String specImg;
    /**
     * 价格
     */
    private Integer price;
    /**
     * 成本价
     */
    private Integer costPrice;
    /**
     * 库存
     */
    private Integer stock;
    /**
     * 锁定库存
     */
    private Integer lockStock;
    /**
     * 可用库存
     */
    private Integer availableStock;


}
