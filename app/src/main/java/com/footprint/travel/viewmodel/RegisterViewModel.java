package com.footprint.travel.viewmodel;

import android.content.Context;
import android.view.View;

import com.footprint.travel.R;
import com.footprint.travel.activity.RegisterActivity;
import com.footprint.travel.callback.RegisterViewModelCallback;

/**
 * @标题: .java
 * @概述:
 * @作者: Allen
 * @日期: 2016/12/13 @版本：
 */
public class RegisterViewModel implements RegisterViewModelCallback.DestroyCallback{
    public Context context;
    public RegisterActivity activity;
    public RegisterViewModelCallback callback;
    public RegisterViewModel(Context context,RegisterViewModelCallback callback) {
        this.context=context;
        this.callback=callback;
    }
    public void onClickEvent(View view){
        switch (view.getId()){
            case R.id.btn_verify_code://获取验证码
                callback.getVerifyCode();
                break;
            case R.id.btn_register://注册
                callback.registerUser();
                break;
            case R.id.float_button_exit://进入登录界面动画
                callback.floatButtonExit();
                break;

        }
    }

    @Override
    public void destroy() {
        context=null;
        callback=null;
    }
}
