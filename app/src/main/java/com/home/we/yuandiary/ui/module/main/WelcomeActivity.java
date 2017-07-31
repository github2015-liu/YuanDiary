package com.home.we.yuandiary.ui.module.main;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.PopupWindow;

import com.home.we.yuandiary.R;
import com.home.we.yuandiary.litehttp.AppApi;
import com.home.we.yuandiary.litehttp.AppContext;
import com.home.we.yuandiary.model.QuestionAndAnswer;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.request.JsonRequest;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.response.Response;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.jesse.nativelogger.NLogger;

/**
 * Created by pactera on 2017/7/26.
 */

public class WelcomeActivity extends Activity {
    private Button btn_welcome_regist;

    private static final String REGIST_URL = "http://114.215.238.246/api?padapi=user-userreg.php";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        inits();
        regist();

        getUa();




    }



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

        //注册
        btn_welcome_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View pop_View = inflater.inflate(R.layout.popwindow_regist, null);

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


