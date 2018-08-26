package com.youngbook.action.fdcg;

import com.youngbook.action.BaseAction;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.JSONDao;
import com.youngbook.entity.po.fdcg.FdcgResponseData;
import com.youngbook.service.system.LogService;
import org.springframework.beans.factory.annotation.Autowired;


public class GeneralCallbackAction extends BaseAction {

    @Autowired
    LogService logService;

    public String receive() throws Exception {

        logService.save("富滇存管回调", "通用回调", getRequest(), getConnection());

        String requestDataString = getHttpRequestParameter("reqData");

        if (!StringUtils.isEmpty(requestDataString)) {
            FdcgResponseData responseData = JSONDao.parse(requestDataString, FdcgResponseData.class);

            if (responseData != null && !responseData.getRetCode().equals("0000")) {
                getResult().setCode(9999);
                getResult().setMessage(responseData.getRetCode() + " " + responseData.getRetMsg());


                getRequest().setAttribute("returnObject", getResult());

                return "fdcg_common_error";
            }
        }

        return SUCCESS;
    }
}
