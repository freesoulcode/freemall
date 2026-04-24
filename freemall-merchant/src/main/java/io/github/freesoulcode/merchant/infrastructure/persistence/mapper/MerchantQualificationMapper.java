package io.github.freesoulcode.merchant.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.freesoulcode.merchant.infrastructure.persistence.entity.MerchantQualificationPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MerchantQualificationMapper extends BaseMapper<MerchantQualificationPO> {
}
