package com.blackfat.learn.netty.client;

import com.blackfat.learn.netty.server.ServerLogicHandler;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * @author wangfeiyang
 * @desc
 * @create 2017/10/13-13:44
 */
public class Client {

    public void start() {
        ChannelFactory factory = new NioClientSocketChannelFactory(
                Executors.newCachedThreadPool(), // boss线程池
                Executors.newCachedThreadPool(),  // worker线程池
                8); // worker线程数

        ClientBootstrap bootstrap = new ClientBootstrap(factory);

   /*
   * 对于每一个连接channel, client都会调用PipelineFactory为该连接创建一个ChannelPipline
   *
   * */
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("decoder", new StringDecoder());
                pipeline.addLast("encoder", new StringEncoder());
                pipeline.addLast("handler", new ClientLogicHandler());
                return pipeline;
            }
        });

         bootstrap.connect(new InetSocketAddress("127.0.0.1", 8080));
        System.out.println("client start success!");
    }


    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }
}
