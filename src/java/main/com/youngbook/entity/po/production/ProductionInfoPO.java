package com.youngbook.entity.po.production;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * Created by Administrator on 2015/4/9.
 */
@Table(name = "CRM_ProductionInfo", jsonPrefix = "productionInfo")
public class ProductionInfoPO extends BasePO{
    /**
     * sid
     */
    @Id
    private int sid = Integer.MAX_VALUE;

    /**
     * id
     */
    private String id = new String();

    /**
     * state
     */
    private int state = Integer.MAX_VALUE;

    /**
     * operatorId
     */
    private String operatorId = new String();

    /**
     * operateTime
     */
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    /**
     * 产品编号
     */
    private String productionId = new String();

    /**
     * 产品描述
     */
    private String description = new String();

    /**
     * 产品标题
     */
    private String title1 = new String();

    /**
     * 产品描述
     */
    private String content1= new String();

    /**
     * 是否显示
     */
    private String isDisplay1 = "";


    /**
     * 产品标题
     */
    private String title2 = new String();

    /**
     * 产品描述
     */
    private String content2= new String();

    /**
     * 是否显示
     */
    private String isDisplay2 = "";

    /**
     * 产品标题
     */
    private String title3 = new String();

    /**
     * 产品描述
     */
    private String content3= new String();

    /**
     * 是否显示
     */
    private String isDisplay3 = "";


    /**
     * 产品标题
     */
    private String title4 = new String();

    /**
     * 产品描述
     */
    private String content4= new String();

    /**
     * 是否显示
     */
    private String isDisplay4 = "";



    /**
     * 产品标题
     */
    private String title5 = new String();

    /**
     * 产品描述
     */
    private String content5= new String();

    /**
     * 是否显示
     */
    private String isDisplay5 = "";
    /**
     * 产品标题
     */
    private String title6 = new String();

    /**
     * 产品描述
     */
    private String content6 =new String();

    /**
     * 是否显示
     */
    private String isDisplay6 = "";
    /**
     * 产品标题
     */
    private String title7 = new String();

    /**
     * 产品描述
     */
    private String content7= new String();

    /**
     * 是否显示
     */
    private String isDisplay7 = "";
    /**
     * 产品标题
     */
    private String title8 = new String();

    /**
     * 产品描述
     */
    private String content8= new String();

    /**
     * 是否显示
     */
    private String isDisplay8 = "";
    /**
     * 产品标题
     */
    private String title9 = new String();

    /**
     * 产品描述
     */
    private String content9= new String();

    /**
     * 是否显示
     */
    private String isDisplay9 = "";
    /**
     * 产品标题
     */
    private String title10 = new String();

    /**
     * 产品描述
     */
    private String content10= new String();

    /**
     * 是否显示
     */
    private String isDisplay10 = "";

    private String websiteDisplayName = new String();

    /**
     * 以下是属性对应的get和set方法
     */
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

    public String getProductionId() {
        return productionId;
    }

    public void setProductionId(String productionId) {
        this.productionId = productionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getContent1() {
        return content1;
    }

    public void setContent1(String content1) {
        this.content1 = content1;
    }

    public String getIsDisplay1() {
        return isDisplay1;
    }

    public void setIsDisplay1(String isDisplay1) {
        this.isDisplay1 = isDisplay1;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public String getContent2() {
        return content2;
    }

    public void setContent2(String content2) {
        this.content2 = content2;
    }

    public String getIsDisplay2() {
        return isDisplay2;
    }

    public void setIsDisplay2(String isDisplay2) {
        this.isDisplay2 = isDisplay2;
    }

    public String getTitle3() {
        return title3;
    }

    public void setTitle3(String title3) {
        this.title3 = title3;
    }

    public String getContent3() {
        return content3;
    }

    public void setContent3(String content3) {
        this.content3 = content3;
    }

    public String getIsDisplay3() {
        return isDisplay3;
    }

    public void setIsDisplay3(String isDisplay3) {
        this.isDisplay3 = isDisplay3;
    }

    public String getTitle4() {
        return title4;
    }

    public void setTitle4(String title4) {
        this.title4 = title4;
    }

    public String getContent4() {
        return content4;
    }

    public void setContent4(String content4) {
        this.content4 = content4;
    }

    public String getIsDisplay4() {
        return isDisplay4;
    }

    public void setIsDisplay4(String isDisplay4) {
        this.isDisplay4 = isDisplay4;
    }

    public String getTitle5() {
        return title5;
    }

    public void setTitle5(String title5) {
        this.title5 = title5;
    }

    public String getContent5() {
        return content5;
    }

    public void setContent5(String content5) {
        this.content5 = content5;
    }

    public String getIsDisplay5() {
        return isDisplay5;
    }

    public void setIsDisplay5(String isDisplay5) {
        this.isDisplay5 = isDisplay5;
    }

    public String getTitle6() {
        return title6;
    }

    public void setTitle6(String title6) {
        this.title6 = title6;
    }

    public String getContent6() {
        return content6;
    }

    public void setContent6(String content6) {
        this.content6 = content6;
    }

    public String getIsDisplay6() {
        return isDisplay6;
    }

    public void setIsDisplay6(String isDisplay6) {
        this.isDisplay6 = isDisplay6;
    }

    public String getTitle7() {
        return title7;
    }

    public void setTitle7(String title7) {
        this.title7 = title7;
    }

    public String getContent7() {
        return content7;
    }

    public void setContent7(String content7) {
        this.content7 = content7;
    }

    public String getIsDisplay7() {
        return isDisplay7;
    }

    public void setIsDisplay7(String isDisplay7) {
        this.isDisplay7 = isDisplay7;
    }

    public String getTitle8() {
        return title8;
    }

    public void setTitle8(String title8) {
        this.title8 = title8;
    }

    public String getContent8() {
        return content8;
    }

    public void setContent8(String content8) {
        this.content8 = content8;
    }

    public String getIsDisplay8() {
        return isDisplay8;
    }

    public void setIsDisplay8(String isDisplay8) {
        this.isDisplay8 = isDisplay8;
    }

    public String getTitle9() {
        return title9;
    }

    public void setTitle9(String title9) {
        this.title9 = title9;
    }

    public String getContent9() {
        return content9;
    }

    public void setContent9(String content9) {
        this.content9 = content9;
    }

    public String getIsDisplay9() {
        return isDisplay9;
    }

    public void setIsDisplay9(String isDisplay9) {
        this.isDisplay9 = isDisplay9;
    }

    public String getTitle10() {
        return title10;
    }

    public void setTitle10(String title10) {
        this.title10 = title10;
    }

    public String getContent10() {
        return content10;
    }

    public void setContent10(String content10) {
        this.content10 = content10;
    }

    public String getIsDisplay10() {
        return isDisplay10;
    }

    public void setIsDisplay10(String isDisplay10) {
        this.isDisplay10 = isDisplay10;
    }

    public String getWebsiteDisplayName() {
        return websiteDisplayName;
    }

    public void setWebsiteDisplayName(String websiteDisplayName) {
        this.websiteDisplayName = websiteDisplayName;
    }
}
