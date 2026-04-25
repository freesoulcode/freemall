package io.github.freesoulcode.bff.seller.interfaces.web;

import io.github.freesoulcode.bff.seller.application.request.CaptchaResponse;
import io.github.freesoulcode.bff.seller.application.request.SubmitQualificationRequest;
import io.github.freesoulcode.bff.seller.application.request.VerifyCaptchaRequest;
import io.github.freesoulcode.bff.seller.application.service.CaptchaService;
import io.github.freesoulcode.bff.seller.application.service.MerchantQualificationService;
import io.github.freesoulcode.common.interfaces.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 商家资质Controller (BFF层)
 * 只负责接收请求和返回响应，业务逻辑在Service层
 *
 * @author freesoulcode
 */
@Tag(name = "商家资质管理", description = "商家资质审核相关接口")
@RestController
@RequestMapping("/api/seller/qualification")
@RequiredArgsConstructor
public class MerchantQualificationController {

    private final CaptchaService captchaService;
    private final MerchantQualificationService merchantQualificationService;

    @Operation(summary = "获取滑块验证码")
    @GetMapping("/captcha")
    public Result<CaptchaResponse> getCaptcha() {
        return Result.success(captchaService.generate());
    }

    @Operation(summary = "验证滑块验证码")
    @PostMapping("/captcha/verify")
    public Result<String> verifyCaptcha(@RequestBody @Valid VerifyCaptchaRequest request) {
        String token = captchaService.verify(request.getCaptchaId(), request.getTrack());
        return Result.success(token);
    }

    @Operation(summary = "提交商家资质")
    @PostMapping("/submit")
    public Result<Void> submitQualification(@ModelAttribute @Valid SubmitQualificationRequest request) {
        merchantQualificationService.submitQualification(
                request.getMerchantId(),
                request.getCaptchaToken(),
                request.getBusinessLicenseFile(),
                request.getCompanyName(),
                request.getTaxId(),
                request.getLegalPerson(),
                request.getContactPhone()
        );
        return Result.success();
    }
}
