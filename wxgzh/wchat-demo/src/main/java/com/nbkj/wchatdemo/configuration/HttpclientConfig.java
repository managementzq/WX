package com.nbkj.wchatdemo.configuration;

import com.nbkj.wchatdemo.service.IdleConnectionEvictor;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author :hsj
 * @description
 * @date :2018/4/19
 */
@Configuration
public class HttpclientConfig {
    @Bean
    public PoolingHttpClientConnectionManager connectionManager() {
        // 创建连接管理器
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        // 设置最大连接数
        connectionManager.setMaxTotal(10);
        return connectionManager;
    }

    @SuppressWarnings("deprecation")
    @Bean
    public RequestConfig requestConfig() {
        // 创建RequestConfig.Builder
        RequestConfig.Builder builder = RequestConfig.custom();
        // 创建连接的最长时间
        builder.setConnectTimeout(2500);
        // 从连接池中获取到连接的最长时间
        builder.setConnectionRequestTimeout(2000);
        // 数据传输的最长时间
        builder.setSocketTimeout(100000);
        // 创建RequestConfig
        return builder.build();
    }

    @Bean(destroyMethod = "shutdown")
    public IdleConnectionEvictor idleConnectionEvictor() {
        // 创建连接池无效连接清理线程
        IdleConnectionEvictor evictor = new IdleConnectionEvictor(this.connectionManager());

        return evictor;
    }
}
