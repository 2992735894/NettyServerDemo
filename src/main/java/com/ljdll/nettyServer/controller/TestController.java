package com.ljdll.nettyServer.controller;

import com.ljdll.nettyServer.common.constant.R;
import com.ljdll.nettyServer.param.FormTestParam;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/mappingTest")
    public R<String> mappingTest() {
        return R.ok("mappingTest");
    }

    @GetMapping("/formTest")
    public R<String> formTest(String message, FormTestParam param) {
        return R.ok(param.getMessage());
    }

    @GetMapping("/pathTest/{message}")
    public R<String> pathTest(@PathVariable("message") String message) {
        return R.ok(message);
    }

    @PostMapping("/emptyBodyTest")
    public R<String> emptyBodyTest() {
        return R.ok("emptyBodyTest");
    }

    @PostMapping("/postFormTest")
    public R<String> postFormTest(String message) {
        return R.ok(message);
    }

    @PostMapping("/bodyTest")
    public R<String> bodyTest(@RequestBody FormTestParam param) {
        return R.ok(param.getMessage());
    }
}
