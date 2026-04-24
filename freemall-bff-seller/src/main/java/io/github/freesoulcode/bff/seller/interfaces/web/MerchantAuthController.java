package io.github.freesoulcode.bff.seller.interfaces.web;

import io.github.freesoulcode.bff.seller.application.request.SellerLoginRequest;
import io.github.freesoulcode.bff.seller.application.request.SellerLoginResponse;
import io.github.freesoulcode.bff.seller.application.service.SellerAuthApplicationService;
import io.github.freesoulcode.bff.seller.infrastructure.remote.merchant.dto.RemoteMerchantRegisterRequest;
import io.github.freesoulcode.bff.seller.infrastructure.remote.merchant.dto.RemoteMerchantVerifyRequest;
import io.github.freesoulcode.common.interfaces.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "商家认证", description = "商家注册、登录、验证接口")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class MerchantAuthController {

    private final SellerAuthApplicationService sellerAuthApplicationService;

    @Operation(summary = "商家注册")
    @PostMapping("/register")
    public Result<Long> register(@RequestBody @Valid RemoteMerchantRegisterRequest request) {
        return Result.success(sellerAuthApplicationService.register(request));
    }

    @Operation(summary = "商家验证")
    @PostMapping("/verify")
    public Result<Void> verify(@RequestBody @Valid RemoteMerchantVerifyRequest request) {
        sellerAuthApplicationService.verify(request);
        return Result.success();
    }

    @Operation(summary = "商家登录")
    @PostMapping("/login")
    public Result<SellerLoginResponse> login(@RequestBody @Valid SellerLoginRequest request) {
        return Result.success(sellerAuthApplicationService.login(request));
    }
}
