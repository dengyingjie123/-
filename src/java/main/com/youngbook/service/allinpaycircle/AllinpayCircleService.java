package com.youngbook.service.allinpaycircle;

import com.alibaba.fastjson.JSONObject;
import com.allinpay.ets.client.StringUtil;
import com.emulator.paymentgateway.util.PaymentGatewayService;
import com.emulator.paymentgateway.util.SecurityUtil;
import com.mind.platform.system.base.CMData;
import com.mind.platform.system.base.DataRow;
import com.youngbook.annotation.Excel;
import com.youngbook.common.*;
import com.youngbook.common.config.AesEncrypt;
import com.youngbook.common.config.Config;
import com.youngbook.common.config.XmlHelper;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.*;
import com.youngbook.common.utils.allinpay.AllinPayUtils;
import com.youngbook.dao.JSONDao;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.allinpaycircle.IAllinpayCircleDao;
import com.youngbook.dao.allinpaycircle.ITransactionDao;
import com.youngbook.dao.core.IAPICommandDao;
import com.youngbook.dao.customer.ICustomerAccountDao;
import com.youngbook.dao.customer.ICustomerCertificateDao;
import com.youngbook.dao.customer.ICustomerPersonalDao;
import com.youngbook.dao.production.IOrderDao;
import com.youngbook.dao.production.IOrderDetailDao;
import com.youngbook.dao.production.IProductionDao;
import com.youngbook.entity.po.allinpaycircle.AllinpayCircleReceiveRawDataPO;
import com.youngbook.entity.po.allinpaycircle.TransactionPO;
import com.youngbook.entity.po.core.APICommandDirection;
import com.youngbook.entity.po.core.APICommandPO;
import com.youngbook.entity.po.core.APICommandType;
import com.youngbook.entity.po.customer.CustomerAccountPO;
import com.youngbook.entity.po.customer.CustomerCertificatePO;
import com.youngbook.entity.po.customer.CustomerPersonalPO;
import com.youngbook.entity.po.production.OrderPO;
import com.youngbook.entity.po.production.OrderStatus;
import com.youngbook.entity.po.production.ProductionPO;
import com.youngbook.entity.vo.Sale.PaymentPlanVO;
import com.youngbook.service.BaseService;
import encryption.DataGramB2cUtil;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import java.net.ConnectException;
import java.security.Key;
import java.sql.Connection;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.XMLFormatter;

/**
 * Created by leevits on 4/15/2018.
 */
@Component("allinpayCircleService")
public class AllinpayCircleService extends BaseService {

    @Autowired
    IAPICommandDao apiCommandDao;

    @Autowired
    ICustomerPersonalDao customerPersonalDao;

    @Autowired
    ICustomerCertificateDao customerCertificateDao;

    @Autowired
    ICustomerAccountDao customerAccountDao;

    @Autowired
    IAllinpayCircleDao allinpayCircleDao;

    @Autowired
    ITransactionDao transactionDao;

    @Autowired
    IProductionDao productionDao;

    @Autowired
    IOrderDao orderDao;

    @Autowired
    IOrderDetailDao orderDetailDao;

    private static String callbackUrl = "";


    public String getCallbackUrl() throws Exception {

        if (StringUtils.isEmpty(callbackUrl)) {
            callbackUrl = Config.getSystemConfig("allinpayCircle.callback.url");
        }

        return callbackUrl;
    }

    public void dealRawData(Connection conn) throws Exception {

        allinpayCircleDao.dealRawData(conn);
    }


    public AllinpayCircleReceiveRawDataPO saveReceiveRawData(AllinpayCircleReceiveRawDataPO allinpayCircleReceiveRawDataPO, Connection conn) throws Exception {

        return allinpayCircleDao.saveReceiveRawData(allinpayCircleReceiveRawDataPO, conn);
    }

    /**
     * 解析返回值
     * @param returnObject
     * @return
     * @throws Exception
     */
    private XmlHelper getResponseXmlHelper(ReturnObject returnObject) throws Exception {

        XmlHelper helper = null;

        if (returnObject == null || returnObject.getCode() != 100) {
            MyException.newInstance("返回值解析异常，【"+returnObject.getMessage()+"】", returnObject.getMessage()).throwException();
        }

        String jsonString = returnObject.getReturnValue().toString();

        if (!StringUtils.isEmpty(jsonString)) {
            KVObjects kvObjects = JSONDao.toKVObjects(jsonString);

            helper = new XmlHelper(kvObjects.getItemString("responseXml"));
        }

        if (helper == null) {
            MyException.newInstance("无法解析返回值", returnObject.getMessage()).throwException();
        }

        return helper;
    }


    /**
     * 文件联机下载
     * @param fileType
     * @return
     * @throws Exception
     */
    public String buildDownloadFileUrl(String fileType) throws Exception {
        StringBuffer sbUrl = new StringBuffer();

        sbUrl.append("http://服务器地址/AppStsWeb/service/orgDownloadReconFile.action?");

        TransactionPO transactionPO = new TransactionPO();
        transactionPO.setTrans_date(TimeUtils.getNowDateYYYYMMDD());
        transactionPO.setTrans_time(TimeUtils.getNowTimeHH24MMSS());

        KVObjects parameters = new KVObjects();

        parameters.addItem("inst_id", transactionPO.getInst_id());
        parameters.addItem("trans_date", transactionPO.getTrans_date());
        parameters.addItem("file_type", fileType);
        parameters.addItem("batch_no", "");

        String signCode = DataGramB2cUtil.signature(parameters.getItemString("inst_id")
                + parameters.getItemString("trans_date")
                + parameters.getItemString("file_type")
                + parameters.getItemString("batch_no"));

        parameters.addItem("sign_code", signCode);


        for (int i = 0; i < parameters.size(); i++) {
            String key = parameters.get(i).getKeyStringValue();
            String value = parameters.get(i).getValueStringValue();
            sbUrl.append(key).append("=").append(value).append("&");
        }

        sbUrl = StringUtils.removeLastLetters(sbUrl, "&");

        return sbUrl.toString();
    }


