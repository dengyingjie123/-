package com.youngbook.action.system;

import com.youngbook.action.BaseAction;
import com.youngbook.common.Database;
import com.youngbook.common.config.Config;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.util.List;

/**
 * Created by Administrator on 2014/12/2.
 */
public class ValidationCheckAction extends BaseAction {
    private UserPO user;
    HttpSession session = ServletActionContext.getRequest().getSession();
    HttpServletRequest request = ServletActionContext.getRequest();
    String mobile = getRequest().getParameter("mobile");

    public String mobile() throws Exception{
        Connection conn =getConnection();

        String sql = "select * from system_user where mobile='"+ Database.encodeSQL(mobile)+"'";
        List<UserPO> list = MySQLDao.query(sql, UserPO.class, null, conn);
        if(list != null){
            session.setAttribute("message","手机号重复");
        }
        return SUCCESS;

    }


    public UserPO getUser() {
        return user;
    }

    public void setUser(UserPO user) {
        this.user = user;
    }

}
