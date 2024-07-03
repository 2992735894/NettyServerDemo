package com.ljdll.nettyServer.config.rocketMq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RocketMqConfig {
    @Value("${rocketmq.producer.group}")
    private String producerGroup;

    @Value("${rocketmq.name-server}")
    private String nameServer;

    @Bean("RocketMQTemplate")
    public RocketMQTemplate rocketMqTemplate() {
        RocketMQTemplate rocketMqTemplate = new RocketMQTemplate();
        DefaultMQProducer producer = new DefaultMQProducer();
        producer.setProducerGroup(producerGroup);
        producer.setNamesrvAddr(nameServer);
        rocketMqTemplate.setProducer(producer);
        return rocketMqTemplate;
    }
}
