package com.fh.intercepter;

import com.fh.intercepter.exception.NologinException;
import com.fh.model.po.Vip;
import com.fh.util.JWT;
import com.fh.util.RedisUse;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

public class LoginIntercepter extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)){
            throw new NologinException("头信息不完整");
        }
        byte[] decode = Base64.getDecoder().decode(token);

        String s = new String(decode);

        String[] split = s.split(",");

        if (split.length!=2){
            throw  new NologinException("信息格式不对");
        }

        String iphone = split[0];

        String sign = split[1];

        Vip unsign = JWT.unsign(sign, Vip.class);
        if (StringUtils.isEmpty(unsign)){
            throw new NologinException("用户没有登录");
        }else if (StringUtils.isEmpty(unsign)!=true){
            String s1 = RedisUse.get("login_" + iphone);
            if (!sign.equals(s1)){
                throw new NologinException("用户没有登录");
            }
        }

        RedisUse.set("login_" + iphone,sign, 60*30);

        request.setAttribute("login_user",unsign);

        return true;
    }
}
