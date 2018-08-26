package com.youngbook.action.wsi;

import com.youngbook.common.ReturnObject;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.service.system.LogService;

import java.util.HashMap;
import java.util.Map;


public class ServiceInvoker {
    private String url = "";

    public static void main(String[] args ) {
        try {
            RequestObject requestObject = new RequestObject();
            requestObject.setName("allinpay.realNameVerification");
            requestObject.addData("name", "李扬1");
            requestObject.addData("number", "530103198203091219");
            ServiceInvoker invoker = new ServiceInvoker();
            ReturnObject returnObject = invoker.invoke(requestObject);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ReturnObject invoke(RequestObject requestObject) throws Exception {

        url = Config.getSystemConfig("wsi.url");

        System.out.println(requestObject.getData());
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("name", requestObject.getName());
        parameters.put("data", requestObject.getData());
        parameters.put("token", requestObject.getToken());

        LogService.info("URL: " + url + "; Parameters: 【"+parameters.toString()+"】", this.getClass());
        String responseText = HttpUtils.postRequest(url, parameters);

        LogService.debug(responseText, this.getClass());

        ReturnObject returnObject = ReturnObject.parse(responseText);
        return returnObject;
    }
}
