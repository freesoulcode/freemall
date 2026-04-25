package io.github.freesoulcode.bff.seller.application.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 提交商家资质请求
 *
 * @author freesoulcode
 */
@Data
public class SubmitQualificationRequest {
    @NotNull(message = "商家ID不能为空")
    private Long merchantId;

    @NotBlank(message = "公司名称不能为空")
    private String companyName;

    @NotNull(message = "营业执照文件不能为空")
    private MultipartFile businessLicenseFile;

    @NotBlank(message = "税务登记证号不能为空")
    private String taxId;

    @NotBlank(message = "法人姓名不能为空")
    private String legalPerson;

    @NotBlank(message = "联系电话不能为空")
    private String contactPhone;

    @NotBlank(message = "验证码Token不能为空")
    private String captchaToken;
}
