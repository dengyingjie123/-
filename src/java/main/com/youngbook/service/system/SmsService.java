package com.youngbook.service.system;

import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.MyException;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.*;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.production.IOrderDao;
import com.youngbook.dao.sale.IPaymentPlanDao;
import com.youngbook.dao.system.ILogDao;
import com.youngbook.dao.system.IUserDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.system.*;
import com.youngbook.entity.vo.Sale.PaymentPlanVO;
import com.youngbook.entity.vo.production.OrderVO;
import com.youngbook.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.*;


@Component("smsService")
public class SmsService extends BaseService {

    @Autowired
    IPaymentPlanDao paymentPlanDao;

    @Autowired
    IUserDao userDao;

    @Autowired
    IOrderDao orderDao;

    @Autowired
    ILogDao logDao;

    public static final String ACTION_SEND = "sendmsg";

    public static void main(String [] args) throws Exception {

        Connection conn = Database.getConnection();

        SmsService smsService = Config.getBeanByName("smsService", SmsService.class);

        try {

            // 所有客户
            String [] mobiles = new String[]{"13888939712","18688998239"};

            List<SmsPO> list = new ArrayList<SmsPO>();

            for (int i = 0; i < mobiles.length; i++) {
                SmsPO sms = new SmsPO();
                sms.setState(Config.STATE_CURRENT);
                sms.setType(SmsType.TYPE_IDENTIFY_CODE);
                // 元宵节
                // buildMessage
                // sms.setContent("企业内部提醒：财务李昕骏向开普乐科技有限公司支付100元，状态成功，代码34565432213445");
                String content = smsService.buildMessage("system.oa.sms.template.t1", "李昕骏", "测试公司", "120.00", "已提交", "123");

                sms.setContent(content);
                sms.setReceiverMobile(mobiles[i]);

                list.add(sms);
            }



            UserPO user = new UserPO();
            user.setId("0");

            smsService.send(list, user, conn);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                conn.close();
            }
        }

    }

    public int sendSms4ValidateGuestMobile(String mobile, String code, Connection conn) throws Exception {
        Integer type = SmsType.TYPE_IDENTIFY_CODE;
        String subject = "获取动态码";
        String content = Config.getSystemVariable("web.code.view.content.before") + code
                + Config.getSystemVariable("web.code.view.content.after");
        String signature = Config.getSystemConfig("system.oa.sms.identityCode.signature");

        return this.insertSMS("0000", "游客验证码发送", mobile, subject, content, signature, type, conn);
    }


    public int sendSms4OrderConfirm(String orderId, Connection conn) throws Exception {

        OrderVO orderVO = orderDao.getOrderVOByOrderId(orderId, conn);

        if (orderVO != null && !StringUtils.isEmpty(orderVO.getReferralCode())) {

            UserPO user = userDao.loadByReferralCode(orderVO.getReferralCode(), conn);


            String smsTemplate = Config.getSystemConfig("system.sms.template.order_confirm");

            List<KVObject> values = new ArrayList<KVObject>();
            values.add(new KVObject("customerName", orderVO.getCustomerName()));
            values.add(new KVObject("productionName", orderVO.getProductionName()));
            values.add(new KVObject("helpURL", Config.getSystemConfig("system.url.order.upload_info")));

            String sms = StringUtils.fill(smsTemplate, values);

            if (!StringUtils.isEmpty(sms)) {
                String signature = Config.getSystemConfig("system.oa.sms.identityCode.signature");
                this.insertSMS("0000", "游客验证码发送", user.getMobile(), "兑付短信", sms, signature, SmsType.TYPE_IDENTIFY_CODE, conn);
            }

        }



        return 0;
    }

    public int sendSms4PaymentPlan(String paymentPlanId, Connection conn) throws Exception {

        PaymentPlanVO paymentPlanVO = paymentPlanDao.loadPaymentPlanVO(paymentPlanId, conn);
        if (paymentPlanVO == null) {
            MyException.newInstance("无法找到兑付计划", "paymentPlanId=" + paymentPlanId).throwException();
        }


        List<UserPO> listUserPO = userDao.listUserPO(paymentPlanVO.getCustomerId(), conn);
        String mobile = "";
        if (listUserPO == null || listUserPO.size() == 0) {
            MyException.newInstance("无法找到对应理财经理", "customerId=" + paymentPlanVO.getCustomerId()).throwException();
        }

        mobile = listUserPO.get(0).getMobile();

        if (StringUtils.isEmpty(mobile)) {
            MyException.newInstance("无法获得手机号", "userId=" + listUserPO.get(0).getId()).throwException();
        }


        String smsTemplate = Config.getSystemConfig("system.sms.template.t2");

        List<KVObject> values = new ArrayList<KVObject>();
        values.add(new KVObject("customerName", paymentPlanVO.getCustomerName()));
        values.add(new KVObject("productionName", paymentPlanVO.getProductionName()));
        values.add(new KVObject("principalMoney", MoneyUtils.format2String(paymentPlanVO.getPaiedPrincipalMoney())));
        values.add(new KVObject("profitMoney", MoneyUtils.format2String(paymentPlanVO.getPaiedProfitMoney())));
        values.add(new KVObject("paymentTime", paymentPlanVO.getPaiedPaymentTime()));

        String sms = StringUtils.fill(smsTemplate, values);

        if (!StringUtils.isEmpty(sms)) {
            String signature = Config.getSystemConfig("system.oa.sms.identityCode.signature");
            this.insertSMS("0000", "游客验证码发送", mobile, "兑付短信", sms, signature, SmsType.TYPE_IDENTIFY_CODE, conn);
        }

        return 1;
    }

    public String buildMessage(String templateId, String ... content) throws Exception {

        String template = Config.getSystemConfig(templateId);

        if (StringUtils.isEmpty(template)) {
            MyException.newInstance("无法找到短信模版", "templateId="+templateId).throwException();
        }

        String message = String.format(template, content);

        return message;
    }


    /**
     * 转换返回值类型为UTF-8格式.
     * @param is
     * @return
     */
    public static String convertStreamToString(InputStream is) {
        StringBuilder sb1 = new StringBuilder();
        byte[] bytes = new byte[4096];
        int size = 0;

        try {
            while ((size = is.read(bytes)) > 0) {
                String str = new String(bytes, 0, size, "UTF-8");
                sb1.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb1.toString();
    }


    public static String buildProductCreatedSms(String identifyCodeSmsTemplate, Map<String, String> values) throws Exception {

        identifyCodeSmsTemplate = "{customer.firstName}{customer.sexName}，您好！您关注的【{product.name}】产品将于{product.valueDate}成立，收益率为{product.expectedYield}，敬请留意。";

        String smsContent = identifyCodeSmsTemplate;
        if (values != null) {
            Iterator<String> itKeys = values.keySet().iterator();
            while (itKeys.hasNext()) {
                String key = itKeys.next();
                String value = values.get(key);

                if (!StringUtils.isEmpty(smsContent)) {
                    smsContent = smsContent.replaceAll(key, value);
                }
            }
        }

        return smsContent;
    }


    public int send(List<SmsPO> smses, UserPO user, Connection conn) throws Exception {
        int count = 0;

        for (int i = 0; i < smses.size(); i++) {
            SmsPO sms = smses.get(i);


            String url = this.buildUrl(ACTION_SEND, sms.getType());

            if (StringUtils.isEmpty(url)) {
//                MyLog.info("此短信暂不发送");
//                MyLog.info(sms);
                continue;
            }


            String responseContent = this.doSend(sms.getReceiverMobile(), sms.getContent(), sms.getSignature());

            SmsResponsePO smsResponse = SmsResponsePO.getInstanceFromDefaultServer(responseContent);

            LogPO log = new LogPO();
            log.setName("短信日志");
            log.setMachineMessage("发送短信【"+sms.getReceiverMobile()+"】，服务器返回【"+responseContent+"】，内容：" + sms.getContent());
            log.setOperatorId(user.getId());
            log.setOperateTime(TimeUtils.getNow());
            logDao.save(log, conn);

            // 改变短信发送状态
            if (smsResponse.getCount() == 1) {
                sms.setFeedbackStatus(SmsStatus.STATUS_SENT_CONFIRM);
            }
            else {
                sms.setFeedbackStatus(SmsStatus.STATUS_SENT_FAILED);
            }
            sms.setFeedbackTime(TimeUtils.getNow());
            count += MySQLDao.insertOrUpdate(sms, conn);
            System.out.println("成功发送短信条数：" + count);
            Thread.sleep(2000);
        }

        return count;
    }

    private String doSend(String mobile, String content, String sign) throws Exception {

        // 创建StringBuffer对象用来操作字符串
        StringBuffer sb = new StringBuffer("");

        sb.append(Config.getSystemConfig("system.oa.sms.identityCode.url")).append("?");

        // 向StringBuffer追加用户名
        sb.append("name=").append(Config.getSystemConfig("system.oa.sms.identityCode.userName"));

        // 向StringBuffer追加密码（登陆网页版，在管理中心--基本资料--接口密码，是28位的）
        sb.append("&pwd=").append(Config.getSystemConfig("system.oa.sms.identityCode.password"));

        // 向StringBuffer追加手机号码
        sb.append("&mobile="+mobile);

        // 向StringBuffer追加消息内容转URL标准码
        sb.append("&content="+URLEncoder.encode(content,"UTF-8"));

        //追加发送时间，可为空，为空为及时发送
        sb.append("&stime=");

        //加签名
        sb.append("&sign="+ URLEncoder.encode(sign, "UTF-8"));

        //type为固定值pt  extno为扩展码，必须为数字 可为空
        sb.append("&type=pt&extno=");
        // 创建url对象
        //String temp = new String(sb.toString().getBytes("GBK"),"UTF-8");
        System.out.println("sb:"+sb.toString());
        URL url = new URL(sb.toString());

        // 打开url连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // 设置url请求方式 ‘get’ 或者 ‘post’
        connection.setRequestMethod("POST");

        // 发送
        InputStream is =url.openStream();

        //转换返回值
        String returnStr = convertStreamToString(is);

        // 返回结果为‘0，20140009090990,1，提交成功’ 发送成功   具体见说明文档
        System.out.println(returnStr);
        // 返回发送结果

        return returnStr;
    }


    private  String buildUrl(String action, int smsType) {

        String url = "";

        try {
            if (smsType == SmsType.TYPE_IDENTIFY_CODE) {
                url = Config.getSystemConfig("system.oa.sms.identityCode.url") + "/smshttp?act="+action+"&unitid="+ Config.getSystemConfig("system.oa.sms.identityCode.unitId")+"&username="+Config.getSystemConfig("system.oa.sms.identityCode.userName") + "&passwd="+Config.getSystemConfig("system.oa.sms.identityCode.password") + "&";
            }
        }
        catch (Exception e) {
            LogService.info("发送短信失败【"+e.getMessage()+"】", this.getClass());
        }

        return url;
    }


    /**
     * 添加或修改数据，并修改数据状态
     * @param sms
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int insertOrUpdate(SmsPO sms, UserPO user, Connection conn) throws Exception{
        int count = 0;
        // 新增
        if (sms.getId().equals("")) {
            sms.setSid(MySQLDao.getMaxSid("System_Sms", conn));
            sms.setId(IdUtils.getUUID32());
            sms.setState(Config.STATE_CURRENT);
            sms.setOperatorId(user.getId());
            sms.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(sms, conn);
        }
        // 更新
        else {
            SmsPO temp = new SmsPO();
            temp.setSid(sms.getSid());
            temp = MySQLDao.load(temp, SmsPO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                sms.setSid(MySQLDao.getMaxSid("System_Sms", conn));
                sms.setState(Config.STATE_CURRENT);
                sms.setOperatorId(user.getId());
                sms.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(sms, conn);
            }
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    /**
     * 根据制定的ID获取数据
     * @param id
     * @return
     * @throws Exception
     */
    public SmsPO loadSmsPO(String id) throws Exception{
        SmsPO po = new SmsPO();

        po.setId(id);
        po.setState(Config.STATE_CURRENT);
        po = MySQLDao.load(po, SmsPO.class);

        return po;
    }

    /**
     * 根据条改编数据的状态
     * @param sms
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int delete(SmsPO sms, UserPO user, Connection conn) throws Exception {
        int count = 0;

        sms.setState(Config.STATE_CURRENT);
        sms = MySQLDao.load(sms, SmsPO.class);
        sms.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(sms, conn);
        if (count == 1) {
            sms.setSid(MySQLDao.getMaxSid("System_Sms", conn));
            sms.setState(Config.STATE_DELETE);
            sms.setOperateTime(TimeUtils.getNow());
            sms.setOperatorId(user.getId());
            count = MySQLDao.insert(sms, conn);
        }

        if (count != 1) {
            throw new Exception("删除失败");
        }

        return count;
    }

    /**
     * 创建人：张舜清
     * 时间：2015年9月2日11:51:25
     *
     * @param id
     * @param name
     * @param mobile
     * @param subject
     * @param content
     * @param type 短信类型
     * @param conn
     * @return
     * @throws Exception
     */
    public int insertSMS (String id,String name,String mobile,String subject,String content, String signature, Integer type,Connection conn)throws Exception {
        int count = 0;
        SmsPO sms = new SmsPO();
        sms.setOperatorId(Config.Admin);
        sms.setOperateTime(TimeUtils.getNow());
        sms.setReceiverId(id);
        sms.setReceiverName(name);
        sms.setReceiverMobile(mobile);
        sms.setSendTime(TimeUtils.getNow());
        sms.setSubject(subject);
        sms.setContent(content);
        sms.setSignature(signature);
        sms.setType(type);
        sms.setFeedbackStatus(SmsStatus.STATUS_WAIT);
        count = MySQLDao.insertOrUpdate(sms,conn);
        if (count != 1){
            MyException.deal(new Exception("发送短信失败！"));
        }
        return count;
    }

}
