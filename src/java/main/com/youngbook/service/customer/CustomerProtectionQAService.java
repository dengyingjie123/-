package com.youngbook.service.customer;

import com.youngbook.common.KVObject;
import com.youngbook.common.MyException;
import com.youngbook.common.Pager;
import com.youngbook.common.ReturnObject;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.customer.ICustomerPersonalDao;
import com.youngbook.entity.po.KVPO;
import com.youngbook.entity.po.customer.CustomerPersonalPO;
import com.youngbook.entity.po.customer.CustomerSafetyQAPO;
import com.youngbook.entity.vo.customer.CustomerSafetyQAVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Component("customerProtectionQAService")
public class CustomerProtectionQAService {

    @Autowired
    ICustomerPersonalDao customerPersonalDao;

    /**
     * 获取安全保护问题
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月13日
     *
     * @param conn
     * @throws Exception
     */
    public Pager getQuestions(Connection conn) throws Exception {

        // 获取 KV 组名的数据库字段
        String groupNameField = "ProtectionQuestionGroup";

        // 组装查询参数
        List<KVObject> kvObjects = new ArrayList<KVObject>();
        KVObject kvObject = new KVObject();
        kvObject.setIndex(1);
        kvObject.setValue(groupNameField);
        kvObjects.add(kvObject);

        // 查询并返回
        String sql = "select kv.id, kv.k, kv.v from system_kv kv where kv.groupName = ?";
        Pager pager = MySQLDao.search(sql, kvObjects, new KVPO(), new ArrayList<KVObject>(), 1, Integer.MAX_VALUE, null, conn);
        return pager;

    }

    /**
     * 获取客户的安全问题和答案
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月13日
     *
     * @param customerId
     * @param conn
     * @return
     * @throws Exception
     */
    public Pager getQAByCustomerId(String customerId, Connection conn) throws Exception {

        // 组装查询参数
        List<KVObject> kvObjects = new ArrayList<KVObject>();
        KVObject kvObject = new KVObject();
        kvObject.setIndex(1);
        kvObject.setValue(customerId);
        kvObjects.add(kvObject);

        // 查询并返回
        String sql = "SELECT qa.id, qa.Q1Id, kv1.v AS q1Content, qa.A1, qa.Q2Id, kv2.v AS q2Content, qa.A2, qa.Q3Id, kv3.v AS q3Content, qa.A3 FROM crm_customersafetyqa qa LEFT JOIN system_kv kv1 ON kv1.k = qa.Q1Id LEFT JOIN system_kv kv2 ON kv2.k = qa.Q2Id LEFT JOIN system_kv kv3 ON kv3.k = qa.Q3Id WHERE qa.state = 0 AND qa.customerId = ?";
        Pager pager = MySQLDao.search(sql, kvObjects, new CustomerSafetyQAVO(), new ArrayList<KVObject>(), 1, 1, null, conn);
        return pager;

    }

    /**
     * 保存客户设置的安全密保问题和答案
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月14日
     *
     * @param customerId
     * @param q1
     * @param a1
     * @param q2
     * @param a2
     * @param q3
     * @param a3
     * @param conn
     * @return
     * @throws Exception
     */
    public Integer saveQA(String customerId, String q1, String a1, String q2, String a2, String q3, String a3, Connection conn) throws Exception {

        Integer count = 0;
        String now = TimeUtils.getNow();

        CustomerPersonalPO personalPO = customerPersonalDao.loadByCustomerPersonalId(customerId, conn);
        if(personalPO == null) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "没有查询到该客户").throwException();
        }

        // 组装查询参数
        List<KVObject> kvObjects = new ArrayList<KVObject>();
        KVObject kvObject = new KVObject();
        kvObject.setIndex(1);
        kvObject.setValue(customerId);
        kvObjects.add(kvObject);

        // 查询并获取数据
        String sql = "select * from crm_customersafetyqa qa where qa.customerId = ? and state = " + Config.STATE_CURRENT + "";
        List<CustomerSafetyQAPO> list = MySQLDao.search(sql, kvObjects, CustomerSafetyQAPO.class, new ArrayList<KVObject>(), conn);

        // 如果没有，新增一条数据
        if(list.size() == 0) {
            CustomerSafetyQAPO po = new CustomerSafetyQAPO();
            po.setSid(MySQLDao.getMaxSid("crm_customersafetyqa", conn));
            po.setId(IdUtils.getUUID32());
            po.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
            po.setOperateTime(now);
            po.setState(Config.STATE_CURRENT);
            po.setCustomerId(customerId);
            po.setQ1Id(q1);
            po.setQ2Id(q2);
            po.setQ3Id(q3);
            po.setA1(a1);
            po.setA2(a2);
            po.setA3(a3);
            count = MySQLDao.insert(po, conn);
        }

        // 如果存在，则修改数据
        else if(list.size() == 1) {
            CustomerSafetyQAPO result = list.get(0);
            result.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
            result.setOperateTime(now);
            result.setQ1Id(q1);
            result.setQ2Id(q2);
            result.setQ3Id(q3);
            result.setA1(a1);
            result.setA2(a2);
            result.setA3(a3);
            count = MySQLDao.insertOrUpdate(result, conn);
        }

        // 多余 1 条，数据有问题，报错
        else {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "您的数据有误，请稍候重试").throwException();
        }

        return count;

    }

    /**
     * 校验安全问题是否存在于系统中
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015年12月14日
     *
     * @param questionId
     * @param conn
     * @return
     * @throws Exception
     */
    public Boolean isQuestionExist(String questionId, Connection conn) throws Exception {

        // 获取 KV 组名的数据库字段
        String groupNameField = "ProtectionQuestionGroup";

        // 组装查询参数
        List<KVObject> kvObjects = new ArrayList<KVObject>();
        KVObject kvObject = new KVObject();
        kvObject.setIndex(1);
        kvObject.setValue(questionId);
        kvObjects.add(kvObject);

        // 查询
        String sql = "select * from system_kv kv where kv.groupName = '" + groupNameField + "' and kv.k = ?";
        List<KVPO> list = MySQLDao.search(sql, kvObjects, KVPO.class, new ArrayList<KVObject>(), conn);

        // 返回
        return list.size() == 1 ? true : false;

    }

}
