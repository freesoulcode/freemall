package io.github.freesoulcode.merchant.application.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MerchantVerifyRequest {
    @NotNull(message = "商家ID不能为空")
    private Long merchantId;
    
    @NotBlank(message = "验证码不能为空")
    private String code;
}
