package com.emulator.fund.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Enumeration;

import com.youngbook.common.utils.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;


public class SignAndUnSign
{

	public static final String SIGNATURE_ALGORITHM = "SHA1withRSA"; //
	 
	public static final String KEY_GEN_ALGORITHM   = "RSA"; //
	  
	private static final String PASSWORD = "111111";//
	  
	private static final String SIGNMODE = "PKCS12";//
	
	private static String certPath = "WEB-INF/cer/110000000000000.pfx";
	
	static 
	{
	       Security.addProvider(new BouncyCastleProvider());
    }
	
    /**
     * ��ǩ����
     */
    public static String getSignMsg(String strMsg, String key)
    {
        if (null == strMsg || strMsg.trim().length() < 1)
        {
            return null;
        }
        String sign = getNodeValue(strMsg, "ENVELOPE");
        sign = sign.replaceAll("\n", "");
        sign = sign.replaceAll("\r", "");
        sign = sign.replaceAll(" ", "");
        
        String signAll = sign+ "<KEY>"+key+"</KEY>";
        System.out.println(signAll);
        // String signMsg = MD5.md5Encode(signAll).toUpperCase();
        String signMsg = StringUtils.md5(signAll).toUpperCase();

        return signMsg;
    }
	
    public static String getRealPath(){
  	  String url = SignAndUnSign.class.getResource("/").getFile();
  	  return  url.substring(0, url.indexOf("WEB-INF"));
    }
    
