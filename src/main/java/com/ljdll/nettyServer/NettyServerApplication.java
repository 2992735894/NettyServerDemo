package com.ljdll.nettyServer;

import com.ljdll.nettyServer.config.NettyServer;

public class NettyServerApplication {

    public static void main(String[] args) throws Exception {
        NettyServer nettyServer = new NettyServer(8080);
        nettyServer.start();
    }

}
