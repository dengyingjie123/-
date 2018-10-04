package com.youngbook.action.pay;

import com.youngbook.action.BaseAction;
import com.youngbook.annotation.Permission;
import com.youngbook.common.KVObject;
import com.youngbook.common.KVObjects;
import com.youngbook.common.MyException;
import com.youngbook.common.ReturnObject;
import com.youngbook.common.config.XmlHelper;
import com.youngbook.common.utils.*;
import com.youngbook.common.utils.allinpay.AllinPayUtils;
import com.youngbook.dao.JSONDao;
import com.youngbook.dao.system.ILogDao;
import com.youngbook.entity.po.allinpaycircle.AllinpayCircleReceiveRawDataPO;
import com.youngbook.entity.po.allinpaycircle.TransactionPO;
import com.youngbook.service.allinpaycircle.AllinpayCircleService;
import encryption.DataGramB2cUtil;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.Quota;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leevits on 5/15/2018.
 */
public class AllinpayCircleAction extends BaseAction {

    @Autowired
    AllinpayCircleService allinpayCircleService;

    @Autowired
    ILogDao logDao;

    public String checkMobileCode() throws Exception {

        String bizId = getHttpRequestParameter("bizId");
        String mobileCode = getHttpRequestParameter("mobileCode");

        ReturnObject returnObject = allinpayCircleService.checkMobileCode(bizId, mobileCode, getConnection());

        if (returnObject.getCode() != 100) {
            getResult().setCode(-1);
            getResult().setMessage(returnObject.getMessage());
            getResult().setReturnValue("0");
        }
        else {
            getResult().setReturnValue("1");
        }

        return SUCCESS;
    }

    public String getMobileCodeInfo4ChangeBankNumber() throws Exception {

        String bizId = getHttpRequestParameter("bizId");

        String thirdPartyAIPName = "<processing_code>1088</processing_code>";

        KVObjects mobileCodeInfo = allinpayCircleService.getMobileCodeInfo(bizId, thirdPartyAIPName, getConnection());

        if (mobileCodeInfo == null) {
            MyException.newInstance("无法获取验证码信息").throwException();
        }

        getResult().setReturnValue(mobileCodeInfo.toJSONObject());

        return SUCCESS;
    }

    @Permission(require = "通联金融生态圈_换手机号")
    public String changeMobile() throws Exception {

        // String accountId, String mobileNew, String operatorId, Connection conn
        String accountId = getHttpRequestParameter("customerAccount.id");
        String mobileNew = getHttpRequestParameter("customerAccount.mobile");

        ReturnObject returnObject = allinpayCircleService.changeMobile(accountId, mobileNew, getLoginUser().getId(), getConnection());

        getResult().setCode(returnObject.getCode());
        getResult().setMessage(returnObject.getMessage());

//        getResult().setMessage(returnObject.getMessage());

        if (returnObject.getCode() == 100) {
            getResult().setReturnValue("1");
        }
        else {
            KVObjects r = new KVObjects();
            r.addItem("code", returnObject.getCode()).addItem("message", returnObject.getMessage());
            getResult().setReturnValue(r.toJSONObject());
        }

        return SUCCESS;
    }

    @Permission(require = "通联金融生态圈_换卡")
    public String changeBankCard() throws Exception {

        String bankCode = getHttpRequestParameter("customerAccount.bank");
        String number = getHttpRequestParameter("customerAccount.number");
        String mobile = getHttpRequestParameter("customerAccount.mobile");
        String accountId = getHttpRequestParameter("customerAccount.id");


        ReturnObject returnObject = allinpayCircleService.changeBankNumber(accountId, bankCode, number, mobile, getConnection());

        if (returnObject.getCode() != 100) {
            getResult().setCode(-1);
            getResult().setMessage(returnObject.getMessage());
            getResult().setReturnValue("0");
        }
        else {

            KVObjects responseKVObjects = JSONDao.toKVObjects(returnObject.getReturnValue().toString());

            XmlHelper helper = new XmlHelper(responseKVObjects.getItemString("responseXml"));

            String org_processing_code = helper.getValue("/transaction/head/processing_code");
            String org_trans_date = helper.getValue("/transaction/head/trans_date");
            String org_req_trace_num = helper.getValue("/transaction/response/req_trace_num");


            KVObjects kvObjects = new KVObjects();
            kvObjects.addItem("org_processing_code", org_processing_code);
            kvObjects.addItem("org_trans_date", org_trans_date);
            kvObjects.addItem("org_req_trace_num", org_req_trace_num);

            getResult().setReturnValue(kvObjects.toJSONObject());
        }

        return SUCCESS;
    }

