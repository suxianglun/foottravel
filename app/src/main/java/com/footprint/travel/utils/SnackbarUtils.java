package com.footprint.travel.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * @标题: .java
 * @概述:
 * @作者: Allen
 * @日期: 2016/12/15 @版本：
 */
public class SnackbarUtils {
    /**
     * 长时间显示Snackbar
     * @ View view :界面上的view 随意传一个就行
     * @ int resid :字符串id
     * @ SnackbarCallback callback: 点击事件回调
    * */
    public  static  void  showSnackbarLong(View view, int resId, final SnackbarCallback callback){
        Snackbar.make(view,view.getResources().getString(resId),Snackbar.LENGTH_LONG)
                .setAction("Yes", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callback.onClickSnackbar();
                    }
                })
                .setDuration(3000).show();
    }
    /**
     * 长时间显示Snackbar
     * @ View view :界面上的view 随意传一个就行
     * @  String str :字符串
     * @ SnackbarCallback callback: 点击事件回调
     * */
    public  static  void  showSnackbarLong(View view, String str, final SnackbarCallback callback){
        Snackbar.make(view,str,Snackbar.LENGTH_LONG)
                .setAction("Yes", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callback.onClickSnackbar();
                    }
                })
                .setDuration(3000).show();
    }
    /**
     * 显示Snackbar
     * @ View view :界面上的view 随意传一个就行
     * @ int resid :字符串id
     * @ SnackbarCallback callback: 点击事件回调
     * */
    public  static  void  showSnackbarShort(View view, int resId, final SnackbarCallback callback){
        Snackbar.make(view,view.getResources().getString(resId),Snackbar.LENGTH_SHORT)
                .setAction("Yes", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callback.onClickSnackbar();
                    }
                })
                .setDuration(2000).show();
    }
    /**
     * 显示Snackbar
     * @ View view :界面上的view 随意传一个就行
     * @  String str:字符串
     * @ SnackbarCallback callback: 点击事件回调
     * */
    public  static  void  showSnackbarShort(View view, String str, final SnackbarCallback callback){
        Snackbar.make(view,str,Snackbar.LENGTH_SHORT)
                .setAction("Yes", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callback.onClickSnackbar();
                    }
                })
                .setDuration(2000).show();
    }
     public   interface  SnackbarCallback{
       void onClickSnackbar();
    }
}
