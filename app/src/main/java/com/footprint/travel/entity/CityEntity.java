package com.footprint.travel.entity;

/**
 * @标题: CityEntity.java
 * @概述: 城市列表用的city
 * @作者: Allen
 * @日期: 2016/12/29 @版本：
 */
public class CityEntity {
    public String name;
    public String pinyi;

    public CityEntity(String name, String pinyi) {
        super();
        this.name = name;
        this.pinyi = pinyi;
    }

    public CityEntity() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyi() {
        return pinyi;
    }

    public void setPinyi(String pinyi) {
        this.pinyi = pinyi;
    }

}
