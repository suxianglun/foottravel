package com.footprint.travel.listener;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import com.footprint.travel.callback.LoadMoreCallback;
import com.footprint.travel.utils.LogUtil;

/**
 * Created by wangenning on 15/11/20.
 */
public class RecyclerViewOnScroll extends RecyclerView.OnScrollListener {
    RecyclerView.Adapter mMyadputer;
    LoadMoreCallback mLodeMoreCallBack;
    int lastVisibleItem = 0;
    int firstVisibleItem = 0;
    private int mScrollThreshold;
    private boolean isUp;//是否是上滑
    private int scrollInt = 0;

    public RecyclerViewOnScroll(RecyclerView.Adapter mMyadputer, LoadMoreCallback mLodeMoreCallBack) {
        this.mMyadputer = mMyadputer;
        this.mLodeMoreCallBack = mLodeMoreCallBack;
    }

    public RecyclerViewOnScroll setScrollInt(int scrollInt) {
        this.scrollInt = scrollInt;
        return this;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        boolean isSignificantDelta = Math.abs(dy) > mScrollThreshold;
        if (isSignificantDelta) {
            if (dy > 0) {
                isUp = true;
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                int totalItemCount = layoutManager.getItemCount();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearLayoutManager = ((LinearLayoutManager) layoutManager);
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    firstVisibleItem = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                } else if (layoutManager instanceof GridLayoutManager) {
                    GridLayoutManager gridLayoutManager = ((GridLayoutManager) layoutManager);
                    //通过LayoutManager找到当前显示的最后的item的position
                    lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();
                    firstVisibleItem = gridLayoutManager.findFirstCompletelyVisibleItemPosition();
                } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                    StaggeredGridLayoutManager staggeredGridLayoutManager = ((StaggeredGridLayoutManager) layoutManager);
                    //因为StaggeredGridLayoutManager的特殊性可能导致最后显示的item存在多个，所以这里取到的是一个数组
                    //得到这个数组后再取到数组中position值最大的那个就是最后显示的position值了
                    int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                    staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                    lastVisibleItem = findMax(lastPositions);
                    firstVisibleItem = staggeredGridLayoutManager.findFirstVisibleItemPositions(lastPositions)[0];
                }
            } else {
                isUp = false;
            }
        }

    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        //获取总的适配器的数量
        int totalCount = mMyadputer.getItemCount();
        LogUtil.d("allen","isUp====="+isUp+"--newState==="+newState+"--lastVisibleItem==="+lastVisibleItem+"--ItemCount==="+mMyadputer.getItemCount());
        //这个就是判断当前滑动停止了，并且获取当前屏幕最后一个可见的条目是第几个，当前屏幕数据已经显示完毕的时候就去加载数据
        if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == (mMyadputer.getItemCount() - scrollInt)) {
            //回调加载更多
            if (isUp&&mLodeMoreCallBack!=null) {
                mLodeMoreCallBack.loadMore();
            }
        }
    }
    //找到数组中的最大值

    private int findMax(int[] lastPositions) {

        int max = lastPositions[0];
        for (int value : lastPositions) {
            //       int max    = Math.max(lastPositions,value);
            if (value > max) {
                max = value;
            }
        }
        return max;
    }


}
