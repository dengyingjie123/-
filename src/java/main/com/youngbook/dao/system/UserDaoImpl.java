package com.youngbook.dao.system;

import com.youngbook.common.Database;
import com.youngbook.common.Pager;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.NumberUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.Struts2Utils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.MenuPO;
import com.youngbook.entity.po.PermissionPO;
import com.youngbook.entity.po.UserPO;
import org.springframework.stereotype.Component;
import sun.awt.SunToolkit;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Lee on 2016/5/28.
 */
@Component("userDao")
public class UserDaoImpl implements IUserDao {


    public List<UserPO> listUserPO(String customerId, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("listUserPO", this);
        dbSQL.addParameter4All("customerId",customerId);
        dbSQL.initSQL();

        return MySQLDao.search(dbSQL, UserPO.class, conn);
    }

    public UserPO login(String mobile, String password, Connection conn) throws Exception {

        UserPO userPO = new UserPO();
        userPO.setState(Config.STATE_CURRENT);
        userPO.setMobile(mobile);

        if (!StringUtils.isEmpty(password) && password.length() != 32) {
            password = StringUtils.md5(password);
        }
        userPO.setPassword(password);

        List<UserPO> list = MySQLDao.search(userPO,UserPO.class, conn);

        if (list != null && list.size() == 1) {
            return list.get(0);
        }

        return null;
    }

    public UserPO loadByReferralCode(String referralCode, Connection conn) throws Exception {

        if (StringUtils.isEmpty(referralCode)) {
            return null;
        }

        if (StringUtils.isNumeric(referralCode)) {
            int number = Integer.parseInt(referralCode);


            referralCode = "S" + StringUtils.buildNumberString(number, 5);
        }

        referralCode = referralCode.toUpperCase();


        DatabaseSQL dbSQL = DatabaseSQL.getInstance("select * from system_user u where u.state=0 and u.referralCode =? ").addParameter(1, referralCode);
        List<UserPO> users = MySQLDao.search(dbSQL, UserPO.class, conn);


        if (users != null && users.size() == 1) {
            return  users.get(0);
        }
        return null;
    }


    /**
     * 网站：通过用户 ID 获取用户
     *
     * @param userId
     * @param conn
     * @return
     * @throws Exception
     */
    public UserPO loadUserByUserId(String userId, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.getInstance("select * from system_user u where u.state=0 and u.id =? ").addParameter(1, userId);
        List<UserPO> users = MySQLDao.search(dbSQL, UserPO.class, conn);

        if (users != null && users.size() == 1) {
            return  users.get(0);
        }
        return null;
    }


    public UserPO loadUserByMobile(String mobile, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.getInstance("select * from system_user u where u.state=0 and u.mobile=? ").addParameter(1, mobile);
        List<UserPO> users = MySQLDao.search(dbSQL, UserPO.class, conn);

        if (users != null && users.size() == 1) {
            return  users.get(0);
        }
        return null;
    }

    public UserPO loadUserByName(String mobile, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.getInstance("select * from system_user u where u.state=0 and u.name=? ").addParameter(1, mobile);
        List<UserPO> users = MySQLDao.search(dbSQL, UserPO.class, conn);

        if (users != null && users.size() == 1) {
            return  users.get(0);
        }
        return null;
    }

    @Override
    public Pager getPermissionName(String userId, String permissionName, int currentPage, int showRowCount, Connection connection) throws Exception {
        MenuPO po = new MenuPO();
        DatabaseSQL dbSQL = DatabaseSQL.newInstance("skrskr");
        dbSQL.addParameter4All("userId",userId);
        dbSQL.addParameter4All("permissionName",permissionName);
        dbSQL.initSQL();
        dbSQL.init4Pager();
        Pager search = MySQLDao.search(dbSQL, po, null, currentPage, showRowCount, null, connection);
        return search;
    }
}
