package com.youngbook.action.customer;

import com.youngbook.action.BaseAction;
import com.youngbook.annotation.Security;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.customer.CustomerPersonalPO;
import com.youngbook.entity.vo.customer.CustomerProductionVO;
import com.youngbook.service.customer.CustomerProductionService;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2014/11/28.
 */

public class CustomerProductionAction extends BaseAction {

    private CustomerProductionVO customerProductionVO = new CustomerProductionVO();

    @Autowired
    CustomerProductionService customerProductionService;

    /**
     * 查询出所有的数据
     * 把所有数据查询出来，在后台管理的 datagrid 组件中分页展示
     * 用法：前台的 URL 指向 /core/customer/CustomerProduction_list.action，如未成功，请检查 struts 配置
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
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request, CustomerProductionVO.class);
        String customerId = getRequest().getParameter("customerId");
        String customerRemark = getRequest().getParameter("customerRemark");//HOPEWEALTH-1224 增加一个参数的读取，判断客户是机构还是个人

        if (!StringUtils.isEmpty(customerId) && !StringUtils.isEmpty(customerRemark)) {
            Pager pager = customerProductionService.list(customerProductionVO, conditions , customerId, customerRemark);

            getResult().setReturnValue(pager.toJsonObject());
        }

        return SUCCESS;
    }




    /**
     * 交易平台请求的 Action，列出客户所购买的产品
     * 前提是网站的 Customer 已经登录，关联产品、产品构成、客户表查询到客户所购买的产品
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @author 邓超
     * @return 适用于交易平台的 json
     * @throws Exception
     *
     *修改人：姚章鹏
     * 内容：在sql上添加 P.WebsiteDisplayName,查出网站显示的产品名字
     * 时间：2015年7月31日15:44:59
     */
    @Security( needWebLogin = true)
    public String list4Web() throws Exception {

        CustomerPersonalPO customer = Config.getLoginCustomerInSession(getRequest());
        Pager pager = customerProductionService.list4Web(customer.getId(), getConnection());
        getResult().setReturnValue(pager.toJsonObject());

        return SUCCESS;
    }

    public CustomerProductionVO getCustomerProductionVO() {
        return customerProductionVO;
    }
    public void setCustomerProductionVO(CustomerProductionVO customerProductionVO) {this.customerProductionVO = customerProductionVO;}
}
