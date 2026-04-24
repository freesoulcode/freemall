package io.github.freesoulcode.bff.seller.interfaces.web;

import io.github.freesoulcode.bff.seller.infrastructure.remote.merchant.MerchantClient;
import io.github.freesoulcode.bff.seller.infrastructure.remote.merchant.dto.RemoteMerchantQualificationRequest;
import io.github.freesoulcode.common.interfaces.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "商家资质", description = "商家资质提交接口")
@RestController
@RequestMapping("/qualification")
@RequiredArgsConstructor
public class MerchantQualificationController {

    private final MerchantClient merchantClient;

    @Operation(summary = "提交企业资质")
    @PostMapping("/submit")
    public Result<Void> submit(@RequestBody @Valid RemoteMerchantQualificationRequest request) {
        return merchantClient.submitQualification(request);
    }
}
