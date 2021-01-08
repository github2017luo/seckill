package com.foxandgrapes.seckill.controller;


import com.foxandgrapes.seckill.pojo.User;
import com.foxandgrapes.seckill.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tsk
 * @since 2021-01-06
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User user, HttpServletRequest request) {
        Map<String, Object> resultMap = userService.login(user.getId(), user.getPassword());
        // 登录错误时处理
        if (!(boolean) resultMap.get("result")) {
            // 手机号不正确直接返回
            if ("手机号不正确！".equals(resultMap.get("msg").toString())) {
                return resultMap;
            }
            // 没注册则直接注册
            if ("用户不存在！".equals(resultMap.get("msg").toString())) {
                resultMap = userService.register(user.getId(), user.getPassword());
            }
        }
        if ((boolean) resultMap.get("result")) {
            HttpSession session = request.getSession();
            // 将登录的用户信息保存到redis
            session.setAttribute("user_" + user.getId(), resultMap.get("user"));
        }
        return resultMap;
    }
}