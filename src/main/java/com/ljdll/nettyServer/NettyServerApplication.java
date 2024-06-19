package com.ljdll.nettyServer;

import com.ljdll.nettyServer.config.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyServerApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(NettyServerApplication.class, args);

        NettyServer nettyServer = new NettyServer(8080);
        nettyServer.start();
    }

}
