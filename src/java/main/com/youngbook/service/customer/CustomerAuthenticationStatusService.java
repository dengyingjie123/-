package com.youngbook.service.customer;

import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.customer.ICustomerAuthenticationStatusDao;
import com.youngbook.entity.po.KVPO;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.customer.CustomerAuthenticationStatus;
import com.youngbook.entity.po.customer.CustomerAuthenticationStatusPO;
import com.youngbook.entity.po.customer.CustomerCertificatePO;
import com.youngbook.entity.po.customer.CustomerPersonalPO;
import com.youngbook.entity.vo.customer.CustomerAuthenticationStatusVO;
import com.youngbook.entity.wvo.ProtectionQuestionWVO;
import com.youngbook.entity.wvo.customer.CustomerSafetyQAWVO;
import com.youngbook.service.BaseService;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Component("customerAuthenticationStatusService")
public class CustomerAuthenticationStatusService extends BaseService {

    @Autowired
    CustomerPersonalService customerPersonalService;

    @Autowired
    ICustomerAuthenticationStatusDao customerAuthenticationStatusDao;

    public int insertOrUpdate(CustomerAuthenticationStatusPO customerAuthenticationStatus, UserPO user, Connection conn) throws Exception {
        return customerAuthenticationStatusDao.insertOrUpdate(customerAuthenticationStatus, user, conn);
    }

    public CustomerAuthenticationStatusPO loadByCustomerId(String id, Connection conn) throws Exception {
        return customerAuthenticationStatusDao.loadByCustomerId(id, conn);
    }

    public Integer saveAuthenticationStatus(String customerId, Integer authenticationType, Connection conn) throws Exception {
        return customerAuthenticationStatusDao.saveAuthenticationStatus(customerId, authenticationType, conn);
    }


    public CustomerAuthenticationStatusPO loadAuthStatusByCustomer(String customerId, Connection conn) throws Exception {

        CustomerAuthenticationStatusPO statusPO = null;

        List<KVObject> kv = new ArrayList<KVObject>();
        KVObject customerKV = new KVObject();
        customerKV.setIndex(1);
        customerKV.setValue(customerId);
        kv.add(customerKV);

        String sql = "select * from crm_customerauthenticationstatus status where status.state = 0 and status.customerId = ?";
        List<CustomerAuthenticationStatusPO> statusPOs = MySQLDao.search(sql, kv, CustomerAuthenticationStatusPO.class, new ArrayList<KVObject>(), conn);
        if(statusPOs.size() == 1) {
            statusPO = statusPOs.get(0);
        }

        return statusPO;
    }

    public Integer updateCustomerAccountAuthStatus(String customerId, String operatorId, Connection conn) throws Exception {
        CustomerAuthenticationStatusPO statusPO = this.loadAuthStatusByCustomer(customerId, conn);
        if (statusPO == null) {
            statusPO = new CustomerAuthenticationStatusPO();
            statusPO.setCustomerId(customerId);
        }
        statusPO.setAccountStatus(1);
        statusPO.setAccountTime(TimeUtils.getNow());
        return MySQLDao.insertOrUpdate(statusPO, operatorId, conn);
    }

    public CustomerCertificatePO loadCertificate(String customerId,Connection conn) throws Exception{
        CustomerCertificatePO customerCertificate = new CustomerCertificatePO();
        customerCertificate.setCustomerId(customerId);
        customerCertificate.setState(Config.STATE_CURRENT);
        customerCertificate = MySQLDao.load(customerCertificate, CustomerCertificatePO.class, conn);
        return customerCertificate;
    }

    /**
     * 获取系统定义的安全问题
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-26
     *
     * @param conn
     * @return 适用于交易平台的 json
     * @throws Exception
     */
    public List<ProtectionQuestionWVO> getSystemProtectionQuestions(Connection conn) throws Exception {
        Integer i = 0;

        // 添加默认的“请选择”项
        List<ProtectionQuestionWVO> questions = new ArrayList<ProtectionQuestionWVO>();
        ProtectionQuestionWVO pleaseSelect = new ProtectionQuestionWVO();
        pleaseSelect.setQuestionId(i);
        pleaseSelect.setQuestionContent("请选择");
        questions.add(pleaseSelect);

        // 通过 KV 查询
        String sql = "select * from system_kv kv where kv.groupName = 'ProtectionQuestionGroup' order by kv.orders";
        List<KVPO> list = MySQLDao.query(sql, KVPO.class, new ArrayList<KVObject>(), conn);
        for (KVPO kv : list) {
            i++;
            ProtectionQuestionWVO question = new ProtectionQuestionWVO();
            question.setQuestionId(Integer.parseInt(kv.getK()));
            question.setQuestionContent(kv.getV());
            questions.add(question);
        }
        return questions;
    }

