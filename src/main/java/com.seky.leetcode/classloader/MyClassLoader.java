package com.seky.leetcode.classloader;

import java.io.FileInputStream;

/**
 * @author: wf
 * @create: 2023/12/19 9:02
 * @description:
 */
public class MyClassLoader extends ClassLoader{
    private String classPath;
    
    public MyClassLoader(String classPath) {
        this.classPath = classPath;
    }

    /**
     * 将读取指定目录下的class文件
     * @param name  如：com.seky.leetcode.entry.Student
     * @return
     * @throws Exception
     */
    private byte[] getByte(String name) throws Exception {
        name = name.replaceAll("\\.", "/");
        FileInputStream fileInputStream = new FileInputStream(classPath + "/" + name + ".class");
        int len = fileInputStream.available();
        byte[] data = new byte[len];
        fileInputStream.read(data);
        fileInputStream.close();
        return data;
    }

    /**
     * 自定义加载器：定义class类寻找方法findClass()(自定义类加载器寻找类的方式)
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] data = getByte(name);
            //ClassLoader.defineClass将一个字节数组转为class对象，这个字节数组是class文件读取的字节数组
            return defineClass(name, data, 0, data.length);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ClassNotFoundException();
        }
    }

    public static void main(String[] args) throws Exception {
        MyClassLoader classLoader = new MyClassLoader("D:/test");
        
        //在D:/test/com/seky/leetcode/entry目录下放入Student.class文件
        // loadClass方法 用双亲委派模式寻找Student类
        Class<?> clazz = classLoader.loadClass("com.seky.leetcode.entry.Student");
        
        //Object student = clazz.newInstance();
        //Method sayMethod = clazz.getMethod("say", null);
        //sayMethod.invoke(student, "我是学生");
        
        //打印Student类真正的类加载器
        System.out.println(clazz.getClassLoader().getClass().getName());
    }
}
