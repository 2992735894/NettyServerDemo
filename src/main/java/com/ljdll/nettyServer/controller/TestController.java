package com.ljdll.nettyServer.controller;

import com.ljdll.nettyServer.common.constant.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/mappingTest")
    public R<String> mappingTest() {
        return R.ok("mappingTest");
    }
}
