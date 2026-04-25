package io.github.freesoulcode.bff.seller.application.service;

import cloud.tianai.captcha.application.ImageCaptchaApplication;
import cloud.tianai.captcha.application.vo.ImageCaptchaVO;
import cloud.tianai.captcha.common.constant.CaptchaTypeConstant;
import cloud.tianai.captcha.common.response.ApiResponse;
import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import io.github.freesoulcode.bff.seller.application.request.CaptchaResponse;
import io.github.freesoulcode.common.interfaces.BusinessException;
import io.github.freesoulcode.common.interfaces.SystemErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 验证码应用服务 (BFF层)
 * 基于 TIANAI-CAPTCHA 实现行为验证码功能
 * 官方文档: https://doc.captcha.tianai.cloud/
 *
 * @author freesoulcode
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CaptchaService {

    /**
     * TIANAI 验证码核心应用接口
     * 在 Spring Boot 中由 tianai-captcha-springboot-starter 自动注入
     */
    private final ImageCaptchaApplication captchaApplication;

    /**
     * 生成滑块验证码
     *
     * @return 验证码数据（包含图片Base64和验证ID）
     */
    public CaptchaResponse generate() {
        // 生成滑块验证码 (SLIDER)
        ApiResponse<ImageCaptchaVO> response = captchaApplication.generateCaptcha(CaptchaTypeConstant.SLIDER);

        if (!response.isSuccess()) {
            log.error("Failed to generate captcha: {}", response.getMsg());
            throw new BusinessException(SystemErrorCode.SYSTEM_ERROR, "验证码生成失败");
        }

        ImageCaptchaVO captcha = response.getData();
        return CaptchaResponse.builder()
                .id(captcha.getId())
                .backgroundImage(captcha.getBackgroundImage())
                .sliderImage(captcha.getTemplateImage())
                .build();
    }

    /**
     * 第一阶段：验证滑块验证码 (轨迹校验)
     *
     * @param captchaId 验证码ID
     * @param track     前端传来的滑动轨迹数据
     * @return 验证成功后生成的 token (用于后续业务二次校验)
     */
    public String verify(String captchaId, ImageCaptchaTrack track) {
        // 调用 matching 方法进行轨迹匹配
        ApiResponse<?> response = captchaApplication.matching(captchaId, track);

        if (!response.isSuccess()) {
            throw new BusinessException(SystemErrorCode.PARAM_ERROR, "验证码校验失败");
        }

        // 开启二次验证后，matching 方法成功时返回的 data 就是 token 字符串
        Object data = response.getData();
        if (data instanceof String token) {
            return token;
        }
        
        // 兼容某些配置下返回 Map 的情况
        if (data instanceof Map<?, ?> dataMap) {
            return (String) dataMap.get("token");
        }
        
        return null;
    }

    /**
     * 第二阶段：二次验证 token 是否有效
     * 官方推荐做法：对于开启了二次验证的场景，只需调用 matching 并传入 token (track 为 null) 即可
     * 这样可以避免直接依赖 SecondaryVerificationApplication 类导致的注入或代理转换问题
     *
     * @param token 验证成功后生成的 token
     * @return 是否有效
     */
    public boolean secondaryVerify(String token) {
        if (token == null || token.isBlank()) {
            return false;
        }

        try {
            // 在 tianai-captcha 中，matching(token, null) 会触发二次验证逻辑
            // 校验成功返回 true，且该 token 会被从缓存中移除（防止重复使用）
            ApiResponse<?> response = captchaApplication.matching(token, (ImageCaptchaTrack) null);
            return response.isSuccess();
        } catch (Exception e) {
            log.error("Secondary verification error for token: {}", token, e);
            return false;
        }
    }
}