    /**
     * 通联万小宝
     * 账户信息验证
     * @param customerId
     * @param accountId
     * @param conn
     * @return
     * @throws Exception
     */
    public ReturnObject accountCheck(String customerId, String accountId, Connection conn) throws Exception {


        CustomerPersonalPO customerPersonalPO = customerPersonalDao.loadByCustomerPersonalId(customerId, conn);

        String allinpayCircleSignNum = customerPersonalPO.getAllinpayCircle_SignNum();

        CustomerAccountPO customerAccountPO = customerAccountDao.loadCustomerAccountPOByAccountId(accountId, conn);

        String bankNumber = AesEncrypt.decrypt(customerAccountPO.getNumber());
        String allinpayCircleBankCode = customerAccountDao.getBankCodeInKVParameter(accountId, "allinpayCircleBankCode", conn);


        CustomerCertificatePO customerCertificatePO = customerCertificateDao.loadByCustomerId(customerId, conn);

        String customerCertificateNumber = AesEncrypt.decrypt(customerCertificatePO.getNumber());


        StringUtils.checkIsEmpty(allinpayCircleSignNum, "金融圈客户号");
        StringUtils.checkIsEmpty(bankNumber, "银行卡号");
        StringUtils.checkIsEmpty(allinpayCircleBankCode, "银行代码");
        StringUtils.checkIsEmpty(customerCertificateNumber, "证件号码");



        TransactionPO transactionPO = new TransactionPO();

        transactionPO.setProcessing_code("1095");


        transactionPO.getRequest().addItem("req_trace_num", IdUtils.getNewLongIdString());
        transactionPO.getRequest().addItem("sign_num", allinpayCircleSignNum);
        transactionPO.getRequest().addItem("cer_type", "01");
        transactionPO.getRequest().addItem("cer_num", customerCertificateNumber);


        ReturnObject returnObject = allinpayCircleDao.sendTransaction(transactionPO, conn);

        XmlHelper helper = getResponseXmlHelper(returnObject);

        String signNum = helper.getValue("/transaction/response/sign_num");

        return returnObject;
    }


    public ReturnObject queryCashShare(String customerId, Connection conn) throws Exception {

        CustomerPersonalPO customerPersonalPO = customerPersonalDao.loadByCustomerPersonalId(customerId, conn);

        String allinpayCircleSignNum = customerPersonalPO.getAllinpayCircle_SignNum();

        StringUtils.checkIsEmpty(allinpayCircleSignNum, "金融圈客户号");




        TransactionPO transactionPO = new TransactionPO();

        transactionPO.setProcessing_code("3004");


        transactionPO.getRequest().addItem("req_trace_num", IdUtils.getNewLongIdString());
        transactionPO.getRequest().addItem("sign_num", allinpayCircleSignNum);
//        transactionPO.getRequest().addItem("bnk_id", allinpayCircleBankCode);
//        transactionPO.getRequest().addItem("acct_type", "1");
//        transactionPO.getRequest().addItem("acct_num", bankNumber);
//        transactionPO.getRequest().addItem("prod_import_flag", "1");
//        transactionPO.getRequest().addItem("supply_inst_code", "000000324");
//        transactionPO.getRequest().addItem("product_num", "KPL555");


        ReturnObject returnObject = allinpayCircleDao.sendTransaction(transactionPO, conn);

        KVObjects kvObjects = JSONDao.toKVObjects(returnObject.getReturnValue().toString());

        String responseXml = kvObjects.getItemString("responseXml");
        XmlHelper helper = new XmlHelper(responseXml);

        String all_total_assets = helper.getValue("/transaction/response/all_total_assets");
        String realtime_total_assets = helper.getValue("/transaction/response/realtime_total_assets");
        String frozen_realtime_total_assets = helper.getValue("/transaction/response/frozen_realtime_total_assets");
        String all_total_income = helper.getValue("/transaction/response/all_total_income");
        String yesterday_total_income = helper.getValue("/transaction/response/yesterday_total_income");
        String yesterday_total_income_date = helper.getValue("/transaction/response/yesterday_total_income_date");
        String qur_rst = helper.getValue("/transaction/response/qur_rst");


        List<KVObjects> listKVObjects = JSONDao.getListKVObjects(qur_rst);

        for (int i = 0; listKVObjects != null && i < listKVObjects.size(); i++) {
            KVObjects kosRawData = listKVObjects.get(i);

            kosRawData.removeByKey("bnkId");
            kosRawData.removeByKey("acctSubNo");
            kosRawData.removeByKey("productYesterdayIncome");
            kosRawData.removeByKey("dateOfProductYesterdayIncome");
            kosRawData.removeByKey("incomeUpdateFlag");
            kosRawData.removeByKey("productHistoryIncome");
            kosRawData.removeByKey("totalIncome");
            kosRawData.removeByKey("defDividendMethod");
            kosRawData.removeByKey("transferAmt");
            kosRawData.removeByKey("addtnl_data1");
            kosRawData.removeByKey("addtnl_data2");
            kosRawData.removeByKey("addtnl_data3");
        }

        KVObjects returnKVObjects = new KVObjects();
        returnKVObjects.addItem("all_total_assets", all_total_assets)
                .addItem("realtime_total_assets", realtime_total_assets)
                .addItem("frozen_realtime_total_assets", frozen_realtime_total_assets)
                .addItem("all_total_income", all_total_income)
                .addItem("yesterday_total_income", yesterday_total_income)
                .addItem("yesterday_total_income_date", yesterday_total_income_date)
                .addItem("qur_rst", JSONDao.convert2JSONArray(listKVObjects));
        returnObject.setReturnValue(returnKVObjects.toJSONObject());

        return returnObject;
    }


