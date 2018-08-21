package com.duke.mvp.base;

import com.duke.mvp.httprequest.Http;
import com.duke.mvp.httprequest.HttpRequestApi;
import com.duke.mvp.interfaces.IManage;

/**
 * author : Duke
 * date   : 2018/8/21
 * explain   :
 * version: 1.0
 */
public class BaseManage implements IManage {

    protected static HttpRequestApi httpService;

    // 初始化HttpService
    static {
        httpService = Http.getHttpService();
    }
}
