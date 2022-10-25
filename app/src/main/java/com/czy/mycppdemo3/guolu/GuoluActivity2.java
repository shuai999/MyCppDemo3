package com.czy.mycppdemo3.guolu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.annotation.Nullable;

import com.czy.mycppdemo3.R;

public class GuoluActivity2 extends Activity {

    // 动态加载 库文件
    static {
        System.loadLibrary("hellolib");
    }

    // 获取 hello.c 中的 压力值
    public native int getPressValue();

    // 开始监控 ，  java 要调用 c的方法， 用c实现，因为 c可以获取到压力值
    public native void startMonitor();
    // 结束监控 ，  java 要调用 c的方法
    public native void stopMonitor();

    private SeekBar seekbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guolu2);

        Button btnStart = findViewById(R.id.btnStart);
        Button btnStop = findViewById(R.id.btnStop);
        seekbar = findViewById(R.id.seekbar);
        seekbar.setMax(100);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 点击开始监控，不停的调用 c的方法，获取信息
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        startMonitor();
                    }
                }).start();;
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 停止监控，时间很短，直接停止就行
                stopMonitor();
            }
        });
    }

    // 设置当前进度
    public void setMyProgress(int progress){
        seekbar.setProgress(progress);
    }
}
