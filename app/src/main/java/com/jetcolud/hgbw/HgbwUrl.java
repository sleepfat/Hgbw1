package com.jetcolud.hgbw;

/**
 * Created by Cqing on 2016/12/20.
 */

public class HgbwUrl {

//    public final static String BASE_URL = "http://www.suiedai.com";
    public final static String BASE_URL = "http://192.168.3.7";
    /**
     * get
     * */
    public final static String HOME_DATA_URL = BASE_URL + "/getindexhtml.do";
    /**
     * post
     * */
    public final static String TAKE_FOOD = BASE_URL + "/order/takefood";
    public final static String ORDER = BASE_URL + "/order";
    public final static String FOOD_DETAIL = BASE_URL + "/order/pdetail";

    private HgbwUrl(){}
}