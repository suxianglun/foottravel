package com.footprint.travel.utils;

/**
 * @标题: Utils.java
 * @概述: 工具类
 * @作者: Allen
 * @日期: 2016/11/23 @版本：
 */
public class Utils {
    private static long currentTime=0;
    private static long lastTime=0;
    /*
    * 判断在时间间隔spceTime内是否为快速多次点击，用于防止快速重复点击
    * @ spaceTime 间隔时间
    * @ return true:是快速多次点击  false:不是快速多次点击
    * */
    public  static  boolean isFastDoubleClick(long spaceTime){
        currentTime=System.currentTimeMillis();
        boolean result;
        if (currentTime-lastTime>spaceTime){
            result=false;
        }else {
            result=true;
        }
        lastTime=currentTime;
        return  result;
    }
}
