package io.github.freesoulcode.product.domain.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.freesoulcode.product.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(Long productId);
    List<Product> findByMerchantId(Long merchantId);
    IPage<Product> searchPage(Long merchantId, String name, Integer status, Long categoryId, int page, int size);
    void save(Product product);
    void deleteById(Long productId);
}