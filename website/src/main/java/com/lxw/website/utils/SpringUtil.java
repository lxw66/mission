package com.lxw.website.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author LXW
 * @date 2021年06月22日 15:39
 */
@Component
@Slf4j
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringUtil.applicationContext==null){
            SpringUtil.applicationContext=applicationContext;
        }
        log.info("---------------------------------------------------------------------");
        log.info("---------------------------------------------------------------------");
        log.info("========ApplicationContext配置成功,在普通类可以通过调用SpringUtils.getAppContext()获取applicationContext对象,applicationContext="+SpringUtil.applicationContext+"========");
        log.info("---------------------------------------------------------------------");
    }

    /**
     * 获取ApplicationContext
     * @author LXW
     * @date 2021/6/22 15:47
     * @return org.springframework.context.ApplicationContext
     */
    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }
    /**
     *
     * @author LXW
     * @date 2021/6/22 15:46
     * @param Beanname  bean名字
     * @return java.lang.Object
     */
    public static  Object getBean(String Beanname){
        return  getApplicationContext().getBean(Beanname);
    }
    /**
     * 根据class 获取bean
     * @author LXW
     * @date 2021/6/22 15:50
     * @param clazz 
     * @return T
     */

    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

   /**
    *  //通过name,以及Clazz返回指定的Bean
    * @author LXW
    * @date 2021/6/22 15:50
    * @param name
    * @param clazz
    * @return T
    */
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }
}
