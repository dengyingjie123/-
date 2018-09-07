package com.youngbook.service.pay;

import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.config.XmlHelper;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.MoneyUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.system.ILogDao;
import com.youngbook.dao.system.ISystemFinanceSimpleRecordDao;
import com.youngbook.entity.po.core.TransferPO;
import com.youngbook.service.BaseService;
import com.youngbook.service.core.IMoneyTransferService;
import org.dom4j.DocumentHelper;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.*;

@Component("fuiouDirectService")
public class FuiouDirectService extends BaseService implements IMoneyTransferService {

    @Autowired
    ILogDao logDao;

    /**
     * 直联支付类型：单笔代付
     */
    public final static String REQUEST_TYPE_PAYFORREQ = "payforreq";    // 请求
    public final static String RESPONSE_TYPE_PAYFORRSP = "payforrsp";   // 响应

    /**
     * 直联支付类型：单笔代收
     */
    public final static String REQUEST_TYPE_SINCOMEFORREQ = "sincomeforreq";
    public final static String RESPONSE_TYPE_INCOMEFORRSP = "incomeforrsp";

    /**
     * 直联支付类型：交易查询
     */
    public final static String REQUEST_TYPE_QRYTRANSREQ = "qrytransreq";
    public final static String RESPONSE_TYPE_QRYTRANSRSP = "qrytransrsp";


    @Autowired
    ISystemFinanceSimpleRecordDao financeSimpleRecordDao;


    @Override
    public ReturnObject doTransfer(List<TransferPO> transfers, Connection conn) throws Exception {



        if (transfers == null || transfers.size() != 1) {
            MyException.newInstance("富友代付接口只支持单笔转账交易，请检查").throwException();
        }


        String now = TimeUtils.getNow();


        TransferPO transfer = transfers.get(0);

        String bizNo = TimeUtils.getNow(TimeUtils.Format_YYYYMMDDHHMMSS);

//        String bankCode = "0308";
        String bankCode = StringUtils.buildNumberString(transfer.getTargetBank(), 4) ;



        String customerName = transfer.getTargetAccountName();

        String bankNumber = transfer.getTargetAccountNo();

        String mobile = "";

        int money = MoneyUtils.getMoney2Fen(transfer.getMoney());

        boolean isDone = payment(bizNo, bankCode, transfer.getTargetCityName(), transfer.getTargetBankBranchName(), bankNumber, customerName, mobile, money, conn);

        ReturnObject returnObject = new ReturnObject();

        if (isDone) {
            returnObject.setCode(ReturnObject.CODE_SUCCESS);
            returnObject.setMessage("富友转账请求已接受");


            /**
             * 保存财务记录
             */
//            financeSimpleRecordDao.newFinanceRecord(SystemFinanceSimpleRecordAccountName.PaymentAccount, 0, money, now,  bizNo, "", conn);
//            financeSimpleRecordDao.newFinanceRecord(SystemFinanceSimpleRecordAccountName.FeeAccount, 0, 200, now,  bizNo, "", conn);

        }
        else {
            returnObject.setCode(ReturnObject.CODE_EXCEPTION);
            returnObject.setMessage("富友转账请求失败，请与系统管理员联系");
        }

        return returnObject;
    }


