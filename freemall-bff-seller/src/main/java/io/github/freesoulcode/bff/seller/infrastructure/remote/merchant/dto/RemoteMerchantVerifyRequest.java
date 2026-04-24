package io.github.freesoulcode.bff.seller.infrastructure.remote.merchant.dto;

import lombok.Data;

@Data
public class RemoteMerchantVerifyRequest {
    private Long merchantId;
    private String code;
}
