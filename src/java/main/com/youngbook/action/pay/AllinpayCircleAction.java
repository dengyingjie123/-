package com.youngbook.action.pay;

import com.mind.platform.system.base.CMData;
import com.mind.platform.system.base.DataRow;
import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObject;
import com.youngbook.common.KVObjects;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.allinpay.AllinPayUtils;
import com.youngbook.entity.po.allinpaycircle.TransactionPO;
import encryption.DataGramB2cUtil;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leevits on 5/15/2018.
 */
public class AllinpayCircleAction extends BaseAction {

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