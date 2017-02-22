package com.footprint.travel.viewmodel;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;

import com.footprint.travel.R;
import com.footprint.travel.activity.LoginActivity;
import com.footprint.travel.activity.RegisterActivity;
import com.footprint.travel.databinding.ActivityLoginBinding;

/**
 * @标题: .java
 * @概述:
 * @作者: Allen
 * @日期: 2016/12/13 @版本：
 */
public class LoginViewModel {
    public Context context;
    public LoginViewModel(Context context) {
        this.context=context;
    }

    public void onClickEvent(View view) {
        switch (view.getId()) {
            case R.id.float_button_register:
                break;
            case R.id.button_login:
                break;
        }
    }


}
