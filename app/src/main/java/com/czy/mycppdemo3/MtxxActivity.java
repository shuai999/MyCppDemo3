package com.czy.mycppdemo3;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.mt.mtxx.image.JNI;

public class MtxxActivity extends Activity {

    // 动态加载 mtimage-jni文件, 文件格式都是： lib开头， .so结尾，
    // 这里不用写前边的这2个，直接写 mtimage-jni即可
    static {
        System.loadLibrary("mtimage-jni");
    }

    private ImageView iv_mtxx;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mtxx);

        iv_mtxx = findViewById(R.id.iv_mtxx);
        // 获取图片
        iv_mtxx.setImageBitmap(BitmapFactory.decodeFile("/sdcard/1.jpg"));
    }

    /**
     * 修改图片的样子，就是修改像素点。 就是整型数组
     *   1. 获取图片的像素点 = 宽*高
     */
    public void style1(View view){
        // 1. 获取模拟器的 图片
        Bitmap bitmap1 = BitmapFactory.decodeFile("/sdcard/1.jpg");
        // 2. 获取图片的像素， 像素是 整形数组组成。
        // 要定义数组，就得先获取 图片的宽高组成
        int width = bitmap1.getWidth();
        int height = bitmap1.getHeight();
        // 3. 定义像素点的数组，把图片的宽高放进去
        // 把图片bitmap1的像素点 ，全部放到 pixels数组中
        int[] pixels = new int[width * height];

        // getPixels()： 像素数组
        // 参数1：存放像素的数组pixels； 参数2：从0开始； 参数3：大小要>=width: 参数4、5：都是0； 参数6、7就是图片的宽高
        bitmap1.getPixels(pixels, 0, width, 0, 0, width, height);

        // 4. 用JNI， 改变pixels数组
        JNI jni = new JNI();
        // 设置图像的样式：   参数1：像素数组 ；参数2、3： 宽高
        jni.StyleLomoB(pixels, width, height);

        // 修改像素后， 创建新的图片 并返回。
        bitmap1 = Bitmap.createBitmap(pixels, width, height, bitmap1.getConfig());
        iv_mtxx.setImageBitmap(bitmap1);
    }

    public void style2(View view){
        // 1. 获取模拟器的 图片
        Bitmap bitmap1 = BitmapFactory.decodeFile("/sdcard/1.jpg");
        // 2. 获取图片的像素， 像素是 整形数组组成。
        // 要定义数组，就得先获取 图片的宽高组成
        int width = bitmap1.getWidth();
        int height = bitmap1.getHeight();
        // 3. 定义像素点的数组，把图片的宽高放进去
        // 把图片bitmap1的像素点 ，全部放到 pixels数组中
        int[] pixels = new int[width * height];

        // getPixels()： 像素数组
        // 参数1：存放像素的数组pixels； 参数2：从0开始； 参数3：大小要>=width: 参数4、5：都是0； 参数6、7就是图片的宽高
        bitmap1.getPixels(pixels, 0, width, 0, 0, width, height);

        // 4. 用JNI， 改变pixels数组
        JNI jni = new JNI();
        // 设置图像的样式：   参数1：像素数组 ；参数2、3： 宽高
        jni.StyleLomoC(pixels, width, height);

        // 修改像素后， 创建新的图片 并返回。
        bitmap1 = Bitmap.createBitmap(pixels, width, height, bitmap1.getConfig());
        iv_mtxx.setImageBitmap(bitmap1);
    }
}
