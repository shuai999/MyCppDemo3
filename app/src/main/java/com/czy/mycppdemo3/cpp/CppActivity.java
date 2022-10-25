package com.czy.mycppdemo3.cpp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.czy.mycppdemo3.R;

public class CppActivity extends Activity {

    // 加载动态库文件
    static {
        System.loadLibrary("cpp");
    }

    // 定义native方法， java 调用 c++， 方法有 c++ 实现
    public native String helloFromCpp();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_cpp);

        // 效果就是： 打开app，就提示toast
        Toast.makeText(this, "来自c++的字符串："+helloFromCpp(),Toast.LENGTH_LONG).show();
    }
}
