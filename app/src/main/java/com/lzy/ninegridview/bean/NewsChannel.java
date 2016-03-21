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
public class NewsChannel implements Serializable {
    private String channelId;
    private String name;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "NewsChannel{" +
                "channelId='" + channelId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