    /**
     * 通联万小宝
     * 换卡方法
     * @param accountId
     * @param conn
     * @return
     * @throws Exception
     */
    public ReturnObject changeBankNumber(String accountId, String bankCode_New, String bankNumber_New, String mobile_New, Connection conn) throws Exception {

        CustomerAccountPO customerAccountPO_Old = customerAccountDao.loadCustomerAccountPOByAccountId(accountId, conn);

        String customerId = customerAccountPO_Old.getCustomerId();


        CustomerPersonalPO customerPersonalPO = customerPersonalDao.loadByCustomerPersonalId(customerId, conn);

        String allinpayCircleSignNum = customerPersonalPO.getAllinpayCircle_SignNum();


        String allinpayCircleBankCode = customerAccountDao.getBankCodeInKVParameterWithBankCode(bankCode_New, "allinpayCircleBankCode", conn);

        CustomerCertificatePO customerCertificatePO = customerCertificateDao.loadByCustomerId(customerId, conn);

        String customerCertificateNumber = AesEncrypt.decrypt(customerCertificatePO.getNumber());



        String bankNumberOld = AesEncrypt.decrypt(customerAccountPO_Old.getNumber());
        String mobileOld = customerAccountPO_Old.getMobile();
        String allinpayCircleBankCodeOld = customerAccountDao.getBankCodeInKVParameter(accountId, "allinpayCircleBankCode", conn);


        StringUtils.checkIsEmpty(allinpayCircleSignNum, "金融圈客户号");
        StringUtils.checkIsEmpty(bankNumber_New, "银行卡号");
        StringUtils.checkIsEmpty(allinpayCircleBankCode, "银行代码");
        StringUtils.checkIsEmpty(customerCertificateNumber, "证件号码");
        StringUtils.checkIsEmpty(mobile_New, "金融圈手机号");
        StringUtils.checkIsEmpty(mobileOld, "原金融圈手机号");


        TransactionPO transactionPO = new TransactionPO();

        transactionPO.setProcessing_code("1088");


        transactionPO.getRequest().addItem("req_trace_num", IdUtils.getNewLongIdString());
        transactionPO.getRequest().addItem("sign_num", allinpayCircleSignNum);
        transactionPO.getRequest().addItem("sign_type", "3");
        transactionPO.getRequest().addItem("prod_flag", "0");
        transactionPO.getRequest().addItem("bnk_id", allinpayCircleBankCode);
        transactionPO.getRequest().addItem("acct_type", "1");
        transactionPO.getRequest().addItem("acct_num", bankNumber_New);
        transactionPO.getRequest().addItem("hld_name", customerPersonalPO.getName());
        transactionPO.getRequest().addItem("cer_type", "01");
        transactionPO.getRequest().addItem("cer_num", customerCertificateNumber);
        transactionPO.getRequest().addItem("tel_num", mobile_New);


        transactionPO.getRequest().addItem("org_acct_type", "1");
        transactionPO.getRequest().addItem("org_bnk_id", allinpayCircleBankCodeOld);
        transactionPO.getRequest().addItem("org_acct_num", bankNumberOld);
        transactionPO.getRequest().addItem("org_tel_num", mobileOld);


        transactionPO.setBizId(accountId);

        ReturnObject returnObject = allinpayCircleDao.sendTransaction(transactionPO, conn);


        if (returnObject.getCode() == 100) {
            customerAccountPO_Old.setBankCode(bankCode_New);
            customerAccountPO_Old.setNumber(bankNumber_New);
            customerAccountPO_Old.setMobile(mobile_New);

            customerAccountPO_Old.setAllinpayCircle_ChangeStatus("2");

            customerAccountDao.inertOrUpdate(customerAccountPO_Old, Config.getDefaultOperatorId(), conn);
        }
        else {
            String responseJSON = returnObject.getReturnValue().toString();

            KVObjects kvObjects = JSONDao.toKVObjects(responseJSON);

            String responseXml = kvObjects.getItemString("responseXml");

            XmlHelper helper = new XmlHelper(responseXml);

            String responseMessage = helper.getValue("/transaction/response/resp_msg");

            returnObject.setMessage(responseMessage);
        }

        return returnObject;
    }


