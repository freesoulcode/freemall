package io.github.freesoulcode.bff.seller.infrastructure.config.external.merchant;

import io.github.freesoulcode.bff.seller.infrastructure.external.merchant.dto.RemoteMerchantAuthResponse;
import io.github.freesoulcode.bff.seller.infrastructure.external.merchant.dto.RemoteMerchantQualificationRequest;
import io.github.freesoulcode.bff.seller.infrastructure.external.merchant.dto.RemoteMerchantRegisterRequest;
import io.github.freesoulcode.bff.seller.infrastructure.external.merchant.dto.RemoteMerchantVerifyRequest;
import io.github.freesoulcode.common.interfaces.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "freemall-merchant", path = "/internal/merchant")
public interface MerchantClient {

    @GetMapping("/auth-info")
    Result<RemoteMerchantAuthResponse> getAuthInfo(@RequestParam("username") String username);

    @PostMapping("/register")
    Result<Long> register(@RequestBody RemoteMerchantRegisterRequest request);

    @PostMapping("/verify")
    Result<Void> verify(@RequestBody RemoteMerchantVerifyRequest request);

    @PostMapping("/qualification/submit")
    Result<Void> submitQualification(@RequestBody RemoteMerchantQualificationRequest request);
}
