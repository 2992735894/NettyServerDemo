package com.ljdll.nettyServer.param;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lombok.Data;

@Data
public class RocketMqSendMessageParam {

    private String topic;

    private String messageKey;

    private String messageTag;

    private JSONObject messageObject;

    private JSONArray messageArray;
}
