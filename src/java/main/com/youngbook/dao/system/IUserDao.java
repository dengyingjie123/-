package com.youngbook.dao.system;

import com.youngbook.common.Pager;
import com.youngbook.entity.po.MenuPO;
import com.youngbook.entity.po.UserPO;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Lee on 2016/5/28.
 */

public interface IUserDao {

    public List<UserPO> listUserPO(String customerId, Connection conn) throws Exception;

    public UserPO login(String mobile, String password, Connection conn) throws Exception;

    public UserPO loadByReferralCode(String referralCode, Connection conn) throws Exception;

    public UserPO loadUserByUserId(String userId, Connection conn) throws Exception;

    public UserPO loadUserByMobile(String mobile, Connection conn) throws Exception;

    public UserPO loadUserByName(String mobile, Connection conn) throws Exception;

    public Pager getPermissionName(String userId,String permissionName,int currentPage, int showRowCount,Connection connection)throws  Exception;

}
