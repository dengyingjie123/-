package com.youngbook.entity.vo.customer;

import com.youngbook.annotation.DataAdapter;
import com.youngbook.annotation.FieldType;
import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 10/10/14
 * Time: 8:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class CustomerAuditVO extends BaseVO {
    // sid
    @Id
    private int sid = Integer.MAX_VALUE;

    // id
    private String id = new String();

    // 客户名
    private String name = new String();

    // 移动电话 : 支持查询,必填
    private String mobile = new String();

    // 固定电话
    private String phone = new String();

    // 地址 : 支持查询,必填
    private String address = new String();

    // 电子邮箱
    private String email = new String();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
