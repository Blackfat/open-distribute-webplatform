package com.blackfat.learn.netty.client;

import org.jboss.netty.channel.*;

/**
 * @author wangfeiyang
 * @desc
 * @create 2017/10/13-13:46
 */
public class ClientLogicHandler extends SimpleChannelHandler {

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        System.out.println("客户端连接成功!");
        String str = "hi server!";
        e.getChannel().write(str);//异步
    }

    @Override
    public void writeComplete(ChannelHandlerContext ctx, WriteCompletionEvent e) throws Exception {
        System.out.println("客户端写消息完成");
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        String msg = (String) e.getMessage();
        System.out.println("客户端接收到消息, msg: " + msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }
}
