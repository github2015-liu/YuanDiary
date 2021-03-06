package com.home.we.yuandiary.ui.fragment;

import android.content.Context;
import android.graphics.pdf.PdfDocument;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.home.we.yuandiary.R;
import com.home.we.yuandiary.adapter.DynamicAdapter;
import com.home.we.yuandiary.adapter.QAAdapter;
import com.home.we.yuandiary.bean.Dynamics;
import com.home.we.yuandiary.bean.QA;
import com.home.we.yuandiary.common.util.Constant;
import com.home.we.yuandiary.common.util.NetworkUtil;
import com.home.we.yuandiary.common.util.SharedUtil;
import com.home.we.yuandiary.litehttp.AppApi;
import com.home.we.yuandiary.litehttp.AppContext;
import com.home.we.yuandiary.ui.util.LruJsonCache;
import com.home.we.yuandiary.ui.util.MeiTuanHeaderLayout;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.response.Response;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cn.jesse.nativelogger.NLogger;
import cn.jesse.nativelogger.logger.base.IFileLogger;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by pactera on 2017/7/7.
 */

public class QAFragment extends Fragment {

    public static QAFragment newInstance(String info) {
        Bundle args = new Bundle();
        QAFragment fragment = new QAFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }



    public static final String baseUrl = "http://litesuits.com";
    public static final String picUrl = "/mockdata/a.jpg";
    public static final String url = "http://baidu.com";
    public static final String yuanDiaryUrl = "http://47.94.245.201/api?padapi=questask-asklist.php";

    private LruJsonCache mlruJsonCache; //缓存框架
    private StringBuilder msbuilder = new StringBuilder();
    private Context mContext;

    private PullToRefreshListView lv_qa;



    private int pageNo = 1;

    /***********************test**************************/
    private ArrayAdapter<String> madapter;


    private String[] datas = {"apple","Banana","Orange","apple","Banana","Orange"};


    private LinkedList<String> listItems = new LinkedList<>();


    /*************************************************/

    private int currentIetm = 0;

    private List<QA.DataBean> dAlists = new ArrayList<>();

    private QAAdapter mqaAdapter;

    //加载更多

    private LayoutInflater mInflater;
    private View mFootView;








    private void initEvents() {
        FragmentActivity activity = getActivity();
        mlruJsonCache = LruJsonCache.get(activity, "DynamicsLruJsonCache"); //初始化缓存框架



        pageNo = SharedUtil.getInt("pageNo");
        if( !NetworkUtil.isNetworkAvailable(getContext())) { //没网从缓存中取数据
            String cacheData = mlruJsonCache.getAsString("qaLists");
            if(cacheData != null) {//如果缓存中有数据，先不访问网络

                NLogger.d("ljk","里面缓存的json是" + cacheData);
                // $是特殊字符需要转义
                String[] jsonStr = cacheData.split("\\$");
                NLogger.d("ljk","缓存json 串长度" + jsonStr.length);
                Gson gson = new Gson();
                for (int i = 0; i < jsonStr.length; i++) {
                    QA dy = gson.fromJson(jsonStr[i], QA.class);
                    List<QA.DataBean> data = dy.getData();
                    dAlists.addAll(data);
                }

                mqaAdapter = new QAAdapter(getContext(),dAlists);
                lv_qa.setAdapter(mqaAdapter);

            }else {
                NLogger.d("ljk","没有缓存数据");
            }

        }else { //有网，直接从网络获取数据
            pageNo = 1;
            NLogger.d("ljk","当前的页数是" + pageNo);
            //获取问答接口数据
            AppContext.getHttp(activity)
                    .executeAsync(new AppApi.QAParam(pageNo).setHttpListener(new HttpListener<QA>() {
                        @Override
                        public void onSuccess(QA qa, Response<QA> response) {
                            super.onSuccess(qa, response);



                            boolean cacheHit = response.isCacheHit();

                            NLogger.d("ljk", "是否使用缓存" + cacheHit);
                            //获取问答数据
                            NLogger.d("ljk" ,"初始刷新onSuccess" + response.toString());

                            if(cacheHit) { //使用缓存
                                return;
                            }

                            List<QA.DataBean> data = qa.getData();  //需展示信息
                            dAlists.addAll(data);
                            mqaAdapter = new QAAdapter(getContext(),dAlists);
                            //NLogger.d("ljk","问答列表集成长度" + qAlists.size());
                            lv_qa.setAdapter(mqaAdapter);


                        }

                        @Override
                        public void onFailure(HttpException e, Response<QA> response) {
                            super.onFailure(e, response);
                            NLogger.d("ljk" ,"初始刷新onFailure" + response.toString());

                        }
                    }));
        }


        lv_qa.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                NLogger.d("ryy","下拉刷新");
                pullDownToRefresh();


            }



            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                NLogger.d("ryy","上拉加载");

