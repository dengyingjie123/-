package com.youngbook.service.allinpay;

import com.aipg.merchantorder.MerchantOrderQueryBatchResult;
import com.allinpay.ets.client.SecurityUtil;
import com.allinpay.ets.client.util.Base64;
import com.youngbook.common.Database;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.service.system.LogService;
import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.StringReader;
import java.sql.Connection;

public class MerchantOrderQueryServiceTest extends TestCase {

    public void testQuery() throws Exception {
        MerchantOrderQueryService service = new MerchantOrderQueryService();

        Connection conn = Config.getConnection();
        try {
            //String orderId = "0FD06BB656EE4AE0880A02D4662DE01C";
            String orderId = "CC4B514BD39C4AEAB3E2EE7DD7B2B3FF";
            String now = TimeUtils.getNow(TimeUtils.Format_YYYYMMDD);
            MerchantOrderQueryBatchResult merchantOrderQueryBatchResult = service.queryMany("20151225");

            LogService.console(merchantOrderQueryBatchResult.getErrorCode());

//            byte[] data = Base64.decode(response);
//            String tempStr = new String(data, "UTF-8");
//            MyLog.console(tempStr);
//            String fileAsString = ""; // 签名信息前的对账文件内容
//            String fileSignMsg = ""; // 文件签名信息
//            boolean isVerified = false; // 验证签名结果
//
//            String viewMsg = "";
//            String postUrl = "https://service.allinpay.com/mchtoq/index.do?";
//            if(tempStr.indexOf("ERRORCODE=")<0){
//                BufferedReader fileReader = new BufferedReader(new StringReader(tempStr));
//                // 读取交易结果
//                String lines;
//                StringBuffer fileBuf = new StringBuffer(); // 签名信息前的字符串				String lines;
//                while ((lines = fileReader.readLine()) != null) {
//                    if (lines.length() > 0) {
//                        // 按行读，每行都有换行符
//                        fileBuf.append(lines + "\r\n");
//                    } else {
//                        // 文件中读到空行，则读取下一行为签名信息
//                        fileSignMsg = fileReader.readLine();
//                    }
//                }
//                fileReader.close();
//                fileAsString = fileBuf.toString();
//                System.out.println("File: \n" + fileAsString);
//                System.out.println("Sign: \n" + fileSignMsg);
//                // 验证签名：先对文件内容计算MD5摘要，再将MD5摘要作为明文进行验证签名
//                String fileMd5 = SecurityUtil.MD5Encode(fileAsString);
//                String certPath="";
//                if(postUrl.indexOf("service.allinpay.com")>0){
//                    certPath=Config.getSystemVariable("bank.pay.allinpay.ertPath");//生产证书路径
//                }else{
//                    certPath="/opt/conf/TLCert-test.cer"; //测试证书路径
//                }
//                isVerified = SecurityUtil.verifyByRSA(certPath, fileMd5.getBytes(), Base64.decode(fileSignMsg));
//                if (isVerified) {
//                    // 验证签名通过，解析交易明细，开始对账
//                    System.out.println("验证签名通过");
//                    viewMsg=fileAsString+fileSignMsg;
//                } else {
//                    // 验证签名不通过，丢弃对账文件
//                    System.out.println("验证签名不通过");
//
//                }
//            }


        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }
    }
}