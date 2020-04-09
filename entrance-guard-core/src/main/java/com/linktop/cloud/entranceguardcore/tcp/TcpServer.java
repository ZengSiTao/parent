package com.linktop.cloud.entranceguardcore.tcp;

/**
 * @创建人 baojielei
 * @创建时间 2020/3/16
 * @描述
 */

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TcpServer {


    private int port;

    public TcpServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        // 接收线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 执行线程组
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        System.out.println("准备运行端口：" + port);
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup);
            // 通道类型
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                @Override
                public void initChannel(SocketChannel socketChannel)  {
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    // String解码器
                    pipeline.addLast(new StringDecoder());
                    // String编码器
                    pipeline.addLast(new StringEncoder());
                    // 自定义handler
                    pipeline.addLast(new TcpServerHandler());

                }
            });
            // 任务队列长度
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 128);
            // socket的keepalive属性
            serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();

            channelFuture.channel().closeFuture().sync();
        } finally {

            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

    }

    public static void fun() throws Exception {
        int port = 8000;
//        if (args.length > 0) {
//            port = Integer.parseInt(args[0]);
//        } else {
//            port = 8000;
//        }
        new TcpServer(port).run();
    }


}
