package io.github.freesoulcode.merchant.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MerchantQualification {
    private Long id;
    private Long merchantId;
    private String companyName;
    private String businessLicenseUrl;
    private String taxId;
    private String legalPerson;
    private String contactPhone;
}
