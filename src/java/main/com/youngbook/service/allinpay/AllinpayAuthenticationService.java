package com.youngbook.service.allinpay;

import com.aipg.common.AipgRsp;
import com.aipg.common.InfoRsp;
import com.aipg.common.XSUtil;
import com.aipg.idverify.IdVer;
import com.aipg.singleacctvalid.ValidRet;
import com.youngbook.common.Database;
import com.youngbook.common.MyException;
import com.youngbook.common.config.AesEncrypt;
import com.youngbook.common.config.Config;
import com.youngbook.common.config.Config4W;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.bank.TranxServiceImpl;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.allinpay.IAllinpayAuthenticationDao;
import com.youngbook.entity.po.customer.*;
import com.youngbook.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

@Component("allinpayAuthenticationService")
public class AllinpayAuthenticationService extends BaseService {

    @Autowired
    IAllinpayAuthenticationDao allinpayAuthenticationDao;

    public CustomerBankAuthenticationPO getBankAuthenticationStatus(String customerId, int authenticationStatus, Connection conn) throws Exception {
        return allinpayAuthenticationDao.getBankAuthenticationStatus(customerId, authenticationStatus, conn);
    }


    /**
     * 根据客户 ID，查询通联的银行卡绑定
     *
     * 修改：邓超
     * 内容：从 CustomerPersonalService 移动至此
     * 时间：2016年5月18日
     *
     * @param customerId
     * @param authenticationStatus
     * @param conn
     * @return
     * @throws Exception
     */
    public CustomerBankAuthenticationPO getCustomerBankauthentication(String customerId, int authenticationStatus, int paymentStatus, Connection conn) throws Exception {
        CustomerBankAuthenticationPO bankauthentication = new CustomerBankAuthenticationPO();
        bankauthentication.setState(Config.STATE_CURRENT);
        bankauthentication.setCustomerId(customerId);
        bankauthentication.setAuthenticationStatus(authenticationStatus); //客户认证状态
        bankauthentication.setPaymentStatus(paymentStatus); //通联支付状态
        bankauthentication.setCode(Config.getSystemVariable("bank.pay.allinpay.callback.success.flag"));
        bankauthentication = MySQLDao.load(bankauthentication, CustomerBankAuthenticationPO.class, conn);
        return bankauthentication;
    }



    /**
     *
     * @param name
     * @param number
     * @return
     * @throws Exception
     * 修改人：姚章鹏
     * 时间：2015年8月13日15:02:08
     * 内容：验证国民身份证
     */
    public boolean whetherPassRealNameVerification(String name, String number) throws Exception {

        //插入验证主表
        AllinpayauthPO allinpayauth = new AllinpayauthPO();
        allinpayauth.setTrx_code(Config4W.TRX_AUTH); //测试代付码  220001
        allinpayauth.setVersion(Config4W.VERSION); //版本  默认3
        allinpayauth.setData_type(Config4W.DATA_TYPE);//数据类型 2是xml
        allinpayauth.setLevel(Config4W.LEVEL);  //默认 5
        //测试的用户名
        allinpayauth.setUser_name(Config.getSystemConfig("bank.pay.allinpay.userName"));
        //测试的密码
        allinpayauth.setUser_pass(Config.getSystemConfig("bank.pay.allinpay.password"));
        //交易id
        String transactionId = StringUtils.buildNumberString(MySQLDao.getSequence("transactionId"), 5);
        //交易批次号=商户id+时间+固定五位数
        allinpayauth.setReq_sn(Config.getSystemConfig("bank.pay.allinpay.daifu.merchantId") + String.valueOf(System.currentTimeMillis()) +transactionId);//交易批次号
        allinpayauth.setMerchant_id(Config.getSystemConfig("bank.pay.allinpay.daifu.merchantId")); //商户ID
        //插入附表返回Id主表
        String id = insertAllinpay(allinpayauth);

        //插入验证附表
        AllinpayauthdetailPO allinpayauthdetail = new AllinpayauthdetailPO();
        allinpayauthdetail.setName(name);
        allinpayauthdetail.setIDNO(AesEncrypt.encrypt(number)); //加密身份证号
        allinpayauthdetail.setParentId(id);
        insertAllinpayDetail(allinpayauthdetail);

        //发送报文
        TranxServiceImpl tranxService = new TranxServiceImpl();
        IdVer vbreq = new IdVer();
        vbreq.setNAME(name);
        vbreq.setIDNO(number);

        //国民身份验证
        try {
            String retXml = tranxService.idVerify(Config.getSystemConfig("bank.pay.allinpay.auth.url"), false, vbreq,allinpayauth);
            boolean flag = dealRet(retXml);
            return flag;
        }
        catch (Exception e) {
            MyException.newInstance("身份验证失败", "身份验证失败，返回接口返回为空【"+e.getMessage()+"】");
        }

        return false;
    }


