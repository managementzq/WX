package com.nbkj.wchatdemo.service;

import org.apache.http.conn.HttpClientConnectionManager;

/**
 * 链接池无效链接清理线程
 */
public class IdleConnectionEvictor extends Thread {
    // 链接管理器
    private final HttpClientConnectionManager connMgr;
    // shutdown标记
    private volatile boolean shutdown;

    public IdleConnectionEvictor(HttpClientConnectionManager connMgr) {
        this.connMgr = connMgr;
        // 启动当前线程
        this.start();
    }

    @Override
    public void run() {
        try {
            while (!shutdown) {
                synchronized (this) {
                    wait(5000);
                    // 关闭失效的连接
                    connMgr.closeExpiredConnections();
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
