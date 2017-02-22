package com.footprint.travel.entity;

/**
 * @标题: .java
 * @概述:
 * @作者: Allen
 * @日期: 2016/12/20 @版本：
 */
public class City {
    /**
     * cityId : 2
     * cityName : 安徽
     * provinceId : 2
     */

    private String cityId;
    private String cityName;
    private String provinceId;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }
}
