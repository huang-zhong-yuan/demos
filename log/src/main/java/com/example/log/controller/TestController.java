package com.example.log.controller;

import com.example.log.services.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/public/log")
@Slf4j
public class TestController {
    @Autowired
    private TestService testService;

    @GetMapping("/test")
    public String test(@RequestParam("name") String name) {
        log.info("testing in controller");
        String rst = testService.getWord(name);
        log.info("result return from testService: " + rst);
        return rst;
    }

}
