package com.youngbook.common.utils.allinpay;

import com.youngbook.common.KVObjects;
import com.youngbook.common.utils.StringUtils;

/**
 * Created by leevits on 7/28/2018.
 */
public class AllinpayCircleUtils {

    private static KVObjects APINames = null;

    public static String getAPIName(String processingCode) {

        if (APINames == null) {
            APINames = new KVObjects();
            APINames.addItem("3010", "验证码验证");
            APINames.addItem("1095", "账户信息验证");
            APINames.addItem("3005", "资产份额查询");
            APINames.addItem("1088", "更换银行卡");
            APINames.addItem("1090", "更换手机号");
            APINames.addItem("1087", "单笔信任开户");
            APINames.addItem("2080", "充值机构自付");
            APINames.addItem("2085", "份额支付");
            APINames.addItem("2280", "普通取现");
            APINames.addItem("2294", "单笔还款");
        }

        String name = APINames.getItemString(processingCode);

        if (!StringUtils.isEmpty(name)) {
            return name;
        }

        return "未知";
    }

}
