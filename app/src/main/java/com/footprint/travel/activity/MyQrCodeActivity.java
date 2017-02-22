package com.footprint.travel.activity;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.footprint.travel.R;
import com.footprint.travel.base.BaseActivity;
import com.footprint.travel.utils.QRCodeUtils;

/**
 * @标题: MyQrCodeActivity.java
 * @概述: 我的二维码
 * @作者: Allen
 * @日期: 2016/10/14 @版本：
 */
public class MyQrCodeActivity extends BaseActivity implements View.OnClickListener{
    public ImageView iv_my_code_logo;


    @Override
    protected void initVariables() {

    }

    @Override
    protected void initDataBinding() {
        setContentView(R.layout.activity_my_qrcode);
        setTitleCenter(R.string.my_qrcode);
        setTitleLeft(R.mipmap.ic_back,0);
        iv_my_code_logo=(ImageView)findViewById(R.id.iv_my_code_logo);

    }
    @Override
    protected void initView() {
        Bitmap bitmap= QRCodeUtils.createQRImage(MyQrCodeActivity.this,"suxianglun",null);
        if (bitmap!=null){
            iv_my_code_logo.setImageBitmap(bitmap);
        }

    }
    @Override
    public void onClick(View v) {

    }
}
