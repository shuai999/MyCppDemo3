package com.czy.mycppdemo3.guolu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.czy.mycppdemo3.R;

public class GuoluActivity extends Activity {

    // 动态加载库文件
    static {
        System.loadLibrary("hellolib");
    }

    // 定义native方法 获取锅炉的压力
    public native int getPressValue();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guolu);
        Button btnGetPress = findViewById(R.id.btnGetPress);
        btnGetPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pressValue = getPressValue();
                Toast.makeText(GuoluActivity.this, "压力值："+pressValue,Toast.LENGTH_LONG).show();
            }
        });
    }
}
