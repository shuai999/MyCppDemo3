package com.czy.mycppdemo3;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.UnsupportedEncodingException;

/**
 * java 字符串 转为 c 的字符串
 */
public class Java2CActivity extends Activity {

    static {
        System.loadLibrary("hellolib");
    }

    // java 调用 c，  功能：我java给 你c 传递一个字符串，
    // 你c 给我字符串前边加一个 hello，然后返回给我
    public native String getHelloFromC(String name);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java2c);

        try {
            // 这个是 java中的正常调用，目的是这个。
            // 现在需要在 hello.c 中 反射调用
            byte[] b = "andy".getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 验证方式： 点击button， 传递 andy， 然后返回给 java： hello： andy
        Button btnStr = findViewById(R.id.btnStr);
        btnStr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Java2CActivity.this, getHelloFromC("andy"), Toast.LENGTH_LONG).show();
            }
        });
    }
}
