package com.czy.mycppdemo3;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import java.lang.reflect.Method;

public class AppReflect extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_reflect);
        /*// 正常调用：
        Person person = new Person();
        person.sing("小苹果");*/

        // 反射调用：
        try {
            // 1. 获取Class对象
            Class<?> clazz = Class.forName("com.czy.mycppdemo3.Person");
            // 2. 获取方法名 sing，  参数1：方法名； 参数2：方法参数，数组
            Method method = clazz.getDeclaredMethod("sing", new Class[]{String.class});
            // 3. 创建对象obj
            Object obj = clazz.newInstance();
            // 4. 调用  方法.对象（正常是：对象.方法）
            method.invoke(obj, new String[]{"小苹果"});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
