package io.github.freesoulcode.bff.admin.interfaces.web;

import io.github.freesoulcode.bff.admin.application.request.LoginRequest;
import io.github.freesoulcode.bff.admin.application.request.LoginResponse;
import io.github.freesoulcode.bff.admin.application.service.LoginApplicationService;
import io.github.freesoulcode.common.interfaces.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "认证管理", description = "后台登录相关接口")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    private final LoginApplicationService loginApplicationService;

    @Operation(summary = "管理员登录")
    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        return Result.success(loginApplicationService.login(request));
    }

    @Operation(summary = "退出登录")
    @PostMapping("/logout")
    public Result<Void> logout() {
        loginApplicationService.logout();
        return Result.success();
    }
}
