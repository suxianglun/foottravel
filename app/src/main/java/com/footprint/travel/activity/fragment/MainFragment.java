package com.footprint.travel.activity.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.footprint.travel.R;
import com.footprint.travel.zxing.CaptureActivity;
import com.footprint.travel.activity.MainActivity;
import com.footprint.travel.activity.MyQrCodeActivity;
import com.footprint.travel.adapter.MainFragmentRecyclerAdapter;
import com.footprint.travel.adapter.MenuRecyclerAdapter;
import com.footprint.travel.base.BaseFragment;
import com.footprint.travel.customview.BaseItemDecoration;
import com.footprint.travel.databinding.FragmentMainBinding;
import com.footprint.travel.entity.City;
import com.footprint.travel.entity.MainMenu;
import com.footprint.travel.entity.Movie;
import com.footprint.travel.entity.Scenery;
import com.footprint.travel.http.Fault;
import com.footprint.travel.http.loader.MovieLoader;
import com.footprint.travel.http.loader.SceneryLoader;
import com.footprint.travel.listener.RecyclerViewItemClickListener;
import com.footprint.travel.utils.PermissionsUtil;
import com.footprint.travel.viewmodel.MainFragmentViewModel;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.functions.Action1;

/**
 * @标题: MainFragment .java
 * @概述: 首页
 * @作者: Allen
 * @日期: 2016/10/10 @版本：
 */
