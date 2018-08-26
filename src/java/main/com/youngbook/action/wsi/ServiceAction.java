package com.youngbook.action.wsi;

import com.youngbook.action.BaseAction;
import com.youngbook.common.ReturnObject;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.dao.JSONDao;
import com.youngbook.service.allinpay.AllinpayAuthenticationService;
import com.youngbook.service.customer.CustomerPersonalService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Lee on 11/11/2015.
 */
public class ServiceAction extends BaseAction {

    @Autowired
    CustomerPersonalService customerPersonalService;

    private String name = "";
    private String version = "";
    private String data = "";
    private String callback = "";
    private String tokens = "";

    public String invoke() throws Exception {

        init();

        if (!tokens.equals(Config.getSystemConfig("wsi.token"))) {
            throw new Exception("服务身份验证失败");
        }
        Map map = JSONDao.toMap(data);
        if (name.equals("allinpay.realNameVerification")) {

            // System.out.println(map);
            // String customerName = StringUtils.toUtf8(map.get("name").toString());
            String customerName = map.get("name").toString();
            String customerNumber = map.get("number").toString();
            AllinpayAuthenticationService service = new AllinpayAuthenticationService();
            boolean isPass = service.whetherPassRealNameVerification(customerName, customerNumber);
            getResult().setReturnValue("0");
            if (isPass) {
                getResult().setReturnValue("1");
            }
        }
        else if (name.equals("allinpay.realNameVerification.v2")) {
            String customerName = map.get("name").toString();
            String customerNumber = map.get("number").toString();
            String bankCode = map.get("bankCode").toString();
            String bankNumber = map.get("bankNumber").toString();
            ReturnObject returnObject = customerPersonalService.validateIdentityV2ByAllinpay(customerName, customerNumber, bankCode, bankNumber);
            getResult().setMessage(returnObject.getMessage());
            getResult().setReturnValue(returnObject.getReturnValue());
        }

        return SUCCESS;
    }


    private void init() throws Exception {
        HttpServletRequest request = getRequest();
        name = HttpUtils.getParameter(request, "name");
        version = HttpUtils.getParameter(request, "version");
        data = HttpUtils.getParameter(request, "data");
        callback = HttpUtils.getParameter(request, "callback");
        tokens = HttpUtils.getParameter(request, "token");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }


    public String getTokens() {
        return tokens;
    }

    public void setTokens(String tokens) {
        this.tokens = tokens;
    }
}
