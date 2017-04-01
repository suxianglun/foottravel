package com.footprint.travel.viewmodel;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.footprint.travel.activity.fragment.MainFragment;
import com.footprint.travel.activity.fragment.MineFragment;
import com.footprint.travel.activity.fragment.NavigationFragment;
import com.footprint.travel.activity.fragment.SpecialServiceFragment;
import com.footprint.travel.adapter.MainViewPageAdapter;
import com.footprint.travel.base.BaseFragment;
import com.footprint.travel.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * @标题: MainViewModel.java
 * @概述: MainActivity Model
 * @作者: Allen
 * @日期: 2016/12/2 @版本：
 */
public class MainViewModel {
    public ActivityMainBinding activityMainBinding;
    public MainViewPageAdapter adapter;
    public FragmentManager fm;
    public List<BaseFragment> fragments=new ArrayList<BaseFragment>();
    private Context context;
    private MainFragment mainFragment;
    private NavigationFragment navigationFragment;
    private SpecialServiceFragment specialServiceFragment;
    private MineFragment mineFragment;
    public MainViewModel(Context context) {
        this.context=context;
    }


}
