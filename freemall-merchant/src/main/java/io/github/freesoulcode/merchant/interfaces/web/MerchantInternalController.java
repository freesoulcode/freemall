package io.github.freesoulcode.merchant.interfaces.web;

import io.github.freesoulcode.common.interfaces.Result;
import io.github.freesoulcode.merchant.application.response.MerchantAuthResponse;
import io.github.freesoulcode.merchant.application.request.MerchantQualificationRequest;
import io.github.freesoulcode.merchant.application.request.MerchantRegisterRequest;
import io.github.freesoulcode.merchant.application.request.MerchantVerifyRequest;
import io.github.freesoulcode.merchant.application.service.MerchantApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "商家内部接口", description = "供其他微服务远程调用")
@RestController
@RequestMapping("/internal/merchant")
@RequiredArgsConstructor
public class MerchantInternalController {

    private final MerchantApplicationService merchantApplicationService;

    @Operation(summary = "获取商家认证信息")
    @GetMapping("/auth-info")
    public Result<MerchantAuthResponse> getAuthInfo(@RequestParam String username) {
        return Result.success(merchantApplicationService.getAuthInfo(username));
    }

    @Operation(summary = "商家注册")
    @PostMapping("/register")
    public Result<Long> register(@RequestBody @Valid MerchantRegisterRequest request) {
        return Result.success(merchantApplicationService.register(request));
    }

    @Operation(summary = "商家验证")
    @PostMapping("/verify")
    public Result<Void> verify(@RequestBody @Valid MerchantVerifyRequest request) {
        merchantApplicationService.verify(request);
        return Result.success();
    }

    @Operation(summary = "提交企业资质")
    @PostMapping("/qualification/submit")
    public Result<Void> submitQualification(@RequestBody @Valid MerchantQualificationRequest request) {
        merchantApplicationService.submitQualification(request);
        return Result.success();
    }
}
