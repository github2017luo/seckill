package com.foxandgrapes.seckill.service;

import com.foxandgrapes.seckill.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tsk
 * @since 2021-01-06
 */
public interface IUserService extends IService<User> {

    Map<String, Object> login(Long userId, String password);

    Map<String, Object> register(Long userId, String password);
}
