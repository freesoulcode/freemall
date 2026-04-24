package io.github.freesoulcode.bff.admin.infrastructure.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.freesoulcode.bff.admin.infrastructure.persistence.entity.AdminUser;
import io.github.freesoulcode.bff.admin.infrastructure.persistence.mapper.AdminUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AdminUserMapper adminUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminUser adminUser = adminUserMapper.selectOne(
                new LambdaQueryWrapper<AdminUser>()
                        .eq(AdminUser::getUsername, username)
        );
        if (adminUser == null) {
            throw new UsernameNotFoundException("用户不存在: " + username);
        }
        return new AdminUserDetails(adminUser);
    }
}
