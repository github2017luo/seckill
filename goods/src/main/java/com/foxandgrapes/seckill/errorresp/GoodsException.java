package com.foxandgrapes.seckill.errorresp;

import com.foxandgrapes.seckill.vo.RespBean;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class GoodsException implements ErrorController {

    @RequestMapping("/error")
    public RespBean handleError(HttpServletRequest request) {

        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        return  RespBean.error("GOODS " + statusCode);
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
