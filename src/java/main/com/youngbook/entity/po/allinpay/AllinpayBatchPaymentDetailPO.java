package com.youngbook.entity.po.allinpay;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.po.BasePO;

/**
 * Created by jepson on 2015/8/4.
 */
@Table(name = "bank_AllinpayBatchPaymentDetail", jsonPrefix = "allinpaybatchpaymentdetail")
public class AllinpayBatchPaymentDetailPO extends BasePO {
    @Id
    private int sid = Integer.MAX_VALUE;

    private String id = new String();

    private int state = Integer.MAX_VALUE;

    private String operatorId = new String();

    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = new String();

    //记录序号
    private String sn=new String();
    //用户编号
    private String e_user_code=new String();
    //银行代码
    private String  bank_code=new String();
    //账号_类型
    private String account_type=new String();
    //账号
    private String account_no=new String();
    //账户名
    private String account_name=new String();
    //开户所在省
    private String province=new String();
    //开户所在市
    private String city=new String();
    //开户所在名称
    private String bank_name=new String();
    //账户属性
    private int account_pror=Integer.MAX_VALUE;
    //金额
    private int amount=Integer.MAX_VALUE;
    //货币类型
    private String currency=new String();
    //协议号
    private String protocol=new String();
    //协议用户编号
    private String protocal_userid=new String();
    //开户证件类型
    private int id_type=Integer.MAX_VALUE;
    //证件号
    private String idnum=new String();
    //手机号
    private String tel=new String();
    //自定义用户号
    private String cust_userid=new String();
    //本交易结算户
    private String settacct=new String();
    //备注
    private String remark=new String();
    //分组清算标识
    private String settgroupflag=new String();
    //交易附言
    private String summary=new String();
    //支付行号
    private String union_bank=new String();
    //主表ID
    private String parentid=new String();
    //业务id
    private String bizId=new String();
    //提现类型
    private int bizType=Integer.MAX_VALUE;
    //是否修正
    private int revised = Integer.MAX_VALUE;

    private int status=Integer.MAX_VALUE;

    public int getSid() {
        return sid;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getBizType() {
        return bizType;
    }

    public void setBizType(int bizType) {
        this.bizType = bizType;
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

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getE_user_code() {
        return e_user_code;
    }

    public void setE_user_code(String e_user_code) {
        this.e_user_code = e_user_code;
    }

    public String getBank_code() {
        return bank_code;
    }

    public void setBank_code(String bank_code) {
        this.bank_code = bank_code;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getAccount_no() {
        return account_no;
    }

    public void setAccount_no(String account_no) {
        this.account_no = account_no;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public int getAccount_pror() {
        return account_pror;
    }

    public void setAccount_pror(int account_pror) {
        this.account_pror = account_pror;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getProtocal_userid() {
        return protocal_userid;
    }

    public void setProtocal_userid(String protocal_userid) {
        this.protocal_userid = protocal_userid;
    }

    public int getId_type() {
        return id_type;
    }

    public void setId_type(int id_type) {
        this.id_type = id_type;
    }

    public String getIdnum() {
        return idnum;
    }

    public void setIdnum(String idnum) {
        this.idnum = idnum;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCust_userid() {
        return cust_userid;
    }

    public void setCust_userid(String cust_userid) {
        this.cust_userid = cust_userid;
    }

    public String getSettacct() {
        return settacct;
    }

    public void setSettacct(String settacct) {
        this.settacct = settacct;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSettgroupflag() {
        return settgroupflag;
    }

    public void setSettgroupflag(String settgroupflag) {
        this.settgroupflag = settgroupflag;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getUnion_bank() {
        return union_bank;
    }

    public void setUnion_bank(String union_bank) {
        this.union_bank = union_bank;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public int getRevised() {return revised;}

    public void setRevised(int revised) {this.revised = revised;}
}
