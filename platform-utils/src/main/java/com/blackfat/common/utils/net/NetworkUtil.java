package com.blackfat.common.utils.net;

import cn.hutool.core.net.NetUtil;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-12-28 15:14
 * @since 1.0-SNAPSHOT
 */
public class NetworkUtil {

    public static boolean isPortUsing(String hostPort) {
        String arrays[] = hostPort.split(":");
        return isPortUsing(arrays[0], Integer.parseInt(arrays[1]));
    }

    public static boolean isPortUsing(String host, int port) {
        SocketAddress address = new InetSocketAddress(host, port);
        try (Socket socket = new Socket()) {
            socket.setReceiveBufferSize(8192);
            socket.setSoTimeout(3000);
            socket.connect(address, 3000);//1.判断ip、端口是否可连接
            return true;
        } catch (IOException e) {

        }
        return false;
    }

    /**
     * 获取可用端口
     * @param defaultPort 默认端口
     * @return
     */
    public static Integer getHealthServerPort(Integer defaultPort) {
        // 考虑支持用户自定义端口
        int port = Integer.valueOf(defaultPort);
        if (!PortCheckEnum.TCP.isPortAvailable(port)) {
            port = NetUtil.getUsableLocalPort();
        }

        return port;
    }
}
