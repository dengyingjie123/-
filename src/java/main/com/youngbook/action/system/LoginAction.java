package com.youngbook.action.system;


import com.youngbook.action.BaseAction;
import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.config.SessionConfig;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.NumberUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.DepartmentPO;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.customer.CustomerPersonalPO;
import com.youngbook.entity.po.system.TokenBizType;
import com.youngbook.entity.po.system.TokenPO;
import com.youngbook.entity.po.system.UserPositionInfoPO;
import com.youngbook.service.customer.CustomerPersonalService;
import com.youngbook.service.system.DepartmentService;
import com.youngbook.service.system.LogService;
import com.youngbook.service.system.TokenService;
import com.youngbook.service.system.UserService;
import com.youngbook.service.wechat.WeChatService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class LoginAction extends BaseAction {

    @Autowired
    DepartmentService departmentService;

    @Autowired
    CustomerPersonalService customerPersonalService;

    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    @Autowired
    WeChatService weChatService;

    private UserPO user;

    public String loadLoginUser() throws Exception {
        user = getLoginUser();
        if (user != null) {
            getResult().setReturnValue(user.toJsonObject());
        } else {
            throw new Exception("无法获得登录用户");
        }
        return SUCCESS;
    }

    public String loginUniversal() throws Exception {

        String loginName = getHttpRequestParameter("loginName");
        String password =  getHttpRequestParameter("password");
        String userType =  getHttpRequestParameter("userType");
        String userInfoId =  getHttpRequestParameter("userInfoId");

        if (StringUtils.isEmpty(userType)) {
            userType = "customer";
        }


        String isLogined = "1";
        String message = "登录失败";

        JSONObject json = new JSONObject();
        // 创建会话
        HttpSession session = getRequest().getSession();


        TokenPO tokenPO = null;


        if (userType.equals("customer")) {

            CustomerPersonalPO customer = customerPersonalService.login(loginName, password, getConnection());

            finishLogin4Customer(customer, session, getConnection());

            isLogined = "1";
            message = "登录成功";

            /**
             * 绑定微信号
             */
            if (!StringUtils.isEmpty(userInfoId)) {
                weChatService.bindCustomer(customer.getId(), userInfoId, getConnection());
            }


            // 插入新 Token
            tokenPO = tokenService.newToken(customer.getId(), TokenBizType.CustomerLoginToken, getRequest().getRemoteAddr(), getConnection());
            if(tokenPO == null) {
                MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "Token 添加失败").throwException();
            }

        }
        else if (userType.equals("user")) {
            UserPO user = userService.login(loginName, password, getConnection());

            if (user == null) {
                MyException.newInstance("登录失败，请检查用户名和密码", "{'loginName':'"+loginName+"','password':'"+password+"'}").throwException();
            }

            // 查询用户权限
            String permissionString = Config.getUserPermissions(user.getId(), getConnection());

//            if (StringUtils.isEmpty(permissionString)) {
//                MyException.newInstance("此用户没有任何权限，请与管理员联系", "{'loginName':'"+loginName+"'}").throwException();
//            }


            finishLogin4User(user, permissionString, session);


            /**
             * 绑定微信号
             */
            if (!StringUtils.isEmpty(userInfoId)) {
                weChatService.bindUser(user.getId(), userInfoId, getConnection());
            }


            // 插入新 Token
            tokenPO = tokenService.newToken(user.getId(), TokenBizType.UserLoginToken, getRequest().getRemoteAddr(), getConnection());
            if(tokenPO == null) {
                MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "Token 添加失败").throwException();
            }

        }




        json.put("isLogined", isLogined);
        json.put("userType", userType);

        getResult().setToken(tokenPO.getToken());
        getResult().setReturnValue(json);

        return SUCCESS;
    }

    private void finishLogin4Customer(CustomerPersonalPO customer, HttpSession session, Connection conn) throws Exception {
        customerPersonalService.loginFinish(customer, session, conn);
    }


    private void finishLogin4User(UserPO user, String permissionString, HttpSession session) throws Exception {
        userService.loginFinish(user, permissionString, session);
    }

    public String customerRegisterAndLogin() throws Exception {

        String mobile = getHttpRequestParameter("mobile");
        String mobileCode = getHttpRequestParameter("mobileCode");
        String referralCode = getHttpRequestParameter("referralCode");
        String ip = HttpUtils.getClientIPFromRequest(getRequest());

        tokenService.checkAndDestroyToken(mobileCode, mobile, TokenBizType.MobileCode, ip, getConnection());

        CustomerPersonalPO customer = customerPersonalService.loadCustomerByMobile(mobile, getConnection());

        if (customer == null) {
            // 注册
            customer = new CustomerPersonalPO();
            customer.setMobile(mobile);
            customer.setPassword(IdUtils.getUUID32());
            customerPersonalService.registerCustomer(customer, referralCode, getConnection());
        }

        // 登录
        CustomerPersonalPO loginCustomer = customerPersonalService.login(customer.getId(), getConnection());

        // 查询用户权限

        finishLogin4Customer(loginCustomer, getRequest().getSession(), getConnection());


        TokenPO tokenPO = tokenService.newToken(loginCustomer.getId(), TokenBizType.CustomerLoginToken, Config.getIP(getRequest()), getConnection());

        getResult().setToken(tokenPO.getToken());
        getResult().setReturnValue(loginCustomer);

        return SUCCESS;
    }


    public String setUserPositionInfo() throws Exception {

        String userId = HttpUtils.getParameter(getRequest(), "userId");

        if (StringUtils.isEmpty(userId)) {
            MyException.newInstance("无法获得用户编号").throwException();
        }

        String positionId = HttpUtils.getParameter(getRequest(), "positionId");

        if (StringUtils.isEmpty(positionId)) {
            MyException.newInstance("无法获得部门岗位编号").throwException();
        }

        DepartmentService departmentService = new DepartmentService();
        UserPositionInfoPO userPositionInfoPO = departmentService.getUserPositionInfo(userId, positionId, getConnection());

        if (userPositionInfoPO == null) {
            MyException.newInstance("无法获得用户部门岗位信息").throwException();
        }

        getRequest().getSession().setAttribute(Config.SESSION_ACTION_LOGIN_USERPOSITION_INFO_STRING, userPositionInfoPO);

        return SUCCESS;
    }


    /**
     * 通过token进行登录
     * 需要参数
     * loginToken: 登录的token
     * success_page: 成功后返回的页面，配置参考struts-system.xml
     * fail_page: 失败返回的页面
     * @return
     * @throws Exception
     */
    public String loginWithToken() throws Exception {

        String loginToken = getHttpRequestParameter("loginToken");
        String success_page = getHttpRequestParameter("success_page");
        String fail_page = getHttpRequestParameter("fail_page");
        String ip = HttpUtils.getClientIPFromRequest(getRequest());

        try {
            if (StringUtils.isEmptyAny(loginToken, success_page)) {
                MyException.newInstance("参数不完整", "loginToken="+loginToken+"&success_page=" + success_page).throwException();
            }

            TokenPO tokenPO = tokenService.loadTokenPOByToken(loginToken, getConnection());

            if (tokenPO == null) {
                MyException.newInstance("请检查Token是否有效", "tokenString=" + loginToken).throwException();
            }

            tokenService.checkAndRenewToken(tokenPO, getConnection());

            KVObjects kvObjects = new KVObjects();
            kvObjects.addItem("loginToken", tokenPO.getToken());

            getResult().setReturnValue(kvObjects.toJSONObject());

            CustomerPersonalPO customerPersonalPO = customerPersonalService.loadByCustomerPersonalId(tokenPO.getBizId(), getConnection());

            finishLogin4Customer(customerPersonalPO, getRequest().getSession(), getConnection());

            return success_page;
        }
        catch (Exception e) {

        }

        return fail_page;
    }


    /**
     * 客户通过手机验证码进行登录
     * @return
     * 1. status： 0：登录失败，1：登录成功
     * 2. tokenString
     * 3. tokenBizId：此处返回客户编号
     * @throws Exception
     */
    public String loginWithMobileCode() throws Exception {

        String mobile = getHttpRequestParameter("mobile");
        String mobileCode = getHttpRequestParameter("mobileCode");
        String ip = HttpUtils.getClientIPFromRequest(getRequest());

        tokenService.checkAndDestroyToken(mobileCode, mobile, TokenBizType.MobileCode, ip, getConnection());



        CustomerPersonalPO customerPersonalPO = customerPersonalService.loadCustomerByMobile(mobile, getConnection());

        if (customerPersonalPO == null) {
            MyException.newInstance("手机号输入有误，请检查，或请注册。","mobile="+mobile).throwException();
        }

        customerPersonalService.login(customerPersonalPO.getId(), getConnection());


        /**
         * 绑定微信账户
         */
        String userInfoId = getHttpRequestParameter("userInfoId");
        if (!StringUtils.isEmpty(userInfoId)) {
            weChatService.bindCustomer(customerPersonalPO.getId(), userInfoId, getConnection());
        }

        // 插入新 Token
        TokenPO tokenPO = tokenService.newToken(customerPersonalPO.getId(), TokenBizType.CustomerLoginToken, getRequest().getRemoteAddr(), getConnection());
        if(tokenPO == null) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "Token 添加失败").throwException();
        }

        finishLogin4Customer(customerPersonalPO, getRequest().getSession(), getConnection());



        String status = "0";
        String tokenString = "";

        // 需要设置登录密码
        if (customerPersonalService.checkCustomerPassword(customerPersonalPO.getId(), getConnection()) == 1) {
            status = "1";
        }


        KVObjects kvObjects = new KVObjects();
        kvObjects.addItem("status", status);
        kvObjects.addItem("tokenString", tokenString);
        kvObjects.addItem("tokenBizId", tokenPO.getBizId());


        getResult().setToken(tokenString);
        getResult().setReturnValue(kvObjects.toJSONObject());

        return SUCCESS;
    }


    public String loginUserWithMobileCode() throws Exception {

        String mobile = getHttpRequestParameter("mobile");
        String mobileCode = getHttpRequestParameter("mobileCode");
        String ip = HttpUtils.getClientIPFromRequest(getRequest());

        tokenService.checkAndDestroyToken(mobileCode, mobile, TokenBizType.MobileCode, ip, getConnection());


        UserPO userPO = userService.loadUserByMobile(mobile, getConnection());

        if (userPO == null) {
            MyException.newInstance("手机号输入有误，请检查，或请注册。","mobile="+mobile).throwException();
        }

        UserPO loginUser = userService.login(userPO.getId(), getConnection());


        // 查询用户权限
        String permissionString = Config.getUserPermissions(loginUser.getId(), getConnection());

        finishLogin4User(loginUser, permissionString, getRequest().getSession());

        getResult().setReturnValue(loginUser);

        return SUCCESS;
    }

    public String loginCustomerWithMobileCode() throws Exception {

        String mobile = getHttpRequestParameter("mobile");
        String mobileCode = getHttpRequestParameter("mobileCode");
        String ip = HttpUtils.getClientIPFromRequest(getRequest());

        tokenService.checkAndDestroyToken(mobileCode, mobile, TokenBizType.MobileCode, ip, getConnection());


        CustomerPersonalPO customer = customerPersonalService.loadCustomerByMobile(mobile, getConnection());

        if (customer == null) {
            MyException.newInstance("手机号输入有误，请检查，或请注册。","mobile="+mobile).throwException();
        }


        CustomerPersonalPO loginCustomer = customerPersonalService.login(customer.getId(), getConnection());


        // 查询用户权限

        finishLogin4Customer(loginCustomer, getRequest().getSession(), getConnection());

        getResult().setReturnValue(loginCustomer);

        return SUCCESS;
    }

    /**
     * 加载登录用户的部门信息
     * @return
     * @throws Exception
     */
    public String loadLoginUserPositionInfo() throws Exception {

        JSONObject departmentInfo = new JSONObject();
        HttpServletRequest request = getRequest();
        UserPO loginUser = Config.getLoginUserInSession(request);
        Connection conn = getConnection();

        // 加载默认部门信息
        UserPositionInfoPO userPositionInfoPO = Config.getLoginUserPositionInofInSession(getRequest());
        if (userPositionInfoPO == null) {
            MyException.newInstance("无法获得登陆用户所属部门", "用户编号【"+loginUser.getId()+"】").throwException();
        }
        departmentInfo.put("defaultUserPosition", userPositionInfoPO);

        // 加载用户所属所有部门信息
        List<UserPositionInfoPO> userPositionInfoPOS = departmentService.getUserDepartmentInfo(loginUser.getId(), conn);

        if (userPositionInfoPOS == null) {
            MyException.newInstance("无法获得登录用户所有部门信息").throwException();
        }

        // 设置默认部门，此部门可能由用户自己选择
        if (userPositionInfoPO != null) {
            for (int i = 0; i < userPositionInfoPOS.size(); i++) {
                UserPositionInfoPO tempUserPositionInfo = userPositionInfoPOS.get(i);
                tempUserPositionInfo.setStatus("0");
                if (tempUserPositionInfo.getPositionId().equals(userPositionInfoPO.getPositionId())) {
                    tempUserPositionInfo.setStatus("1");
                }
            }
        }

        departmentInfo.put("userPositionInfo", userPositionInfoPOS);


        getResult().setReturnValue(departmentInfo);

        return SUCCESS;
    }

    public String logout() {
        HttpSession session = getRequest().getSession();
        session.setAttribute("login", "0");
        session.setAttribute("loginPO", null);
        session.setAttribute("PERMISSION_STRING", null);
        return SUCCESS;
    }


    public String logoutCustomer() {

        HttpSession session = getRequest().getSession();
        customerPersonalService.logoutFinish(session);
        session.setAttribute("login", "0");
        session.setAttribute("loginPO", null);
        session.setAttribute("PERMISSION_STRING", null);
        return SUCCESS;
    }

    public String checkLoginCustomer() {
        CustomerPersonalPO customerPersonalPO = Config.getLoginCustomerInSession(getRequest());

        if (customerPersonalPO == null) {
            getResult().setReturnValue("0");
        }
        else {
            getResult().setReturnValue("1");
        }

        return SUCCESS;
    }

    public String checkLoginUser() {
        UserPO userPO = Config.getLoginUserInSession(getRequest());

        if (userPO == null) {
            getResult().setReturnValue("0");
        }
        else {
            getResult().setReturnValue("1");
        }

        return SUCCESS;
    }


    public boolean buildSessionOfUserPositionInfo(UserPO loginUser, String defaultPositionId, Connection conn) throws Exception {

        if (!StringUtils.isEmpty(defaultPositionId)) {
            List<UserPositionInfoPO> list = departmentService.getUserDepartmentInfo(loginUser.getId(), conn);
            for(UserPositionInfoPO userPositionInfoPO : list) {
                if (userPositionInfoPO.getPositionId().equals(defaultPositionId)) {
                    getRequest().getSession().setAttribute(Config.SESSION_ACTION_LOGIN_USERPOSITION_INFO_STRING, userPositionInfoPO);
                    return true;
                }
            }
        }


        UserPositionInfoPO userPositionInfoPO = departmentService.getDefaultUserPositionInfo(loginUser.getId(), conn);

        if (userPositionInfoPO == null) {
            return false;
        }

        getRequest().getSession().setAttribute(Config.SESSION_ACTION_LOGIN_USERPOSITION_INFO_STRING, userPositionInfoPO);
        return true;
    }


    /**
     * 获得登陆用户部门信息，并保存到session中
     *
     * @param loginUser
     * @param conn
     * @return
     * @throws Exception
     * @author: leevits
     */
    public boolean buildSessionOfDepartment(UserPO loginUser, String defaultDepartmentId, Connection conn) throws Exception {

        DepartmentPO departmentPO = null;

        if (!StringUtils.isEmpty(defaultDepartmentId)) {
            departmentPO = departmentService.load(defaultDepartmentId, conn);
        }


        if (departmentPO == null) {
            departmentPO = Config.getDefaultDepartmentByUserId(loginUser.getId(), conn);
        }

        if (departmentPO == null) {
            MyException.newInstance("无法设置登录用户部门信息").throwException();
        }

        getRequest().getSession().setAttribute(Config.SESSION_ACTION_LOGIN_DEPARTMENT_STRING, departmentPO);

        return true;

    }

    public String test() throws Exception {
        UserPO user = HttpUtils.getInstanceFromRequest(getRequest(), UserPO.class);

        System.out.println(user);

        return SUCCESS;
    }

    /**
     * 登陆
     *
     * //////////////////////////////////////////
     * 保存的Session有：
     * 登陆成功：
     * 登陆失败：
     * //////////////////////////////////////////
     *
     *
     * 修改：leevits
     * 时间：2015年7月14日 23:27:27
     * 内容：
     * 1. 增加没有查询出任何权限时，抛异常；
     * 2. 一开始验证数据是否完整；
     * 3. 增加注释
     * 4. 记录登陆设置的session
     *
     * 修改：leevits
     * 时间：2015年6月28日 09:10:11
     * 内容：
     * 1. 在同一个connection链接下完成处理
     *
     * 修改人：leevits
     * 时间：2015年6月15日 14:25:51
     * 内容：增加系统登陆后，设置登陆用户所属的部门信息，存放到seesion里。
     * 6/17/15 张舜清 添加了密码错误提示
     *
     *
     *
     * @return
     * @throws Exception
     * @history 2015-6-25 | 江万东：修改通过员工号登录的方式
     */
    public String execute() throws Exception {

        // 判断用户是否登录
        UserPO checkLoginUser = Config.getLoginUserInSession(getRequest());
        if (checkLoginUser != null) {
            logout();
        }


        UserPO user = HttpUtils.getInstanceFromRequest(getRequest(), "user", UserPO.class);
        // 获得登陆IP
        String ip = Config.getIP(getRequest());

        Connection conn = getConnection();


        String defaultPositionId = HttpUtils.getParameter(getRequest(), "defaultPositionId");


        //获取登录用户的信息，调用log方法存入数据库 system_log 表
        // 执行登陆操作
        try {

            if (user == null || StringUtils.isEmpty(user.getName()) || StringUtils.isEmpty(user.getPassword())) {
                user = HttpUtils.getInstanceFromRequest(getRequest(), UserPO.class);
            }


            // 初始化日志信息
            if (user == null || StringUtils.isEmpty(user.getName()) || StringUtils.isEmpty(user.getPassword())) {
                String machineMessage = MyException.getData(user);
                MyException.newInstance("登陆用户为空或者信息不全", machineMessage).throwException();
            }


            /**
             * 判断用户名的类型
             */
            String type = checkNameType(user.getName());
            LogService.debug("用户名类型：" + type, this.getClass());

            DatabaseSQL dbSQL = new DatabaseSQL();

            // 员工编号
            if (type.equals("staffCode")) {
                String name = user.getName();
                String queryName = "";
                if(name.matches("^([HR][BQC])?[0][1-9]\\d{4}") && name.length() == 9){
                    queryName = name.substring(4);
                }

                // 合法的员工编号
                if (!NumberUtils.isInteger(name)) {
                    MyException.newInstance("用户名类型不正确，请检查").throwException();
                }

                int number = Integer.parseInt(name);
                queryName = StringUtils.buildNumberString(number, Config.StaffCodeLength);
                dbSQL.newSQL("select * from system_user where state = 0 and staffCode like ? and password=?");
                dbSQL.addParameter(1, "%" + queryName + "%").addParameter(2, user.getPassword());
            }
            // 用户名登陆
            else {
                dbSQL.newSQL("select * from system_user where state = 0 and (name=? or mobile=?) and password=?");
                dbSQL.addParameter(1, user.getName()).addParameter(2, user.getName()).addParameter(3, user.getPassword());
            }

            List<UserPO> list = MySQLDao.search(dbSQL.getSQL(), dbSQL.getParameters(), UserPO.class, null, conn);

            // 用户登陆成功
            if (list != null && list.size() == 1) {
                UserPO loginUser = list.get(0);
                HttpSession session = ServletActionContext.getRequest().getSession();

                //若登录成功，在session中放置登录状态“login”，使用1表示成功，使用0表示失败
                session.setAttribute(Config.SESSION_ACTION_LOGINTARGET_STRING, "1");

                //登录成功时将UserPO放入session中，以便于其他方法从session中获取当前登录的用户信息
                session.setAttribute(Config.SESSION_ACTION_LOGINUSER_STRING, loginUser);


                // 查询用户权限
                String permissionString = Config.getUserPermissions(loginUser.getId(), conn);

                if (StringUtils.isEmpty(permissionString)) {
                    MyException.newInstance("此用户没有任何权限，请与管理员联系").throwException();
                }
                // 将权限写入session
                session.setAttribute(Config.SESSION_PERMISSION_STRING, permissionString);
                session.setAttribute(Config.SESSION_LOGINUSER_TYPE, SessionConfig.LOGIN_USER_TYPE_USER);

                // 记录日志，登陆成功

                // 检查用户是否是第一次登陆，如果是第一次登陆，则需要修改登陆密码

                String sqlPassword = "select count(*) count from system_log where machineMessage like '%"+loginUser.getId()+"%'";

                int userLoginTimes = 0;
                List<Map<String, Object>> isLoginedBeforeList = MySQLDao.query(sqlPassword, conn);
                if (isLoginedBeforeList != null && isLoginedBeforeList.size() == 1) {
                    Map<String, Object> isLoginedBefore = isLoginedBeforeList.get(0);
                    userLoginTimes = Integer.parseInt(isLoginedBefore.get("count").toString());
                }

                if (userLoginTimes == 0) {
                    // todo 记录日志，需要确定
                    getRequest().setAttribute("isUpdatePassword", "true");

                    // 构造登陆用户的组织
                    buildSessionOfUserPositionInfo(loginUser, defaultPositionId, conn);

                    return SUCCESS;
                }
                getRequest().setAttribute("isUpdatePassword", "false");


                // 构造登陆用户的组织
                buildSessionOfUserPositionInfo(loginUser, defaultPositionId, conn);

                return SUCCESS;
            }
            // 登陆失败，系统手机号记录重复
            else if (type.equals("mobile") && list != null && list.size() >= 2) {
                HttpSession session = ServletActionContext.getRequest().getSession();
                session.setAttribute(Config.SESSION_ACTION_LOGINTARGET_STRING, "0");
                String message = "已有其他用户使用此手机号，请使用用户名登录！";

                session.setAttribute("Msg", message);

                return ERROR;
            }
            // 登陆失败
            else {
                HttpSession session = ServletActionContext.getRequest().getSession();
                session.setAttribute(Config.SESSION_ACTION_LOGINTARGET_STRING, "0");

                String message = "用户名或者密码错误！";
                getRequest().setAttribute("Msg", message);

                return ERROR;
            }
        }
        catch (Exception e) {
            HttpSession session = ServletActionContext.getRequest().getSession();
            session.setAttribute(Config.SESSION_ACTION_LOGINTARGET_STRING, "0");
            String pepleMessage = "登陆失败，请与管理员联系";
            String machineMessage = MyException.getExceptionMessage(e);
            getRequest().setAttribute("Msg", pepleMessage);

            return ERROR;
        }
    }

    /**
     * 获取用户名的类型
     *
     * @param name
     * @return
     * @history 2016-6-25 | 江万东：修改按照员工号登录规则，使得员工编号为HB0100001的员工可以输入诸如：
     * HB0100001/00001/001.../1这样的字段仍然可以登录
     */
    private String checkNameType(String name) {
        String type = "name";
        String reg_staffType = "^([HR][BQC][0][1-9])?\\d{1,5}$";
        String reg_mobile = "^[1]+[3,5,8]+\\d{9}$";

        if (name.matches(reg_mobile)) {
            type = "mobile";
        }
        if(name.matches(reg_staffType)){
            type="staffCode";
        }
        return type;
    }

    public UserPO getUser() {
        return user;
    }

    public void setUser(UserPO user) {
        this.user = user;
    }

}