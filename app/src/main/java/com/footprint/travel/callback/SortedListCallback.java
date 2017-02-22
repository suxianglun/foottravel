package com.footprint.travel.callback;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;

import com.footprint.travel.entity.Scenery;

/**
 * 介绍：比较规则Callback。
 * 和DiffUtil.Callback。写法套路一毛一样。
 * 而且比DiffUtil.Callback简单。
 * 因为不用传数据集进来，每次直接给你Item比较。
 *
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2016/11/29.
 */

public class SortedListCallback extends SortedListAdapterCallback<Scenery> {
    /**
     * Creates a {@link android.support.v7.util.SortedList.Callback} that will forward data change events to the provided
     * Adapter.
     *
     * @param adapter The Adapter instance which should receive events from the SortedList.
     */
    public SortedListCallback(RecyclerView.Adapter adapter) {
        super(adapter);
    }

    /**
     * 把它当成equals 方法就好
     */
    @Override
    public int compare(Scenery o1, Scenery o2) {
        return Integer.parseInt(o1.getSid())-Integer.parseInt(o1.getSid());
    }

    /**
     * 和DiffUtil方法一致，不再赘述
     */
    @Override
    public boolean areItemsTheSame(Scenery item1, Scenery item2) {
        return item1.getSid() == item2.getSid();
    }
    /**
     * 和DiffUtil方法一致，不再赘述
     */
    @Override
    public boolean areContentsTheSame(Scenery oldItem, Scenery newItem) {
        //默认相同 有一个不同就是不同
        if (oldItem.getSid() != newItem.getSid()) {
            return false;
        }
        if (oldItem.getTitle().equals(newItem.getTitle())) {
            return false;
        }
        if (oldItem.getImgurl() != newItem.getImgurl()) {
            return false;
        }
        return true;
    }


}
