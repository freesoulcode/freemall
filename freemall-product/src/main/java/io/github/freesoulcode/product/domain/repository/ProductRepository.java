package io.github.freesoulcode.product.domain.repository;

import io.github.freesoulcode.product.domain.model.Product;

import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(Long productId);
}
