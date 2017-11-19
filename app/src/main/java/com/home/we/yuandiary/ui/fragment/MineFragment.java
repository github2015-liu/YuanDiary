package com.home.we.yuandiary.ui.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.home.we.yuandiary.R;
import com.home.we.yuandiary.adapter.QAAdapter;
import com.home.we.yuandiary.bean.LoginPersonInfo;
import com.home.we.yuandiary.bean.QA;
import com.home.we.yuandiary.common.util.SharedUtil;
import com.home.we.yuandiary.litehttp.AppApi;
import com.home.we.yuandiary.litehttp.AppContext;
import com.home.we.yuandiary.ui.module.main.LoginPageActivity;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.response.Response;

import java.util.List;

import cn.jesse.nativelogger.NLogger;


/**
 * Created by pactera on 2017/7/7.
 */

public class MineFragment extends Fragment {
    private TextView tv_common_header;

    private String myuser_info = "http://47.94.245.201/api?padapi=user-myinfo.php";

    private LinearLayout mll_person_info;

    public static MineFragment newInstance(String info) {
        Bundle args = new Bundle();
        MineFragment fragment = new MineFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        //文字设置失效
        View view_common_header = inflater.inflate(R.layout.common_header, null);
        TextView tv_common_header = (TextView) view_common_header.findViewById(R.id.tv_common_header);
        tv_common_header.setText("我的");
        //设置头部
        View view_fragment_mine = inflater.inflate(R.layout.fragment_mine, null);


        mll_person_info = (LinearLayout) view_fragment_mine.findViewById(R.id.ll_person_info);

        // 点击个人信息，判断是否已登录
        mll_person_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //获取问答接口数据
                AppContext.getHttp(getActivity())
                        .executeAsync(new AppApi.PersonInfoParam().setHttpListener(new HttpListener<LoginPersonInfo>() {
                            @Override
                            public void onSuccess(LoginPersonInfo qa, Response<LoginPersonInfo> response) {
                                super.onSuccess(qa, response);
                                
                                if(qa != null) {
                                    String data = qa.getData();

                                    if(data.equals("未登录")) {
                                        // 跳转到登录页面
                                        skipLoginPage();


                                    }else {

                                    }

                                }else {
                                    
                                }



                            



                             

                            }



                            @Override
                            public void onFailure(HttpException e, Response<LoginPersonInfo> response) {
                                super.onFailure(e, response);
                                NLogger.d("ljk" ,"初始刷新onFailure" + response.toString());

                            }

                            private void skipLoginPage() {
                                Intent intent = new Intent(getActivity(),LoginPageActivity.class);
                                startActivity(intent);

                            }
                        }));

            }
        });


        //设置头部文字

        return view_fragment_mine;
    }



}
