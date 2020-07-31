package com.example.springcache.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class RedissonConfig {

    @Autowired
    private RedisProperties redisProperties;

    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    //除了锁以外，其他关于redis的cache尽量使用lettuce， 见：RedisConfig
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() {
        Config config = new Config();
        String address = "redis://" + redisProperties.getHost() + ":" + redisProperties.getPort();
        config.useSingleServer().setDnsMonitoringInterval(60000).setAddress(address).setTimeout((int)redisProperties.getTimeout().toMillis())
                .setConnectTimeout((int)redisProperties.getLettuce().getPool().getMaxWait().toMillis()).setDnsMonitoringInterval(1000)
//                .setSubscriptionConnectionPoolSize(redisProperties.getLettuce().getPool().getMaxActive())
//                .setSubscriptionConnectionMinimumIdleSize(redisProperties.getLettuce().getPool().getMinIdle())
                .setConnectionPoolSize(redisProperties.getLettuce().getPool().getMaxActive())
                .setConnectionMinimumIdleSize(redisProperties.getLettuce().getPool().getMinIdle());
        ObjectMapper mapper = getObjectMapper();
        config.setCodec(new JsonJacksonCodec(mapper));
        return Redisson.create(config);
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        mapper.registerModule(javaTimeModule);
        return mapper;
    }
}
