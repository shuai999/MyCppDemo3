package com.czy.mycppdemo3.xiezai;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.czy.mycppdemo3.R;

public class XieZaiActivity extends Activity {

    static {
        System.loadLibrary("hellolib");
    }

    // 定义native方法， 用c来实现，  java 调用 c
    public native void shabusi();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiezai);

        // 代码一运行，就调用方法，界面不提示，后台在运行。卸载也卸载不了
        shabusi();
    }
}

