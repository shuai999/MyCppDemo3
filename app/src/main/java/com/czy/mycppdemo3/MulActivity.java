package com.czy.mycppdemo3;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

/**
 * 用工具开发ndk步骤：  两个数相乘
 */
public class MulActivity extends Activity {

    static {
        // 这个是 Android.mk中的LOCAL_MODULE:=hellolib
        System.loadLibrary("hellolib");
    }

    // 2个数 相乘
    public native int mul(int a, int b);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mul);

        Button btnMul = findViewById(R.id.btnMul);
        btnMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MulActivity.this, "result: "+mul(3,5), 1).show();
            }
        });
    }
}
