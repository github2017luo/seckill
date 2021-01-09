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
        switch (statusCode) {
            case 404:
                return RespBean.error("GOODS 404");
            case 403:
                return RespBean.error("GOODS 403");
            case 500:
                return RespBean.error("GOODS 500");
            default:
                return RespBean.error("GOODS 未知错误！");
        }
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
