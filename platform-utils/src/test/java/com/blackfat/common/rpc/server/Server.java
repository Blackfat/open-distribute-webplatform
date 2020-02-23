package com.blackfat.common.rpc.server;

import com.blackfat.common.rpc.RpcServer;
import com.blackfat.common.rpc.client.CalculatorService;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-02-21 09:49
 * @since 1.0-SNAPSHOT
 */
public class Server {

    public static void main(String[] args) throws Exception {
        CalculatorService service = new CalculatorServiceImpl();
        RpcServer server = new RpcServer();
        server.export(service, 1234);
    }
}
