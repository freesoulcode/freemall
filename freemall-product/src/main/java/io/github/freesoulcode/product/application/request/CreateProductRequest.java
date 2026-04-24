package io.github.freesoulcode.product.application.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CreateProductRequest {

    @NotNull(message = "商家ID不能为空")
    private Long merchantId;

    @NotNull(message = "店铺ID不能为空")
    private Long shopId;

    @NotBlank(message = "商品名称不能为空")
    private String name;

    private String subTitle;

    private String description;

    @Valid
    @NotEmpty(message = "SKU列表不能为空")
    private List<SkuRequest> skus;

    @Data
    public static class SkuRequest {

        @NotBlank(message = "SKU名称不能为空")
        private String name;

        private String skuCode;

        @NotEmpty(message = "SKU属性不能为空")
        private Map<String, String> skuAttributes;

        private String specImg;

        @NotNull(message = "SKU价格不能为空")
        private Integer price;

        private Integer costPrice;

        @NotNull(message = "库存不能为空")
        private Integer stock;
    }
}
