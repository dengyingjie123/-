package com.youngbook.entity.wvo;

/**
 * Created by 邓超
 * Date 2015-5-28
 */
public class ProtectionQuestionWVO extends BaseWVO {

    private Integer questionId;
    private String questionContent;

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }
}
