package com.home.we.yuandiary.litehttp;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.request.content.HttpBody;
import com.litesuits.http.request.content.StringBody;
import com.litesuits.http.request.content.UrlEncodedFormBody;
import com.litesuits.http.request.content.multi.MultipartBody;
import com.litesuits.http.request.content.multi.StringPart;
import com.litesuits.http.request.param.CacheMode;
import com.litesuits.http.request.param.HttpMethods;
import com.litesuits.http.request.param.HttpRichParamModel;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * Created by SuSir on  2017/7/20 0020
 * <br>Explanation:接口封装类
 */

public class AppApi {

    private static final String PICTURE_URL = "http://odhoy5nad.bkt.clouddn.com/tu%20%2830%29.jpg";
    private static final String REGIST_URL = "http://114.215.238.246/api?padapi=user-userreg.php";


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
    @HttpMethod(HttpMethods.Get)
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
     * 演示
     *
     * @param name
     * @param listener
     */
    public static void postForRegist(Context context, String name, HttpListener<String> listener, LinkedList<NameValuePair> pList) {
        MultipartBody body = new MultipartBody();
       /* body.addPart(new StringPart("alias", "小宝"));
        body.addPart(new StringPart("username", "18201090103"));
        body.addPart(new StringPart("password", "123456"));
        body.addPart(new StringPart("topassword", "123456"));*/

        pList = new LinkedList<NameValuePair>();
        for (int i = 0; i < pList.size(); i++) {
            String name1 = pList.get(i).getName();
            String value1 = pList.get(i).getValue();
            NameValuePair pair = new NameValuePair(name1,value1);
            pList.add(pair);

        }
        AppContext.getHttp(context).executeAsync(new StringRequest(REGIST_URL)
                .setMethod(HttpMethods.Post)
                //.addUrlParam("","")在url 后面追加参数

                .setHttpBody(new UrlEncodedFormBody(pList))
                .addHeader(AppContext.addHttpHeader())
                .setHttpListener(listener));

    }


}

