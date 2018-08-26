package com.youngbook.action.system;

import com.youngbook.action.BaseAction;
import com.youngbook.annotation.Permission;
import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.config.SessionConfig;
import com.youngbook.common.utils.*;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.DepartmentPO;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.UserType;
import com.youngbook.entity.po.sale.SalesmanPO;
import com.youngbook.entity.po.system.UserPositionInfoPO;
import com.youngbook.entity.vo.system.UserVO;
import com.youngbook.service.customer.AuthenticationCodeService;
import com.youngbook.service.sale.SalesmanService;
import com.youngbook.service.system.DepartmentService;
import com.youngbook.service.system.TokenService;
import com.youngbook.service.system.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


public class UserAction extends BaseAction {


    @Autowired
    UserService userService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    TokenService tokenService;

    @Autowired
    AuthenticationCodeService authenticationCodeService;

    private ReturnObject result;
    private UserPO user = new UserPO();
    private UserVO userVO = new UserVO();

    private SalesmanPO salesmanPO = new SalesmanPO();

    @Autowired
    SalesmanService salesmanService;
    // private List<OrgPositionPO> orgPositions = new ArrayList<OrgPositionPO>();
    private DepartmentPO department = new DepartmentPO();




    public String register() throws Exception {
        String name = getHttpRequestParameter("name");
        String mobile = getHttpRequestParameter("mobile");
        String password = getHttpRequestParameter("password");
        String referralCode = getHttpRequestParameter("referralCode");

        if (StringUtils.isEmptyAny(password)) {
            MyException.newInstance("密码不能为空").throwException();
        }
        password = StringUtils.md5(password);
        UserPO userPO = userService.register(name, mobile, password, referralCode, UserType.FinanceCircleUser, getConnection());

        if (userPO != null) {
            getResult().setReturnValue(userPO);


            // 查询用户权限
            String permissionString = Config.getUserPermissions(userPO.getId(), getConnection());
            HttpSession session = getRequest().getSession();
            session.setAttribute(Config.SESSION_PERMISSION_STRING, permissionString);
            session.setAttribute(Config.SESSION_LOGINUSER_TYPE, SessionConfig.LOGIN_USER_TYPE_USER);
            //若登录成功，在session中放置登录状态“login”，使用1表示成功，使用0表示失败
            session.setAttribute(Config.SESSION_ACTION_LOGINTARGET_STRING, "1");

            //登录成功时将UserPO放入session中，以便于其他方法从session中获取当前登录的用户信息
            session.setAttribute(Config.SESSION_ACTION_LOGINUSER_STRING, userPO);

        }

        return SUCCESS;
    }

    /**
     * http://localhost:8080/core/system/User_login
     * 传入参数: {"user.id":"xxx", "password":"xxx"}
     *
     * @return
     * @throws Exception
     */
    public String login() throws Exception {
        // 解密
        // 判断数据有效性
        // 查询数据库
        return SUCCESS;
    }

    public String updateUserInfo() throws Exception {

        String userId = getHttpRequestParameter("user.id");
        String description = getHttpRequestParameter("user.description");

        UserPO user = userService.loadUserByUserId(userId, getConnection());
        if (user != null) {
            user.setDescription(description);

            MySQLDao.insertOrUpdate(user, getConnection());
        }


        return SUCCESS;
    }


    /**
     * 新增或修改
     *
     * @return
     * @throws Exception
     */
    /*
    * 2015-6-16 周海鸿
    * 修改insertOurUpdate 方法 让id不重复
    * */
    @Permission(require = "系统管理-用户管理-新增")
    public String insertOrUpdate() throws Exception {

        UserPO user =  HttpUtils.getInstanceFromRequest(getRequest(), "user", UserPO.class);

        result = new ReturnObject();
        try {
            int count = 0;
            Connection conn = this.getConnection();
            count = userService.newOrUpdateUser(user, conn);


            if (count == 1) {
                result.setMessage("操作成功");
                result.setCode(ReturnObject.CODE_SUCCESS);
                result.setReturnValue(user.toJsonObject());
            }
            else {
                result.setMessage("操作失败");
                result.setCode(ReturnObject.CODE_EXCEPTION);
            }
        }
        catch (Exception e) {
            result.setCode(ReturnObject.CODE_EXCEPTION);
            result.setMessage("操作失败");
            result.setException(e);
        }
        return SUCCESS;
    }

