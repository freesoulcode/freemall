package io.github.freesoulcode.product.application.request;

import lombok.Data;

@Data
public class ProductQuery {
    private Long merchantId;
    private String name;
    private Integer status;
    private Long categoryId;
    private int page = 1;
    private int size = 10;
}