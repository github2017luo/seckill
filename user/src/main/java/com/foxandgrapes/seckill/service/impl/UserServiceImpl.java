package com.foxandgrapes.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.foxandgrapes.seckill.mapper.UserMapper;
import com.foxandgrapes.seckill.pojo.User;
import com.foxandgrapes.seckill.service.IUserService;
import com.foxandgrapes.seckill.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tsk
 * @since 2021-01-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 登录
     * @param userId
     * @param password
     * @return
     */
    @Override
    public Map<String, Object> login(Long userId, String password) {
        Map<String, Object> resultMap = new HashMap<>();

        if (!isMobileNO(userId)) {
            resultMap.put("result", false);
            resultMap.put("msg", "手机号不正确！");
            return resultMap;
        }

        User user = userMapper.selectById(userId);
        if (user == null) {
            resultMap.put("result", false);
            resultMap.put("msg", "用户不存在！");
            return resultMap;
        }

        if (!user.getPassword().equals(MD5Util.fromPassToDBPass(password, user.getSalt()))) {
            resultMap.put("result", false);
            resultMap.put("msg", "密码错误！");
            return resultMap;
        }

        resultMap.put("result", true);
        resultMap.put("msg", "用户登录成功！");
        return resultMap;
    }

    /**
     * 注册
     * @param userId
     * @param password
     * @return
     */
    @Override
    public Map<String, Object> register(Long userId, String password) {
        Map<String, Object> resultMap = new HashMap<>();

        if (!isMobileNO(userId)) {
            resultMap.put("result", false);
            resultMap.put("msg", "手机号不正确！");
            return resultMap;
        }

        User user = userMapper.selectById(userId);
        if (user != null) {
            resultMap.put("result", false);
            resultMap.put("msg", "用户已存在！注册失败！");
            return resultMap;
        }

        String randomSalt = MD5Util.getRandomSalt();
        user = new User(userId, MD5Util.fromPassToDBPass(password, randomSalt), randomSalt);

        int res = userMapper.insert(user);
        if (res == 0) {
            resultMap.put("result", false);
            resultMap.put("msg", "注册失败！");
        }

        resultMap.put("result", true);
        resultMap.put("msg", "用户注册成功");
        resultMap.put("user", user);
        return resultMap;
    }

    /**
     * 检查是否为正确的手机号
     * @param mobiles
     * @return
     */
    private boolean isMobileNO(Long mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher("" + mobiles);
        return m.matches();
    }
}
