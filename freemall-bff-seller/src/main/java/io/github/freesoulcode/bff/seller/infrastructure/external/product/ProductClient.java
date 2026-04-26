package io.github.freesoulcode.bff.seller.infrastructure.external.product;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.freesoulcode.bff.seller.infrastructure.external.product.dto.CreateProductRequest;
import io.github.freesoulcode.bff.seller.infrastructure.external.product.dto.RemoteProductResponse;
import io.github.freesoulcode.bff.seller.infrastructure.external.product.dto.UpdateProductRequest;
import io.github.freesoulcode.common.interfaces.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "freemall-product", path = "/api/v1/product")
public interface ProductClient {

    @GetMapping("/{productId}")
    Result<RemoteProductResponse> getById(@PathVariable("productId") Long productId);

    @GetMapping("/merchant/{merchantId}")
    Result<List<RemoteProductResponse>> listByMerchant(@PathVariable("merchantId") Long merchantId);

    @GetMapping("/search")
    Result<Page<RemoteProductResponse>> search(
            @RequestParam("merchantId") Long merchantId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    );

    @PostMapping
    Result<Long> create(@RequestBody CreateProductRequest request);

    @PutMapping
    Result<Void> update(@RequestBody UpdateProductRequest request);

    @PutMapping("/{productId}/publish")
    Result<Void> publish(@PathVariable("productId") Long productId);

    @PutMapping("/{productId}/offline")
    Result<Void> offline(@PathVariable("productId") Long productId);

    @DeleteMapping("/{productId}")
    Result<Void> delete(@PathVariable("productId") Long productId);
}