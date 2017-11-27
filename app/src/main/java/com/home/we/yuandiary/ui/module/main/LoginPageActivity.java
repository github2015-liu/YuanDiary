package com.home.we.yuandiary.ui.module.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.home.we.yuandiary.R;
import com.home.we.yuandiary.bean.LoginFailed;
import com.home.we.yuandiary.bean.LoginFedBack;
import com.home.we.yuandiary.common.util.GsonUtil;
import com.home.we.yuandiary.litehttp.AppApi;
import com.home.we.yuandiary.litehttp.AppContext;
import com.home.we.yuandiary.ui.fragment.MineFragment;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.response.Response;

import cn.jesse.nativelogger.NLogger;

/**
 * Created by pactera on 2017/11/19.
 */

public class LoginPageActivity extends Activity {

    private TextView m_tv_LoginNowRegist;
    private Button m_btn_login;
    private EditText m_et_login_username;
    private EditText m_et_login_pass;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();


    }

    private void initViews() {

        m_tv_LoginNowRegist = (TextView) findViewById(R.id.tv_login_now_regist);
        m_et_login_username = (EditText) findViewById(R.id.et_login_username);
        m_et_login_pass = (EditText) findViewById(R.id.et_login_pass);


        m_btn_login = (Button) findViewById(R.id.btn_login_login);

        //点击登录按钮
        m_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NLogger.d("ljk","---------------》点击登录按钮");

                //获取注册信息
                String s_login_phoneNum = m_et_login_username.getText().toString().trim();
                String s_login_pwd = m_et_login_pass.getText().toString().trim();

                NLogger.d("ljk","用户名" + s_login_phoneNum + "\n密码" + s_login_pwd);


                AppContext.getHttp(LoginPageActivity.this)
                        .executeAsync(new AppApi.LoginParam(s_login_phoneNum,s_login_pwd))
                        .setHttpListener(new HttpListener<LoginFedBack>() {
                            @Override
                            public void onSuccess(LoginFedBack loginFedBack, Response<LoginFedBack> response) {
                                super.onSuccess(loginFedBack, response);
                                NLogger.d("ljk","LOGIN onSuccess");
                                String flag = loginFedBack.getFlag();
                                NLogger.d("ljk", "登录标识" + response.toString());

                                if(flag.equals("001")) { //登录成功 返回到个人中心页面

                                    Toast.makeText(LoginPageActivity.this, "登录成功！",Toast.LENGTH_SHORT).show();


                                }else {

                                }

                            }

                            @Override
                            public void onFailure(HttpException e, Response<LoginFedBack> response) {
                                super.onFailure(e, response);


                            }
                        });


            }
        });


        //点击立即注册按钮，跳转到注册页面
        m_tv_LoginNowRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipRegistPage();
            }

            private void skipRegistPage() {
                Intent intent = new Intent(LoginPageActivity.this,RegistPageActivity.class);
                startActivity(intent);
            }

        });

    }
}
