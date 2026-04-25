package io.github.freesoulcode.bff.seller.application.service;


import io.github.freesoulcode.bff.seller.infrastructure.config.storage.FileStorageService;
import io.github.freesoulcode.bff.seller.infrastructure.external.merchant.MerchantClient;
import io.github.freesoulcode.bff.seller.infrastructure.external.merchant.dto.RemoteMerchantQualificationRequest;
import io.github.freesoulcode.common.interfaces.BusinessException;
import io.github.freesoulcode.common.interfaces.SystemErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 商家资质应用服务 (BFF层)
 *
 * @author freesoulcode
 */
@Service
@RequiredArgsConstructor
public class MerchantQualificationService {

    private final CaptchaService captchaService;
    private final FileStorageService fileStorageService;
    private final MerchantClient merchantClient;

    /**
     * 提交商家资质
     *
     * @param merchantId          商家ID
     * @param captchaToken        验证码token
     * @param businessLicenseFile 营业执照文件
     * @param companyName         公司名称
     * @param taxId               税务登记证号
     * @param legalPerson         法人姓名
     * @param contactPhone        联系电话
     */
    public void submitQualification(
            Long merchantId,
            String captchaToken,
            MultipartFile businessLicenseFile,
            String companyName,
            String taxId,
            String legalPerson,
            String contactPhone
    ) {
        // 1. 二次验证token有效性
        if (!captchaService.secondaryVerify(captchaToken)) {
            throw new BusinessException(SystemErrorCode.PARAM_ERROR, "验证码已过期或无效");
        }

        // 2. 上传营业执照到RustFS
        String licenseUrl = fileStorageService.uploadFile(
                businessLicenseFile,
                "merchant/license/" + merchantId
        );

        // 3. 构造请求并调用merchant服务
        RemoteMerchantQualificationRequest request = new RemoteMerchantQualificationRequest();
        request.setMerchantId(merchantId);
        request.setCompanyName(companyName);
        request.setBusinessLicenseUrl(licenseUrl);
        request.setTaxId(taxId);
        request.setLegalPerson(legalPerson);
        request.setContactPhone(contactPhone);

        merchantClient.submitQualification(request);
    }
}
