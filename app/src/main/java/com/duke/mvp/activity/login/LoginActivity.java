package com.duke.mvp.activity.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.duke.mvp.R;
import com.duke.mvp.base.BaseActivity;
import com.duke.mvp.bean.LoginBean;
import com.duke.mvp.eventbus.LoginSuccessEvent;
import com.duke.mvp.interfaces.ILoginListener;
import com.duke.mvp.manage.LoginManage;
import com.duke.mvp.util.FileUtils;
import com.duke.mvp.util.Lg;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseActivity implements ILoginListener {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };

    private EditText etAccount, etPassword;
    private ImageView ivAccountDet, ivPasswordDet, ivPswControl;
    private TextView tvLogin;
    LoginManage loginManage;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void init() {
        EventBus.getDefault().register(this);
        FileUtils.init();
        verifyStoragePermissions(this);
//        Observable.timer(5, TimeUnit.SECONDS)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(@NonNull Long aLong) throws Exception {
        loginManage = new LoginManage();

//                    }
//                });


    }

    /**
     * 动态申请权限
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void initView() {
        etAccount = findView(R.id.etAccount);
        etPassword = findView(R.id.etPwd);
        ivAccountDet = findView(R.id.ivAccountDet);
        ivPasswordDet = findView(R.id.ivPasswordDet);
        ivPswControl = findView(R.id.psw_control);
        tvLogin = findView(R.id.btnLogin);
        tvLogin.setTag(false);
        setOnClickListener(this, ivAccountDet, ivPasswordDet, ivPswControl);
        setOnClickListener(this, R.id.btnLogin, R.id.stayIn, R.id.retrievePassword);
        etAccount.addTextChangedListener(new EditTextChangeListener(ivAccountDet));
        etPassword.addTextChangedListener(new EditTextChangeListener(ivPasswordDet));
        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ivPswControl.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
            }
        });

//        String account = S.getInstance().getUserAccount();
//        if (!TextUtils.isEmpty(account)) {
//            etAccount.setText(account);
//            etPassword.requestFocus();
//        }
    }
    class EditTextChangeListener implements TextWatcher {

        ImageView clearView;

        EditTextChangeListener(ImageView clearView) {
            this.clearView = clearView;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            if (s.toString().length() == 0) {
                clearView.setVisibility(View.GONE);
            } else {
                clearView.setVisibility(View.VISIBLE);
            }
            //判断输入框输入的内容跟换登录按钮的颜色。
            if (etAccount.getText().length() != 0 && etPassword.getText().length() != 0) {
                tvLogin.setBackgroundResource(R.drawable.bt_canclick);
                tvLogin.setTag(true);
            } else {
                tvLogin.setBackgroundResource(R.drawable.bt_disableclick);
                tvLogin.setTag(false);
            }

        }
    }


    /**
     * 校验账号和密码的长度和规则，无需做空值校验
     *
     * @param account
     * @param password
     * @return
     */

    private boolean checkInput(String account, String password) {

        if (account.length() != 11) {
            toastShort("请输入11位手机号码");
            return false;
        }
        if (!account.startsWith("1")) {
            toastShort("请输入11位手机号码");
            return false;
        }
        if (password.length() < 6) {
            toastShort("密码为6位数以上");
            return false;
        }
        return true;
    }
    @Override
    protected void initData() {

    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.ivAccountDet:
                etAccount.setText("");
                break;
            case R.id.ivPasswordDet:
                etPassword.setText("");
                break;
            //登录
            case R.id.btnLogin:
                TextView loginBt = (TextView) view;
                boolean clickAble = (boolean) loginBt.getTag();
                String account = etAccount.getText().toString();
                String password = etPassword.getText().toString();
                if (clickAble && checkInput(account, password)) {
                    loginManage.login(account,password,mContext,this);
                }
                break;
            //入驻
            case R.id.stayIn:

                break;
            // 找回密码
            case R.id.retrievePassword:

                break;
            // 显示与隐藏密码
            case R.id.psw_control:
                passWordShowControl();
                break;
        }
    }

    private void passWordShowControl() {

        boolean isSelect = ivPswControl.isSelected();
        if (!isSelect) {
            ivPswControl.setSelected(true);
            etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            ivPswControl.setSelected(false);
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                    InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        etPassword.setSelection(etPassword.getText().length());
    }
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onLoginSuccessEvent(LoginSuccessEvent event) {
        Lg.d("onLoginSuccessEvent"+event.getLoginBean().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void successInfo(LoginBean loginBean) {

    }

    @Override
    public void failInfo(String result) {

    }
}
