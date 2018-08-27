package com.youngbook.service.allinpaycircle;

import com.alibaba.fastjson.JSONObject;
import com.allinpay.ets.client.StringUtil;
import com.emulator.paymentgateway.util.PaymentGatewayService;
import com.emulator.paymentgateway.util.SecurityUtil;
import com.mind.platform.system.base.CMData;
import com.mind.platform.system.base.DataRow;
import com.youngbook.common.*;
import com.youngbook.common.config.AesEncrypt;
import com.youngbook.common.config.Config;
import com.youngbook.common.config.XmlHelper;
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
import com.youngbook.dao.production.IProductionDao;
import com.youngbook.entity.po.allinpaycircle.TransactionPO;
import com.youngbook.entity.po.core.APICommandDirection;
import com.youngbook.entity.po.core.APICommandType;
import com.youngbook.entity.po.customer.CustomerAccountPO;
import com.youngbook.entity.po.customer.CustomerCertificatePO;
import com.youngbook.entity.po.customer.CustomerPersonalPO;
import com.youngbook.entity.po.production.OrderPO;
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

import java.security.Key;
import java.sql.Connection;
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

    private static final String callbackUrl = "http://118.126.103.58:8574/core/api/APICommand_receiveAllinpayCircle";


    /**
     * 解析返回值
     * @param returnObject
     * @return
     * @throws Exception
     */
    private XmlHelper getResponseXmlHelper(ReturnObject returnObject) throws Exception {

        XmlHelper helper = null;

        if (returnObject == null || returnObject.getCode() != 100) {
            MyException.newInstance("返回值解析异常", returnObject.getMessage()).throwException();
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

        String url = "http://118.126.103.58:8574/core/api/APICommand_receiveAllinpayCircle";

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


    /**
     * 通联万小宝
     * 换卡方法
     * @param customerId
     * @param accountId
     * @param accountIdOld
     * @param conn
     * @return
     * @throws Exception
     */
    public ReturnObject changeBankNumber(String customerId, String accountId, String accountIdOld, Connection conn) throws Exception {


        CustomerPersonalPO customerPersonalPO = customerPersonalDao.loadByCustomerPersonalId(customerId, conn);

        String allinpayCircleSignNum = customerPersonalPO.getAllinpayCircle_SignNum();

        CustomerAccountPO customerAccountPO = customerAccountDao.loadCustomerAccountPOByAccountId(accountId, conn);

        String mobileNew = customerAccountPO.getMobile();

        String bankNumberNew = AesEncrypt.decrypt(customerAccountPO.getNumber());
        String allinpayCircleBankCode = customerAccountDao.getBankCodeInKVParameter(accountId, "allinpayCircleBankCode", conn);

        CustomerCertificatePO customerCertificatePO = customerCertificateDao.loadByCustomerId(customerId, conn);

        String customerCertificateNumber = AesEncrypt.decrypt(customerCertificatePO.getNumber());



        CustomerAccountPO customerAccountPOOld = customerAccountDao.loadCustomerAccountPOByAccountId(accountIdOld, conn);

        String bankNumberOld = AesEncrypt.decrypt(customerAccountPOOld.getNumber());
        String mobileOld = customerAccountPOOld.getMobile();
        String allinpayCircleBankCodeOld = customerAccountDao.getBankCodeInKVParameter(accountId, "allinpayCircleBankCode", conn);


        StringUtils.checkIsEmpty(allinpayCircleSignNum, "金融圈客户号");
        StringUtils.checkIsEmpty(bankNumberNew, "银行卡号");
        StringUtils.checkIsEmpty(allinpayCircleBankCode, "银行代码");
        StringUtils.checkIsEmpty(customerCertificateNumber, "证件号码");
        StringUtils.checkIsEmpty(mobileNew, "金融圈手机号");
        StringUtils.checkIsEmpty(mobileOld, "原金融圈手机号");


        TransactionPO transactionPO = new TransactionPO();

        transactionPO.setProcessing_code("1088");


        transactionPO.getRequest().addItem("req_trace_num", IdUtils.getNewLongIdString());
        transactionPO.getRequest().addItem("sign_num", allinpayCircleSignNum);
        transactionPO.getRequest().addItem("sign_type", "3");
        transactionPO.getRequest().addItem("prod_flag", "0");
        transactionPO.getRequest().addItem("bnk_id", allinpayCircleBankCode);
        transactionPO.getRequest().addItem("acct_type", "1");
        transactionPO.getRequest().addItem("acct_num", bankNumberNew);
        transactionPO.getRequest().addItem("hld_name", customerPersonalPO.getName());
        transactionPO.getRequest().addItem("cer_type", "01");
        transactionPO.getRequest().addItem("cer_num", customerCertificateNumber);
        transactionPO.getRequest().addItem("tel_num", mobileNew);


        transactionPO.getRequest().addItem("org_acct_type", "1");
        transactionPO.getRequest().addItem("org_bnk_id", allinpayCircleBankCodeOld);
        transactionPO.getRequest().addItem("org_acct_num", bankNumberOld);
        transactionPO.getRequest().addItem("org_tel_num", mobileOld);

        ReturnObject returnObject = allinpayCircleDao.sendTransaction(transactionPO, conn);

        return returnObject;
    }


    /**
     * 通联万小宝
     * 更改手机号
     * @param customerId
     * @param accountId
     * @param mobileNew
     * @param conn
     * @return
     * @throws Exception
     */
    public ReturnObject changeMobile(String customerId, String accountId, String mobileNew, Connection conn) throws Exception {

        CustomerPersonalPO customerPersonalPO = customerPersonalDao.loadByCustomerPersonalId(customerId, conn);

        StringUtils.checkIsEmpty(customerPersonalPO.getAllinpayCircle_SignNum(), "金融圈客户编号");

        CustomerAccountPO customerAccountPO = customerAccountDao.loadCustomerAccountPOByAccountId(accountId, conn);

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

        String signNum = helper.getValue("/transaction/response/sign_num");



        return returnObject;
    }

    /**
     * 通联万小宝
     * 更改手机号
     * @param customerId
     * @param accountId
     * @param mobileNew
     * @param conn
     * @return
     * @throws Exception
     */
    public ReturnObject queryWithOneOrder(String customerId, String accountId, String mobileNew, Connection conn) throws Exception {

        CustomerPersonalPO customerPersonalPO = customerPersonalDao.loadByCustomerPersonalId(customerId, conn);

        StringUtils.checkIsEmpty(customerPersonalPO.getAllinpayCircle_SignNum(), "金融圈客户编号");

        CustomerAccountPO customerAccountPO = customerAccountDao.loadCustomerAccountPOByAccountId(accountId, conn);

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

        String signNum = helper.getValue("/transaction/response/sign_num");



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


    /**
     * 实时取现-机构自付
     * @param customerId
     * @param accountId
     * @param productionId
     * @param money
     * @param operatorId
     * @param conn
     * @return
     * @throws Exception
     */
    public ReturnObject withdrawalByInstitution(String customerId, String accountId, String productionId, double money, String operatorId, Connection conn) throws Exception {

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
        transactionPO.getRequest().addItem("pay_mode", "4");
        transactionPO.getRequest().addItem("charge_flag", "1");
        transactionPO.getRequest().addItem("bnk_id", allinpayCircleBankCode);
        transactionPO.getRequest().addItem("acct_type", "1");
        transactionPO.getRequest().addItem("acct_num", bankNumber);
        transactionPO.getRequest().addItem("cur_type", "156");
        transactionPO.getRequest().addItem("hld_name", customerPersonalPO.getName());
        transactionPO.getRequest().addItem("cer_type", "01");
        transactionPO.getRequest().addItem("amt_tran", MoneyUtils.format2Fen(money));
        transactionPO.getRequest().addItem("advance_flag", "1");
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
     * @param customerId
     * @param accountId
     * @param operatorId
     * @param conn
     * @throws Exception
     */
    public ReturnObject openAccountPersonalByTrust(String customerId, String accountId, String operatorId, Connection conn) throws Exception {


        CustomerPersonalPO customerPersonalPO = customerPersonalDao.loadByCustomerPersonalId(customerId, conn);


        CustomerAccountPO customerAccountPO = customerAccountDao.loadCustomerAccountPOByAccountId(accountId, conn);

        String allinpayCircleMobile = customerAccountPO.getMobile();

        String bankNumber = AesEncrypt.decrypt(customerAccountPO.getNumber());
        String allinpayCircleBankCode = customerAccountDao.getBankCodeInKVParameter(accountId, "allinpayCircleBankCode", conn);

        CustomerCertificatePO customerCertificatePO = customerCertificateDao.loadByCustomerId(customerId, conn);

        String customerCertificateNumber = AesEncrypt.decrypt(customerCertificatePO.getNumber());


        StringUtils.checkIsEmpty(bankNumber, "银行卡号");
        StringUtils.checkIsEmpty(allinpayCircleBankCode, "银行代码");
        StringUtils.checkIsEmpty(customerCertificateNumber, "证件号码");
        StringUtils.checkIsEmpty(allinpayCircleMobile, "金融圈手机号");


        TransactionPO transactionPO = new TransactionPO();

        transactionPO.setProcessing_code("1087");


        transactionPO.getRequest().addItem("req_trace_num", IdUtils.getNewLongIdString());
        transactionPO.getRequest().addItem("sub_merchant_id", "");
        transactionPO.getRequest().addItem("sign_type", "3");
        transactionPO.getRequest().addItem("prod_flag", "0");
        transactionPO.getRequest().addItem("bnk_id", allinpayCircleBankCode);
        transactionPO.getRequest().addItem("acct_type", "1");
        transactionPO.getRequest().addItem("acct_num", bankNumber);
        transactionPO.getRequest().addItem("hld_name", customerPersonalPO.getName());
        transactionPO.getRequest().addItem("cer_type", "01");
        transactionPO.getRequest().addItem("cer_num", customerCertificateNumber);
        transactionPO.getRequest().addItem("tel_num", allinpayCircleMobile);
        transactionPO.getRequest().addItem("supply_inst_code", "");
        transactionPO.getRequest().addItem("is_send_msg", "");
        transactionPO.getRequest().addItem("ms_signature", "");
        transactionPO.getRequest().addItem("reqs_url", "");
        transactionPO.getRequest().addItem("resp_url", "");
        transactionPO.getRequest().addItem("ip_addr", "");
        transactionPO.getRequest().addItem("addtnl_data1", "");
        transactionPO.getRequest().addItem("coop_id", "");
        transactionPO.getRequest().addItem("sys_id", "");
        transactionPO.getRequest().addItem("account_manager_tel", "");


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


    /**
     * 通联万小宝
     * 单笔购买交易，份额支付
     * @param customerId
     * @param accountId
     * @param orderId
     * @param operatorId
     * @param conn
     * @return
     * @throws Exception
     */
    public ReturnObject payByShare(String customerId, String accountId, String orderId, String operatorId, Connection conn) throws Exception {

        String url = "";

        CustomerPersonalPO customerPersonalPO = customerPersonalDao.loadByCustomerPersonalId(customerId, conn);

        CustomerAccountPO customerAccountPO = customerAccountDao.loadCustomerAccountPOByAccountId(accountId, conn);

        String bankNumber = AesEncrypt.decrypt(customerAccountPO.getNumber());
        String allinpayCircleBankCode = customerAccountDao.getBankCodeInKVParameter(accountId, "allinpayCircleBankCode", conn);


        OrderPO orderPO = orderDao.loadByOrderId(orderId, conn);

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
        transactionPO.getRequest().addItem("cer_type", "01");
        transactionPO.getRequest().addItem("amt_tran", MoneyUtils.format2Fen(orderPO.getMoney()));
        transactionPO.getRequest().addItem("prod_import_flag", "0");
        transactionPO.getRequest().addItem("supply_inst_code", productionPO.getAllinpayCircle_SupplyInstCode());
        transactionPO.getRequest().addItem("product_num", productionPO.getAllinpayCircle_ProductNum());
        transactionPO.getRequest().addItem("product_code_cash_acct", productionPO.getAllinpayCircle_ProductCodeCashAcct());
        transactionPO.getRequest().addItem("order_num", orderPO.getId());
        transactionPO.getRequest().addItem("resp_url", url);


        ReturnObject returnObject = allinpayCircleDao.sendTransaction(transactionPO, conn);

        XmlHelper helper = getResponseXmlHelper(returnObject);

        String signNum = helper.getValue("/transaction/response/sign_num");

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

        TransactionPO transactionPO = new TransactionPO();

        transactionPO.setProcessing_code("3010");


        transactionPO.getRequest().addItem("req_trace_num", IdUtils.getNewLongIdString());
        transactionPO.getRequest().addItem("org_processing_code", processingCodeCheck);
        transactionPO.getRequest().addItem("org_req_trace_num", bizId);
        transactionPO.getRequest().addItem("org_trans_date", transDateCheck);
        transactionPO.getRequest().addItem("verify_code", mobileCode);


        ReturnObject returnObject = allinpayCircleDao.sendTransaction(transactionPO, conn);

        return returnObject;
    }


    /**
     * 快捷换卡
     * @param parameters
     * @param conn
     * @throws Exception
     */
    public void changeBankCard(KVObjects parameters, Connection conn) throws Exception {

        if (parameters.isContainsAnyEmptyValue("sign_num","bnk_id","hld_name", "cer_num", "tel_num", "org_bnk_id", "org_acct_type", "org_acct_num")) {
            MyException.newInstance("参数不完整，包含空值", parameters.toJSONObject().toString()).throwException();
        }

        String bankName = Config.getKVString(parameters.getItemString("bnk_id"), "AllinpayCircleBankCode", conn);

        if (StringUtils.isEmpty(bankName)) {
            MyException.newInstance("无法获得银行编号", "bank_id" + parameters.getItemString("bnk_id")).throwException();
        }

        TransactionPO transactionPO = new TransactionPO();

        transactionPO.setProcessing_code("1088");


        transactionPO.getRequest().addItem("req_trace_num", IdUtils.getNewLongIdString());
        transactionPO.getRequest().addItem("sign_num", parameters.getItemString("sign_num"));
        transactionPO.getRequest().addItem("sign_type", "2");
        transactionPO.getRequest().addItem("bnk_id", parameters.getItemString("bnk_id"));
        transactionPO.getRequest().addItem("acct_type", "1");
        transactionPO.getRequest().addItem("acct_num", parameters.getItemString("acct_num"));
        transactionPO.getRequest().addItem("hld_name", parameters.getItemString("hld_name"));
        transactionPO.getRequest().addItem("cer_type", "01");
        transactionPO.getRequest().addItem("cer_num", parameters.getItemString("cer_num"));
        transactionPO.getRequest().addItem("tel_num", parameters.getItemString("tel_num"));
        transactionPO.getRequest().addItem("is_send_msg", "0");
        transactionPO.getRequest().addItem("mhnt_name", "德合基金");
        transactionPO.getRequest().addItem("ms_signature", "德合基金");
        transactionPO.getRequest().addItem("org_bnk_id", parameters.getItemString("org_bnk_id"));
        transactionPO.getRequest().addItem("org_acct_type", parameters.getItemString("org_acct_type"));
        transactionPO.getRequest().addItem("org_acct_num", parameters.getItemString("org_acct_num"));



        sendTransaction(transactionPO, conn);
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
