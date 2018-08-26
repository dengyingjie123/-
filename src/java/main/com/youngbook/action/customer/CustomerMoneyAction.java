package com.youngbook.action.customer;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.entity.po.customer.CustomerMoneyPO;
import com.youngbook.entity.vo.customer.CustomerMoneyVO;
import com.youngbook.service.customer.CustomerMoneyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class CustomerMoneyAction extends BaseAction {

    //客户资金VO对象
    private CustomerMoneyVO customerMoneyVO = new CustomerMoneyVO();
    //客户资金PO类对象
    private CustomerMoneyPO customerMoney = new CustomerMoneyPO();
    //逻辑处理对象
    @Autowired
    CustomerMoneyService customerMoneyService;

    /**
     * 查询出所有验证码的数据
     * 把所有数据查询出来，在后台管理的 datagrid 组件中分页展示
     * 用法：前台的 URL 指向 /core/customer/CustomerMoney_list.action，如未成功，请检查 struts 配置
     *
     * 作者：
     * 内容：创建代码
     * 时间：
     *
     * @author 邓超
     * @return 适用于 easyui 的 JSON
     * @throws Exception
     */
    public String list() throws Exception {
        //设置查询条件
        List<KVObject> conditions = new ArrayList<KVObject>();
        //查询返回分页对象
       Pager pager = customerMoneyService.list(customerMoneyVO,conditions);
        //返回数据
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 新增或修改的数据
     * 把页面请求过来的数据进行持久化，如果存在 ID，则修改，否则会新增一条记录
     * 修改是把原来的数据状态修改为 update 状态的代号，再新增一条状态为当前的新记录
     * 用法：前台的 URL 指向 /core/customer/CustomerMoney_insertOrUpdate.action，如未成功，请检查 struts 配置
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @author 邓超
     * @return 适用于 easyui 的 JSON，类似 {code:100, message:'操作成功'}
     * @throws Exception
     */
    public String insertOrUpdate() throws Exception {
        int count = customerMoneyService.insertOrUpdate(customerMoney, getLoginUser(), getConnection());
        if (count != 1) {
            getResult().setMessage("操作失败");
        }
        return SUCCESS;
    }

    /**
     * 查询单条招商银行验证码数据
     * 通过 ID 或 SID，查询出一条记录
     * 用法：前台的 URL 指向 /core/customer/CustomerMoney_load.action，如未成功，请检查 struts 配置
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
        customerMoney = customerMoneyService.loadCustomerMoneyPO(customerMoney.getId());
        getResult().setReturnValue(customerMoney.toJsonObject4Form());
        return SUCCESS;
    }

    public CustomerMoneyVO getCustomerMoneyVO() {
        return customerMoneyVO;
    }
    public void setCustomerMoneyVO(CustomerMoneyVO customerMoneyVO) {
        this.customerMoneyVO = customerMoneyVO;
    }

    public CustomerMoneyPO getCustomerMoney() {
        return customerMoney;
    }
    public void setCustomerMoney(CustomerMoneyPO customerMoney) {
        this.customerMoney = customerMoney;
    }

    public CustomerMoneyService getCustomerMoneyService() {
        return customerMoneyService;
    }

    public void setCustomerMoneyService(CustomerMoneyService customerMoneyService) {
        this.customerMoneyService = customerMoneyService;
    }
}