	/**
     *
     */
    public static String getSignMsg(String strMsg)
    {
        if (null == strMsg || strMsg.trim().length() < 1)
        {
            return null;
        }
        String sign = getNodeValue(strMsg, "ENVELOPE");
        sign = sign.replaceAll("\n", "");
        sign = sign.replaceAll("\r", "");
        sign = sign.replaceAll(" ", "");
        String signMsg = null;
        try
        {
        	FileInputStream priStream = new FileInputStream(getRealPath()+certPath);
        	
        	//System.out.println("sign  !=! " + sign);
        	
        	KeyStore ks = KeyStore.getInstance(SIGNMODE);  
    		ks.load(priStream, PASSWORD.toCharArray());
    		priStream.close();  
    		
    		Enumeration enumas = ks.aliases();  
    	    String keyAlias = null;  
    	    if (enumas.hasMoreElements())
    	    {  
    	          keyAlias = (String)enumas.nextElement();   
            }  
    	    PrivateKey priKey = (PrivateKey) ks.getKey(keyAlias, PASSWORD.toCharArray());  //�õ�˽Կ
    	    
	        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM,"BC");  
	        signature.initSign(priKey);  
	        signature.update(sign.getBytes("UTF-8"));
	        byte[] signData = signature.sign();  
	        
	        byte[] signStr = Base64.encode(signData);
	        signMsg = new String(signStr);
        }
        catch (NoSuchAlgorithmException e) 
	    {
	          throw new RuntimeException("ǩ��ʧ�ܣ��Ҳ���RSA�㷨����Կ��������", e);
	    } 
	    catch (InvalidKeyException e) 
	    {
	          throw new RuntimeException("ǩ��ʧ�ܣ�˽Կ��Ч", e);
	    } 
	    catch (SignatureException e) 
	    {
	          throw new RuntimeException("ǩ��ʧ�ܣ�signatureִ��ǩ��ʧ��", e);
	    }    
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        
        return signMsg;
    }
    
    public static String getNodeValue(String returnStr, String tagName) {
        int startIdx, endIdx;
        String beginTag = "<" + tagName + ">";
        String endTag = "</" + tagName + ">";
        startIdx = returnStr.indexOf(beginTag);
        endIdx = returnStr.indexOf(endTag, startIdx);
        return returnStr.substring(startIdx, endIdx + beginTag.length() + 1);
    }
    
    /**
     * ��ǩ����
     */
    public static boolean verifySign(String respMsg, String key)throws Exception
    {
        if (null == respMsg || respMsg.trim().length() < 1)
        {
            throw new Exception(
                    "value of param respMsg is :" + respMsg);
        }
        else if (null == key || key.trim().length() < 1)
        {
            throw new Exception(
                    "value of param key is :" + key);
        }
        
        String signMsg = SignAndUnSign.getSignMsg(respMsg,key);
        System.out.println("signMsg!!!!!!!!!!!!>>>>>>>>>>>>>>>>>>>>>>>>>>  === "+signMsg);
        String oriSignMsg = getNodeValue(respMsg, "SIGN_MSG");
        oriSignMsg = oriSignMsg.replaceAll("<SIGN_MSG>", "");
        oriSignMsg = oriSignMsg.replaceAll("</SIGN_MSG>", "");
        if (signMsg.equalsIgnoreCase(oriSignMsg))
        {
            return true;
        }
        return false;
    }
    
    //=-----
    /**
     * ��ǩ����
     */
    public static boolean verifySign(String respMsg)throws Exception
    {
        if (null == respMsg || respMsg.trim().length() < 1)
        {
            throw new Exception(
                    "value of param respMsg is :" + respMsg);
        }
        
        String oriSignMsg= getNodeValue(respMsg, "SIGN_MSG");
        oriSignMsg = oriSignMsg.replaceAll("<SIGN_MSG>", "");
        oriSignMsg = oriSignMsg.replaceAll("</SIGN_MSG>", "");
        
        String oriRespMsg= getNodeValue(respMsg, "ENVELOPE");        
        Boolean verify = verify("D:\\TL_CODE\\codes\\ExtAndJobWeb\\web\\WEB-INF\\cer\\fund\\gt\\gy.cer", oriRespMsg, oriSignMsg);
        return verify;
    }
    
    /**

     * ��֤ǩ��
     * 
     * @param certPath
     *            ��Կ·��
     * @param src
     *            Դ����
     * @param mac
     *            ǩ������
     * @return ��ǩ���
     */

     private static boolean verify(String certPath, String src, String mac) {

        boolean tResult = false;

        try {

            byte[] aPlainData = src.getBytes("UTF-8");
            byte[] aSignature = Base64.decode(mac);

            InputStream inStream = new FileInputStream(certPath);
            CertificateFactory tCertFactory = CertificateFactory.getInstance("X.509");
            Certificate tCertificate = tCertFactory.generateCertificate(inStream);
            Signature tSign = Signature.getInstance("SHA1withRSA","BC");
            tSign.initVerify(tCertificate);
            tSign.update(aPlainData);
            tResult = tSign.verify(aSignature);

        } 
        catch (Exception e) 
        {
        	e.printStackTrace();
        }
        return tResult;

     }
    
    public static void main(String[] agre) throws Exception
    {
    	SignAndUnSign sa = new SignAndUnSign();
    	
    	
    	//String aa;// = "<RESPONSE><ENVELOPE><HEAD><VERSION>v1.0</VERSION><BUSINESS_TYPE>03</BUSINESS_TYPE><PAY_TYPE>08</PAY_TYPE><TRANS_CODE>2001</TRANS_CODE><AGENT_ID>900002012030001</AGENT_ID><TRACE_NUM>20120929115852000218</TRACE_NUM><TRANS_DATE>20120929</TRANS_DATE><TRANS_TIME>115852</TRANS_TIME></HEAD><TX_INFO><MERCHANT_NO>900002012030001</MERCHANT_NO><TRANS_AMOUNT>100000</TRANS_AMOUNT><CURRENCY>156</CURRENCY><ACCT_CAT>01</ACCT_CAT><ACCT_NO>MTE0</ACCT_NO><CONTRACT_NO>2012092911585346103559</CONTRACT_NO><RET_CODE>000000</RET_CODE><RET_MSG>\u4ea4\u6613\u6210\u529f</RET_MSG><IPP_TRACE_NUM>2012092911585346103559</IPP_TRACE_NUM><IPP_TRANS_DATE>20120929</IPP_TRANS_DATE><IPP_TRANS_TIME>115853</IPP_TRANS_TIME><EXTEND_INFO>Hello.Hi!</EXTEND_INFO></TX_INFO></ENVELOPE>";
    	//String bb = sa.getSignMsg(aa);
    	/*aa = "<RESPONSE><ENVELOPE><HEAD><VERSION>v1.0</VERSION><BUSINESS_TYPE>03</BUSINESS_TYPE><PAY_TYPE>08</PAY_TYPE><TRANS_CODE>2001</TRANS_CODE><AGENT"+
"_ID>900002012030001</AGENT_ID><TRACE_NUM>20120929022051000219</TRACE_NUM><TRANS_DATE>20120929</TRANS_DATE><TRANS_TIME>022051</TRANS_TIME></H"+
"EAD><TX_INFO><MERCHANT_NO>900002012030001</MERCHANT_NO><TRANS_AMOUNT>100000</TRANS_AMOUNT><CURRENCY>156</CURRENCY><ACCT_CAT>01</ACCT_CAT><AC"+
"CT_NO>MTE0</ACCT_NO><CONTRACT_NO>2012092914205209128055</CONTRACT_NO><RET_CODE>000000</RET_CODE><RET_MSG>���׳ɹ�</RET_MSG><IPP_TRACE_NU"+
"M>2012092914205209128055</IPP_TRACE_NUM><IPP_TRANS_DATE>20120929</IPP_TRANS_DATE><IPP_TRANS_TIME>142052</IPP_TRANS_TIME><EXTEND_INFO>Hello.H"+
"i!</EXTEND_INFO></TX_INFO></ENVELOPE><SIGNATURE><SIGN_TYPE>0</SIGN_TYPE><SIGN_MSG>QqRkTnfyExhYA5dN/OfrYRBQD4/x6XrrCBeR2DStzAj5bbYuP8GBSozwF7"+
"MFQ23RNVpGbwH8K2fe+ND1UL7mrl3Webag890K+S8MFZCc3MokSgEnqJD/rrNz7m4olxHzUe1Nv7BloLZBfkiVxsP+NKlV9zp93IYZAB+wxgGdw3s=</SIGN_MSG></SIGNATURE></R"+
"ESPONSE>";*/
    	
    	/*String aa = "<ENVELOPE><HEAD><VERSION>v1.0</VERSION><BUSINESS_TYPE>03</BUSINESS_TYPE><PAY_TYPE>08</PAY_TYPE><TRANS_CODE>2002</TRANS_CODE><AGENT_ID>100000211209000</AGENT_ID><TRACE_NUM>20130416103405032969</TRACE_NUM><TRANS_DATE>20130416</TRANS_DATE><TRANS_TIME>103337</TRANS_TIME><TRADING_DAY>20130417</TRADING_DAY><TRADING_NUM>1</TRADING_NUM><NOTIFY_URL></NOTIFY_URL><BG_NOTIFY_URL>http://192.168.1.64:8480/AppStsBankProcessorLcAndB2C/payMoney.action</BG_NOTIFY_URL></HEAD><TX_INFO><MERCHANT_NO>100000211209004</MERCHANT_NO><TRANS_AMOUNT>100000</TRANS_AMOUNT><CURRENCY>156</CURRENCY><BANK_CODE>01040000</BANK_CODE><BANK_NO></BANK_NO><BANK_NAME></BANK_NAME><BANK_PROVINCE></BANK_PROVINCE><BANK_CITY></BANK_CITY><ACCT_NAME>Ф��</ACCT_NAME><ACCT_CAT>01</ACCT_CAT><ACCT_NO>6013820800064094749</ACCT_NO><ID_TYPE>01</ID_TYPE><ID_NO>310109197505275614</ID_NO><CONTRACT_NO>20130411114237028</CONTRACT_NO><EXTEND_INFO></EXTEND_INFO></TX_INFO></ENVELOPE>";
    	String cc = getSignMsg(aa,"12345678901");
    	System.out.println("miwen="+cc);*/
    	
    	String miwen ="<RESPONSE><ENVELOPE><HEAD><VERSION>v1.0</VERSION><BUSINESS_TYPE>03</BUSINESS_TYPE><PAY_TYPE>50</PAY_TYPE><TRANS_CODE>2010</TRANS_CODE><AGENT_ID>FN0000201206001</AGENT_ID><TRACE_NUM>20130723181055085398</TRACE_NUM><TRANS_DATE>20130723</TRANS_DATE><TRANS_TIME>181055</TRANS_TIME></HEAD><TX_INFO><MERCHANT_NO>FN0000201207115</MERCHANT_NO><TRANS_AMOUNT>1</TRANS_AMOUNT><CURRENCY>156</CURRENCY><BANK_CODE>50010001</BANK_CODE><RET_CODE>000000</RET_CODE><RET_MSG>���׳ɹ�</RET_MSG><IPP_TRACE_NUM>1307231810570080</IPP_TRACE_NUM><IPP_TRANS_DATE>20130723</IPP_TRANS_DATE><IPP_TRANS_TIME>181057</IPP_TRANS_TIME><ORG_SERIAL_NO>20130723181109045</ORG_SERIAL_NO></TX_INFO></ENVELOPE><SIGNATURE><SIGN_TYPE>0</SIGN_TYPE><SIGN_MSG>75DCFA3C5D97B5437262073B783B6DA6</SIGN_MSG></SIGNATURE></RESPONSE>";
    	
    	//miwen = BASE64.BASE64Decode(miwen);
    	//System.out.println(miwen);
    	System.out.println("----------------------------------------");
    	//aa = aa +"<SIGN_MSG>"+cc+"</SIGN_MSG>";
    	System.out.println(verifySign(miwen, "12345678901"));
    	//System.out.println(verifySign(aa,"12345678901"));
    }
    
    
}
