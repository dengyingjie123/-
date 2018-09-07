package com.youngbook.service.customer;

import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.customer.CustomerAuthenticationCodePO;
import com.youngbook.entity.vo.customer.CustomerAuthenticationCodeVO;
import com.youngbook.service.BaseService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015-4-7.
 */
@Component("authenticationCodeService")
public class AuthenticationCodeService extends BaseService {

    /**
     * 查询列出数据
     * @param authCodeVO
     * @param conditions
     * @param request
     * @return Pager
     * @throws Exception
     */
    public Pager list(CustomerAuthenticationCodeVO authCodeVO, List<KVObject> conditions, HttpServletRequest request) throws Exception {
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        String sql ="select distinct code.sid, code.id, person.name, code.code, code.sendTime, code.expiredTime, code.authenticationTime, code.info, k1.v sendType, k2.v status " +
                "from crm_customerpersonal person, crm_customerauthenticationcode code " +
                "left join system_kv k1 on code.sendType = k1.k " +
                "left join system_kv k2 on code.status = k2.k " +
                "where code.CustomerId = person.id and code.state = 0 ";

        String customerAuthenticationCode_SendTime_Start = request.getParameter("sendTimeStart");
        if (!StringUtils.isEmpty(customerAuthenticationCode_SendTime_Start)) {
            sql += " and code.sendTime >= '"+Database.encodeSQL(customerAuthenticationCode_SendTime_Start) + "'";
        }
        String customerAuthenticationCode_SendTime_End = request.getParameter("sendTimeEnd");
        if (!StringUtils.isEmpty(customerAuthenticationCode_SendTime_End)) {
            sql += " and code.sendTime <= '"+Database.encodeSQL(customerAuthenticationCode_SendTime_End) + "'";
        }
        String customerAuthenticationCode_ExpiredTime_Start = request.getParameter("expiredTimeStart");
        if (!StringUtils.isEmpty(customerAuthenticationCode_ExpiredTime_Start)) {
            sql += " and code.expiredTime >= '"+Database.encodeSQL(customerAuthenticationCode_ExpiredTime_Start) + "'";
        }
        String customerAuthenticationCode_ExpiredTime_End = request.getParameter("expiredTimeEnd");
        if (!StringUtils.isEmpty(customerAuthenticationCode_ExpiredTime_End)) {
            sql += " and code.expiredTime <= '"+Database.encodeSQL(customerAuthenticationCode_ExpiredTime_End) + "'";
        }
        String customerAuthenticationCode_AuthenticationTime_Start = request.getParameter("authenticationTimeStart");
        if (!StringUtils.isEmpty(customerAuthenticationCode_AuthenticationTime_Start)) {
            sql += " and code.authenticationTime >= '"+Database.encodeSQL(customerAuthenticationCode_AuthenticationTime_Start) + "'";
        }
        String customerAuthenticationCode_AuthenticationTime_End = request.getParameter("authenticationTimeEnd");
        if (!StringUtils.isEmpty(customerAuthenticationCode_AuthenticationTime_End)) {
            sql += " and code.authenticationTime <= '"+Database.encodeSQL(customerAuthenticationCode_AuthenticationTime_End) + "'";
        }
        String info = request.getParameter("info");
        if (!StringUtils.isEmpty(info)) {
            sql += " and code.info like '%" + Database.encodeSQL(info) + "%'";
        }
        String status = request.getParameter("status");
        if (!StringUtils.isEmpty(status)) {
            sql += " and k2.v like '%" + Database.encodeSQL(status) + "%'";
        }
        String sendType = request.getParameter("sendType");
        if (!StringUtils.isEmpty(sendType)) {
            sql += " and k1.v like '%" + Database.encodeSQL(sendType) + "%'";
        }
        String name = request.getParameter("name");
        if (!StringUtils.isEmpty(name)) {
            sql += " and person.name like '%" + Database.encodeSQL(name) + "%'";
        }
        Pager pager = Pager.query(sql, authCodeVO, conditions, request, queryType);
        return pager;
    }

