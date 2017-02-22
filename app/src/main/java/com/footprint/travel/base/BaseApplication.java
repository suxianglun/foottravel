package com.footprint.travel.base;

import android.app.Application;
import android.content.Context;

import com.footprint.travel.utils.LogUtils;

import cn.bmob.v3.Bmob;

/**
 * @标题: BaseApplication.java
 * @概述: Application
 * @作者: Allen
 * @日期: 2016/10/8 @版本：
 */
public class BaseApplication extends Application{
    public static Context context;
    /**
     * SDK初始化也可以放到Application中
     */
    public static String APPID ="eb8ce1bef4be5b3c7d982ead070601ca";
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        //提供以下两种方式进行初始化操作：
//		//第一：设置BmobConfig，允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)
//		BmobConfig config =new BmobConfig.Builder(this)
//		//设置appkey
//		.setApplicationId(APPID)
//		//请求超时时间（单位为秒）：默认15s
//		.setConnectTimeout(30)
//		//文件分片上传时每片的大小（单位字节），默认512*1024
//		.setUploadBlockSize(1024*1024)
//		//文件的过期时间(单位为秒)：默认1800s
//		.setFileExpiration(5500)
//		.build();
//		Bmob.initialize(config);
        //第二：默认初始化
        Bmob.initialize(this,APPID,"demo");
        LogUtils.setDebugable(context);
    }
    public  static Context getAppContext(){
        return context;
    }
}
