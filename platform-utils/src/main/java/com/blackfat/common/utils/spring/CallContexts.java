package com.blackfat.common.utils.spring;

/**
 * @author wangfeiyang
 * @Description
 * @create 2021-08-10 16:09
 * @since 1.0-SNAPSHOT
 */
public class CallContexts {

    private static final ThreadLocal<CallContext> LOCAL = new ThreadLocal();

    public static CallContext start(String api){
        CallContext context = new CallContext();
        context.setApi(api);
        LOCAL.set(context);
        return context;
    }

    public static CallContext get(){
        return LOCAL.get();
    }

    public static void remove(){
        LOCAL.remove();
    }
}
