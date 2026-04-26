package io.github.freesoulcode.bff.seller.interfaces.web;

import io.github.freesoulcode.bff.seller.application.service.ProductService;
import io.github.freesoulcode.bff.seller.infrastructure.config.security.SellerUserDetails;
import io.github.freesoulcode.bff.seller.infrastructure.external.product.dto.CreateProductRequest;
import io.github.freesoulcode.bff.seller.infrastructure.external.product.dto.RemoteProductResponse;
import io.github.freesoulcode.bff.seller.infrastructure.external.product.dto.UpdateProductRequest;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.freesoulcode.common.interfaces.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/list")
    public Result<List<RemoteProductResponse>> listByMerchant(@AuthenticationPrincipal SellerUserDetails userDetails) {
        return Result.success(productService.listByMerchant(userDetails.getId()));
    }

    @Operation(summary = "搜索商品（分页）")
    @GetMapping("/search")
    public Result<Page<RemoteProductResponse>> search(
            @AuthenticationPrincipal SellerUserDetails userDetails,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return Result.success(productService.search(userDetails.getId(), name, status, categoryId, page, size));
    }

    @Operation(summary = "创建商品")
    @PostMapping
    public Result<String> create(@AuthenticationPrincipal SellerUserDetails userDetails,
                                 @RequestBody CreateProductRequest request) {
        request.setMerchantId(userDetails.getId());
        Long id = productService.create(request);
        return Result.success(String.valueOf(id));
    }

    @Operation(summary = "更新商品")
    @PutMapping
    public Result<Void> update(@RequestBody UpdateProductRequest request) {
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