package com.open;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("网站访问网址：localhost:8888/page/front/index.html");
        System.out.println("管理员账号密码：admin  123456");
    }
}
