package com.blackfat.common.utils.net;

import javax.net.ServerSocketFactory;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-12-28 15:17
 * @since 1.0-SNAPSHOT
 */
public enum PortCheckEnum {


    /**
     * TCP
     */
    TCP {
        @Override
        public boolean isPortAvailable(int port) {
            try {
                // check for localhost
                ServerSocketFactory.getDefault().createServerSocket(port, 1, InetAddress.getByName("localhost")).close();
                // check for 0.0.0.0
                ServerSocketFactory.getDefault().createServerSocket(port, 1).close();
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
    },
    /**
     * UDP
     */
    UDP {
        @Override
        public boolean isPortAvailable(int port) {
            try {
// check for localhost
                new DatagramSocket(port, InetAddress.getByName("localhost")).close();
// check for 0.0.0.0
                new DatagramSocket(port).close();
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
    };

    public boolean isPortAvailable(int port) {
        return true;
    }
}
