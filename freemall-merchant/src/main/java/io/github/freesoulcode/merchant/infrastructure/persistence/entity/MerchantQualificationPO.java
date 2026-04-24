package io.github.freesoulcode.merchant.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("merchant_qualification")
public class MerchantQualificationPO {
    @TableId
    private Long id;
    private Long merchantId;
    private String companyName;
    private String businessLicenseUrl;
    private String taxId;
    private String legalPerson;
    private String contactPhone;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
