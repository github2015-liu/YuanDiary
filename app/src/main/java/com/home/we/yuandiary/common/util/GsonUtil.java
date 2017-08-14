package com.home.we.yuandiary.common.util;

import com.google.gson.Gson;

/**
 * Created by pactera on 2017/8/14.
 */

public class GsonUtil {
    public static <T> T parseJsonWithGson(String jsonData, Class<T> type) {
        Gson gson = new Gson();
        T result = gson.fromJson(jsonData, type);
        return gson.fromJson(jsonData, type);
    }
}
