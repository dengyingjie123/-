package com.youngbook.entity.vo.cms;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 10/17/14
 * Time: 10:18 AM
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "cms_article", jsonPrefix = "article")
public class ArticleVO extends BaseVO {

    // sid
    @Id
    private int sid = Integer.MAX_VALUE;

    // id
    private String id = new String();

    // state
    private int state = Integer.MAX_VALUE;

    private String title = new String();

    private String columnId = new String();

    @DataAdapter(fieldType = FieldType.DATE)
    private String publishedTime = new String();

    private String content = new String();

    private String orders = new String();

    private String operatorId = new String();

    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    private String columnName = new String();
    private String summaryHtml = "";
    private String summaryText = "";

    // 积分
    private int score = Integer.MAX_VALUE;

    // 缩略图
    private String image = "";

    private String bizId = "";

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSummaryHtml() {
        return summaryHtml;
    }

    public void setSummaryHtml(String summaryHtml) {
        this.summaryHtml = summaryHtml;
    }

    public String getSummaryText() {
        return summaryText;
    }

    public void setSummaryText(String summaryText) {
        this.summaryText = summaryText;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(String publishedTime) {
        this.publishedTime = publishedTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public String getColumnName() {return columnName;}

    public void setColumnName(String columnName) {this.columnName = columnName;}
}
