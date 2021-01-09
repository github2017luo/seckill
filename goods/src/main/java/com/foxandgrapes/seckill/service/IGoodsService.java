package com.foxandgrapes.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.foxandgrapes.seckill.pojo.Goods;
import com.foxandgrapes.seckill.vo.RespBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tsk
 * @since 2021-01-06
 */
public interface IGoodsService extends IService<Goods> {

    RespBean getGoodsPage(Integer pageIndex, Integer pageSize);

    RespBean getGoods(Long id);
}
