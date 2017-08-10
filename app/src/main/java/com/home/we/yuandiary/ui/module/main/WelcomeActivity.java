package com.home.we.yuandiary.ui.module.main;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.home.we.yuandiary.common.util.Constant;
import com.home.we.yuandiary.litehttp.AppApi;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.response.Response;

import java.util.LinkedList;

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
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_welcome);
        inits();

    }

    /**
     * 验证注册
     */
    private Handler m_regist_Handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(Constant.VALIDATE_REGIST1 == 1 && Constant.VALIDATE_REGIST2 == 1 && Constant.VALIDATE_REGIST3 ==1) {
                btn_welcome_finish_regist.setEnabled(true);
                btn_welcome_finish_regist.setTextColor(getResources().getColor(R.color.welcome_regist_color));

            }

        }
    };

    /**
     * 获取user_agent
     */
    private void getUa() {
        WebView webView = new WebView(this);
        String userAgentString = webView.getSettings().getUserAgentString();
        NLogger.d("ljk", "ua \n" + userAgentString);
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
                popupWindow.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.welcome_regist_bg)));


                //设置弹出菜单位置
                popupWindow.showAtLocation(pop_View, Gravity.CENTER, 0, 40);
                BackgroudAlpha((float)0.5);

                //点击弹出菜单外的其他部分，弹出菜单消失
                popupWindow.setOnDismissListener(new Popupwindowdismisslistener());


                //获取注册信息
                final String s_welcome_phoneNum = et_welcome_phoneNum.getText().toString().trim();
                final String s_welcome_pwd = et_welcome_pwd.getText().toString().trim();
                final String s_welcome_name = et_welcome_name.getText().toString().trim();




                //对注册信息进行验证，电话号码11位，用户名，密码，不为空
                et_welcome_phoneNum.addTextChangedListener(new TextWatcher() {

                    CharSequence temp;

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        temp = s;
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (temp.length() == 11) {
                            Constant.VALIDATE_REGIST1 = 1;
                            NLogger.d("ljk","电话号码符合规则");
                            m_regist_Handler.sendEmptyMessage(0);

                        } else {
                            Constant.VALIDATE_REGIST1 = 0;
                        }

                    }
                });


                et_welcome_name.addTextChangedListener(new TextWatcher() {
                    CharSequence temp2;


                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        temp2 = s;

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if(temp2 != null) {
                            Constant.VALIDATE_REGIST2 = 1;
                            NLogger.d("ljk","用户名符合规则");
                            m_regist_Handler.sendEmptyMessage(0);

                        } else {
                            NLogger.d("ljk","用户名不符合规则");
                            Constant.VALIDATE_REGIST2 = 0;
                        }


                    }
                });


                et_welcome_pwd.addTextChangedListener(new TextWatcher() {
                    CharSequence temp3;


                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        temp3 = s;

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if(temp3 != null) {
                            Constant.VALIDATE_REGIST3 = 1;
                            m_regist_Handler.sendEmptyMessage(0);
                            NLogger.d("ljk","密码符合规则");

                        } else {
                            NLogger.d("ljk","密码不符合规则");
                            Constant.VALIDATE_REGIST3 = 0;
                        }


                    }
                });






                //点击注册完成按钮
 /*               btn_welcome_finish_regist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        boolean regist_ok = false;

                        NLogger.d("ljk","点击完成注册按钮");



                        NameValuePair nameValuePair1 =new NameValuePair("alias",s_welcome_name);
                        NameValuePair nameValuePair2 =new NameValuePair("username",s_welcome_phoneNum);
                        NameValuePair nameValuePair3 =new NameValuePair("password",s_welcome_pwd);
                        NameValuePair nameValuePair4 =new NameValuePair("topassword",s_welcome_pwd);

                        LinkedList<NameValuePair> linkedList = new LinkedList<NameValuePair>();
                        linkedList.add(nameValuePair1);
                        linkedList.add(nameValuePair2);
                        linkedList.add(nameValuePair3);
                        linkedList.add(nameValuePair4);

                        AppApi.postForRegist(WelcomeActivity.this, null, new HttpListener<String>() {
                            @Override
                            public void onSuccess(String s, Response<String> response) {
                                super.onSuccess(s, response);

                            }

                            @Override
                            public void onFailure(HttpException e, Response<String> response) {
                                super.onFailure(e, response);
                            }
                        },linkedList);



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

                        if(s_welcome_phoneNum.length() == 11 && !s_welcome_pwd.equals("") && !s_welcome_name.equals("") &&  (regist_ok == true)) {
                            btn_welcome_finish_regist.setText("注册成功");
                            btn_welcome_finish_regist.setTextColor(getResources().getColor(R.color.welcome_regist_color));
                        }

                    }
                });*/







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


