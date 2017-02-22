package com.footprint.travel.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.footprint.travel.base.BaseFragment;

import java.util.List;

/**
 * @标题:MainViewPageAdapter .java
 * @概述:
 * @作者: Allen
 * @日期: 2016/10/10 @版本：
 */
public class MainViewPageAdapter extends FragmentPagerAdapter {
    public List<BaseFragment> fragments;
    public MainViewPageAdapter(FragmentManager fm,List<BaseFragment> fragments) {
        super(fm);
        this.fragments=fragments;
    }
    public  void addFragment(BaseFragment fragment){
        if (fragment!=null){
            fragments.add(fragment);
            notifyDataSetChanged();
        }
    }
    @Override
    public Fragment getItem(int position) {
        return  fragments.get(position);
    }
    @Override
    public int getCount() {
        return fragments.size();
    }




}