    /**
     * 新增或修改数据
     * @param customerAuthenticationCode
     * @param user
     * @param conn
     * @return int
     * @throws Exception
     */
    public int insertOrUpdate(CustomerAuthenticationCodePO customerAuthenticationCode, UserPO user, Connection conn) throws Exception{
        int count = 0;
        // 新增
            if (customerAuthenticationCode.getId().equals("")) {
                customerAuthenticationCode.setSid(MySQLDao.getMaxSid("CRM_CustomerAuthenticationCode", conn));
                customerAuthenticationCode.setId(IdUtils.getUUID32());
                customerAuthenticationCode.setState(Config.STATE_CURRENT);
                customerAuthenticationCode.setOperatorId(user.getId());
                customerAuthenticationCode.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(customerAuthenticationCode, conn);
            }
            // 更新
            else {
                CustomerAuthenticationCodePO temp = new CustomerAuthenticationCodePO();
                temp.setSid(customerAuthenticationCode.getSid());
                temp = MySQLDao.load(temp, CustomerAuthenticationCodePO.class);
                temp.setState(Config.STATE_UPDATE);
                count = MySQLDao.update(temp, conn);
                if (count == 1) {
                    customerAuthenticationCode.setSid(MySQLDao.getMaxSid("CRM_CustomerAuthenticationCode", conn));
                    customerAuthenticationCode.setState(Config.STATE_CURRENT);
                    customerAuthenticationCode.setOperatorId(user.getId());
                    customerAuthenticationCode.setOperateTime(TimeUtils.getNow());
                    count = MySQLDao.insert(customerAuthenticationCode, conn);
                }
            }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    /**
     * 载入一条数据
     * @param id
     * @return CustomerAuthenticationCodePO
     * @throws Exception
     */
    public CustomerAuthenticationCodePO loadCustomerAuthenticationCodePO(String id) throws Exception{
        CustomerAuthenticationCodePO po = new CustomerAuthenticationCodePO();
        po.setId(id);
        po.setState(Config.STATE_CURRENT);
        po = MySQLDao.load(po, CustomerAuthenticationCodePO.class);
        return po;
    }

    /**
     * 删除数据
     * @param customerAuthenticationCode
     * @param user
     * @param conn
     * @return int
     * @throws Exception
     */
    public int delete(CustomerAuthenticationCodePO customerAuthenticationCode, UserPO user, Connection conn) throws Exception {
        int count = 0;
        customerAuthenticationCode.setState(Config.STATE_CURRENT);
        customerAuthenticationCode = MySQLDao.load(customerAuthenticationCode, CustomerAuthenticationCodePO.class);
        customerAuthenticationCode.setState(Config.STATE_DELETE);
        count = MySQLDao.update(customerAuthenticationCode, conn);
        if (count != 1) {
            throw new Exception("删除失败");
        }
        return count;
    }

    /**
     * 删除之前的认证码
     * @param codePO
     * @param conn
     * @return
     * @throws Exception
     */
    public int delete4W(CustomerAuthenticationCodePO codePO, Connection conn) throws Exception {
        // String sql = "select * from crm_customerauthenticationcode code where code.mobile = '" + Database.encodeSQL(codePO.getMobile()) + "' and code.state = 0";
        String sql = "select * from crm_customerauthenticationcode code where code.mobile=? and code.state = 0";
        List<KVObject> parameters = new ArrayList<KVObject>();
        KVObject parameter = new KVObject(1, codePO.getMobile());
        parameters.add(parameter);


        List<CustomerAuthenticationCodePO> codes = MySQLDao.search(sql, parameters, CustomerAuthenticationCodePO.class, null, conn);
        int count = 0;
        for(CustomerAuthenticationCodePO code : codes) {
            count = MySQLDao.deletePhysically(code, conn);
        }
        return count;
    }

    /**
     * 删除以前的客户认证码
     * @param mobile
     * @param conn
     * @throws Exception
     */
    public void deleteAuthentucatuibCode(String mobile,Connection conn) throws Exception {
        AuthenticationCodeService authCodeService = new AuthenticationCodeService();
        CustomerAuthenticationCodePO authCode = new CustomerAuthenticationCodePO();
        authCode.setMobile(mobile);
        delete4W(authCode, conn);
    }

}
