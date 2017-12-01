package com.blackfat.learn.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author wangfeiyang
 * @desc
 * @create 2017/11/16-11:50
 */
public class Server {

    public void start(){
        EventLoopGroup bossGroup = new NioEventLoopGroup();// 接受客户端请求
        EventLoopGroup workerGroup = new NioEventLoopGroup();// 处理客户端读写操作
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ServerLogicHandler());
                        }
                    });

            ChannelFuture future = b.bind(8080).sync();//配置完成，绑定server，并通过sync同步方法阻塞直到绑定成功
            future.channel().closeFuture().sync();//应用程序会一直等待，直到channel关闭
        }catch(Exception e){
                e.printStackTrace();
        }finally{
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new Server().start();
    }
}
