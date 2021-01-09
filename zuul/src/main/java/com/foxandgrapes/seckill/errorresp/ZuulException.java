package com.foxandgrapes.seckill.errorresp;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ZuulException implements ErrorController {

    @RequestMapping("/error")
    public Map<String, Object> handleError(HttpServletRequest request) {

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("ret", false);
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        switch (statusCode) {
            case 404:
                resultMap.put("msg", "ZUUL 404");
                return resultMap;
            case 403:
                resultMap.put("msg", "ZUUL 403");
                return resultMap;
            case 500:
                resultMap.put("msg", "ZUUL 500");
                return resultMap;
            default:
                resultMap.put("msg", "ZUUL 未知错误！");
                return resultMap;
        }
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
