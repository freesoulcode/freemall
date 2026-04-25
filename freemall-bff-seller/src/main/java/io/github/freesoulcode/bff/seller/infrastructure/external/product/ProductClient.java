package io.github.freesoulcode.bff.seller.infrastructure.external.product;

import io.github.freesoulcode.bff.seller.infrastructure.external.product.dto.RemoteProductResponse;
import io.github.freesoulcode.common.interfaces.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "freemall-product", path = "/api/v1/product")
public interface ProductClient {

    @GetMapping("/{productId}")
    Result<RemoteProductResponse> getById(@PathVariable("productId") Long productId);

    @GetMapping("/merchant/{merchantId}")
    Result<List<RemoteProductResponse>> listByMerchant(@PathVariable("merchantId") Long merchantId);

    @PostMapping
    Result<Long> create(@RequestBody Map<String, Object> request);

    @PutMapping
    Result<Void> update(@RequestBody Map<String, Object> request);

    @PutMapping("/{productId}/publish")
    Result<Void> publish(@PathVariable("productId") Long productId);

    @PutMapping("/{productId}/offline")
    Result<Void> offline(@PathVariable("productId") Long productId);

    @DeleteMapping("/{productId}")
    Result<Void> delete(@PathVariable("productId") Long productId);
}
