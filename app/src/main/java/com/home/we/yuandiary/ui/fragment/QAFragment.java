package com.home.we.yuandiary.ui.fragment;

import android.graphics.pdf.PdfDocument;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.home.we.yuandiary.R;
import com.home.we.yuandiary.adapter.QAAdapter;
import com.home.we.yuandiary.bean.QA;
import com.home.we.yuandiary.common.util.Constant;
import com.home.we.yuandiary.common.util.NetworkUtil;
import com.home.we.yuandiary.common.util.SharedUtil;
import com.home.we.yuandiary.litehttp.AppApi;
import com.home.we.yuandiary.litehttp.AppContext;
import com.home.we.yuandiary.ui.util.MeiTuanHeaderLayout;
import com.litesuits.http.exception.HttpException;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.response.Response;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cn.jesse.nativelogger.NLogger;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by pactera on 2017/7/7.
 */

public class QAFragment extends Fragment {

    public static final String baseUrl = "http://litesuits.com";
    public static final String picUrl = "/mockdata/a.jpg";
    public static final String url = "http://baidu.com";
    public static final String yuanDiaryUrl = "http://114.215.238.246/api?padapi=questask-asklist.php";

    private ListView lv_qa;

    private PullToRefreshScrollView mPullToRefreshScrollView;
    private int pageNo = 1;

    /***********************test**************************/
    private ArrayAdapter<String> madapter;


    private String[] datas = {"apple","Banana","Orange","apple","Banana","Orange"};


    private LinkedList<String> listItems = new LinkedList<>();


    /*************************************************/

    private int currentIetm = 0;

    private List<QA.DataBean> qAlists = new ArrayList<>();

    private QAAdapter mqaAdapter;




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


    }


    private void initEvents() {


        pageNo = SharedUtil.getInt("pageNo");

        NLogger.d("ljk","当前的页数是" + pageNo);

        //获取问答接口数据
        AppContext.getHttp(getActivity())
            .executeAsync(new AppApi.QAParam(pageNo).setHttpListener(new HttpListener<QA>() {
                @Override
                public void onSuccess(QA qa, Response<QA> response) {
                    super.onSuccess(qa, response);
                    boolean cacheHit = response.isCacheHit();

                    NLogger.d("ljk", "是否使用缓存" + cacheHit);
                    //获取问答数据
                    NLogger.d("ljk" ,"初始刷新onSuccess" + response.toString());

                    List<QA.DataBean> data = qa.getData();
                    qAlists.addAll(data);

                    mqaAdapter = new QAAdapter(getContext(),qAlists);
                    //NLogger.d("ljk","问答列表集成长度" + qAlists.size());
                    lv_qa.setAdapter(mqaAdapter);
                    setListViewHeightBasedOnChildren(lv_qa);


                }

                @Override
                public void onFailure(HttpException e, Response<QA> response) {
                    super.onFailure(e, response);
                    NLogger.d("ljk" ,"初始刷新onFailure" + response.toString());

                }
            }));


        /**
         * 判断是否滑动到底部
         *
         */

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

                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0) {

                    NLogger.d("ljk","已经滑动到底部");
/*
                    Toast.makeText(getActivity(),"已经滑动到底部",Toast.LENGTH_SHORT);
*/
                }
            }
        });


        /**
         * 每次下拉时刷新数据
         */
        mPullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {


                pageNo = SharedUtil.getInt("pageNo");
                NLogger.d("ljk","刷新时的页数是" + pageNo);

                //获取问答接口数据
                AppContext.getHttp(getActivity())
                        .executeAsync(new AppApi.QAParam(pageNo).setHttpListener(new HttpListener<QA>() {
                            @Override
                            public void onSuccess(QA qa, Response<QA> response) {
                                super.onSuccess(qa, response);

                                //有网pageNo + 1

                                boolean networkAvailable = NetworkUtil.isNetworkAvailable(getContext());
                                if(networkAvailable) { //有网,刷新过，刷新页数在本地+1
                                    pageNo = pageNo + 1;
                                    SharedUtil.putInt("pageNo",pageNo);
                                    NLogger.d("刷新完共享参数的页数是" + SharedUtil.getInt("pageNo"));

                                }


                                boolean cacheHit = response.isCacheHit();

                                NLogger.d("ljk", "是否使用缓存" + cacheHit);
                                //获取问答数据
                                NLogger.d("ljk" ,"初始刷新onSuccess" + response.toString());

                                List<QA.DataBean> data = qa.getData();
                                qAlists.addAll(data);

                                mqaAdapter = new QAAdapter(getContext(),qAlists);
                                //NLogger.d("ljk","问答列表集成长度" + qAlists.size());
                                lv_qa.setAdapter(mqaAdapter);
                                setListViewHeightBasedOnChildren(lv_qa);


                            }

                            @Override
                            public void onFailure(HttpException e, Response<QA> response) {
                                super.onFailure(e, response);
                                NLogger.d("ljk" ,"初始刷新onFailure" + response.toString());

                            }
                        }));


                mPullToRefreshScrollView.onRefreshComplete();


            }


        });
    }

    private void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qa, null);
        lv_qa = (ListView) view.findViewById(R.id.lv_qa);
        mPullToRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.qa_scrollview);
        mPullToRefreshScrollView.setHeaderLayout(new MeiTuanHeaderLayout(getActivity()));


        //
        NLogger.d("ljk","------------->" + "onCreateView" + "页数" + SharedUtil.getInt("pageNo"));
        initEvents();


        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
