package io.github.freesoulcode.product.application.service;

import io.github.freesoulcode.product.application.request.CreateProductRequest;
import io.github.freesoulcode.product.application.request.GetProductResponse;
import io.github.freesoulcode.product.application.request.UpdateProductRequest;

public interface ProductApplicationService {
    GetProductResponse getById(Long productId);

    Long create(CreateProductRequest request);

    void update(UpdateProductRequest request);

    void publish(Long productId);

    void offline(Long productId);

    void delete(Long productId);
}
