package com.czy.mycppdemo3.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class MyView extends View {
    // 设置当前压力值  —— 就是圆柱的 高度
    private int currentPress = 60;

    public int getCurrentPress() {
        return currentPress;
    }

    public void setCurrentPress(int currentPress) {
        this.currentPress = currentPress;
        // 在子线程中更新UI， 会调用 onDraw，根据currentPress
        // 不断调用 drawText， 重新绘制页面
        postInvalidate();
    }

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(2);
        paint.setTextSize(20);

        if (currentPress < 50) {
            paint.setColor(Color.GREEN);
        } else if (currentPress < 70) {
            paint.setColor(Color.YELLOW);
        } else if (currentPress < 90) {
            paint.setColor(Color.BLUE);
        } else {
            // 到这里说明 压力值 > 90， 在这里发短信、放音乐说明 锅炉压力值太大了
            paint.setColor(Color.RED);
        }
        canvas.drawRect(50, 300-currentPress,100, 300, paint);
        canvas.drawText("当前压力值："+currentPress, 130, 270, paint);
    }
}
