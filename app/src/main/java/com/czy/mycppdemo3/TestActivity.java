package com.czy.mycppdemo3;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

/**
 * java 调用 C ， 返回字符串 hello world from c
 */
public class TestActivity extends Activity {

    static {
        // 这个就是 Android.mk中的 LOCAL_MODULE:=hellolib。 因为 把 hello.c编译成so后 的名字就是 hellolib。
        // java 调用 hellolib名字， 就能找到 hello.c代码程序
        System.loadLibrary("hellolib");
    }

    // 目的：是从 c语言中 获取helloworld。
    // java不能直接操作硬件， c可以。 java中定义 native方法，来调用c方法，实现java调用c
    // 1. java中定义native方法， 调用C语言。
    public native String helloFromC();

    // 2. 写C语言

    // 3. 把windows下的 hello.c文件 用 ndk-build 编译成 hellolib.so库文件， 在android studio中使用
    // java调用 hellolib 就是 调用 hello.c

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Button btnTest = findViewById(R.id.btnTest);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 调用 helloFromC() 方法， 会打印 hello world from c
                Toast.makeText(TestActivity.this,helloFromC(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
