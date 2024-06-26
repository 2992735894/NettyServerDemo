package com.ljdll.nettyServer.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.consumer.ConsumeResult;
import org.apache.rocketmq.client.apis.consumer.FilterExpression;
import org.apache.rocketmq.client.apis.consumer.MessageListener;
import org.apache.rocketmq.client.apis.consumer.PushConsumer;
import org.apache.rocketmq.client.apis.message.MessageView;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
public class TestTopic {
    public static void main(String[] args) {
        ClientServiceProvider provider = ClientServiceProvider.loadService();
        String topic = "testTopic";
        FilterExpression filterExpression = new FilterExpression();
        try {
            PushConsumer pushConsumer = provider.newPushConsumerBuilder()
                    .setConsumerGroup("testGroup")
                    .setClientConfiguration(ClientConfiguration.newBuilder().setEndpoints("101.37.161.202:8081").build())
                    .setSubscriptionExpressions(Collections.singletonMap(topic, filterExpression))
                    .setMessageListener(new MessageListener() {
                        @Override
                        public ConsumeResult consume(MessageView messageView) {
                            log.info("receive message: {}", messageView);
                            return ConsumeResult.SUCCESS;
                        }
                    })
                    .build();
        } catch (Exception e) {
            log.error("error", e);
        }

    }
}
