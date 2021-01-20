package com.blackfat.common.utils.web;

import com.blackfat.common.utils.ip.IPs;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-12-30 10:37
 * @since 1.0-SNAPSHOT
 */
public class RequestUtils {

    public static final String X_Real_IP = "X-Real-IP";
    public static final String x_forwarded_for = "x-forwarded-for";
    public static final String x_client_ip = "x-client-ip";
    public static final String remoteip = "remoteip";

    /**
     * 获取当前web请求的真实ip
     * @param request HttpServletRequest NativeWebRequest
     */
    public static String getRealIP(Object request){
        String ip = getAttribute(request, X_Real_IP);
        if(ip != null){ return ip; }
        ip = parseRealIP(request);
        setAttribute(X_Real_IP, ip, request);
        return ip;
    }

    /**
     * 解析当前web请求的真实ip
     * @param request HttpServletRequest NativeWebRequest
     */
    public static String parseRealIP(Object request){
        String ip = getHeader(request, x_client_ip);
        if(!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)){ return ip; }
        ip = getHeader(request, remoteip);
        if(!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)){ return ip; }
        ip = getHeader(request, x_forwarded_for);
        if(StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)){ return IPs.getIpv4(getRemoteAddr(request)); }
        int i = ip.indexOf(',');
        ip = i>-1 ? ip.substring(0, i):ip;
        return StringUtils.isEmpty(ip) ? IPs.getIpv4(getRemoteAddr(request)):ip;
    }

    /**
     * @param request HttpServletRequest NativeWebRequest
     */
    public static String getRemoteAddr(Object request){
        if(request instanceof HttpServletRequest){
            return ((HttpServletRequest) request).getRemoteAddr();
        }
        Object req = ((NativeWebRequest) request).getNativeRequest();
        if(req instanceof HttpServletRequest){
            return ((HttpServletRequest) req).getRemoteAddr();
        }
        return null;
    }

    /**
     * @param request HttpServletRequest NativeWebRequest
     */
    public static String getHeader(Object request, String key){
        if(request instanceof HttpServletRequest){
            return ((HttpServletRequest) request).getHeader(key);
        }
        return ((NativeWebRequest) request).getHeader(key);
    }

    /**
     * @param request ServletRequest RequestAttributes
     */
    public static <T> T getAttribute(Object request, String key){
        if(request instanceof ServletRequest){
            return (T) ((ServletRequest) request).getAttribute(key);
        }
        if(request == null){ request = RequestContextHolder.currentRequestAttributes(); }
        if(request == null){ return null; }
        return (T) ((RequestAttributes) request).getAttribute(key, RequestAttributes.SCOPE_REQUEST);
    }

    /**
     * @param request ServletRequest RequestAttributes
     */
    public static void setAttribute(String key, Object val, Object request){
        if(request instanceof ServletRequest){
            ((ServletRequest) request).setAttribute(key, val);
            return;
        }
        if(request == null){ request = RequestContextHolder.currentRequestAttributes(); }
        if(request == null){ return ; }
        ((RequestAttributes) request).setAttribute(key, val, RequestAttributes.SCOPE_REQUEST);
    }
}