    /**
     * 通联万小宝
     * 更改手机号
     * @param accountId
     * @param mobileNew
     * @param conn
     * @return
     * @throws Exception
     */
    public ReturnObject changeMobile(String accountId, String mobileNew, String operatorId, Connection conn) throws Exception {

        CustomerAccountPO customerAccountPO = customerAccountDao.loadCustomerAccountPOByAccountId(accountId, conn);

        CustomerPersonalPO customerPersonalPO = customerPersonalDao.loadByCustomerPersonalId(customerAccountPO.getCustomerId(), conn);

        StringUtils.checkIsEmpty(customerPersonalPO.getAllinpayCircle_SignNum(), "金融圈客户编号");

        String mobileOld = customerAccountPO.getMobile();

        String bankNumber = AesEncrypt.decrypt(customerAccountPO.getNumber());
        String allinpayCircleBankCode = customerAccountDao.getBankCodeInKVParameter(accountId, "allinpayCircleBankCode", conn);

        StringUtils.checkIsEmpty(bankNumber, "银行卡号");
        StringUtils.checkIsEmpty(mobileNew, "新手机号");
        StringUtils.checkIsEmpty(mobileOld, "旧手机号");


        TransactionPO transactionPO = new TransactionPO();

        transactionPO.setProcessing_code("1090");


        transactionPO.getRequest().addItem("req_trace_num", IdUtils.getNewLongIdString());
        transactionPO.getRequest().addItem("sign_num", customerPersonalPO.getAllinpayCircle_SignNum());
        transactionPO.getRequest().addItem("bnk_id", allinpayCircleBankCode);
        transactionPO.getRequest().addItem("acct_type", "1");
        transactionPO.getRequest().addItem("acct_num", bankNumber);
        transactionPO.getRequest().addItem("tel_num", mobileNew);
        transactionPO.getRequest().addItem("org_tel_num", mobileOld);


        ReturnObject returnObject = allinpayCircleDao.sendTransaction(transactionPO, conn);

        XmlHelper helper = getResponseXmlHelper(returnObject);

        String resp_code = helper.getValue("/transaction/response/resp_code");
        String resp_msg = helper.getValue("/transaction/response/resp_msg");

        if (!StringUtils.isEmpty(resp_code) && resp_code.equals("0000")) {
            customerAccountPO.setMobile(mobileNew);

            customerAccountDao.inertOrUpdate(customerAccountPO, operatorId, conn);
        }

        return returnObject;
    }

    /**
     * 通联万小宝
     * 单笔查询
     * @param conn
     * @return
     * @throws Exception
     */
    public ReturnObject queryWithOneOrder(String bizId, Connection conn) throws Exception {

        TransactionPO transactionPOCheck = transactionDao.loadByRequestTraceNum(bizId, APICommandDirection.Send, conn);

        if (transactionPOCheck == null) {
            MyException.newInstance("无法查到所需要的记录", "bizId=" + bizId).throwException();
        }

        if (StringUtils.isEmpty(transactionPOCheck.getBizId())) {
            MyException.newInstance("无法查到所需要记录的原编号", "bizId=" + bizId).throwException();
        }

        TransactionPO transactionPO = new TransactionPO();

        transactionPO.setProcessing_code("3001");


        transactionPO.getRequest().addItem("req_trace_num", IdUtils.getNewLongIdString());
        transactionPO.getRequest().addItem("org_req_trace_num", transactionPOCheck.getBizId());
        transactionPO.getRequest().addItem("org_trans_date", transactionPOCheck.getTrans_date());


        ReturnObject returnObject = allinpayCircleDao.sendTransaction(transactionPO, conn);

        XmlHelper helper = getResponseXmlHelper(returnObject);

        if (returnObject.getCode() == 100) {
            String qur_rst = helper.getValue("/transaction/response/qur_rst");
            String qur_rst_msg = helper.getValue("/transaction/response/qur_rst_msg");

            KVObjects kvObjects = new KVObjects();
            kvObjects.addItem("qur_rst", qur_rst).addItem("qur_rst_msg", qur_rst_msg);

            returnObject.setReturnValue(kvObjects.toJSONObject());
        }
        else {
            returnObject.setCode(5000);
        }





        return returnObject;
    }


    /**
     * 通联万小宝
     * 资产份额查询
     * @param customerId
     * @param conn
     * @return
     * @throws Exception
     */
    public ReturnObject assetShareQuery(String customerId, Connection conn) throws Exception {

        String url = "";

        CustomerPersonalPO customerPersonalPO = customerPersonalDao.loadByCustomerPersonalId(customerId, conn);

        if (customerPersonalPO == null || StringUtils.isEmpty(customerPersonalPO.getAllinpayCircle_SignNum())) {
            MyException.newInstance("确实客户编号", "确实通联万小宝客户编号，customerId=" + customerId).throwException();
        }


        TransactionPO transactionPO = new TransactionPO();

        transactionPO.setProcessing_code("3005");


        transactionPO.getRequest().addItem("req_trace_num", IdUtils.getNewLongIdString());
        transactionPO.getRequest().addItem("sign_num", customerPersonalPO.getAllinpayCircle_SignNum());


        ReturnObject returnObject = allinpayCircleDao.sendTransaction(transactionPO, conn);

        XmlHelper helper = getResponseXmlHelper(returnObject);

        String signNum = helper.getValue("/transaction/response/sign_num");

        return returnObject;
    }


    public ReturnObject depositByInstitution(String orderId, String operatorId, Connection conn) throws Exception {

        OrderPO orderPO = orderDao.loadByOrderId(orderId, conn);

        String accountId = orderPO.getAccountId();
        String productionId = orderPO.getProductionId();

        double money = orderPO.getMoney();

        ReturnObject returnObject = depositByInstitution(accountId, productionId, orderId, operatorId, conn);


        return returnObject;
    }

    public void dealDepositByInstitution(Connection conn) throws Exception {

        allinpayCircleDao.dealDepositByInstitution(conn);

    }

