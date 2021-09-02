package com.lxw.website.controller;

import com.lxw.website.redis.redisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mission
 * @version 1.0
 * @description: TODO
 * @date 2021/8/27 9:58
 */
@RestController
@RequestMapping("/redisController")
@Slf4j
public class RedisController {

    @Autowired
    private redisUtil redisUtil;


    @RequestMapping(value="/redisAdd.json")
    private void redisAdd(){
        for(int i=0;i<=10;i++){
            redisUtil.setKeyValue("lxw"+i,"lxw");
        }
    }

    @RequestMapping(value="/redisdelet.json")
    private void redisdelet(){
        redisUtil.delkeys("lxw","lxw0","lxw1");
    }

    @RequestMapping(value="/redisAddExpire.json")
    private void redisAddExpire(){
        for(int i=0;i<=10;i++){
        redisUtil.expire("lxw"+i,200);
        }
    }

    @RequestMapping(value="/redisGetString.json")
    private Object redisGetString(){
       return redisUtil.getValueByKey("lxw");
    }

    @RequestMapping(value="/redisGetExpire.json")
    private Object redisGetExpire(){
        return redisUtil.getExpire("lxw10");
    }

    @RequestMapping(value="/redisIsExit.json")
    private Object redisIsExit(){
        return redisUtil.isExitKey("lxw10");
    }

    @RequestMapping(value="/redisHasHSet.json")
    private Object redisHasHSet(){
        return redisUtil.isExitKey("lxw10");
    }

    @RequestMapping(value="/hmset.json")
    private Object hmset(){
        Map<String,Object> map=new HashMap<>();
        for(int i=0;i<=10;i++){
            map.put("lxw"+i,"lxw"+i);
        }
        return redisUtil.hmset("0827",map);
    }

    @RequestMapping(value="/getHashAllBykey.json")
    private Object getHashAllBykey(){
        return redisUtil.getHashAllBykey("0827");
    }

    @RequestMapping(value="/getHashValueByKeyItem.json")
    private Object getHashValueByKeyItem(){
        return redisUtil.getHashValueByKeyItem("0827","lxw2");
    }

    @RequestMapping(value="/hmSetOne.json")
    private Object hmSetOne(){
        return redisUtil.hmSetOne("0827","lxw99","lxw99",20);
    }

    @RequestMapping(value="/hmSetOne1.json")
    private Object hmSetOne1(){
        return redisUtil.hmSetOne("0828","lxw99","lxw99",20);
    }

}
