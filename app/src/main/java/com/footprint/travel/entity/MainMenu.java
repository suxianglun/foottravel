package com.footprint.travel.entity;

import java.io.Serializable;

/**
 * @标题: .java
 * @概述:
 * @作者: Allen
 * @日期: 2016/10/12 @版本：
 */
public class MainMenu implements Serializable {
    public String menuName;
    public int menulogo;

    @Override
    public String toString() {
        return "MainMenu{" +
                "menuName='" + menuName + '\'' +
                ", menulogo=" + menulogo +
                '}';
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getMenulogo() {
        return menulogo;
    }

    public void setMenulogo(int menulogo) {
        this.menulogo = menulogo;
    }
}
