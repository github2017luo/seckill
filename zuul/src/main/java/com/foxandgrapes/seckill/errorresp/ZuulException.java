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

        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");

        resultMap.put("ret", false);
        resultMap.put("msg", "ZUUL " + statusCode);
        return resultMap;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
