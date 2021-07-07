package com.lxw.website.aop;

import com.lxw.website.jdbc.DBContextHolder;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class DataSourceAop {


    @Pointcut("@annotation(com.lxw.website.annotation.Read)"+   //注解Read
        "|| execution(* com.lxw.website.dao.*.select*(..))"+   //拦截
        "|| execution(* com.lxw.website.dao.*.get*(..))")
    public void readPointCut(){

    }

    @Pointcut("@annotation(com.lxw.website.annotation.Write)"+   //注解Write
            "|| execution(* com.lxw.website.dao.*.update*(..))"+   //拦截
            "|| execution(* com.lxw.website.dao.*.insert*(..))"+
            "|| execution(* com.lxw.website.dao.*.add*(..))"+
            "|| execution(* com.lxw.website.dao.*.edit*(..))"+
            "|| execution(* com.lxw.website.dao.*.delete*(..))"+
            "|| execution(* com.lxw.website.dao.*.remove*(..))")
    public void writePointCut(){
    }

    @Before("readPointCut()")
    public void read() {

        DBContextHolder.read();
    }

    @Before("writePointCut()")
    public void write() {

        DBContextHolder.write();
    }

}
