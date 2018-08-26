package com.youngbook.service.wechat;

import com.youngbook.common.Database;
import com.youngbook.common.MyException;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.JSONDao;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.system.ILogDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.customer.CustomerPersonalPO;
import com.youngbook.entity.po.system.LogPO;
import com.youngbook.entity.po.wechat.UserInfoPO;
import com.youngbook.service.BaseService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Lee on 2/10/2017.
 */
@Component("weChatService")
public class WeChatService extends BaseService {

    @Autowired
    ILogDao logDao;


    public UserInfoPO bindCustomer(String customerId, String userInfoId, Connection conn) throws Exception {
        String customerOrUserType = "customer";
        return bindCustomerOrUser(customerId, userInfoId, customerOrUserType, conn);
    }

    public UserInfoPO bindUser(String userId, String userInfoId, Connection conn) throws Exception {

        String customerOrUserType = "user";
        return bindCustomerOrUser(userId, userInfoId, customerOrUserType, conn);
    }


    private UserInfoPO bindCustomerOrUser(String customerOrUserId, String userInfoId, String customerOrUserType, Connection conn) throws Exception {

        if (StringUtils.isEmptyAny(customerOrUserId, userInfoId)) {
            MyException.newInstance("无法绑定微信用户信息","customerOrUserId="+customerOrUserId+"&userInfoId="+userInfoId+"&customerOrUserType="+customerOrUserType).throwException();
        }

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("bindCustomer", this);
        dbSQL.addParameter4All("userInfoId", userInfoId);
        dbSQL.initSQL();

        List<UserInfoPO> listUserInfoPO = MySQLDao.search(dbSQL, UserInfoPO.class, conn);

        if (listUserInfoPO == null || listUserInfoPO.size() != 1) {
            MyException.newInstance("无法找到微信用户信息", "customerOrUserId="+customerOrUserId+"&userInfoId="+userInfoId+"&customerOrUserType="+customerOrUserType).throwException();
        }

        UserInfoPO userInfoPO = listUserInfoPO.get(0);
        userInfoPO.setCustomerOrUserId(customerOrUserId);
        userInfoPO.setCustomerOrUserType(customerOrUserType);
        MySQLDao.insertOrUpdate(userInfoPO, conn);


        /**
         * 记录日志
         */
        LogPO logPO = new LogPO();
        logPO.setName("绑定微信号");
        logPO.setPeopleMessage("绑定微信账号");
        logPO.setMachineMessage("customerOrUserId="+customerOrUserId+"&userInfoId="+userInfoId+"&customerOrUserType="+customerOrUserType);
        logDao.save(logPO, conn);

        return userInfoPO;
    }

    public UserInfoPO insertOrUpdate(UserInfoPO userInfoPO, String customerOrUserType, Connection conn) throws Exception {

        if (userInfoPO == null || StringUtils.isEmptyAny(userInfoPO.getOpenid())) {
            MyException.newInstance("无法获得微信用户信息").throwException();
        }

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("insertOrUpdate", this);
        dbSQL.addParameter4All("openid", userInfoPO.getOpenid());
        dbSQL.addParameter4All("customerOrUserType", customerOrUserType);
        dbSQL.initSQL();

        List<UserInfoPO> listUserInfoPO = MySQLDao.search(dbSQL, UserInfoPO.class, conn);

        if (listUserInfoPO != null && listUserInfoPO.size() == 1) {
            UserInfoPO temp = listUserInfoPO.get(0);

            userInfoPO.setCustomerOrUserId(temp.getCustomerOrUserId());
            userInfoPO.setCustomerOrUserType(temp.getCustomerOrUserType());
            userInfoPO.setId(temp.getId());
        }

        MySQLDao.insertOrUpdate(userInfoPO, conn);

        return userInfoPO;
    }

    public CustomerPersonalPO getCustomerPersonalPO(String userInfoId, Connection conn) throws Exception {

        if (StringUtils.isEmpty(userInfoId)) {
            MyException.newInstance("微信用户信息编号为空").throwException();
        }

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("getCustomerPersonalPO", this);
        dbSQL.addParameter4All("userInfoId", userInfoId);
        dbSQL.initSQL();

        List<CustomerPersonalPO> list = MySQLDao.search(dbSQL, CustomerPersonalPO.class, conn);

        if (list != null && list.size() == 1) {
            return list.get(0);
        }

        return null;
    }

    public UserPO getUserPO(String userInfoId, Connection conn) throws Exception {

        if (StringUtils.isEmpty(userInfoId)) {
            MyException.newInstance("微信用户信息编号为空").throwException();
        }

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("getUserPO", this);
        dbSQL.addParameter4All("userInfoId", userInfoId);
        dbSQL.initSQL();

        List<UserPO> list = MySQLDao.search(dbSQL, UserPO.class, conn);

        if (list != null && list.size() == 1) {
            return list.get(0);
        }

        return null;
    }

    public UserInfoPO getUserInfo(String code, Connection conn) throws Exception {

        String s = com.youngbook.common.utils.HttpUtils.getRequest("https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx0cf3490b0e5c79b4&secret=6e19d51655b73fac328ed77385f16f35&code="+code+"&grant_type=authorization_code");

        LogPO logPO = new LogPO();
        logPO.setName("登录微信");
        logPO.setMachineMessage(s);
        logDao.save(logPO, conn);

        JSONObject json = JSONObject.fromObject(s);

        if (json.get("access_token") == null || json.get("openid") == null) {
            MyException.newInstance("登录微信失败").throwException();
        }

        String access_token = json.get("access_token").toString();
        String openid = json.get("openid").toString();

        String url = "https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+openid+"&lang=zh_CN";

        String userInfoString = com.youngbook.common.utils.HttpUtils.getRequest(url);

        UserInfoPO userInfoPO = JSONDao.parse(userInfoString, UserInfoPO.class);


        return userInfoPO;
    }

    public UserInfoPO load(String id, Connection conn) throws Exception {


        UserInfoPO userInfoPO = new UserInfoPO();
        userInfoPO.setId(id);
        userInfoPO.setState(Config.STATE_CURRENT);

        userInfoPO = MySQLDao.load(userInfoPO, UserInfoPO.class, conn);

        return userInfoPO;
    }

    public UserInfoPO load(String id) throws Exception {

        Connection conn = Config.getConnection();

        try {
            return load(id, conn);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }
    }

}
