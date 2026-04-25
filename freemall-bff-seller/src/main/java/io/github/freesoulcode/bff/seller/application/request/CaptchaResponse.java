package io.github.freesoulcode.bff.seller.application.request;

import lombok.Builder;
import lombok.Data;

/**
 * 验证码响应对象
 *
 * @author freesoulcode
 */
@Data
@Builder
public class CaptchaResponse {
    /**
     * 验证码唯一ID
     */
    private String id;

    /**
     * 背景图片Base64
     */
    private String backgroundImage;

    /**
     * 滑块图片Base64
     */
    private String sliderImage;
}
