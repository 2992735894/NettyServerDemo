package com.ljdll.nettyServer.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProducerTest {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public void sendMessage(String topic, String message) {
        log.info("发送消息：{}", message);
        rocketMQTemplate.convertAndSend(topic, message);
    }

    public void sendAsyncMessage(String topic, String message) {
        rocketMQTemplate.asyncSend(topic, message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("发送消息成功：{}", message);
            }

            @Override
            public void onException(Throwable throwable) {
                log.error("发送消息失败：{}", message);
            }
        });
    }

    public void sendOneWayMessage(String topic, String message) {
        rocketMQTemplate.sendOneWay(topic, message);
    }
}
