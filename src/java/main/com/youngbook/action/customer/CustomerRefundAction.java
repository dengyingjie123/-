package com.youngbook.action.customer;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.config.Config;
import com.youngbook.entity.po.customer.CustomerAccountPO;
import com.youngbook.entity.po.customer.CustomerRefundPO;
import com.youngbook.entity.vo.customer.CustomerRefundVO;
import com.youngbook.service.customer.CustomerRefundService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张舜清 on 2015/8/26.
 */
public class CustomerRefundAction extends BaseAction {
    CustomerRefundPO customerRefund = new CustomerRefundPO();
    CustomerRefundVO customerRefundVO = new CustomerRefundVO();
    CustomerAccountPO customerAccount = new CustomerAccountPO();
    CustomerRefundService customerRefundService = new CustomerRefundService();

    /**
     * 创建人：张舜清
     * 时间：2015年8月26日14:36:36
     * 内容：后台管理系统客户退款显示list的action
     *
     * @return
     * @throws Exception
     */
    public String list() throws Exception{
        //设置查询条件
        List<KVObject> conditions = new ArrayList<KVObject>();
        //查询返回分页对象
        Pager pager = customerRefundService.list(customerRefundVO,conditions);
        //返回数据
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 创建人：张舜清
     * 时间：2015年8月26日14:35:57
     * 内容：后台js退款按钮调用action
     *
     * @return
     * @throws Exception
     */
    public String refund() throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        Connection conn = this.getConnection();

        int count = customerRefundService.refund(request,conn);
        getResult().setReturnValue(count);
        return SUCCESS;
    }
}
