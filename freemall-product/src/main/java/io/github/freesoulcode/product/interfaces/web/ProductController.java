package io.github.freesoulcode.product.interfaces.web;

import io.github.freesoulcode.common.interfaces.Result;
import io.github.freesoulcode.product.application.request.CreateProductRequest;
import io.github.freesoulcode.product.application.request.GetProductResponse;
import io.github.freesoulcode.product.application.request.UpdateProductRequest;
import io.github.freesoulcode.product.application.service.ProductApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "商品服务", description = "商品服务接口")
@RestController
@RequestMapping("/api/v1/product")
@AllArgsConstructor
public class ProductController {
    private final ProductApplicationService productApplicationService;

    @Operation(summary = "获取商品信息")
    @GetMapping("/{productId}")
    public Result<GetProductResponse> getById(@PathVariable Long productId) {
        return Result.success(productApplicationService.getById(productId));
    }

    @Operation(summary = "获取商家商品列表")
    @GetMapping("/merchant/{merchantId}")
    public Result<List<GetProductResponse>> listByMerchant(@PathVariable Long merchantId) {
        return Result.success(productApplicationService.listByMerchant(merchantId));
    }

    @Operation(summary = "创建商品")
    @PostMapping
    public Result<Long> create(@RequestBody CreateProductRequest request) {
        return Result.success(productApplicationService.create(request));
    }

    @Operation(summary = "更新商品信息")
    @PutMapping
    public Result<Void> update(@RequestBody UpdateProductRequest request) {
        productApplicationService.update(request);
        return Result.success();
    }

    @Operation(summary = "上架商品")
    @PutMapping("/{productId}/publish")
    public Result<Void> publish(@PathVariable Long productId) {
        productApplicationService.publish(productId);
        return Result.success();
    }

    @Operation(summary = "下架商品")
    @PutMapping("/{productId}/offline")
    public Result<Void> offline(@PathVariable Long productId) {
        productApplicationService.offline(productId);
        return Result.success();
    }

    @Operation(summary = "删除商品")
    @DeleteMapping("/{productId}")
    public Result<Void> delete(@PathVariable Long productId) {
        productApplicationService.delete(productId);
        return Result.success();
    }
}