    /**
     * 查询客户安全等级
     *
     * 作者：曾权
     * 内容：创建代码
     * 时间：不明
     *
     * 修改：邓超
     * 内容：优化代码，从 CustomerPersonalService 移动至此
     * 时间：2016年5月18日
     *
     * @param customerId
     * @param conn
     * @return
     */
    public int calSecurtyLevel(String customerId, Connection conn) throws Exception {


        int sum = 0;

        // 判断交易密码是否为空，有交易密安全等级加 25
        if (customerPersonalService.isSetTransactionPassword(customerId, conn)) {
            sum += 25;
        }

        // 查询客户其他认证信息
        CustomerAuthenticationStatusPO statusPO = customerAuthenticationStatusDao.loadByCustomerId(customerId, conn);
        if (statusPO != null) {
            // 计算安全等级
            sum += (statusPO.getAccountStatus() + statusPO.getMobileStatus() + statusPO.getQaStatus()) * 25;
        }
        return sum;
    }

    /**
     * 交易平台请求的 Service，查询用户已经设置好的安全保护问题
     * 前提是网站的 Customer 已经登录
     * 用法：在 status-w-customer 中找到指向该方法的 action 配置，在页面中按配置调用
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-28
     *
     * @param loginUser
     * @param conn
     * @return 适用于交易平台的 json
     * @throws Exception
     * @author 邓超
     */
    public CustomerSafetyQAWVO getCustomerQuestions(CustomerPersonalPO loginUser, Connection conn) throws Exception {
        String sql = "SELECT qa.a1, qa.a2, qa.a3, question0.v q1Content, question1.v q2Content, question2.v q3Content " +
                "FROM crm_customersafetyqa qa " +
                "LEFT JOIN system_kv question0 ON question0.k = qa.q1Id " +
                "LEFT JOIN system_kv question1 ON question1.k = qa.q2Id " +
                "LEFT JOIN system_kv question2 ON question2.k = qa.q3Id " +
                "WHERE qa.state = 0 " +
                "AND qa.customerId = '" + Database.encodeSQL(loginUser.getId()) + "' ";
        CustomerSafetyQAWVO wvo = null;
        List<CustomerSafetyQAWVO> list = MySQLDao.query(sql, CustomerSafetyQAWVO.class, new ArrayList<KVObject>(), conn);
        if (list.size() == 1) {
            wvo = list.get(0);
        }
        return wvo;
    }



    /**
     * 获取数据列表方法
     *
     * 修改：邓超
     * 内容：补充数据
     * 时间：2016年5月18日
     *
     * @param customerAuthenticationStatusVO
     * @param conditions
     * @return
     * @throws Exception
     */
    public Pager list(CustomerAuthenticationStatusVO customerAuthenticationStatusVO, List<KVObject> conditions) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        //sql查询语句
        String sql = "SELECT cas.*, cp.`Name` customerName," +
                " (CASE cas.MobileStatus WHEN 0 THEN '未认证' ELSE '已认证' END) AS mobile_Status, " +
                " (CASE cas.EmailStatus WHEN 0 THEN '未认证' ELSE '已认证' END) AS email_Status, " +
                " (CASE cas.AccountStatus WHEN 0 THEN '未认证' ELSE '已认证' END) AS account_Status, " +
                " (CASE cas.QAStatus WHEN 0 THEN '未认证' ELSE '已认证' END) AS qa_Status," +
                " (CASE cas.VideoStatus WHEN 0 THEN '未认证' ELSE '已认证' END) AS video_Status," +
                " (CASE cas.FaceStatus WHEN 0 THEN '未认证' ELSE '已认证' END) AS face_Status " +
                " FROM CRM_CustomerAuthenticationStatus cas,crm_customerpersonal cp " +
                " WHERE 1 = 1 AND cas.State = 0 AND cp.state = 0 AND cas.CustomerId = cp.id";
        Pager pager = Pager.query(sql, customerAuthenticationStatusVO, conditions, request, queryType);

        return pager;
    }



    /**
     * 删除方法
     * @param customerAuthenticationStatus
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int delete(CustomerAuthenticationStatusPO customerAuthenticationStatus, UserPO user, Connection conn) throws Exception {
        int count = 0;
        customerAuthenticationStatus = MySQLDao.load(customerAuthenticationStatus, CustomerAuthenticationStatusPO.class);
        customerAuthenticationStatus.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(customerAuthenticationStatus, conn);
        if (count == 1) {
            customerAuthenticationStatus.setSid(MySQLDao.getMaxSid("CRM_CustomerAuthenticationStatus", conn));
            customerAuthenticationStatus.setState(Config.STATE_DELETE);
            customerAuthenticationStatus.setOperateTime(TimeUtils.getNow());
            customerAuthenticationStatus.setOperatorId(user.getId());
            count = MySQLDao.insert(customerAuthenticationStatus, conn);
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    /**
     * 获取是树型下拉列表
     * @return
     */
    public JSONArray getStatusTree(){
        CustomerAuthenticationStatus CAStatus = new CustomerAuthenticationStatus();
        JSONArray array = CAStatus.toJsonArray();
        return array;
    }

}
