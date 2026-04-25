package io.github.freesoulcode.bff.seller.infrastructure.external.merchant.dto;

import lombok.Data;

@Data
public class RemoteMerchantQualificationRequest {
    private Long merchantId;
    private String companyName;
    private String businessLicenseUrl;
    private String taxId;
    private String legalPerson;
    private String contactPhone;
}
