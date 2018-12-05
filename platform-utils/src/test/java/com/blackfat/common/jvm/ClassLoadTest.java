package com.blackfat.common.jvm;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author wangfeiyang
 * @desc
 * @create 2018/12/5-14:36
 */
public class ClassLoadTest
{
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassLoader myLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
               try{
                   String filename = name.substring(name.lastIndexOf(".")+1)+".class";
                    // 当前类路径下加载文件
                   InputStream is = getClass().getResourceAsStream(filename);
                   if(is == null){
                       return super.loadClass(name);
                   }
                   byte[] b = new byte[is.available()];
                   is.read(b);
                   return defineClass(name, b, 0, b.length);
               }catch (IOException e){
                   throw new ClassNotFoundException(name);
               }
            }
        };
        Object obj = myLoader.loadClass("com.blackfat.common.jvm.ClassLoadTest").newInstance();
        System.out.println(obj.getClass());
        // 加载的类加载器不同，类型检查为false
        System.out.println(obj instanceof ClassLoadTest);
    }
}