    /**
     * 充值-机构自付
     * @param accountId
     * @param productionId
     * @param orderId
     * @param operatorId
     * @param conn
     * @return
     * @throws Exception
     */
    public ReturnObject depositByInstitution(String accountId, String productionId, String orderId, String operatorId, Connection conn) throws Exception {

        OrderPO orderPO = orderDao.loadByOrderId(orderId, conn);

        /**
         * 检查订单状态
         */
        if (orderPO.getStatus() != OrderStatus.SaleAndWaiting) {
            MyException.newInstance("已打款订单才能进行充值确认", "orderId=" + orderId).throwException();
        }

        if (!StringUtils.isEmpty(orderPO.getAllinpayCircle_deposit_status()) && !orderPO.getAllinpayCircle_deposit_status().equals("0")) {
            MyException.newInstance("该订单已经进行充值确认，请不要重复充值！", "orderId=" + orderId).throwException();
        }

        CustomerAccountPO customerAccountPO = customerAccountDao.loadCustomerAccountPOByAccountId(accountId, conn);

        String customerId = customerAccountPO.getCustomerId();

        CustomerPersonalPO customerPersonalPO = customerPersonalDao.loadByCustomerPersonalId(customerId, conn);

        String bankNumber = AesEncrypt.decrypt(customerAccountPO.getNumber());
        String allinpayCircleBankCode = customerAccountDao.getBankCodeInKVParameter(accountId, "allinpayCircleBankCode", conn);

        CustomerCertificatePO customerCertificatePO = customerCertificateDao.loadByCustomerId(customerId, conn);

        String customerCertificateNumber = AesEncrypt.decrypt(customerCertificatePO.getNumber());

        ProductionPO productionPO = productionDao.getProductionById(productionId, conn);



        double money = orderPO.getMoney();

        TransactionPO transactionPO = new TransactionPO();

        transactionPO.setProcessing_code("2080");


        transactionPO.getRequest().addItem("req_trace_num", IdUtils.getNewLongIdString());
        transactionPO.getRequest().addItem("sign_num", customerPersonalPO.getAllinpayCircle_SignNum());
        transactionPO.getRequest().addItem("pay_mode", "4");
        transactionPO.getRequest().addItem("charge_flag", "1");
        transactionPO.getRequest().addItem("bnk_id", allinpayCircleBankCode);
        transactionPO.getRequest().addItem("acct_type", "1");
        transactionPO.getRequest().addItem("acct_num", bankNumber);
        transactionPO.getRequest().addItem("tel_num", customerAccountPO.getMobile());
        transactionPO.getRequest().addItem("cur_type", "156");
        transactionPO.getRequest().addItem("amt_tran", MoneyUtils.format2Fen(money));
        transactionPO.getRequest().addItem("product_code_cash_acct", productionPO.getAllinpayCircle_ProductCodeCashAcct());
        transactionPO.getRequest().addItem("resp_url", getCallbackUrl());


        ReturnObject returnObject = new ReturnObject();

        try {

            returnObject = allinpayCircleDao.sendTransaction(transactionPO, conn);

            KVObjects kvObjects = JSONDao.toKVObjects(returnObject.getReturnValue().toString());

            String responseCode = kvObjects.getItemString("responseCode");
            String responseMessage = kvObjects.getItemString("responseMessage");

            if (returnObject.getCode() == 100) {


                returnObject.setMessage(responseMessage);


                XmlHelper helper = getResponseXmlHelper(returnObject);

                String signNum = helper.getValue("/transaction/response/sign_num");

                customerPersonalPO.setAllinpayCircle_SignNum(signNum);

                customerPersonalPO = customerPersonalDao.insertOrUpdate(customerPersonalPO, operatorId, conn);


                String acctSubNo = helper.getValue("/transaction/response/acct_sub_no");


                customerAccountPO.setAllinpayCircle_AcctSubNo(acctSubNo);

                customerAccountPO = customerAccountDao.inertOrUpdate(customerAccountPO, operatorId, conn);


                orderPO.setAllinpayCircle_req_trace_num(transactionPO.getRequest().getItemString("req_trace_num"));

                orderPO.setAllinpayCircle_deposit_status("2");

                orderDao.insertOrUpdate(orderPO, operatorId, conn);

                orderDetailDao.saveOrderDetail(orderPO, orderPO.getMoney(), TimeUtils.getNow(), "通联金融圈充值【"+responseMessage+"】", operatorId, conn);

            }
            else {
                returnObject.setCode(5000);
                returnObject.setMessage(responseMessage);
            }



        }
        catch (Exception e) {
            returnObject.setCode(-1);
            returnObject.setMessage(e.getMessage());
        }



        return returnObject;
    }


    public ReturnObject withdrawalByBankNormal(String accountId, String productionId, double money, String operatorId, Connection conn) throws Exception {

        CustomerAccountPO customerAccountPO = customerAccountDao.loadCustomerAccountPOByAccountId(accountId, conn);

        String customerId = customerAccountPO.getCustomerId();

        CustomerPersonalPO customerPersonalPO = customerPersonalDao.loadByCustomerPersonalId(customerId, conn);


        String bankNumber = AesEncrypt.decrypt(customerAccountPO.getNumber());
        String allinpayCircleBankCode = customerAccountDao.getBankCodeInKVParameter(accountId, "allinpayCircleBankCode", conn);


        TransactionPO transactionPO = new TransactionPO();

        transactionPO.setProcessing_code("2280");


        transactionPO.getRequest().addItem("req_trace_num", IdUtils.getNewLongIdString());
        transactionPO.getRequest().addItem("sign_num", customerPersonalPO.getAllinpayCircle_SignNum());
        transactionPO.getRequest().addItem("pay_mode", "0");
        transactionPO.getRequest().addItem("charge_flag", "1");
        transactionPO.getRequest().addItem("bnk_id", allinpayCircleBankCode);
        transactionPO.getRequest().addItem("acct_type", "1");
        transactionPO.getRequest().addItem("acct_num", bankNumber);
        transactionPO.getRequest().addItem("cur_type", "156");
        transactionPO.getRequest().addItem("amt_tran", MoneyUtils.format2Fen(money));
        transactionPO.getRequest().addItem("product_code_cash_acct", "000709");
        transactionPO.getRequest().addItem("resp_url", getCallbackUrl());


        ReturnObject returnObject = allinpayCircleDao.sendTransaction(transactionPO, conn);


        XmlHelper helper = getResponseXmlHelper(returnObject);

        String signNum = helper.getValue("/transaction/response/sign_num");

        if (returnObject.getCode() == 100) {

            // todo: 普通取现



        }
        else {
            returnObject.setCode(5000);
        }


        return null;
    }


