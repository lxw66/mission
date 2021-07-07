package com.lxw.website.JWT;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author LXW
 * @date 2021年04月26日 10:47
 */
public class JWTUtil {

    //过期时间为一天
    private static final long exportTime=60*24*1*1000;


    /**
     * 校验token  是否正确
     * @author LXW
     * @date 2021/4/26 10:57
     * @param token
     * @param userName
     * @param passWord 
     * @return boolean
     */
    public static  boolean  verify(String token,String userName,String passWord){
        try {
            Algorithm algorithm=Algorithm.HMAC256(passWord);
            JWTVerifier verifier=JWT.require(algorithm).withClaim("userName", userName).build();
            DecodedJWT decodedJWT=verifier.verify(token);
            return  true;
        }catch (Exception e){
            return  false;
        }
    }

    /**
     *token 获取用户名
     * @author LXW
     * @date 2021/4/26 11:01
     * @param token
     * @return java.lang.String
     */
    public static String  getUserNameByToken(String token){
        try {
            DecodedJWT decodedJWT=JWT.decode(token);
            return decodedJWT.getClaim("userName").asString();
        }catch (Exception e){
            return  null;
        }
    }

    /**
     * 获取token
     * @author LXW
     * @date 2021/4/26 11:17
     * @param userName
     * @param passWord 
     * @return java.lang.String
     */
    public static String getToken(String userName,String passWord)  {
        try {
            Date date = new Date(System.currentTimeMillis() + exportTime);
            Algorithm algorithm = Algorithm.HMAC256(passWord);
            return JWT.create()
                    .withClaim("userName",userName)
                    .withExpiresAt(date)
                    .sign(algorithm);
        }catch (UnsupportedEncodingException e){
            return null;
        }
    }
}
