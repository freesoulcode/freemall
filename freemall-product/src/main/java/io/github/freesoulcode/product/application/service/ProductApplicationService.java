package io.github.freesoulcode.product.application.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.freesoulcode.product.application.request.CreateProductRequest;
import io.github.freesoulcode.product.application.request.GetProductResponse;
import io.github.freesoulcode.product.application.request.ProductQuery;
import io.github.freesoulcode.product.application.request.UpdateProductRequest;

import java.util.List;

public interface ProductApplicationService {
    GetProductResponse getById(Long productId);

    Long create(CreateProductRequest request);

    void update(UpdateProductRequest request);

    void publish(Long productId);

    void offline(Long productId);

    void delete(Long productId);

    List<GetProductResponse> listByMerchant(Long merchantId);

    IPage<GetProductResponse> search(ProductQuery query);
}