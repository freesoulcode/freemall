package io.github.freesoulcode.bff.seller.infrastructure.config.external.merchant.dto;

import lombok.Data;

@Data
public class RemoteMerchantVerifyRequest {
    private Long merchantId;
    private String code;
}
