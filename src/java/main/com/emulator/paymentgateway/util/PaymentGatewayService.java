package com.emulator.paymentgateway.util;

import java.security.Key;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import sun.misc.BASE64Encoder;

import com.emulator.paymentgateway.entity.PaymentGateway;

public class PaymentGatewayService
{
	
//	public void runThread(String url)
//	{
//		try 
//		{
//			PaymentGatewayThread pt = new PaymentGatewayThread("one");
//			int i=0;
//			DefaultHttpClient httpClient = new DefaultHttpClient();
//			HttpPost httpPost = new HttpPost(url);
//			while(true)
//			{
//				pt.run();
//				System.out.println("Thread One is alive: "+i);
//				
//				String xml = getXML();
//				
//				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
//		        paramList.add(new BasicNameValuePair("respMsg", xml));
//
//		        httpPost.setEntity(new UrlEncodedFormEntity(paramList, Consts.UTF_8));
//		        HttpResponse response = httpClient.execute(httpPost);
//
//		        HttpEntity entity = response.getEntity();
//		         
//		        String resultHtml = EntityUtils.toString(entity,"UTF-8");
//		        
//		        resultHtml = new String(new BASE64Decoder().decodeBuffer(resultHtml), Consts.UTF_8);
//		        System.out.println(" resultHtml  " + i +" === "+resultHtml);
//		        
//		        i++;
//			}
//		}
//		catch (Exception e) 
//		{
//			e.printStackTrace();
//		}
//	}
	
