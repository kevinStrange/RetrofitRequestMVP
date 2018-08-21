package com.duke.mvp.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.Toast;
import com.zhy.autolayout.AutoLayoutActivity;
/**
 * 父类的Activity
 */
public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {

    public Context mContext;//上下文，只要是继承BaseActivity的Activity都可以调用该上下文

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mContext = this;
        setContentView(getView());
        init();//可以先初始化一些对象类
        initView();//初始化view视图id 可通过调用
        initData();//加载数据
    }

    /**
     * 用于加载 layout 界面布局
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化一些对象
     */
    protected abstract void init();

    /**
     * 初始化视图布局&ID
     */
    protected abstract void initView();

    /**
     * 初始化加载数据
     */
    protected abstract void initData();

    /**
     * 做点击界面动作
     *
     * @param view
     */
    protected abstract void otherViewClick(View view);

    /**
     * @return 显示的内容
     */
    public View getView() {
        return View.inflate(this, getLayoutId(), null);
    }

    /**
     * 设置点击事件
     *
     * @param onClickListener
     * @param ids
     */
    protected void setOnClickListener(View.OnClickListener onClickListener, int... ids) {
        for (int id : ids) {
            super.findViewById(id).setOnClickListener(onClickListener);
        }
    }

    /**
     * 设置点击事件
     *
     * @param onClickListener
     * @param views
     */
    protected void setOnClickListener(View.OnClickListener onClickListener, View... views) {
        for (View view : views) {
            view.setOnClickListener(onClickListener);
        }
    }

    /**
     * toast短 提示
     *
     * @param msg
     */
    protected void toastShort(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * toast长 提示
     *
     * @param msg
     */
    protected void toastLong(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 跳转Activity
     *
     * @param classz
     */
    protected void startActivity(Class classz) {
        Intent intent = new Intent(this, classz);
        startActivity(intent);
    }


    /**
     * 用于查找布局中的ID
     *
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }

    /**
     * 点击的事件的统一的处理
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                otherViewClick(view);
                break;
        }
    }
}
