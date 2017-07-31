package com.home.we.yuandiary.ui.fragment;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.home.we.yuandiary.R;
import com.home.we.yuandiary.litehttp.AppApi;
import com.home.we.yuandiary.litehttp.AppContext;
import com.home.we.yuandiary.litehttp.BannerBean;
import com.home.we.yuandiary.model.Phone;
import com.home.we.yuandiary.model.QuestionAndAnswer;
import com.home.we.yuandiary.ui.util.MeiTuanHeaderLayout;
import com.litesuits.http.LiteHttp;
import com.litesuits.http.data.GsonImpl;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.impl.huc.HttpUrlClient;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.request.AbstractRequest;
import com.litesuits.http.request.BitmapRequest;
import com.litesuits.http.request.JsonRequest;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.response.Response;
import com.litesuits.http.utils.HttpUtil;

import cn.jesse.nativelogger.NLogger;


/**
 * Created by pactera on 2017/7/7.
 */

public class QAFragment extends Fragment {

    public static final String baseUrl = "http://litesuits.com";
    public static final String picUrl = "/mockdata/a.jpg";
    public static final String url = "http://baidu.com";
    public static final String yuanDiaryUrl = "http://114.215.238.246/api?padapi=questask-asklist.php";

    protected static LiteHttp liteHttp;


    private PullToRefreshScrollView mPullToRefreshScrollView;


    public static QAFragment newInstance(String info) {
        Bundle args = new Bundle();
        QAFragment fragment = new QAFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initLiteHttp();
        getDemo();


    }

    /**
     * 单例 keep an singleton instance of litehttp
     */
    private void initLiteHttp() {

        AppApi.getBitmap(getContext(), new HttpListener<Bitmap>(false, true, false) {
            @Override
            public void onSuccess(Bitmap bitmap, Response<Bitmap> response) {
                super.onSuccess(bitmap, response);
                Log.i("DXY", "onSuccess: 成功");
            }

            @Override
            public void onFailure(HttpException e, Response<Bitmap> response) {
                super.onFailure(e, response);
                Log.i("DXY", "onSuccess: 失败");
            }

            @Override
            public void onLoading(AbstractRequest<Bitmap> request, long total, long len) {
                super.onLoading(request, total, len);
                Log.i("DXY", "onSuccess: 下载中..." + "total>>>>" + total + "<<<<<len>>>>" + len);
            }
        });





    }


    public void getDemo() {
        // TODO: 缺少RUL
        AppContext.getHttp(getContext()).executeAsync(new JsonRequest<QuestionAndAnswer>(new AppApi.GetQuestionAndAnswer(), QuestionAndAnswer.class)
                .setHttpListener(new HttpListener<QuestionAndAnswer>() {
                    @Override
                    public void onSuccess(QuestionAndAnswer bannerBean, Response<QuestionAndAnswer> response) {
                        super.onSuccess(bannerBean, response);
                        Log.i("DXY", "onSuccess: PostDemo " + bannerBean.getData().get(0).getPicstr());
                    }

                    @Override
                    public void onFailure(HttpException e, Response<QuestionAndAnswer> response) {
                        super.onFailure(e, response);
                    }
                }));

    }


    private void initEvents() {
        mPullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                new GetDataTask().execute();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qa, null);


        mPullToRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.main_act_scrollview);
        mPullToRefreshScrollView.setHeaderLayout(new MeiTuanHeaderLayout(getActivity()));
        initEvents();


        return view;
    }


    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            // Do some stuff here

            // Call onRefreshComplete when the list has been refreshed.
            mPullToRefreshScrollView.onRefreshComplete();

            super.onPostExecute(result);
        }
    }

}