    public String receiveRawData() throws Exception {

        String parametersStringValue = HttpUtils.getParametersStringValue(getRequest());

        AllinpayCircleReceiveRawDataPO allinpayCircleReceiveRawDataPO = new AllinpayCircleReceiveRawDataPO();
        allinpayCircleReceiveRawDataPO.setMessage(parametersStringValue);
        allinpayCircleReceiveRawDataPO.setStatus("0");

        allinpayCircleService.saveReceiveRawData(allinpayCircleReceiveRawDataPO, getConnection());

        return SUCCESS;
    }

    public String depositByInstitution() throws Exception {

        String orderId = getHttpRequestParameter("orderId");

        ReturnObject returnObject = allinpayCircleService.depositByInstitution(orderId, getLoginUser().getId(), getConnection());

        getResult().setMessage(returnObject.getMessage());

        if (returnObject.getCode() == 100) {
            getResult().setReturnValue("1");
        }
        else {
            KVObjects r = new KVObjects();
            r.addItem("code", returnObject.getCode()).addItem("message", returnObject.getMessage());
            getResult().setReturnValue(r.toJSONObject());
        }

        return SUCCESS;
    }

    public String payByShare() throws Exception {

        String orderId = getHttpRequestParameter("orderId");

        ReturnObject returnObject = allinpayCircleService.payByShare(orderId, getLoginUser().getId(), getConnection());

        if (returnObject.getCode() == 100) {
            getResult().setReturnValue("1");
        }
        else {
            KVObjects r = new KVObjects();
            r.addItem("code", returnObject.getCode()).addItem("message", returnObject.getMessage());
            getResult().setReturnValue(r.toJSONObject());
        }

        return SUCCESS;
    }


    public String queryCashShare() throws Exception {

        String customerId = getHttpRequestParameter("customerId");

        ReturnObject returnObject = allinpayCircleService.queryCashShare(customerId, getConnection());

        getResult().setCode(returnObject.getCode());
        getResult().setMessage(returnObject.getMessage());
        getResult().setReturnValue(returnObject.getReturnValue());

        return SUCCESS;
    }

    public String withdrawalByBankNormal() throws Exception {

        String accountId = getHttpRequestParameter("accountId");
        String moneyString = getHttpRequestParameter("money");

        ReturnObject returnObject = allinpayCircleService.withdrawalByBankNormal(accountId, Double.parseDouble(moneyString), getLoginUser().getId(), getConnection());

        getResult().setCode(returnObject.getCode());
        getResult().setMessage(returnObject.getMessage());
        getResult().setReturnValue(returnObject.getReturnValue());

        return SUCCESS;
    }
    public String queryWithOneOrder() throws Exception {
        String bizId = getHttpRequestParameter("bizId");
        ReturnObject returnObject = allinpayCircleService.queryWithOneOrder(bizId, getConnection());

        if (returnObject.getCode() == 100) {
            getResult().setReturnValue(returnObject.getReturnValue());
        }
        else {
            KVObjects r = new KVObjects();
            r.addItem("code", returnObject.getCode()).addItem("message", returnObject.getMessage());
            getResult().setReturnValue(r.toJSONObject());
        }


        return SUCCESS;
    }

    @Permission(require = "通联金融生态圈_信任开户")
    public String openAccountPersonalByTrust() throws Exception {

        String customerAccountId = getHttpRequestParameter("customerAccountId");

        ReturnObject returnObject = allinpayCircleService.openAccountPersonalByTrust(customerAccountId, getLoginUser().getId(), getConnection());

        getResult().setMessage(returnObject.getMessage());
        getResult().setReturnValue("1");

        return SUCCESS;
    }

    public String getMessage() throws Exception {

        

        return SUCCESS;
    }
}
