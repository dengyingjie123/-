package com.youngbook.entity.vo.oa.Information;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;


/**
 * 报送资料明细表
 */
@Table(name = "oa_InformationSubmittedItem2", jsonPrefix = "informationSubmittedItem2VO")
public class InformationSubmittedItem2VO extends BasePO {
    // sid
    @Id
    private int sid = Integer.MAX_VALUE;

    // id
    private String id = new String();

    // state
    private int state = Integer.MAX_VALUE;

    // operatorId
    private String operatorId = new String();

    // operateTime
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    //资料申请编号
    private String applicationId = new String();
    //kv组合 对应组名 OA_Information_Group
    private String dataForKVId= new String();
    //kv组合名称
    private String dataForKVName= new String();


    // 报送资料名称
    private String dataName = new String();

    // 报送资料备注
    private String dataComment = new String();
    //报送资料份数
    private String topies = new String();
    //	报送资料状态：0：内部使印，1：外带。
    private int status = Integer.MAX_VALUE;
    private String sentToAddress = new String();//	外带地点

    private String receiveId = new String();//	外带报送资料接收人编号
    private String receiveName = new String();// 外带接收人
    @DataAdapter(fieldType = FieldType.DATE)
    private String receiveTime = new String();//	外带报送资料接收时间
    private String receiveIsConfirm = new String();//	外带报送资料是否已接收：0未接收、1：以接收	int	11

    private String outBackId = new String();//	外带报送资料归还接收人编号
    private String outBackName = new String();//	外带报送资料归还接收人
    @DataAdapter(fieldType = FieldType.DATE)
    private String outBackTime = new String();//	外带报送资料归还接收时间
    private String outBackIsConfirm = new String();//	外带报送资料是否已经归还:0:未归还、1：以归还。	int	11


    //getset
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

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getDataComment() {
        return dataComment;
    }

    public void setDataComment(String dataComment) {
        this.dataComment = dataComment;
    }

    public String getTopies() {
        return topies;
    }

    public void setTopies(String topies) {
        this.topies = topies;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSentToAddress() {
        return sentToAddress;
    }

    public void setSentToAddress(String sentToAddress) {
        this.sentToAddress = sentToAddress;
    }

    public String getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }


    public String getOutBackId() {
        return outBackId;
    }

    public void setOutBackId(String outBackId) {
        this.outBackId = outBackId;
    }

    public String getOutBackTime() {
        return outBackTime;
    }

    public void setOutBackTime(String outBackTime) {
        this.outBackTime = outBackTime;
    }


    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getOutBackName() {
        return outBackName;
    }

    public void setOutBackName(String outBackName) {
        this.outBackName = outBackName;
    }

    public String getReceiveIsConfirm() {
        return receiveIsConfirm;
    }

    public void setReceiveIsConfirm(String receiveIsConfirm) {
        this.receiveIsConfirm = receiveIsConfirm;
    }

    public String getOutBackIsConfirm() {
        return outBackIsConfirm;
    }

    public void setOutBackIsConfirm(String outBackIsConfirm) {
        this.outBackIsConfirm = outBackIsConfirm;
    }

    public String getDataForKVId() {
        return dataForKVId;
    }

    public void setDataForKVId(String dataForKVId) {
        this.dataForKVId = dataForKVId;
    }

    public String getDataForKVName() {
        return dataForKVName;
    }

    public void setDataForKVName(String dataForKVName) {
        this.dataForKVName = dataForKVName;
    }
}
