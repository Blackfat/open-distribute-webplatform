package com.blackfat.learn.netty.server;

import org.jboss.netty.channel.*;

/**
 * @author wangfeiyang
 * @desc
 * @create 2017/10/13-11:53
 */
public class ServerLogicHandler extends SimpleChannelHandler {


    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        String msg = (String) e.getMessage();
        System.out.println("接收到了client的消息, msg: " + msg);

        Channel channel = e.getChannel();
        String str = "hi, client";
        channel.write(str);//写消息发给client端
        System.out.println("服务端发送数据: " + str + "完成");
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("连接成功, channel: " + e.getChannel().toString());
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }
}
