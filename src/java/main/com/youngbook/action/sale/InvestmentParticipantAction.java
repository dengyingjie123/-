package com.youngbook.action.sale;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.sale.InvestmentParticipantPO;
import com.youngbook.entity.vo.Sale.InvestmentParticipantVO;
import com.youngbook.service.sale.InvestmentParticipantService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class InvestmentParticipantAction extends BaseAction {

    //投资计划PO，VO类
    private InvestmentParticipantVO investmentParticipantVO = new InvestmentParticipantVO();
    private InvestmentParticipantPO investmentParticipant = new InvestmentParticipantPO();
    //逻辑控制类
    private InvestmentParticipantService service = new InvestmentParticipantService();

    /**
     * 获取数据列表
     * @return
     * @throws Exception
     */
    public String list() throws Exception {
        // 获取请求
        HttpServletRequest request = ServletActionContext.getRequest();
        // 查询条件
        List<KVObject> conditions =MySQLDao.getQueryDatetimeParameters(request, InvestmentParticipantVO.class);
        // 查询数据
        Pager pager = service.List(investmentParticipantVO,conditions);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 查询单条语句
     * @return
     * @throws Exception
     */
    public String load() throws Exception {
        //获取数据
        investmentParticipant = service.loadInvestmentParticipantPO(investmentParticipant.getId());
        getResult().setReturnValue(investmentParticipant.toJsonObject4Form());
        return SUCCESS;
    }

    public InvestmentParticipantVO getInvestmentParticipantVO() {
        return investmentParticipantVO;
    }
    public void setInvestmentParticipantVO(InvestmentParticipantVO investmentParticipantVO) {this.investmentParticipantVO = investmentParticipantVO;}

    public InvestmentParticipantPO getInvestmentParticipant() {
        return investmentParticipant;
    }
    public void setInvestmentParticipant(InvestmentParticipantPO investmentParticipant) {this.investmentParticipant = investmentParticipant;}

    public InvestmentParticipantService getService() {
        return service;
    }
    public void setService(InvestmentParticipantService service) {
        this.service = service;
    }
}
