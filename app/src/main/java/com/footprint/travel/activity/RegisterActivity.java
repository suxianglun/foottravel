package com.footprint.travel.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.TextAppearanceSpan;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;

import com.footprint.travel.R;
import com.footprint.travel.base.BaseActivity;
import com.footprint.travel.callback.RegisterViewModelCallback;
import com.footprint.travel.databinding.ActivityRegisterBinding;
import com.footprint.travel.entity.User;
import com.footprint.travel.utils.IdWorker;
import com.footprint.travel.utils.LogUtils;
import com.footprint.travel.utils.RegexUtils;
import com.footprint.travel.utils.StringUtils;
import com.footprint.travel.utils.ToastUtils;
import com.footprint.travel.viewmodel.RegisterViewModel;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * @标题: .java
 * @概述:
 * @作者: Allen
 * @日期: 2016/12/13 @版本：
 */
public class RegisterActivity extends BaseActivity implements RegisterViewModelCallback{
    public ActivityRegisterBinding activityRegisterBinding;
    public RegisterViewModel registerViewModel;
    public RegisterViewModelCallback callback=this;
    public String phone;
    public String code;
    public String password;
    public String passwordConfirm;
    @Override
    protected void initVariables() {

    }
    @Override
    protected void initDataBinding() {
        activityRegisterBinding= DataBindingUtil.setContentView(this,R.layout.activity_register);
        registerViewModel=new RegisterViewModel(RegisterActivity.this,this);
        activityRegisterBinding.setRegisterViewModel(registerViewModel);

    }
    @Override
    protected void initView() {
        initToolBar(R.string.register,true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ShowEnterAnimation();
        }
        /**手机号、验证码、登录密码、确认密码输入提示*/
        textInputTip();
    }
    /**手机号、验证码、登录密码、确认密码输入提示*/
    private void textInputTip() {
        /**手机号输入提示*/
        activityRegisterBinding.etRegisterPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!RegexUtils.isMobileExact(s.toString())){
                    activityRegisterBinding.etRegisterPhone.setError("手机号码不正确");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        /**手机号输入提示*/
        activityRegisterBinding.etRegisterCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()<6){
                    activityRegisterBinding.etRegisterCode.setError("长度必须为6位",null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        /**密码输入提示*/
        activityRegisterBinding.etRegisterPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()<6){
                    activityRegisterBinding.etRegisterPassword.setError("长度必须在6-20位之间",null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /**确认密码输入提示*/
        activityRegisterBinding.etRegisterPwdConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password=activityRegisterBinding.etRegisterPassword.getText().toString();
                if (s.length()<6){
                    activityRegisterBinding.etRegisterPwdConfirm.setError("长度必须在6-20位之间",null);
                }else if (!password.equals(s.toString())){
                    activityRegisterBinding.etRegisterPwdConfirm.setError("两次密码输入不一致",null);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void getVerifyCode(){
        if (canGetVerifyCode()){
            initTimeCounter();
            findBmobUser();
        }
    }
    /**
     * 倒计时
     */
    private void initTimeCounter() {
        CountDownTimer timer=new CountDownTimer(60*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                activityRegisterBinding.btnVerifyCode.setEnabled(false);
                String time = millisUntilFinished / 1000 + "s";
                //处理汉字和英文字母大小不一致
                SpannableString spannableString = new SpannableString(time);
                if (time.length() == 3) {
                    spannableString.setSpan(new TextAppearanceSpan(RegisterActivity.this, R.style.style_timer_large),
                            0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString.setSpan(new TextAppearanceSpan(RegisterActivity.this, R.style.style_timer_small),
                            2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    spannableString.setSpan(new TextAppearanceSpan(RegisterActivity.this, R.style.style_timer_large),
                            0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString.setSpan(new TextAppearanceSpan(RegisterActivity.this, R.style.style_timer_small),
                            1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                activityRegisterBinding.btnVerifyCode.setText(String.valueOf(spannableString));
            }

            @Override
            public void onFinish() {
                activityRegisterBinding.btnVerifyCode.setEnabled(true);
                activityRegisterBinding.btnVerifyCode.setText(getResources().getString(R.string.get_code));
            }
        };
        timer.start();

    }

    /**
     * 查询用户是否存在
     */
    private void findBmobUser() {
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("mobilePhoneNumber", phone);
        addSubscription(query.findObjects(new FindListener<User>() {

            @Override
            public void done(List<User> object, BmobException e) {
                if(e==null){
                    if (object.size()!=0){
                        ToastUtils.show(RegisterActivity.this,"该手机号已被注册：" + object.size());
                    }else{
                        sendVerifyCode();
                    }
                }else{
                    loge(e);
                }
            }

        }));
    }
    /**获取验证码注册表单校验*/
    private boolean canGetVerifyCode(){
        boolean canGetVerifyCode;
        phone=activityRegisterBinding.etRegisterPhone.getText().toString();
        if (StringUtils.isEmpty(phone)){
            activityRegisterBinding.etRegisterPhone.setError("手机号不能为空",null);
            canGetVerifyCode=false;
            LogUtils.e(TAG,"canGetVerifyCode1=="+canGetVerifyCode);
            return canGetVerifyCode;
        }else if (!RegexUtils.isMobileExact(phone)){
            activityRegisterBinding.etRegisterPhone.setError("手机号格式不正确",null);
            canGetVerifyCode=false;
            LogUtils.e(TAG,"canGetVerifyCode2=="+canGetVerifyCode);
            return canGetVerifyCode;
        }
        canGetVerifyCode=true;
        LogUtils.e(TAG,"canGetVerifyCode=="+canGetVerifyCode);
        return canGetVerifyCode;
    }
    /**注册表单校验*/
    private boolean canRegisterUser(){
        boolean canRegisterUser;
        phone=activityRegisterBinding.etRegisterPhone.getText().toString();
        code=activityRegisterBinding.etRegisterCode.getText().toString();
        password=activityRegisterBinding.etRegisterPassword.getText().toString();
        passwordConfirm=activityRegisterBinding.etRegisterPassword.getText().toString();
        if (StringUtils.isEmpty(phone)||!RegexUtils.isMobileExact(phone)){//对手机号的校验
            activityRegisterBinding.etRegisterPhone.setError("手机号不正确",null);
            canRegisterUser=false;
            LogUtils.e(TAG,"canRegisterUser1=="+canRegisterUser);
            return canRegisterUser;
        }else if (StringUtils.isEmpty(code)||code.length()<6){//对验证码的校验
            activityRegisterBinding.etRegisterCode.setError("验证码格式不正确",null);
            canRegisterUser=false;
            LogUtils.e(TAG,"canRegisterUser2=="+canRegisterUser);
            return canRegisterUser;
        }else if (StringUtils.isEmpty(password)||password.length()<6){//对密码的校验
            activityRegisterBinding.etRegisterPassword.setError("密码格式不正确",null);
            canRegisterUser=false;
            LogUtils.e(TAG,"canRegisterUser3=="+canRegisterUser);
            return canRegisterUser;
        }else if (StringUtils.isEmpty(passwordConfirm)||passwordConfirm.length()<6){//对确认密码的校验
            activityRegisterBinding.etRegisterPwdConfirm.setError("确认密码格式不正确",null);
            canRegisterUser=false;
            LogUtils.e(TAG,"canRegisterUser4=="+canRegisterUser);
            return canRegisterUser;
        }else if (!password.equals(passwordConfirm)){
            activityRegisterBinding.etRegisterPwdConfirm.setError("两次密码不一致",null);
            canRegisterUser=false;
            LogUtils.e(TAG,"canRegisterUser5=="+canRegisterUser);
        }
        canRegisterUser=true;
        LogUtils.e(TAG,"canRegisterUser=="+canRegisterUser);
        return  canRegisterUser;
    }
    /**发送验证码*/
    private void sendVerifyCode() {
        BmobSMS.requestSMSCode(phone, "注册模板", new QueryListener<Integer>() {
            @Override
            public void done(Integer integer, BmobException e) {
                Log.e("allen","integer==------------------>"+integer);
                if(e==null){//验证码发送成功
                    ToastUtils.show(RegisterActivity.this,"验证码发送成功，短信id："+integer);//用于查询本次短信发送详情
                }else{
                    loge(e);
                    ToastUtils.show(RegisterActivity.this,"errorCode = "+e.getErrorCode()+",errorMsg = "+e.getLocalizedMessage());
                }
            }
        });
    }
    /**一键注册登录**/
    @Override
    public void registerUser() {
        if (canRegisterUser()){
            IdWorker idWorker=new IdWorker(1,1);
            long uuid=idWorker.nextId();
            String userId=String.valueOf(uuid);
            Log.e(TAG,"userId==-------------->"+userId);
            final User user = new User();
            user.setPassword(password);
            user.setMobilePhoneNumber(phone);
            user.setUserid(userId);
            addSubscription(user.signOrLogin(code, new SaveListener<User>() {

                @Override
                public void done(User myUser, BmobException e) {
                    if(e==null){
                        ToastUtils.show(RegisterActivity.this,"注册成功");
                        Log.e(TAG,myUser.getObjectId()+"-"+myUser.getUserid()+myUser.getSessionToken());
                        onBackPressed();
                    }else{
                        loge(e);
                    }
                }

            }));
        }
    }

    @Override
    public void floatButtonExit() {
        onBackPressed();
    }

    @Override
    public void onClick(View v) {

    }


    //进入动画
    private void ShowEnterAnimation() {
        Transition transition = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementEnterTransition(transition);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            transition.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {
                    activityRegisterBinding.cardViewRegister.setVisibility(View.GONE);
                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        transition.removeListener(this);
                    }
                    animateRevealShow();
                }

                @Override
                public void onTransitionCancel(Transition transition) {

                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }

            });
        }
    }

    public void animateRevealShow() {
        Animator mAnimator = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            mAnimator = ViewAnimationUtils.createCircularReveal(activityRegisterBinding.cardViewRegister, activityRegisterBinding.cardViewRegister.getWidth() / 2, 0, activityRegisterBinding.floatButtonExit.getWidth() / 2, activityRegisterBinding.cardViewRegister.getHeight());
        }
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                activityRegisterBinding.cardViewRegister.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    //退出动画
    public void animateRevealClose() {
        Animator mAnimator = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            mAnimator = ViewAnimationUtils.createCircularReveal(activityRegisterBinding.cardViewRegister, activityRegisterBinding.cardViewRegister.getWidth() / 2, 0, activityRegisterBinding.cardViewRegister.getHeight(), activityRegisterBinding.floatButtonExit.getWidth() / 2);

            mAnimator.setDuration(500);
            mAnimator.setInterpolator(new AccelerateInterpolator());
            mAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    activityRegisterBinding.cardViewRegister.setVisibility(View.INVISIBLE);
                    super.onAnimationEnd(animation);
                    activityRegisterBinding.floatButtonExit.setImageResource(R.drawable.ic_add);
                    RegisterActivity.super.onBackPressed();
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                }
            });
            mAnimator.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        registerViewModel.destroy();
    }

    @Override
    public void onBackPressed() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            animateRevealClose();
        }
        super.onBackPressed();
    }
}
