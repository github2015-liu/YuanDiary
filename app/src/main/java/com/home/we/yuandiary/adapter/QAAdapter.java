package com.home.we.yuandiary.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.home.we.yuandiary.R;
import com.home.we.yuandiary.bean.QA;

import java.util.List;

import cn.jesse.nativelogger.NLogger;

/**
 * Created by pactera on 2017/8/15.
 */

public class QAAdapter extends BaseAdapter {
    private Context mContext;
    private List<QA.DataBean> mlists;


    public QAAdapter(Context context,List<QA.DataBean> lists) {
        this.mContext = context;
        this.mlists = lists;
    }

    @Override
    public int getCount() {
        return mlists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        QA.DataBean dataBean = mlists.get(position);

        ViewHolder viewHolder;
        View view;
        if(convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.qa_item,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.tv_qa_qname = (TextView) view.findViewById(R.id.tv_qa_qname);
            viewHolder.iv_qa_person_head = (ImageView) view.findViewById(R.id.iv_q_person_head);
            viewHolder.iv_qa_pic = (ImageView) view.findViewById(R.id.iv_qa_pic);
            viewHolder.tv_qa_person = (TextView) view.findViewById(R.id.tv_q_person);
            viewHolder.tv_qa_time = (TextView) view.findViewById(R.id.tv_q_time);
            viewHolder.tv_qa_count = (TextView) view.findViewById(R.id.tv_a_count);

            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder

        }

        //问题
        viewHolder.tv_qa_qname.setText(dataBean.getIssue());
        String head_pic_url = dataBean.getAvatar(); //图片路径
        //利用Glide 从网络加载图片
        Glide.with(mContext).load(head_pic_url).into(viewHolder.iv_qa_person_head);
        viewHolder.tv_qa_person.setText(dataBean.getAlias());
        viewHolder.tv_qa_time.setText("提问于" + dataBean.getTm());
        //回答数
        int answers = dataBean.getAnswers();
        //NLogger.d("ljk", "回答数" + answers);
        viewHolder.tv_qa_count.setText("回答" + answers);

        //问答中的图片
        String pic = dataBean.getPic();
        Glide.with(mContext).load(pic).into(viewHolder.iv_qa_pic);



        return view;
    }


    class ViewHolder {
        TextView tv_qa_qname;
        ImageView iv_qa_person_head;
        TextView tv_qa_person;
        TextView tv_qa_time;
        TextView tv_qa_count;
        ImageView iv_qa_pic;



    }
}
