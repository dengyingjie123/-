package com.youngbook.action.customer;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.entity.po.customer.CustomerAuthenticationCodePO;
import com.youngbook.entity.vo.customer.CustomerAuthenticationCodeVO;
import com.youngbook.service.customer.AuthenticationCodeService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class CustomerAuthenticationCodeAction extends BaseAction {

    @Autowired
    AuthenticationCodeService authenticationCodeService;
    CustomerAuthenticationCodePO customerAuthenticationCodePO = new CustomerAuthenticationCodePO();
    CustomerAuthenticationCodeVO authenticationCodeVO = new CustomerAuthenticationCodeVO();

    /**
     * 获取数据
     * @return String
     * @throws Exception
     */
    public String list() throws Exception {

        authenticationCodeVO = HttpUtils.getInstanceFromRequest(getRequest(), "authenticationCodeVO", CustomerAuthenticationCodeVO.class);

        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions =new ArrayList<KVObject>();
        Pager pager = this.authenticationCodeService.list(authenticationCodeVO, conditions, request);
        JSONObject json = pager.toJsonObject();
        getResult().setReturnValue(json);
        return SUCCESS;
    }

    /**
     * 插入或修改数据
     * @return String
     * @throws Exception
     */
    public String insertOrUpdate() throws Exception {

        customerAuthenticationCodePO = HttpUtils.getInstanceFromRequest(getRequest(), "customerAuthenticationCodePO", CustomerAuthenticationCodePO.class);

        int count = authenticationCodeService.insertOrUpdate(customerAuthenticationCodePO, getLoginUser(), getConnection());
        if (count != 1) {
            getResult().setMessage("操作失败");
        }
        return SUCCESS;
    }

    /**
     * 查询数据
     * @return String
     * @throws Exception
     */
    public String load() throws Exception {

        customerAuthenticationCodePO = HttpUtils.getInstanceFromRequest(getRequest(), "customerAuthenticationCodePO", CustomerAuthenticationCodePO.class);

        customerAuthenticationCodePO = authenticationCodeService.loadCustomerAuthenticationCodePO(customerAuthenticationCodePO.getId());
        getResult().setReturnValue(customerAuthenticationCodePO.toJsonObject4Form());
        return SUCCESS;
    }

    /**
     * 删除
     * @return String
     * @throws Exception
     */
    public String delete() throws Exception {
        customerAuthenticationCodePO = HttpUtils.getInstanceFromRequest(getRequest(), "customerAuthenticationCodePO", CustomerAuthenticationCodePO.class);
        authenticationCodeService.delete(customerAuthenticationCodePO, getLoginUser(), getConnection());
        return SUCCESS;
    }

    public AuthenticationCodeService getAuthenticationCodeService() {
        return authenticationCodeService;
    }

    public void setAuthenticationCodeService(AuthenticationCodeService authenticationCodeService) {
        this.authenticationCodeService = authenticationCodeService;
    }

    public CustomerAuthenticationCodeVO getAuthenticationCodeVO() {
        return authenticationCodeVO;
    }
    public void setAuthenticationCodeVO(CustomerAuthenticationCodeVO authenticationCodeVO) {this.authenticationCodeVO = authenticationCodeVO;}

    public CustomerAuthenticationCodePO getCustomerAuthenticationCodePO() {
        return customerAuthenticationCodePO;
    }
    public void setCustomerAuthenticationCodePO(CustomerAuthenticationCodePO customerAuthenticationCodePO) {this.customerAuthenticationCodePO = customerAuthenticationCodePO;}
}
