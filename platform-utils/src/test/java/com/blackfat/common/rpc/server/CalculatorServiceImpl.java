package com.blackfat.common.rpc.server;

import com.blackfat.common.rpc.client.CalculatorService;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-02-21 09:48
 * @since 1.0-SNAPSHOT
 */
public class CalculatorServiceImpl implements CalculatorService {
    @Override
    public int add(int a, int b) {
        return a + b;
    }
}
