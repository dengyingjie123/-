/**
 *项目：xmlInterf
 *通联支付网络有限公司
 * 作者：张广海
 * 日期：Jan 2, 2013
 * 功能说明：系统对接xml批量代收demo
 */
package com.youngbook.common.utils.bank;


import com.aipg.common.AipgRsp;
import com.aipg.common.XSUtil;
import com.aipg.idverify.IdVer;
import com.aipg.singleacctvalid.ValidRet;
import com.aipg.transquery.TransQueryReq;
import com.youngbook.common.ReturnObject;
import com.youngbook.common.config.Config;
import com.youngbook.common.config.XmlHelper;

import com.aipg.common.AipgReq;
import com.aipg.common.InfoReq;
import com.allinpay.XmlTools;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.customer.AllinpayauthPO;
import com.youngbook.service.system.LogService;

public class TranxServiceImpl {

	public String sendXml(String xml,String url,boolean isFront) throws  Exception {


		System.out.println("======================发送报文======================：\n"+xml);
		String resp=XmlTools.send(url,xml);
		System.out.println("======================响应内容======================") ;
		System.out.println(new String(resp.getBytes(),"UTF-8")) ;
		boolean flag= this.verifyMsg(resp, Config.getSystemConfig("bank.pay.allinpay.tltcerPath"),isFront);
		if(flag){
			System.out.println("响应内容验证通过") ;
		}else{
			System.out.println("响应内容验证不通过") ;
		}
		return resp;
	}

    public ReturnObject dealResponse(String response) throws Exception{

        XmlHelper xmlHelper = new XmlHelper(response);

        ReturnObject returnObject = new ReturnObject();

        String message = xmlHelper.getElementByXPath("//AIPG/VALIDRET/ERR_MSG").getStringValue();

        returnObject.setCode(ReturnObject.CODE_EXCEPTION);
        returnObject.setMessage(message);
        returnObject.setReturnValue(response);
        return returnObject;
    }

	public String isFront(String xml,boolean flag,String url) throws Exception {
		try{
			if(!flag){
				xml=this.signMsg(xml);
			}else{
				xml=xml.replaceAll("<SIGNED_MSG></SIGNED_MSG>", "");
			}
			return sendXml(xml,url,flag);
		}catch(Exception e){
			throw e;
		}
	}
	/**
	 * 报文签名
	 * @param
	 * @return
	 * @throws Exception
	 */
	public String signMsg(String xml) throws Exception{

        String pfxPath = Config.getSystemConfig("bank.pay.allinpay.pfxPath");
        String password = Config.getSystemConfig("bank.pay.allinpay.pfxPassword");

		xml=XmlTools.signMsg(xml, pfxPath, password, false);
		return xml;
	}
	
	/**
	 * 验证签名
	 * @param msg
	 * @return
	 * @throws Exception
	 */
	public boolean verifyMsg(String msg,String cer,boolean isFront) throws Exception{
		 boolean flag=XmlTools.verifySign(msg, cer, false,isFront);
		System.out.println("验签结果["+flag+"]") ;
		return flag;
	}




    /**
     * 将数据组装成xml，并请求过去
     *
     * 用法：先使用makeReq组装头文件,将数据存放在vbreq对象里面，再添加到创建好的AipgReq,
     * 使用xmlTool.buildXml 生成xml数据，使用isFront发送
     * @throws Exception*
     * 修改人：姚章鹏
     * 时间：2015年8月13日15:02:08
     * 内容：国民身份验证 代码220001
     */
    public String idVerify(String url, boolean isTLTFront, IdVer vbreq, AllinpayauthPO temp) throws Exception {
        String xml="";
        AipgReq aipgReq=new AipgReq();
        InfoReq info=makeReq(temp);
        aipgReq.setINFO(info);
        aipgReq.addTrx(vbreq);
        xml= XmlTools.buildXml(aipgReq, true);
        //发送xml数据


        LogService.info("发送身份验证请求", this.getClass());
        LogService.info(xml, this.getClass());

        String resp = isFront(xml,isTLTFront,url);
        return resp;
    }

    public String sendRequest(InfoReq info, Object requestBody, boolean isTLTFront,String url) throws Exception {

        String xml="";
        AipgReq aipgReq=new AipgReq();
        aipgReq.setINFO(info);
        aipgReq.addTrx(requestBody);
        xml= XmlTools.buildXml(aipgReq, true);
        //发送xml数据

        LogService.info("发送身份验证请求", MySQLDao.class);
        LogService.info(xml, MySQLDao.class);

        String resp = isFront(xml,isTLTFront,url);
        return resp;
    }

    /**
     * 组装报文头部
     * @param temp
     * @return
     */
    public InfoReq makeReq(AllinpayauthPO temp) throws Exception {
        String trxcod = String.valueOf(temp.getTrx_code());
        InfoReq info=new InfoReq();
        info.setTRX_CODE(trxcod);
        info.setREQ_SN(temp.getReq_sn());
        info.setUSER_NAME(temp.getUser_name());
        info.setUSER_PASS(temp.getUser_pass());
        info.setLEVEL(String.valueOf(temp.getLevel()));
        info.setDATA_TYPE(String.valueOf(temp.getData_type()));
        info.setVERSION(String.valueOf(temp.getVersion()));
        if("300000".equals(trxcod)||"300001".equals(trxcod)||"300003".equals(trxcod)||"220001".equals(trxcod)||"220003".equals(trxcod)){
            info.setMERCHANT_ID(temp.getMerchant_id());
        }
        return info;
    }

    /**
     * 功能：交易结果查询
     *
     * @param reqsn 交易流水号
     * @param url 通联地址
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @throws Exception
     */
    public String queryTradeNew(String url,String reqsn,boolean isTLTFront,String startDate,String endDate) throws Exception {

        String xml="";

        AipgReq aipgReq=new AipgReq();
        InfoReq info=makeReq("200004");
        aipgReq.setINFO(info);

        //组装QueryReq
        TransQueryReq dr=new TransQueryReq();
        aipgReq.addTrx(dr);
        dr.setMERCHANT_ID(Config.getSystemConfig("bank.pay.allinpay.daifu.merchantId")) ; //商户号
        dr.setQUERY_SN(reqsn);
        dr.setSTATUS(1);
        dr.setTYPE(1) ;
       //若不填QUERY_SN 则必填开始时间和结束时间
        if(reqsn==null||"".equals(reqsn)){
            dr.setSTART_DAY(startDate);
            dr.setEND_DAY(endDate);
        }
        xml=XmlTools.buildXml(aipgReq,true);
        return xml;
    }


    //组装
    private InfoReq makeReq(String trxcod) throws Exception {

        InfoReq info=new InfoReq();
        info.setTRX_CODE("200004");
        info.setREQ_SN(String.valueOf(System.currentTimeMillis()));
        info.setUSER_NAME(Config.getSystemConfig("bank.pay.allinpay.userName"));
        info.setUSER_PASS(Config.getSystemConfig("bank.pay.allinpay.password"));
        info.setLEVEL("5");
        info.setDATA_TYPE("2");
        info.setVERSION("03");
        return info;
    }


}
