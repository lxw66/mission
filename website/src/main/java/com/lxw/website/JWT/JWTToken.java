package com.lxw.website.JWT;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author LXW
 * @date 2021年04月26日 10:37
 */
public class JWTToken implements AuthenticationToken {

    private String token;

    public JWTToken(String token){
        this.token=token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
