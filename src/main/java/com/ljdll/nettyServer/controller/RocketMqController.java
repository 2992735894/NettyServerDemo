package com.ljdll.nettyServer.controller;

import com.ljdll.nettyServer.param.RocketMQMessageParam;
import com.ljdll.nettyServer.producer.ProducerTest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rocketMq")
public class RocketMqController {
    private final ProducerTest producer;

    @PostMapping("/sendMessage")
    public void sendMessage(@RequestBody RocketMQMessageParam param) {
        producer.sendMessage(param.getTopic(), param.getMessage());
    }
}
