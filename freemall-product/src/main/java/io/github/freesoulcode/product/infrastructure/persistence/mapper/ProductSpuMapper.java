package io.github.freesoulcode.product.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.freesoulcode.product.infrastructure.persistence.entity.ProductSpuPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductSpuMapper extends BaseMapper<ProductSpuPO> {
}
