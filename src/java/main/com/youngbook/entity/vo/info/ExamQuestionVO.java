package com.youngbook.entity.vo.info;

import com.youngbook.annotation.*;
import com.youngbook.entity.po.info.ExamOptionPO;
import com.youngbook.entity.vo.BaseVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：这是培训管理->试题对应的VO类。
 * 时间：2015-04-14
 * 创建人：张舜清
 * 修改描述：
 * 修改时间：
 * 修改人：
 * 参考需求：<a href="http://c.hopewealth.net/pages/viewpage.action?pageId=25198880" target="_blank">链接</a>
 * @author 张舜清
 */
@Table(name = "Info_ExamQuestion", jsonPrefix = "examQuestionVO")
public class ExamQuestionVO extends BaseVO{
    // sid
    @Id
    private int sid = Integer.MAX_VALUE;

    // id
    private String id = new String();

    // state
    private int state = Integer.MAX_VALUE;

    // operatorId
    private String operatorId = new String();

    //操作员
    private String operatorName = new String();

    // operateTime
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    // 标题
    private String title = new String();

    // 问题 : 支持查询,必填
    private String question = new String();

    // 类型 : 必填
    private String type = new String();

    // 创建时间 : 时间类型
    @DataAdapter(fieldType = FieldType.DATE)
    private String time = new String();

    // KV类型
    private String typeName = new String();
    private String paperId = new String();
    private String paperName = new String();

    private int questionNO = Integer.MAX_VALUE;

    private int questionCount = Integer.MAX_VALUE;

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    @IgnoreDB
    private List<ExamOptionPO> examOptionPOs = new ArrayList<ExamOptionPO>();

    public List<ExamOptionPO> getExamOptionPOs() {
        return examOptionPOs;
    }

    public void setExamOptionPOs(List<ExamOptionPO> examOptionPOs) {
        this.examOptionPOs = examOptionPOs;
    }

    public int getQuestionNO() {
        return questionNO;
    }

    public void setQuestionNO(int questionNO) {
        this.questionNO = questionNO;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    // get和set
    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
