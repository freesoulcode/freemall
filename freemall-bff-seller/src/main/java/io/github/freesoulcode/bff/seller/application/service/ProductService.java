package io.github.freesoulcode.bff.seller.application.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.freesoulcode.bff.seller.infrastructure.external.product.ProductClient;
import io.github.freesoulcode.bff.seller.infrastructure.external.product.dto.CreateProductRequest;
import io.github.freesoulcode.bff.seller.infrastructure.external.product.dto.RemoteProductResponse;
import io.github.freesoulcode.bff.seller.infrastructure.external.product.dto.UpdateProductRequest;
import io.github.freesoulcode.common.interfaces.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductClient productClient;

    public RemoteProductResponse getById(Long productId) {
        Result<RemoteProductResponse> result = productClient.getById(productId);
        return result.getData();
    }

    public List<RemoteProductResponse> listByMerchant(Long merchantId) {
        Result<List<RemoteProductResponse>> result = productClient.listByMerchant(merchantId);
        return result.getData();
    }

    public Page<RemoteProductResponse> search(Long merchantId, String name, Integer status, Long categoryId, int page, int size) {
        Result<Page<RemoteProductResponse>> result = productClient.search(merchantId, name, status, categoryId, page, size);
        return result.getData();
    }

    public Long create(CreateProductRequest request) {
        Result<Long> result = productClient.create(request);
        return result.getData();
    }

    public void update(UpdateProductRequest request) {
        productClient.update(request);
    }

    public void publish(Long productId) {
        productClient.publish(productId);
    }

    public void offline(Long productId) {
        productClient.offline(productId);
    }

    public void delete(Long productId) {
        productClient.delete(productId);
    }
}