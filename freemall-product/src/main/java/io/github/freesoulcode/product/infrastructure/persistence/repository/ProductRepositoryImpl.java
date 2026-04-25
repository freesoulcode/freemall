package io.github.freesoulcode.product.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.freesoulcode.product.domain.model.Product;
import io.github.freesoulcode.product.domain.model.ProductStatus;
import io.github.freesoulcode.product.domain.model.Sku;
import io.github.freesoulcode.product.domain.model.SkuAttributes;
import io.github.freesoulcode.product.domain.repository.ProductRepository;
import io.github.freesoulcode.product.infrastructure.persistence.entity.ProductSkuPO;
import io.github.freesoulcode.product.infrastructure.persistence.entity.ProductSpuPO;
import io.github.freesoulcode.product.infrastructure.persistence.mapper.ProductSkuMapper;
import io.github.freesoulcode.product.infrastructure.persistence.mapper.ProductSpuMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductSpuMapper spuMapper;
    private final ProductSkuMapper skuMapper;

    @Override
    public Optional<Product> findById(Long productId) {
        ProductSpuPO spuPO = spuMapper.selectById(productId);
        if (spuPO == null) {
            return Optional.empty();
        }

        // 查询SKU列表
        List<ProductSkuPO> skuPOList = skuMapper.selectList(
                new LambdaQueryWrapper<ProductSkuPO>().eq(ProductSkuPO::getSpuId, productId)
        );

        // 转换为领域模型
        Product product = convertToProduct(spuPO, skuPOList);
        return Optional.of(product);
    }

    @Override
    public List<Product> findByMerchantId(Long merchantId) {
        List<ProductSpuPO> spuPOList = spuMapper.selectList(
                new LambdaQueryWrapper<ProductSpuPO>().eq(ProductSpuPO::getMerchantId, merchantId)
        );
        return spuPOList.stream()
                .map(spu -> {
                    List<ProductSkuPO> skuPOList = skuMapper.selectList(
                            new LambdaQueryWrapper<ProductSkuPO>().eq(ProductSkuPO::getSpuId, spu.getId())
                    );
                    return convertToProduct(spu, skuPOList);
                })
                .collect(Collectors.toList());
    }

    @Override
    public void save(Product product) {
        ProductSpuPO spuPO = convertToSpuPO(product);
        
        if (product.getId() == null) {
            spuMapper.insert(spuPO);
            product.setId(spuPO.getId());
        } else {
            spuMapper.updateById(spuPO);
        }

        // 保存SKU
        if (product.getSkus() != null) {
            for (Sku sku : product.getSkus()) {
                ProductSkuPO skuPO = convertToSkuPO(sku, product.getId());
                if (sku.getId() == null) {
                    skuMapper.insert(skuPO);
                    sku.setId(skuPO.getId());
                } else {
                    skuMapper.updateById(skuPO);
                }
            }
        }
    }

    @Override
    public void deleteById(Long productId) {
        spuMapper.deleteById(productId);
        skuMapper.delete(new LambdaQueryWrapper<ProductSkuPO>().eq(ProductSkuPO::getSpuId, productId));
    }

    private Product convertToProduct(ProductSpuPO spuPO, List<ProductSkuPO> skuPOList) {
        List<Sku> skus = skuPOList.stream()
                .map(this::convertToSku)
                .collect(Collectors.toList());

        return Product.builder()
                .id(spuPO.getId())
                .merchantId(spuPO.getMerchantId())
                .name(spuPO.getName())
                .subTitle(null) // 数据库表没有这个字段，可以扩展
                .description(spuPO.getDescription())
                .status(ProductStatus.fromCode(spuPO.getStatus()))
                .createTime(spuPO.getCreateTime())
                .updateTime(spuPO.getUpdateTime())
                .skus(skus)
                .build();
    }

    private Sku convertToSku(ProductSkuPO skuPO) {
        return Sku.builder()
                .id(skuPO.getId())
                .productId(skuPO.getSpuId())
                .name(skuPO.getName())
                .skuCode(skuPO.getName()) // 可根据需要生成编码
                .price(skuPO.getPrice() != null ? skuPO.getPrice().intValue() : 0)
                .costPrice(skuPO.getOriginalPrice() != null ? skuPO.getOriginalPrice().intValue() : 0)
                .stock(skuPO.getStock() != null ? skuPO.getStock() : 0)
                .lockStock(0)
                .availableStock(skuPO.getStock() != null ? skuPO.getStock() : 0)
                .specImg(skuPO.getImage())
                .skuAttributes(SkuAttributes.builder().build()) // 可从 spec 字段解析
                .build();
    }

    private ProductSpuPO convertToSpuPO(Product product) {
        ProductSpuPO po = new ProductSpuPO();
        po.setId(product.getId());
        po.setName(product.getName());
        po.setMerchantId(product.getMerchantId());
        po.setDescription(product.getDescription());
        po.setStatus(product.getStatus().getCode());
        return po;
    }

    private ProductSkuPO convertToSkuPO(Sku sku, Long spuId) {
        ProductSkuPO po = new ProductSkuPO();
        po.setId(sku.getId());
        po.setSpuId(spuId);
        po.setName(sku.getName());
        po.setPrice(sku.getPrice() != null ? new java.math.BigDecimal(sku.getPrice()) : java.math.BigDecimal.ZERO);
        po.setOriginalPrice(sku.getCostPrice() != null ? new java.math.BigDecimal(sku.getCostPrice()) : java.math.BigDecimal.ZERO);
        po.setStock(sku.getStock());
        po.setImage(sku.getSpecImg());
        po.setStatus(1);
        return po;
    }
}
