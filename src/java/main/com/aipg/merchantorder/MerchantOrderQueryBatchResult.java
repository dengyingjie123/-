package com.aipg.merchantorder;

import com.youngbook.entity.po.allinpay.AllinPayOrderPO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 2015/12/24.
 */
public class MerchantOrderQueryBatchResult {
    private String merchantId = "";
    private int currentBillCount = Integer.MAX_VALUE;
    private int currentPageNo = Integer.MAX_VALUE;
    private boolean hasNextPage = false;
    private boolean isVerified = false;
    private String errorCode = "";
    private String errorMessage = "";

    private List<AllinPayOrderPO> allinPayOrders = new ArrayList<AllinPayOrderPO>();

    public List<AllinPayOrderPO> getAllinPayOrders() {
        return allinPayOrders;
    }

    public void setAllinPayOrders(List<AllinPayOrderPO> allinPayOrders) {
        this.allinPayOrders = allinPayOrders;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public int getCurrentBillCount() {
        return currentBillCount;
    }

    public void setCurrentBillCount(int currentBillCount) {
        this.currentBillCount = currentBillCount;
    }

    public int getCurrentPageNo() {
        return currentPageNo;
    }

    public void setCurrentPageNo(int currentPageNo) {
        this.currentPageNo = currentPageNo;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
