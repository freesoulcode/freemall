package io.github.freesoulcode.product.domain.repository;

import io.github.freesoulcode.product.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(Long productId);
    List<Product> findByMerchantId(Long merchantId);
    void save(Product product);
    void deleteById(Long productId);
}
