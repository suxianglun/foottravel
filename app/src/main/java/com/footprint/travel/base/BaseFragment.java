package com.footprint.travel.base;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.footprint.travel.R;
import com.footprint.travel.customview.ScrollViewPager;
import com.footprint.travel.utils.DisplayUtil;
import com.footprint.travel.utils.LogUtils;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * @标题: BaseFragment.java
 * @概述: fragment基类
 * @作者: Allen
 * @日期: 2016/10/8 @版本：
 */

public  abstract class BaseFragment extends Fragment implements View.OnClickListener{
    /** Fragment当前状态是否可见 */
    protected boolean isVisible;
    /** Fragment是否第一次加载 */
    private boolean isFirst = true;

    private BaseActivity baseActivity;
    public TextView left, center, rightneiber, right;
    public BaseFragment createFragment;
    public ScrollViewPager viewPager;
    public CompositeSubscription mCompositeSubscription;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (left == null) {
            left = (TextView) view.findViewById(R.id.activity_title_left);
            if (left != null)
                left.setOnClickListener(this);
        }

        if (center == null)
            center = (TextView) view.findViewById(R.id.activity_title_center);

        if (right == null) {
            right = (TextView) view.findViewById(R.id.activity_title_right);
            if (right != null)
                right.setOnClickListener(this);
        }

        if (rightneiber == null) {
            rightneiber = (TextView) view.findViewById(R.id.activity_title_rightneiber);
            if (rightneiber != null)
                rightneiber.setOnClickListener(this);
        }

        titleSet(view);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()){
            loadData();
        }
    }
    protected  void loadData(){

    }

    //    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        LogUtils.e("isVisibleToUser=="+isVisibleToUser);
//        if (isVisibleToUser) {
//            isVisible=true;
//            if (isFirst) {
//                isFirst = false;
//                LogUtils.e("可见");
//                onFirstVisible();
//            } else {
//                onVisible();
//            }
//        } else {
//            LogUtils.e("Fragment不可见");
//            isVisible=false;
//            onGone();
//        }
//    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        baseActivity = (BaseActivity) context;
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
    //设置起源fragment
    public void setCreateFragment(BaseFragment createFragment) {
        this.createFragment = createFragment;
    }

    //获取创建fragment
    public BaseFragment getCreateFragment() {
        return createFragment;
    }
    /**
     * fragment第一次显示调用
     */
    protected abstract void onFirstVisible();

    /**
     * fragment显示调用
     */
    protected abstract void onVisible();

    /**
     * fragment消失调用
     */
    protected abstract void onGone();
    /**
     * 设置titlebar 左边
     *
     * @param imgId 不设置图片传0,否则传图片id
     * @param text  不设置传空
     */
    public void setTitleLeft(int imgId, int text) {

        if (left == null)
            return;

        if (imgId < 1 && text < 1) {
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

        if (text < 1) {
            left.setText("");
        } else {
            left.setText(text);
        }
    }

    /**
     * 设置中间文字
     *
     * @param text
     */
    public void setTitleCenter(int text) {

        if (center == null)
            return;
        if (text < 1) {
            center.setVisibility(View.GONE);
            center.setText("");
        } else {
            center.setVisibility(View.VISIBLE);
            center.setText(text);
        }
    }

    /**
     * 设置中间文字
     *
     * @param text
     */
    public void setTitleCenter(String text) {

        if (center == null)
            return;
        if (TextUtils.isEmpty(text)) {
            center.setVisibility(View.GONE);
            center.setText("");
        } else {
            center.setVisibility(View.VISIBLE);
            if (rightneiber.getVisibility() == View.VISIBLE) {
                if (baseActivity != null)
                    center.setMaxWidth((DisplayUtil.dip2px(baseActivity, 17) * 10));
            } else {
                if (baseActivity != null)
                    center.setMaxWidth((DisplayUtil.dip2px(baseActivity, 17) * 15));
            }
            center.setText(text);
        }
    }

    /**
     * 设置titlebar 右边
     *
     * @param imgId 不设置图片传0,否则传图片id
     * @param text  不设置传空
     */
    public void setTitleRightNeiber(int imgId, int text) {

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

    public boolean rightVIsible() {
        return right.isShown();
    }

    /**
     * 设置titlebar 右边
     *
     * @param imgId 不设置图片传0,否则传图片id
     * @param text  不设置传空
     */
    public void setTitleRight(int imgId, int text) {


        if (right == null)
            return;

        if (imgId < 1 && text < 1) {
            right.setVisibility(View.GONE);
        } else {
            right.setVisibility(View.VISIBLE);
        }

        if (imgId > 1) {
            Drawable drawable = getResources().getDrawable(imgId);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

            right.setCompoundDrawables(drawable, null, null, null);
        } else {
            right.setCompoundDrawables(null, null, null, null);
        }

        if (text < 1) {
            right.setText("");
        } else {
            right.setText(text);
        }
    }
    public void setViewPager(ScrollViewPager scrollViewPager) {
        this.viewPager = scrollViewPager;
    }

    public ScrollViewPager getViewPager() {
        return viewPager;
    }

    public void titleSet(View view) {
    }

}
