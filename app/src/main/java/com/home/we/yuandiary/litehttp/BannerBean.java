package com.home.we.yuandiary.litehttp;

import java.util.List;

/**
 * Created by SuSir on  2017/7/19 0019
 * <br>Explanation:
 */
public class BannerBean {


    /**
     * code : 0
     * message : ok
     * body : {"banners":[{"id":1,"url":"http://cangoonline.com/car/banner/image1"},{"id":2,"url":"http://cangoonline.com/car/banner/image2"},{"id":3,"url":"http://cangoonline.com/car/banner/image3"}]}
     */

    private int code;
    private String message;
    private BodyBean body;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public static class BodyBean {
        private List<BannersBean> banners;

        public List<BannersBean> getBanners() {
            return banners;
        }

        public void setBanners(List<BannersBean> banners) {
            this.banners = banners;
        }

        public static class BannersBean {
            /**
             * id : 1
             * url : http://cangoonline.com/car/banner/image1
             */

            private int id;
            private String url;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
