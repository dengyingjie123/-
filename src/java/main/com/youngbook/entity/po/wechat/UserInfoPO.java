package com.youngbook.entity.po.wechat;

import com.youngbook.annotation.*;
import com.youngbook.entity.po.BasePO;

/**
 * Created by Lee on 2/10/2017.
 */
@Table(name = "WeChat_UserInfo", jsonPrefix = "userInfo")
public class UserInfoPO extends BasePO {

    @Id(type = IdType.LONG)
    private long sid = Long.MAX_VALUE;

    private String id = "";

    //登录用户状态
    private int state = Integer.MAX_VALUE;
    //操作者Id
    private String operatorId = "";
    @DataAdapter(fieldType = FieldType.DATE)
    private String operateTime = "";

    private String customerOrUserId = "";

    private String customerOrUserType = "";

    private String openid = "";
    private String nickname = "";
    private String sex = "";
    private String province = "";
    private String city = "";
    private String country = "";
    private String headimgurl = "";
    private String privilege = "";
    private String unionid = "";

    public String getCustomerOrUserId() {
        return customerOrUserId;
    }

    public void setCustomerOrUserId(String customerOrUserId) {
        this.customerOrUserId = customerOrUserId;
    }

    public String getCustomerOrUserType() {
        return customerOrUserType;
    }

    public void setCustomerOrUserType(String customerOrUserType) {
        this.customerOrUserType = customerOrUserType;
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
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

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
