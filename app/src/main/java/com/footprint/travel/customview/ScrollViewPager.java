package com.footprint.travel.customview;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.footprint.travel.adapter.MainViewPageAdapter;
import com.footprint.travel.base.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;


/**
 * 默认无手势滑动的viewpager
 * 可获取滑动状态
 * 设置是否可滑动
 * 自己维护fragment
 * Created by allen
 */
public class ScrollViewPager extends ViewPager {
    private boolean scrollble = false;
    private Map<Fragment, Stack<Fragment>> fragmentStackMap;//多个维护栈集合
    private int currentItem = 0;//当前fragment
    public MainViewPageAdapter mainPagerAdapter;//viewpager 适配器
    private List<BaseFragment> fragments;
    private boolean isCanScroll = true;
    private FragmentManager fragmentManager;
    //拓展字段
    private int position = -1;


    public ScrollViewPager(Context context) {
        super(context);
        init();
    }

    public ScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        fragmentStackMap = new HashMap<>();
        fragments = new ArrayList<>();
        setOffscreenPageLimit(5);//缓存5项

    }

    //显示fragment,如果没有就加入
    public void showFragment(BaseFragment fragment) {
        if (fragment == null)
            return;

        if (fragmentStackMap.containsKey(fragment)) {
            Stack<Fragment> stack = fragmentStackMap.get(fragment);
            if (stack != null && stack.size() > 0) {
                currentItem = getItem(stack.lastElement());
                currentItem(currentItem);
            }
        } else {
            addFragment(fragment);
            fragment.setViewPager(this);
            //显示fragment
            currentItem = getItem(fragment);
            currentItem(currentItem);
        }
    }

    //添加fragment,并显示出来
    public void addFragment(BaseFragment fragment) {
        if (fragment == null)
            return;

        Fragment targetFragment = fragment.getCreateFragment();
        if (fragmentStackMap.containsKey(targetFragment)) {
            //如果已经添加过,弹出当前栈
            Stack<Fragment> stack = fragmentStackMap.get(targetFragment);
            if (!stack.contains(fragment))
                stack.add(fragment);
        } else {
            Stack<Fragment> stack = new Stack<>();
            stack.add(fragment);
            fragmentStackMap.put(targetFragment == null ? fragment : targetFragment, stack);
        }
        //将fragment  添加到viewpager中
        if (!fragments.contains(fragment)) {
            fragments.add(fragment);
            mainPagerAdapter.notifyDataSetChanged();
        }

    }

    public void setPosition(int position) {
        this.position = position;
    }

    //回退fragment,返回时使用,用于此fragment是否是第二层
    public boolean backFragment() {
        if (fragmentStackMap.isEmpty())
            return false;
        //回退 fragment
        BaseFragment fragment = fragments.get(currentItem);
        if (fragment == null || fragmentStackMap.containsKey(fragment)) {
            //此为最后一个
            return false;
        } else {
            BaseFragment targetFragment = fragment.getCreateFragment();
            //如果绑定fragment 可以找到
            if (targetFragment != null && fragmentStackMap.containsKey(targetFragment)) {
                Stack<Fragment> fragmentStack = fragmentStackMap.get(targetFragment);
                if (fragmentStack.size() > 1) {
                    fragmentStack.pop();
                    if (position > -1) {
                        position = -1;
                    }
                    currentItem = getItem(fragmentStack.lastElement());
                    currentItem(currentItem);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    //回收所有fragment
    public void destory() {
        Iterator<Fragment> iterator = fragmentStackMap.keySet().iterator();
        while (iterator.hasNext()) {
            Fragment next = iterator.next();
            Stack<Fragment> fragments = fragmentStackMap.get(next);
            fragments.clear();
        }
    }

    private int getItem(Fragment fragment) {
        return fragments.indexOf(fragment);
    }

    //设置fragment管理器
    public void setManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        mainPagerAdapter = new MainViewPageAdapter(fragmentManager, fragments);
        setAdapter(mainPagerAdapter);
        setCurrentItem(currentItem);
    }

    //获取当前fragment
    public Fragment getCurrentFragment() {
        return fragments.get(currentItem);
    }

    /**
     * 选择current
     *
     * @param item
     */
    private void currentItem(int item) {
        setCurrentItem(item, scrollble);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!scrollble) {
            return true;
        }

        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!scrollble) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isScrollble() {
        return scrollble;
    }

    public void setScrollble(boolean scrollble) {
        this.scrollble = scrollble;
    }

    @Override
    public void scrollTo(int x, int y) {
        if (isCanScroll) {
            super.scrollTo(x, y);
        }
    }
}
