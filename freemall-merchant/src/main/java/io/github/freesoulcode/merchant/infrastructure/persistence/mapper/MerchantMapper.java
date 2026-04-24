package io.github.freesoulcode.merchant.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.freesoulcode.merchant.infrastructure.persistence.entity.MerchantPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MerchantMapper extends BaseMapper<MerchantPO> {
}
