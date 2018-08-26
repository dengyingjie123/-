package com.youngbook.action.customer;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.config.AesEncrypt;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.customer.CustomerCertificatePO;
import com.youngbook.service.customer.CustomerCertificateService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class CustomerCertificateAction extends BaseAction {

    private CustomerCertificatePO customerCertificate = new CustomerCertificatePO();

    @Autowired
    CustomerCertificateService customerCertificateService;

    /**
     * 新增或修改验证码的数据
     * 把页面请求过来的数据进行持久化，如果存在 ID，则修改，否则会新增一条记录
     * 修改是把原来的数据状态修改为 update 状态的代号，再新增一条状态为当前的新记录
     * 用法：前台的 URL 指向 /core/customer/CustomerCertificate_load.action，如未成功，请检查 struts 配置
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
      //  boolean temp = service.isNumber(customerCertificate.getName(),customerCertificate.getNumber(),customerCertificate.getId());

        customerCertificateService.insertOrUpdate(customerCertificate, getLoginUser().getId(), getConnection());

        return SUCCESS;
    }

    /**
     * 删除一条数据
     * 通过 ID 或 SID，把查询出来的记录状态设置为已删除，再新增一条状态为当前的新记录
     * 用法：前台的 URL 指向 /core/customer/CustomerCertificate_delete.action，如未成功，请检查 struts 配置
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
        customerCertificateService.delete(customerCertificate, getLoginUser().getId(), getConnection());
        return SUCCESS;
    }

    /**
     * 查询单条数据
     * 通过 ID 或 SID，查询出一条记录
     * 用法：前台的 URL 指向 /core/customer/CustomerCertificate_load.action，如未成功，请检查 struts 配置
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
        customerCertificate.setState(Config.STATE_CURRENT);
        customerCertificate = MySQLDao.load(customerCertificate, CustomerCertificatePO.class);
        //解决身份证号码
        if(!StringUtils.isEmpty(customerCertificate.getNumber()))
        {
            customerCertificate.setNumber(AesEncrypt.decrypt(customerCertificate.getNumber()));
        }
        getResult().setReturnValue(customerCertificate.toJsonObject4Form());
        return SUCCESS;
    }

    /**
     * 查询出所有的数据
     * 把所有数据查询出来，在后台管理的 datagrid 组件中分页展示
     * 用法：前台的 URL 指向 /core/customer/CustomerCertificate_list.action，如未成功，请检查 struts 配置
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
        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions = new ArrayList<KVObject>();
        conditions.add(new KVObject("customerId", " in ('" + customerCertificate.getCustomerId() + "')"));
        Pager pager = customerCertificateService.list(customerCertificate, conditions, request);
        getResult().setReturnValue(pager.toJsonCustomerCertificatePO());
        return SUCCESS;
    }

    public CustomerCertificatePO getCustomerCertificate() {return customerCertificate;}
    public void setCustomerCertificate(CustomerCertificatePO customerCertificate) {this.customerCertificate = customerCertificate;}

}