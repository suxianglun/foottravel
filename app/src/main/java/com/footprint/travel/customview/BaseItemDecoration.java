package com.footprint.travel.customview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @标题: .java
 * @概述: TODO
 * @作者: Allen
 * @日期: 2016-4-1 @版本： V1.2.9
 */
public class BaseItemDecoration extends RecyclerView.ItemDecoration{
    private int space;

    public BaseItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if(parent.getChildPosition(view) != 0)
            outRect.top = space;
    }
}
