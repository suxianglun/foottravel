package com.footprint.travel.activity;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.footprint.travel.R;
import com.footprint.travel.base.BaseActivity;
import com.footprint.travel.utils.QRCodeUtils;

/**
 * @标题: ReceiptActivity.java
 * @概述: 收款码
 * @作者: Allen
 * @日期: 2016/10/14 @版本：
 */
public class ReceiptActivity extends BaseActivity implements View.OnClickListener {
    public ImageView iv_pay_code;
    @Override
    protected void initVariables() {

    }

    @Override
    protected void initDataBinding( ) {
        setContentView(R.layout.activity_receipt);
        setTitleCenter(R.string.receipt);
        setTitleRight(0,R.string.set_money);
        setTitleLeft(R.mipmap.ic_back,0);
        iv_pay_code=(ImageView)findViewById(R.id.iv_pay_code);
    }

    @Override
    protected void initView() {
        Bitmap bitmap=QRCodeUtils.createQRImage(ReceiptActivity.this,"向我付款5000元",null);
        if (bitmap!=null){
            iv_pay_code.setImageBitmap(bitmap);
        }


    }

    @Override
    public void onClick(View v) {

    }
}
