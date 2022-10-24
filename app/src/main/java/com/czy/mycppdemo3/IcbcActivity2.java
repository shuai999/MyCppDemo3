package com.czy.mycppdemo3;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

/**
 * 改版的 ————  工商银行的 客户端登录。
 *      把验证结果的 toast删掉。  这些 toast在c中实现。
 *      用c调用 java
 */
public class IcbcActivity2 extends Activity {
    static {
        System.loadLibrary("hellolib");
    }

    /**
     * 判断是否登录成功  ——————————————  这个用 c语言 实现
     */
    public native int checkLogin(String name, String password);

    /**
     * java写好方法， 让c调用。 就是 c 调用 java
     */
    public void setName(String name){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                et_name.setText(name);
            }
        });
    }

    // 显示和隐藏对话框，都是java的，  现在需要 c来调用 java 的界面功能。
    // 这里是  c 调用 showMessage， 在c中来显示对话框
    // 显示对话框
    public void showMessage(String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (pd != null) {
                    pd.dismiss();
                }
                pd = new ProgressDialog(IcbcActivity2.this);
                pd.setTitle("正在处理");
                // 传递过来的msg :  比如 用户名正在加密、密码正在加密
                pd.setMessage(msg);
                pd.show();
            }
        });
    }

    // 关闭对话框
    public void closeMessage(){
        if (pd != null) {
            pd.dismiss();
        }
    }

    private EditText et_name, et_password;
    private Button login;
    private ProgressDialog pd;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icbc);

        et_name = findViewById(R.id.et_name);
        et_password = findViewById(R.id.et_password);
        login = findViewById(R.id.login);

        // 点击登录，把用户名和密码 传递给 native方法
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et_name.getText().toString().trim();
                String password = et_password.getText().toString().trim();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // java调用c:  checkLogin 调用 c的 Java_com_czy_mycppdemo3_IcbcActivity_checkLogin方法，
                        // c调用java： 然后在 c的 Java_com_czy_mycppdemo3_IcbcActivity_checkLogin方法 又调用java的 closeMessage方法
                        checkLogin(name, password);
                    }
                }).start();
            }
        });
    }
}
