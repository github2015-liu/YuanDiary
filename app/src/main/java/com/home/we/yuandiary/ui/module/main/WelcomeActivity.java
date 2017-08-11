package com.home.we.yuandiary.ui.module.main;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.home.we.yuandiary.R;
import com.home.we.yuandiary.bean.LoginFedBack;
import com.home.we.yuandiary.bean.RegistData;
import com.home.we.yuandiary.common.util.Constant;
import com.home.we.yuandiary.litehttp.AppApi;
import com.home.we.yuandiary.litehttp.AppContext;
import com.home.we.yuandiary.ui.util.BaseActivity;
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
    private Button btn_welcome_login;
    private Button btn_welcome_finish_regist;
    private EditText et_welcome_phoneNum;
    private EditText et_welcome_pwd;
    private EditText et_welcome_name;

    //登录
    private EditText et_login_phoneNum;
    private EditText et_login_pwd;
    private Button btn_pop_login;
    private Button btn_pop_thrid_login;


    private TextView tv_server_fedback;

    private PopupWindow popupWindow;


    private static final String REGIST_URL = "http://114.215.238.246/api?padapi=user-userreg.php";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_welcome);
        inits();

        final LayoutInflater inflater = this.getLayoutInflater();
        //注册
        regist(inflater);

        login(inflater);


    }

    private void login(final LayoutInflater inflater) {


        //点击注册按钮，弹出注册菜单
        btn_welcome_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final View pop_View = inflater.inflate(R.layout.popwindow_login, null);

                et_login_phoneNum = (EditText) pop_View.findViewById(R.id.et_login_phoneNum);
                et_login_pwd = (EditText) pop_View.findViewById(R.id.et_login_pwd);
                btn_pop_login = (Button) pop_View.findViewById(R.id.btn_login);
                btn_pop_thrid_login = (Button) pop_View.findViewById(R.id.btn_thrid_login);

                WindowManager windowManager = getWindowManager();
                int width = windowManager.getDefaultDisplay().getWidth();
                int heigth = windowManager.getDefaultDisplay().getHeight();

                popupWindow = new PopupWindow(pop_View, (int) (width * 0.8), (int) (heigth * 0.4));
                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);

                //给弹出菜单设置背景，如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
                popupWindow.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.welcome_regist_bg)));


                //设置弹出菜单位置
                popupWindow.showAtLocation(pop_View, Gravity.CENTER, 0, 40);
                BackgroudAlpha((float)0.5);

                //点击弹出菜单外的其他部分，弹出菜单消失
                popupWindow.setOnDismissListener(new Popupwindowdismisslistener());





                //点击注册完成按钮
                btn_pop_login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //获取注册信息
                        String s_login_phoneNum = et_login_phoneNum.getText().toString().trim();
                        String s_login_pwd = et_login_pwd.getText().toString().trim();

                        NLogger.d("ljk","用户名" + s_login_phoneNum + "\n密码" + s_login_pwd);


                        AppContext.getHttp(WelcomeActivity.this)
                                .executeAsync(new AppApi.LoginParam(s_login_phoneNum,s_login_pwd))
                                .setHttpListener(new HttpListener<LoginFedBack>() {
                                    @Override
                                    public void onSuccess(LoginFedBack loginFedBack, Response<LoginFedBack> response) {
                                        super.onSuccess(loginFedBack, response);
                                        NLogger.d("ljk","LOGIN onSuccess");
                                        String flag = loginFedBack.getFlag();
                                        NLogger.d("ljk", "登录标识" + flag);

                                        if(flag.equals("001")) { //登录成功
                                            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }

                                    }

                                    @Override
                                    public void onFailure(HttpException e, Response<LoginFedBack> response) {
                                        super.onFailure(e, response);

                                        NLogger.d("ljk","LOGIN onFailure");

                                    }
                                })
                        ;


                    }
                });





                btn_pop_thrid_login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NLogger.d("ljk","第三方登录直接跳转到MainActivity");
                        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                        startActivity(intent);

                    }
                });







            }
        });

    }


    /**
     * 验证注册
     */
    private Handler m_close_popWindow_Handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.arg1 == 5) {
                popupWindow.dismiss();
            }

        }
    };

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

            } else {
                btn_welcome_finish_regist.setEnabled(false);
                btn_welcome_finish_regist.setTextColor(getResources().getColor(R.color.welcome_regist_finish_color));

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
        btn_welcome_login = (Button) findViewById(R.id.btn_welcome_login);
    }



    private void regist(final LayoutInflater inflater) {

        //点击注册按钮，弹出注册菜单
        btn_welcome_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View pop_View = inflater.inflate(R.layout.popwindow_regist, null);

                btn_welcome_finish_regist = (Button) pop_View.findViewById(R.id.btn_welcome_finish_regist);
                et_welcome_phoneNum = (EditText) pop_View.findViewById(R.id.et_welcome_phoneNum);
                et_welcome_pwd = (EditText) pop_View.findViewById(R.id.et_welcome_pwd);
                et_welcome_name = (EditText) pop_View.findViewById(R.id.et_welcome_name);

                tv_server_fedback = (TextView) pop_View.findViewById(R.id.tv_welcome_server_fedback);

                WindowManager windowManager = getWindowManager();
                int width = windowManager.getDefaultDisplay().getWidth();
                int heigth = windowManager.getDefaultDisplay().getHeight();

                popupWindow = new PopupWindow(pop_View, (int) (width * 0.8), (int) (heigth * 0.5));
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
                String s_welcome_phoneNum = et_welcome_phoneNum.getText().toString().trim();
                String s_welcome_pwd = et_welcome_pwd.getText().toString().trim();
                String s_welcome_name = et_welcome_name.getText().toString().trim();







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
                            m_regist_Handler.sendEmptyMessage(0);

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
                        if(temp2.length() >= 1) {
                            Constant.VALIDATE_REGIST2 = 1;
                            NLogger.d("ljk","用户名符合规则");
                            m_regist_Handler.sendEmptyMessage(0);

                        } else {
                            NLogger.d("ljk","用户名不符合规则");
                            Constant.VALIDATE_REGIST2 = 0;
                            m_regist_Handler.sendEmptyMessage(0);

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
                        if(temp3.length() >= 1) {
                            Constant.VALIDATE_REGIST3 = 1;
                            m_regist_Handler.sendEmptyMessage(0);
                            NLogger.d("ljk","密码符合规则");

                        } else {
                            NLogger.d("ljk","密码不符合规则");
                            Constant.VALIDATE_REGIST3 = 0;
                            m_regist_Handler.sendEmptyMessage(0);

                        }


                    }
                });






                //点击注册完成按钮
                btn_welcome_finish_regist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String welcome_phoneNum = et_welcome_phoneNum.getText().toString().trim();
                        String welcome_pwd = et_welcome_pwd.getText().toString().trim();
                        String welcome_name = et_welcome_name.getText().toString().trim();


                        NLogger.d("ljk","注册数据\n" + "welcome_phoneNum" + welcome_phoneNum + "\nwelcome_pwd" + welcome_pwd
                                + "s_welcome_name" + welcome_name);




                        NameValuePair nameValuePair1 =new NameValuePair("alias",welcome_name);
                        NameValuePair nameValuePair2 =new NameValuePair("username",welcome_phoneNum);
                        NameValuePair nameValuePair3 =new NameValuePair("password",welcome_pwd);
                        NameValuePair nameValuePair4 =new NameValuePair("topassword",welcome_pwd);

                        LinkedList<NameValuePair> linkedList = new LinkedList<NameValuePair>();
                        linkedList.add(nameValuePair1);
                        linkedList.add(nameValuePair2);
                        linkedList.add(nameValuePair3);
                        linkedList.add(nameValuePair4);




                        AppApi.postForRegist(WelcomeActivity.this, null, new HttpListener<RegistData>() {
                            @Override
                            public void onSuccess(RegistData registData, Response<RegistData> response) {
                                super.onSuccess(registData, response);
                                String fedBack_regist_message = registData.getData();

                                tv_server_fedback.setVisibility(View.VISIBLE);
                                tv_server_fedback.setText(fedBack_regist_message);

                                //注册成功 1s 后关闭注册window
                               /* Message message = new Message();
                                message.arg1 = 5;
                                m_close_popWindow_Handler.sendMessageDelayed(message,1000);*/
                            }

                            @Override
                            public void onFailure(HttpException e, Response<RegistData> response) {
                                super.onFailure(e, response);
                            }
                        },linkedList);








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


