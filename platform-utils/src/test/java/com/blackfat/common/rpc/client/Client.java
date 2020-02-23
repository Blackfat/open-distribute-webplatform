package com.blackfat.common.rpc.client;

import com.blackfat.common.rpc.RpcClient;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-02-21 09:47
 * @since 1.0-SNAPSHOT
 */
public class Client {

    public static void main(String[] args)throws  Exception {
        RpcClient client = new RpcClient();
        CalculatorService service = client.refer(CalculatorService.class, "127.0.0.1", 1234);
        int result = service.add(2, 4);
        System.out.println("result:" + result);
    }
}
