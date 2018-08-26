package com.youngbook.action.customer;

import com.youngbook.action.BaseAction;
import com.youngbook.common.Database;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.entity.po.system.SmsPO;
import com.youngbook.entity.vo.system.SmsVO;
import com.youngbook.service.customer.CustomerSmsService;
import com.youngbook.service.system.LogService;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class CustomerSmsAction extends BaseAction {

    private SmsPO sms = new SmsPO();
    private List<SmsPO> smsPOs = new ArrayList<SmsPO>();
    private SmsVO smsVO = new SmsVO();

    @Autowired
    CustomerSmsService customerSmsService;

    public String list() throws Exception{

        smsVO = HttpUtils.getInstanceFromRequest(getRequest(), "smsVO", SmsVO.class);

        StringBuffer sbSQL =  new StringBuffer();
        sbSQL.append("SELECT ");
        sbSQL.append(" s.receiverName receiverName , ");
        sbSQL.append(" s.receiverMobile receiverMobile , ");
        sbSQL.append(" s.subject subject ,");
        sbSQL.append(" s.content content , ");
        sbSQL.append(" s.waitingTime waitingTime , ");
        sbSQL.append(" u.`name` senderName ,");
        sbSQL.append(" s.sendTime sendTime , ");
        sbSQL.append(" s.feedbackStatus feedbackStatus ");
        sbSQL.append(" FROM");
        sbSQL.append(" system_sms s ");
        sbSQL.append(" LEFT JOIN ");
        sbSQL.append(" system_user u ");
        sbSQL.append(" ON s.SenderId = u.Id");
        sbSQL.append(" WHERE ");
        sbSQL.append(" s.state = 0 ");
        sbSQL.append(" AND ");
        sbSQL.append(" u.state = 0 ");
        sbSQL.append(" ORDER BY waitingTime ");

        HttpServletRequest request = ServletActionContext.getRequest();
        QueryType quertType = new QueryType(Database.QUERY_FUZZY , Database.NUMBER_EQUAL);
        Pager pager = Pager.query(sbSQL.toString() , smsVO , null , request , quertType);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    public String push() throws Exception{
        for(int i = 0; i<smsPOs.size();i++){
            String name = smsPOs.get(i).getReceiverName();
            smsPOs.get(i).setReceiverName(java.net.URLDecoder.decode(name,"UTF-8"));
            smsPOs.get(i).setSubject(java.net.URLDecoder.decode(smsPOs.get(i).getSubject(),"UTF-8"));
            smsPOs.get(i).setContent(java.net.URLDecoder.decode(smsPOs.get(i).getContent(),"UTF-8"));
        }
        int count = customerSmsService.push((ArrayList<SmsPO>) smsPOs, getLoginUser(), getConnection());
        LogService.debug("COUNT = " + count, this.getClass());
        return SUCCESS;
    }

    public SmsPO getSms() {
        return sms;
    }
    public void setSms(SmsPO sms) {
        this.sms = sms;
    }

    public List<SmsPO> getSmsPOs() {
        return smsPOs;
    }
    public void setSmsPOs(List<SmsPO> smsPOs) {
        this.smsPOs = smsPOs;
    }

    public SmsVO getSmsVO() {
        return smsVO;
    }

    public void setSmsVO(SmsVO smsVO) {
        this.smsVO = smsVO;
    }

    public CustomerSmsService getCustomerSmsService() {
        return customerSmsService;
    }

    public void setCustomerSmsService(CustomerSmsService customerSmsService) {
        this.customerSmsService = customerSmsService;
    }
}
