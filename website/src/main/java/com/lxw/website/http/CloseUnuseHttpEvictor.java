package com.lxw.website.http;

import org.apache.http.conn.HttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author LXW
 * @date 2021年04月25日 14:02
 */
//TODO   该线程处理连接有点问题  待研究
public class CloseUnuseHttpEvictor  extends Thread {

    @Autowired
    private HttpClientConnectionManager httpClientConnectionManager;

    private volatile  boolean shutdown;

   public CloseUnuseHttpEvictor(){
       super();
       super.start();
   }

    @Override
    public void run() {
        try {
            while (!shutdown) {
                synchronized (this) {
                    wait(5000);
                    // 关闭失效的连接
                    httpClientConnectionManager.closeExpiredConnections();
                }
            }
        } catch (InterruptedException ex) {
            // 结束
        }
    }

    public void shutdown() {
        shutdown = true;
        synchronized (this) {
            notifyAll();
        }
    }


}
