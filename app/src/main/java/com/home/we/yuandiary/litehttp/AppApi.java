package com.home.we.yuandiary.litehttp;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.sip.SipAudioCall;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.home.we.yuandiary.bean.LoginFedBack;
import com.home.we.yuandiary.bean.QA;
import com.home.we.yuandiary.bean.RegistData;
import com.home.we.yuandiary.model.Phone;
import com.home.we.yuandiary.model.QuestionAndAnswer;
import com.litesuits.http.annotation.HttpCacheExpire;
import com.litesuits.http.annotation.HttpCacheMode;
import com.litesuits.http.annotation.HttpID;
import com.litesuits.http.annotation.HttpMethod;
import com.litesuits.http.annotation.HttpUri;
import com.litesuits.http.data.NameValuePair;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.request.BitmapRequest;
import com.litesuits.http.request.JsonAbsRequest;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.HttpBody;
import com.litesuits.http.request.content.StringBody;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.content.multi.MultipartBody;
import com.litesuits.http.request.content.multi.StringPart;
import com.litesuits.http.request.param.CacheMode;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.request.param.HttpRichParamModel;
import com.litesuits.http.response.Response;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by SuSir on  2017/7/20 0020
 * <br>Explanation:接口封装类
 */

public class AppApi {

    private static final String PICTURE_URL = "http://odhoy5nad.bkt.clouddn.com/tu%20%2830%29.jpg";

    //注册
    private static final String REGIST_URL = "http://114.215.238.246/api?padapi=user-userreg.php";

    //问答接口
    private static final String QA_URL = "http://114.215.238.246/api?padapi=questask-asklist.php";


    /**
     * 获取图片接口， 这里的context，如果appcontext继承了baseapplication就可以不用写。
     *
     * @param listener
     */
    public static void getBitmap(@Nullable Context context, @NonNull HttpListener<Bitmap> listener) {
        AppContext.getHttp(context).executeAsync(new BitmapRequest(PICTURE_URL).setHttpListener(listener));
    }

    /**
     * 获取Banner接口  Get 请求时候用这种方式去写
     * 表示请求 获取Banner接口Url 这个地址，
     * 并传递 requestSource 和 showPlace 参数，返回用户 BannerBean 对象。
     * 具体看文档把。
     */
    @HttpUri("http://114.215.238.246/api?padapi=questask-asklist.php")
    @HttpMethod(HttpMethods.Post)
    @HttpID(1)
    @HttpCacheMode(CacheMode.CacheFirst)
    @HttpCacheExpire(value = 1, unit = TimeUnit.MINUTES)
    public static class GetQuestionAndAnswer extends HttpRichParamModel<QuestionAndAnswer> {
        @Override
        protected HttpBody createHttpBody() {
            return super.createHttpBody();

        }
    }



    /**
     *
     * 获取登录返回数据
     */
    @HttpUri(QA_URL)
    @HttpMethod(HttpMethods.Post)
    @HttpID(1)
    @HttpCacheMode(CacheMode.CacheFirst)
    @HttpCacheExpire(value = 1, unit = TimeUnit.MINUTES)
    public static class  QAParam extends HttpRichParamModel<QA> {


        public QAParam() {

        }
    }


    /**
     *
     * 获取问答列表返回数据
     */
    @HttpUri("http://114.215.238.246/api?padapi=login-login.php")
    @HttpMethod(HttpMethods.Post)
    @HttpID(1)
    @HttpCacheMode(CacheMode.CacheFirst)
    @HttpCacheExpire(value = 1, unit = TimeUnit.MINUTES)
    public static class  LoginParam extends HttpRichParamModel<LoginFedBack> {
        private String username;
        private String password;

        public LoginParam(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }







    /**
     * 演示
     *
     * @param name
     * @param listener
     */
    public static void postForRegist(Context context, String name, HttpListener<RegistData> listener, HashMap<String,String> postDatas) {

        MultipartBody body = new MultipartBody();
        Set<Map.Entry<String, String>> entries = postDatas.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String> next = iterator.next();
            String key = next.getKey();
            String value = next.getValue();
            StringPart part = new StringPart(key,value);
            body.addPart(part);
        }



        AppContext.getHttp(context).executeAsync(new JsonAbsRequest<RegistData>(REGIST_URL){


        }
                        .setMethod(HttpMethods.Post)
                        .setHttpBody(body)
                        .addHeader(AppContext.addHttpHeader())
                        .setHttpListener(listener)

        );



    }


}

