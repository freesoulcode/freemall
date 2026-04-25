package io.github.freesoulcode.bff.seller.infrastructure.config.config;

import cloud.tianai.captcha.resource.ResourceStore;
import cloud.tianai.captcha.resource.impl.LocalMemoryResourceStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TIANAI验证码配置 (BFF层)
 *
 * @author freesoulcode
 */
@Configuration
public class CaptchaConfig {

    /**
     * 配置验证码资源存储器
     * 注意：如果使用系统自带模板，背景图尺寸需要改成600x360
     */
    @Bean
    public ResourceStore resourceStore() {
        LocalMemoryResourceStore resourceStore = new LocalMemoryResourceStore();

        // 配置滑块验证码背景图（建议尺寸: 600x360）
        // 可以从 classpath、file、url 加载图片
        // 这里使用系统默认图片作为示例，实际项目中应替换为自己的背景图
        
        return resourceStore;
    }
}
