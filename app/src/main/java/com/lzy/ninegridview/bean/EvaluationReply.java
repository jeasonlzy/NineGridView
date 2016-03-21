package com.lzy.ninegridview.bean;

import java.io.Serializable;

/** 评价列表的回复详情 */
public class EvaluationReply implements Serializable {
    public int erid;             //回复id
    public String erContent;    //回复内容
    public String erReplyuser;  //回复人姓名
    public String erReplytime;  //回复时间

    public int getErid() {
        return erid;
    }

    public void setErid(int erid) {
        this.erid = erid;
    }

    public String getErContent() {
        return erContent;
    }

    public void setErContent(String erContent) {
        this.erContent = erContent;
    }

    public String getErReplyuser() {
        return erReplyuser;
    }

    public void setErReplyuser(String erReplyuser) {
        this.erReplyuser = erReplyuser;
    }

    public String getErReplytime() {
        return erReplytime;
    }

    public void setErReplytime(String erReplytime) {
        this.erReplytime = erReplytime;
    }
}
