package com.lzy.ninegridview.bean;

import java.io.Serializable;
import java.util.List;

/**
 * ================================================
 * 作    者：廖子尧
 * 版    本：1.0
 * 创建日期：2016/3/20
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class NewsContent implements Serializable {
    private String channelId;
    private String channelName;
    private String desc;
    private String link;
    private String nid;
    private String pubDate;
    private String source;
    private String title;
    private List<NewsImage> imageurls;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<NewsImage> getImageurls() {
        return imageurls;
    }

    public void setImageurls(List<NewsImage> imageurls) {
        this.imageurls = imageurls;
    }

    @Override
    public String toString() {
        return "NewsContent{" +
                "channelId='" + channelId + '\'' +
                ", channelName='" + channelName + '\'' +
                ", desc='" + desc + '\'' +
                ", link='" + link + '\'' +
                ", nid='" + nid + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", source='" + source + '\'' +
                ", title='" + title + '\'' +
                ", imageurls=" + imageurls +
                '}';
    }
}