    /**
     * 实时取现-银行卡支付
     * @param customerId
     * @param accountId
     * @param productionId
     * @param money
     * @param operatorId
     * @param conn
     * @return
     * @throws Exception
     */
    public ReturnObject withdrawalByBankRealTime(String customerId, String accountId, String productionId, double money, String operatorId, Connection conn) throws Exception {

        String url = "";

        CustomerPersonalPO customerPersonalPO = customerPersonalDao.loadByCustomerPersonalId(customerId, conn);

        CustomerAccountPO customerAccountPO = customerAccountDao.loadCustomerAccountPOByAccountId(accountId, conn);

        String bankNumber = AesEncrypt.decrypt(customerAccountPO.getNumber());
        String allinpayCircleBankCode = customerAccountDao.getBankCodeInKVParameter(accountId, "allinpayCircleBankCode", conn);

        CustomerCertificatePO customerCertificatePO = customerCertificateDao.loadByCustomerId(customerId, conn);

        String customerCertificateNumber = AesEncrypt.decrypt(customerCertificatePO.getNumber());

        ProductionPO productionPO = productionDao.getProductionById(productionId, conn);

        TransactionPO transactionPO = new TransactionPO();

        transactionPO.setProcessing_code("2290");


        transactionPO.getRequest().addItem("req_trace_num", IdUtils.getNewLongIdString());
        transactionPO.getRequest().addItem("sign_num", customerPersonalPO.getAllinpayCircle_SignNum());
        transactionPO.getRequest().addItem("pay_mode", "1");
        transactionPO.getRequest().addItem("charge_flag", "1");
        transactionPO.getRequest().addItem("bnk_id", allinpayCircleBankCode);
        transactionPO.getRequest().addItem("acct_type", "1");
        transactionPO.getRequest().addItem("acct_num", bankNumber);
        transactionPO.getRequest().addItem("cur_type", "156");
        transactionPO.getRequest().addItem("hld_name", customerPersonalPO.getName());
        transactionPO.getRequest().addItem("amt_tran", MoneyUtils.format2Fen(money));
        transactionPO.getRequest().addItem("product_code_cash_acct", "000709");
        transactionPO.getRequest().addItem("resp_url", getCallbackUrl());


        ReturnObject returnObject = allinpayCircleDao.sendTransaction(transactionPO, conn);


        XmlHelper helper = getResponseXmlHelper(returnObject);

        String signNum = helper.getValue("/transaction/response/sign_num");

        if (returnObject.getCode() == 100) {

            // todo: 实时取现

        }
        else {
            returnObject.setCode(5000);
        }


        return null;
    }

    /**
     * 份额赎回
     * @param customerId
     * @param accountId
     * @param operatorId
     * @param conn
     * @throws Exception
     */
    public ReturnObject redeem(String customerId, String accountId, String productionId, double money, String operatorId, Connection conn) throws Exception {

        String url = "";

        CustomerPersonalPO customerPersonalPO = customerPersonalDao.loadByCustomerPersonalId(customerId, conn);

        CustomerAccountPO customerAccountPO = customerAccountDao.loadCustomerAccountPOByAccountId(accountId, conn);

        String bankNumber = AesEncrypt.decrypt(customerAccountPO.getNumber());
        String allinpayCircleBankCode = customerAccountDao.getBankCodeInKVParameter(accountId, "allinpayCircleBankCode", conn);

        CustomerCertificatePO customerCertificatePO = customerCertificateDao.loadByCustomerId(customerId, conn);

        String customerCertificateNumber = AesEncrypt.decrypt(customerCertificatePO.getNumber());

        ProductionPO productionPO = productionDao.getProductionById(productionId, conn);

        TransactionPO transactionPO = new TransactionPO();

        transactionPO.setProcessing_code("1087");


        transactionPO.getRequest().addItem("req_trace_num", IdUtils.getNewLongIdString());
        transactionPO.getRequest().addItem("pay_mode", "3");
        transactionPO.getRequest().addItem("bnk_id", allinpayCircleBankCode);
        transactionPO.getRequest().addItem("acct_type", "1");
        transactionPO.getRequest().addItem("acct_num", bankNumber);
        transactionPO.getRequest().addItem("cur_type", "156");
        transactionPO.getRequest().addItem("hld_name", customerPersonalPO.getName());
        transactionPO.getRequest().addItem("cer_type", "01");
        transactionPO.getRequest().addItem("amt_tran", MoneyUtils.format2Fen(money));
        transactionPO.getRequest().addItem("prod_import_flag", "1");
        transactionPO.getRequest().addItem("supply_inst_code", productionPO.getAllinpayCircle_SupplyInstCode());
        transactionPO.getRequest().addItem("product_num", productionPO.getAllinpayCircle_ProductNum());
        transactionPO.getRequest().addItem("product_code_cash_acct", productionPO.getAllinpayCircle_ProductCodeCashAcct());
        transactionPO.getRequest().addItem("resp_url", url);


        ReturnObject returnObject = allinpayCircleDao.sendTransaction(transactionPO, conn);


        XmlHelper helper = getResponseXmlHelper(returnObject);

        String signNum = helper.getValue("/transaction/response/sign_num");

        customerPersonalPO.setAllinpayCircle_SignNum(signNum);

        customerPersonalPO = customerPersonalDao.insertOrUpdate(customerPersonalPO, operatorId, conn);


        String acctSubNo = helper.getValue("/transaction/response/acct_sub_no");



        customerAccountPO.setAllinpayCircle_AcctSubNo(acctSubNo);

        customerAccountPO = customerAccountDao.inertOrUpdate(customerAccountPO, operatorId, conn);


        return returnObject;
    }


