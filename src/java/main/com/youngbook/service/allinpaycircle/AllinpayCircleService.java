package com.youngbook.service.allinpaycircle;

import com.allinpay.ets.client.StringUtil;
import com.youngbook.common.KVObject;
import com.youngbook.common.KVObjects;
import com.youngbook.common.config.XmlHelper;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.allinpay.AllinPayUtils;
import com.youngbook.entity.po.allinpaycircle.TransactionPO;
import com.youngbook.service.BaseService;
import encryption.DataGramB2cUtil;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by leevits on 4/15/2018.
 */
@Component("allinpayCircleService")
public class AllinpayCircleService extends BaseService {

    public static void main(String[] args) throws Exception {

        TransactionPO transactionPO = new TransactionPO();

        transactionPO.setProcessing_code("1087");
        transactionPO.setTrans_date(TimeUtils.getNowDateYYYYMMDD());
        transactionPO.setTrans_time(TimeUtils.getNowTimeHH24MMSS());


        transactionPO.getRequest().addItem("req_trace_num", IdUtils.getNewLongIdString());
        transactionPO.getRequest().addItem("sub_merchant_id", "79020000");
        transactionPO.getRequest().addItem("sign_type", "2");
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

        System.out.println(base64);

        Map<String,String> rawParams = new HashMap<>();
        rawParams.put("msgPlain", base64);
        String postRequest = HttpUtils.postRequest(url, rawParams);

        System.out.println(postRequest);
    }



    public String sendTransaction(TransactionPO transactionPO, Connection conn) throws Exception {


        // XmlHelper helper = new XmlHelper();


        return null;
    }

}
