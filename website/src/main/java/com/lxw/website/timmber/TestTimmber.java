package com.lxw.website.timmber;

import lombok.extern.slf4j.Slf4j;
import sun.net.www.http.HttpClient;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author LXW
 * @date 2021年04月23日 16:14
 */
@Slf4j
public class TestTimmber {
    public void test(){
        Timer time=new Timer();

        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {

            }
        };
        time.schedule(timerTask,10,3);
    }

    public static void main(String[] args) {
        TestTimmber timmber=new TestTimmber();
        timmber.test();
    }

}
