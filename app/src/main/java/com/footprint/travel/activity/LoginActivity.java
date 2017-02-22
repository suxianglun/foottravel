package com.footprint.travel.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.footprint.travel.R;
import com.footprint.travel.base.BaseActivity;
import com.footprint.travel.databinding.ActivityLoginBinding;
import com.footprint.travel.entity.User;
import com.footprint.travel.utils.LogUtils;
import com.footprint.travel.utils.RegexUtils;
import com.footprint.travel.utils.StringUtils;
import com.footprint.travel.utils.ToastUtils;
import com.footprint.travel.viewmodel.LoginViewModel;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

/**
 * @标题: LoginActivity.java
 * @概述:
 * @作者: Allen
 * @日期: 2016/10/13 @版本：
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{
    public LoginViewModel loginViewModel;
    public ActivityLoginBinding activityLoginBinding;
    public String phoneNum;
    public String passWord;
    @Override
    protected void initVariables() {

    }

    @Override
    protected void initDataBinding() {
        activityLoginBinding= DataBindingUtil.setContentView(this,R.layout.activity_login);
        loginViewModel=new LoginViewModel(LoginActivity.this);
        activityLoginBinding.setLoginViewModel(loginViewModel);
    }
    @Override
    protected void initView() {
        initToolBar(R.string.login,true);
        activityLoginBinding.textInputEtPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length()<6){
                        activityLoginBinding.textInputEtPwd.setError("必须在6-20位长度之间",null);
                    }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        activityLoginBinding.floatButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**动画进入注册页面*/
                startRegisterWithAnimation();
            }
        });
        activityLoginBinding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canLogin()){
                    loginByPhonePwd();
                }
            }
        });
    }
    /**校验登录表单*/
   public boolean canLogin(){
       phoneNum=activityLoginBinding.textInputEtPhone.getText().toString();
       passWord=activityLoginBinding.textInputEtPwd.getText().toString();
       boolean canLogin;
       if (StringUtils.isEmpty(phoneNum)||!RegexUtils.isMobileExact(phoneNum)){
           activityLoginBinding.textInputEtPhone.setError("手机号输入不正确，请重试",null);
           canLogin=false;
           LogUtils.e(TAG,"canLogin1==--------->"+canLogin);
           return canLogin;
       }else if (StringUtils.isEmpty(passWord)||passWord.length()<6){
           activityLoginBinding.textInputEtPwd.setError("密码格式不正确，请重试",null);
           canLogin=false;
           LogUtils.e(TAG,"canLogin2==--------->"+canLogin);
           return canLogin;
       }
       canLogin=true;
       LogUtils.e(TAG,"canLogin==--------->"+canLogin);
       return canLogin;
   }
    private void loginByPhonePwd(){
        addSubscription(User.loginByAccount(phoneNum,passWord, new LogInListener<User>() {

            @Override
            public void done(User user, BmobException e) {
                if(user!=null){
                    ToastUtils.show(LoginActivity.this,"登录成功");
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                }else{
                    loge(e);
                }
            }
        }));
    }
    @Override
    public void onClick(View v) {

    }
    /**动画进入注册页面*/
    private void startRegisterWithAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setExitTransition(null);
            getWindow().setEnterTransition(null);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,
                    activityLoginBinding.floatButtonRegister,
                    activityLoginBinding.floatButtonRegister.getTransitionName());
            startActivity(new Intent(this, RegisterActivity.class), options.toBundle());
        } else {
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }
}
