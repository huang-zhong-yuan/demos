package com.example.mp;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan(basePackages = "com.example.mp.infra.mapper", markerInterface = BaseMapper.class)
public class MpApplications {
    public static void main(String[] args) {
        SpringApplication.run(MpApplications.class, args);
    }
}
