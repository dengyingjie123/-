package com.youngbook.action.customer;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.wf.Database;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.customer.CustomerWithdrawVerifyPO;
import com.youngbook.entity.vo.customer.CustomerWithdrawVerifyVO;
import com.youngbook.service.customer.CustomerWithdrawVerifyService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张舜清 on 7/19/2015.
 */
public class CustomerWithdrawVerifyAction extends BaseAction {
    //实例化PO、VO、Servlet 类对象
    private CustomerWithdrawVerifyPO customerWithdrawVerify = new CustomerWithdrawVerifyPO();
    private CustomerWithdrawVerifyVO customerWithdrawVerifyVO = new CustomerWithdrawVerifyVO();
    private CustomerWithdrawVerifyService service = new CustomerWithdrawVerifyService();

    private String sort = new String();//需要排序的列名
    private String order = new String();

    /**
     * 获取数据列表
     *
     * @return
     * @throws Exception
     */
    public String list() throws Exception {

        HttpServletRequest request = getRequest();

        // 构造SQL语句
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT  ");
        sbSQL.append("    cwv.*,  ");
        sbSQL.append("    p.LoginName AS CustomerLoginName,  ");
        sbSQL.append("    p.`Name` AS CustomerName,  ");
        sbSQL.append("    u.`name` AS ExamineUserName1,  ");
        sbSQL.append("    u.`name` AS ExamineUserName2,  ");
        sbSQL.append("    u.`name` AS ExamineUserName3  ");
        sbSQL.append(" FROM  ");
        sbSQL.append("    crm_customerwithdrawverify cwv  ");
        sbSQL.append(" LEFT JOIN crm_customerpersonal p ON p.state = 0  ");
        sbSQL.append(" AND cwv.State = 0  ");
        sbSQL.append(" AND p.id = cwv.CustomerID  ");
        sbSQL.append(" LEFT JOIN system_user u ON u.state = 0  ");
        sbSQL.append(" AND cwv.ExamineUserID1 = u.Id  ");
        sbSQL.append(" AND cwv.ExamineUserID2 = u.Id  ");
        sbSQL.append(" AND cwv.ExamineUserID3 = u.Id  ");


        List<KVObject> conditions = new ArrayList<KVObject>();
        conditions = MySQLDao.getQueryDatetimeParameters(request, customerWithdrawVerify.getClass(), conditions);
        //整型范围查询
        conditions = MySQLDao.getQueryNumberParameters(request, customerWithdrawVerify.getClass(), conditions);

        //设置排序
        if (!getSort().equals("")) {
            conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, getSort() + " " + getOrder()));
        }

        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        //获取分页对象
        Pager pager = Pager.query(sbSQL.toString(), customerWithdrawVerifyVO, conditions, request, queryType);
        //返回数据
        getResult().setReturnValue(pager.toJsonObject());

        return SUCCESS;
    }

    /**
     * 添加过更新数据
     *
     * @return
     * @throws Exception
     */
    public String insertOrUpdate() throws Exception {
        int count = service.insertOrUpdate(customerWithdrawVerify, getLoginUser(), getConnection());
        if (count != 1) {
            getResult().setMessage("操作失败");
        }
        return SUCCESS;
    }

    /**
     * 获取单条数据
     *
     * @return
     * @throws Exception
     */
    public String load() throws Exception {

        customerWithdrawVerify = service.loadCustomerWithdrawVerifyPO(customerWithdrawVerify.getId());

        getResult().setReturnValue(customerWithdrawVerify.toJsonObject4Form());

        return SUCCESS;
    }

    /**
     * 删除数据
     *
     * @return
     * @throws Exception
     */
    public String delete() throws Exception {

        service.delete(customerWithdrawVerify, getLoginUser(), getConnection());

        return SUCCESS;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public CustomerWithdrawVerifyPO getCustomerWithdrawVerify() {
        return customerWithdrawVerify;
    }

    public void setCustomerWithdrawVerify(CustomerWithdrawVerifyPO customerWithdrawVerify) {
        this.customerWithdrawVerify = customerWithdrawVerify;
    }

    public CustomerWithdrawVerifyVO getCustomerWithdrawVerifyVO() {
        return customerWithdrawVerifyVO;
    }

    public void setCustomerWithdrawVerifyVO(CustomerWithdrawVerifyVO customerWithdrawVerifyVO) {
        this.customerWithdrawVerifyVO = customerWithdrawVerifyVO;
    }

    public CustomerWithdrawVerifyService getService() {
        return service;
    }

    public void setService(CustomerWithdrawVerifyService service) {
        this.service = service;
    }
}
