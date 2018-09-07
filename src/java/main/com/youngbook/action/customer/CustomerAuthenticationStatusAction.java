package com.youngbook.action.customer;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.config.Config;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.customer.CustomerAuthenticationStatusPO;
import com.youngbook.entity.po.customer.CustomerPersonalPO;
import com.youngbook.entity.vo.customer.CustomerAuthenticationStatusVO;
import com.youngbook.service.customer.CustomerAuthenticationStatusService;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class CustomerAuthenticationStatusAction extends BaseAction {

    CustomerAuthenticationStatusPO customerAuthenticationStatus = new CustomerAuthenticationStatusPO();
    CustomerAuthenticationStatusVO customerAuthenticationStatusVO = new CustomerAuthenticationStatusVO();

    @Autowired
    CustomerAuthenticationStatusService customerAuthenticationStatusService;

    /**
     * 查询单条数据
     * 通过 ID 或 SID，查询出一条记录
     * 用法：前台的 URL 指向 /core/customer/CustomerAuthenticationStatus_load.action，如未成功，请检查 struts 配置
     *
     * 作者：
     * 内容：创建代码
     * 时间：
     *
     * @author 邓超
     * @return 适用于 easyui 的 JSON
     * @throws Exception
     */
    public String load() throws Exception{
        customerAuthenticationStatus.setState(Config.STATE_CURRENT);
        customerAuthenticationStatus = MySQLDao.load(customerAuthenticationStatus, CustomerAuthenticationStatusPO.class);
        getResult().setReturnValue(customerAuthenticationStatus.toJsonObject4Form());
        return SUCCESS;
    }

    /**
     * 查询出所有数据
     * 把所有数据查询出来，在后台管理的 datagrid 组件中分页展示
     * 用法：前台的 URL 指向 /core/customer/CustomerAuthenticationStatus_list.action，如未成功，请检查 struts 配置
     *
     * 作者：
     * 内容：创建代码
     * 时间：
     *
     * @author 邓超
     * @return 适用于 easyui 的 JSON
     * @throws Exception
     */
    public String list() throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request, CustomerAuthenticationStatusVO.class);
        Pager pager = customerAuthenticationStatusService.list(customerAuthenticationStatusVO,conditions);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 新增或修改验证码的数据
     * 把页面请求过来的数据进行持久化，如果存在 ID，则修改，否则会新增一条记录
     * 修改是把原来的数据状态修改为 update 状态的代号，再新增一条状态为当前的新记录
     * 用法：前台的 URL 指向 /core/customer/CustomerAuthenticationStatus_load.action，如未成功，请检查 struts 配置
     *
     * 作者：
     * 内容：创建代码
     * 时间：
     *
     * @author 邓超
     * @return 适用于 easyui 的 JSON，类似 {code:100, message:'操作成功'}
     * @throws Exception
     */
    public String insertOrUpdate() throws Exception {
        customerAuthenticationStatusService.insertOrUpdate(customerAuthenticationStatus, getLoginUser(), getConnection());
        return SUCCESS;
    }

    /**
     * 删除一条招行接口验证码数据
     * 通过 ID 或 SID，把查询出来的记录状态设置为已删除，再新增一条状态为当前的新记录
     * 用法：前台的 URL 指向 /core/customer/CustomerAuthenticationStatus_delete.action，如未成功，请检查 struts 配置
     *
     * 作者：
     * 内容：创建代码
     * 时间：
     *
     * @author 邓超
     * @return 适用于 easyui 的 JSON，类似 {code:100, message:'操作成功'}
     * @throws Exception
     */
    public String delete() throws Exception {
        customerAuthenticationStatusService.delete(customerAuthenticationStatus, getLoginUser(), getConnection());
        return SUCCESS;
    }

    /**
     * 获取JSON数组
     * @return
     */
    public String StatusTree() throws Exception {
        getResult().setReturnValue(customerAuthenticationStatusService.getStatusTree());
        return SUCCESS;
    }

    /**
     * 交易平台请求的 Action，获取当前登录人的认证状态
     * 前提是网站的 Customer 已经登录，通过 Session 获取到登录的人，然后查询 CustomerAuthenticationStatus 表
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @author 邓超
     * @return 适用于 easyui 的 JSON
     * @throws Exception
     */
    public String getStatus4W() throws Exception {
        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession();
        if(session.getAttribute("loginUser") != null) {
            CustomerPersonalPO personalPO = (CustomerPersonalPO)session.getAttribute("loginUser");
            CustomerAuthenticationStatusPO status = new CustomerAuthenticationStatusPO();
            status.setCustomerId(personalPO.getId());
            status.setState(Config.STATE_CURRENT);
            CustomerAuthenticationStatusPO po = MySQLDao.load(status, CustomerAuthenticationStatusPO.class, getConnection());
            if(po == null) {
                po = new CustomerAuthenticationStatusPO();
            }
            getResult().setReturnValue(po.toJsonObject());
        } else {
            getResult().setMessage("未登录");
            getRequest().setAttribute("returnObject", getResult());
            return "info";
        }
        return SUCCESS;
    }

    public CustomerAuthenticationStatusPO getCustomerAuthenticationStatus() {
        return customerAuthenticationStatus;
    }
    public void setCustomerAuthenticationStatus(CustomerAuthenticationStatusPO customerAuthenticationStatus) {this.customerAuthenticationStatus = customerAuthenticationStatus;}

    public CustomerAuthenticationStatusVO getCustomerAuthenticationStatusVO() {
        return customerAuthenticationStatusVO;
    }
    public void setCustomerAuthenticationStatusVO(CustomerAuthenticationStatusVO customerAuthenticationStatusVO) {this.customerAuthenticationStatusVO = customerAuthenticationStatusVO;}

}