    /**
     * @param retXml
     * * 修改人：姚章鹏
     * 时间：2015年8月13日15:02:08
     * 内容：报文处理逻辑
     */
    public boolean dealRet(String retXml) throws Exception {
        AipgRsp aipgrsp = XSUtil.xmlRsp(retXml);
        if (aipgrsp.getINFO() != null) {
            //主表返回成功
            InfoRsp info = aipgrsp.getINFO();
            AllinpayauthcallbackPO allinpayauthcallback = new AllinpayauthcallbackPO();
            allinpayauthcallback.setTrx_code(Integer.parseInt(info.getTRX_CODE()));
            allinpayauthcallback.setVersion(info.getVERSION());
            allinpayauthcallback.setData_type(Integer.parseInt(info.getDATA_TYPE()));
            allinpayauthcallback.setReq_sn(info.getREQ_SN());
            allinpayauthcallback.setRet_code(info.getRET_CODE());
            allinpayauthcallback.setErr_msg(info.getERR_MSG());
            allinpayauthcallback.setSigned_msg(info.getSIGNED_MSG());
            insertAllinpayCallBack(allinpayauthcallback);
        }
        List<ValidRet> trxData = aipgrsp.getTrxData();
        if (trxData != null) {
            ValidRet validRet = trxData.get(0);
            AllinpayauthcallbackdetailPO allinpayauthcallbackdetail = new AllinpayauthcallbackdetailPO();
            allinpayauthcallbackdetail.setRet_code(validRet.getRET_CODE());
            allinpayauthcallbackdetail.setErr_msg(validRet.getERR_MSG());
            insertAllinpayCallBackDetail(allinpayauthcallbackdetail);
            if (validRet.getRET_CODE().equals("0000")) {
                return true;
            }
        }
        return false;
    }

    /**
     * * * 修改人：姚章鹏
     * 时间：2015年8月13日15:02:08
     * 内容：通联的新增主表返回
     * @param allinpayauthcallback
     * @return
     * @throws Exception
     */
    public String insertAllinpayCallBack(AllinpayauthcallbackPO allinpayauthcallback) throws Exception {
        Connection conn = Config.getConnection();

        try {
            int count = 0;
            int sid = MySQLDao.getMaxSid("crm_allinpayauthcallback", conn);
            String id = IdUtils.getUUID32();
            if (allinpayauthcallback.getId().equals("")) {
                allinpayauthcallback.setSid(sid);
                allinpayauthcallback.setId(id);
                allinpayauthcallback.setState(Config.STATE_CURRENT);
                allinpayauthcallback.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
                allinpayauthcallback.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(allinpayauthcallback, conn);
            }
            if (count != 1) {
                throw new Exception("数据库异常");
            }
            return id;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }

    }

    // 通联的新增主表返回
    public String insertAllinpayCallBackDetail(AllinpayauthcallbackdetailPO allinpayauthcallbackdetail) throws Exception {
        Connection conn = Config.getConnection();
        try {
            int count = 0;
            int sid = MySQLDao.getMaxSid("crm_allinpayauthcallbackdetail", conn);
            String id = IdUtils.getUUID32();
            if (allinpayauthcallbackdetail.getId().equals("")) {
                allinpayauthcallbackdetail.setSid(sid);
                allinpayauthcallbackdetail.setId(id);
                allinpayauthcallbackdetail.setState(Config.STATE_CURRENT);
                allinpayauthcallbackdetail.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
                allinpayauthcallbackdetail.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(allinpayauthcallbackdetail, conn);
            }
            if (count != 1) {
                throw new Exception("数据库异常");
            }
            return id;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }

    }

    /**
     *
     * @param allinpayauth
     * @return
     * @throws Exception
     * * 修改人：姚章鹏
     * 时间：2015年8月13日15:02:08
     * 内容：添加验证主表
     */
    public String insertAllinpay(AllinpayauthPO allinpayauth) throws Exception {
        Connection conn = Config.getConnection();

        try {
            int count = 0;
            int sid = MySQLDao.getMaxSid("crm_allinpayauth", conn);
            String id = IdUtils.getUUID32();
            if (allinpayauth.getId().equals("")) {
                allinpayauth.setSid(sid);
                allinpayauth.setId(id);
                allinpayauth.setState(Config.STATE_CURRENT);
                allinpayauth.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
                allinpayauth.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(allinpayauth, conn);
            }

            if (count != 1) {
                throw new Exception("插入身份验证主表失败");
            }

            return id;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }

    }

    // 通联的新增附表
    public String insertAllinpayDetail(AllinpayauthdetailPO allinpayauthdetail) throws Exception {
        Connection conn = Config.getConnection();

        try {
            int count = 0;
            int sid = MySQLDao.getMaxSid("crm_allinpayauthdetail", conn);
            String id = IdUtils.getUUID32();
            if (allinpayauthdetail.getId().equals("")) {
                allinpayauthdetail.setSid(sid);
                allinpayauthdetail.setId(id);
                allinpayauthdetail.setState(Config.STATE_CURRENT);
                allinpayauthdetail.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
                allinpayauthdetail.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(allinpayauthdetail, conn);
            }
            if (count != 1) {
                throw new Exception("数据库异常");
            }
            return id;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }



    }

}
