package com.footprint.travel.utils;

import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import java.lang.reflect.Field;

/**
 * @标题: BottomNavigationViewHelper.java
 * @概述: 处理BottomNavigationButton缩放
 * @作者: Allen
 * @日期: 2016/11/23 @版本：
 */
public class BottomNavigationViewHelper {
    public static  void disableShiftMode(BottomNavigationView bottomNavigationView) throws NoSuchFieldException, IllegalAccessException {
        BottomNavigationMenuView menuView=(BottomNavigationMenuView)bottomNavigationView.getChildAt(0);
        try {
            Field mShiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            mShiftingMode.setAccessible(true);
            mShiftingMode.setBoolean(menuView,false);
            for (int i=0;i<menuView.getChildCount();i++){
                 BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
                itemView.setShiftingMode(false);
                itemView.setChecked(itemView.getItemData().isChecked());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
