package io.github.freesoulcode.bff.seller.application.service;

import cloud.tianai.captcha.application.ImageCaptchaApplication;
import cloud.tianai.captcha.application.vo.ImageCaptchaVO;
import cloud.tianai.captcha.common.response.ApiResponse;
import cloud.tianai.captcha.validator.common.constant.CaptchaTypeConstant;
import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import cloud.tianai.captcha.application.SecondaryVerificationApplication;
import io.github.freesoulcode.common.interfaces.BusinessException;
import io.github.freesoulcode.common.interfaces.SystemErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 验证码服务 (BFF层)
 * 基于TIANAI-CAPTCHA实现滑块验证码功能
 *
 * @author freesoulcode
 */
@Service
@RequiredArgsConstructor
public class CaptchaService {

    private final ImageCaptchaApplication captchaApplication;

    /**
     * 生成滑块验证码
     *
     * @return 验证码数据（包含图片Base64和验证ID）
     */
    public CaptchaVO generate() {
        ApiResponse<ImageCaptchaVO> response =
                captchaApplication.generateCaptcha(CaptchaTypeConstant.SLIDER);

        if (!response.isSuccess()) {
            throw new BusinessException(SystemErrorCode.SYSTEM_ERROR, "验证码生成失败");
        }

        ImageCaptchaVO captcha = response.getData();
        return CaptchaVO.builder()
                .id(captcha.getId())
                .backgroundImage(captcha.getBackgroundImage())
                .sliderImage(captcha.getSliderImage())
                .build();
    }

    /**
     * 验证滑块验证码
     *
     * @param captchaId 验证码ID
     * @param track     前端传来的滑动轨迹数据
     * @return 验证token（验证成功时返回，用于二次校验）
     */
    public String verify(String captchaId, ImageCaptchaTrack track) {
        ApiResponse<?> response = captchaApplication.matching(captchaId, track);

        if (!response.isSuccess()) {
            throw new BusinessException(SystemErrorCode.PARAM_ERROR, "验证码校验失败");
        }

        // 如果开启了二次验证，返回的data中包含token
        Object data = response.getData();
        if (data instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> dataMap = (Map<String, Object>) data;
            return (String) dataMap.get("token");
        }
        return null;
    }

    /**
     * 二次验证token是否有效
     *
     * @param token 验证成功后生成的token
     * @return 是否有效
     */
    public boolean secondaryVerify(String token) {
        if (captchaApplication instanceof SecondaryVerificationApplication app) {
            return app.secondaryVerification(token);
        }
        // 如果没有开启二次验证，直接返回false
        return false;
    }
}
