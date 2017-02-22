package com.footprint.travel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.footprint.travel.R;
import com.footprint.travel.customview.GlideRoundTransform;

/**
 * @标题: .java
 * @概述:
 * @作者: Allen
 * @日期: 2016/12/21 @版本：
 */
public class ViewHolder extends RecyclerView.ViewHolder {
    //    SparseArray类似HaspMap不过比HashMap节省空间，方法都差不多，id和控件对应
    private SparseArray<View> mViews;
    private View mConvertView;
    private Context context;

    public ViewHolder(Context context,View itemView)
    {
        super(itemView);
        mViews = new SparseArray<View>();
        mConvertView=itemView;
        this.context=context;
    }

    //根据id和内容进行摆放
    public void setText(int viewId,String msg){
        TextView textView=getView(viewId);
        textView.setText(msg);
    }
    public void setBitmap(int viewId, String url){
        ImageView imageView=getView(viewId);
        Glide.with(context)
                .load(url)
                .centerCrop()
                .placeholder(R.mipmap.bg_navigation)
                .error(R.mipmap.bg_navigation)
                .crossFade()
                .transform(new GlideRoundTransform(context, 5))
                .into( imageView);
    }
    public void setOnClickListener(int viewId, View.OnClickListener onClickListener){
        View view=getView(viewId);
        view.setOnClickListener(onClickListener);
    }
    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId)
    {
        View view = mViews.get(viewId);
        if (view == null)
        {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }
}
