package io.github.freesoulcode.product.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * 商品SKU持久化对象
 */
@Data
@TableName("product_sku")
public class ProductSkuPO {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long spuId;
    private String name;
    private String spec;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private Integer stock;
    private String image;
    private Integer status;
    private Instant createTime;
    private Instant updateTime;
}
