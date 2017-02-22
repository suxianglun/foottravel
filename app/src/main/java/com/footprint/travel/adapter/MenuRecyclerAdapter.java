package com.footprint.travel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
public class MenuRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<MainMenu> mainMenus;
    public Context context;
    public RecyclerViewItemClickListener recyclerViewItemClickListener;

    public MenuRecyclerAdapter(Context context, List<MainMenu> mainMenus) {
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
        convertView= LayoutInflater.from(context).inflate(R.layout.item_mainmenu_recyclerview,parent,false);
        ItemViewHolder itemViewHolder=new ItemViewHolder(convertView);
        return itemViewHolder;
    }

    @Override
    public int getItemCount() {
        return mainMenus.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        ((ItemViewHolder)holder).iv_menulogo.setImageResource(mainMenus.get(position).getMenulogo());
        ((ItemViewHolder)holder).tv_menuname.setText(mainMenus.get(position).getMenuName());
        ((ItemViewHolder)holder).ll_main_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recyclerViewItemClickListener!=null){
                    recyclerViewItemClickListener.onItemClick(position);
                }
            }
        });

    }
    public class ItemViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_menuname;
        public ImageView iv_menulogo;
        public LinearLayout ll_main_menu;

        public ItemViewHolder(View view) {
            super(view);
            tv_menuname=(TextView)view.findViewById(R.id.tv_menu_name);
            iv_menulogo=(ImageView)view.findViewById(R.id.iv_menu_logo);
            ll_main_menu=(LinearLayout)view.findViewById(R.id.ll_main_menu);

        }
    }
    public void setOnItemClickListener(RecyclerViewItemClickListener recyclerItemOnClickListener){
        this.recyclerViewItemClickListener=recyclerItemOnClickListener;

    }
}