    public String changePassword() throws Exception {

        String oldPressword = getHttpRequestParameter("oldPassword");
        String password = getHttpRequestParameter("password");

        UserPO loginUser = getLoginUser();

        if (!loginUser.getPassword().equals(oldPressword)) {
            MyException.newInstance("原密码有误").throwException();
        }

        loginUser.setPassword(password);
        MySQLDao.insertOrUpdate(loginUser, getConnection());

        getResult().setReturnValue("密码修改成功");

        return SUCCESS;
    }

    /**
     * 姚章鹏
     *
     * @return
     * @内容:修改自己密码的权限
     */
    @Permission(require = "系统管理-用户管理-修改自己密码")
    public String updateSelfPassword() throws Exception {
        result = new ReturnObject();
        try {
            int count = 0;
            Connection conn = this.getConnection();
            count = userService.updateSelfPassword(user, conn);
            if (count == 1) {
                result.setMessage("操作成功");
                result.setCode(ReturnObject.CODE_SUCCESS);
                result.setReturnValue(user.toJsonObject());
            } else {
                result.setMessage("操作失败");
                result.setCode(ReturnObject.CODE_EXCEPTION);
            }
        } catch (Exception e) {
            result.setCode(ReturnObject.CODE_EXCEPTION);
            result.setMessage("操作失败");
            result.setException(e);
        }
        return SUCCESS;
    }

    /**
     * 删除
     *
     * @return
     * @throws Exception
     */
    @Permission(require = "系统管理-用户管理-删除")
    public String delete() throws Exception {
        result = new ReturnObject();
        try {
            int count = 0;// MySQLDao.delete(user);
            user = MySQLDao.load(user, UserPO.class);
            user.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(user, getConnection());
            if (count == 1) {
                user.setSid(MySQLDao.getMaxSid("system_user", getConnection()));
                user.setState(Config.STATE_DELETE);
                user.setOperateTime(TimeUtils.getNow());
                user.setOperatorId(user.getId());
                count = MySQLDao.insert(user, getConnection());
                // 获取岗位K键
                String positionTypeId = user.getPositionTypeId();
                // 8953是KV管理中的销售人员的K键
                if (positionTypeId.equals("8953")) {
                    salesmanService.deleteSaleMan(user, getLoginUser(), getConnection());
                }
            }
            if (count == 1) {
                result.setMessage("操作成功");
                result.setCode(ReturnObject.CODE_SUCCESS);
            } else {
                result.setMessage("操作失败");
                result.setCode(ReturnObject.CODE_EXCEPTION);
            }

        } catch (Exception e) {
            result.setCode(ReturnObject.CODE_EXCEPTION);
            result.setMessage("操作失败");
            result.setException(e);
        }
        return SUCCESS;
    }

    public String searchReferralCode() throws Exception {
        result = new ReturnObject();
        String code = HttpUtils.getParameter(getRequest(), "code");

        if (!StringUtils.isEmpty(code)) {
            UserService userService = new UserService();
            List<UserPO> listUsers = userService.searchReferralCode(code, getConnection());

            JSONArray array = new JSONArray();
            for (UserPO user : listUsers) {
                String referralCode = Config.getReferralCode(user.getStaffCode());

                JSONObject json = new JSONObject();
                json.put("text", user.getName() + "【"+referralCode+"】");
                json.put("id", referralCode);

                array.add(json);
            }

            result.setReturnValue(array);
            result.setMessage("操作成功");
            result.setCode(ReturnObject.CODE_SUCCESS);
        }

        return SUCCESS;
    }

