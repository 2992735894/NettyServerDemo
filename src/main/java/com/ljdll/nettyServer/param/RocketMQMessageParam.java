package com.ljdll.nettyServer.param;

import lombok.Data;

@Data
public class RocketMQMessageParam {

    private String topic;

    private String message;
}
