package com.youngbook.service.pay;

import com.youngbook.common.MyException;
import com.youngbook.common.ReturnObject;
import com.youngbook.common.ReturnObjectCode;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.system.ILogDao;
import com.youngbook.entity.po.customer.CustomerPersonalPO;
import com.youngbook.service.BaseService;
import com.youngbook.service.system.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@Component("fuiouAuthenticationService")
public class FuiouAuthenticationService extends BaseService {

    @Autowired
    ILogDao logDao;

    public static void main(String[] args) throws Exception {

    }

    /**
     * 进行富友的实名认证
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月29日
     *
     * @author 邓超
     * @param customerName      姓名
     * @param mobile            手机号码
     * @param certificateNo     客户身份证号（需要解密后传参）
     * @param bankNumber        银行卡号（需要解密后传参）
     * @throws Exception
     */
    public Boolean authenticationByCustomerName(String customerName, String mobile, String certificateNo, String bankNumber, Connection conn) throws Exception {

        // 获取富友的接口 URL
        String fuiouURL = Config.getSystemConfig("fuiou.authentication.url");
        if (StringUtils.isEmpty(fuiouURL)) {
            MyException.newInstance("无法获得验证接口URL").throwException();
        }

        // 获取富友商户 ID
        String merchantId = Config.getSystemConfig("fuiou.merchant.id");
        if (StringUtils.isEmpty(merchantId)) {
            MyException.newInstance("无法获得验证商户编号").throwException();
        }

        // 获取富友的商户密钥
        String key = Config.getSystemConfig("fuiou.key");
        if (StringUtils.isEmpty(key)) {
            MyException.newInstance("无法获得验证商户信息").throwException();
        }

        // 生成请求签名
        String sign = StringUtils.md5(merchantId + "|" + bankNumber + "|" + "0" + "|" + key).toLowerCase();

        // 组装 XML
        StringBuffer xml = new StringBuffer();
        xml.append("<FM>");
        xml.append("<MchntCd>" + merchantId + "</MchntCd>");
        xml.append("<Ono>" + bankNumber + "</Ono>");
        xml.append("<OCerTp>0</OCerTp>");               // 证件类型，只支持身份证
        xml.append("<Onm>" + customerName + "</Onm>");
        xml.append("<OCerNo>" + certificateNo + "</OCerNo>");
        xml.append("<Mno>" + mobile + "</Mno>");
        xml.append("<Sign>" + sign + "</Sign>");
        xml.append("</FM>");

        // 组装参数
        Map<String, String> params = new HashMap<String,String>();
        params.put("FM", xml.toString());

        LogService.log2File("富友实名认证信息发送" + xml.toString(), this.getClass());

        logDao.save("富友支付", "富友实名认证返回", xml.toString(), conn);

        // 发起请求并获取响应 XML
        String response = HttpUtils.postRequest(fuiouURL, params);
        // 解析 XML
        Map<String, String> data = FuiouPaymentService.parseFuiouXmlCallback(response);
        String responseCode = data.get("Rcd");
        String responseString = data.get("RDesc");
        String responseMd5 = data.get("Sign");
        // 一般校验数据
        if("".equals(responseCode) || "".equals(responseString) || "".equals(responseMd5)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "接口响应参数不完整").throwException();
        }
        if(responseMd5.length() != 32) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_CORRECT, "接口响应参数不正确").throwException();
        }
        // 本地生成响应签名
        String localMd5 = StringUtils.md5(responseCode + "|" + key).toLowerCase();
        // 验证响应码和签名
        if("0000".equals(responseCode) && localMd5.equals(responseMd5)) {
            return true;
        }
        else {
            return false;
        }

    }

    /**
     * 进行富友的实名认证
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月29日
     *
     * @param customerId
     * @param mobile
     * @param certificateNo
     * @param bankNumber
     * @param conn
     * @throws Exception
     */
    public Boolean authenticationByCustomerId(String customerId, String mobile, String certificateNo, String bankNumber, Connection conn) throws Exception {

        // 构造查询实体
        CustomerPersonalPO personal = new CustomerPersonalPO();
        personal.setId(customerId);
        personal.setState(Config.STATE_CURRENT);

        // 查询客户
        personal = MySQLDao.load(personal, CustomerPersonalPO.class, conn);
        if(personal == null) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "没有查询到客户的信息").throwException();
        }

        String customerName = personal.getName();
        return authenticationByCustomerName(customerName, mobile, certificateNo, bankNumber, conn);

    }

}
