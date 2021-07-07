package com.lxw.website.utils;

import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author LXW
 * @date 2021年06月17日 13:13
 */
@Service
public  class Md5Utils {

    private AtomicInteger cnt = new AtomicInteger(1);

    public String randUid() {
        return cnt.getAndAdd(1) + "_" + UUID.randomUUID().toString();
    }

    public static String getMd5(String str){
        return str+UUID.randomUUID().toString();
    }
}
