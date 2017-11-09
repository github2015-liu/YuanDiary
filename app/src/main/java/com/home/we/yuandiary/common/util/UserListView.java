package com.home.we.yuandiary.common.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by pactera on 2017/11/8.
 */

public class UserListView extends ListView {

    public UserListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UserListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    //重写该方法，达到使ListView适应ScrollView的效果
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
