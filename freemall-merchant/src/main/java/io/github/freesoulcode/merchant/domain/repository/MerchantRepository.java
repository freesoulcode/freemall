package io.github.freesoulcode.merchant.domain.repository;

import io.github.freesoulcode.merchant.domain.model.Merchant;

import java.util.Optional;

public interface MerchantRepository {
    Optional<Merchant> findById(Long id);
    Optional<Merchant> findByUsername(String username);
    void save(Merchant merchant);
}
