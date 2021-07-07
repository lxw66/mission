package com.lxw.website.JWT;

import com.lxw.website.Exception.MyException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**  目前没什么用,待后面代码更新使用
 * @author LXW
 * @date 2021年04月26日 13:02
 */
public class JWTFilter extends BasicHttpAuthenticationFilter {
    //登录标识
    private static String LOGIN_SIGN="JD-Token";

    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req=(HttpServletRequest) request;
        String jdToken=req.getHeader(LOGIN_SIGN);
        return jdToken!=null;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req=(HttpServletRequest) request;
        String jdToken=req.getHeader(LOGIN_SIGN);
        JWTToken token=new JWTToken(jdToken);
        getSubject(request,response).login(token);
        return  true;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {
            try {
                executeLogin(request, response);
            } catch (Exception e) {
                throw new MyException(-1,e.toString(),"无登录权限！");
            }
        }

        return true;
    }
        /**
         * 处理跨域
         * @author LXW
         * @date 2021/4/26 13:32
         * @param request
         * @param response
         * @return boolean
         */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }


}
