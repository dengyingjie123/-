package com.youngbook.action.system;

import com.youngbook.action.BaseAction;
import com.youngbook.common.Pager;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.entity.po.system.LogPO;
import com.youngbook.service.system.LogService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Lee on 1/19/2017.
 */
public class LogAction extends BaseAction {

    @Autowired
    LogService logService;

    public String listPagerLogPO() throws Exception {

        LogPO logPO = HttpUtils.getInstanceFromRequest(getRequest(), LogPO.class);

        Pager pager = Pager.getInstance(getRequest());

        pager = logService.listPagerLogPO(logPO, pager.getCurrentPage(), pager.getShowRowCount(), getConnection());

        if (pager != null) {
            getResult().setReturnValue(pager.toJsonObject());
        }

        return SUCCESS;
    }
}
