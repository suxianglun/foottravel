package com.footprint.travel.callback;

/**
 * @标题: .java
 * @概述:
 * @作者: Allen
 * @日期: 2016/12/14 @版本：
 */
public interface RegisterViewModelCallback {
    /***获取验证码* */
    void getVerifyCode();
    /**进行注册*/
    void registerUser();
    /***进入登录界面动画* */
    void floatButtonExit();
    interface DestroyCallback{
        void destroy();
    }

}
