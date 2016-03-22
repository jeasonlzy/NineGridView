package com.lzy.ninegridview.model.evaluation.bean;

import java.io.Serializable;
import java.util.List;

/** 评论列表 */
public class EvaluationItem implements Serializable {
    public int anonymous;                   // 是否匿名，0不匿名，1匿名
    public List<EvaluationPic> attachments; // 评论图片列表
    public Avatar avatar;
    public String content;                  // 内容信息
    public String creatTime;                // 评论时间
    public int evaluationId;                // 评论id
    public int grade;                       // 评分
    public int sid;                         // 商品id
    public String orderId;                  // 小区id
    public String userName;
    public List<EvaluationReply> evaluatereplys;//回复列表内容

    public int getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(int anonymous) {
        this.anonymous = anonymous;
    }

    public List<EvaluationPic> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<EvaluationPic> attachments) {
        this.attachments = attachments;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public int getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(int evaluationId) {
        this.evaluationId = evaluationId;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<EvaluationReply> getEvaluatereplys() {
        return evaluatereplys;
    }

    public void setEvaluatereplys(List<EvaluationReply> evaluatereplys) {
        this.evaluatereplys = evaluatereplys;
    }
}
