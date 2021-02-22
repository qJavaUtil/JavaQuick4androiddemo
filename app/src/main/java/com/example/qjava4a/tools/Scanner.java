package com.example.qjava4a.tools;

import java.lang.reflect.Field;
import java.util.Enumeration;

import blxt.qjava.autovalue.inter.Component;
import blxt.qjava.autovalue.inter.Run;
import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;

@Component
public class Scanner {

    private static Field dexField;

    static {
        try {
            dexField = PathClassLoader.class.getDeclaredField("mDexs");
            dexField.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Scanner(){
        System.out.println("测试_Scanner");
    }

    @Run
    public void test(){
        System.out.println("测试");
    }

    public static void scan() {
        try {
            PathClassLoader classLoader = (PathClassLoader) Thread.currentThread().getContextClassLoader();

            DexFile[] dexs = (DexFile[]) dexField.get(classLoader);
            for (DexFile dex : dexs) {
                Enumeration<String> entries = dex.entries();
                while (entries.hasMoreElements()) {
                    String entry = entries.nextElement();

                    Class<?> entryClass = dex.loadClass(entry, classLoader);
                    if (entryClass != null) {
                        System.out.println("扫描:" + entryClass.getName());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}