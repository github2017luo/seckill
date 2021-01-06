package com.foxandgrapes.seckill.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("time-server")
public interface ITimeService {

    @RequestMapping("/getTime")
    public String getTime();
}