    /**
     * 单笔信任开户
     * @param accountId
     * @param operatorId
     * @param conn
     * @throws Exception
     */
    public ReturnObject openAccountPersonalByTrust(String accountId, String operatorId, Connection conn) throws Exception {

        CustomerAccountPO customerAccountPO = customerAccountDao.loadCustomerAccountPOByAccountId(accountId, conn);


        CustomerPersonalPO customerPersonalPO = customerPersonalDao.loadByCustomerPersonalId(customerAccountPO.getCustomerId(), conn);




        String allinpayCircleMobile = customerAccountPO.getMobile();

        String bankNumber = AesEncrypt.decrypt(customerAccountPO.getNumber());
        String allinpayCircleBankCode = customerAccountDao.getBankCodeInKVParameter(accountId, "allinpayCircleBankCode", conn);

        CustomerCertificatePO customerCertificatePO = customerCertificateDao.loadByCustomerId(customerAccountPO.getCustomerId(), conn);

        String customerCertificateNumber = AesEncrypt.decrypt(customerCertificatePO.getNumber());


        StringUtils.checkIsEmpty(bankNumber, "银行卡号");
        StringUtils.checkIsEmpty(allinpayCircleBankCode, "银行代码");
        StringUtils.checkIsEmpty(customerCertificateNumber, "证件号码");
        StringUtils.checkIsEmpty(allinpayCircleMobile, "金融圈手机号");


        TransactionPO transactionPO = new TransactionPO();

        transactionPO.setProcessing_code("1087");


        transactionPO.getRequest().addItem("req_trace_num", IdUtils.getNewLongIdString());
        transactionPO.getRequest().addItem("sign_type", "3");
        transactionPO.getRequest().addItem("prod_flag", "2");
        transactionPO.getRequest().addItem("bnk_id", allinpayCircleBankCode);
        transactionPO.getRequest().addItem("acct_type", "1");
        transactionPO.getRequest().addItem("acct_num", bankNumber);
        transactionPO.getRequest().addItem("hld_name", customerPersonalPO.getName());
        transactionPO.getRequest().addItem("cer_type", "01");
        transactionPO.getRequest().addItem("cer_num", customerCertificateNumber);
        transactionPO.getRequest().addItem("tel_num", allinpayCircleMobile);
        transactionPO.getRequest().addItem("supply_inst_code", "000000324");


        ReturnObject returnObject = allinpayCircleDao.sendTransaction(transactionPO, conn);


        /**
         * 处理返回值
         */
        XmlHelper helper = getResponseXmlHelper(returnObject);

        String signNum = helper.getValue("/transaction/response/sign_num");

        customerPersonalPO.setAllinpayCircle_SignNum(signNum);

        customerPersonalPO = customerPersonalDao.insertOrUpdate(customerPersonalPO, operatorId, conn);

        String acctSubNo = helper.getValue("/transaction/response/acct_sub_no");
        customerAccountPO.setAllinpayCircle_AcctSubNo(acctSubNo);

        customerAccountPO = customerAccountDao.inertOrUpdate(customerAccountPO, operatorId, conn);


        return returnObject;
    }

    public void dealPayByShare(Connection conn) throws Exception {
        allinpayCircleDao.dealPayByShare(conn);
    }

    /**
     * 通联万小宝
     * 单笔购买交易，份额支付
     * @param orderId
     * @param operatorId
     * @param conn
     * @return
     * @throws Exception
     */
    public ReturnObject payByShare(String orderId, String operatorId, Connection conn) throws Exception {

        OrderPO orderPO = orderDao.loadByOrderId(orderId, conn);

        CustomerPersonalPO customerPersonalPO = customerPersonalDao.loadByCustomerPersonalId(orderPO.getCustomerId(), conn);

        CustomerAccountPO customerAccountPO = customerAccountDao.loadCustomerAccountPOByAccountId(orderPO.getAccountId(), conn);

        String bankNumber = AesEncrypt.decrypt(customerAccountPO.getNumber());
        String allinpayCircleBankCode = customerAccountDao.getBankCodeInKVParameter(orderPO.getAccountId(), "allinpayCircleBankCode", conn);




        ProductionPO productionPO = productionDao.getProductionById(orderPO.getProductionId(), conn);

        TransactionPO transactionPO = new TransactionPO();

        transactionPO.setProcessing_code("2085");


        transactionPO.getRequest().addItem("req_trace_num", IdUtils.getNewLongIdString());
        transactionPO.getRequest().addItem("sign_num", customerPersonalPO.getAllinpayCircle_SignNum());
        transactionPO.getRequest().addItem("purchase_type", "0");
        transactionPO.getRequest().addItem("pay_mode", "3");
        transactionPO.getRequest().addItem("bnk_id", allinpayCircleBankCode);
        transactionPO.getRequest().addItem("acct_type", "1");
        transactionPO.getRequest().addItem("acct_num", bankNumber);
        transactionPO.getRequest().addItem("tel_num", customerAccountPO.getMobile());
        transactionPO.getRequest().addItem("cur_type", "156");
        transactionPO.getRequest().addItem("amt_tran", MoneyUtils.format2Fen(orderPO.getMoney()));
        transactionPO.getRequest().addItem("prod_import_flag", "0");
        transactionPO.getRequest().addItem("supply_inst_code", "000000324");
        transactionPO.getRequest().addItem("product_num", "KPL555");
        transactionPO.getRequest().addItem("product_code_cash_acct", "000709");
        transactionPO.getRequest().addItem("order_num", orderPO.getId());
        transactionPO.getRequest().addItem("resp_url", getCallbackUrl());


        ReturnObject returnObject = allinpayCircleDao.sendTransaction(transactionPO, conn);

        XmlHelper helper = getResponseXmlHelper(returnObject);

        String resp_code = helper.getValue("/transaction/response/resp_code");
        String resp_msg = helper.getValue("/transaction/response/resp_msg");
        String order_num = helper.getValue("/transaction/response/order_num");

        if (returnObject.getCode() == 100) {
            OrderPO orderDone = orderDao.loadByOrderId(order_num, conn);

            orderDone.setAllinpayCircle_payByShare_status("2");
            orderDone.setAllinpayCircle_payByShare_time(TimeUtils.getNow());

            orderDao.insertOrUpdate(orderDone, operatorId, conn);

            orderDetailDao.saveOrderDetail(orderDone, orderDone.getMoney(), TimeUtils.getNow(), "通联金融圈份额支付【"+resp_msg+"】", operatorId, conn);
        }
        else {
            returnObject.setCode(5000);
        }


        return returnObject;
    }




