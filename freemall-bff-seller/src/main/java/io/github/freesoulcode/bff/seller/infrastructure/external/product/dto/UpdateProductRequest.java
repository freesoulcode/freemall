package io.github.freesoulcode.bff.seller.infrastructure.external.product.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdateProductRequest {
    private Long id;
    private String name;
    private String subTitle;
    private String description;
    private List<SkuRequest> skus;

    @Data
    public static class SkuRequest {
        private Long id;
        private String name;
        private String skuCode;
        private Integer price;
        private Integer costPrice;
        private Integer stock;
        private String specImg;
    }
}