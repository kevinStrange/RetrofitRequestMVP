package com.duke.mvp.manage;

import android.content.Context;
import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.duke.mvp.base.BaseManage;
import com.duke.mvp.bean.LoginBean;
import com.duke.mvp.eventbus.LoginSuccessEvent;
import com.duke.mvp.exception.ApiException;
import com.duke.mvp.interfaces.ILoginListener;
import com.duke.mvp.observer.CommonObserver;
import com.duke.mvp.transformer.CommonTransformer;
import com.duke.mvp.util.FileUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * author : Duke
 * date   : 2018/8/21
 * explain   :登录管理类，主要负责逻辑处理
 * version: 1.0
 */
public class LoginManage extends BaseManage {

    public void login(@NonNull String username, @NonNull String pwd, final Context context,@NonNull final ILoginListener<LoginBean>
            listener) {
        httpService.login(username, pwd)
                .compose(new CommonTransformer<LoginBean>())
                .subscribe(new CommonObserver<LoginBean>(context,true) {

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull LoginBean loginBean) {
                        EventBus.getDefault().post(new LoginSuccessEvent(loginBean));
//                        listener.successInfo(loginBean);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        listener.failInfo(e.message);
                    }
                });
    }

    /**
     * 以json方式提交
     * @param username
     * @param pwd
     * @param listener
     * @return
     */
    public boolean loginToJsonCommit(@NonNull String username, @NonNull String pwd, final Context context,@NonNull final ILoginListener<LoginBean>
            listener) {
        JSONObject root = new JSONObject();
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("param3", "string");
            requestData.put("param4", "string2");
            root.put("param1", "111");
            root.put("param2", "222");
            root.put("data", requestData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), root.toString());
        httpService.login(requestBody)
                .compose(new CommonTransformer<LoginBean>())
                .subscribe(new CommonObserver<LoginBean>(context,false) {

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull LoginBean loginBean) {
                        listener.successInfo(loginBean);
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        listener.failInfo(e.message);
                    }
                });
        return true;
    }

    public void uploadImg( final Context context){
//        Map<String,RequestBody> params = new HashMap<>();
//        //以下参数是伪代码，参数需要换成自己服务器支持的
//        params.put("type", convertToRequestBody("type"));
//        params.put("title",convertToRequestBody("title"));
//
//        //为了构建数据，同样是伪代码
        String path1 = FileUtils.SDPATH + "/sctek/" +  "test.jpg";
//        String path2 = Environment.getExternalStorageDirectory() + File.separator + "test1.jpg";
//        List<File> fileList = new ArrayList<>();
//        fileList.add(new File(path1));
//        fileList.add(new File(path2));
//        List<MultipartBody.Part> partList = filesToMultipartBodyParts(fileList);

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);//表单类型
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), new File(path1));
        builder.addFormDataPart("file", "test.jpg", imageBody);//imgfile 后台接收图片流的参数名
        List<MultipartBody.Part> parts = builder.build().parts();
        httpService.addCase2(parts).compose(new CommonTransformer<LoginBean>())
                .subscribe(new CommonObserver<LoginBean>(context,false) {

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull LoginBean loginBean) {
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                    }
                });

    }
    private RequestBody convertToRequestBody(String param){
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), param);
        return requestBody;
    }

    private List<MultipartBody.Part> filesToMultipartBodyParts(List<File> files) {
        List<MultipartBody.Part> parts = new ArrayList<>(files.size());
        for (File file : files) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("multipartFiles", file.getName(), requestBody);
            parts.add(part);
        }
        return parts;
    }
}
