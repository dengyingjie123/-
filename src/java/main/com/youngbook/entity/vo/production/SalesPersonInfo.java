package com.youngbook.entity.vo.production;

import com.youngbook.entity.vo.BaseVO;

public class SalesPersonInfo extends BaseVO {

    private String SalesPersonID = "";

    private String SalesPerson = "";

    private String staffcode = "";

    private String referralCode = "";

    private String SalesMobile = "";

    private String salescenter = "";

    public String getSalesPersonID() {
        return SalesPersonID;
    }

    public void setSalesPersonID(String salesPersonID) {
        SalesPersonID = salesPersonID;
    }

    public String getSalesPerson() {
        return SalesPerson;
    }

    public void setSalesPerson(String salesPerson) {
        SalesPerson = salesPerson;
    }

    public String getStaffcode() {
        return staffcode;
    }

    public void setStaffcode(String staffcode) {
        this.staffcode = staffcode;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public String getSalesMobile() {
        return SalesMobile;
    }

    public void setSalesMobile(String salesMobile) {
        SalesMobile = salesMobile;
    }

    public String getSalescenter() {
        return salescenter;
    }

    public void setSalescenter(String salescenter) {
        this.salescenter = salescenter;
    }

}
