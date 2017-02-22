package com.footprint.travel.entity;

/**
 * @标题: .java
 * @概述:
 * @作者: Allen
 * @日期: 2016/12/20 @版本：
 */
public class Scenery {


    /**
     * title : 郑州黄河风景名胜区
     * grade : AAAA
     * price_min : 54
     * comm_cnt : null
     * cityId : 163
     * address : 郑州市黄河南岸郑州黄河风景名胜区
     * sid : 28411
     * url : http://www.ly.com/scenery/BookSceneryTicket_28411.html
     * imgurl : http://pic4.40017.cn/scenery/destination/2016/11/09/15/WkaQ8y_240x135_00.jpg
     */

    private String title;
    private String grade;
    private String price_min;
    private Object comm_cnt;
    private String cityId;
    private String address;
    private String sid;
    private String url;
    private String imgurl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getPrice_min() {
        return price_min;
    }

    public void setPrice_min(String price_min) {
        this.price_min = price_min;
    }

    public Object getComm_cnt() {
        return comm_cnt;
    }

    public void setComm_cnt(Object comm_cnt) {
        this.comm_cnt = comm_cnt;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
