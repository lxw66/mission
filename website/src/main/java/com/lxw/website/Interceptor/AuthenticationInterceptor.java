package com.lxw.website.Interceptor;

import com.lxw.website.annotation.PassToken;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author LXW
 * @date 2021年04月26日 14:17
 */
@Configuration
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
            //请求头取出token
            String jdToken=request.getHeader("JD-Token");
            //不是方法的请求
            if(!(handler instanceof Method)){
                return true;
            }
            //获取方法
            HandlerMethod handlerMethod=(HandlerMethod)handler;
            Method method=handlerMethod.getMethod();
            //是否包含@PassToken  注解
            if(method.isAnnotationPresent(PassToken.class)){
                PassToken passToken=method.getAnnotation(PassToken.class);
               if(passToken.required()){
                   return  true;
               }
            }

            //权限

            return true;
     }


    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }


}
