package com.blackfat.dubbo.filter;

/**
 * @author wangfeiyang
 * @desc   拦截对象
 * @create 2018/1/8-11:31
 */
public class FilterDesc {

    private String interfaceName;
    private String methodName;
    private Object[] args;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
