package io.github.freesoulcode.product.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.freesoulcode.product.infrastructure.persistence.entity.ProductSkuPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductSkuMapper extends BaseMapper<ProductSkuPO> {
}
