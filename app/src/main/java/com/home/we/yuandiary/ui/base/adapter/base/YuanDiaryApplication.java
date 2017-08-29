package com.home.we.yuandiary.ui.base.adapter.base;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.home.we.yuandiary.common.util.Constant;
import com.home.we.yuandiary.common.util.SharedUtil;

import cn.jesse.nativelogger.NLogger;
import cn.jesse.nativelogger.formatter.SimpleFormatter;
import cn.jesse.nativelogger.logger.LoggerLevel;
import cn.jesse.nativelogger.logger.base.IFileLogger;
import cn.jesse.nativelogger.util.CrashWatcher;

/**
 * Created by pactera on 2017/7/13.
 */

public class YuanDiaryApplication extends Application {
    private Context mContext;



    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        initLocalLog();
        initSharedPreferences();


    }

    private void initSharedPreferences() {
        SharedUtil.init(mContext);

        if(SharedUtil.getInt("pageNo") == 0) {
            SharedUtil.putInt("pageNo",1);

        }else {
            int newPageNo = SharedUtil.getInt("pageNo");
            SharedUtil.putInt("pageNo",newPageNo);

        }

    }

    private void initLocalLog() {

        Log.d("ljk","initLocalLog");
        NLogger.getInstance()
                .builder()
                .tag("APP")
                .loggerLevel(LoggerLevel.DEBUG)
                .fileLogger(true)
                .fileDirectory("/sdcard/yuanDiary/logs")
                .fileFormatter(new SimpleFormatter())
                .expiredPeriod(3)
                .catchException(true, new CrashWatcher.UncaughtExceptionListener() {
                    @Override
                    public void uncaughtException(Thread thread, Throwable ex) {
                        NLogger.e("uncaughtException", ex);
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                })
                .build();



    }
}
