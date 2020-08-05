package com.fh.intercepter.config;

import com.fh.intercepter.CorsIntercepter;
import com.fh.intercepter.LoginIntercepter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration //声明是配置文件类
public class IntercepterConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

         //注册TestInterceptor拦截器
        InterceptorRegistration registration = registry.addInterceptor(new CorsIntercepter());
        registration.addPathPatterns("/**");//所有路径都被拦截

        //注册TestInterceptor拦截器
        InterceptorRegistration registration1 = registry.addInterceptor(new LoginIntercepter());
        registration1.addPathPatterns("/car/**");//所有路径都被拦截cons
        registration1.addPathPatterns("/cons/**");//所有路径都被拦截cons

    }


    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(true)	//设置是否是后缀模式匹配,即:/test.*
                .setUseTrailingSlashMatch(true);	//设置是否自动后缀路径模式匹配,即：/test/
    }
}
