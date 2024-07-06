package com.ljdll.nettyServer.config.netty;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljdll.nettyServer.common.constant.R;
import com.ljdll.nettyServer.common.utils.ApplicationContextUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.CharsetUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BusinessServerHandler extends SimpleChannelInboundHandler<Object> {
    Log log = LogFactory.getLog(BusinessServerHandler.class);
    private static final Map<ChannelId, ChannelHandlerContext> userMap = new HashMap<>();
    private static final Map<ChannelId, FullHttpRequest> httpRequestMap = new HashMap<>();

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object object) throws Exception {
        this.channelRead(ctx, object);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest fullHttpRequest) {
            if (fullHttpRequest.decoderResult().isSuccess()
                    && fullHttpRequest.headers().get("Connection").equals("Upgrade")
                    && fullHttpRequest.headers().get("Upgrade").equals("websocket")) {
                httpRequestMap.put(ctx.channel().id(), fullHttpRequest);
                userMap.put(ctx.channel().id(), ctx);
            }

            this.channelReadHttpRequest(ctx, fullHttpRequest);
        } else if (msg instanceof TextWebSocketFrame textWebSocketFrame) {
            this.channelReadWebSocketFrame(ctx, textWebSocketFrame);
        }
    }

    private void channelReadWebSocketFrame(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame) {
        log.info("接收消息:" + textWebSocketFrame.text() + "\n" + ctx.channel().id());
        ctx.writeAndFlush(new TextWebSocketFrame("readWebSocket"));
    }

    public void channelReadHttpRequest(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) throws Exception {
        ByteBuf content;
        if (userMap.containsKey(ctx.channel().id())) {
            content = Unpooled.copiedBuffer("upgradeWebSocket", CharsetUtil.UTF_8);
        } else {
            ApplicationContext webApplicationContext = ApplicationContextUtil.getApplicationContext();
            RequestMappingHandlerMapping mapping = webApplicationContext.getBean(RequestMappingHandlerMapping.class);
            Map<RequestMappingInfo, HandlerMethod> methodMap = mapping.getHandlerMethods();
            boolean find = false;
            Object object = null;
            for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : methodMap.entrySet()) {
                // 匹配路由
                // 目前匹配了application/json multipart/form-data
                if (!CollectionUtils.isEmpty(entry.getKey().getMethodsCondition().getMethods())
                        && entry.getKey().getMethodsCondition().getMethods().stream().toList().getFirst().name().equals(fullHttpRequest.method().name())
                        &&
                        (entry.getKey().getPatternValues().stream().toList().getFirst().equals(fullHttpRequest.uri())
                                || isPathUri(entry.getKey().getPatternValues().stream().toList().getFirst(), fullHttpRequest.uri()))) {
                    find = true;
                    boolean pathValue = isPathUri(entry.getKey().getPatternValues().stream().toList().getFirst(), fullHttpRequest.uri());
                    // 获取对应的bean实例并执行
                    Object instance = ApplicationContextUtil.getApplicationContext().getBean(entry.getValue().getBeanType());
                    Class<?>[] paramClazzArr = entry.getValue().getMethod().getParameterTypes();
                    ObjectMapper objectMapper = new ObjectMapper();

                    if (paramClazzArr.length == 0) {
                        object = objectMapper.writeValueAsString(entry.getValue().getMethod().invoke(instance));
                    } else {
                        if (pathValue) {
                            String value = fullHttpRequest.uri().split("/")[fullHttpRequest.uri().split("/").length - 1].replace("\\{", "").replace("\\}", "");
                            object = objectMapper.writeValueAsString(entry.getValue().getMethod().invoke(instance, value));
                        } else {
                            String contentType = fullHttpRequest.headers().get("Content-Type");
                            if (StringUtils.hasText(contentType)) {
                                String paramType = contentType.split(";")[0];
                                if (paramType.equals("application/json")) {
                                    String body = fullHttpRequest.content().toString(CharsetUtil.UTF_8);
                                    object = objectMapper.writeValueAsString(entry.getValue().getMethod().invoke(instance, JSONObject.parseObject(body, paramClazzArr[0])));
                                } else if (paramType.equals("multipart/form-data")) {
                                    String[] formString = fullHttpRequest.content().toString(CharsetUtil.UTF_8).split("name=");
                                    Map<String, String> formMap = new HashMap<>();
                                    for (int index = 1; index < formString.length; index++) {
                                        String key = formString[index].split("\\r\\n")[0].replace("\"", "");
                                        String value = formString[index].split("\\r\\n\\r\\n")[1].split("\\r\\n")[0];
                                        formMap.put(key, value);
                                    }

                                    Parameter[] parameters = entry.getValue().getMethod().getParameters();
                                    Object[] params = new Object[parameters.length];
                                    if (formString.length > 1) {
                                        for (int index = 0; index < parameters.length; index++) {
                                            try {
                                                Object param = formMap.get(parameters[index].getName());
                                                Class<?> clazz = parameters[index].getType();
                                                if (clazz.equals(String.class) ||
                                                        clazz.equals(Integer.class) ||
                                                        clazz.equals(Long.class) ||
                                                        clazz.equals(Double.class) ||
                                                        clazz.equals(Float.class) ||
                                                        clazz.equals(Boolean.class) ||
                                                        clazz.equals(Byte.class) ||
                                                        clazz.equals(Short.class) ||
                                                        clazz.equals(Character.class)) {
                                                    params[index] = clazz.cast(param);
                                                } else {
                                                    params[index] = objectMapper.convertValue(formMap, clazz);
                                                }
                                            } catch (Exception ignore) {
                                            }
                                        }
                                    }
                                    object = objectMapper.writeValueAsString(entry.getValue().getMethod().invoke(instance, params));
                                }
                            } else {
                                Parameter[] parameters = entry.getValue().getMethod().getParameters();
                                Object[] params = new Object[parameters.length];
                                try {

                                    for (int index = 0; index < parameters.length; index++) {
                                        Class<?> clazz = parameters[index].getType();
                                        if (clazz.equals(Integer.class) ||
                                                clazz.equals(Long.class) ||
                                                clazz.equals(Double.class) ||
                                                clazz.equals(Float.class) ||
                                                clazz.equals(Boolean.class) ||
                                                clazz.equals(Byte.class) ||
                                                clazz.equals(Short.class) ||
                                                clazz.equals(Character.class)) {
                                            params[index] = clazz.cast(0);
                                        }
                                    }
                                } catch (Exception ignore) {
                                }

                                object = objectMapper.writeValueAsString(entry.getValue().getMethod().invoke(instance, params));
                            }
                        }
                    }
                }
            }
            if (!find) {
                object = R.fail(R.NOT_FOUND, "error route");
            }

            if (Objects.nonNull(object)) {
                content = Unpooled.copiedBuffer(object.toString(), CharsetUtil.UTF_8);
            } else {
                content = Unpooled.copiedBuffer("null", CharsetUtil.UTF_8);
            }
        }

        HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("断开链接:" + ctx);
        userMap.remove(ctx.channel().id());
        httpRequestMap.remove(ctx.channel().id());
    }

    public boolean isPathUri(String uri, String url) {
        if (uri.contains("{") && uri.contains("}")) {
            int uriLastIndex = uri.lastIndexOf("/");
            String newUri = uri.substring(0, uriLastIndex);
            int urlLastIndex = url.lastIndexOf("/");
            String newUrl = url.substring(0, urlLastIndex);
            return newUri.equals(newUrl);
        }
        return false;
    }

//    @Override
//    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        if (evt instanceof IdleStateEvent idleStateEvent) {
//            switch (idleStateEvent.state()) {
//                case READER_IDLE:
//                    log.info("读空闲");
//                    break;
//                case WRITER_IDLE:
//                    log.info("写空闲");
//                    break;
//                case ALL_IDLE:
//                    log.info("读写空闲，资源释放");
//                    Channel channel = ctx.channel();
//                    channel.close();
//                    userMap.remove(ctx.channel().id());
//                    httpRequestMap.remove(ctx.channel().id());
//                    break;
//            }
//        }
//    }
}
