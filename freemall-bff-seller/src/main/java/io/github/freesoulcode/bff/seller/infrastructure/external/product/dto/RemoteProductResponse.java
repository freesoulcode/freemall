package io.github.freesoulcode.bff.seller.infrastructure.external.product.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class RemoteProductResponse {
    private String id;
    private String merchantId;
    private String name;
    private String subTitle;
    private String description;
    private Integer status;
    private String statusName;
    private List<SkuResponse> skus;

    @Data
    public static class SkuResponse {
        private String id;
        private String productId;
        private String name;
        private String skuCode;
        private Integer price;
        private Integer costPrice;
        private Integer stock;
        private String specImg;
    }
}
