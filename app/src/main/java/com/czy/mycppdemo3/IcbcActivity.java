package com.czy.mycppdemo3;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

/**
 * 工商银行的 客户端登录。
 *      用 jni校验，否则编译源码后就知道了
 */
public class IcbcActivity extends Activity {

    static {
        System.loadLibrary("hellolib");
    }

    /**
     * 判断是否登录成功  ——————————————  这个用 c语言 实现
     * @param name  用户名
     * @param password 密码
     * @return     404：用户名不存在； 200：登录成功 500：密码错误
     */
    public native int checkLogin(String name, String password);

    private EditText et_name, et_password;
    private Button login;
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

                int result = checkLogin(name, password);
                switch (result){
                    case 200:
                        Toast.makeText(IcbcActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                         break;
                    case 404:
                        Toast.makeText(IcbcActivity.this, "用户名不存在", Toast.LENGTH_LONG).show();
                        break;
                    case 500:
                        Toast.makeText(IcbcActivity.this, "密码错误", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }
}