public class MainFragment extends BaseFragment implements View.OnClickListener{
    public MainActivity activity;
    private PopupWindow popupWindow;
    private FragmentMainBinding fragmentMainBinding;
    private MainFragmentViewModel mainFragmentViewModel;
    private Subscription mSubscription;
    private List<MainMenu> datas;
    private List<MainMenu> mainMenus=new ArrayList<>();
    private MenuRecyclerAdapter menuRecyclerAdapter;
    private MovieLoader movieLoader;
    private SceneryLoader sceneryLoader;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        fragmentMainBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_main,container,false);
        mainFragmentViewModel=new MainFragmentViewModel(getContext(),fragmentMainBinding);
        //加上后fragment中的onCreatMenu()才会被调用
        setHasOptionsMenu(true);
        activity= (MainActivity) getActivity();
        initToolbar();
        movieLoader=new MovieLoader();
        sceneryLoader=new SceneryLoader();
        mainFragmentViewModel.setGallery();
        initMenuList();
        initContentList();
        mainFragmentViewModel.initScrollAdView();
        mainFragmentViewModel.initFab();
        mainFragmentViewModel.clickEvent();
        return fragmentMainBinding.getRoot();
    }
    /*
    * 初始化首页菜单list
    * */
    private void initMenuList() {
        fragmentMainBinding.mainMenuRecycler.setLayoutManager(new GridLayoutManager(getContext(),4));
        fragmentMainBinding.mainMenuRecycler.addItemDecoration(new BaseItemDecoration(3));
        initMenu();
        menuRecyclerAdapter=new MenuRecyclerAdapter(getContext(),mainMenus);
        menuRecyclerAdapter.setOnItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position){
                    case 0://充值
                        break;
                    case 1://转账
                        break;
                    case 2://体现
                        break;
                    case 3://理财
                        break;
                }
            }
        });
        fragmentMainBinding.mainMenuRecycler.setAdapter(menuRecyclerAdapter);
    }
    private void  initMenu() {
        MainMenu changer=new MainMenu();
        changer.setMenulogo(R.mipmap.ic_changer);
        changer.setMenuName("攻略");
        mainMenus.add(changer);

        MainMenu transfer_accounts=new MainMenu();
        transfer_accounts.setMenulogo(R.mipmap.ic_transfer_accounts);
        transfer_accounts.setMenuName("游记");
        mainMenus.add(transfer_accounts);


        MainMenu finance=new MainMenu();
        finance.setMenulogo(R.mipmap.ic_finance);
        finance.setMenuName("语音导游");
        mainMenus.add(finance);

        MainMenu refund=new MainMenu();
        refund.setMenulogo(R.mipmap.ic_refund);
        refund.setMenuName("工具");
        mainMenus.add(refund);

    }

    /*
    * 初始化首页内容list
    * */
    private void initContentList() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        fragmentMainBinding.mainFragmentRecycler.setLayoutManager(linearLayoutManager);
        fragmentMainBinding.mainFragmentRecycler.setHasFixedSize(true);
        fragmentMainBinding.mainFragmentRecycler.setNestedScrollingEnabled(false);
        datas=new ArrayList<>();
        for (int i=0;i<5;i++){
            MainMenu mainMenu=new MainMenu();
            datas.add(mainMenu);
        }
        MainFragmentRecyclerAdapter mainFragmentRecyclerAdapter=new MainFragmentRecyclerAdapter(getContext(),datas);
        fragmentMainBinding.mainFragmentRecycler.setAdapter(mainFragmentRecyclerAdapter);
    }
    public void initToolbar(){
        fragmentMainBinding.toolbar.setTitle("");
        fragmentMainBinding.toolbar.inflateMenu(R.menu.menu_toolbar_main);//设置右上角的填充菜单
        activity.setSupportActionBar( fragmentMainBinding.toolbar);
        fragmentMainBinding.toolbar.setOnMenuItemClickListener(onMenuItemClick);
    }
    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            String msg = "";
            switch (item.getItemId()) {
                case R.id.action_scan_code:
                    if (Build.VERSION.SDK_INT >= 23) {
                        checkAndRequestCameraPermission();
                    }else {
                        startActivity(new Intent(getActivity(), CaptureActivity.class));
                    }

                    break;
                case R.id.action_add:
//                    getScenryList(11,163,1);
//                    showPopupWindow(getActivity().getApplicationContext(),  fragmentMainBinding.toolbar.getChildAt(1));
                    break;
            }
            if(!msg.equals("")) {
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            }
            return true;
        }

    };
    /**
     * 获取城市列表
     */
    private void getCityList(){
        sceneryLoader.getCitys().subscribe(new Action1<List<City>>() {
            @Override
            public void call(List<City> cities) {
                Log.e("allen","cities"+cities.size());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }
    /**
     * 获取景点列表
     * int provinceId 省份id
     * int cityId     城市id
     * int page       页数
     */
    private void getScenryList(int provinceId,int cityId,int page){
        sceneryLoader.getScenerys(provinceId,cityId,page).subscribe(new Action1<List<Scenery>>() {
            @Override
            public void call(List<Scenery> sceneries) {
                Log.e("allen","cities"+sceneries.size());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }
    /**
     * 获取电影列表
     */
    private void getMovieList(){
        movieLoader.getMovie(0,10).subscribe(new Action1<List<Movie>>() {
            @Override
            public void call(List<Movie> movies) {
              Log.e("allen","movies"+movies.size());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e("TAG","error message:"+throwable.getMessage());
                if(throwable instanceof Fault){
                    Fault fault = (Fault) throwable;
                    if(fault.getErrorCode() == 404){
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
    public void checkAndRequestCameraPermission() {
       String[] permissions=new String[]{Manifest.permission.CAMERA};
        PermissionsUtil.setNeedRequestPermissions(permissions);
        PermissionsUtil.checkAndRequestPermissions(activity, new PermissionsUtil.PermissionsCallback() {
            @Override
            public void onPermissionsGranted() {
                startActivity(new Intent(getActivity(), CaptureActivity.class));
            }

            @Override
            public void onPermissionsDenied(int requestCode, List<String> perms) {

            }
        });
    }
    @Override
    public void titleSet(View view) {
        super.titleSet(view);
    }

    public void showPopupWindow(Context context, View view) {
        WindowManager manager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        View contentView = LayoutInflater.from(context).inflate(R.layout.menu_popupwindow_top, null);
        int height=manager.getDefaultDisplay().getHeight();
        int width=manager.getDefaultDisplay().getWidth();
        popupWindow = new PopupWindow(contentView, width/2, height/5);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.mipmap.bg_main_popupwindow));
        popupWindow.setOutsideTouchable(true);
        int xpos = manager.getDefaultDisplay().getWidth() - popupWindow.getWidth()/2;
        //xoff,yoff基于anchor的左下角进行偏移。
        popupWindow.showAsDropDown(view, xpos, 10);

        //添加朋友
        contentView.findViewById(R.id.ll_add_friends).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
            }
        });
        //扫一扫
        contentView.findViewById(R.id.ll_scan_scan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
            }
        });
        //我的二维码
        contentView.findViewById(R.id.ll_my_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                    startActivity(new Intent(getActivity(), MyQrCodeActivity.class));
                }
            }
        });

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
    }


    @Override
    public void onClick(View v) {

    }
}
