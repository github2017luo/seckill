package com.foxandgrapes.seckill.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 商品类
 * </p>
 *
 * @author tsk
 * @since 2021-01-06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("tb_goods")
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品标题
     */
    private String goodsTitle;

    /**
     * 商品图片
     */
    private String goodsImg;

    /**
     * 商品详情
     */
    private String goodsDetail;

    /**
     * 商品价格
     */
    private BigDecimal goodsPrice;

    /**
     * 秒杀信息
     */
    @TableField(exist = false)
    private SeckillGoods seckillGoods;

    /**
     * 展示商品的当前时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Shanghai")
    @TableField(exist = false)
    private Date nowTime;
}
