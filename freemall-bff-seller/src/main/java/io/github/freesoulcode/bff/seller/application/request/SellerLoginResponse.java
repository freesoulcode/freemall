package io.github.freesoulcode.bff.seller.application.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerLoginResponse {
    private String token;
    private String username;
    private String role;
}
