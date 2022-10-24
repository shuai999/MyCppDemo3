package com.czy.mycppdemo3;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

/**
 * java 调用 c， 中定义的 add(a, b)方法
 */
public class AddActivity extends Activity {

    // 加载 hellolib.so文件
    static {
        System.loadLibrary("hellolib");
    }

    // 1. java中定义 native 方法
    public native int add(int a, int b);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int result = add(3 ,5);
                Toast.makeText(AddActivity.this, "result："+result, Toast.LENGTH_LONG).show();
            }
        });
    }
}
