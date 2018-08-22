package com.duke.mvp.dialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;

import com.duke.mvp.util.Lg;

public class LoadingDialog {
    public static void showLoading(Context context) {
        showLoading(context, "请稍候");
    }

    static ProgressDialog progressDialog;

    public static void showLoading(Context context, String s) {
        try {
            // 如果 activity销毁就不show
            if (null == context) {
                return;
            }
            if (((Activity) context).isFinishing()) {
                return;
            }
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(context);
            } else {
                progressDialog.dismiss();
                return;
            }
            progressDialog.setIndeterminate(true);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage(s);
            progressDialog.show();
            progressDialog.getWindow().getDecorView().setTag(System.currentTimeMillis());// 记录当前show的时间戳
        } catch (Exception e) {
            Lg.e(e);
            // 有时候dialog还在显示 可能activity已经要销毁了 就会报错'has leaked window'
        }
    }

    public static void dismissLoading() {
        if (progressDialog != null && progressDialog.getWindow() != null) {
            // 如果loading显示的时间不到1s, 就延迟0.5s消失. 这样dialog就不会闪一下就消失, 避免视觉上的不流畅
            long createTimeMillis = (long) progressDialog.getWindow().getDecorView().getTag();
            if (System.currentTimeMillis() - createTimeMillis < 1000) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // 再次判断dialog是否为null, 可能存在1s的等待期间被销毁的情况
                            if (progressDialog != null) {
                                progressDialog.dismiss();
                                //progressDialog.cancel();
                                progressDialog = null;
                            }
                        } catch (Exception e) {
                            Lg.e(e);
                        }
                    }
                }, 500);
            } else {
                try {
                    progressDialog.dismiss();
                    //progressDialog.cancel();
                    progressDialog = null;
                } catch (Exception e) {
                    Lg.e(e);
                }
            }
        }
    }
}
