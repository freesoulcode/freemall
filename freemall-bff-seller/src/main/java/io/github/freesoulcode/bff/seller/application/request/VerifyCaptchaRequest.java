package io.github.freesoulcode.bff.seller.application.request;

import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 验证验证码请求
 *
 * @author freesoulcode
 */
@Data
public class VerifyCaptchaRequest {
    @NotBlank(message = "验证码ID不能为空")
    private String captchaId;

    @NotNull(message = "验证码轨迹不能为空")
    private ImageCaptchaTrack track;
}
