package com.home.we.yuandiary.ui.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.home.we.yuandiary.R;
import com.home.we.yuandiary.ui.util.MeiTuanHeaderLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by pactera on 2017/7/7.
 */

public class QAFragment extends Fragment {

    public static final String baseUrl = "http://litesuits.com";
    public static final String picUrl = "/mockdata/a.jpg";
    public static final String url = "http://baidu.com";
    public static final String yuanDiaryUrl = "http://114.215.238.246/api?padapi=questask-asklist.php";

    private ListView lv_qa;
    private ArrayAdapter<String> madapter;

    private PullToRefreshScrollView mPullToRefreshScrollView;

    /***********************test**************************/

    private String[] datas = {"apple","Banana","Orange","apple","Banana","Orange"};

    private LinkedList<String> listItems = new LinkedList<>();


    /*************************************************/




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
        //刚开始模拟数据添加
        listItems.addAll(Arrays.asList(datas));
        madapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,listItems);
        lv_qa.setAdapter(madapter);

        //解决ScroolView 嵌套 ListView 只显示一行的问题，ScroolView 无法计算listView 的行高，需要自己设置
        setListViewHeightBasedOnChildren(lv_qa);


        mPullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                new GetDataTask().execute();

                //1.添加数据


                //2.新加载数据
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
        initEvents();


        return view;
    }


    private class GetDataTask extends AsyncTask<Object, Object, String> {

        @Override
        protected String doInBackground(Object... params) {
            // Simulates a background job.
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
            }
            return "this is new fruit";
        }

        @Override
        protected void onPostExecute(String result) {
            // Do some stuff here

            // Call onRefreshComplete when the list has been refreshed.
            listItems.addFirst(result);
            //重新测量listview 的高度
            setListViewHeightBasedOnChildren(lv_qa);
            madapter.notifyDataSetChanged();
            mPullToRefreshScrollView.onRefreshComplete();

            super.onPostExecute(result);
        }
    }

}
