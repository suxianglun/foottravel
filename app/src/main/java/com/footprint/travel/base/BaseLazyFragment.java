package com.footprint.travel.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.footprint.travel.utils.LogUtils;

/**
 * @标题: BaseLazyFragment.java
 * @概述: 懒加载Fragment基类
 * @作者: Allen
 * @日期: 2016-4-1 @版本： V1.2.9
 */
public abstract class BaseLazyFragment extends BaseFragment {

    @Override
    protected void onFirstVisible() {
        LogUtils.e("onFirstVisible");
        lazyLoad();
    }

    @Override
    protected void onVisible() {
        LogUtils.e("onVisible");
        lazyLoad();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtils.e("getUserVisibleHint()=="+getUserVisibleHint());
        if (getUserVisibleHint()){
            lazyLoad();
        }
    }
    @Override
    protected void onGone() {

    }
    /**
     * 延迟加载
     *
     */
    protected abstract void lazyLoad();

}
