package com.lxw.website.redis;

import com.sun.istack.internal.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author mission
 * @version 1.0
 * @description: TODO
 * @date 2021/8/25 15:57
 */
@Data
@Setter
@Getter
@Slf4j
public class redisUtil {
    private RedisTemplate<String, Object>  redisTemplate;

    public redisUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public redisUtil() {
    }

    /**
     *指定关键key缓存失效时间
     * @param key
     * @param time 失效的时间
     * @return
     */
    public boolean expire(@NotNull String key,@NotNull long time){
       try {
           if(time>0){
               return redisTemplate.expire(key,time,TimeUnit.SECONDS);
           }else{
               log.info("设置失效时间必须大于0---");
               return false;
           }
       }catch (Exception e){
            log.info("设置失效时间失败！---"+e.getMessage());
           return  false;
       }
    }

    /**
     * 根据key 获取过期时间 key 键 不能为null
     * @param key
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(@NotNull String key){
        return redisTemplate.getExpire(key,TimeUnit.SECONDS);
    }

    /**
     *根据KEy  查询是否存在
     * @param key
     * @return
     */
    public boolean isExitKey(@NotNull String key){
       try{
           return redisTemplate.hasKey(key);
       }catch (Exception e){
           log.info("isExitKey失败！---"+e.getMessage());
           return false;
       }
    }

