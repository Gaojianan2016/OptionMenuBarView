package com.gjn.optionmenubarlibrary;

/**
 * @author gjn
 * @time 2018/7/19 16:10
 */

public class MenuItem {
    private String name;
    private Object img;

    public MenuItem() {
    }

    public MenuItem(String name) {
        this.name = name;
    }

    public MenuItem(String name, Object img) {
        this.name = name;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getImg() {
        return img;
    }

    public void setImg(Object img) {
        this.img = img;
    }
}
