package com.youngbook.action.customer;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.ReturnObject;
import com.youngbook.common.config.Config;
import com.youngbook.common.config.Config4Bank;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.bank.MerchantCodePO;
import com.youngbook.entity.po.bank.MerchantOrderPO;
import com.youngbook.entity.po.bank.MerchantTransferPO;
import com.youngbook.entity.po.customer.CustomerDepositPO;
import com.youngbook.entity.po.customer.CustomerMoneyLogPO;
import com.youngbook.entity.po.customer.CustomerMoneyPO;
import com.youngbook.entity.po.customer.CustomerPersonalPO;
import com.youngbook.entity.vo.customer.CustomerDepositVO;
import com.youngbook.entity.wvo.customer.CustomerPersonalWVO;
import com.youngbook.service.customer.CustomerDepositService;
import com.youngbook.service.customer.CustomerInfoService;
import com.youngbook.service.customer.CustomerMoneyLogService;
import com.youngbook.service.customer.CustomerMoneyService;
import com.youngbook.service.production.OrderService;
import com.youngbook.service.system.CaptchaService;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.util.List;

public class CustomerDepositAction extends BaseAction {

    @Autowired
    OrderService orderService;

    @Autowired
    CustomerDepositService customerDepositService;

    private CustomerDepositPO customerDeposit = new CustomerDepositPO();
    private CustomerMoneyPO customerMoney = new CustomerMoneyPO();
    private CustomerMoneyLogPO customerMoneyLog = new CustomerMoneyLogPO();
    private CustomerDepositVO customerDepositVO = new CustomerDepositVO();


    /**
     * 新增或修改验证码的数据
     * 把页面请求过来的数据进行持久化，如果存在 ID，则修改，否则会新增一条记录7
     * 修改是把原来的数据状态修改为 update 状态的代号，再新增一条状态为当前的新记录
     * 用法：前台的 URL 指向 /core/customer/CustomerDeposit_load.action，如未成功，请检查 struts 配置
     * <p/>
     * 作者：
     * 内容：创建代码
     * 时间：
     *
     * @return 适用于 easyui 的 JSON，类似 {code:100, message:'操作成功'}
     * @throws Exception
     * @author 邓超
     */
    public String insertOrUpdate() throws Exception {
        int count = customerDepositService.insertOrUpdate(customerDeposit, getLoginUser(), getConnection());
        if (count != 1) {
            getResult().setMessage("操作失败");
        }
        return SUCCESS;
    }

    /**
     * 删除一条数据
     * 通过 ID 或 SID，把查询出来的记录状态设置为已删除，再新增一条状态为当前的新记录
     * 用法：前台的 URL 指向 /core/customer/CustomerDeposit_delete.action，如未成功，请检查 struts 配置
     * <p/>
     * 作者：
     * 内容：创建代码
     * 时间：
     *
     * @return 适用于 easyui 的 JSON，类似 {code:100, message:'操作成功'}
     * @throws Exception
     * @author 邓超
     */
    public String delete() throws Exception {
        customerDepositService.delete(customerDeposit, getLoginUser(), getConnection());
        return SUCCESS;
    }

    /**
     * 查询单条数据
     * 通过 ID 或 SID，查询出一条记录
     * 用法：前台的 URL 指向 /core/customer/CustomerDeposit_load.action，如未成功，请检查 struts 配置
     * <p/>
     * 作者：
     * 内容：创建代码
     * 时间：
     *
     * @return 适用于 easyui 的 JSON
     * @throws Exception
     * @author 邓超
     */
    public String load() throws Exception {
        customerDeposit.setState(Config.STATE_CURRENT);
        customerDeposit = MySQLDao.load(customerDeposit, CustomerDepositPO.class);
        getResult().setReturnValue(customerDeposit.toJsonObject4Form());
        return SUCCESS;
    }

    /**
     * 查询出所有的数据
     * 把所有数据查询出来，在后台管理的 datagrid 组件中分页展示
     * 用法：前台的 URL 指向 /core/customer/CustomerDeposit_list.action，如未成功，请检查 struts 配置
     * <p/>
     * 作者：
     * 内容：创建代码
     * 时间：
     *
     * @return 适用于 easyui 的 JSON
     * @throws Exception
     * @author 邓超
     */
    public String list() throws Exception {
        ;
        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request, CustomerDepositVO.class);
        Pager pager = customerDepositService.list(customerDepositVO, conditions);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    public CustomerDepositPO getCustomerDeposit() {
        return customerDeposit;
    }

    public void setCustomerDeposit(CustomerDepositPO customerDeposit) {
        this.customerDeposit = customerDeposit;
    }

    public CustomerDepositVO getCustomerDepositVO() {
        return customerDepositVO;
    }

    public void setCustomerDepositVO(CustomerDepositVO customerDepositVO) {
        this.customerDepositVO = customerDepositVO;
    }

    public CustomerMoneyLogPO getCustomerMoneyLog() {
        return customerMoneyLog;
    }

    public void setCustomerMoneyLog(CustomerMoneyLogPO customerMoneyLog) {
        this.customerMoneyLog = customerMoneyLog;
    }

    public CustomerMoneyPO getCustomerMoney() {
        return customerMoney;
    }

    public void setCustomerMoney(CustomerMoneyPO customerMoney) {
        this.customerMoney = customerMoney;
    }

}
