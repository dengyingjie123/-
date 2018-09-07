package com.youngbook.entity.po.fdcg;

/**
 * Created by Lee on 2/26/2018.
 */
public class FdcgRequestData {
    private String merchantNo = "M02573344910091001";
    private String data = "";
    private String sign = "";
    private String singData = "";
    private String certInfo = "";

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSingData() {
        return singData;
    }

    public void setSingData(String singData) {
        this.singData = singData;
    }

    public String getCertInfo() {
        return certInfo;
    }

    public void setCertInfo(String certInfo) {
        this.certInfo = certInfo;
    }
}
