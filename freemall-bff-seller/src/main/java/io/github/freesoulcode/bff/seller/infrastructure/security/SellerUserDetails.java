package io.github.freesoulcode.bff.seller.infrastructure.security;

import io.github.freesoulcode.bff.seller.infrastructure.remote.merchant.dto.RemoteMerchantAuthResponse;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class SellerUserDetails implements UserDetails {

    @Getter
    private final RemoteMerchantAuthResponse merchant;

    public SellerUserDetails(RemoteMerchantAuthResponse merchant) {
        this.merchant = merchant;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_SELLER"));
    }

    @Override
    public String getPassword() {
        return merchant.getPassword();
    }

    @Override
    public String getUsername() {
        return merchant.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return merchant.getStatus() != 5; // 5-已禁用
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
