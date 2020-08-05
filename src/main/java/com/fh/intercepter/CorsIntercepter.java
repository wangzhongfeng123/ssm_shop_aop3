package com.fh.intercepter;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CorsIntercepter extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String origin = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", origin);

        response.setHeader("Access-Control-Allow-Methods","POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers","x-requested-with");
        response.setHeader("Access-Control-Allow-Credentials","true");

        String method = request.getMethod();

        if (method.equalsIgnoreCase("options")){
            response.setHeader("Access-Control-Allow-Headers","token");
            return false;
        }

        request.getHeader("login_token");

        return true;
    }
}
