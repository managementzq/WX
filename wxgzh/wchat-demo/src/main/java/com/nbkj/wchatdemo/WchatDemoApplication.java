package com.nbkj.wchatdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class WchatDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WchatDemoApplication.class, args);
    }
}
