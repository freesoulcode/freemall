package io.github.freesoulcode.product.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.Instant;

/**
 * 商品SPU持久化对象
 */
@Data
@TableName("product_spu")
public class ProductSpuPO {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String name;
    private Long categoryId;
    private Long brandId;
    private Long merchantId;
    private String mainImage;
    private String images;
    private String description;
    private Integer status;
    private Instant createTime;
    private Instant updateTime;
}
