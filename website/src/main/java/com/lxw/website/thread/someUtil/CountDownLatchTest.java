package com.lxw.website.thread.someUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author mission
 * @version 1.0
 * @description: TODOCountDownLatch是JUC中常见的一种工具类，从类名中我们也能略窥一二这个类的含义，
 * CountDown字面意思为倒数，latch有门闩（shuan）的意思，主要是用来控制并发流程
 * 场景1:控制子流程结束：一个线程等待多个线程执行完毕
 * 场景2：控制子流程开始：多个线程等待一个线程执行完毕
 * @date 2021/7/13 15:00
 */
public class CountDownLatchTest  {


    //控制子流程结束：一个线程等待多个线程执行完毕
    /*
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(5);
        ExecutorService executorService=Executors.newFixedThreadPool(5);
        for(int i=1;i<=5;i++){
              int num=i;
              Runnable runnable=()->{
                  try {
                      //模拟测试耗时
                      Thread.sleep((long) (Math.random()*5000));
                      System.out.println("模块："+ num +"测试完毕");
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }finally {
                      //测试完毕后执行减一操作
                      latch.countDown();
                  }
              };
            executorService.submit(runnable);
        }
        System.out.println("等待模块检测...");
        //主线程等待，直到count为0
        latch.await();
        System.out.println("所有模块测试完毕,开始上线");
    }
     */

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch begin = new CountDownLatch(1);

        ExecutorService service = Executors.newFixedThreadPool(5);
        //创建5个线程
        for (int i = 1; i <= 5; i++) {
            final int num = i;
            Runnable runnable = () -> {
                try {
                    System.out.println("运动员："+ num +"等待起跑指令");
                    //所有运动员等待指令
                    begin.await();
                    System.out.println("运动员："+ num +"开始跑步");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            service.submit(runnable);
        }
       // Thread.sleep(1000);
        //裁判员发出起步指令
        begin.countDown();
        System.out.println("发令完毕");
    }

}
