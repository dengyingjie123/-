package com.youngbook.entity.po.system;

import com.youngbook.common.utils.StringUtils;
import com.youngbook.entity.po.BasePO;

/**
 * Created with IntelliJ IDEA.
 * User: Lee
 * Date: 1/24/15
 * Time: 11:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class SmsResponsePO extends BasePO {

    private int count = 0;
    private String serverSideCode = "";
    private String message = "";

    public static SmsResponsePO getInstanceFromDefaultServer(String responseCode) throws Exception {
        SmsResponsePO response = new SmsResponsePO();

        if (!StringUtils.isEmpty(responseCode) && responseCode.contains(",")) {
            String [] responseItems = responseCode.split(",");
            try {
                response.setCount(Integer.valueOf(responseItems[0]));
                response.setServerSideCode(responseItems[1]);
                response.setMessage(responseItems[2]);
            }
            catch (Exception e) {
                throw new Exception("构造短信回复对象失败，数据【"+responseCode+"】");
            }

        }

        // todo 测试时使用
        response.setCount(1);
        return  response;
    }



    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getServerSideCode() {
        return serverSideCode;
    }

    public void setServerSideCode(String serverSideCode) {
        this.serverSideCode = serverSideCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