    /**
     * 删除key
     * @param key
     */
    public void delkeys(String ... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            }
            if (key.length > 1) {
                redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 通过key  获取值
      * @param key
     * @return
     */
    public Object getValueByKey(String key){
        return key==null?null:redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通set  key  value
     * @param key
     * @param value
     * @return
     */
    public  boolean setKeyValue(String key,Object value){
        try {
            redisTemplate.opsForValue().set(key,value);
            return  true;
        }catch (Exception e){
            log.info("setKeyValue-false!---"+e.getMessage());
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     * @param key
     * @param value
     * @param time 时间  单位  S
     * @return
     */
    public boolean setkeyValueTime(String key,Object value,long time){
        try {
            redisTemplate.opsForValue().set(key,value,time,TimeUnit.SECONDS);
            return  true;
        }catch (Exception e){
            log.info("setkeyValueTime-false!---"+e.getMessage());
            return false;
        }
    }

    /**
     * 递增
     * @param key
     * @param delta
     * @return
     */
    public long increment(String key,long delta){
        if(delta<0){
            throw new RuntimeException("递增因子必须大于0");
        }
        return  redisTemplate.opsForValue().increment(key,delta);
    }

    /**
     * 递减
     * @param key
     * @param delta
     * @return
     */
    public  long decrement(String key,long delta){
        if(delta<0){
            throw new RuntimeException("递减因子必须大于0");
        }
        return  redisTemplate.opsForValue().decrement(key,delta);
    }

//========================================Map============================================================================================

    /**
     * Hash  get
     * @param key
     * @param item
     * @return
     */
    public Object getHashValueByKeyItem(@NotNull String key, @NotNull String item){
        return  redisTemplate.opsForHash().get(key,item);
    }

    /**
     * 获取完成Hash所有key-value
     * @param key
     * @return
     */
    public Map<Object,Object> getHashAllBykey(@NotNull String key){
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet   map
     * @param key
     * @param map
     * @return
     */
    public boolean hmset(String key,Map<String,Object> map){
       try {
           redisTemplate.opsForHash().putAll(key,map);
           return  true;
       }catch (Exception e){
           log.info("hmset-false!---"+e.getMessage());
           return  false;
       }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key  key
     * @param oneKey  单个key
     * @param oneValue 单个value
     * @return
     */
    public boolean hmSetOne(String key,Object oneKey,Object oneValue){
        try {
            redisTemplate.opsForHash().put(key, oneKey, oneValue);
            return  true;
        }catch (Exception e){
            log.info("hmset-false!---"+e.getMessage());
            return  false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key
     * @param oneKey
     * @param oneValue
     * @param time
     * @return
     */
    public boolean hmSetOne(String key,Object oneKey,Object oneValue,long time){
        try {
            redisTemplate.opsForHash().put(key, oneKey, oneValue);
            redisTemplate.expire(key,time,TimeUnit.SECONDS);
            return  true;
        }catch (Exception e){
            log.info("hmSetOne-false!---"+e.getMessage());
            return  false;
        }
    }

    /**
     * hash set map  失效时间
     * @param key
     * @param map
     * @param time
     * @return
     */
    public boolean hmset(String key,Map<String,Object> map,long time){
        if(time>0){
            redisTemplate.opsForHash().putAll(key,map);
            redisTemplate.expire(key,time,TimeUnit.SECONDS);
            return true;
        }else{
            throw  new RuntimeException("失效时间必须大于0!");
        }
    }

    /**
     * hash  删除某个值
     * @param key
     * @param item
     * @return
     */
    public boolean hmDel(String key,Object ...item){
       try {
           redisTemplate.opsForHash().delete(key,item);
           return  true;
       }catch (Exception e){
           log.info("hmDel-false!---"+e.getMessage());
           return  false;
       }
    }

    /**
     * 判断hash表中是否有该项的值
     * @param key
     * @param item
     * @return
     */
    public boolean hmIsExit(String key,String item){
        return redisTemplate.opsForHash().hasKey(key,item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     * @param key
     * @param item
     * @param delay 要增加几(大于0)
     * @return
     */
    public long hashinrc(String key,String item,long delay){
        if(delay>0){
            return redisTemplate.opsForHash().increment(key,item,delay);
        }else {
            throw new RuntimeException("delay必须大于0！");
        }
    }

    /**
     * hash递减 如果不存在,就会创建一个 并把新增后的值返回
     * @param key
     * @param item
     * @param delay 要增加几(大于0)
     * @return
     */
    public long hashdecr(String key,String item,long delay){
        if(delay>0){
            return redisTemplate.opsForHash().increment(key,item,-delay);
        }else {
            throw new RuntimeException("delay必须大于0！");
        }
    }
//=============================================set==========================================================

    /**
     *  根据key获取Set中的所有值
     * @param key
     * @return
     */
    public Set<Object> setGet(String key){
        return  redisTemplate.opsForSet().members(key);
    }

    /**
     * 根据value从一个set中查询,是否存在
     * @param key
     * @param value
     * @return
     */
    public boolean setHaveByKey(String key,Object value){
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     *  将数据放入set缓存
     * @param key
     * @param value
     * @return  返回添加的个数
     */
    public long setAdd(String key,Object ... value){
        return redisTemplate.opsForSet().add(key,value);
    }


    /**
     * 将set数据放入缓存
     * @param key
     * @param time time 时间(秒)   必须大于0
     * @param value
     * @return 返回添加的个数
     */
    public long setAdd(String key,long time,Object ... value){
        try{
            Long count=redisTemplate.opsForSet().add(key,value);
            if(time>0)
                redisTemplate.expire(key,time,TimeUnit.SECONDS);
            return count;
        }catch (Exception e){
            log.info("hmDel-false!---"+e.getMessage());
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     * @param key
     * @return
     */
    public long setSize(String key){
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 移除值为value的
     * @param key
     * @param value
     * @return 移除的个数
     */
    public long setRemove(String key,Object ...value){
        return  redisTemplate.opsForSet().remove(key, value);
    }
 //=============================================List==========================================

    /**
     * 获取list缓存的内容
     * @param key
     * @param start
     * @param end end 结束  **  0 到 -1代表所有值
     * @return
     */
    public List<Object>  listGet(String key,long start,long end){
        try{
            return  redisTemplate.opsForList().range(key, start, end);
        }catch (Exception e){
            log.info("listGet-false!---"+e.getMessage());
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     * @param key
     * @return
     */
    public long listSizeGet(String key){
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 通过索引 获取list中的值
     * @param key
     * @param index  index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object listIndexGet(String key,long index){
        return redisTemplate.opsForList().index(key,index);
    }

    /**
     * 将list放入缓存
     * @param key
     * @param value
     * @return
     */
    public long listSet(String key,Object value){
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 将list放入缓存  失效时间
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean listSetExpire(String key,Object value,Long time){
        try {
            redisTemplate.opsForList().rightPush(key,value);
            redisTemplate.expire(key,time,TimeUnit.SECONDS);
            return true;
        }catch (Exception e){
            log.info("listSetExpire-false!---"+e.getMessage());
            return false;
        }
    }

    /**
     * 将list放入缓存
     * @param key
     * @param value  list<Object>
     * @return
     */
    public long listSet(String key,List<Object> value){
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 将list放入缓存  失效时间
     * @param key
     * @param value  list<Object>
     * @param time
     * @return
     */
    public boolean listSetExpire(String key,List<Object> value,Long time){
        try {
            redisTemplate.opsForList().rightPush(key,value);
            redisTemplate.expire(key,time,TimeUnit.SECONDS);
            return true;
        }catch (Exception e){
            log.info("listSetExpire-false!---"+e.getMessage());
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     * @param key
     * @param index
     * @param value
     * @return
     */
    public Boolean listIndexUpdate(String key,Long index,Object value){
          try{
              redisTemplate.opsForList().set(key,index,value);
              return true;
          }catch (Exception e){
              log.info("listIndexUpdate-false!---"+e.getMessage());
              return false;
          }
    }

    /**
     * 移除N个值为value
     * @param key
     * @param value
     * @param cout
     * @return
     */
    public long listRemoveValue(String key,Object value,Long cout){
        return  redisTemplate.opsForList().remove(key,cout,value);
    }
}
