package com.footprint.travel.entity;

import cn.bmob.v3.BmobUser;

/**
 * @标题: .java
 * @概述:
 * @作者: Allen
 * @日期: 2016/12/15 @版本：
 */
public class User extends BmobUser {
    private String userid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
