package com.youngbook.service.customer;

import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.config.AesEncrypt;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.customer.ICustomerCertificateDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.customer.CustomerAuthenticationStatusPO;
import com.youngbook.entity.po.customer.CustomerCertificatePO;
import com.youngbook.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 10/18/14
 * Time: 5:04 PM
 * To change this template use File | Settings | File Templates.
 */
@Component("customerCertificateService")
public class CustomerCertificateService extends BaseService {

    @Autowired
    ICustomerCertificateDao customerCertificateDao;

    public int delete(CustomerCertificatePO customerCertificate, String userId, Connection conn) throws Exception {
        return  customerCertificateDao.delete(customerCertificate, userId, conn);
    }

    public Integer insertOrUpdateCertificate(String customerId, String type, String number, UserPO operator, Connection conn) throws Exception {
        return customerCertificateDao.insertOrUpdateCertificate(customerId, type, number, operator.getId(), conn);
    }

    public CustomerCertificatePO loadByCustomerId(String customerId,Connection conn) throws Exception {
        return customerCertificateDao.loadByCustomerId(customerId, conn);
    }

    /**
     * 通过加密后的证件号码查询
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年5月20日
     *
     * @param AESString
     * @return
     * @throws Exception
     */
    public List<CustomerCertificatePO> listByCardAesString(String AESString, String cardCode, Connection conn) throws Exception {
        String sql = "select * from crm_customercertificate c where c.state = 0 and c.number = '" + AESString + "' and c.name = '" + cardCode + "'" ;
        List<CustomerCertificatePO> list = MySQLDao.query(sql, CustomerCertificatePO.class, new ArrayList<KVObject>(), conn);
        return list;
    }

    /**
     * 创建人：姚章鹏
     * 时间：2015年12月10日
     * 内容：获取客户身份证信息
     *
     * @param id
     * @param conn
     * @return
     * @throws Exception
     */
    public CustomerCertificatePO getCustomerCertificate(String id, Connection conn) throws Exception {
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("  SELECT ");
        sbSQL.append(" cc.id,  ");
        sbSQL.append(" cp. NAME, ");
        sbSQL.append("  cc.CustomerId, ");
        sbSQL.append("  cc.Number ");
        sbSQL.append(" FROM ");
        sbSQL.append("  crm_customercertificate cc, ");
        sbSQL.append("  crm_customerpersonal cp ");
        sbSQL.append(" WHERE ");
        sbSQL.append("cc.state = 0 ");
        sbSQL.append("AND cp.state = 0 ");
        sbSQL.append("AND cp.id = cc.customerId ");
        sbSQL.append("AND cc.CustomerId = ? ");
        //创建kv对象
        List<KVObject> parmeters = new ArrayList<KVObject>();
        parmeters = Database.addQueryKVObject(1, id, parmeters);
        //执行查询
        List<CustomerCertificatePO> pager = MySQLDao.search(sbSQL.toString(), parmeters, CustomerCertificatePO.class, null, conn);
        if (pager != null && pager.size() == 1) {
            String number = AesEncrypt.decrypt(pager.get(0).getNumber());
            number = number.replace(number.substring(4, 14), "**********");
            pager.get(0).setNumber(number);
            return pager.get(0);
        }
        return null;
    }



    public Pager list(CustomerCertificatePO customerCertificate, List<KVObject> conditions, HttpServletRequest request) throws Exception {
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        String sql ="SELECT a.sid,a.id,a.CustomerId,b.V Name,a.Number,a.ValidityDate,a.ValidityDateStart,a.ValidityDateEnd,a.AuthenticationInstitution FROM crm_customercertificate a,system_kv b WHERE a.state=0 and b.state=0 and a.name=b.k";
        Pager pager = Pager.query(sql,customerCertificate,conditions,request,queryType);
        return pager;
    }

    /**
     * 通过客户编号和证件类型，查询客户证件对象
     *
     * 作者：李昕骏
     * 时间：2015年11月21日 21:30:34
     *
     * @param customerId 客户编号
     * @param customerCertificateName 证件类型名称，可以通过 Config.getSystemVariable("web.customer.certificate.idcard.kv.v");获得
     * @param conn
     * @return
     * @throws Exception
     */
    public CustomerCertificatePO loadByCustomerId(String customerId, String customerCertificateName, Connection conn) throws Exception {

        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT");
        sbSQL.append("     *");
        sbSQL.append(" FROM");
        sbSQL.append("     crm_customercertificate cc");
        sbSQL.append(" WHERE");
        sbSQL.append("     1 = 1");
        sbSQL.append(" AND cc.state = 0");
        sbSQL.append(" AND cc.CustomerId = ?");
        sbSQL.append(" AND cc.NAME = ?");

        List<KVObject> parameters = new ArrayList<KVObject>();
        parameters = Database.addQueryKVObject(1, customerId, parameters);
        parameters = Database.addQueryKVObject(2, customerCertificateName, parameters);

        List<CustomerCertificatePO> list = MySQLDao.search(sbSQL.toString(), parameters, CustomerCertificatePO.class, null, conn);

        if (list != null && list.size() == 1) {
            return list.get(0);
        }

        return null;
    }