	public String getSingXML(PaymentGateway pg,Key key)
	{
		DateFormat sfDay = new SimpleDateFormat("yyyyMMdd");
		DateFormat sfTime = new SimpleDateFormat("HHmmss");
		Date date = new Date();
		String nowDay = sfDay.format(date);
		String nowTime = sfTime.format(date);
		
		if(null == pg.getBrcId()|| "".equals(pg.getBrcId()))
			pg.setBrcId("0000");
		
		//����
		String xml1 = "<transaction>" +
		"<Head>" +
		"<Processing_code>" + pg.getProcessingCode() + "</Processing_code>" +
		"<brk_id>"+pg.getBrkId()+"</brk_id>" +
		"<trans_date>" + nowDay + "</trans_date>" +
		"<trans_time>" + nowTime + "</trans_time>" +
		"<Bus_num>"+pg.getBusNum()+"</Bus_num>";
		
		String xml2= "</Head>" +
		"<Request>" +
		"<Brk_trace_num>"+pg.getBrkTraceNum()+"</Brk_trace_num>" +
		"<Brc_id>"+pg.getBrcId()+"</Brc_id>" +
		"<acct_num>" + pg.getAcctNum() + "</acct_num>" +
		"<Bnk_id>" + pg.getBnkId() + "</Bnk_id>" +
		"<hld_name>" + pg.getHidName() + "</hld_name>" +
		"<cer_type>"+pg.getCerType()+"</cer_type>" +
		"<Cer_num>"+pg.getCerNum()+"</Cer_num>" +
		"<Tel_num>"+pg.getTelNum()+"</Tel_num>" +
		"<Stock_num>" + pg.getStockNum() + "</Stock_num>" +
		"<Trace_fee>"+pg.getTraceFee()+"</Trace_fee>" +
		"<Reqs_url>" + pg.getReqsUrl() + "</Reqs_url>" +
		"<Resp_url>" + pg.getRespUrl() + "</Resp_url>" +
		"<addtnl_data>"+pg.getAddtnlData()+"</addtnl_data>" +
		"</Request>" +
		"</transaction>";
		System.out.println(111111+" === "+xml1+xml2);
		String signformat="";
		
		try
		{
			signformat = singMsg(xml1,xml2, key);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return signformat;
	}
	
	public String getQuerySingXML(PaymentGateway pg,Key key)
	{
		DateFormat sfDay = new SimpleDateFormat("yyyyMMdd");
		DateFormat sfTime = new SimpleDateFormat("HHmmss");
		Date date = new Date();
		String nowDay = sfDay.format(date);
		String nowTime = sfTime.format(date);
		
		if(null == pg.getBrcId()|| "".equals(pg.getBrcId()))
			pg.setBrcId("0000");
		
		//����
		String xml1 = "<?xml version=\"1.0\" encoding=\"GBK\"?><transaction>" +
		"<Head>" +
		"<Processing_code>" + pg.getProcessingCode() + "</Processing_code>" +
		"<brk_id>"+pg.getBrkId()+"</brk_id>" +
		"<trans_date>" + nowDay + "</trans_date>" +
		"<trans_time>" + nowTime + "</trans_time>" +
		"<Bus_num>"+pg.getBusNum()+"</Bus_num>";
		
		String xml2= "</Head>" +
		"<Request>" +
		"<Brk_trace_num>"+pg.getBrkTraceNum()+"</Brk_trace_num>" +
		"<Brc_id>"+pg.getBrcId()+"</Brc_id>" +
		"<Org_trace_num>" + pg.getOrgTraceNum() + "</Org_trace_num>" +
		"<addtnl_data>"+pg.getAddtnlData()+"</addtnl_data>" +
		"</Request>" +
		"</transaction>";
		System.out.println(111111+" === "+xml1+xml2);
		
		String signformat="";
		
		try
		{
			xml1 = new String(xml1.getBytes("UTF-8"),"GBK");
			xml2 = new String(xml2.getBytes("UTF-8"),"GBK");
			
			signformat = singMsg(xml1,xml2, key);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return signformat;
	}
	
	public String singMsg(String xml1,String xml2,Key key)
	{
		String signformat="";
		try
		{
			
			//===��ǩ
			String signMsg = SecurityUtil.getSignMsg(xml1+xml2);
			signMsg = xml1 + "<sign_code>"+signMsg+"</sign_code>"+xml2;
			
			//�ԳƼ���
			byte[] encryptedText = SecurityUtil.encryptSymmetry(signMsg, key);
			
			//�Գ���Կ����
			X509Certificate cert = SecurityUtil.getCer();
			byte[] encryptedKey = SecurityUtil.encryptSymmetricKey(key, cert.getPublicKey());
			BASE64Encoder encoder = new BASE64Encoder();
			String xml3 = "<STSPackage>"+
			"<EncryptedText>"+encoder.encode(encryptedText)+"</EncryptedText>"+
			"<KeyInfo>"+
			"<ReceiverX509CertSN>"+cert.getSerialNumber().toString(10)+"</ReceiverX509CertSN>" +
			"<EncryptedKey>"+encoder.encode(encryptedKey)+"</EncryptedKey>" +
			"</KeyInfo>"+
			"</STSPackage>";
			System.out.println("xml3  ======"+xml3);
			signformat = encoder.encode(xml3.getBytes("UTF-8"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return signformat;
	}
	
	public static String getNodeValue(String returnStr, String tagName)  {
        String beginTag = "<" + tagName + ">";
        String endTag = "</" + tagName + ">";


        returnStr = returnStr.substring(returnStr.indexOf(beginTag)+beginTag.length(),returnStr.indexOf(endTag));
        return returnStr;
    }
	
	public static void main(String[] agre) throws Exception
	{
//		//�õ��Գ�KEY
//		Key key = SecurityUtil.generateSymmetricKey();
//		//�ԳƼ���
//		byte[] keySignMsg = SecurityUtil.encryptSymmetry("aaaaaa", key);
//		
//		System.out.println(key.toString());
//		
//		System.out.println(keySignMsg.toString());
		PaymentGatewayService p = new PaymentGatewayService();
		//System.out.println(p.getXML());
		
		System.out.println(p.getNodeValue("<bbb><aaa>123</aaa>xxxx", "aaa"));
	}
	
	
}
