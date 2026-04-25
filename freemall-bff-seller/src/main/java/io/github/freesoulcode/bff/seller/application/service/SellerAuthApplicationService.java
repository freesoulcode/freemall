package io.github.freesoulcode.bff.seller.application.service;

import io.github.freesoulcode.bff.seller.application.request.SellerLoginRequest;
import io.github.freesoulcode.bff.seller.application.request.SellerLoginResponse;
import io.github.freesoulcode.bff.seller.infrastructure.config.security.JwtUtils;
import io.github.freesoulcode.bff.seller.infrastructure.config.security.SellerUserDetails;
import io.github.freesoulcode.bff.seller.infrastructure.external.merchant.MerchantClient;
import io.github.freesoulcode.bff.seller.infrastructure.external.merchant.dto.RemoteMerchantRegisterRequest;
import io.github.freesoulcode.bff.seller.infrastructure.external.merchant.dto.RemoteMerchantVerifyRequest;
import io.github.freesoulcode.common.interfaces.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerAuthApplicationService {

    private final MerchantClient merchantClient;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public Long register(RemoteMerchantRegisterRequest request) {
        Result<Long> result = merchantClient.register(request);
        return result.getData();
    }

    public void verify(RemoteMerchantVerifyRequest request) {
        merchantClient.verify(request);
    }

    public SellerLoginResponse login(SellerLoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        SellerUserDetails userDetails = (SellerUserDetails) authentication.getPrincipal();
        String token = jwtUtils.generateToken(userDetails);

        return SellerLoginResponse.builder()
                .token(token)
                .username(userDetails.getUsername())
                .role("SELLER")
                .merchantId(String.valueOf(userDetails.getId()))
                .build();
    }
}
