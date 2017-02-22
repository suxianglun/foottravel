package com.footprint.travel.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.footprint.travel.R;

import java.util.List;

import static com.footprint.travel.base.BaseApplication.context;

/**
 * @标题: .java
 * @概述:
 * @作者: Allen
 * @日期: 2016/12/21 @版本：
 */
public abstract class CommonAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_FOOTER = 0;//加载更多
    private static final int TYPE_NODATA=1;//空数据
    private static final int TYPE_ITEM = 2;

    //正在加载中
    public static final int  LOADING_MORE=0;
    //无更多数据加载
    public static final int  CANNOT_LOAD_MORE=1;
    //上拉加载更多状态-默认为0
    public int load_more_status=0;


    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;


    public CommonAdapter(Context context, int layoutId, List<T> datas) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        if (viewType == TYPE_FOOTER) {
            View view = mInflater.inflate(R.layout.item_recyclerview_foot, parent, false);
            return new FooterViewHolder(view);
        }else if (viewType == TYPE_NODATA){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_no_data, parent, false);
            return new NODataViewHoler(view);
        }else {
            View view = mInflater.inflate(mLayoutId, parent, false);
            return new ViewHolder(mContext,view);
        }


    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CommonAdapter.FooterViewHolder){
            ((FooterViewHolder) holder).tv_state.setText(load_more_status==LOADING_MORE ?"正在加载中":"无更多数据");
            ((FooterViewHolder) holder).ll_state.setVisibility(load_more_status==LOADING_MORE ?View.VISIBLE:View.INVISIBLE);
        }else {
            if (position < mDatas.size()) {
                convert((ViewHolder) holder, mDatas.get(position));
            }
        }
    }
    //    摆放数据只能在用的时候才写
    public abstract void convert(ViewHolder holder, T t);

    @Override
    public int getItemCount() {
        return mDatas.size() + 1;
    }

    //是底部iewType为TYPE_FOOTER，不是底部viewType为TYPE_ITEM
    @Override
    public int getItemViewType(int position) {
        if (mDatas==null||mDatas.size()<=0){//空数据类型
            return TYPE_NODATA;
        }else if (position == getItemCount()-1) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    public void setmDatas(List<T> mDatas){
        this.mDatas=mDatas;
        notifyDataSetChanged();
    }
    public void setLoadState(int state ){
        this.load_more_status=state;
        notifyDataSetChanged();
    }
    //    底部的ViewHolder
    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_state;
        public LinearLayout ll_state;
        public FooterViewHolder(View itemView) {
            super(itemView);
            tv_state=(TextView)itemView.findViewById(R.id.tvState);
            ll_state=(LinearLayout)itemView.findViewById(R.id.ll_state);
        }

    }
    public  class NODataViewHoler extends RecyclerView.ViewHolder{
        public TextView tv_no_data;

        public NODataViewHoler(View itemView) {
            super(itemView);
            tv_no_data=(TextView)itemView.findViewById(R.id.tv_no_data);

        }
    }
}

