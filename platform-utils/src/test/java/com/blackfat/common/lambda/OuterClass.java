package com.blackfat.common.lambda;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-04-24 10:01
 * @since 1.0-SNAPSHOT
 */
/**
 *内部类和静态内部类都是延时加载的，也就是说只有在明确用到内部类时才加载。只使用外部类时不加载
 *非静态内部类不能拥有 静态变量 静态语句 静态方法
 *静态内部类无需外部类实例即可调用
 *非静态内部类需要外部类实例调用
 */
public class OuterClass {

    static{
        System.out.println("OuterClass static load.");
    }

    public OuterClass()
    {
        System.out.println("flag");
    }

    public OuterClass(String flag)
    {
        System.out.println("flag:"+flag);
    }


    class InnerClass
    {
        private OuterClass out = new OuterClass("inner");
    }

    static class InnerStaticClass
    {
        private static OuterClass out = new OuterClass("innerStatic");
        static{
            System.out.println("InnerStaticClass static load.");
        }
        private static void load()
        {
            System.out.println("InnerStaticClass func load().");
        }
    }

    public static OuterClass getInstatnce()
    {
        return OuterClass.InnerStaticClass.out;
    }

    public static void main(String[] args)
    {
//        OuterClass out = new OuterClass();
//        OuterClass.InnerStaticClass.load();
//        // 静态内部类无需外部类实例即可调用
        OuterClass out = OuterClass.InnerStaticClass.out;
//        OuterClass.InnerClass innerClass = out.new InnerClass();
//        // 非静态内部类需要外部类实例调用
    }

}
