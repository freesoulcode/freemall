package io.github.freesoulcode.product.infrastructure.persistence.repository;

import io.github.freesoulcode.product.domain.model.Product;
import io.github.freesoulcode.product.domain.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    @Override
    public Optional<Product> findById(Long productId) {
        return Optional.empty();
    }
}
