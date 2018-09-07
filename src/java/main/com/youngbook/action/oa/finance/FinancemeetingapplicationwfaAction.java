package com.youngbook.action.oa.finance;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.oa.finance.FinancemeetingapplicationwfaPO;
import com.youngbook.entity.vo.oa.finance.FinancemeetingapplicationwfaVO;
import com.youngbook.service.oa.finance.FinancemeetingService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by admin on 2015/4/29.
 */
public class FinancemeetingapplicationwfaAction extends BaseAction {
    private FinancemeetingapplicationwfaPO financemeetingapplicationwfa = new FinancemeetingapplicationwfaPO ();
    private FinancemeetingapplicationwfaVO financemeetingapplicationwfaVO = new FinancemeetingapplicationwfaVO ();

    private FinancemeetingService service = new FinancemeetingService ();

    /**
     * 获取的列表
     *
     * @return
     * @throws Exception
     */
    public String list () throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest ();
        //根据时间获取时间查询对象
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters (request, FinancemeetingapplicationwfaVO.class);
        //获取列表
        Pager pager = service.list (financemeetingapplicationwfaVO, conditions);
        getResult ().setReturnValue (pager.toJsonObject ());
        return SUCCESS;
    }

    public String insertOrUpdate () throws Exception {
        int count = service.insertOrUpdate (financemeetingapplicationwfa, getLoginUser (), getConnection ());
        if ( count != 1 ) {
            getResult ().setMessage ("操作失败");
        }
        return SUCCESS;
    }

    public String delete () throws Exception {

        service.delete (financemeetingapplicationwfa, getLoginUser (), getConnection ());

        return SUCCESS;
    }

    // 编写Action里的load
    public String load () throws Exception {

        financemeetingapplicationwfa = service.loadFinancemeetingPO (financemeetingapplicationwfa.getId ());

        getResult ().setReturnValue (financemeetingapplicationwfa.toJsonObject4Form ());

        return SUCCESS;
    }

    public FinancemeetingapplicationwfaPO getFinancemeetingapplicationwfa () {
        return financemeetingapplicationwfa;
    }

    public void setFinancemeetingapplicationwfa (FinancemeetingapplicationwfaPO financemeetingapplicationwfa) {
        this.financemeetingapplicationwfa = financemeetingapplicationwfa;
    }

    public FinancemeetingapplicationwfaVO getFinancemeetingapplicationwfaVO () {
        return financemeetingapplicationwfaVO;
    }

    public void setFinancemeetingapplicationwfaVO (FinancemeetingapplicationwfaVO financemeetingapplicationwfaVO) {
        this.financemeetingapplicationwfaVO = financemeetingapplicationwfaVO;
    }

    public FinancemeetingService getService () {
        return service;
    }

    public void setService (FinancemeetingService service) {
        this.service = service;
    }
}
