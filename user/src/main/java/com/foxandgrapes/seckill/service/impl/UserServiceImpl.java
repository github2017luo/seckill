package com.foxandgrapes.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.foxandgrapes.seckill.mapper.UserMapper;
import com.foxandgrapes.seckill.pojo.User;
import com.foxandgrapes.seckill.service.IUserService;
import com.foxandgrapes.seckill.utils.MD5Util;
import com.foxandgrapes.seckill.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public RespBean login(Long userId, String password) {

        if (!isMobileNO(userId)) {
            return RespBean.error("手机号不正确！");
        }

        User user = userMapper.selectById(userId);
        if (user == null) {
            return RespBean.error("用户不存在！");
        }

        if (!user.getPassword().equals(MD5Util.fromPassToDBPass(password, user.getSalt()))) {
            return RespBean.error("密码错误！");
        }

        return RespBean.success("用户登录成功！", null);
    }

    /**
     * 注册
     * @param userId
     * @param password
     * @return
     */
    @Override
    public RespBean register(Long userId, String password) {

        if (!isMobileNO(userId)) {
            return RespBean.error("手机号不正确！");
        }

        User user = userMapper.selectById(userId);
        if (user != null) {
            return RespBean.error("用户已存在！注册失败！");
        }

        String randomSalt = MD5Util.getRandomSalt();
        user = new User(userId, MD5Util.fromPassToDBPass(password, randomSalt), randomSalt);

        int res = userMapper.insert(user);
        if (res != 1) {
            return RespBean.error("注册失败！");
        }

        return RespBean.success("用户注册成功", null);
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
