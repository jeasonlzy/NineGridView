package com.lzy.ninegridview.model.evaluation.bean;

import java.io.Serializable;
import java.util.ArrayList;

/** 评价详情 */
public class Evaluation implements Serializable{

    public int totalCount;   // 总评论数
    public int pageNo;       // 页号
    public int pageCount;
    public int goodCount;
    public int badCount;
    public int middleCount;
    public String goodPD;    // 好评率
    public ArrayList<EvaluationItem> evaluataions;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(int goodCount) {
        this.goodCount = goodCount;
    }

    public int getBadCount() {
        return badCount;
    }

    public void setBadCount(int badCount) {
        this.badCount = badCount;
    }

    public int getMiddleCount() {
        return middleCount;
    }

    public void setMiddleCount(int middleCount) {
        this.middleCount = middleCount;
    }

    public String getGoodPD() {
        return goodPD;
    }

    public void setGoodPD(String goodPD) {
        this.goodPD = goodPD;
    }

    public ArrayList<EvaluationItem> getEvaluataions() {
        return evaluataions;
    }

    public void setEvaluataions(ArrayList<EvaluationItem> evaluataions) {
        this.evaluataions = evaluataions;
    }

    @Override
    public String toString() {
        return "Evaluation{" +
                "totalCount=" + totalCount +
                ", pageNo=" + pageNo +
                ", pageCount=" + pageCount +
                ", goodCount=" + goodCount +
                ", badCount=" + badCount +
                ", middleCount=" + middleCount +
                ", goodPD='" + goodPD + '\'' +
                ", evaluataions=" + evaluataions +
                '}';
    }
}
