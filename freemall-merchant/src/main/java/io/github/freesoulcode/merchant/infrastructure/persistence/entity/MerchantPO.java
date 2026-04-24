package io.github.freesoulcode.merchant.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("merchant")
public class MerchantPO {
    @TableId
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    /**
     * 状态：0-待验证，1-已验证，2-待审核，3-已审核，4-已驳回，5-已禁用
     */
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
