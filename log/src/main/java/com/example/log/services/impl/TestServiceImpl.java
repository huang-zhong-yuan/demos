package com.example.log.services.impl;

import com.example.log.services.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestServiceImpl implements TestService {
    @Override
    public String getWord(String name) {
        log.info("TestServiceImpl.getword, " + name);
        return "hello" + name;
    }
}
