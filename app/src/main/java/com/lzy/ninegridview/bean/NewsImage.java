package com.lzy.ninegridview.bean;

import java.io.Serializable;

/**
 * ================================================
 * 作    者：廖子尧
 * 版    本：1.0
 * 创建日期：2016/3/20
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class NewsImage implements Serializable {
    private int width;
    private int height;
    private String url;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "NewsImage{" +
                "width=" + width +
                ", height=" + height +
                ", url='" + url + '\'' +
                '}';
    }
}
