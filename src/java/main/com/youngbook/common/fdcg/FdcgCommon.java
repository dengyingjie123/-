package com.youngbook.common.fdcg;

import com.rd.bds.sign.util.CFCASignatureUtil;
import com.youngbook.common.KVObjects;
import com.youngbook.common.MyException;
import com.youngbook.common.ReturnObject;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.JSONDao;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.fdcg.FdcgRequestData;
import com.youngbook.entity.po.fdcg.FdcgResponseData;
import com.youngbook.entity.po.fdcg.FdcgSignData;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;

/**
 * Created by Lee on 3/14/2018.
 */
public class FdcgCommon {

    public static String getApiUrl(String key) throws Exception {

        String url = Config.getSystemConfig("thirdparty.fdcg.api.url") + Config.getSystemConfig(key);

        return url;
    }

    public static String dealReturn(String name, HttpServletRequest request, Connection conn) throws Exception {

        String requestDataString = request.getParameter("reqData");

        if (!StringUtils.isEmpty(requestDataString)) {

            FdcgResponseData responseData = JSONDao.parse(requestDataString, FdcgResponseData.class);

            responseData.setName(name);


            // String sign = Common.sign(responseData.getContent());

            boolean isPass = FdcgCommon.verifySign(responseData.getContent(), responseData.getSign(), responseData.getCertInfo());

            if (!isPass) {
                responseData.setVerifyStatus("0");
            }
            else {
                responseData.setVerifyStatus("1");
            }


            FdcgCommon.saveResponseData(responseData, conn);


            ReturnObject result = new ReturnObject();



            if (!responseData.getRetCode().equals("0000")) {
                result.setCode(9999);
                result.setMessage(responseData.getRetCode() + " " + responseData.getRetMsg());

                request.setAttribute("returnObject", result);

                return "fdcg_common_error";
            }
            else {

                result.setCode(100);
                result.setMessage("操作成功");

                request.setAttribute("returnObject", result);

                return "fdcg_common_success";
            }

        }

        return "success";
    }


    public static String dealNotify(String name, HttpServletRequest request, Connection conn) throws Exception {

        String requestDataString = request.getParameter("reqData");

        if (!StringUtils.isEmpty(requestDataString)) {

            FdcgResponseData responseData = JSONDao.parse(requestDataString, FdcgResponseData.class);
            responseData.setName(name);

            // String sign = Common.sign(responseData.getContent());

            boolean isPass = FdcgCommon.verifySign(responseData.getContent(), responseData.getSign(), responseData.getCertInfo());

            if (!isPass) {
                responseData.setVerifyStatus("0");
            }
            else {
                responseData.setVerifyStatus("1");
            }


            FdcgCommon.saveResponseData(responseData, conn);

            ReturnObject result = new ReturnObject();

            request.setAttribute("returnObject", result);

            return "fdcg_common_error";

        }

        return "success";
    }

    public static boolean isChannelOpen() throws Exception {



        /**
         *
         * thirdparty.fdcg.channel.open
         * 0: 存管功能关闭
         * 1：存管功能打开
         *
         */

        String open = Config.getSystemConfig("thirdparty.fdcg.channel.open");

        if (!StringUtils.isEmpty(open) && open.equals("1")) {
            return true;
        }

        return false;
    }

    public static FdcgResponseData saveResponseData(FdcgResponseData responseData, Connection conn) throws Exception {

        MySQLDao.insertOrUpdate(responseData, conn);

        return responseData;
    }

    public static <T> T getDataInstanceFromRequestData(String requestDataString, Class<T> clazz) throws Exception {
        FdcgRequestData requestData = JSONDao.parse(requestDataString, FdcgRequestData.class);

        boolean isPass = verifySign(requestData.getData(), requestData.getSign(), requestData.getCertInfo());

        if (!isPass) {
            MyException.newInstance("数据校验失败", "requestDataString=" + requestDataString).throwException();
        }

        T t = JSONDao.parse(requestData.getData(), clazz);

        return t;
    }

    public static <T> T getDataInstanceResponseData(String responseDataString, Class<T> clazz) throws Exception {
        FdcgResponseData responseData = JSONDao.parse(responseDataString, FdcgResponseData.class);

        boolean isPass = verifySign(responseData.getContent(), responseData.getSign(), responseData.getCertInfo());

//        if (!isPass) {
//            MyException.newInstance("数据校验失败", "responseDataString=" + responseDataString).throwException();
//        }

        T t = JSONDao.parse(responseData.getContent(), clazz);

        return t;
    }


    public static KVObjects getDataInstanceResponseData(String responseDataString) throws Exception {
        FdcgResponseData responseData = JSONDao.parse(responseDataString, FdcgResponseData.class);

        boolean isPass = verifySign(responseData.getContent(), responseData.getSign(), responseData.getCertInfo());

        if (!isPass) {
            MyException.newInstance("数据校验失败", "responseDataString=" + responseDataString).throwException();
        }

        KVObjects kvObjects = JSONDao.toKVObjects(responseData.getContent());

        return kvObjects;
    }

    public static String sign(String data) throws Exception {

        FdcgRequestData requestData = new FdcgRequestData();

        // String signFolder = "R:\\WorkSpace\\git\\dianjinpai\\src\\resources\\config\\fdcg";
        String signFolder = Config.getSystemConfig("thirdparty.fdcg.sign.folder");

        String pfxPass = "123456";
        String pfxFilePath = signFolder + "\\p2p.pfx";
        String cerFilePath = signFolder + "\\bank.cer";
        String crlFilePath = signFolder + "\\bank.crl";


        String requestDataString = CFCASignatureUtil.sign(requestData.getMerchantNo(), data, pfxFilePath, pfxPass, cerFilePath);

        return requestDataString;

    }

    // CFCASignatureUtil.verifySign()

    public static boolean verifySign(String content, String sign, String certInfo) throws Exception {
        // String signFolder = "R:\\WorkSpace\\git\\dianjinpai\\src\\resources\\config\\fdcg";
        String signFolder = Config.getSystemConfig("thirdparty.fdcg.sign.folder");

        String pfxPass = "123456";
        String pfxFilePath = signFolder + "\\p2p.pfx";
        String cerFilePath = signFolder + "\\bank.cer";
        String crlFilePath = signFolder + "\\bank.crl";

        boolean b = CFCASignatureUtil.verifySign(content, cerFilePath, crlFilePath, sign, certInfo);

        return b;
    }

    public static FdcgSignData getSignData(String signDataString) throws Exception {

        FdcgSignData signData = JSONDao.parse(signDataString, FdcgSignData.class);

        return signData;
    }

    public static String getId20() throws Exception {
        String id = IdUtils.getNewLongIdString();

        return StringUtils.buildLongNumberString(id, 20);
    }

    public static String getMerchantNo() throws Exception {
        return "M02573344910091001";
    }
}
