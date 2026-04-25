package io.github.freesoulcode.bff.seller.application.service;

import io.github.freesoulcode.bff.seller.infrastructure.external.product.ProductClient;
import io.github.freesoulcode.bff.seller.infrastructure.external.product.dto.RemoteProductResponse;
import io.github.freesoulcode.common.interfaces.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    public Long create(Map<String, Object> request) {
        Result<Long> result = productClient.create(request);
        return result.getData();
    }

    public void update(Map<String, Object> request) {
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
