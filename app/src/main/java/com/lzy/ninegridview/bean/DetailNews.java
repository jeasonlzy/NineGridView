package com.lzy.ninegridview.bean;

import java.util.List;

/**
 * ================================================
 * 作    者：廖子尧
 * 版    本：1.0
 * 创建日期：2016/3/16
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class DetailNews {
    private String title;
    private List<ImageDetail> imageDetails;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ImageDetail> getImageDetails() {
        return imageDetails;
    }

    public void setImageDetails(List<ImageDetail> imageDetails) {
        this.imageDetails = imageDetails;
    }
}
