package com.ljdll.nettyServer.consumer;

import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(topic = "TestTopic", consumerGroup = "test")
public class ConsumerTest implements RocketMQListener<MessageExt> {
    @Override
    public void onMessage(MessageExt messageExt) {
        String msg = new String(messageExt.getBody(), CharsetUtil.UTF_8);
        log.info("接收到消息：" + msg);
    }

}