    /**
     * 发起代付
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年7月1日
     *
     * @param bizNo         流水号
     * @param bankCode      银行编码
     * @param cityCode      城市编码
     * @param branchName    分支名称（中国银行、建设银行、广东发展银行 必填）
     * @param customerName  客户姓名
     * @param mobile        手机号码
     * @param money         金额（整型，单位：分）
     *
     * @return Boolean true：成功，false：失败
     * @throws Exception
     */
    public Boolean payment(String bizNo, String bankCode, String cityCode, String branchName, String bankNumber, String customerName, String mobile, Integer money, Connection conn) throws Exception {

        // 请求类型
        String reqtype = FuiouDirectService.REQUEST_TYPE_PAYFORREQ;
        // 响应类型
        String rsptype = FuiouDirectService.RESPONSE_TYPE_PAYFORRSP;

        // 检查参数
        Integer paraNotCurrectCode = ReturnObjectCode.PUBLIC_PARAMETER_NOT_CORRECT;
        if(money == null || money <= 0) {
            MyException.newInstance(paraNotCurrectCode, "只允许支付大于零的金额").throwException();
        }
        if(StringUtils.isEmpty(bizNo)) {
            MyException.newInstance(paraNotCurrectCode, "缺少业务编号").throwException();
        }
        if(StringUtils.isEmpty(bankCode)) {
            MyException.newInstance(paraNotCurrectCode, "缺少银行编码").throwException();
        }
        if(StringUtils.isEmpty(cityCode)) {
            MyException.newInstance(paraNotCurrectCode, "缺少城市编号").throwException();
        }
        if(StringUtils.isEmpty(branchName)) {
            // 如果银行为中国银行、建设银行、广东发展银行，支行名称必填
            if("0104".equals(bankCode) || "0105".equals(bankCode) || "0306".equals(bankCode)) {
                MyException.newInstance(paraNotCurrectCode, "如果您银行卡的开户行为“中国银行”或“中国建设银行”或“广东发展银行”则必须填写支行名称！").throwException();
            }
        }
        if(StringUtils.isEmpty(bankNumber)) {
            MyException.newInstance(paraNotCurrectCode, "缺少银行卡号").throwException();
        }
        if(StringUtils.isEmpty(customerName)) {
            MyException.newInstance(paraNotCurrectCode, "缺少收款人名称").throwException();
        }

        // 生成 XML
        String xml = this.buildPaymentXMLWithDom4J(bizNo, bankCode, cityCode, branchName, bankNumber, customerName, mobile, money, "", "");

        logDao.save("富友支付", "富友支付返回值", xml);


        String x = StringUtils.md5(xml);

        System.out.println(x);

        // 发送请求
         String response = this.sendRequest(reqtype, xml);
//        String response = xml;

        logDao.save("富友支付", "富友支付-代付返回值",response);

        // 解析响应的 XML
        Map<String, String> result = FuiouDirectService.parseXmlCallback(rsptype, response);
        String ret = result.get("ret");
        String memo = result.get("memo");

        // 如果不是 6 个 0，说明有异常，抛出
        if(!"000000".equals(ret)) {
            MyException.newInstance("富友支付支付失败", "该代付操作存在异常，异常信息为“" + memo + "”，业务编号为：“" + bizNo + "”请联系系统管理员！").throwException();
            return false;
        }
        else {
            System.out.println("有 1 笔富友直连代付交易完成，业务编号：" + bizNo);
            logDao.save("富友支付","富友支付支付成功", "bizNo="+bizNo);
        }

        return true;

    }

    /**
     * 交易查询
     * @param bizNo             流水号
     * @param startDate         开始时间
     * @param endDate           结束时间
     * @param tradingStatus     富友端的交易状态
     * @return
     * @throws Exception
     */
    public String query(String bizNo, String startDate, String endDate, String tradingStatus, Connection conn) throws Exception {

        Date date = new Date();

        // 请求类型
        String reqtype = FuiouDirectService.REQUEST_TYPE_PAYFORREQ;

        // 校正参数
        if(StringUtils.isEmpty(bizNo)) {
            bizNo = "";
        }
        if(StringUtils.isEmpty(tradingStatus)) {
            tradingStatus = "";
        }
        if(StringUtils.isEmpty(startDate)) {
            startDate = TimeUtils.getDatePaymentString(date);
        }
        if(StringUtils.isEmpty(endDate)) {
            String now = TimeUtils.getNow();
            String tomorrow = TimeUtils.getTime(now, 1, "DATE");    // 获取下一天的日期，在 SQL 中进行小于查询才能范围有效
            Date finallyDate = TimeUtils.getDateCommon(tomorrow);    // 转换成 Date
            endDate = TimeUtils.getDatePaymentString(date);
        } else {
            endDate = TimeUtils.getTime(endDate, 1, "DATE");        // 获取下一天的日期，在 SQL 中进行小于查询才能范围有效
        }

        // 构建查询 XML
        String xml = this.buildQueryXML(bizNo, startDate, endDate, tradingStatus, conn);

        // 发送请求
        String response = this.sendRequest(reqtype, xml);

        // 解析响应的 XML
        // Map<String, String> result = FuiouDirectService

        return null;

    }