    /**
     *
     * @param customerCertificate
     * @param conn
     * @return
     * @throws Exception
     */
    public int insertOrUpdate(CustomerCertificatePO customerCertificate, String userId, Connection conn) throws Exception{
        int count = 0;
        //aes加密身份证号
        if(null != customerCertificate && !StringUtils.isEmpty(customerCertificate.getNumber()))
        {
            customerCertificate.setNumber(AesEncrypt.encrypt(customerCertificate.getNumber()));
        }
        // 新增
        if (customerCertificate.getId().equals("")) {
            customerCertificate.setSid(MySQLDao.getMaxSid("crm_customerCertificate", conn));
            customerCertificate.setId(IdUtils.getUUID32());
            customerCertificate.setState(Config.STATE_CURRENT);
            customerCertificate.setOperatorId(userId);
            customerCertificate.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(customerCertificate, conn);
        }
        // 更新
        else {
            CustomerCertificatePO temp = new CustomerCertificatePO();

            if (customerCertificate.getSid() != Integer.MAX_VALUE) {
                temp.setSid(customerCertificate.getSid());
            }

            if (!StringUtils.isEmpty(customerCertificate.getId())) {
                temp.setId(customerCertificate.getId());
                temp.setState(Config.STATE_CURRENT);
            }

            temp = MySQLDao.load(temp, CustomerCertificatePO.class);
            if (temp == null) {
                throw new Exception("无法找到对应的客户认证信息");
            }

            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                customerCertificate.setSid(MySQLDao.getMaxSid("crm_customerCertificate", conn));
                customerCertificate.setState(Config.STATE_CURRENT);
                customerCertificate.setOperatorId(userId);
                customerCertificate.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(customerCertificate, conn);
            }
        }

        if (count != 1) {
            throw new Exception("数据库异常");
        }

        return count;
    }

    /**
     * 姚章鹏 修改
     * 修改个人客户选驾驶证的时候，号码的验证还是身份证的验证
     * date 6/17/15
     * 检查证件号码
     * @param number
     * @return
     * @throws Exception
     */
    public boolean hasBeenTaken(String customerId, String number, String name, Connection conn) throws Exception {

        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT");
        sbSQL.append("     cc.*");
        sbSQL.append(" FROM");
        sbSQL.append("     crm_customercertificate cc,");
        sbSQL.append("     crm_customerpersonal c");
        sbSQL.append(" WHERE");
        sbSQL.append("     1 = 1");
        sbSQL.append(" AND cc.state = 0");
        sbSQL.append(" AND c.state = 0");
        sbSQL.append(" AND c.id = cc.CustomerId");
        sbSQL.append(" AND cc.Number = ?");
        sbSQL.append(" AND cc.`Name` = ?");
        sbSQL.append(" AND c.id = ?");

        List<KVObject> parameters = new ArrayList<KVObject>();
        parameters = Database.addQueryKVObject(1, number, parameters);
        parameters = Database.addQueryKVObject(2, name, parameters);
        parameters = Database.addQueryKVObject(3, customerId, parameters);

        List<CustomerCertificatePO> list = MySQLDao.search(sbSQL.toString(), parameters, CustomerCertificatePO.class, null, conn);


        boolean hasBeenTaken = false;
        if (list != null && list.size() > 0) {
            hasBeenTaken = true;
        }

        return hasBeenTaken;


    }

    public Integer insertOrUpdateCertificate(String customerId, String type, String number, String userId, Connection conn) throws Exception {
        return customerCertificateDao.insertOrUpdateCertificate(customerId, type, number, userId, conn);
    }



    /**
     * 获取当前用户的手机认证状态
     * 作者：周海鸿
     * 时间：2015-7-27
     *
     * @param customerId
     * @param conn
     * @return
     * @throws Exception
     */
    public int  getMobileStatus(String customerId,Connection conn) throws  Exception{
        //创建SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT");
        sql.append(" *");
        sql.append(" FROM");
        sql.append(" crm_customerauthenticationstatus");
        sql.append(" WHERE");
        sql.append(" 1=1");
        sql.append(" AND State = 0");
        sql.append(" AND CustomerId = '"+Database.encodeSQL(customerId)+"'");
        List<CustomerAuthenticationStatusPO> list = MySQLDao.query(sql.toString(), CustomerAuthenticationStatusPO.class, null, conn);

        //查询到的数据列表位一条
        if(list.size() == 1){
            //返回手机认证状态
            return list.get(0).getMobileStatus();
        }
        //获取的数据不止一条
        return -1;

    }





}