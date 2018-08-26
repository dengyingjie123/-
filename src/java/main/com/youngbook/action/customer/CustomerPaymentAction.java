package com.youngbook.action.customer;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.entity.po.customer.CustomerPaymentPO;
import com.youngbook.entity.vo.customer.CustomerPaymentVO;
import com.youngbook.service.customer.CustomerPaymentService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/4/2.
 * 客户支付Action类
 */
public class CustomerPaymentAction extends BaseAction {

    //客户支付VO类 PO 类
    private CustomerPaymentVO customerPaymentVO = new CustomerPaymentVO();
    private CustomerPaymentPO customerPayment = new CustomerPaymentPO();
    //客户支付Service类
    private CustomerPaymentService service = new CustomerPaymentService();

    /**
     * 新增或修改数据
     * 把页面请求过来的数据进行持久化，如果存在 ID，则修改，否则会新增一条记录
     * 修改是把原来的数据状态修改为 update 状态的代号，再新增一条状态为当前的新记录
     * 用法：前台的 URL 指向 /core/customer/CustomerPayment_insertOrUpdate.action，如未成功，请检查 struts 配置
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
        int count = service.insertOrUpdate(customerPayment, getLoginUser(), getConnection());
        if (count != 1) {
            getResult().setMessage("操作失败");
        }
        return SUCCESS;
    }

    /**
     * 查询出所有的数据
     * 把所有数据查询出来，在后台管理的 datagrid 组件中分页展示
     * 用法：前台的 URL 指向 /core/customer/CustomerPayment_list.action，如未成功，请检查 struts 配置
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
        List<KVObject> conditions = new ArrayList<KVObject>();
        Pager pager = service.list(customerPaymentVO, conditions);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 查询单条数据
     * 通过 ID 或 SID，查询出一条记录
     * 用法：前台的 URL 指向 /core/customer/CustomerPayment_load.action，如未成功，请检查 struts 配置
     *
     * 作者：
     * 内容：创建代码
     * 时间：
     *
     * @author 邓超
     * @return 适用于 easyui 的 JSON
     * @throws Exception
     */
    public String load() throws Exception {
        customerPayment = service.loadCustomerPaymentPO(customerPayment.getId());
        getResult().setReturnValue(customerPayment.toJsonObject4Form());
        return SUCCESS;
    }

    /**
     * 删除一条数据
     * 通过 ID 或 SID，把查询出来的记录状态设置为已删除，再新增一条状态为当前的新记录
     * 用法：前台的 URL 指向 /core/customer/CustomerPayment_delete.action，如未成功，请检查 struts 配置
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
        service.delete(customerPayment,getLoginUser(),getConnection());
        return SUCCESS;
    }

    public CustomerPaymentVO getCustomerPaymentVO() {
        return customerPaymentVO;
    }
    public void setCustomerPaymentVO(CustomerPaymentVO customerPaymentVO) {this.customerPaymentVO = customerPaymentVO;}

    public CustomerPaymentPO getCustomerPayment() {
        return customerPayment;
    }
    public void setCustomerPayment(CustomerPaymentPO customerPayment) {
        this.customerPayment = customerPayment;
    }

    public CustomerPaymentService getService() {
        return service;
    }
    public void setService(CustomerPaymentService service) {
        this.service = service;
    }

}