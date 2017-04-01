package com.footprint.travel.activity;

import android.content.Context;
import android.content.res.ColorStateList;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.footprint.travel.R;
import com.footprint.travel.activity.fragment.MainFragment;
import com.footprint.travel.activity.fragment.MineFragment;
import com.footprint.travel.activity.fragment.NavigationFragment;
import com.footprint.travel.activity.fragment.SpecialServiceFragment;
import com.footprint.travel.adapter.MainViewPageAdapter;
import com.footprint.travel.base.BaseActivity;
import com.footprint.travel.base.BaseFragment;
import com.footprint.travel.databinding.ActivityMainBinding;
import com.footprint.travel.utils.BottomNavigationViewHelper;
import com.footprint.travel.utils.LogUtils;
import com.footprint.travel.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @标题: MainActivity.java
 * @概述: 首页 Fragment+viewpage+Radiobutton
 * @作者: Allen
 * @日期: 2016/10/8 @版本：
 */
public class MainActivity extends BaseActivity implements View.OnClickListener{
    private MainActivity activity;
    private MainViewModel mainViewModel;
    public ActivityMainBinding activityMainBinding;
    public MainViewPageAdapter adapter;
    public FragmentManager fm;
    public List<BaseFragment> fragments=new ArrayList<BaseFragment>();
    private Context context;
    private MainFragment mainFragment;
    private NavigationFragment navigationFragment;
    private SpecialServiceFragment specialServiceFragment;
    private MineFragment mineFragment;

    @Override
    protected void initVariables() {
    activity=this;
    }

    @Override
    protected void initDataBinding() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        activityMainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        mainViewModel=new MainViewModel(activity);
    }
    @Override
    protected void initView() {
        initViewPage();
        initBottomNavigationView();
    }
    /*
      * 初始化ViewPage
      * */
    public void initViewPage() {
        mainFragment=new MainFragment();
        navigationFragment=new NavigationFragment();
        specialServiceFragment=new SpecialServiceFragment();
        mineFragment=new MineFragment();
        fm=activity.getSupportFragmentManager();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        adapter=new MainViewPageAdapter(fm,fragments);
        adapter.addFragment(mainFragment);
        adapter.addFragment(navigationFragment);
        adapter.addFragment(specialServiceFragment);
        adapter.addFragment(mineFragment);
        activityMainBinding.vpMain.setAdapter(adapter);
        activityMainBinding.vpMain.setOffscreenPageLimit(3);
        activityMainBinding.vpMain.setCurrentItem(0);
        //将viewpage与BottomNavigationView关联起来
        activityMainBinding.vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                activityMainBinding.navigation.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    /*
   * 初始化BottomNavigationView
   * */
    public void initBottomNavigationView() {
        try {
            BottomNavigationViewHelper.disableShiftMode(activityMainBinding.navigation);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //将viewpage与BottomNavigationView关联起来
        activityMainBinding.navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int index=item.getItemId();
                LogUtils.e("allen","index=="+index);
                switch (index){
                    case R.id.item_home_page:
                        activityMainBinding.vpMain.setCurrentItem(0);
                        break;
                    case R.id.item_main_financing:
                        activityMainBinding.vpMain.setCurrentItem(1);
                        break;
                    case R.id.item_friend:
                        activityMainBinding.vpMain.setCurrentItem(2);
                        break;
                    case R.id.item_mine:
                        activityMainBinding.vpMain.setCurrentItem(3);
                        break;
                }
                return false;
            }
        });
        // 实现底部导航菜单状态改变
        int[][] states = new int[][]{
                new int[]{ -android.R.attr.state_checked},
                new int[]{android.R.attr.state_checked}
        };

        int[] colors = new int[]{
                getResources().getColor(R.color.white),
                getResources().getColor(R.color.yellow)
        };
        ColorStateList csl = new ColorStateList(states, colors);

        activityMainBinding.navigation.setItemTextColor(csl);
        activityMainBinding.navigation.setItemIconTintList(csl);
    }
    @Override
    public void onClick(View v) {

    }

}
