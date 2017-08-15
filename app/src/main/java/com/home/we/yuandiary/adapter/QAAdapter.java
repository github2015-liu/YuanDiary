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

import com.home.we.yuandiary.R;
import com.home.we.yuandiary.bean.QA;

import java.util.List;

/**
 * Created by pactera on 2017/8/15.
 */

public class QAAdapter extends BaseAdapter {
    private List<QA> mlists;
    private Context mContext;


    public QAAdapter(Context context,List<QA> lists) {
        this.mContext = mContext;
        this.mlists = mlists;
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
        QA qa = mlists.get(position);

        ViewHolder viewHolder;
        View view;
        if(convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.qa_item,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.tv_qa_qname = (TextView) view.findViewById(R.id.tv_qa_qname);
            viewHolder.tv_qa_lable = (TextView) view.findViewById(R.id.tv_qa_lable);
            viewHolder.iv_qa_person_head = (ImageView) view.findViewById(R.id.iv_q_person_head);
            viewHolder.tv_qa_person = (TextView) view.findViewById(R.id.tv_q_person);
            viewHolder.tv_qa_time = (TextView) view.findViewById(R.id.tv_q_time);
            viewHolder.tv_qa_count = (TextView) view.findViewById(R.id.tv_a_count);

            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder

        }
        viewHolder.tv_qa_qname.setText(qa.getQa_qname());
        viewHolder.tv_qa_lable.setText(qa.getQa_lable());
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.ic_launcher);
        viewHolder.iv_qa_person_head.setImageBitmap(bitmap);
        viewHolder.tv_qa_person.setText(qa.getQa_lable());
        viewHolder.tv_qa_time.setText(qa.getQa_lable());
        viewHolder.tv_qa_count.setText(qa.getQa_lable());



        return view;
    }


    class ViewHolder {
        TextView tv_qa_qname;
        TextView tv_qa_lable;
        ImageView iv_qa_person_head;
        TextView tv_qa_person;
        TextView tv_qa_time;
        TextView tv_qa_count;


    }
}
