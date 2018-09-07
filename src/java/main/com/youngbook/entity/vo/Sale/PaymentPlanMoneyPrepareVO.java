package com.youngbook.entity.vo.Sale;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.dao.JSONDao;
import com.youngbook.entity.vo.BaseVO;
import net.sf.json.JSONObject;

/**
 * Created by yux on 2016/6/15.
 */
@Table(name = "workflowaction_PaymentPlanMoneyPrepare", jsonPrefix = "paymentPlanMoneyPrepareVO")
public class PaymentPlanMoneyPrepareVO extends BaseVO {
    // sid

    @Id
    private int sid = Integer.MAX_VALUE;

    // Id
    private String id = new String();

    //状态
    private int state = Integer.MAX_VALUE;


    // 操作ID
    private String operatorId = new String();
    // 操作时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();


    /***
     * 申请人申请
     */
    // 申请人申请内容
    private String applicationContent = new String();

    // 申请人Id
    private String applicationId = new String();

    // 申请人申请时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String applicationTime = new String();

    // 申请人申请状态
    private int applicationStatus = Integer.MAX_VALUE;


    /***
     * 部门负责人审核
     */
    // 部门负责人审核内容
    private String departmentLeaderContent = new String();

    // 部门负责人Id
    private String departmentLeaderId = new String();

    // 部门负责人审核时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String departmentLeaderTime = new String();

    // 部门负责人审核状态
    private int departmentLeaderStatus = Integer.MAX_VALUE;


    /***
     * 财务审核
     */
    // 财务总监审核内容
    private String financeDirectorContent = new String();

    // 财务总监Id
    private String financeDirectorId = new String();

    // 财务总监审核时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String financeDirectorTime = new String();

    // 财务总监审核状态
    private int financeDirectorStatus = Integer.MAX_VALUE;


    /***
     * 副总裁审核
     */
    // 副总裁核内容
    private String vicePresidentContent = new String();

    // 副总裁Id
    private String vicePresidentId = new String();

    // 副总裁审核时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String vicePresidentTime = new String();

    // 副总裁审核状态
    private int vicePresidentStatus = Integer.MAX_VALUE;



    /***
     * 财务出纳
     */
    // 出纳内容
    private String cashierContent = new String();

    // 财务ID
    private String cashierId = new String();

    // 出纳时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String cashierTime = new String();

    // 出纳状态
    private int cashierStatus = Integer.MAX_VALUE;




    //资金准备开始时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String capitalPreStartTime = new String();

    //资金准备截止时间
    @DataAdapter(fieldType = FieldType.DATE)
    private String capitalPreEndTime = new String();

    //资金准备金额
    private double money=Double.MAX_VALUE;

    //资金准备时间段
    private String capitalPreTimes=new String();

    @Override
    public JSONObject toJsonObject4Form(){
        try {
            String prefix = new String();
            Table tableAnnotation = this.getClass().getAnnotation(Table.class);

            if (tableAnnotation != null) {
                prefix = tableAnnotation.jsonPrefix();
            }
            return JSONDao.get(this,prefix);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

    public String getApplicationContent() {
        return applicationContent;
    }

    public void setApplicationContent(String applicationContent) {
        this.applicationContent = applicationContent;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(String applicationTime) {
        this.applicationTime = applicationTime;
    }

    public int getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(int applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String getDepartmentLeaderContent() {
        return departmentLeaderContent;
    }

    public void setDepartmentLeaderContent(String departmentLeaderContent) {
        this.departmentLeaderContent = departmentLeaderContent;
    }

    public String getDepartmentLeaderId() {
        return departmentLeaderId;
    }

    public void setDepartmentLeaderId(String departmentLeaderId) {
        this.departmentLeaderId = departmentLeaderId;
    }

    public String getDepartmentLeaderTime() {
        return departmentLeaderTime;
    }

    public void setDepartmentLeaderTime(String departmentLeaderTime) {
        this.departmentLeaderTime = departmentLeaderTime;
    }

    public int getDepartmentLeaderStatus() {
        return departmentLeaderStatus;
    }

    public void setDepartmentLeaderStatus(int departmentLeaderStatus) {
        this.departmentLeaderStatus = departmentLeaderStatus;
    }

    public String getFinanceDirectorContent() {
        return financeDirectorContent;
    }

    public void setFinanceDirectorContent(String financeDirectorContent) {
        this.financeDirectorContent = financeDirectorContent;
    }

    public String getFinanceDirectorId() {
        return financeDirectorId;
    }

    public void setFinanceDirectorId(String financeDirectorId) {
        this.financeDirectorId = financeDirectorId;
    }

    public String getFinanceDirectorTime() {
        return financeDirectorTime;
    }

    public void setFinanceDirectorTime(String financeDirectorTime) {
        this.financeDirectorTime = financeDirectorTime;
    }

    public int getFinanceDirectorStatus() {
        return financeDirectorStatus;
    }

    public void setFinanceDirectorStatus(int financeDirectorStatus) {
        this.financeDirectorStatus = financeDirectorStatus;
    }

    public String getVicePresidentContent() {
        return vicePresidentContent;
    }

    public void setVicePresidentContent(String vicePresidentContent) {
        this.vicePresidentContent = vicePresidentContent;
    }

    public String getVicePresidentId() {
        return vicePresidentId;
    }

    public void setVicePresidentId(String vicePresidentId) {
        this.vicePresidentId = vicePresidentId;
    }

    public String getVicePresidentTime() {
        return vicePresidentTime;
    }

    public void setVicePresidentTime(String vicePresidentTime) {
        this.vicePresidentTime = vicePresidentTime;
    }

    public int getVicePresidentStatus() {
        return vicePresidentStatus;
    }

    public void setVicePresidentStatus(int vicePresidentStatus) {
        this.vicePresidentStatus = vicePresidentStatus;
    }

    public String getCashierContent() {
        return cashierContent;
    }

    public void setCashierContent(String cashierContent) {
        this.cashierContent = cashierContent;
    }

    public String getCashierId() {
        return cashierId;
    }

    public void setCashierId(String cashierId) {
        this.cashierId = cashierId;
    }

    public String getCashierTime() {
        return cashierTime;
    }

    public void setCashierTime(String cashierTime) {
        this.cashierTime = cashierTime;
    }

    public int getCashierStatus() {
        return cashierStatus;
    }

    public void setCashierStatus(int cashierStatus) {
        this.cashierStatus = cashierStatus;
    }

    public String getCapitalPreStartTime() {
        return capitalPreStartTime;
    }

    public void setCapitalPreStartTime(String capitalPreStartTime) {
        this.capitalPreStartTime = capitalPreStartTime;
    }

    public String getCapitalPreEndTime() {
        return capitalPreEndTime;
    }

    public void setCapitalPreEndTime(String capitalPreEndTime) {
        this.capitalPreEndTime = capitalPreEndTime;
    }

    public String getCapitalPreTimes() {
        return capitalPreTimes;
    }

    public void setCapitalPreTimes(String capitalPreTimes) {
        this.capitalPreTimes = capitalPreTimes;
    }

    public double getMoney() {
        return money;
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

    public void setMoney(double money) {
        this.money = money;
    }
}
