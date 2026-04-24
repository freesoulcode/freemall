package io.github.freesoulcode.bff.seller.infrastructure.security;

import io.github.freesoulcode.bff.seller.infrastructure.remote.merchant.MerchantClient;
import io.github.freesoulcode.bff.seller.infrastructure.remote.merchant.dto.RemoteMerchantAuthResponse;
import io.github.freesoulcode.common.interfaces.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerDetailsServiceImpl implements UserDetailsService {

    private final MerchantClient merchantClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Result<RemoteMerchantAuthResponse> result = merchantClient.getAuthInfo(username);
        RemoteMerchantAuthResponse authResponse = result.getData();
        
        if (authResponse == null) {
            throw new UsernameNotFoundException("商家不存在: " + username);
        }
        return new SellerUserDetails(authResponse);
    }
}
