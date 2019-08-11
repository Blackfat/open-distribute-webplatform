package com.blackfat.common.utils.ip;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class IPSets {

    static final Map<String, IPSet> vars = new HashMap<>();

    public static final IPSet LAN_A = new IPSet(Arrays.asList("10.0.0.0~10.255.255.255"));
    public static final IPSet LAN_B = new IPSet(Arrays.asList("172.16.0.0~172.31.255.255"));
    public static final IPSet LAN_C = new IPSet(Arrays.asList("192.168.0.0~192.168.255.255"));
    public static final IPSet LAN = new IPSet(Arrays.asList("127.0.0.1")).addSet(LAN_A).addSet(LAN_B).addSet(LAN_C);
    public static final IPSet ANY = new IPSet(Arrays.asList("0.0.0.0~255.255.255.255"));

    public static final String
            _LAN_A=bind(".LAN_A", LAN_A),
            _LAN_B=bind(".LAN_B", LAN_B),
            _LAN_C=bind(".LAN_C", LAN_C),
            _LAN=bind(".LAN", LAN),
            _ANY=bind(".ANY", ANY);

    public static String bind(String var, IPSet ipSet){
        vars.put(var, ipSet);
        return var;
    }

    public static IPSet of(IPSet defSet, String... ips){
        return ArrayUtils.isEmpty(ips) ? defSet:new IPSet(Arrays.asList(ips));
    }

    public static IPSet of(String... ips){
        return of(ANY, ips);
    }

    public static IPSet see(String var){
        if(var.charAt(0) != '.') { return null; }
        return vars.get(var.toUpperCase());
    }

}
