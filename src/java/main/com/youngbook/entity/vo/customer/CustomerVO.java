package com.youngbook.entity.vo.customer;

/**
 * Created by Lee on 3/20/2017.
 */
public class CustomerVO {

    private String id = "";
    private String customerId = "";
    private String name = "";
    private String mobile = "";
    private String mobileMasked = "";
    private int commentCount = Integer.MAX_VALUE;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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

    public String getMobileMasked() {
        return mobileMasked;
    }

    public void setMobileMasked(String mobileMasked) {
        this.mobileMasked = mobileMasked;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
