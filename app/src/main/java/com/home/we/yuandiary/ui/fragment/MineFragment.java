package com.home.we.yuandiary.ui.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.home.we.yuandiary.R;


/**
 * Created by pactera on 2017/7/7.
 */

public class MineFragment extends Fragment {
    private TextView tv_common_header;

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

        //设置头部文字


        return view_fragment_mine;
    }

}
