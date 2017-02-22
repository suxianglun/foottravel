package com.footprint.travel.activity;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.footprint.travel.R;
import com.footprint.travel.base.BaseActivity;
import com.footprint.travel.utils.QRCodeUtils;
import com.google.zxing.WriterException;

/**
 * @标题: PayCodeActivity.java
 * @概述: 付款码
 * @作者: Allen
 * @日期: 2016/10/14 @版本：
 */
public class PayCodeActivity extends BaseActivity implements View.OnClickListener {
    public ImageView iv_bar_code,iv_qrcode;//条形码、二维码
    @Override
    protected void initVariables() {

    }

    @Override
    protected void initDataBinding() {
        setContentView(R.layout.activity_paycode);
        setTitleCenter(R.string.pay);
        setTitleLeft(R.mipmap.ic_back,0);
        setTitleRight(R.mipmap.ic_gengduo,0);
        iv_bar_code=(ImageView)findViewById(R.id.iv_bar_code);
        iv_qrcode=(ImageView)findViewById(R.id.iv_qrcode);
    }

    @Override
    protected void initView() {
        try {
         Bitmap oneDCode= QRCodeUtils.CreateOneDCode("1300 9985 1289 163748");
         if (oneDCode!=null){
             iv_bar_code.setImageBitmap(oneDCode);
         }
        } catch (WriterException e) {
            e.printStackTrace();
        }
        Bitmap qrImage= QRCodeUtils.createQRImage(PayCodeActivity.this,"suxianglun",null);
        if (qrImage!=null){
            iv_qrcode.setImageBitmap(qrImage);
        }

    }

    @Override
    public void onClick(View v) {

    }
}
