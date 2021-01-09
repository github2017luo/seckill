package com.foxandgrapes.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.foxandgrapes.seckill.pojo.User;
import com.foxandgrapes.seckill.vo.RespBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tsk
 * @since 2021-01-06
 */
public interface IUserService extends IService<User> {

    RespBean login(Long userId, String password);

    RespBean register(Long userId, String password);
}
