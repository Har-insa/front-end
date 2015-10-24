package com.hardis.connect.model;


public class NavDrawerItem {
    private boolean showNotify;
    private String title;
    int imgResID;

    public NavDrawerItem() {

    }

    public NavDrawerItem(boolean showNotify, String title, int imgResID) {
        this.showNotify = showNotify;
        this.title = title;
        this.imgResID = imgResID;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public int getImgResID() {
        return imgResID;
    }
    public void setImgResID(int imgResID) {
        this.imgResID = imgResID;
    }
}
