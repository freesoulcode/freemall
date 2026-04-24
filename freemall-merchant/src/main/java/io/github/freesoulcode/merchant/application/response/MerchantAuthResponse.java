package io.github.freesoulcode.merchant.application.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MerchantAuthResponse {
    private Long id;
    private String username;
    private String password;
    private Integer status;
}
