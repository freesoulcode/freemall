package io.github.freesoulcode.product.application.service.impl;

import io.github.freesoulcode.common.interfaces.BusinessException;
import io.github.freesoulcode.product.application.convert.ProductAppConvert;
import io.github.freesoulcode.product.application.request.CreateProductRequest;
import io.github.freesoulcode.product.application.request.GetProductResponse;
import io.github.freesoulcode.product.application.request.UpdateProductRequest;
import io.github.freesoulcode.product.application.service.ProductApplicationService;
import io.github.freesoulcode.product.domain.BizErrorCode;
import io.github.freesoulcode.product.domain.model.Product;
import io.github.freesoulcode.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductApplicationServiceImpl implements ProductApplicationService {

    private final ProductRepository productRepository;
    private final ProductAppConvert productAppConvert;

    @Override
    public GetProductResponse getById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(BizErrorCode.PRODUCT_NOT_FOUND));
        return productAppConvert.toGetProductResponse(product);
    }

    @Override
    public Long create(CreateProductRequest request) {
        return 0L;
    }

    @Override
    public void update(UpdateProductRequest request) {

    }

    @Override
    public void publish(Long productId) {

    }

    @Override
    public void offline(Long productId) {

    }

    @Override
    public void delete(Long productId) {

    }
}
