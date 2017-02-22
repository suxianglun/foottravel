package com.footprint.travel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.footprint.travel.R;
import com.footprint.travel.entity.MainMenu;
import com.footprint.travel.listener.RecyclerViewItemClickListener;

import java.util.List;

/**
 * @标题: MenuRecyclerAdapter.java
 * @概述: 首页 菜单 recyclierAdapter
 * @作者: Allen
 * @日期: 2016/10/12 @版本：
 */
public class MainFragmentRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<MainMenu> mainMenus;
    public Context context;
    public RecyclerViewItemClickListener recyclerViewItemClickListener;

    public MainFragmentRecyclerAdapter(Context context, List<MainMenu> mainMenus) {
        this.context=context;
        this.mainMenus=mainMenus;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView;
        convertView= LayoutInflater.from(context).inflate(R.layout.item_main_fragmnet_reycylerview,parent,false);
        ItemViewHolder itemViewHolder=new ItemViewHolder(convertView);
        return itemViewHolder;
    }

    @Override
    public int getItemCount() {
        return mainMenus.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        ((ItemViewHolder)holder).tv_content.setText(context.getResources().getString(R.string.card_string));
        ((ItemViewHolder)holder).tv_title.setText("CardView");
//        ((ItemViewHolder)holder).ll_main_menu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (recyclerViewItemClickListener!=null){
//                    recyclerViewItemClickListener.onItemClick(position);
//                }
//            }
//        });

    }
    public class ItemViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_title;
        public TextView tv_content;

        public ItemViewHolder(View view) {
            super(view);
            tv_title=(TextView)view.findViewById(R.id.tv_title);
            tv_content=(TextView)view.findViewById(R.id.tv_content);

        }
    }
    public void setOnItemClickListener(RecyclerViewItemClickListener recyclerItemOnClickListener){
        this.recyclerViewItemClickListener=recyclerItemOnClickListener;

    }
}
