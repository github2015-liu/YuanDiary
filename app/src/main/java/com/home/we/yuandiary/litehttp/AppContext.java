package com.home.we.yuandiary.litehttp;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

import com.litesuits.http.LiteHttp;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import cn.jesse.nativelogger.NLogger;

/**
 * Created by SuSir on  2017/7/20 0020
 * <br>Explanation:
 */
public class AppContext {   //这里应该继承baseApplication


    private static LiteHttp mHttp;
    private static  String VERSION_NAME = "";
    private static  String DEVICE_UUID = "";
    private static  String PLANTFORM = "";
    private static Context mContext;



    /**
     * 初始化
     */
    private static void init(Context context) { //这里的context 应该从baseapplication传过来


        mHttp = LiteHttp.build(context)
                .setDebugged(true)                   // log output when debugged
                .setDetectNetwork(true)              // detect network before connect
                .setDoStatistics(true)               // statistics of time and traffic
                .setUserAgent("Mozilla/5.0 (...)")
                .setDefaultMaxRetryTimes(0)
                // set custom User-Agent
                .setSocketTimeout(10000)
                .setConnectTimeout(10000)
                .create();
        // connect and socket timeout: 10s

        mContext = context;

        //获取应用名称+版本号|UUID|设备型号
        getDeviceInfos();

    }

    private static void getDeviceInfos() {
        VERSION_NAME = getVersionName();
        DEVICE_UUID = getUuid();
        PLANTFORM = android.os.Build.MODEL + android.os.Build.VERSION.SDK_INT;
        NLogger.d("ljk","\nVERSION_NAME " + VERSION_NAME +  "\nPLANTFORM "  + PLANTFORM + "\nDEVICE_UUID " + DEVICE_UUID);


    }

    //获取UUID
    private static String getUuid() {

        String res = "";
        final TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, tmPhone, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(mContext.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        res = deviceUuid.toString();

        return res;
    }

    private static String getVersionName() {
        String res = "";
        PackageManager packageManager = mContext.getApplicationContext().getPackageManager();
        try {
            String packageName = mContext.getPackageName();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            //应用版本号
            String version = packageInfo.versionName;
            //应用名称
            String applicationName = (String) packageManager.getApplicationLabel(applicationInfo);
            res = applicationName + version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        return  res;
    }

    /**
     * 单例
     */
    public static LiteHttp getHttp(Context context) {
        if (mHttp != null) {
            return mHttp;
        } else {
            init(context);
            return mHttp;
        }
    }

    /**
     * 获取http请求的Header信息
     * @return
     */
    public static Map<String ,String> addHttpHeader(){
        Map<String,String> map =new HashMap<>();
        map.put("version",VERSION_NAME);
        map.put("uuid",DEVICE_UUID);
        map.put("plantform",PLANTFORM);
        //map.put("ua_anent","Mozilla/5.0 (Linux; Android 5.1.1; SM-G9280 Build/LMY47X; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/43.0.2357.121 Mobile Safari/537.36");

        return map;
    }



}
