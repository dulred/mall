package com.example.ggb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.ggb.repository")
public class GgbApplication {

    public static void main(String[] args) {
        SpringApplication.run(GgbApplication.class, args);
    }

}
