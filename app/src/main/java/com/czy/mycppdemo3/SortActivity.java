package com.czy.mycppdemo3;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Date;

public class SortActivity extends Activity {
    private static final String TAG = "SortActivity";

    // 加载 hellolib.so文件
    static {
        System.loadLibrary("hellolib");
    }

    // 对int数组中的 每个值 +1   数组, 数组长度
    public native void addDatas(int[] datas, int length);

    // c的插入排序
    public native void insertData(int[] datas, int length);

    public void init(int[] datas){
        for (int i=0; i<datas.length; i++){
            // 100以内随机数
            datas[i] = (int)Math.random()*100;
        }
    }
    /**
     * 插入排序
     */
    public void insertSort(int[] datas){
        // 插入排序的外层循环，插入排序的次数
        for (int i=1; i<datas.length; i++){
            int temp = datas[i];
            int j=0;
            for (j=i-1; j>0; j--){
                if (datas[j] > temp) {
                    // 把j的位置 放到后边，就是 j+1
                    datas[j+1] = datas[j];
                } else {
                    datas[j+1] = temp;
                    break;//跳出循环
                }
            }
            if (j == -1) {
                datas[0] = temp;
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);

        Button btnArray = findViewById(R.id.btnArray);
        btnArray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] datas = {11,12,13,14};
                addDatas(datas, datas.length);
                Toast.makeText(SortActivity.this, "1的位置："+datas[0], Toast.LENGTH_LONG).show();
            }
        });


        // 创建10个数据
        int[] datas = new int[10];
        // 初始化100以内随机数
        init(datas);
        printData(datas);// 排序之前打印数据

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "排序之前的时间： "+new Date());
                //insertSort(datas); // 排序
                insertData(datas, datas.length);
                printData(datas);// 排序之后打印数据
                Log.i(TAG, "排序之后的时间： "+new Date());
            }
        }).start();

        // 排序之前：13,40,24,68,58,46,21,37,72,96
        // 排序之后：13,21,24,37,40,46,58,68,72,96
    }

    private void printData(int[] data){
        for (int i=0; i<data.length; i++) {
            Log.i(TAG, data[i]+", ");
        }
        Log.i(TAG, "-----------------");
    }

}
