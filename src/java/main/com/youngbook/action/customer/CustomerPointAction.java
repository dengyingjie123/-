package com.youngbook.action.customer;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.entity.po.customer.CustomerPointPO;
import com.youngbook.entity.vo.customer.CustomerPointVO;
import com.youngbook.service.customer.CustomerPointService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class CustomerPointAction extends BaseAction {

    /**
     * 客户积分VO、PO、Service类
     */
    private CustomerPointVO customerPointVO = new CustomerPointVO();
    private CustomerPointPO customerPoint = new CustomerPointPO();
    private CustomerPointService service = new CustomerPointService();

    /**
     * 获取客户积分列表
     *
     * @return
     * @throws Exception
     */
    public String list() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions = new ArrayList<KVObject>();
        Pager pager = service.list(customerPointVO, conditions);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 添加于更新数据
     * @return
     * @throws Exception
     */
    public String insertOrUpdate() throws Exception {
        int count = service.insertOrUpdate(customerPoint, getLoginUser(), getConnection());
        if (count != 1) {
            getResult().setMessage("操作失败");
        }
        return SUCCESS;
    }

    /**
     * 获取单条数据
     * @return
     * @throws Exception
     */
    public String load() throws Exception {
        customerPoint = service.loadCustomerPointPO(customerPoint.getId());
        getResult().setReturnValue(customerPoint.toJsonObject4Form());
        return SUCCESS;
    }

    /**
     * 删除指定数据
     * @return
     * @throws Exception
     */
    public String delete() throws Exception {
        service.delete(customerPoint, getLoginUser(), getConnection());
        return SUCCESS;
    }

    public CustomerPointVO getCustomerPointVO() {
        return customerPointVO;
    }
    public void setCustomerPointVO(CustomerPointVO customerPointVO) {
        this.customerPointVO = customerPointVO;
    }

    public CustomerPointPO getCustomerPoint() {
        return customerPoint;
    }
    public void setCustomerPoint(CustomerPointPO customerPoint) {
        this.customerPoint = customerPoint;
    }

    public CustomerPointService getService() {
        return service;
    }
    public void setService(CustomerPointService service) {
        this.service = service;
    }

}
