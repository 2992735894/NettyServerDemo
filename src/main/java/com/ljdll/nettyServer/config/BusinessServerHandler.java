package com.ljdll.nettyServer.config;

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

import java.util.HashMap;
import java.util.Map;

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

    public void channelReadHttpRequest(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) {
        ByteBuf content = Unpooled.copiedBuffer("readHttp", CharsetUtil.UTF_8);
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
