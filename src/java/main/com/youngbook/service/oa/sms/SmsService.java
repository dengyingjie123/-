package com.youngbook.service.oa.sms;

/**
 * Created by Jepson on 2015/7/3.
 */
import com.youngbook.common.MyException;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.production.ProductionPO;
import com.youngbook.entity.po.system.SmsPO;
import com.youngbook.entity.po.system.SmsStatus;
import com.youngbook.entity.po.system.SmsType;
import com.youngbook.service.BaseService;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 *
 * 创建一个SmsService 类 继承BaseService类
 * @author Codemaker
 */

public class SmsService extends BaseService {


    public int sendSms4ValidateGuestMobile(String mobile, String code, Connection conn) throws Exception {
        Integer type = SmsType.TYPE_IDENTIFY_CODE;
        String subject = "获取动态码";
        String content = Config.getSystemVariable("web.code.view.content.before") + code
                + Config.getSystemVariable("web.code.view.content.after");

        return this.insertSMS("0000", "游客验证码发送", mobile, subject, content, type, conn);
    }

    /**
     * 新建产品时，发送短信
     *
     * 接收短信者是销售团队负责人
     *
     * @param production
     * @param conn
     * @return
     * @throws Exception
     */
    public int sendSms4CreateProduction(ProductionPO production, Connection conn) throws Exception {



        StringBuffer sqlDB = new StringBuffer();
        sqlDB.append(" SELECT DISTINCT ");
        sqlDB.append(" u.id, ");
        sqlDB.append(" u.`name` AS saleManName, ");
        sqlDB.append(" u.mobile ");
        sqlDB.append(" FROM ");
        sqlDB.append(" system_user u, ");
        sqlDB.append(" crm_saleman s, ");
        sqlDB.append(" crm_saleman_salemangroup sg ");
        sqlDB.append(" WHERE ");
        sqlDB.append(" u.id = s.UserId ");
        sqlDB.append(" AND u.state = 0 ");
        sqlDB.append(" AND s.State = 0 ");
        sqlDB.append(" AND u.id = sg.saleManId ");
        sqlDB.append(" AND u.PositionTypeId = '" + Config.PostTypeToSaleMan + "' ");
        sqlDB.append(" AND sg.saleManStatus = '" + Config.SaleManGroupPrincipal + "' ");
        sqlDB.append(" AND u.`name` != '" + Config.Admin + "' ");
        List<Map<String, Object>> saleManList = MySQLDao.query(sqlDB.toString(), conn);
        if (saleManList == null || saleManList.size() == 0) {
            MyException.deal(new Exception("获取销售组负责人失败！"));

        }
        SmsService smsService = new SmsService();
        SmsPO sms = new SmsPO();
        SmsType smsType = new SmsType();
        String subject = "添加或者更新产品";
        String content = production.getName();
        for (Map<String, Object> map : saleManList) {
            String id = map.get("Id").toString();
            String name = map.get("name").toString();
            String mobile = map.get("mobile").toString();
            // 设置短信类型
            Integer type = smsType.TYPE_PRODUCT_MESSAGE;
            smsService.insertSMS(id,name,mobile,subject,content,type, conn);
        }


        return 0;
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
    public int insertSMS (String id,String name,String mobile,String subject,String content,Integer type,Connection conn)throws Exception{
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
        sms.setType(type);
        sms.setFeedbackStatus(SmsStatus.STATUS_WAIT);
        count = MySQLDao.insertOrUpdate(sms,conn);
        if (count != 1){
            MyException.deal(new Exception("发送短信失败！"));
        }
        return count;
    }
}
