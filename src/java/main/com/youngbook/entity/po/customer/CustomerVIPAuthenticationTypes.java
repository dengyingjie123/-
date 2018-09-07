package com.youngbook.entity.po.customer;

import com.youngbook.entity.po.BasePO;

public class CustomerVIPAuthenticationTypes extends BasePO {

    // 未认证
    public final static Integer UNAUTHORIZED = 0;
    public final static String UNAUTHORIZED_TEXT = "未认证";

    // 已认证
    public final static Integer AUTHENTICATED = 1;
    public final static String AUTHENTICATED_TEXT = "已认证";

}
