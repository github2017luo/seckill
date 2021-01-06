package com.foxandgrapes.seckill.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tsk
 * @since 2021-01-06
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }
}