    public static void main(String[] args) throws Exception {

        TransactionPO transactionPO = new TransactionPO();

        transactionPO.setProcessing_code("1087");


        transactionPO.getRequest().addItem("req_trace_num", IdUtils.getNewLongIdString());
        transactionPO.getRequest().addItem("sub_merchant_id", "");
        transactionPO.getRequest().addItem("sign_type", "3");
        transactionPO.getRequest().addItem("prod_flag", "0");
        transactionPO.getRequest().addItem("bnk_id", "03040000");
        transactionPO.getRequest().addItem("acct_type", "1");
        transactionPO.getRequest().addItem("acct_num", "1234558712203145872");
        transactionPO.getRequest().addItem("cnaps_code", "");
        transactionPO.getRequest().addItem("hld_name", "李扬");
        transactionPO.getRequest().addItem("cer_type", "01");
        transactionPO.getRequest().addItem("cer_num", "530103198203091218");
        transactionPO.getRequest().addItem("tel_num", "13888939712");
        transactionPO.getRequest().addItem("supply_inst_code", "");
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


        AllinpayCircleService allinpayCircleService = Config.getBeanByName("allinpayCircleService", AllinpayCircleService.class);


        Connection conn = Config.getConnection();

        try {
            allinpayCircleService.sendTransaction(transactionPO, conn);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);

        }


    }


    public KVObjects getMobileCodeInfo(String bizId, String thirdPartyAIPName, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("C4DF1809");
        dbSQL.addParameter4All("bizId", bizId).addParameter4All("thirdPartyAIPName", thirdPartyAIPName).initSQL();

        List<APICommandPO> listAPICommandPO = MySQLDao.search(dbSQL, APICommandPO.class, conn);

        if (listAPICommandPO != null && listAPICommandPO.size() > 0) {
            APICommandPO apiCommandPO = listAPICommandPO.get(0);

            XmlHelper helper = new XmlHelper(apiCommandPO.getCommands());

            String org_processing_code = helper.getValue("/transaction/head/processing_code");
            String org_trans_date = helper.getValue("/transaction/head/trans_date");
            String org_req_trace_num = helper.getValue("/transaction/response/req_trace_num");


            KVObjects kvObjects = new KVObjects();
            kvObjects.addItem("org_processing_code", org_processing_code);
            kvObjects.addItem("org_trans_date", org_trans_date);
            kvObjects.addItem("org_req_trace_num", org_req_trace_num);
            kvObjects.addItem("bizId", bizId);

            return kvObjects;

        }

        return null;
    }



    /**
     * 通联万小宝
     * 验证码验证
     * @param bizId
     * @param mobileCode
     * @param conn
     * @return
     * @throws Exception
     */
    public ReturnObject checkMobileCode(String bizId, String mobileCode, Connection conn) throws Exception {

        TransactionPO transactionPOCheck = transactionDao.loadByRequestTraceNum(bizId, APICommandDirection.Receive, conn);

        if (transactionPOCheck == null) {
            MyException.newInstance("无法找到需要验证的交易", "bizId=" + bizId).throwException();
        }

        String processingCodeCheck = transactionPOCheck.getProcessing_code();
        String transDateCheck = transactionPOCheck.getTrans_date();
        String req_trace_num = transactionPOCheck.getRequest().getItemString("req_trace_num");

        TransactionPO transactionPO = new TransactionPO();

        transactionPO.setProcessing_code("3010");


        transactionPO.getRequest().addItem("req_trace_num", IdUtils.getNewLongIdString());
        transactionPO.getRequest().addItem("org_processing_code", processingCodeCheck);
        transactionPO.getRequest().addItem("org_req_trace_num", req_trace_num);
        transactionPO.getRequest().addItem("org_trans_date", transDateCheck);
        transactionPO.getRequest().addItem("verify_code", mobileCode);


        ReturnObject returnObject = allinpayCircleDao.sendTransaction(transactionPO, conn);

        return returnObject;
    }


    public ReturnObject receiveTransaction() throws Exception {


        ReturnObject returnObject = new ReturnObject();

        return returnObject;
    }



    public ReturnObject sendTransaction(String processingCode, KVObjects parameters, Connection conn) throws Exception {


        TransactionPO transactionPO = new TransactionPO();
        transactionPO.setProcessing_code(processingCode);

        for (int i = 0; i < parameters.size(); i++) {
            String key = parameters.get(i).getKeyStringValue();
            String value = parameters.get(i).getValueStringValue();

            transactionPO.getRequest().addItem(key, value);
        }

        ReturnObject returnObject = sendTransaction(transactionPO, conn);

        return returnObject;
    }

    public ReturnObject sendTransaction(TransactionPO transactionPO, Connection conn) throws Exception {

        return allinpayCircleDao.sendTransaction(transactionPO, conn);
    }

}
