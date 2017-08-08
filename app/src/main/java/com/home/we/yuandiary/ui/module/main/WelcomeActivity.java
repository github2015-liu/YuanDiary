package com.home.we.yuandiary.ui.module.main;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.home.we.yuandiary.R;
import com.home.we.yuandiary.litehttp.AppApi;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.response.Response;

import cn.jesse.nativelogger.NLogger;

/**
 * Created by pactera on 2017/7/26.
 */

public class WelcomeActivity extends Activity {
    private Button btn_welcome_regist;
    private Button btn_welcome_finish_regist;
    private EditText et_welcome_phoneNum;
    private EditText et_welcome_pwd;
    private EditText et_welcome_name;


    private static final String REGIST_URL = "http://114.215.238.246/api?padapi=user-userreg.php";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        inits();
        regist();

        //getUa();




    }


    /**
     * 获取user_agent
     */
    private void getUa() {
        WebView webView = new WebView(this);
        String userAgentString = webView.getSettings().getUserAgentString();
        NLogger.d("ljk", "ua \n" + userAgentString);
    }

    private void regist() {
        AppApi.postForRegist(this, "1", new HttpListener<String>() {
            @Override
            public void onSuccess(String s, Response<String> response) {
                super.onSuccess(s, response);

                //NLogger.d("ljk", "onSuccess \n" + s);

                NLogger.d("ljk", "onSuccess \n" + response.toString());
            }

            @Override
            public void onFailure(HttpException e, Response<String> response) {
                super.onFailure(e, response);
                NLogger.d("ljk", "onFailure \n" + response.toString());

            }
        });
    }

    private void inits() {
        btn_welcome_regist = (Button) findViewById(R.id.btn_welcome_register);


        final LayoutInflater inflater = this.getLayoutInflater();





        //点击注册按钮，弹出注册菜单
        btn_welcome_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View pop_View = inflater.inflate(R.layout.popwindow_regist, null);

                btn_welcome_finish_regist = (Button) pop_View.findViewById(R.id.btn_welcome_finish_regist);
                et_welcome_phoneNum = (EditText) pop_View.findViewById(R.id.et_welcome_phoneNum);
                et_welcome_pwd = (EditText) pop_View.findViewById(R.id.et_welcome_pwd);
                et_welcome_name = (EditText) pop_View.findViewById(R.id.et_welcome_name);

                WindowManager windowManager = getWindowManager();
                int width = windowManager.getDefaultDisplay().getWidth();
                int heigth = windowManager.getDefaultDisplay().getHeight();

                PopupWindow popupWindow = new PopupWindow(pop_View, (int) (width * 0.8), (int) (heigth * 0.5));
                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);

                //给弹出菜单设置背景，如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

                //设置弹出菜单位置
                popupWindow.showAtLocation(pop_View, Gravity.CENTER, 0, 40);
                BackgroudAlpha((float)0.5);

                //点击弹出菜单外的其他部分，弹出菜单消失
                popupWindow.setOnDismissListener(new Popupwindowdismisslistener());

                //获取注册信息，并完成注册
                final String s_welcome_phoneNum = et_welcome_phoneNum.getText().toString().trim();
                final String s_welcome_pwd = et_welcome_pwd.getText().toString().trim();
                final String s_welcome_name = et_welcome_name.getText().toString().trim();


                if(s_welcome_phoneNum.length() == 11 && !s_welcome_pwd.equals("") && !s_welcome_name.equals("")) {
                    btn_welcome_regist.setEnabled(true);
                    btn_welcome_regist.setTextColor(getResources().getColor(R.color.welcome_regist_color));
                }

                //注册
                btn_welcome_finish_regist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        NLogger.d("ljk","点击完成注册\n" + "s_welcome_phoneNum" + s_welcome_phoneNum + "\ns_welcome_pwd" + s_welcome_pwd
                        + "s_welcome_name" + s_welcome_name);


                        if(s_welcome_phoneNum.length() != 11) {


                            Toast.makeText(WelcomeActivity.this,"请输入正确手机号",Toast.LENGTH_SHORT);
                        }

                        if(s_welcome_pwd.equals("")) {
                            NLogger.d("ljk","密码不能为空");

                        }

                        if(s_welcome_name.equals("")) {
                            NLogger.d("ljk","请输入昵称");

                        }


                    }
                });







            }
        });





    }



    //点击其他部分popwindow消失时，屏幕恢复透明度
    class Popupwindowdismisslistener implements PopupWindow.OnDismissListener{

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            BackgroudAlpha((float)1);
        }




    }

    private void BackgroudAlpha(float alpha) {

        WindowManager.LayoutParams l = WelcomeActivity.this.getWindow().getAttributes();
        l.alpha = alpha;
        getWindow().setAttributes(l);
    }

}


