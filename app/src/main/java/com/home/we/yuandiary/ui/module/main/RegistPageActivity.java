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
import com.home.we.yuandiary.bean.RegistData;
import com.home.we.yuandiary.litehttp.AppApi;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.response.Response;

import java.util.HashMap;

import cn.jesse.nativelogger.NLogger;

import static com.home.we.yuandiary.R.id.et_welcome_pwd;

/**
 * Created by pactera on 2017/11/19.
 */

public class RegistPageActivity extends Activity {

    private Button m_Btn_Regist;
    private EditText m_et_regist_phoneNum;
    private EditText m_et_regist_pass;
    private EditText m_et_regist_sure_pass;

    private TextView m_tv_already_regist;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        initViews();

        m_Btn_Regist = (Button) findViewById(R.id.btn_regist_regist);
        m_et_regist_phoneNum = (EditText) findViewById(R.id.et_regist_phoneNum);
        m_et_regist_pass = (EditText) findViewById(R.id.et_regist_pass);
        m_et_regist_sure_pass = (EditText) findViewById(R.id.et_regist_sure_pass);

        m_tv_already_regist = (TextView) findViewById(R.id.tv_already_regist);

        //已经注册，返回登录按钮
        m_tv_already_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipToLoginPage();
            }

            private void skipToLoginPage() {

                Intent intent = new Intent(RegistPageActivity.this,LoginPageActivity.class);
                startActivity(intent);
            }
        });


        //点击注册按钮处理注册逻辑
        m_Btn_Regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regist();

            }

            private void regist() {

                String s_et_regist_phoneNum = m_et_regist_phoneNum.getText().toString().trim();
                String s_et_regist_pass = m_et_regist_pass.getText().toString().trim();
                String s_et_regist_sure_pass = m_et_regist_sure_pass.getText().toString().trim();




                HashMap<String, String> postDatas = new HashMap<String, String>();
                //postDatas.put("alias","开飞机的舒克");
                postDatas.put("username",s_et_regist_phoneNum);
                postDatas.put("password",s_et_regist_pass);
                postDatas.put("topassword",s_et_regist_sure_pass);





                AppApi.postForRegist(RegistPageActivity.this, null, new HttpListener<RegistData>() {
                    @Override
                    public void onSuccess(RegistData registData, Response<RegistData> response) {
                        super.onSuccess(registData, response);
                        String fedBack_regist_message = registData.getData();
                        //NLogger.d("ljk","注册返回数据\n" + registData.toString());
                        NLogger.d("ljk", response.toString());
                        Toast.makeText(RegistPageActivity.this, fedBack_regist_message,Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onFailure(HttpException e, Response<RegistData> response) {
                        super.onFailure(e, response);
                    }



                },postDatas);
            }
        });
    }

    private void initViews() {

    }
}