                pullUpLoadData();

            }


            private void pullDownToRefresh() {
                int page = 0;
                //获取问答接口数据
                AppContext.getHttp(getContext())
                        .executeAsync(new AppApi.QAParam(page).setHttpListener(new HttpListener<QA>() {
                            @Override
                            public void onSuccess(QA qa, Response<QA> response) {
                                super.onSuccess(qa, response);


                                List<QA.DataBean> data = qa.getData();  //需展示信息

                                boolean dataNull = data.toString().equals("[]");
                                NLogger.d("dataNull" + dataNull);
                                if(!dataNull) {
                                    //dAlists.addAll(data);

                                    mqaAdapter = new QAAdapter(getContext(),data);
                                    //NLogger.d("ljk","问答列表集成长度" + qAlists.size());
                                    //lv_dynamic.setAdapter(mqaAdapter);
                                    mqaAdapter.notifyDataSetChanged();
                                }



                                lv_qa.onRefreshComplete();


                            }

                            @Override
                            public void onFailure(HttpException e, Response<QA> response) {
                                super.onFailure(e, response);
                                NLogger.d("ljk" ,"初始刷新onFailure" + response.toString());

                            }
                        }));

            }

            private void pullUpLoadData() {

                final boolean networkAvailable = NetworkUtil.isNetworkAvailable(getContext());


                pageNo = SharedUtil.getInt("pageNo");
                //NLogger.d("ljk","刷新时的页数是" + pageNo);

                //获取问答接口数据
                AppContext.getHttp(getActivity())
                        .executeAsync(new AppApi.QAParam(pageNo).setHttpListener(new HttpListener<QA>() {
                            @Override
                            public void onSuccess(QA qa, Response<QA> response) {
                                super.onSuccess(qa, response);



                                if(networkAvailable) { //有网,刷新过，刷新页数在本地+1
                                    pageNo = pageNo + 1;
                                    SharedUtil.putInt("pageNo",pageNo);
                                    NLogger.d("刷新完共享参数的页数是" + SharedUtil.getInt("pageNo"));

                                }



                                //获取问答数据
                                NLogger.d("ljk" ,"初始刷新onSuccess" + response.toString());

                                List<QA.DataBean> data = qa.getData();
                                boolean dataNull = data.toString().equals("[]");
                                NLogger.d("dataNull" + dataNull);
                                if(!dataNull) {
                                    dAlists.addAll(data);

                                    mqaAdapter = new QAAdapter(getContext(),dAlists);
                                    //NLogger.d("ljk","问答列表集成长度" + qAlists.size());
                                    lv_qa.setAdapter(mqaAdapter);
                                }



                                lv_qa.onRefreshComplete();

                            }

                            @Override
                            public void onFailure(HttpException e, Response<QA> response) {
                                super.onFailure(e, response);
                                NLogger.d("ljk" ,"初始刷新onFailure" + response.toString());

                            }
                        }));


                if (!networkAvailable) { //刷新结束后，如果网络不可用，提示用户
                    Toast.makeText(getContext(),"请检查您的网络",Toast.LENGTH_SHORT).show();
                }
            }


        });







        //**判断是否滑动到底部
        lv_qa.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }




            /**
             *
             * @param view
             * @param firstVisibleItem 当前能看见的第一个列表项ID（从0开始）
             * @param visibleItemCount 当前能看见的列表项个数（小半个也算）
             * @param totalItemCount 列表项共数
             */

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if(firstVisibleItem == 0) {//刷新时，没有一项需要重新setAdapter
                    currentIetm = firstVisibleItem;
                    NLogger.d("ljk", "刷新时，没有一项需要重新setAdapter");
                }
                NLogger.d("ljk","当前能看见的第一个列表项ID" + firstVisibleItem + "\n当前能看见的列表项个数" + visibleItemCount

                        + "\n列表总数" + totalItemCount);

                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0) {

                    NLogger.d("ljk","已经滑动到底部");

                    Toast.makeText(getActivity(),"已经滑动到底部",Toast.LENGTH_SHORT);

                    loadData();



                }
            }

            private void loadData() {




            }
        });



    }





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_qa, null);
        lv_qa = (PullToRefreshListView) view.findViewById(R.id.lv_qa);

        lv_qa.setMode(PullToRefreshBase.Mode.BOTH);
        //设置PullRefreshListView上提加载时的加载提示
        lv_qa.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载...");
        lv_qa.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载...");
        lv_qa.getLoadingLayoutProxy(false, true).setReleaseLabel("松开加载更多...");
        // 设置PullRefreshListView下拉加载时的加载提示
        lv_qa.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新...");
        lv_qa.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在刷新...");
        lv_qa.getLoadingLayoutProxy(true, false).setReleaseLabel("松开刷新...");

        initEvents();


        return view;

        //NLogger.d("ljk","------------->" + "onCreateView" + "页数" + SharedUtil.getInt("pageNo"));


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NLogger.d("debug");
        NLogger.i("MainActivity", "type1");


        NLogger.zipLogs(new IFileLogger.OnZipListener() {
            @Override
            public void onZip(boolean succeed, String target) {
                if (succeed)
                    NLogger.i("zip", "succeed : " + target);
            }
        });
    }

}
