package com.footprint.travel.viewmodel;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Toast;

import com.footprint.travel.R;
import com.footprint.travel.customview.ImageSlideshow;
import com.footprint.travel.customview.ScrollAdView;
import com.footprint.travel.databinding.FragmentMainBinding;
import com.footprint.travel.entity.HeadlineBean;
import com.footprint.travel.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @标题: MainFragmentViewModel.java
 * @概述:
 * @作者: Allen
 * @日期: 2016/12/2 @版本：
 */
public class MainFragmentViewModel {
    private FragmentMainBinding fragmentMainBinding;
    private Context context;
    private boolean isFabOpened=false;


    public MainFragmentViewModel(Context context, FragmentMainBinding fragmentMainBinding) {
        this.context=context;
        this.fragmentMainBinding=fragmentMainBinding;
    }
    /*
   *  轮播图片设置
   * */
    public  void setGallery(){
        initData();
        fragmentMainBinding.isGallery.setDotSpace(12);// // 设置小圆点的间距
        fragmentMainBinding.isGallery.setDotSize(12);// // 设置小圆点的大小
        fragmentMainBinding.isGallery.setDelay(3000);//时间间隔
        fragmentMainBinding.isGallery.setOnItemClickListener(new ImageSlideshow.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
            }
        });
        fragmentMainBinding.isGallery.commit();
    }
    /**
     * 初始化数据
     */
    private void initData() {
        int[] images = {R.mipmap.bg_lunbo1,R.mipmap.bg_lunbo2,R.mipmap.bg_lunbo3,R.mipmap.bg_lunbo4,};
        String [] titles={"","","","",};
        for (int i = 0; i < 4; i++) {
            fragmentMainBinding.isGallery.addImageTitle(images[i], titles[i]);
        }
    }
    public void initScrollAdView() {
        List<HeadlineBean> data = new ArrayList<>();
        data.add(new HeadlineBean("热门", "新马泰七日游，玩爽了，嗨翻天，你还在等什么"));
        data.add(new HeadlineBean("推荐", "马尔代夫三日游温泉度假更有漂亮的风景等你来欣赏"));
        data.add(new HeadlineBean("去哪", "这个冬天就去三亚，用沙滩和海水温暖你的心房"));
        data.add(new HeadlineBean("省钱", "组团游穷游走起，天天省钱，天天开心"));
        fragmentMainBinding.scrollAdView.setData(data);
        fragmentMainBinding.scrollAdView.setHeadlineClickListener(new ScrollAdView.HeadlineClickListener() {
            @Override
            public void onHeadlineClick(HeadlineBean bean) {
                ToastUtils.show(context,bean.getTitle() + ":" + bean.getContent());
            }

            @Override
            public void onMoreClick() {
                ToastUtils.show(context,"更多");
            }
        });
    }
    public void initFab(){
        //fab 打开关闭操作
        fragmentMainBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFabOpened) {
                    openMenu(v);
                }else {
                    closeMenu(v);
                }
            }
        });
    }
    /*
  * 打开蒙版菜单
  * */
    public void openMenu(View view){
        ObjectAnimator rotation=ObjectAnimator.ofFloat(view,"rotation",0,-155,-133);
        rotation.setDuration(500);
        rotation.start();
        fragmentMainBinding.includeMask.rlMask.setVisibility(View.VISIBLE);
        AlphaAnimation alphaAnimation=new AlphaAnimation(0,0.7f);
        alphaAnimation.setDuration(500);
        alphaAnimation.setFillAfter(true);
        fragmentMainBinding.includeMask.rlMask.startAnimation(alphaAnimation);
        fragmentMainBinding.includeMask.rlMask.setClickable(true);
        isFabOpened=true;
    }
    /*
    * 关闭蒙版菜单
    * */
    public void closeMenu(View view){
        ObjectAnimator rotation=ObjectAnimator.ofFloat(view,"rotation",-135,20,0);
        rotation.setDuration(500);
        rotation.start();
        AlphaAnimation alphaAnimation=new AlphaAnimation(0.7f,0);
        alphaAnimation.setDuration(500);
        fragmentMainBinding.includeMask.rlMask.startAnimation(alphaAnimation);
        fragmentMainBinding.includeMask.rlMask.setVisibility(View.GONE);
        fragmentMainBinding.includeMask.rlMask.setClickable(false);
        isFabOpened=false;
    }
    public void clickEvent(){
        fragmentMainBinding.includeMask.rlMask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu(fragmentMainBinding.fab);

            }
        });
        fragmentMainBinding.includeMask.tvWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "写文章了", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
