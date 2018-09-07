package com.youngbook.entity.po.production;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;

/**
 * Created by Jevons on 2015/10/28.
 */
public class ProductionAgreementPO {

    //�ͻ����
    private String user_name = new String();

    private String number = new String();

    //֤������
    private String id_number = new String();

    //��Ʒ���
    private String production_name = new String();

    //������Ŀ
    private String project_Name = new String();

    //�ͻ����
    private String customer_name = new String();

    //�ͻ�����
    private String customer_Attribute = new String();

    //������
    private String order_Num = new String();

    //Ͷ�ʽ��
    private String invest_Money = new String();

    //Ͷ��ʱ��
    private String invest_Date = new String();
    private String investTermView = new String();

    //��Ϣ��
    private String value_Date = new String();


    private String expectedYiel = new String();

    @DataAdapter(fieldType = FieldType.DATE)
    private String createTime= new String();

    private String DateTime=new String();

    public String getInvestTermView() {
        return investTermView;
    }

    public void setInvestTermView(String investTermView) {
        this.investTermView = investTermView;
    }

    public String getExpectedYiel() {
        return expectedYiel;
    }

    public void setExpectedYiel(String expectedYiel) {
        this.expectedYiel = expectedYiel;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getCreateTime() {
        return createTime;
    }


    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getProduction_name() {
        return production_name;
    }

    public void setProduction_name(String production_name) {
        this.production_name = production_name;
    }

    public String getProject_Name() {
        return project_Name;
    }

    public void setProject_Name(String project_Name) {
        this.project_Name = project_Name;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_Attribute() {
        return customer_Attribute;
    }

    public void setCustomer_Attribute(String customer_Attribute) {
        this.customer_Attribute = customer_Attribute;
    }

    public String getOrder_Num() {
        return order_Num;
    }

    public void setOrder_Num(String order_Num) {
        this.order_Num = order_Num;
    }

    public String getInvest_Money() {
        return invest_Money;
    }

    public void setInvest_Money(String invest_Money) {
        this.invest_Money = invest_Money;
    }

    public String getInvest_Date() {
        return invest_Date;
    }

    public void setInvest_Date(String invest_Date) {
        this.invest_Date = invest_Date;
    }

    public String getValue_Date() {
        return value_Date;
    }

    public void setValue_Date(String value_Date) {
        this.value_Date = value_Date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
