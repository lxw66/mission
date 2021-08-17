package com.lxw.website.Exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LXW
 * @date 2021年04月26日 13:21
 */
@ControllerAdvice
@Slf4j
public class MyExceptionControllerAdvice {

    @ExceptionHandler(value=MyException.class)
    @ResponseBody
    public Map  expectionHandler(MyException exception){
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("code",exception.getCode());
        map.put("message",exception.getMessage());
        map.put("info",exception.getDecinfo());
        return map;
    }
}
