package com.youngbook.entity.vo.callcenter;

import com.youngbook.annotation.Id;
import com.youngbook.annotation.Table;
import com.youngbook.entity.vo.BaseVO;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-1-20
 * Time: 上午12:54
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "crm_customerpersonal", jsonPrefix = "customerpersonal",backupTableName = "crm_customerpersonal_archive")
public class CC_CustomerPersonalVO extends BaseVO {

    private int sid = Integer.MAX_VALUE;

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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile2() {
        return mobile2;
    }

    public void setMobile2(String mobile2) {
        this.mobile2 = mobile2;
    }

    public String getMobile3() {
        return mobile3;
    }

    public void setMobile3(String mobile3) {
        this.mobile3 = mobile3;
    }

    public String getMobile4() {
        return mobile4;
    }

    public void setMobile4(String mobile4) {
        this.mobile4 = mobile4;
    }

    public String getMobile5() {
        return mobile5;
    }

    public void setMobile5(String mobile5) {
        this.mobile5 = mobile5;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getPhone3() {
        return phone3;
    }

    public void setPhone3(String phone3) {
        this.phone3 = phone3;
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

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getEmail3() {
        return email3;
    }

    public void setEmail3(String email3) {
        this.email3 = email3;
    }

    public String getEmail4() {
        return email4;
    }

    public void setEmail4(String email4) {
        this.email4 = email4;
    }

    public String getEmail5() {
        return email5;
    }

    public void setEmail5(String email5) {
        this.email5 = email5;
    }

    public String getSalemanname() {
        return salemanname;
    }

    public void setSalemanname(String salemanname) {
        this.salemanname = salemanname;
    }

    @Id
    private String id = new String();

    private String name = new String();

    private Integer sex = 0;

    private String birthday = new String();

    private String mobile = new String();

    private String mobile2 = new String();

    private String mobile3 = new String();

    private String mobile4 = new String();

    private String mobile5 = new String();

    private String phone = new String();

    private String phone2 = new String();

    private String phone3 = new String();

    private String address = new String();

    private String postNo = new String();

    private String email = new String();

    private String email2 = new String();

    private String email3 = new String();

    private String email4 = new String();

    private String email5 = new String();

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getPostNo() {
        return postNo;
    }

    public void setPostNo(String postNo){
        this.postNo = postNo;
    }

    private String createdate = new String();

    private String salemanname = new String();
}