    /**
     * 构建代付的 XML
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年7月1日
     *
     * @param bizNo         流水号
     * @param bankCode      银行编码
     * @param cityCode      城市编码
     * @param branchName    分支名称（中国银行、建设银行、广东发展银行 必填）
     * @param bankNumber      银行卡号
     * @param customerName  客户姓名
     * @param mobileNum     手机号码
     * @param money         金额（整型，单位：分）
     * @param sequence      企业流水号
     * @param content       备注
     *
     * @return  String
     * @throws Exception
     */
    private String buildPaymentXML(String bizNo, String bankCode, String cityCode, String branchName, String bankNumber, String customerName, String mobileNum, Integer money, String sequence, String content) throws Exception {

        Date date = new Date();

        // 创建根元素，
        Element payforreq = new Element("payforreq");

        // 创建子元素
        Element ver = new Element("ver").setText("1.00");
        Element merdt = new Element("merdt").setText(TimeUtils.getDatePaymentString(date));
        Element orderno = new Element("orderno").setText(bizNo);
        Element bankno = new Element("bankno").setText(bankCode);
        Element cityno = new Element("cityno").setText(cityCode);
        Element branchnm = new Element("branchnm").setText(branchName);
        Element accntno = new Element("accntno").setText(bankNumber);
        Element accntnm = new Element("accntnm").setText(customerName);
        Element amt = new Element("amt").setText(money.toString());
        Element entseq = new Element("entseq").setText(sequence);
        Element memo = new Element("memo").setText(content);
        Element mobile = new Element("mobile").setText(mobileNum);

        // 子元素加到根元素
        payforreq.addContent(ver);
        payforreq.addContent(merdt);
        payforreq.addContent(orderno);
        payforreq.addContent(bankno);
        payforreq.addContent(cityno);
        payforreq.addContent(branchnm);
        payforreq.addContent(accntno);
        payforreq.addContent(accntnm);
        payforreq.addContent(amt);
        payforreq.addContent(entseq);
        payforreq.addContent(memo);
        payforreq.addContent(mobile);

        // 将根节点添加到文档中；
        Document doc = new Document(payforreq);

        // 缩进
        // Format format = Format.getPrettyFormat();
        // XMLOutputter xmlOut = new XMLOutputter(format);

        XMLOutputter xmlOut = new XMLOutputter();
        String xml = xmlOut.outputString(doc);

        System.out.println(StringUtils.md5(xml));

        return xml;

    }


    public String buildPaymentXMLWithDom4J(String bizNo, String bankCode, String cityCode, String branchName, String bankNumber, String customerName, String mobileNum, Integer money, String sequence, String content) throws Exception {

        Date date = new Date();

        org.dom4j.Document document = DocumentHelper.createDocument();
        org.dom4j.Element root = document.addElement( "payforreq" );

        root.addElement("ver").setText("2.00");
        root.addElement("merdt").setText(TimeUtils.getDatePaymentString(date));
        root.addElement("orderno").setText(bizNo);
        root.addElement("bankno").setText(bankCode);
        root.addElement("cityno").setText(cityCode);
        root.addElement("branchnm").setText(branchName);
        root.addElement("accntno").setText(bankNumber);
        root.addElement("accntnm").setText(customerName);
        root.addElement("amt").setText(money.toString());
        root.addElement("entseq").setText(sequence);
        root.addElement("memo").setText(content);
        root.addElement("mobile").setText(mobileNum);
        root.addElement("txncd").setText("");
        root.addElement("projectid").setText("");

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + root.asXML();

        System.out.println(xml);

        return xml;

    }

    /**
     * 构建交易查询 XML
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年7月5日
     *
     * @param bizNo             流水号
     * @param startDate         开始时间
     * @param endDate           结束时间
     * @param tradingStatus     富友端的交易状态
     * @return
     * @throws Exception
     */
    public String buildQueryXML(String bizNo, String startDate, String endDate, String tradingStatus, Connection conn) throws Exception {

        // 创建根元素，
        Element qrytransreq = new Element("qrytransreq");

        // 创建子元素
        Element ver = new Element("ver").setText("1.00");
        Element busicd = new Element("busicd").setText("AC01");
        Element orderno = new Element("orderno").setText(bizNo);
        Element startdt = new Element("startdt").setText(startDate);
        Element enddt = new Element("enddt").setText(endDate);
        Element transst = new Element(tradingStatus);

        // 子元素加到根元素
        qrytransreq.addContent(ver);
        qrytransreq.addContent(busicd);
        qrytransreq.addContent(orderno);
        qrytransreq.addContent(startdt);
        qrytransreq.addContent(enddt);
        qrytransreq.addContent(transst);

        // 将根节点添加到文档中；
        Document doc = new Document(qrytransreq);

        // 缩进
        // Format format = Format.getPrettyFormat();
        // XMLOutputter xmlOut = new XMLOutputter(format);

        XMLOutputter xmlOut = new XMLOutputter();
        String xml = xmlOut.outputString(doc);

        logDao.save("富友支付","富友直联交易查询 XML：", xml, conn);

        return xml;

    }

