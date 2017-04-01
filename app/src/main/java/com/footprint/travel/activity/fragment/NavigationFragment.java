package com.footprint.travel.activity.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.footprint.travel.R;
import com.footprint.travel.activity.ChangeCityActivity;
import com.footprint.travel.activity.MainActivity;
import com.footprint.travel.activity.SceneryInfoActivity;
import com.footprint.travel.adapter.CommonAdapter;
import com.footprint.travel.adapter.ViewHolder;
import com.footprint.travel.base.BaseFragment;
import com.footprint.travel.customview.MyNestedScrollView;
import com.footprint.travel.databinding.FragmentNavigationBinding;
import com.footprint.travel.entity.Scenery;
import com.footprint.travel.http.Fault;
import com.footprint.travel.http.loader.SceneryLoader;
import com.footprint.travel.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

/**
 * @标题: NavigationFragment.java
 * @概述: 目的地
 * @作者: Allen
 * @日期: 2016/10/10 @版本：
 */
public class NavigationFragment extends BaseFragment implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener {
    public FragmentNavigationBinding binding;
    public MainActivity activity;
    public SceneryLoader sceneryLoader;
    public Boolean isRefresh = false;//是否 下拉刷新
    public List<Scenery> datas=new ArrayList<>();
    public  CommonAdapter<Scenery> adapter;
    public LinearLayoutManager lm;
    private int page=1;
    private boolean isLoading;//正在加载

    /** 标志位，标志已经初始化完成 */
    private boolean isPrepared=false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_navigation,container,false);
        //加上后fragment中的onCreatMenu()才会被调用
        setHasOptionsMenu(true);
        isPrepared=true;
        activity= (MainActivity) getActivity();
        sceneryLoader=new SceneryLoader();
        initView();
        return binding.getRoot();
    }

    private void initView() {
        initToolbar();
        initRefresh();
        initRecyclerView();
    }

    private void initRefresh() {
        binding.refreshNavigation.setProgressViewOffset(false,-90,10);
        binding.refreshNavigation.setColorSchemeColors(getResources().getColor(R.color.green_them));
        binding.refreshNavigation.setOnRefreshListener(this);
    }


    private void initRecyclerView() {
        lm=new LinearLayoutManager(activity);
        lm.setSmoothScrollbarEnabled(true);
        lm.setAutoMeasureEnabled(true);
        binding.navigationRecycler.setLayoutManager(lm);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        binding.navigationRecycler.setHasFixedSize(true);
        //禁用嵌套滑动之后会有惯性滑动，否则无
        binding.navigationRecycler.setNestedScrollingEnabled(false);
        initAdapter(datas);
        binding.navigationRecycler.setAdapter(adapter);
        binding.scrollView.setOnScrollToBottomLintener(new MyNestedScrollView.OnScrollToBottomListener() {
            @Override
            public void onScrollBottomListener(boolean isBottom) {
                //非正在加载，到底部，正在向上滑动，三者兼备才进行加载下一页
                if (!isLoading&&isBottom&&binding.scrollView.isTop()){
                    isLoading=true;
                    LogUtil.d("okhttp","滚动到最后");
                    page++;
                    LogUtil.e("allen","page=="+page);
                    getScenryList(11,163,page);
                }
            }
        });
        binding.includeNavigationToolbar.tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChangeCityActivity.class));
            }
        });
    }

    private void initToolbar() {
        binding.toolbar.setTitle("");
        binding.toolbar.inflateMenu(R.menu.menu_toolbar_navigation);//设置右上角的填充菜单
        activity.setSupportActionBar( binding.toolbar);
        binding.toolbar.setOnMenuItemClickListener(onMenuItemClick);
    }
    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_collect:
                    break;
            }
            return true;
        }

    };
    /**
     * 获取景点列表
     * int provinceId 省份id
     * int cityId     城市id
     * int page       页数
     */
    private void getScenryList(int provinceId, int cityId, final int page){
        sceneryLoader.getScenerys(provinceId,cityId,page).subscribe(new Action1<List<Scenery>>() {
            @Override
            public void call(List<Scenery> sceneries) {
                if (sceneries!=null&&sceneries.size()!=0){
                    isLoading=false;//加载完成
                    binding.refreshNavigation.setRefreshing(false);
                    datas.addAll(sceneries);
                    adapter.setmDatas(datas);
                }else {
                    adapter.setLoadState(CommonAdapter.CANNOT_LOAD_MORE);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e("TAG","error message:"+throwable.getMessage());
                if(throwable instanceof Fault){
                    Fault fault = (Fault) throwable;
                    if (fault.getErrorCode()==10025){
                        adapter.setLoadState(CommonAdapter.CANNOT_LOAD_MORE);
                    }else if(fault.getErrorCode() == 404){
                        //错误处理
                    }else if(fault.getErrorCode() == 500){
                        //错误处理
                    }else if(fault.getErrorCode() == 501){
                        //错误处理
                    }
                }
            }
        });
    }

    private void initAdapter(List<Scenery> sceneries) {
         adapter=new CommonAdapter<Scenery>(getContext(),R.layout.item_navigation_fragmnet_reycylerview,sceneries) {
            @Override
            public void convert(ViewHolder holder, final Scenery scenery) {
                holder.setText(R.id.tv_scenery_title,scenery.getTitle());
                holder.setText(R.id.tv_address,scenery.getAddress());
                holder.setText(R.id.tv_price_min,scenery.getPrice_min());
                holder.setBitmap(R.id.iv_scenery,scenery.getImgurl());
                holder.setText(R.id.tv_grade,scenery.getGrade());
                holder.setOnClickListener(R.id.card_main_fragment, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getActivity(), SceneryInfoActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("sid",scenery.getSid());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        };

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

    @Override
    protected void loadData() {
        super.loadData();
        if (isPrepared){
            binding.refreshNavigation.post(new Runnable() {
                @Override
                public void run() {
                    binding.refreshNavigation.setRefreshing(true);
                    onRefresh();
                }
            });
        }

    }
    @Override
    public void onRefresh() {
        page=1;
        getScenryList(11,163,page);
    }

    @Override
    public void onClick(View v) {

    }



}