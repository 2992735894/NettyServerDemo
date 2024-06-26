package com.ljdll.nettyServer.controller;

import com.ljdll.nettyServer.param.RocketMqSendMessageParam;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientConfigurationBuilder;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.message.Message;
import org.apache.rocketmq.client.apis.producer.Producer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rocketMq")
@RequiredArgsConstructor
public class RocketMqController {
    private static final String url = "101.37.161.202:8081";

    @PostMapping("/sendMessage")
    @SneakyThrows
    public void sendMessage(@RequestBody RocketMqSendMessageParam param) {
//        byte[] body = null;
//        if (!param.getMessageObject().isEmpty()) {
//            body = param.getMessageObject().toJSONString().getBytes();
//        } else if (!param.getMessageArray().isEmpty()) {
//            body = param.getMessageArray().toJSONString().getBytes();
//        }
//
//        ClientServiceProvider provider = ClientServiceProvider.loadService();
//        ClientConfigurationBuilder builder = ClientConfiguration.newBuilder().setEndpoints(url);
//        ClientConfiguration configuration = builder.build();
//        Producer producer = provider.newProducerBuilder()
//                .setTopics(param.getTopic())
//                .setClientConfiguration(configuration)
//                .build();
//        Message message = provider.newMessageBuilder()
//                .setTopic(param.getTopic())
//                .setKeys(param.getMessageKey())
//                .setTag(param.getMessageTag())
//                .setBody(body)
//                .build();
//        producer.send(message);

        System.out.println("21");
    }
}
