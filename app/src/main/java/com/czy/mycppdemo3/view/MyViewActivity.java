package com.czy.mycppdemo3.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.czy.mycppdemo3.R;

/**
 * 自定义矩形和文字 实现 ———— 锅炉压力检测
 */
public class MyViewActivity extends Activity {

    static {
        System.loadLibrary("hellolib");
    }

    // java 调用 c的startMonitor，  开始监控
    public native void startMonitor();

    // java 调用 c的startMonitor，  结束监控
    public native void stopMonitor();

    private MyView myView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myview);

        myView = findViewById(R.id.myView);

        Button btnStart = findViewById(R.id.btnStart);
        Button btnStop = findViewById(R.id.btnStop);

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

    /**
     *  hello.c 中获取到 压力值后， c调用java。
     *  调用 java的 setMyProgress方法，在这里更新压力值
     *
     * @param progress hello.c 获取到的压力值
     */
    public void setMyProgress(int progress) {
        if (progress > 90) {
            //  大于90，可以发送短信、播放音乐
        }
        myView.setCurrentPress(progress);
    }
}