    /**
     * 发送企业直连请求
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年7月5日
     *
     * @param reqtype       // 请求类型
     * @param xml           // 请求 XML
     * @return
     * @throws Exception
     */
    public String sendRequest(String reqtype, String xml) throws Exception {

        // 获取请求配置
        String url = Config.getSystemConfig("fuiou.direct.url");  // 请求地址
        String merchantId = Config.getSystemConfig("fuiou.merchant.id");  // 商户 ID
        String password = Config.getSystemConfig("fuiou.direct.key");     // 商户密钥
        String sign = StringUtils.md5V2(merchantId + "|" + password + "|" + reqtype + "|" + xml);

        // 组装参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("merid", merchantId);
        params.put("reqtype", reqtype);
        params.put("xml", xml);
        params.put("mac", sign);

        // 发送请求
        String response = HttpUtils.postRequest(url, params);

        return response;

    }

    /**
     * 解析富友直联的代付、代收 XML 回调数据
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月17日
     *
     * @param responseType
     * @param response
     * @return
     * @throws Exception
     */
    public static Map<String, String> parseXmlCallback(String responseType, String response) throws Exception {

        // 解析并获取富友接口回调的 XML 参数
        XmlHelper helper = new XmlHelper(response);
        String ret = helper.getValue("//" + responseType + "/ret");      // 返回码
        String memo = helper.getValue("//" + responseType + "/memo");    // 返回描述

        // 构造到 Map
        Map<String, String> map = new HashMap<String, String>();
        map.put("ret", ret);
        map.put("memo", memo);

        // 返回数据
        return map;

    }

    /**
     * 解析富友直联交易查询回调数据
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年7月5日
     *
     * @param response
     * @return
     * @throws Exception
     */
    public Map<String, String> parseQueryXmlCallback(String response) throws Exception {

        // 解析并获取富友接口回调的 XML 参数
        XmlHelper helper = new XmlHelper(response);
        String ret = helper.getValue("//qrytransrsp/ret");      // 返回码
        String memo = helper.getValue("//qrytransrsp/memo");    // 返回描述

        helper.parseFuiouQueryCallbackXML("//qrytransrsp/trans");

        return null;

    }

    public static void main(String[] args) throws Exception {
        Connection conn = Config.getConnection();

        try {
            conn.setAutoCommit(false);

//            FuiouDirectService directService = Config.getBeanByName("fuiouDirectService", FuiouDirectService.class);

//
//            TransferPO transferPO = new TransferPO();
//            transferPO.setTargetAccountName("李扬");
//            transferPO.setTargetBank("0308");
//            transferPO.setTargetAccountNo("6225888712203953");
//            transferPO.setTargetCityName("7310");
//            transferPO.setMoney(2.00);
//
//            List<TransferPO> transferPOs = new ArrayList<>();
//            transferPOs.add(transferPO);
//
//            directService.doTransfer(transferPOs, conn);


            conn.commit();
        }
        catch (Exception e) {
            conn.rollback();
            e.printStackTrace();
        }
        finally {
            Database.close(conn);
        }


        // directService.payment("9876543216", "0308", "5810", "", "6225887867377315", "邓超", "18007550150", 1, conn);

//        String response = "";
//        response += "<?xml version='1.0' encoding='utf-8' standalone='yes'?>";
//        response += "<qrytransrsp>";
//        response += "	<ret>000000</ret>";
//        response += "	<memo>成功</memo>";
//        response += "	<trans>";
//        response += "		<merdt>20110417</merdt>";
//        response += "	<orderno>20110417000001</orderno>";
//        response += "	<accntno>1111111111111111111</accntno>";
//        response += "	<accntnm>张三</accntnm>";
//        response += "	<amt>100000</amt>";
//        response += "	<state>1</state>";
//        response += "	</trans>";
//        response += "	<trans>";
//        response += "		<merdt>20110418</merdt>";
//        response += "	<orderno>20110417000002</orderno>";
//        response += "	<accntno>1111111111111111112</accntno>";
//        response += "	<accntnm>李四</accntnm>";
//        response += "	<amt>100001</amt>";
//        response += "	<state>1</state>";
//        response += "	</trans>";
//        response += "</qrytransrsp>";
//
//        directService.parseQueryXmlCallback(response);

    }


}
