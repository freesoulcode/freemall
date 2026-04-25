package io.github.freesoulcode.bff.seller.infrastructure.external.merchant.dto;

import lombok.Data;

@Data
public class RemoteMerchantAuthResponse {
    private Long id;
    private String username;
    private String password;
    private Integer status;
}
