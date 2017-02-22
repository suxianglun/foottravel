package com.footprint.travel.utils;

import java.util.UUID;

/**
 * @标题: .java
 * @概述:数据库获取一个唯一的主键id:UUID
 * @作者: Allen
 * @日期: 2016/12/15 @版本：
 */
public class UUIDGenerator  {
    public UUIDGenerator() {
    }
    /**
     * 获得一个UUID
     * @return String UUID  UUID是由一个十六进制形式的数字组成,表现出来的形式例如550E8400-E29B-11D4-A716-446655440000
     */
    public static String getUUID(){
        String s = UUID.randomUUID().toString();
        //去掉“-”符号
        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
    }
    /**
     * 获得指定数目的UUID
     * @param number int 需要获得的UUID数量
     * @return String[] UUID数组
     */
    public static String[] getUUID(int number){
        if(number < 1){
            return null;
        }
        String[] ss = new String[number];
        for(int i=0;i<number;i++){
            ss[i] = getUUID();
        }
        return ss;
    }
}
