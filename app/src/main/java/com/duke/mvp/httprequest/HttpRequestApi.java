package com.duke.mvp.httprequest;


import com.duke.mvp.base.BaseHttpResult;
import com.duke.mvp.bean.LoginBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 网络请求的API
 */
public interface HttpRequestApi {

    /**
     * 表单方式提交
     *
     * @param username 登录用户名
     * @param pwd      登录密码
     * @return
     */
    @FormUrlEncoded
    @POST("v2/590b00f3290000de0723d9c3")
    Observable<BaseHttpResult<LoginBean>> login(@Field("username") String username,
                                                @Field("password") String pwd);


    /**
     * json 方式提交 登录接口 Your request address
     *
     * @param requestBody
     * @return
     */
    @POST("v2/590b00f3290000de0723d9c3")
    Observable<BaseHttpResult<LoginBean>> login(@Body RequestBody requestBody);

    @Multipart
    @POST("qwe/asd")
    Observable<BaseHttpResult<LoginBean>> addCase2(@Part List<MultipartBody.Part> partList);
}
