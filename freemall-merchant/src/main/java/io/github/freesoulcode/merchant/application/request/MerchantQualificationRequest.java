package io.github.freesoulcode.merchant.application.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MerchantQualificationRequest {
    @NotNull(message = "商家ID不能为空")
    private Long merchantId;
    
    @NotBlank(message = "公司名称不能为空")
    private String companyName;
    
    @NotBlank(message = "营业执照图片不能为空")
    private String businessLicenseUrl;
    
    @NotBlank(message = "税务登记证号不能为空")
    private String taxId;
    
    @NotBlank(message = "法人姓名不能为空")
    private String legalPerson;
    
    @NotBlank(message = "联系电话不能为空")
    private String contactPhone;
}
