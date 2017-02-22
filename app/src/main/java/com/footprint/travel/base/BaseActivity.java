package com.footprint.travel.base;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.footprint.travel.R;

import cn.bmob.v3.exception.BmobException;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * @标题: BaseActivity.java
 * @概述: activity基类
 * @作者: Allen
 * @日期: 2016/10/8 @版本：
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    //头部
    public TextView left, center, rightneiber, right;
    public Toolbar toolbar;
    public TextView titleText;
    public CompositeSubscription mCompositeSubscription;
    public static String TAG = "allen";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //将当前activity加入activity栈
        AppManager.getInstance(this).addActivity(this);
        initVariables();
        initDataBinding();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /*初始化变量：包含intent带的数据和activity内的数据*/
    protected   abstract void initVariables();
    /*加载layout布局,绑定数据*/
    protected   abstract void initDataBinding();
    /*初始化控件,为控件添加监听事件*/
    protected   abstract void initView();
    //设置通用的toolbar
    protected void initToolBar(int titleId, boolean needBack) {
        String title=getApplicationContext().getResources().getString(titleId);
        toolbar = (Toolbar) findViewById(R.id.toolbar_comm);
        titleText = (TextView) findViewById(R.id.text_title);
        setTitle("");
        titleText.setText(title);
        if (needBack) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }
    /**
     * 解决Subscription内存泄露问题
     * @param s
     */
    protected void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }
    public static void loge(Throwable e) {
        Log.i(TAG,"===============================================================================");
        if(e instanceof BmobException){
            Log.e(TAG, "错误码："+((BmobException)e).getErrorCode()+",错误描述："+((BmobException)e).getMessage());
        }else{
            Log.e(TAG, "错误描述："+e.getMessage());
        }
    }
    /**
     * 设置titlebar 左边
     *
     * @param imgId  不设置图片传0,否则传图片id
     * @param textId 不设置传空
     */
    public void setTitleLeft(int imgId, int textId) {
        if (left == null) {
            left = (TextView) findViewById(R.id.activity_title_left);
            if (left != null)
                left.setOnClickListener(this);
        }

        if (left == null)
            return;

        if (imgId < 1 && textId < 1) {
            left.setVisibility(View.GONE);
        } else {
            left.setVisibility(View.VISIBLE);
        }

        if (imgId > 1) {
            Drawable drawable = getResources().getDrawable(imgId);
            if (drawable != null)
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

            left.setCompoundDrawables(drawable, null, null, null);
        } else {
            left.setCompoundDrawables(null, null, null, null);
        }

        if (textId < 1) {
            left.setText("");
        } else {
            left.setText(textId);
        }
    }

    /**
     * 设置中间文字
     *
     * @param textId
     */
    public void setTitleCenter(int textId) {
        if (center == null)
            center = (TextView) findViewById(R.id.activity_title_center);

        if (center == null)
            return;
        if (textId < 1) {
            center.setVisibility(View.GONE);
        } else {
            center.setVisibility(View.VISIBLE);
            center.setText(textId);
        }
    }

    /**
     * 设置titlebar 右边
     *
     * @param imgId 不设置图片传0,否则传图片id
     * @param text  不设置传空
     */
    public void setTitleRightNeiber(int imgId, int text) {
        if (rightneiber == null) {
            rightneiber = (TextView) findViewById(R.id.activity_title_rightneiber);
            if (rightneiber != null)
                rightneiber.setOnClickListener(this);
        }

        if (rightneiber == null)
            return;

        if (imgId < 1 && text < 1) {
            rightneiber.setVisibility(View.GONE);
        } else {
            rightneiber.setVisibility(View.VISIBLE);
        }

        if (imgId > 1) {
            Drawable drawable = getResources().getDrawable(imgId);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

            rightneiber.setCompoundDrawables(drawable, null, null, null);
        } else {
            rightneiber.setCompoundDrawables(null, null, null, null);
        }

        if (text < 1) {
            rightneiber.setText("");
        } else {
            rightneiber.setText(text);
        }
    }

    /**
     * 设置titlebar 右边
     *
     * @param limgId 不设置图片传0,否则传图片id
     * @param textId 不设置传空
     */
    public void setTitleRight(int limgId, int textId) {
        if (textId > 0) {
            setTitleRight(limgId, getResources().getString(textId));
        } else {
            setTitleRight(limgId, "");
        }

    }

    /**
     * 设置字体颜色
     *
     * @param id
     */
    public void setRightColor(int id) {
        if (right != null) {
            right.setTextColor(getResources().getColor(id));
            if (id == R.color.white) {
                right.setClickable(true);
            } else {
                right.setClickable(false);
            }
        }
    }

    public void setTitleRight(int limgId, String text) {
        if (right == null) {
            right = (TextView) findViewById(R.id.activity_title_right);
            if (right != null)
                right.setOnClickListener(this);
        }


        if (right == null)
            return;

        if (limgId < 1 && TextUtils.isEmpty(text)) {
            right.setVisibility(View.GONE);
        } else {
            right.setVisibility(View.VISIBLE);
        }

        if (limgId > 1) {
            Drawable drawable = getResources().getDrawable(limgId);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

            right.setCompoundDrawables(drawable, null, null, null);
        } else {
            right.setCompoundDrawables(null, null, null, null);
        }

        if (TextUtils.isEmpty(text)) {
            right.setText("");
        } else {
            right.setText(text);
        }
    }


}
