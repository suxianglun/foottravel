package com.footprint.travel.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.footprint.travel.R;
import com.footprint.travel.base.BaseFragment;

/**
 * @标题: SpecialServiceFragment.java
 * @概述: 特色服务
 * @作者: Allen
 * @日期: 2016/11/15 @版本：
 */
public class SpecialServiceFragment extends BaseFragment{
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_special_service,null);
        return view;
    }
    @Override
    protected void onFirstVisible() {

    }

    @Override
    protected void onVisible() {

    }

    @Override
    protected void onGone() {

    }

//    @Override
//    protected void lazyLoad() {
//
//    }


    @Override
    protected void loadData() {
        super.loadData();
    }

    @Override
    public void onClick(View v) {

    }
}
