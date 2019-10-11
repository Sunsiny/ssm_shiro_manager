package com.syc.perms.pojo;

import lombok.Data;

import java.util.List;

/**
 * 左侧导航栏的菜单效果:封装这个类主要是为了tree树形控件
 */
@Data
public class Menu {

    private String title;

    private String icon;

    private String href;

    private String spread;
    //子菜单
    private List<Menu> children;

}
