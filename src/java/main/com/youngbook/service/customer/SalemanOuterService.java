package com.youngbook.service.customer;

import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.KVPO;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.production.OrderPO;
import com.youngbook.entity.po.production.OrderStatus;
import com.youngbook.entity.vo.system.UserVO;
import com.youngbook.service.BaseService;
import com.youngbook.service.system.KVService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Component("salemanOuterService")
public class SalemanOuterService extends BaseService {

    /**
     * 获取对外销售人员的佣金统计
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年3月31日
     *
     * @param salemanId
     * @param conn
     * @return
     * @throws Exception
     */
    public UserVO getCommissionCount(String salemanId, Connection conn) throws Exception {

        StringBuffer sb = new StringBuffer("");
        sb.append(" select  ");
        sb.append(" ( select sum(o1.commissionMoney) from crm_order o1 where o1.state = 0 and o1.salemanId = '" + salemanId + "' and o1.status = " + OrderStatus.Rebate + " group by o1.referralCode) as prePayCommissionMoney,  ");
        sb.append(" sum(o.commissionMoney) as totalCommissionMoney  ");
        sb.append(" from crm_order o  ");
        sb.append(" where o.state = 0  ");
        sb.append(" and o.salemanId = '" + salemanId + "' ");
        sb.append(" group by o.salemanId ");

        List<UserVO> customerOuters = MySQLDao.query(sb.toString(), UserVO.class, new ArrayList<KVObject>(), conn);

        if (customerOuters.size() > 1) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "数据异常").throwException();
        }
        if(customerOuters.size() == 1) {
            return customerOuters.get(0);
        } else {
            UserVO userVO = new UserVO();
            userVO.setPrePayCommissionMoney(0);
            userVO.setTotalCommissionMoney(0);
            return userVO;
        }
    }

    /**
     * 修改对外销售人员的密码
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年3月31日
     *
     * @param userPO
     * @param newPassword
     */
    public Boolean updatePassword(UserPO userPO, String newPassword, Connection conn) throws Exception {

        userPO.setPassword(newPassword);
        Integer count = MySQLDao.insertOrUpdate(userPO, conn);
        return count == 1 ? true : false;

    }

    /**
     * 修改手机号码
     * <p/>
     * 作者：姚章鹏
     * 内容：创建代码
     * 时间：2015年12月21日
     *
     * @param mobile
     * @param salemanId
     * @param conn
     * @return
     * @throws Exception
     */
    public int updateMobile(String mobile, String salemanId, Connection conn) throws Exception {

        int count = 0;

        // 组织 SQL
        String sql = "select * from system_user o where o.state = 0 and o.id = '" + salemanId + "'";

        // 查询
        List<UserPO> list = MySQLDao.query(sql, UserPO.class, new ArrayList<KVObject>(), conn);
        if(list.size() != 1) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "数据异常").throwException();
        }

        UserPO po = list.get(0);
        po.setMobile(mobile);
        count = MySQLDao.insertOrUpdate(po, conn);

        return count;
    }

    /**
     * 获取销售人员的基本信息
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年3月24日
     *
     * @param salemanId
     * @param conn
     * @return
     * @throws Exception
     */
    public UserVO loadMyInformation(String salemanId, Connection conn) throws Exception {

        // 组织 SQL
        String sql = "select * from system_user o where o.state = 0 and o.id = '" + salemanId + "'";

        // 查询
        List<UserVO> list = MySQLDao.query(sql, UserVO.class, new ArrayList<KVObject>(), conn);
        if(list.size() != 1) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "数据异常").throwException();
        }

        UserVO userVO = list.get(0);

        // 行业换成 KV 的中文
        KVService kvService = new KVService();
        KVPO kv = kvService.load(userVO.getIndustry(), "Industries", conn);
        userVO.setIndustryType(kv.getV());

        return userVO;

    }

    /**
     * JIRA: HOPEWEALTH-1280
     * 作者：曾威恺
     * 内容：创建代码
     * 时间：2016年3月14日
     * <p/>
     * PO: SalemanOuterPO
     * table: crm_saleman_outer
     * 用户登录
     *
     * @param username
     * @param password
     * @param conn
     * @return
     */
    public UserPO login(String username, String password, Connection conn) throws Exception {

        UserPO user=new UserPO();
        user.setPassword(password);
        user.setMobile(username);
        //K键为9662的账号才是被启用的
        user.setStatus("9662");
        user.setState(Config.STATE_CURRENT);

        user = MySQLDao.load(user, UserPO.class, conn);

        return user;
    }


    /**
     * 检测对外销售人员的手机号码是否被注册
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年3月14日
     *
     * @param mobile
     * @param conn
     * @return
     * @throws Exception
     */
    public Boolean isRegistered(String mobile, Connection conn) throws Exception {

        // 校验参数
        if (StringUtils.isEmpty(mobile)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }
        if (mobile.length() != 11) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_CORRECT, "参数不正确").throwException();
        }

        // 查询数据
        String sql = "select s.id from system_user s where s.state = 0 and s.mobile = ? ";
        List<KVObject> params = new ArrayList<KVObject>();
        params = Database.addQueryKVObject(1, mobile, params);
        List<UserVO> list = MySQLDao.search(sql, params, UserVO.class, new ArrayList<KVObject>(), conn);

        return list.size() > 0 ? true : false;

    }

    /**
     * 注册新的外部销售人员
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年3月14日
     *
     * @param username
     * @param password
     * @param conn
     * @return
     * @throws Exception
     */
    public Integer register(String username, String password, Connection conn) throws Exception { // 21232f297a57a5a743894a0e4a801fc3

        // 保存到数据库
        UserPO po = new UserPO();
        po.setPassword(password);
        po.setMobile(username);
        po.setJointime(TimeUtils.getNow());
        po.setIndustry("31849");
        po.setStatus("31850");
        po.setUserType("1"); //设置用户为外部销售
        po.setReferralCode("S" + StringUtils.buildNumberString(MySQLDao.getSequence("userCode", conn), 5));

        return MySQLDao.insertOrUpdate(po, conn);
    }

    /**
     * 外部销售人员更新
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年3月14日
     *
     * @param userPO
     * @param conn
     * @return
     * @throws Exception
     */
    public Integer update(UserPO userPO, Connection conn) throws Exception {
        return MySQLDao.insertOrUpdate(userPO, conn);
    }

    public Pager list(HttpServletRequest request, Connection conn) throws Exception {
        return null;
    }

    public Pager load(UserPO po) throws Exception {
        return null;
    }

    public int delete() throws Exception {
        return 0;
    }

    /**
     * 通过推荐码获取销售人员
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年5月10日
     *
     * @param referralCode
     * @param conn
     * @return
     * @throws Exception
     */
    public UserPO loadSalemanByReferralCode(String referralCode, Connection conn) throws Exception {

        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT");
        sbSQL.append(" *");
        sbSQL.append(" FROM");
        sbSQL.append(" system_user c");
        sbSQL.append(" WHERE");
        sbSQL.append(" 1 = 1");
        sbSQL.append(" AND c.state = 0");
        sbSQL.append(" AND c.referralCode = ?");

        List<KVObject> parameters = new ArrayList<KVObject>();
        parameters = Database.addQueryKVObject(1, referralCode, parameters);

        List<UserPO> salemanList = MySQLDao.search(sbSQL.toString(), parameters, UserPO.class, null, conn);

        if (salemanList != null && salemanList.size() == 1) {
            return salemanList.get(0);
        }
        return null;

    }

    /**
     * (for 销售APP)
     * 通过crm_saleman_outer ID来获取一条SalemanOuterPO<br/>
     * JIRA：HOPEWEALTH-1280<br/>
     * 作者：曾威恺<br/>
     * 内容：创建代码<br/>
     * 时间：2016年03月15日<br/>
     *
     * @param id
     * @param conn
     * @return
     * @throws Exception
     */
    public UserPO loadSalemanById(String id, Connection conn) throws Exception {
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT");
        sbSQL.append(" *");
        sbSQL.append(" FROM");
        sbSQL.append(" system_user c");
        sbSQL.append(" WHERE");
        sbSQL.append(" 1 = 1");
        sbSQL.append(" AND c.state = 0");
        sbSQL.append(" AND c.id = ?");

        List<KVObject> parameters = new ArrayList<KVObject>();
        parameters = Database.addQueryKVObject(1, id, parameters);

        List<UserPO> salemanList = MySQLDao.search(sbSQL.toString(), parameters, UserPO.class, null, conn);

        if (salemanList != null && salemanList.size() == 1) {
            return salemanList.get(0);
        }
        return null;
    }


    /**
     * (for 销售APP)
     * 通过crm_saleman_outer mobile来获取一条SalemanOuterPO<br/>
     * JIRA：HOPEWEALTH-1325<br/>
     * 作者：付高杨<br/>
     * 内容：创建代码<br/>
     * 时间：2016年03月31日<br/>
     *
     * @param mobile
     * @param conn
     * @return
     * @throws Exception
     */
    public UserPO loadSalemanByMobile(String mobile, Connection conn) throws Exception {
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT");
        sbSQL.append(" *");
        sbSQL.append(" FROM");
        sbSQL.append(" system_user c");
        sbSQL.append(" WHERE");
        sbSQL.append(" 1 = 1");
        sbSQL.append(" AND c.state = 0");
        sbSQL.append(" AND c.Mobile = ?");

        List<KVObject> parameters = new ArrayList<KVObject>();
        parameters = Database.addQueryKVObject(1, mobile, parameters);

        List<UserPO> salemanList = MySQLDao.search(sbSQL.toString(), parameters, UserPO.class, null, conn);

        if (salemanList != null && salemanList.size() == 1) {
            return salemanList.get(0);
        }
        return null;
    }

    /**
     * 获取推荐人下特定状态的佣金列表
     *
     * 作者：付高杨
     * 内容：创建代码
     *
     * @param orderPO
     * @param request
     * @param conn
     * @return
     * @throws Exception
     */
    public Pager listCommissions(OrderPO orderPO, HttpServletRequest request, Connection conn) throws Exception {

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM crm_order ");
        sql.append("WHERE salemanId = '" + orderPO.getSalemanId()+ "' ");
        sql.append("AND `Status` = '" + orderPO.getStatus() + "' AND state = 0 ");

        List<KVObject> conditions = new ArrayList<KVObject>();
        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, "createTime " + Database.ORDERBY_DESC));

        Pager pager = Pager.query(sql.toString(), orderPO, conditions, request, null, conn);
        return  pager;
    }


    /**
     * 添加或修改数据，并修改数据状态
     * @param userPO
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int insertOrUpdate(UserPO userPO, UserPO user, Connection conn) throws Exception{
        int count = 0;
        // 新增
        if (userPO.getId().equals("")) {
            userPO.setSid(MySQLDao.getMaxSid("system_user", conn));
            userPO.setId(IdUtils.getUUID32());
            userPO.setState(Config.STATE_CURRENT);
            userPO.setOperatorId(user.getId());
            userPO.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(userPO, conn);
        }
        // 更新
        else {
            UserPO temp = new UserPO();
            temp.setSid(userPO.getSid());
            temp = MySQLDao.load(temp, UserPO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                userPO.setSid(MySQLDao.getMaxSid("system_user", conn));
                userPO.setState(Config.STATE_CURRENT);
                userPO.setOperatorId(user.getId());
                userPO.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(userPO, conn);
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
    public UserPO loadSalemanOuterPO(String id) throws Exception{
        UserPO po = new UserPO();

        po.setId(id);
        po.setState(Config.STATE_CURRENT);
        po = MySQLDao.load(po, UserPO.class);

        return po;
    }

    /**
     * 根据条改编数据的状态
     * @param userPO
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int delete(UserPO userPO, UserPO user, Connection conn) throws Exception {
        int count = 0;

        userPO.setState(Config.STATE_CURRENT);
        userPO = MySQLDao.load(userPO, UserPO.class);
        userPO.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(userPO, conn);
        if (count == 1) {
            userPO.setSid(MySQLDao.getMaxSid("system_user", conn));
            userPO.setState(Config.STATE_DELETE);
            userPO.setOperateTime(TimeUtils.getNow());
            userPO.setOperatorId(user.getId());
            count = MySQLDao.insert(userPO, conn);
        }

        if (count != 1) {
            throw new Exception("删除失败");
        }

        return count;
    }


}