    public String getReferralCode() throws Exception {
        result = new ReturnObject();

        String userId = "";

        if (user != null && !StringUtils.isEmpty(user.getId())) {
            userId = user.getId();
        }
        else {
            userId = getLoginUser().getId();
        }


        if (StringUtils.isEmpty(userId)) {
            MyException.newInstance("获取推荐码失败，无法获得客户编号").throwException();
        }


        String referralCode = userService.getReferralCode(userId, getConnection());


        if (!StringUtils.isEmpty(referralCode)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("referralCode", referralCode);


            result.setReturnValue(jsonObject);
            result.setMessage("操作成功");
            result.setCode(ReturnObject.CODE_SUCCESS);
        }
        else {
            result.setCode(ReturnObject.CODE_EXCEPTION);
            result.setMessage("操作失败，无法获得推荐码");
        }
        return SUCCESS;
    }

    /**
     * 载入数据
     *
     * @return
     * @throws Exception
     */
//    @Permission(require = "系统管理-用户管理-修改")
    public String load() throws Exception {

        String userId = getHttpRequestParameter("user.id");

        if (StringUtils.isEmpty(userId)) {
            MyException.newInstance("无法获得客户编号").throwException();
        }
        user = new UserPO();
        user.setId(userId);
        try {
            user.setState(Config.STATE_CURRENT);
            user = MySQLDao.load(user, UserPO.class);
            result.setMessage("操作成功");
            result.setCode(ReturnObject.CODE_SUCCESS);
            result.setReturnValue(user.toJsonObject4Form());

        } catch (Exception e) {
            result.setCode(ReturnObject.CODE_EXCEPTION);
            result.setMessage("操作失败");
            result.setException(e);
        }
        return SUCCESS;
    }


    /**
     * 创建人：张舜清
     * 创建时间：6/17/15
     * 描述：通过用户ID获取用户归属部门名称
     * @return
     * @throws Exception
     */
    //2016-6 -17 增加异常处理增加注解
    public String getUserForDepartmentName() throws Exception {
        UserPositionInfoPO userPositionInfoPO = Config.getLoginUserPositionInofInSession(getRequest());

        DepartmentPO departmentPO = departmentService.load(userPositionInfoPO.getDepartmentId(), getConnection());
        if(departmentPO ==  null){
            getResult().setCode(2015617);
            getResult().setMessage("该用户没有默认部门");
            throw  new Exception("该用户没有默认部门归宿");
        }else {
            getResult().setReturnValue(departmentPO.toJsonObject());
        }
        return SUCCESS;
    }


    /**
     * 列出数据
     *
     * @return
     * @throws Exception
     */
    public String list() throws Exception {

        UserVO userVO = HttpUtils.getInstanceFromRequest(getRequest(), "userVO", UserVO.class);

        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions =new ArrayList<KVObject>();
        String userType = user.getUserType();
        Pager pager = userService.list(userVO,conditions,userType,request,getConnection());
        getResult().setReturnValue(pager.toJsonObject());

        return SUCCESS;
    }


    public ReturnObject getResult() {
        return result;
    }

    public void setResult(ReturnObject result) {
        this.result = result;
    }

    public UserPO getUser() {
        return user;
    }

    public void setUser(UserPO user) {
        this.user = user;
    }

    public UserVO getUserVO() {
        return userVO;
    }

    public void setUserVO(UserVO userVO) {
        this.userVO = userVO;
    }

    public SalesmanPO getSalesmanPO() {
        return salesmanPO;
    }

    public void setSalesmanPO(SalesmanPO salesmanPO) {
        this.salesmanPO = salesmanPO;
    }


    public DepartmentPO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentPO department) {
        this.department = department;
    }
}
