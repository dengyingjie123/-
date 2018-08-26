package com.youngbook.service.allinpay;

import com.aipg.merchantorder.MerchantOrderQueryBatchResult;
import com.aipg.merchantorder.MerchantOrderQueryEntity;
import com.allinpay.ets.client.SecurityUtil;
import com.allinpay.ets.client.StringUtil;
import com.allinpay.ets.client.util.Base64;
import com.youngbook.common.MyException;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.allinpay.AllinPayOrderPO;
import com.youngbook.entity.po.allinpay.AllinPayOrderTradingStatus;
import com.youngbook.service.BaseService;
import com.youngbook.service.system.LogService;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.StringReader;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lee on 2015/12/24.
 */
@Component("merchantOrderQueryService")
public class MerchantOrderQueryService extends BaseService {

    public MerchantOrderQueryBatchResult parseQueryBatchResult(String response) throws Exception {
        MerchantOrderQueryBatchResult result = new MerchantOrderQueryBatchResult();


        byte[] data = Base64.decode(response);
        String responseText = new String(data, "UTF-8");

        if (StringUtils.isEmpty(response)) {
            MyException.newInstance("解析异常").throwException();
        }

        if (responseText.indexOf("ERRORCODE=") >= 0) {
            String [] tempText = responseText.split("&");
            result.setErrorCode(tempText[0]);
            result.setErrorMessage(tempText[1]);
            return result;
        }

        String responseBody = "";
        String fileSignMsg = "";
        BufferedReader fileReader = new BufferedReader(new StringReader(responseText));
        // 读取交易结果
        String lines;
        StringBuffer fileBuf = new StringBuffer();  // 签名信息前的字符串				String lines;
        while ((lines = fileReader.readLine()) != null) {
            if (lines.length() > 0) {
                // 按行读，每行都有换行符
                fileBuf.append(lines + "\r\n");
            }
            else {
                // 文件中读到空行，则读取下一行为签名信息
                fileSignMsg = fileReader.readLine();
            }
        }
        fileReader.close();

        responseBody = fileBuf.toString();
        // 验证签名：先对文件内容计算MD5摘要，再将MD5摘要作为明文进行验证签名
        String fileMd5 = SecurityUtil.MD5Encode(responseBody);
        String certPath = Config.getSystemConfig("bank.pay.allinpay.ertPath");//生产证书路径
        boolean isVerified = SecurityUtil.verifyByRSA(certPath, fileMd5.getBytes(), Base64.decode(fileSignMsg));
        result.setVerified(isVerified);

        if (!result.isVerified()) {
            MyException.newInstance("签名验证失败").throwException();
        }

        LogService.console(responseBody);

        String [] responseBodyArray = responseBody.split("\r\n");

        // 处理第一行
        String[] responseBodyHead = responseBodyArray[0].split("\\|");
        result.setMerchantId(responseBodyHead[0]);
        result.setCurrentBillCount(Integer.parseInt(responseBodyHead[1]));
        result.setCurrentPageNo(Integer.parseInt(responseBodyHead[2]));
        result.setHasNextPage(responseBodyHead[3].equals("Y") ? true : false);

        // 处理返回的订单数据
        for (int i = 1; i < responseBodyArray.length; i++) {
            String[] responseBodyValue = responseBodyArray[i].split("\\|");

            AllinPayOrderPO allinPayOrder = new AllinPayOrderPO();
            allinPayOrder.setMerchantId(responseBodyValue[0]);
            allinPayOrder.setId(responseBodyValue[1]);
            allinPayOrder.setOrderNo(responseBodyValue[2]);
            // responseBodyValue[3] 商户订单提交时间
            // responseBodyValue[4] 商户订单金额
            allinPayOrder.setOrderDatetime(responseBodyValue[5]);
            allinPayOrder.setOrderAmount(Integer.parseInt(responseBodyValue[6]));
            // responseBodyValue[7] 扩展字段1
            // responseBodyValue[8] 扩展字段2
            allinPayOrder.setTradingStatus(responseBodyValue[9].equals("1")? AllinPayOrderTradingStatus.Accepted : AllinPayOrderTradingStatus.Unaccepted);
            result.getAllinPayOrders().add(allinPayOrder);

        }

        return result;
    }

