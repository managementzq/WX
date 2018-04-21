package com.nbkj.wchatdemo.model.menu;

import lombok.Data;

/**
 * @author :hsj
 * @description
 * @date :2018/4/21
 */
@Data
public class Button {
    //菜单类型
    private String type;
    //菜单名称
    private String name;
    //二级菜单
    private Button[] sub_button;
}
