package com.footprint.travel.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.footprint.travel.R;

/**
 * @标题: .java
 * @概述:
 * @作者: Allen
 * @日期: 2017/1/5 @版本：
 */
public class DialogUtils {
    public static void showDialog(Context context,int title, int message, View contentView,
                                  int positiveBtnText, int negativeBtnText,
                                  DialogInterface.OnClickListener positiveCallback,
                                  DialogInterface.OnClickListener negativeCallback,
                                  boolean cancelable){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle(title==0? context.getResources().getString(R.string.tip):context.getResources().getString(title));
        if (message!=0){
            builder.setMessage(message);
        }
        if (contentView!=null){
            builder.setView(contentView);
        }
        if (positiveBtnText!=0){
            builder.setPositiveButton(positiveBtnText,positiveCallback);
        }
        if (positiveBtnText!=0){
            builder.setNegativeButton(negativeBtnText,negativeCallback);
        }
        builder.setCancelable(cancelable);
        builder.show();

    }
    //普通对话框
    public static void showSimpleDialog(Context context, int title, int message,
                                        int positiveBtnText, int negativeBtnText,
                                               DialogInterface.OnClickListener positiveCallback,
                                               DialogInterface.OnClickListener negativeCallback)
    {
       showDialog(context, title, message, null, positiveBtnText, negativeBtnText, positiveCallback, negativeCallback, false);
    }
}