    public MerchantOrderQueryBatchResult queryMany(String date) throws Exception {

        if (StringUtils.isEmpty(date)) {
            MyException.newInstance("传入时间为空").throwException();
        }

        date = date.replaceAll("-","");

        MerchantOrderQueryEntity entity = new MerchantOrderQueryEntity();
        entity.setMerchantId(Config.getSystemConfig("bank.pay.allinpay.merchantId"));
        entity.setVersion("v1.6");
        entity.setSignType("1");
        entity.setPageNo("1");
        entity.setKey(Config.getSystemConfig("bank.pay.allinpay.md5key"));

        String beginDateTime = date + "00";
        String endDateTime = date + "23";


        entity.setBeginDateTime(beginDateTime);
        entity.setEndDateTime(endDateTime);

        StringBuffer bufSignSrc = new StringBuffer();
        StringUtil.appendSignPara(bufSignSrc, "version", entity.getVersion());
        StringUtil.appendSignPara(bufSignSrc, "merchantId", entity.getMerchantId());
        StringUtil.appendSignPara(bufSignSrc, "beginDateTime", entity.getBeginDateTime());
        StringUtil.appendSignPara(bufSignSrc, "endDateTime", entity.getEndDateTime());
        StringUtil.appendSignPara(bufSignSrc, "pageNo", entity.getPageNo());
        StringUtil.appendSignPara(bufSignSrc, "signType", entity.getSignType());
        StringUtil.appendLastSignPara(bufSignSrc, "key", entity.getKey());
        entity.setSign(SecurityUtil.MD5Encode(bufSignSrc.toString()));

        String url = "https://service.allinpay.com/mchtoq/index.do";
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("merchantId", entity.getMerchantId());
        parameters.put("version", entity.getVersion());
        parameters.put("beginDateTime", entity.getBeginDateTime());
        parameters.put("endDateTime", entity.getEndDateTime());
        parameters.put("pageNo", entity.getPageNo());
        parameters.put("signType", entity.getSignType());
        parameters.put("key", entity.getKey());
        parameters.put("signMsg", entity.getSign());

        String result = HttpUtils.postRequest(url, parameters);



        LogService.console(result);

        return this.parseQueryBatchResult(result);
    }

    public String queryOne(String orderId, Connection conn) throws Exception {

        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("select * from bank_AllinPayTransfer where state=0 and OrderNo=? limit 1");

        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL(sbSQL.toString());
        dbSQL.addParameter(1, orderId);
        List<AllinPayOrderPO> list = MySQLDao.search(dbSQL.getSQL(), dbSQL.getParameters(), AllinPayOrderPO.class, null, conn);
        if (list == null || list.size() != 1) {
            MyException.newInstance("订单查询失败").throwException();
        }

        AllinPayOrderPO allinPayOrder = list.get(0);

        allinPayOrder = MySQLDao.load(allinPayOrder, AllinPayOrderPO.class, conn);

        MerchantOrderQueryEntity entity = new MerchantOrderQueryEntity();
        entity.setMerchantId(Config.getSystemConfig("bank.pay.allinpay.merchantId"));
        entity.setVersion("v1.5");
        entity.setSignType("0");
        entity.setOrderNo(allinPayOrder.getOrderNo());


        entity.setQueryDatetime(TimeUtils.getNow(TimeUtils.Format_YYYYMMDDHHMMSS));

        String orderDateTime =  TimeUtils.format(allinPayOrder.getOrderDatetime(), TimeUtils.Format_YYYY_MM_DD_HH_M_S, TimeUtils.Format_YYYYMMDDHHMMSS);


        entity.setOrderDatetime(orderDateTime);

        StringBuffer bufSignSrc = new StringBuffer();
        StringUtil.appendSignPara(bufSignSrc, "merchantId", entity.getMerchantId());
        StringUtil.appendSignPara(bufSignSrc, "version", entity.getVersion());
        StringUtil.appendSignPara(bufSignSrc, "signType", entity.getSignType());
        StringUtil.appendSignPara(bufSignSrc, "orderNo", entity.getOrderNo());
        StringUtil.appendSignPara(bufSignSrc, "orderDatetime",entity.getOrderDatetime());
        StringUtil.appendSignPara(bufSignSrc, "queryDatetime",entity.getQueryDatetime());
        StringUtil.appendLastSignPara(bufSignSrc, "key", Config.getSystemConfig("bank.pay.allinpay.md5key"));

        String signSrc = bufSignSrc.toString();

        entity.setSign(SecurityUtil.MD5Encode(bufSignSrc.toString()));

        String url = "https://service.allinpay.com/gateway/index.do?";
        Map<String, String> parameters = new HashMap<String, String>();

        parameters.put("merchantId", entity.getMerchantId());
        parameters.put("version", entity.getVersion());
        parameters.put("signType", entity.getSignType());
        parameters.put("orderNo", entity.getOrderNo());
        parameters.put("orderDatetime", entity.getOrderDatetime());
        parameters.put("queryDatetime", entity.getQueryDatetime());
        parameters.put("key", Config.getSystemConfig("bank.pay.allinpay.md5key"));
        parameters.put("signMsg", entity.getSign());

        String result = HttpUtils.postRequest(url, parameters);



        LogService.console(result);

        return result;
    }
}
