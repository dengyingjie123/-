package com.youngbook.action.oa.finance;

import com.youngbook.action.BaseAction;
import com.youngbook.annotation.Permission;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.config.Config;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.oa.finance.MoneyLogPO;
import com.youngbook.entity.vo.oa.finance.MoneyLogVO;
import com.youngbook.service.oa.finance.MoneyLogService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class MoneyLogAction extends BaseAction {
    private MoneyLogPO moneyLog = new MoneyLogPO();
    private MoneyLogVO moneyLogVO = new MoneyLogVO();
    private MoneyLogService service = new MoneyLogService();

    @Permission(require = "财务管理-记账管理-删除")
    public String delete() throws Exception {
        service.delete(moneyLog, getLoginUser(), getConnection());
        return SUCCESS;
    }
    @Permission(require = "财务管理-记账管理-修改")
    public String load() throws Exception {
        moneyLog.setState(Config.STATE_CURRENT);
        moneyLog = MySQLDao.load(moneyLog, MoneyLogPO.class);
        getResult().setReturnValue(moneyLog.toJsonObject4Form());
        return SUCCESS;
    }


    @Permission(require = "财务管理-记账管理-新增")
    public String insertOrUpdate() throws Exception {
        service.insertOrUpdate(moneyLog, getLoginUser(), getConnection());
        return SUCCESS;
    }

    public String list() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request, MoneyLogVO.class);
        String departments = getRequest().getParameter("Departments");
        Pager pager = service.list(moneyLogVO, conditions, departments);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    public MoneyLogPO getMoneyLog() {
        return moneyLog;
    }
    public void setMoneyLog(MoneyLogPO moneyLog) {
        this.moneyLog = moneyLog;
    }

    public MoneyLogVO getMoneyLogVO() {
        return moneyLogVO;
    }
    public void setMoneyLogVO(MoneyLogVO moneyLogVO) {
        this.moneyLogVO = moneyLogVO;
    }

    public MoneyLogService getService() {
        return service;
    }
    public void setService(MoneyLogService service) {
        this.service = service;
    }

}
