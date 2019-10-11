package com.syc.perms.pojo;

import lombok.Data;

@Data
public class TbMenus {
    private Long menuId;

    private String title;

    private String icon;

    private String href;

    private String perms;

    private String spread;

    private Long parentId;

    private Long sorting;

    private String checked;

    private boolean isOpen=false;


}