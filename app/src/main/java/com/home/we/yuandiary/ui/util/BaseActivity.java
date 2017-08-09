package com.home.we.yuandiary.ui.util;


import android.app.Activity;
import android.view.View;

/**
 * Created by pactera on 2017/8/9.
 */

public class BaseActivity extends Activity {
    protected <T extends View> T findView(int id) { return (T)findViewById(id); }


}
