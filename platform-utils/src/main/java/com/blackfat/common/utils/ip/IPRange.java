package com.blackfat.common.utils.ip;

public class IPRange {
    String start;
    String end;
    long _start;
    long _end;

    public IPRange(String start, String end) {
        this.start = start;
        this.end = end;
        this._start = IPs.toLong(start);
        this._end = IPs.toLong(end);
    }

    public boolean in(long ip){
        return _start<=ip && ip<=_end;
    }
}
