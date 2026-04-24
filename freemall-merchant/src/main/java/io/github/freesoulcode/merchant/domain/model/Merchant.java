package io.github.freesoulcode.merchant.domain.model;

import io.github.freesoulcode.common.interfaces.BusinessException;
import io.github.freesoulcode.common.interfaces.SystemErrorCode;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Merchant {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private MerchantStatus status;
    private MerchantQualification qualification;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    /**
     * 注册
     */
    public static Merchant create(String username, String password, String email, String phone) {
        return Merchant.builder()
                .username(username)
                .password(password)
                .email(email)
                .phone(phone)
                .status(MerchantStatus.PENDING_VERIFY)
                .build();
    }

    /**
     * 验证
     */
    public void verify(String code) {
        if (!"123456".equals(code)) {
            throw new BusinessException(SystemErrorCode.PARAM_ERROR, "验证码错误");
        }
        if (this.status != MerchantStatus.PENDING_VERIFY) {
            throw new BusinessException(SystemErrorCode.FORBIDDEN, "当前状态不可验证");
        }
        this.status = MerchantStatus.VERIFIED;
    }

    /**
     * 提交资质
     */
    public void submitQualification(MerchantQualification qualification) {
        if (this.status != MerchantStatus.VERIFIED && this.status != MerchantStatus.REJECTED) {
            throw new BusinessException(SystemErrorCode.FORBIDDEN, "请先完成账号验证");
        }
        this.qualification = qualification;
        this.status = MerchantStatus.PENDING_AUDIT;
    }
}
