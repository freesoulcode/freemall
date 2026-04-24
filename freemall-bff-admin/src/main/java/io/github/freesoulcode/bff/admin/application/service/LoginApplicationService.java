package io.github.freesoulcode.bff.admin.application.service;

import io.github.freesoulcode.bff.admin.application.request.LoginRequest;
import io.github.freesoulcode.bff.admin.application.request.LoginResponse;
import io.github.freesoulcode.bff.admin.infrastructure.security.AdminUserDetails;
import io.github.freesoulcode.bff.admin.infrastructure.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginApplicationService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public LoginResponse login(LoginRequest request) {
        // 1. 执行认证
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // 2. 认证通过后，将认证信息存入上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. 生成 Token
        AdminUserDetails userDetails = (AdminUserDetails) authentication.getPrincipal();
        String jwt = jwtUtils.generateToken(userDetails);

        // 4. 返回结果
        return LoginResponse.builder()
                .token(jwt)
                .username(userDetails.getUsername())
                .role("ADMIN")
                .build();
    }

    public void logout() {
        SecurityContextHolder.clearContext();
    }
}
