package com.foxandgrapes.seckill.controller;


import com.foxandgrapes.seckill.pojo.User;
import com.foxandgrapes.seckill.service.IUserService;
import com.foxandgrapes.seckill.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tsk
 * @since 2021-01-06
 */
@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public RespBean login(@RequestBody User user, HttpServletRequest request) {
        RespBean respBean = userService.login(user.getId(), user.getPassword());
        // 登录错误时处理
        if (!respBean.getRet()) {
            // 手机号不正确直接返回
            if ("手机号不正确！".equals(respBean.getMsg())) {
                return respBean;
            }
            // 没注册则直接注册
            if ("用户不存在！".equals(respBean.getMsg())) {
                respBean = userService.register(user.getId(), user.getPassword());
            }
        }
        if (respBean.getRet()) {
            HttpSession session = request.getSession();
            // 将登录信息保存到redis
            session.setAttribute("user", true);
        }
        return respBean;
    }
}