package com.youngbook.action.core;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObjects;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.system.ILogDao;
import com.youngbook.entity.po.core.APICommandPO;
import com.youngbook.entity.po.pay.APICommandStatus;
import com.youngbook.service.core.APICommandService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Lee on 11/23/2017.
 */
public class APICommandAction extends BaseAction {

    @Autowired
    APICommandService apiCommandService;

    @Autowired
    ILogDao logDao;

    /**
     * 接受任何消息
     * @return
     * @throws Exception
     */
    public String receiveAnyData() throws Exception {

        String parametersStringValue = HttpUtils.getParametersStringValue(getRequest());

        logDao.save("receiveAnyData", "收到信息", parametersStringValue, getConnection());

        return SUCCESS;
    }

    public String receiveFuiouMobilePay() throws Exception {

        String VERSION = getHttpRequestParameter("VERSION");
        String TYPE = getHttpRequestParameter("TYPE");
        String RESPONSECODE = getHttpRequestParameter("RESPONSECODE");
        String RESPONSEMSG = getHttpRequestParameter("RESPONSEMSG");
        String MCHNTCD = getHttpRequestParameter("MCHNTCD");
        String MCHNTORDERID = getHttpRequestParameter("MCHNTORDERID");
        String ORDERID = getHttpRequestParameter("ORDERID");
        String BANKCARD = getHttpRequestParameter("BANKCARD");
        String AMT = getHttpRequestParameter("AMT");
        String SIGN = getHttpRequestParameter("SIGN");

        KVObjects parameters = new KVObjects();
        parameters.addItem("VERSION", VERSION);
        parameters.addItem("TYPE", TYPE);
        parameters.addItem("RESPONSECODE", RESPONSECODE);
        parameters.addItem("RESPONSEMSG", RESPONSEMSG);
        parameters.addItem("MCHNTCD", MCHNTCD);
        parameters.addItem("MCHNTORDERID", MCHNTORDERID);
        parameters.addItem("ORDERID", ORDERID);
        parameters.addItem("BANKCARD", BANKCARD);
        parameters.addItem("AMT", AMT);
        parameters.addItem("SIGN", SIGN);

        APICommandPO apiCommandPO = new APICommandPO();
        apiCommandPO.setBizId(MCHNTORDERID);
        apiCommandPO.setRemain01(ORDERID);
        apiCommandPO.setCommands(StringUtils.buildUrlParameters(parameters));
        apiCommandPO.setCallbackCode(RESPONSECODE);
        apiCommandPO.setCallbackMessage(RESPONSEMSG);

        apiCommandPO.setStatus(APICommandStatus.UNDEAL);
        apiCommandPO.setCommandType(3);

        apiCommandService.receiveFuiouMobilePay(apiCommandPO, getConnection());

        return SUCCESS;
    }


    public String receiveFuiouPCPay() throws Exception {

        String mchnt_cd = getHttpRequestParameter("mchnt_cd");
        String order_id = getHttpRequestParameter("order_id");
        String order_date = getHttpRequestParameter("order_date");
        String order_amt = getHttpRequestParameter("order_amt");
        String order_st = getHttpRequestParameter("order_st");
        String txn_cd = getHttpRequestParameter("txn_cd");
        String order_pay_code = getHttpRequestParameter("order_pay_code");
        String order_pay_error = getHttpRequestParameter("order_pay_error");
        String fy_ssn = getHttpRequestParameter("fy_ssn");
        String resv1 = getHttpRequestParameter("resv1");
        String md5 = getHttpRequestParameter("md5");

        KVObjects parameters = new KVObjects();
        parameters.addItem("mchnt_cd", mchnt_cd);
        parameters.addItem("order_id", order_id);
        parameters.addItem("order_date", order_date);
        parameters.addItem("order_amt", order_amt);
        parameters.addItem("order_st", order_st);
        parameters.addItem("txn_cd", txn_cd);
        parameters.addItem("order_pay_code", order_pay_code);
        parameters.addItem("order_pay_error", order_pay_error);
        parameters.addItem("fy_ssn", fy_ssn);
        parameters.addItem("resv1", resv1);
        parameters.addItem("md5", md5);

        APICommandPO apiCommandPO = new APICommandPO();
        apiCommandPO.setBizId(order_id);
        apiCommandPO.setCommands(StringUtils.buildUrlParameters(parameters));
        apiCommandPO.setCallbackCode(order_pay_code);
        apiCommandPO.setCallbackMessage(order_pay_error);

        apiCommandPO.setStatus(APICommandStatus.UNDEAL);
        apiCommandPO.setCommandType(3);

        apiCommandService.receiveFuiouPCPay(apiCommandPO, getConnection());

        return SUCCESS;
    }
}
