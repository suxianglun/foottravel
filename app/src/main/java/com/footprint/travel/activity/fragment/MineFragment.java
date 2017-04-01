package com.footprint.travel.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.footprint.travel.R;
import com.footprint.travel.activity.LoginActivity;
import com.footprint.travel.activity.MainActivity;
import com.footprint.travel.base.BaseFragment;
import com.footprint.travel.customview.GlideCircleTransform;

/**
 * @标题: MineFragment.java
 * @概述: 我的 fragment
 * @作者: Allen
 * @日期: 2016/10/10 @版本：
 */
public class MineFragment extends BaseFragment implements View.OnClickListener{
    private View view;
    private Toolbar toolbar;
    private MainActivity activity;
    private FloatingActionButton fab_mine;
    private ImageView iv_head_logo;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_mine,null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        activity=(MainActivity) getActivity();
        //加上后fragment中的onCreatMenu()才会被调用
        setHasOptionsMenu(true);
        toolbar=(Toolbar)view.findViewById(R.id.toolbar);
        toolbar.setTitle("我的");
        activity.setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(onMenuItemClick);
        fab_mine = (FloatingActionButton) view.findViewById(R.id.fab_mine);
        fab_mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "编辑", Toast.LENGTH_SHORT).show();
            }
        });
        iv_head_logo=(ImageView)view.findViewById(R.id.iv_head_logo);
        Glide.with(activity.getApplicationContext())
                .load(R.mipmap.ic_profile)
                .centerCrop()
                .error(R.mipmap.ic_profile)
                .crossFade()
                .transform(new GlideCircleTransform(activity.getApplicationContext()))
                .into(iv_head_logo);
        iv_head_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //清除activity中的menu
        menu.clear();
        inflater.inflate(R.menu.menu_toolbar_mine, menu);
    }
    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            String msg = "";
            switch (item.getItemId()) {
                case R.id.action_setting:
                    msg="设置";
                    break;
                case R.id.action_information:
                    msg="信息";
                    break;
            }

            if(!msg.equals("")) {
                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
            }
            return true;
        }


    };
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
