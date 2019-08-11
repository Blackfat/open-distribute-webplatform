package com.blackfat.common.utils.ip;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * @author wangfeiyang
 * @Description
 * @create 2019-08-06 16:02
 * @since 1.0-SNAPSHOT
 */
public class IPs {

    public static final long[] IP4_POWERS = {256L*256L*256L, 256L*256L, 256, 1};

    /**
     * A,B,C类私有地址
     */
    public static final IPSet LAN_A = new IPSet(Arrays.asList("10.0.0.0~10.255.255.255"));
    public static final IPSet LAN_B = new IPSet(Arrays.asList("172.16.0.0~172.31.255.255"));
    public static final IPSet LAN_C = new IPSet(Arrays.asList("192.168.0.0~192.168.255.255"));
    public static final IPSet LAN = new IPSet(Arrays.asList("127.0.0.1")).addSet(LAN_A).addSet(LAN_B).addSet(LAN_C);

    public static String getIpv4(String addr){
        if("0:0:0:0:0:0:0:1".equals(addr)){
            return "127.0.0.1";
        }
        return addr;
    }

    public static long toLong(String ip){
        String[] parts = StringUtils.split(ip, '.');
        long num = 0;
        for(int i=0; i<4; i++){
            num += Long.valueOf(parts[i]) * IP4_POWERS[i];
        }
        return num;
    }


}
