package io.github.freesoulcode.product.application.service.impl;

import io.github.freesoulcode.common.interfaces.BusinessException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.freesoulcode.product.application.convert.ProductAppConvert;
import io.github.freesoulcode.product.application.request.CreateProductRequest;
import io.github.freesoulcode.product.application.request.GetProductResponse;
import io.github.freesoulcode.product.application.request.ProductQuery;
import io.github.freesoulcode.product.application.request.UpdateProductRequest;
import io.github.freesoulcode.product.application.service.ProductApplicationService;
import io.github.freesoulcode.product.domain.BizErrorCode;
import io.github.freesoulcode.product.domain.model.Product;
import io.github.freesoulcode.product.domain.model.ProductStatus;
import io.github.freesoulcode.product.domain.model.Sku;
import io.github.freesoulcode.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    @Transactional
    public Long create(CreateProductRequest request) {
        List<Sku> skus = request.getSkus().stream()
                .map(skuReq -> Sku.builder()
                        .name(skuReq.getName())
                        .skuCode(skuReq.getSkuCode())
                        .price(skuReq.getPrice())
                        .costPrice(skuReq.getCostPrice())
                        .stock(skuReq.getStock())
                        .lockStock(0)
                        .availableStock(skuReq.getStock())
                        .specImg(skuReq.getSpecImg())
                        .build())
                .collect(Collectors.toList());

        Product product = Product.builder()
                .merchantId(request.getMerchantId())
                .shopId(request.getShopId())
                .name(request.getName())
                .subTitle(request.getSubTitle())
                .description(request.getDescription())
                .status(ProductStatus.DRAFT)
                .skus(skus)
                .build();

        productRepository.save(product);
        return product.getId();
    }

    @Override
    @Transactional
    public void update(UpdateProductRequest request) {
        Product product = productRepository.findById(request.getId())
                .orElseThrow(() -> new BusinessException(BizErrorCode.PRODUCT_NOT_FOUND));

        List<Sku> skus = request.getSkus() != null ? request.getSkus().stream()
                .map(skuReq -> Sku.builder()
                        .id(skuReq.getId())
                        .productId(product.getId())
                        .name(skuReq.getName())
                        .skuCode(skuReq.getSkuCode())
                        .price(skuReq.getPrice())
                        .costPrice(skuReq.getCostPrice())
                        .stock(skuReq.getStock())
                        .lockStock(0)
                        .availableStock(skuReq.getStock())
                        .specImg(skuReq.getSpecImg())
                        .build())
                .collect(Collectors.toList()) : product.getSkus();

        product.update(request.getName(), request.getSubTitle(), request.getDescription(), skus);
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void publish(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(BizErrorCode.PRODUCT_NOT_FOUND));
        product.submitAudit();
        product.onShelf();
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void offline(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(BizErrorCode.PRODUCT_NOT_FOUND));
        product.offShelf();
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void delete(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public List<GetProductResponse> listByMerchant(Long merchantId) {
        List<Product> products = productRepository.findByMerchantId(merchantId);
        return products.stream()
                .map(productAppConvert::toGetProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public IPage<GetProductResponse> search(ProductQuery query) {
        IPage<Product> productPage = productRepository.searchPage(
                query.getMerchantId(), query.getName(), query.getStatus(), query.getCategoryId(),
                query.getPage(), query.getSize()
        );
        Page<GetProductResponse> resultPage = new Page<>(query.getPage(), query.getSize());
        resultPage.setTotal(productPage.getTotal());
        resultPage.setRecords(productPage.getRecords().stream()
                .map(productAppConvert::toGetProductResponse)
                .collect(Collectors.toList()));
        return resultPage;
    }
}