package com.itheima;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
@MapperScan("com.itheima.mapper")
public class SpringbootMybatisCrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMybatisCrudApplication.class, args);
    }

}

