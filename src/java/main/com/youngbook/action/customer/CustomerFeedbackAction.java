package com.youngbook.action.customer;

import com.youngbook.action.BaseAction;
import com.youngbook.annotation.Security;
import com.youngbook.common.*;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.KVPO;
import com.youngbook.entity.po.customer.CustomerFeedbackPO;
import com.youngbook.entity.po.customer.CustomerPersonalPO;
import com.youngbook.entity.vo.customer.CustomerFeedbackVO;
import com.youngbook.service.customer.CustomerFeedbackService;
import com.youngbook.service.customer.CustomerPersonalService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class CustomerFeedbackAction extends BaseAction {

    @Autowired
    CustomerPersonalService customerPersonalService;

    @Autowired
    CustomerFeedbackService customerFeedbackService;

    private CustomerFeedbackVO customerFeedbackVO = new CustomerFeedbackVO();
    private CustomerFeedbackPO customerFeedback = new CustomerFeedbackPO();



    /**
     * 删除客户回访接口
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年3月28日
     *
     * @return
     * @throws Exception
     */
    @Security(needToken = true)
    public String deleteFeedbacksAPI() throws Exception {

        // 获取请求对象
        HttpServletRequest request = this.getRequest();

        // 获取数据库连接
        Connection conn = this.getConnection();

        // 获取参数
        String ids = HttpUtils.getParameter(request, "ids");
        if(StringUtils.isEmpty(ids)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }

        String[] idsArr = ids.split("\\|");

        CustomerFeedbackService feedbackService = new CustomerFeedbackService();
        Integer count = feedbackService.deleteFeedbacks(idsArr, conn);

        if(count != ids.length()) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "数据异常").throwException();
        }

        return SUCCESS;

    }

    /**
     * HOPEWEALTH-1303
     * 客户回访APP接口，获取组名为CRM_CustomerFeedbackType的KV值，即所有回访类型
     * test url
     * http://localhost:8080/core/api/customer/CustomerFeedback_getFeedbackType
     * @return
     * @throws Exception
     */
    @Security(needToken = true)
    public String getFeedbackType() throws Exception {
        List<KVPO> ret = customerFeedbackService.getFeedbackType();
        //用JSON数组存储
        JSONArray ja = new JSONArray();
        for (KVPO kvpo: ret) {
            JSONObject jo = new JSONObject();
            jo.put("ID", kvpo.getID());
            jo.put("K", kvpo.getK());
            jo.put("V", kvpo.getV());
            jo.put("GroupName", kvpo.getGroupName());
            jo.put("Orders", kvpo.getOrders());
            ja.add(jo);
        }

        getResult().setReturnValue(ja);
        return SUCCESS;
    }

    /**
     * HOPEWEALTH-1303
     * 获得自己（操作人id）的所有回访记录
     *
     */
    public String getFeedbacksAPI() throws Exception {

        HttpServletRequest request = this.getRequest();
        Connection conn = this.getConnection();

        String feedbackManId = HttpUtils.getParameter(request, "feedbackManId");//操作人ID（自己）
        String customerId = HttpUtils.getParameter(request, "customerId");
        String currentPage = HttpUtils.getParameter(request, "currentPage");
        String showRowCount = HttpUtils.getParameter(request, "showRowCount");

        // 校验参数合法性
        if (StringUtils.isEmpty(feedbackManId) || StringUtils.isEmpty(customerId) || StringUtils.isEmpty(currentPage) || StringUtils.isEmpty(showRowCount)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }

        Integer currentPageInt = Integer.parseInt(currentPage);
        Integer showRowCountInt = Integer.parseInt(showRowCount);

        Pager pager = customerFeedbackService.getFeedbacks(customerId, feedbackManId, currentPageInt, showRowCountInt, conn);
        getResult().setReturnValue(pager);

        return SUCCESS;
    }

    /**
     * HOPEWEALTH-1303<br/>
     * 客户回访的插入（新增一条）APP接口
     * test url
     * http://localhost:8080/core/api/customer/CustomerFeedback_addFeedback?typeId=587&&content=hehe&&time=2016-03-03 23:00:32&&customerId=9C014C3A879F4E23A07E12503FF049B9&&feedbackManId=ab7996b505df42cda37400f550f7cf1c
     * @return
     * @throws Exception
     */
    public String addFeedback() throws Exception {
        HttpServletRequest request = this.getRequest();

        String typeId = HttpUtils.getParameter(request, "typeId");

        String content = HttpUtils.getParameter(request, "content");
        String time = HttpUtils.getParameter(request, "time");
        String customerId = HttpUtils.getParameter(request, "customerId");
        String feedbackManId = HttpUtils.getParameter(request, "feedbackManId");

        // 校验参数合法性
        if (StringUtils.isEmpty(typeId) || StringUtils.isEmpty(content) || StringUtils.isEmpty(time) || StringUtils.isEmpty(customerId) || StringUtils.isEmpty(feedbackManId)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }
        // 查询客户姓名并插入
        CustomerPersonalPO customer = customerPersonalService.loadByCustomerPersonalId(customerId, getConnection());
        // 新建PO准备插入
        CustomerFeedbackPO temp = new CustomerFeedbackPO();
        temp.setCustomerId(customerId);
        temp.setTypeId(typeId);
        temp.setContent(content);
        temp.setFeedbackManId(feedbackManId);
        temp.setTime(time);
        temp.setCustomerName(customer == null ? "" : customer.getName());

        int count = MySQLDao.insertOrUpdate(temp, getConnection());

        if (count != 1) {
            getResult().setMessage("操作失败");
        }

        return SUCCESS;
    }

    /**
     * 载入一条数据
     *
     * @return
     * @throws Exception
     */
    public String load() throws Exception {
        customerFeedback = customerFeedbackService.loadCustomerFeedbackPO(customerFeedback.getId());
        getResult().setReturnValue(customerFeedback.toJsonObject4Form());
        return SUCCESS;
    }

    /**
     * 加载销售人员
     *
     * @return
     * @throws Exception
     */
    public String loadSalesMan() throws Exception {
        customerFeedback = customerFeedbackService.loadCustomerFeedbackPOSales(customerFeedback.getId());
        getResult().setReturnValue(customerFeedback.toJsonObject4Form());
        return SUCCESS;
    }

    /**
     * 删除一条数据
     *
     * @return
     * @throws Exception
     */
    public String delete() throws Exception {
        customerFeedbackService.delete(customerFeedback, getLoginUser(), getConnection());
        return SUCCESS;
    }

    /**
     * 新增或修改数据
     *
     * @return
     * @throws Exception
     */
    public String insertOrUpdate() throws Exception {
        int count = customerFeedbackService.insertOrUpdate(customerFeedback, getLoginUser(), getConnection());
        if (count != 1) {
            getResult().setMessage("操作失败");
        }
        return SUCCESS;
    }

    /**
     * 列出所有数据
     *
     * @return
     * @throws Exception
     */
    public String list() throws Exception {

        Pager pager = Pager.getInstance(getRequest());
        pager = customerFeedbackService.listPagerCustomerFeedback(customerFeedback.getCustomerId(), null, null, null, pager.getCurrentPage(), pager.getShowRowCount(), getConnection());
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    public String listCustomerFeedback() throws Exception {

        String customerId = getHttpRequestParameter("customerId");

        List<CustomerFeedbackPO> listCustomerFeedback = customerFeedbackService.listCustomerFeedback(customerId, null, null, null, getConnection());
        getResult().setReturnValue(listCustomerFeedback);

        return SUCCESS;
    }

    public CustomerFeedbackVO getCustomerFeedbackVO() {
        return customerFeedbackVO;
    }

    public void setCustomerFeedbackVO(CustomerFeedbackVO customerFeedbackVO) {
        this.customerFeedbackVO = customerFeedbackVO;
    }


    public CustomerFeedbackPO getCustomerFeedback() {
        return customerFeedback;
    }

    public void setCustomerFeedback(CustomerFeedbackPO customerFeedback) {
        this.customerFeedback = customerFeedback;
    }

}
