package com.youngbook.action.customer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.youngbook.action.BaseAction;
import com.youngbook.common.Pager;
import com.youngbook.common.ReturnObject;
import com.youngbook.common.utils.JSONUtils;
import com.youngbook.entity.po.customer.CustomerDistributionPO;
import com.youngbook.entity.vo.customer.CustomerInstitutionAuditVO;
import com.youngbook.entity.vo.customer.CustomerPersonalAuditVO;
import com.youngbook.entity.vo.customer.CustomerPersonalVO;
import com.youngbook.service.customer.CustomerDistributionService;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

public class CustomerDistributionAction extends BaseAction {

    private CustomerDistributionPO customerDistribution = new CustomerDistributionPO();
    private CustomerPersonalAuditVO personalAuditVO = new CustomerPersonalAuditVO();
    private CustomerInstitutionAuditVO institutionAuditVO = new CustomerInstitutionAuditVO();

    @Autowired
    CustomerDistributionService customerDistributionService;
    private ReturnObject result;

    /**
     * 新增或修改验证码的数据
     * 把页面请求过来的数据进行持久化，如果存在 ID，则修改，否则会新增一条记录
     * 修改是把原来的数据状态修改为 update 状态的代号，再新增一条状态为当前的新记录
     * 用法：前台的 URL 指向 /core/customer/CustomerDistribution_load.action，如未成功，请检查 struts 配置
     * <p/>
     * 修改：姚章鹏
     * 内容：修改 saveCustomerDistribution() 方法，关联销售负责人
     * 时间：2015-6-3
     * <p/>
     * 修改人：张舜清
     * 修改内容：重新写了service.saveCustomerDistribution服务
     * 修改时间：2015.7.7
     *
     * 修改人：胡超怡
     * 修改内容：重新写了service.saveCustomerDistribution服务，修改为批量修改的功能
     * 修改时间：2018.11.27
     *
     * @return 适用于 easyui 的 JSON，类似 {code:100, message:'操作成功'}
     * @throws Exception
     * @author 胡超怡
     */
    public String saveCustomerDistribution() throws Exception {
        int count = 0;


        /**
         * 接收前端传来的客户ids，并进行处理
         */
        String customerArray = getHttpRequestParameter("customerIds");
        String[] customerIds = customerArray.split(",");




        /**
         * @description
         * 遍历ids分配客户的销售人员，实现了批量修改的功能，其中每次创建CustomerDistributionPO对象
         * 是为了防止第二次遍历时customerDistribution会把上一次的对象初始化过来而带上id，造成第二次遍历只会修改而不是新增分配
         * 其中customerIds必定有值，因为在前台时就会过滤掉
         *
         * @author 胡超怡
         *
         * @date 2018/11/27 16:12
         * @param  CustomerDistributionPO
         * @return java.lang.String
         * @throws Exception
         */
        for (String customer: customerIds ) {

            CustomerDistributionPO customerPO = new CustomerDistributionPO();
            customerPO.setCustomerId(customer);
            customerPO.setSaleManId(customerDistribution.getSaleManId());
            customerPO.setDepartmentId(customerDistribution.getDepartmentId());
            customerPO.setSaleGroupId(customerDistribution.getSaleGroupId());

            customerDistributionService.distributeToOneSalesman(customerPO, getLoginUser().getId(), getConnection());
        }


        /**
         * 成功返回
         */
        return SUCCESS;
    }

    /**
     * @description 加载分配客户功能
     *
     * @author 胡超怡
     *
     * @date 2018/11/27 17:38
     *
     * @return java.lang.String
     * @throws Exception
     */
    public String load() throws Exception {

        String customers = getRequest().getParameter("customers");
        getResult().setReturnValue(customers);

        result.setMessage("操作成功");

        return SUCCESS;
    }

    /**
     * 取消客户所分配的销售员
     */
    public String cancelDistribution() {
        int count = 0;
        Connection conn = getConnection();
        try {
            count = customerDistributionService.cancelDistribution(customerDistribution, conn, getLoginUser());
        } catch (Exception e) {
            result.setMessage("取消失败，重新执行");
        }
        if (count == 1) {
            result.setMessage("操作成功");
        }
        return SUCCESS;
    }

    /**
     * 修改：姚章鹏
     * 内容：修改listPersonalCustomer()方法，关联 saleMan 表
     * 时间：2015-6-3
     */
    public String listPersonalCustomer() throws Exception {

        String customerName = getHttpRequestParameter("customerName");

        CustomerPersonalAuditVO customerPersonalAuditVO = new CustomerPersonalAuditVO();
        customerPersonalAuditVO.setName(customerName);

        HttpServletRequest request = ServletActionContext.getRequest();
        Pager pager = Pager.getInstance(request);
        pager = customerDistributionService.listPersonalCustomer(customerName, pager.getCurrentPage(), pager.getShowRowCount(), getConnection());
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 获取机构客户审核的列表
     *
     * @return
     * @throws Exception
     */
    public String listInstitutionCustomer() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        Pager pager = customerDistributionService.listInstitutionCustomer(institutionAuditVO, request);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 审核
     *
     * @return
     * @throws Exception
     */
    public String auditDistribution() throws Exception {
        int count = 0;
        try {
            count = customerDistributionService.auditDistribution(customerDistribution, getConnection(), getLoginUser());
        } catch (Exception e) {
            result.setMessage("操作失败");
        }
        if (count == 1) {
            result.setMessage("操作成功");
        }
        return SUCCESS;
    }


    /**
     * 删除
     * @return
     * @throws Exception
     */
    public String remove() throws Exception {

        String customerId = customerDistribution.getCustomerId();
        String userId = customerDistribution.getSaleManId();

        customerDistributionService.remove(customerId, userId, getConnection());

        return SUCCESS;
    }

    public String removeByLoginUser() throws Exception {

        String customerId = getHttpRequestParameter("customerId");
        String userId = getLoginUser().getId();

        customerDistributionService.remove(customerId, userId, getConnection());

        return SUCCESS;
    }

    public CustomerDistributionPO getCustomerDistribution() {
        return customerDistribution;
    }

    public void setCustomerDistribution(CustomerDistributionPO customerDistribution) {
        this.customerDistribution = customerDistribution;
    }

    public ReturnObject getResult() {
        return result;
    }

    public void setResult(ReturnObject result) {
        this.result = result;
    }

    public CustomerInstitutionAuditVO getInstitutionAuditVO() {
        return institutionAuditVO;
    }

    public void setInstitutionAuditVO(CustomerInstitutionAuditVO institutionAuditVO) {
        this.institutionAuditVO = institutionAuditVO;
    }

    public CustomerPersonalAuditVO getPersonalAuditVO() {
        return personalAuditVO;
    }

    public void setPersonalAuditVO(CustomerPersonalAuditVO personalAuditVO) {
        this.personalAuditVO = personalAuditVO;
    }
}
