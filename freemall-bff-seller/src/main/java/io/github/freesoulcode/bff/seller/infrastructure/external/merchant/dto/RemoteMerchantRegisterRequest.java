package io.github.freesoulcode.bff.seller.infrastructure.config.external.merchant.dto;

import lombok.Data;

@Data
public class RemoteMerchantRegisterRequest {
    private String username;
    private String password;
    private String email;
    private String phone;
}
