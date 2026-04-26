package io.github.freesoulcode.product.application.request;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class GetProductResponse {
    private String id;
    private String merchantId;
    private String name;
    private String subTitle;
    private String description;
    private Integer status;
    private String statusName;
    private String mainImage;
    private Instant createTime;
    private Instant updateTime;
    private List<SkuResponse> skus;

    @Data
    @Builder
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
