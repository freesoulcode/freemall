package io.github.freesoulcode.bff.seller.interfaces.web;

import io.github.freesoulcode.bff.seller.application.service.ProductService;
import io.github.freesoulcode.bff.seller.infrastructure.external.product.dto.RemoteProductResponse;
import io.github.freesoulcode.common.interfaces.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "商品管理", description = "商家商品管理接口")
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "获取商品详情")
    @GetMapping("/{productId}")
    public Result<RemoteProductResponse> getById(@PathVariable Long productId) {
        return Result.success(productService.getById(productId));
    }

    @Operation(summary = "获取商家商品列表")
    @GetMapping("/merchant/{merchantId}")
    public Result<List<RemoteProductResponse>> listByMerchant(@PathVariable Long merchantId) {
        return Result.success(productService.listByMerchant(merchantId));
    }

    @Operation(summary = "创建商品")
    @PostMapping
    public Result<String> create(@RequestBody Map<String, Object> request) {
        Long id = productService.create(request);
        return Result.success(String.valueOf(id));
    }

    @Operation(summary = "更新商品")
    @PutMapping
    public Result<Void> update(@RequestBody Map<String, Object> request) {
        productService.update(request);
        return Result.success();
    }

    @Operation(summary = "上架商品")
    @PutMapping("/{productId}/publish")
    public Result<Void> publish(@PathVariable Long productId) {
        productService.publish(productId);
        return Result.success();
    }

    @Operation(summary = "下架商品")
    @PutMapping("/{productId}/offline")
    public Result<Void> offline(@PathVariable Long productId) {
        productService.offline(productId);
        return Result.success();
    }

    @Operation(summary = "删除商品")
    @DeleteMapping("/{productId}")
    public Result<Void> delete(@PathVariable Long productId) {
        productService.delete(productId);
        return Result.success();
    }
}
