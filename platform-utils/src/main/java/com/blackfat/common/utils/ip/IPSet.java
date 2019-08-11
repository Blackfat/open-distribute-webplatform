package com.blackfat.common.utils.ip;

import java.util.ArrayList;
import java.util.List;

public class IPSet {
    List<IPRange> ranges;
    List<String> ips;
    List<Long> _ips;

    public IPSet(List<String> ips) {
        for(String ip:ips){
            int i = ip.indexOf('~');
            if(i > -1){
                addRange(new IPRange(ip.substring(0, i), ip.substring(i+1)));
            }else{
                addIp(ip);
            }
        }
    }

    public IPSet addSet(IPSet set){
        if(set.ranges != null){
            for(IPRange range:set.ranges){
                addRange(range);
            }
        }
        if(set.ips != null){
            for(String ip:set.ips){
                addIp(ip);
            }
        }
        return this;
    }

    void addRange(IPRange range){
        if(ranges == null){ ranges = new ArrayList<IPRange>(); }
        ranges.add(range);
    }

    void addIp(String ip){
        IPSet set = IPSets.see(ip);
        if(set != null){
            addSet(set);
        }else{
            if(ips == null){ ips = new ArrayList<String>(); }
            ips.add(ip);
        }
    }

    List<Long> _ips(){
        if(_ips != null || ips == null){ return _ips; }
        List<Long> list = new ArrayList<Long>(ips.size());
        for(String ip:ips){
            list.add(IPs.toLong(ip));
        }
        _ips = list;
        return list;
    }

    boolean inRanges(long ip){
        for(IPRange range:ranges){
            if(range.in(ip)){ return true; }
        }
        return false;
    }

    public boolean in(String ip){
        if(this == IPSets.ANY){ return true; }
        if(ranges != null && inRanges(IPs.toLong(ip))){
            return true;
        }
        if(ips != null){ return ips.contains(ip); }
        return false;
    }

    public boolean in(long ip){
        if(this == IPSets.ANY){ return true; }
        if(ranges != null && inRanges(ip)){
            return true;
        }
        List<Long> list = _ips();
        if(list != null){ return list.contains(ip); }
        return false;
    }
}
