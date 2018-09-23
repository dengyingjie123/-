package com.youngbook.action.pay;

import com.mind.platform.system.base.CMData;
import com.mind.platform.system.base.DataRow;
import com.youngbook.action.BaseAction;
import com.youngbook.annotation.Permission;
import com.youngbook.common.KVObject;
import com.youngbook.common.KVObjects;
import com.youngbook.common.MyException;
import com.youngbook.common.ReturnObject;
import com.youngbook.common.config.XmlHelper;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.TimeUtils;
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


        getResult().setMessage(returnObject.getMessage());

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

    @Permission(require = "通联金融生态圈_信任开户")
    public String openAccountPersonalByTrust() throws Exception {

        String customerAccountId = getHttpRequestParameter("customerAccountId");

        ReturnObject returnObject = allinpayCircleService.openAccountPersonalByTrust(customerAccountId, getLoginUser().getId(), getConnection());

        getResult().setMessage(returnObject.getMessage());
        getResult().setReturnValue("1");

        return SUCCESS;
    }

    public String getMessage() throws Exception {

        TransactionPO transactionPO = new TransactionPO();

        transactionPO.setProcessing_code("1087");
        transactionPO.setTrans_date(TimeUtils.getNowDateYYYYMMDD());
        transactionPO.setTrans_time(TimeUtils.getNowTimeHH24MMSS());


        transactionPO.getRequest().addItem("req_trace_num", IdUtils.getNewLongIdString());
        transactionPO.getRequest().addItem("sub_merchant_id", "79020000");
        transactionPO.getRequest().addItem("sign_type", "3");
        transactionPO.getRequest().addItem("prod_flag", "0");
        transactionPO.getRequest().addItem("bnk_id", "03040000");
        transactionPO.getRequest().addItem("acct_type", "1");
        transactionPO.getRequest().addItem("acct_num", "1234558712203953");
        transactionPO.getRequest().addItem("cnaps_code", "");
        transactionPO.getRequest().addItem("hld_name", "李扬");
        transactionPO.getRequest().addItem("cer_type", "01");
        transactionPO.getRequest().addItem("cer_num", "530103198203091219");
        transactionPO.getRequest().addItem("tel_num", "13888939712");
        transactionPO.getRequest().addItem("supply_inst_code", "2");
        transactionPO.getRequest().addItem("is_send_msg", "");
        transactionPO.getRequest().addItem("mhnt_name", "2");
        transactionPO.getRequest().addItem("ms_signature", "");
        transactionPO.getRequest().addItem("reqs_url", "");
        transactionPO.getRequest().addItem("resp_url", "");
        transactionPO.getRequest().addItem("ip_addr", "");
        transactionPO.getRequest().addItem("addtnl_data1", "");
        transactionPO.getRequest().addItem("coop_id", "");
        transactionPO.getRequest().addItem("sys_id", "");
        transactionPO.getRequest().addItem("account_manager_tel", "");


        System.out.println(transactionPO.toXmlString());

        // allinpay_circle


        String signature = DataGramB2cUtil.signature(transactionPO.toXmlString());

        transactionPO.setSign_code(signature);

        System.out.println(transactionPO.toXmlString());


        String url = "http://116.228.64.55:28082/AppStsWeb/service/acquireAction.action";


        String base64 = AllinPayUtils.getBASE64(transactionPO.toXmlString());

        KVObjects kvObjects = new KVObjects();
        kvObjects.addItem("msgPlain", base64);

        getResult().setReturnValue(kvObjects.toJSONObject());

        return SUCCESS;
    }


    public String getMessage2() throws Exception {

        TransactionPO transactionPO = new TransactionPO();

        transactionPO.setProcessing_code("1087");
        transactionPO.setTrans_date(TimeUtils.getNowDateYYYYMMDD());
        transactionPO.setTrans_time(TimeUtils.getNowTimeHH24MMSS());


        transactionPO.getRequest().addItem("req_trace_num", IdUtils.getNewLongIdString());
        transactionPO.getRequest().addItem("sub_merchant_id", "79020000");
        transactionPO.getRequest().addItem("sign_type", "3");
        transactionPO.getRequest().addItem("prod_flag", "0");
        transactionPO.getRequest().addItem("bnk_id", "03040000");
        transactionPO.getRequest().addItem("acct_type", "1");
        transactionPO.getRequest().addItem("acct_num", "1234558712203953");
        transactionPO.getRequest().addItem("cnaps_code", "");
        transactionPO.getRequest().addItem("hld_name", "李扬");
        transactionPO.getRequest().addItem("cer_type", "01");
        transactionPO.getRequest().addItem("cer_num", "530103198203091219");
        transactionPO.getRequest().addItem("tel_num", "13888939712");
        transactionPO.getRequest().addItem("supply_inst_code", "2");
        transactionPO.getRequest().addItem("is_send_msg", "");
        transactionPO.getRequest().addItem("mhnt_name", "2");
        transactionPO.getRequest().addItem("ms_signature", "");
        transactionPO.getRequest().addItem("reqs_url", "");
        transactionPO.getRequest().addItem("resp_url", "");
        transactionPO.getRequest().addItem("ip_addr", "");
        transactionPO.getRequest().addItem("addtnl_data1", "");
        transactionPO.getRequest().addItem("coop_id", "");
        transactionPO.getRequest().addItem("sys_id", "");
        transactionPO.getRequest().addItem("account_manager_tel", "");


        DataRow transactionDataRow = new CMData();
        DataRow headListRow = new CMData(), requestListRow = new CMData();

        headListRow.put("processing_code", transactionPO.getProcessing_code());
        headListRow.put("inst_id", transactionPO.getInst_id());
        headListRow.put("trans_date", transactionPO.getTrans_date());
        headListRow.put("trans_time", transactionPO.getTrans_time());
        headListRow.put("ver_num", transactionPO.getVer_num());


        requestListRow.put("req_trace_num", transactionPO.getRequest().getItemString("req_trace_num"));
        requestListRow.put("sub_merchant_id", transactionPO.getRequest().getItemString("sub_merchant_id"));
        requestListRow.put("sign_type", transactionPO.getRequest().getItemString("sign_type"));
        requestListRow.put("prod_flag", transactionPO.getRequest().getItemString("prod_flag"));
        requestListRow.put("bnk_id", transactionPO.getRequest().getItemString("bnk_id"));
        requestListRow.put("acct_type", transactionPO.getRequest().getItemString("acct_type"));
        requestListRow.put("acct_num", transactionPO.getRequest().getItemString("acct_num"));
        requestListRow.put("cnaps_code", transactionPO.getRequest().getItemString("cnaps_code"));
        requestListRow.put("hld_name", transactionPO.getRequest().getItemString("hld_name"));
        requestListRow.put("cer_type", transactionPO.getRequest().getItemString("cer_type"));
        requestListRow.put("cer_num", transactionPO.getRequest().getItemString("cer_num"));
        requestListRow.put("tel_num", transactionPO.getRequest().getItemString("tel_num"));
        requestListRow.put("supply_inst_code", transactionPO.getRequest().getItemString("supply_inst_code"));
        requestListRow.put("is_send_msg", transactionPO.getRequest().getItemString("is_send_msg"));
        requestListRow.put("mhnt_name", transactionPO.getRequest().getItemString("mhnt_name"));
        requestListRow.put("ms_signature", transactionPO.getRequest().getItemString("ms_signature"));
        requestListRow.put("reqs_url", transactionPO.getRequest().getItemString("reqs_url"));
        requestListRow.put("resp_url", transactionPO.getRequest().getItemString("resp_url"));
        requestListRow.put("ip_addr", transactionPO.getRequest().getItemString("ip_addr"));
        requestListRow.put("addtnl_data1", transactionPO.getRequest().getItemString("addtnl_data1"));
        requestListRow.put("coop_id", transactionPO.getRequest().getItemString("coop_id"));
        requestListRow.put("sys_id", transactionPO.getRequest().getItemString("sys_id"));
        requestListRow.put("account_manager_tel", transactionPO.getRequest().getItemString("account_manager_tel"));


        String xml = DataGramB2cUtil.createRequestJrQianyueCryptoMsg(transactionDataRow);

        System.out.println("xml ===" + xml);

        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        paramList.add(new BasicNameValuePair("msgPlain", xml));

        DefaultHttpClient httpClient = new DefaultHttpClient();

        String url = "http://116.228.64.55:28082/AppStsWeb/service/acquireAction.action";
        HttpPost httpPost = new HttpPost(url);

        httpPost.setEntity(new UrlEncodedFormEntity(paramList, Consts.UTF_8));
        HttpResponse response = httpClient.execute(httpPost);
        System.out.println("response ==" + response);

        return SUCCESS;
    }
}
