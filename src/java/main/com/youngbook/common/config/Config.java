package com.youngbook.common.config;

import com.youngbook.annotation.EnumType;
import com.youngbook.annotation.IgnoreDB;
import com.youngbook.annotation.IgnoreJson;
import com.youngbook.common.*;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.VarificationUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.*;
import com.youngbook.entity.po.customer.CustomerAuthenticationCodePO;
import com.youngbook.entity.po.customer.CustomerPersonalPO;
import com.youngbook.entity.po.info.CustomerAgreementPO;
import com.youngbook.entity.po.system.TokenPO;
import com.youngbook.entity.po.system.UserPositionInfoPO;
import com.youngbook.entity.vo.customer.CustomerPersonalVO;
import com.youngbook.service.info.CustomerAgreementService;
import com.youngbook.service.system.*;
import org.dom4j.Element;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Config {


    public static final String Admin = "ab7996b505df42cda37400f550f7cf1c";


    public static final String InvestorConfirmAgreementId = "E0399EE781D74B63A4218C75467FE95A";

    public static final String APP_NAME = "开普乐信息";

    public static final String SESSION_PERMISSION_STRING = "PERMISSION_STRING";
    public static final String SESSION_LOGINUSER_STRING = "loginUser";
    public static final String SESSION_CUSTOMERVO_STRING = "SESSION_CUSTOMERVO_STRING";
    public static final String SESSION_ACTION_LOGINUSER_STRING = "loginPO";
    public static final String SESSION_ACTION_LOGINTARGET_STRING = "login";
    public static final String SESSION_ACTION_LOGIN_DEPARTMENT_STRING = "loginDepartment";
    public static final String SESSION_ACTION_LOGIN_USERPOSITION_INFO_STRING = "loginUserPositionInfo";

    public static final String SESSION_LOGINUSER_TYPE = "SESSION_LOGINUSER_TYPE";

    public static final String SPLIT_LETTER = "★";
    public static final String CONNECT_LETTER = "☆";

    public static final int STATE_CURRENT = 0;
    public static final int STATE_UPDATE = 1;
    public static final int STATE_DELETE = 2;

    public static final String Web_URL_Index = "/w2/index/ShowIndex";
    public static final String Web_URL_Login = "/w2/customer/LoginRequest";

    private static List<KVPO> systemConfig = null;
    private static List<KVPO> systemSQL = null;
    private static List<KVPO> reportConfig = null;
    private static List<KVPO> systemWords = null;

    public static final String Type_PeopleId = "98";  // 身份证类型

    // 员工号长度
    public static final int StaffCodeLength = 4;

    private static CustomerAgreementService customerAgreementService = null;
    public static ApplicationContext applicationContext = null;


    public static String getWebPH() throws Exception {
        return Config.getWebRoot() + "/ph";
    }

    public static String getWebDehecircle() throws Exception {
        return Config.getWebRoot() + "/dehecircle";
    }

    public static String getWebMonopoly() throws Exception {
        return Config.getWebRoot() + "/monopoly";
    }


    public static String getWebDeheSales() throws Exception {
        return Config.getWebRoot() + "/dehesales";
    }

    public static ApplicationContext getContext() throws Exception {
        if (applicationContext == null) {
            applicationContext = new ClassPathXmlApplicationContext("classpath:spring/spring-service.xml");
        }
        return applicationContext;
    }

    public static boolean checkloginUserHasPermission(String permissionName, HttpServletRequest request) throws Exception {
        UserPO loginUserInSession = getLoginUserInSession(request);

        if (loginUserInSession != null) {
            Connection conn = Config.getConnection();
            try {
                String permissions = getUserPermissions(loginUserInSession.getId(), conn);

                if (!StringUtils.isEmpty(permissions) && permissions.contains(permissionName)) {
                    return true;
                }
            }
            catch (Exception e) {
                throw e;
            }
            finally {
                Database.close(conn);
            }
        }

        return false;
    }

    /**
     * 通过组名和键值，获取具体数值
     * @param key
     * @param groupName
     * @param conn
     * @return
     * @throws Exception
     */
    public static String getKVString(String key, String groupName, Connection conn) throws Exception {

        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("SELECT * from system_kv where state=0 and GroupName='"+groupName+"'");
        List<KVPO> search = MySQLDao.search(dbSQL.getSQL(), dbSQL.getParameters(), KVPO.class, null, conn);

        for (KVPO config : search) {
            if (config.getK().equalsIgnoreCase(key)) {
                return  config.getV().toString();
            }
        }

        return null;
    }

    public static String getProductionExpectedYieldName(double expectedYield) throws Exception {

        if (expectedYield != Double.MAX_VALUE && expectedYield > 0) {
            return expectedYield + "%";
        }

        return "浮动";
    }

    public static boolean isCustomerAgreement(HttpServletRequest request) throws Exception {

        CustomerPersonalPO customerPersonalPO = Config.getLoginCustomerInSession(request);

        if (customerAgreementService == null) {
            customerAgreementService = Config.getBeanByName("customerAgreementService", CustomerAgreementService.class);
        }


        Connection conn = Config.getConnection();

        try {
            CustomerAgreementPO customerAgreementPO = customerAgreementService.loadCustomerAgreementPO(customerPersonalPO.getId(), Config.InvestorConfirmAgreementId, conn);

            if (customerAgreementPO != null) {
                return true;
            }

            return false;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }


    }

    public static String getUserAavtarUrl(String userId) throws Exception {

        Connection conn = Config.getConnection();
        try {
            UserService userService = Config.getBeanByName("userService", UserService.class);
            String avatarUrl = userService.getAvatarUrl(userId, conn);
            return avatarUrl;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }

    }

    public static String getModernSaleManagementFooter(String md) throws Exception{

        if (StringUtils.isEmpty(md)) {
            md = "10000";
        }

        String home = md.charAt(0) == '1' ? "home-a" : "home-ia";
        String production = md.charAt(1) == '1' ? "production-a" : "production-ia";
        String customer = md.charAt(2) == '1' ? "customer-a" : "customer-ia";
        String account = md.charAt(3) == '1' ? "account-a" : "account-ia";
        String more = md.charAt(4) == '1' ? "more-a" : "more-ia";

        StringBuffer sbHtml = new StringBuffer();
        sbHtml.append("<footer>");
        sbHtml.append("    <ul class=\"footer-menu\">");
        sbHtml.append("        <li onclick=\"fm.goto('"+ Config.getModernSaleManagementPages()+"/production/home.jsp')\">");
//        sbHtml.append("            <object class=\"icon\" data=\""+ Config.getWebRoot() + "/modern/images/svg/"+home+".svg\" type=\"image/svg+xml\"></object><br>");
        sbHtml.append("            <img class='icon' src='"+Config.getWebRoot()+"/modern/images/"+home+".png' /><br>");
        sbHtml.append("            <span class=\"text\">首页</span>");
        sbHtml.append("        </li>");
        sbHtml.append("        <li onclick=\"fm.goto('"+ Config.getModernSaleManagementPages()+"/production/production_list.jsp')\">");
        sbHtml.append("            <img class='icon' src='"+Config.getWebRoot()+"/modern/images/"+production+".png' /><br>");
        sbHtml.append("            <span class=\"text\">产品</span>");
        sbHtml.append("        </li>");
        sbHtml.append("        <li onclick=\"fm.goto('"+ Config.getModernSaleManagementPages()+"/customer/customer_list.jsp')\">");
        sbHtml.append("            <img class='icon' src='"+Config.getWebRoot()+"/modern/images/"+customer+".png' /><br>");
        sbHtml.append("            <span class=\"text\">客户</span>");
        sbHtml.append("        </li>");
        sbHtml.append("        <li onclick=\"fm.goto('"+Config.getModernSaleManagementPages()+"/user/user_home.jsp')\">");
        sbHtml.append("            <img class='icon' src='"+Config.getWebRoot()+"/modern/images/"+account+".png' /><br>");
        sbHtml.append("            <span class=\"text\">账户</span>");
        sbHtml.append("        </li>");
        sbHtml.append("        <li onclick=\"fm.goto('"+ Config.getWebRoot() + "/modern/pages/sale_management/info/info_home.jsp')\">");
//        sbHtml.append("        <li onclick=\"fm.goto('"+ Config.getWebRoot() + "/modern/pages/index.jsp')\">");
        sbHtml.append("            <img class='icon' src='"+Config.getWebRoot()+"/modern/images/"+more+".png' /><br>");
        sbHtml.append("            <span class=\"text\">更多</span>");
        sbHtml.append("        </li>");
        sbHtml.append("    </ul>");
        sbHtml.append("</footer>");
        return sbHtml.toString();
    }

    public static String getModernCustomerManagementFooter(String md) throws Exception{

        if (StringUtils.isEmpty(md)) {
            md = "1000";
        }

        String home = md.charAt(0) == '1' ? "home-a" : "home-ia";
        String production = md.charAt(1) == '1' ? "production-a" : "production-ia";
        String account = md.charAt(2) == '1' ? "account-a" : "account-ia";
        String more = md.charAt(3) == '1' ? "more-a" : "more-ia";

        StringBuffer sbHtml = new StringBuffer();
        sbHtml.append("<footer>");
        sbHtml.append("    <ul class=\"footer-menu\">");
        sbHtml.append("        <li onclick=\"fm.goto('"+ Config.getModernCustomerManagementPages()+"/production/home.jsp')\">");
//        sbHtml.append("            <object class=\"icon\" data=\""+ Config.getWebRoot() + "/modern/images/svg/"+home+".svg\" type=\"image/svg+xml\"></object><br>");
        sbHtml.append("            <img class='icon' src='"+Config.getWebRoot()+"/modern/images/"+home+".png' /><br>");
        sbHtml.append("            <span class=\"text\">首页</span>");
        sbHtml.append("        </li>");
        sbHtml.append("        <li onclick=\"fm.goto('"+ Config.getModernCustomerManagementPages()+"/production/production_list.jsp')\">");
        sbHtml.append("            <img class='icon' src='"+Config.getWebRoot()+"/modern/images/"+production+".png' /><br>");
        sbHtml.append("            <span class=\"text\">产品</span>");
        sbHtml.append("        </li>");
        sbHtml.append("        <li onclick=\"fm.goto('"+Config.getModernCustomerManagementPages()+"/customer/customer_detail.jsp')\">");
        sbHtml.append("            <img class='icon' src='"+Config.getWebRoot()+"/modern/images/"+account+".png' /><br>");
        sbHtml.append("            <span class=\"text\">账户</span>");
        sbHtml.append("        </li>");
        sbHtml.append("        <li onclick=\"fm.goto('"+ Config.getModernCustomerManagementPages() + "/info/info_home.jsp')\">");
//        sbHtml.append("        <li onclick=\"fm.goto('"+ Config.getWebRoot() + "/modern/pages/index.jsp')\">");
        sbHtml.append("            <img class='icon' src='"+Config.getWebRoot()+"/modern/images/"+more+".png' /><br>");
        sbHtml.append("            <span class=\"text\">更多</span>");
        sbHtml.append("        </li>");
        sbHtml.append("    </ul>");
        sbHtml.append("</footer>");
        return sbHtml.toString();
    }

    public static String getModernRoot() throws Exception {
        return Config.getWebRoot() + "/modern";
    }

    public static String getModernFrameworkRoot() throws Exception {
        return Config.getWebRoot() + "/include/framework/jeasyui/1.5";
    }

    public static String getReferralCode(String staffCode) {
        if (!StringUtils.isEmpty(staffCode)) {
            return "S" + staffCode.substring(staffCode.length() - Config.StaffCodeLength, staffCode.length());
        }

        return null;
    }

    public static void reloadSystemConfig() throws Exception {
        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("SELECT * from system_kv where state=0 and GroupName='SystemConfig'");
        systemConfig = MySQLDao.search(dbSQL.getSQL(), dbSQL.getParameters(), KVPO.class, null);
    }

    public static void reloadSystemSQL() throws Exception {
        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("SELECT * from system_kv where state=0 and GroupName='SQL'");
        systemSQL = MySQLDao.search(dbSQL.getSQL(), dbSQL.getParameters(), KVPO.class, null);
    }

    public static String getSystemConfig(String key) throws Exception {

        if (systemConfig == null) {
            DatabaseSQL dbSQL = new DatabaseSQL();
            dbSQL.newSQL("SELECT * from system_kv where state=0 and GroupName='SystemConfig'");
            systemConfig = MySQLDao.search(dbSQL.getSQL(), dbSQL.getParameters(), KVPO.class, null);
        }

        for (KVPO config : systemConfig) {
            if (config.getK().equalsIgnoreCase(key)) {
                return  config.getV().toString();
            }
        }

        return null;
    }

    /**
     * key 命名： 类名_方法名_列名
     * @param key
     * @return
     * @throws Exception
     */
    public static String getReportConfig(String key) throws Exception {

        if (reportConfig == null) {
            DatabaseSQL dbSQL = new DatabaseSQL();
            dbSQL.newSQL("SELECT * from system_kv where GroupName='ReportConfig'");
            reportConfig = MySQLDao.search(dbSQL.getSQL(), dbSQL.getParameters(), KVPO.class, null);
        }

        for (KVPO config : reportConfig) {
            if (config.getK().equalsIgnoreCase(key)) {
                return  config.getV().toString();
            }
        }

        System.out.println("Config.getReportConfig(): need to set Title Value ["+key+"]");
        return key;
    }

    public static int getSystemConfigInt(String key) throws Exception {
        String str = Config.getSystemConfig(key);
        return Integer.parseInt(str);
    }

    public static double getSystemConfigDouble(String key) throws Exception {
        String str = Config.getSystemConfig(key);
        return Double.parseDouble(str);
    }

    public static String getF7Folder() {
        return "framework7-1.6.5";
    }

    public static String getSystemWord(String key) throws Exception {

        if (systemWords == null) {
            DatabaseSQL dbSQL = new DatabaseSQL();
            dbSQL.newSQL("SELECT * from system_kv where GroupName='SystemWord'");
            systemWords = MySQLDao.search(dbSQL.getSQL(), dbSQL.getParameters(), KVPO.class, null);
        }

        for (KVPO config : systemWords) {
            if (config.getK().toString().equals(key)) {
                return  config.getV().toString();
            }
        }

        return null;
    }


    public static final String Workflow_Active_Field_CSS = "background-color: indianred";

    // 销售组负责人K值
    public static final int SaleManGroupPrincipal = 2;

    // 用户销售岗位K值
    public static final String PostTypeToSaleMan = "8953";


    /*用来判断是否为测试环境：
    * 内容为：test 测试环境， 其他为非测试环境*/
    // todo 部署到正式环境后，该变量要设为非 test 【邓超】 2015年12月7日
    public static final Boolean  isTest = false; // test
    /*默认部门的id编号*/
    public static final String DefaultDepartment="9B882D7E-8BF0-41F0-8DDA-16E813A6AE4B";

    @IgnoreJson
    @IgnoreDB
    @EnumType(id = "Search_Company", display = " 德合基金", value = "DH")
    public static final String DeheFund = "DH";
    @IgnoreJson
    @IgnoreDB
    @EnumType(id = "Search_Company", display = "开普乐科技", value = "KEP")
    public static final String Kepler = "KEP";


    /*
     * 修改：周海鸿
     * 时间：2015-7-23
     * 内容：设置MD5 加密文
     */
    public static final String MD5String = "!@#!#!@#!@#!@#!@A!A!!A!A!!A!!!";

    public Config () {}


    public static String getPasswordDeal4User(String password) {
        return StringUtils.md5(password + Config.MD5String);
    }

    public static String getPasswordDeal4Customer(String password) {
        // 通过系统指定的字符进行二次加密
        if (password.length() != 32) {
            password = StringUtils.md5(password);
        }
        password = StringUtils.md5(password + Config.MD5String);

        return password;
    }

    /**
     * 验证网站验证码
     *
     * @param request
     * @param conn
     * @return
     * @throws Exception
     */
    public static boolean checkWebCode(HttpServletRequest request, Connection conn) throws Exception {

        String captcha = request.getParameter("captcha");
        if(captcha != null) {
            captcha = captcha.trim();
        }
        String u = request.getParameter("u");

        // 校验验证码
        CaptchaService captchaService = new CaptchaService();
        return captchaService.validateCode(captcha, u, conn);
    }

    public static boolean checkWebCode(HttpServletRequest request) throws Exception {

        Connection conn = Config.getConnection();
        try {
            return checkWebCode(request, conn);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }

    }

    /**
     * 检验token
     * @param request
     * @param conn
     * @return
     * @throws Exception
     *
     * 作者：quan.zeng
     * 内容：修改代码
     * 时间：2015-12-6
     * 描述：把校验成功后的tokenPO放入request请求域中
     *
     */
    public static TokenPO checkToken(HttpServletRequest request, Connection conn) throws Exception {
        String token = request.getParameter("token");
        TokenPO tokenPO = new TokenPO();
        tokenPO.setToken(token);
        tokenPO.setState(0);
        TokenService tokenService = Config.getBeanByName("tokenService", TokenService.class);
        tokenPO = tokenService.checkAndRenewToken(tokenPO, conn);
        request.setAttribute("tokenPO",tokenPO); // 把校验成功后的tokenPO放入request请求域中
        return tokenPO;
    }

    /**
     * 验证手机验证码
     *
     * @param mobile
     * @param code
     * @param conn
     * @return
     * @throws Exception
     */
    public static boolean checkMobileCode(String mobile, String code, Connection conn) throws Exception {

        // 验证手机动态码
        CustomerAuthenticationCodePO codePO = VarificationUtils.viladateMobileCode(code, mobile);

        return codePO == null ? false : true;

    }

    public static String getDefaultCustomerPassword() throws Exception {
        return AesEncrypt.encrypt("123456");
    }

    /**
     * 获取默认的操作者 ID
     * @return
     */
    public static String getDefaultOperatorId() {
        return "0000";
    }

    /**
     * 获得远程IP地址
     * @param request
     * @return
     */
    public static String getIP(HttpServletRequest request) {

        if (request == null) {
            return "";
        }

        return request.getRemoteAddr();
    }

    public static boolean checkLoginedUniversal(HttpServletRequest request) {

        if (request == null || request.getSession().getAttribute(SESSION_LOGINUSER_TYPE) == null) {
            return false;
        }

        String loginUserType = (String)request.getSession().getAttribute(SESSION_LOGINUSER_TYPE);

        if (loginUserType.equals(SessionConfig.LOGIN_USER_TYPE_CUSTOMER_PERSONAL)) {
            return true;
        }

        if (loginUserType.equals(SessionConfig.LOGIN_USER_TYPE_USER)) {
            return true;
        }

        return false;
    }

    /**
     * 检查会话中客户是否是游客
     * @param request
     * @return 1：是，2：不是，3：没有会话信息
     */
    public static int checkIsGuest(HttpServletRequest request) {



        if (!checkLoginedUniversal(request)) {
            return 2;
        }

        String loginUserType = (String)request.getSession().getAttribute(SESSION_LOGINUSER_TYPE);

        if (loginUserType.equals(SessionConfig.LOGIN_USER_TYPE_CUSTOMER_PERSONAL)) {

            CustomerPersonalPO customerPersonalPO = Config.getLoginCustomerInSession(request);

            if (customerPersonalPO.getId().equals("0000")) {
                return 1;
            }
        }

        return 0;

    }

    public static void checkMobileCode(HttpServletRequest request) throws Exception {
        String mobile = request.getParameter("mobile");
        String mobileCode = request.getParameter("mobileCode");
        CustomerAuthenticationCodePO authenticationCodePO = VarificationUtils.viladateMobileCode(mobileCode, mobile);

        if (authenticationCodePO == null) {
            MyException.newInstance("验证码输入有误").throwException();
        }
    }

    public static UserPO getLoginUserInSession(HttpServletRequest request) {
        return (UserPO)request.getSession().getAttribute(SESSION_ACTION_LOGINUSER_STRING);
    }

    public static String getLoginUserInSession2Json(HttpServletRequest request) {
        UserPO user = getLoginUserInSession(request);

        if (user != null) {
            return user.toJsonObject().toString();
        }

        return "";
    }

    public static String getLoginCustomerInSession2Json(HttpServletRequest request) {
        CustomerPersonalPO customer = getLoginCustomerInSession(request);

        if (customer != null) {
            return customer.toJsonObject().toString();
        }

        return "";
    }


    public static CustomerPersonalPO getLoginCustomerInSession(HttpServletRequest request) {
        return (CustomerPersonalPO)request.getSession().getAttribute(SESSION_LOGINUSER_STRING);
    }

    public static CustomerPersonalVO getLoginCustomerVOInSession(HttpServletRequest request) {
        return (CustomerPersonalVO)request.getSession().getAttribute(SESSION_CUSTOMERVO_STRING);
    }

    public static List<String> getermissionSaleManGroup (){
        List<String> permissionSaleManGroupList = new ArrayList<String>();
        permissionSaleManGroupList.add(0,DepartmentTypeStatus.TYPE_14511);// 中心
        permissionSaleManGroupList.add(1,DepartmentTypeStatus.TYPE_14512);// 渠道
        return permissionSaleManGroupList;
    }


    public static UserPositionInfoPO getLoginUserPositionInofInSession(HttpServletRequest request) {
        return  (UserPositionInfoPO) request.getSession().getAttribute(Config.SESSION_ACTION_LOGIN_USERPOSITION_INFO_STRING);
    }


    /**
     * 向外部发送消息
     * @param title
     * @param content
     * @return
     */
    public static ReturnObject sendMessage(String title, String content) {
        ReturnObject returnObject = new ReturnObject();
//        try {
//            String url = Config.getSystemConfig("thirdparty.youngbook.api.url") + "api/b";
//
//            String data = "MessageSave@1.0@"+title+"@"+content;
//
//            Map<String, String> parameters = new HashMap<>();
//            parameters.put("data", data);
//
//            String postRequestString = HttpUtils.postRequest(url, parameters);
//
//
//            returnObject = JSONDao.parse(postRequestString, ReturnObject.class);
//
//
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }

        return returnObject;

    }


    /**
     * 获得系统默认的用户部门
     *
     * 此为系统设置默认的部门，并非用户切换的部门
     * @param id
     * @param conn
     * @return
     * @throws Exception
     */
    public static DepartmentPO getDefaultDepartmentByUserId(String id, Connection conn) throws Exception {

        String sql = "select * from system_department where id = (select DepartmentId from system_position where  id in (select  positionId from system_positionuser where states=1 and userId='"+id+"'))";
        List<DepartmentPO> departmentPOs = MySQLDao.query(sql, DepartmentPO.class, null, conn);
        if (departmentPOs != null && departmentPOs.size() == 1) {
            return departmentPOs.get(0);
        }
        return null;
    }

    public static DepartmentPO getDefaultDepartmentByUserId(String id) throws Exception {
        Connection conn = Config.getConnection();
        try {
            return Config.getDefaultDepartmentByUserId(id, conn);
        }
        catch (Exception e) {
            MyException.deal(e);
        }
        finally {
            Database.close(conn);
        }
        return null;
    }

    public static int getSystemSequence() throws Exception{
        return MySQLDao.getSequence("system");
    }

    public static int getSystemSequence(Connection conn) throws Exception{
        return MySQLDao.getSequence("system", conn);
    }

    /**
     * 根据公司全名称获取贡献的字母缩写
     * @param companyName
     * @return
     */
    public static String getDepartmentPrefix4OA(String companyName) {
        if (companyName.equals("深圳市厚币财富投资管理有限公司")) {
            return "HBCF";
        }  if (companyName.equals("深圳市厚币财富投资管理有限公司云南分公司")) {
            return "HBCFYN";
        }
        if (companyName.equals("上海展瑞新富股权投资基金管理有限公司")) {
            return "ZRXF";
        } if (companyName.equals("深圳厚币芯通科技有限公司")) {
            return "HBXT";
        } if (companyName.equals("深圳厚币欣荣财富管理有限公司")) {
            return "HBXR";
        }
        return "";
    }

    /**
     * 根据模块的id获取模块的英文缩写
     * @param WorkFlowID
     * @return
     */
    public static String getWorkFlow4OA(int WorkFlowID){
        String temp ="";

        switch(WorkFlowID){
            case 1:  temp=""; break;
            case 2:  temp=""; break;
            case 3:  temp=""; break;
            case 4:  temp="YZ"; break;
            case 5:  temp=""; break;
            case 6:  temp="ZJ"; break;
            case 7:  temp="FY"; break;
            case 8:  temp="CL"; break;
            case 9:  temp="GDZC"; break;
            case 10:  temp="XXBS"; break;
            case 11:  temp="QJXJ"; break;
            case 12:  temp="CC"; break;
            case 13:  temp="CP"; break;
            case 14:  temp="DF"; break;
            case 15:  temp="JK"; break;
            case 16:  temp="YZ"; break;
            case 17:  temp="ZLBS"; break;
            case 23:  temp="FY"; break;
            case 24:  temp="CL"; break;
            case 25:  temp="ZJ"; break;
            case 26:  temp="QJXJ"; break;
        }
        return temp;
    }
    /**
     * 获取表示列
     * @param conn
     * @return
     * @throws Exception
     */
    public static int getOASequence(Connection conn) throws Exception{
        return MySQLDao.getSequence("oa", conn);
    }



    public static Connection getConnection() throws Exception {
        return Database.getConnection();
    }

    public static String getSystemSQL(String key) throws Exception {

        if (systemSQL == null) {
            DatabaseSQL dbSQL = new DatabaseSQL();
            dbSQL.newSQL("SELECT * from system_kv where GroupName='SQL'");
            systemSQL = MySQLDao.search(dbSQL.getSQL(), dbSQL.getParameters(), KVPO.class, null);
        }

        for (KVPO config : systemSQL) {
            if (config.getK().equalsIgnoreCase(key)) {
                return  config.getV().toString();
            }
        }

        return null;
    }



    public static List<KVPO> getBanks(String parameter, Connection conn) throws Exception {

        KVService kvService = Config.getBeanByName("kvService", KVService.class);

        KVPO kvpo = new KVPO();
        kvpo.setState(Config.STATE_CURRENT);
        kvpo.setGroupName("Bank");
        kvpo.setParameter(parameter);

        List<KVPO> listKVPO = kvService.getListKVPO(kvpo, conn);

        return listKVPO;

    }

    /**
     * 获得系统变量值
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String getSystemVariable(String key) throws Exception {
        String value = "";

        XmlHelper xml = new XmlHelper( Config.getConfigSystemFile() );
        value = xml.getValue ( "//config/variables/variable[@key='" + key + "']" );

        if (StringUtils.isEmpty(value)) {
            value = Config.getSystemConfig(key);
        }

        if (StringUtils.isEmpty(value)) {
            MyException.newInstance("无法找到【"+key+"】").throwException();
        }

        return value;

//        return Config.getSystemConfig(key);
    }

    public static int getSystemVariableAsInt(String key) throws Exception {
        String value = "";
        XmlHelper xml = new XmlHelper( Config.getConfigSystemFile() );
        value = xml.getValue ( "//config/variables/variable[@key='" + key + "']" );
        return Integer.parseInt(value);

//        return Config.getSystemConfigInt(key);
    }

    public static String getLanguageVariable(String key) throws Exception {
        String value = "";
        XmlHelper xml = new XmlHelper( Config.getConfigLanguageFile() );
        value = xml.getValue ( "//config/display-words-config/text[@key='" + key + "']" );
        return value;
    }

    /**
     * 获得错误变量值
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String getErrorVariable(String key) throws Exception {
        String value = "";
        XmlHelper xml = new XmlHelper(Config.getConfigErrorFile());
        value = xml.getValue ( "//config/variables/variable[@key='" + key + "']" );
        return value;
    }

    /**
     * 获得异常变量值
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String getExceptionVariable(String key) throws Exception {
        String value = "";
        XmlHelper xml = new XmlHelper(Config.getConfigExceptionFile());
        value = xml.getValue ( "//config/variables/variable[@key='" + key + "']" );
        return value;
    }

    /**
     * 通过返回的错误或异常码，寻找错误或异常信息
     *
     * 说明：优先寻找错误再寻找异常
     *
     * @param code
     * @return
     */
    public static String getAllinpayFailedMessage(String code) throws Exception {
        if(!"".equals(Config.getErrorVariable("error.allinpay." + code))) {
            return Config.getErrorVariable("error.allinpay." + code);
        } else if (!"".equals(Config.getExceptionVariable("exception.allinpay." + code))) {
            return Config.getExceptionVariable("exception.allinpay." + code);
        } else {
            return "";
        }
    }

    public static boolean hasPermission(String permissionName, HttpServletRequest request) {
        boolean has = false;
        if (request != null && request.getSession() != null) {
            permissionName = Config.SPLIT_LETTER + permissionName + Config.SPLIT_LETTER;
            String permissionString = (String)request.getSession().getAttribute(SESSION_PERMISSION_STRING);
            return permissionString.contains(permissionName);
        }
        return has;
    }

    /**
     * 获取用户菜单和权限
     * @param userId 用户ID
     * @return 用户菜单集合
     * @throws Exception
     */
    public static List<MenuPO> getUserMenusAndPermission(String userId, Connection conn) throws Exception {
        String sql = "select distinct m.* from system_positionpermission pp, system_positionuser pu, system_user u, system_menu m" +
                " where u.id=pu.userId and pp.positionId=pu.positionId " +
                " and pp.permissionId=m.id and u.id='"+ Database.encodeSQL(userId) +"' and m.type=0";
        LogService.debug("获得用户权限，Config.getUserMenusAndPermission(): " + sql, Config.class);
        List<MenuPO> menus = MySQLDao.query(sql, MenuPO.class, null, conn);
        return menus;
    }

    public static List<MenuPO> getUserMenusAndPermission(String userId) throws Exception {
        return getUserMenusAndPermission(userId);
    }

    /**
     * 获取用户菜单
     * @param userId 用户ID
     * @return 用户菜单集合
     * @throws Exception
     */
    public static List<MenuPO> getUserMenus(String userId) throws Exception {
        String sql = "select DISTINCT m.* from system_positionpermission pp, system_positionuser pu, system_user u, system_menu m " +
                " where m.type=1 and u.id=pu.userId and pp.positionId=pu.positionId and pp.permissionId=m.id and u.id='"+userId+"' order by m.orders";
        List<MenuPO> menus = MySQLDao.query(sql, MenuPO.class, null);
        return menus;
    }

    /**
     * 获取用户权限
     *
     * 修改人：leevits
     * 时间：2015年7月14日 23:13:28
     * 内容：
     * 如果没有查到任何权限，则返回一个空的字符串
     * @param userId
     * @return
     * @throws Exception
     */
    public static String getUserPermissions(String userId, Connection conn) throws Exception {
        StringBuffer sbPermissionNames = new StringBuffer(Config.SPLIT_LETTER);
        List<MenuPO> menus = getUserMenusAndPermission(userId, conn);
        for (MenuPO menu : menus) {
            sbPermissionNames.append(menu.getPermissionName()).append(Config.SPLIT_LETTER);
        }
        LogService.debug("用户获得权限：" + sbPermissionNames.toString(), Config.class);

        // 没有查到任何权限
        if (sbPermissionNames.toString().equals(Config.SPLIT_LETTER)) {
            return null;
        }

        return sbPermissionNames.toString();
    }

    /**
     * 获取用户权限树
     * @param userId 用户ID
     * @return 用户权限树
     * @throws Exception
     */
    public static String getUserPermissions4Tree(String userId) throws Exception {
        List<String> list = new ArrayList<String>();
        List<MenuPO> menus = getUserMenusAndPermission(userId);
        Tree menuRoot = TreeOperator.createForest();
        for(MenuPO tempMenu : menus) {
            Tree tree = new Tree(tempMenu.getId(),tempMenu.getName(), tempMenu.getParentId(), tempMenu);
            TreeOperator.add(menuRoot, tree);
        }
        doGetUserPermissions(menuRoot, Config.SPLIT_LETTER, list);
        //menuRoot.
        StringBuffer sbPermissionNames = new StringBuffer();
        for (int i = 0; list != null && i < list.size(); i++) {
            String permissions = list.get(i);
            permissions = StringUtils.removeLastLetters(permissions, Config.CONNECT_LETTER);
            sbPermissionNames.append(permissions);
        }
        if (sbPermissionNames.length() > 0) {
            sbPermissionNames.append(Config.SPLIT_LETTER);
        }
        return sbPermissionNames.toString().replaceAll("ROOT☆根☆","");
    }

    private static void doGetUserPermissions(Tree forest, String parentNames, List<String> list) {
        if (forest != null) {
            parentNames = parentNames + forest.getName() + Config.CONNECT_LETTER;
            if (forest.getChildren() == null || forest.getChildren().size() == 0) {
                list.add(parentNames);
            }
            for (Tree child : forest.getChildren()) {
                doGetUserPermissions(child, parentNames, list);
            }
        }
    }


    /**
     * 通过配置名来获取配置的文件
     * name取值：default/language/system
     * @param name
     * @return
     */
    public static File getConfigFile (String name) throws Exception{

        String path = "classpath:config/common/"+name+".xml";
        Resource resource = getContext().getResource(path);
        File configFile =  resource.getFile();

        return configFile;
    }


    public static InputStream getConfigFileStream (String name) throws Exception{

        String path = "classpath:config/common/"+name+".xml";
        Resource resource = getContext().getResource(path);
        return resource.getInputStream();
    }

    public static InputStream getConfigDefaultFileStream () throws Exception{
        return Config.getConfigFileStream("default");
    }

    public static File getConfigDefaultFile() throws Exception {
        return Config.getConfigFile("default");
    }

    public static File getConfigLanguageFile() throws Exception {
        return Config.getConfigFile("language");
    }

    public static File getConfigSystemFile() throws Exception {
        return Config.getConfigFile("system");
    }

    public static File getConfigErrorFile() throws Exception {
        return Config.getConfigFile("error");
    }

    public static File getConfigExceptionFile() throws Exception {
        return Config.getConfigFile("exception");
    }

    public static File getConfigSecurityFile() throws Exception {
        return Config.getConfigFile("security");
    }

    public static File getConfigBankFile() throws Exception {
        return Config.getConfigFile("bank");
    }


    public static void main ( String[] args ) {
        try {
            ;
        }
        catch (Exception e) {
            e.printStackTrace ();
        }
    }

    /**
     * 获取Web根目录
     * @return
     * @throws Exception
     */
    public static String getWebRoot() throws Exception {
        XmlHelper xml = new XmlHelper( Config.getConfigDefaultFile() );
        String webroot = xml.getValue ( "//config/web-root" );
        return webroot;
    }


    public static String getModernSaleManagementPages() throws Exception {
        return Config.getWebRoot() + "/modern/pages/sale_management";
    }

    public static String getModernCustomerManagementPages() throws Exception {
        return Config.getWebRoot() + "/modern/pages/customer_management";
    }


    /**
     * 获得class文件夹的同级目录
     * 已经处理路径中有空格的问题
     * @return
     * @throws Exception
     */
    public static String getClassFolder () throws Exception {
        String pathString = "V:\\work\\02_server\\apache\\apache-tomcat-6.0.41\\webapps\\hopewealth\\WEB-INF\\classes\\";
        try {
            pathString = Config.class.getClassLoader ().getResource ( "" ).getPath ();
            pathString = pathString.replaceAll ( "\\\\", "/" ).replaceAll ( "%20", " " );
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return StringUtils.UTF8URLDecode(pathString);
    }

    public static String getClassFolder(Class clazz) throws Exception {
        String pathString = clazz.getResource ("").toString();
        pathString = pathString.replaceAll ( "\\\\", "/" ).replaceAll ( "%20", " " ).replaceAll("file:/","");
        return pathString;
    }

    public static String getDataSourceType ( String name ) throws Exception {
        String source = "";
        XmlHelper xml = new XmlHelper( Config.getConfigDefaultFile() );
        source = xml.getValue ( "//config/datasources/datasource[@name='" + name + "']/type" );
        return source;
    }

    public static String getDataSourceJNDIName(String name) throws Exception {
        String jndiName = "";
        XmlHelper xml = new XmlHelper( Config.getConfigDefaultFile() );
        jndiName = xml.getValue ( "//config/datasources/datasource[@name='" + name + "']/jndi-name" );
        return jndiName;
    }

    public static String getDataSourceDriver ( String name ) throws Exception {
        String source = "";
        XmlHelper xml = new XmlHelper ( Config.getConfigDefaultFile() );
        source = xml.getValue ( "//config/datasources/datasource[@name='" + name + "']/jdbc-driver" );
        return source;
    }

    public static String getDataSourceServerIP ( String name ) throws Exception {
        String source = "";
        XmlHelper xml = new XmlHelper ( Config.getConfigDefaultFile() );
        source = xml.getValue ( "//config/datasources/datasource[@name='" + name + "']/server-ip" );
        return source;
    }

    public static String getDataSourceDBID ( String name ) throws Exception {
        String source = "";
        XmlHelper xml = new XmlHelper ( Config.getConfigDefaultFile() );
        source = xml.getValue ( "//config/datasources/datasource[@name='" + name + "']/database-id" );
        return source;
    }

    public static String getDataSourceUser ( String name ) throws Exception {
        String source = "";
        XmlHelper xml = new XmlHelper ( Config.getConfigDefaultFile() );
        source = xml.getValue("//config/datasources/datasource[@name='" + name + "']/user" );
        return source;
    }

    public static String getDataSourcePassword ( String name ) throws Exception {
        String source = "";
        XmlHelper xml = new XmlHelper ( Config.getConfigDefaultFile() );
        source = xml.getValue ( "//config/datasources/datasource[@name='" + name + "']/password" );
        return source;
    }

    public static <T> T  getBeanByName(String name, Class<T> clazz) {

        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-service.xml");

        return context.getBean(name, clazz);
    }


    public static String getWords ( String key ) throws Exception {
        String text = "";
        XmlHelper xml = new XmlHelper ( Config.getConfigLanguageFile() );
        text = xml.getValue("//config/display-words-config/text[@key='" + key + "']" );
        return text;
    }

    public static String getWords4WebGeneralError() throws Exception {
        return Config.getWords("w.gloabl.error.unknown");
    }

    public static String getDatabaseTableName(String className) throws Exception {
        String source = "";
        //System.out.println("getDatabaseTableName:"+className);
        XmlHelper xml = new XmlHelper ( Config.getConfigDefaultFile() );
        source = xml.getValue("//config/bean-mapping/bean[@class='" + className + "']" );
        return source;
    }

//    public static String getDatabaseTablePrimaryKey (String className) throws Exception {
//        String source = "";
//        //System.out.println("getDatabaseTableName:"+className);
//        XmlHelper xml = new XmlHelper ( Config.getConfigDefaultFile() );
//        source = xml.getAttributeValue("//config/bean-mapping/bean[@class='" + className + "']","pk" );
//        return source;
//    }

    public static String getValue ( String path ) throws Exception {
        String source = "";
        try {
            XmlHelper xml = new XmlHelper ( Config.getConfigDefaultFile() );
            source = xml.getValue ( path );
        }
        catch (Exception e) {
            XmlHelper xml = new XmlHelper ( Config.getConfigDefaultFileStream() );
            source = xml.getValue ( path );
            e.printStackTrace ();
        }
        return source;
    }


    public static int getSecurityFrequence ( String actionUrl ) throws Exception {
        int seconds = -1;
        XmlHelper xml = new XmlHelper( Config.getConfigSecurityFile() );
        String value = xml.getValue ( "//config/security/actions/action[@action-url='" + actionUrl + "']/frequence");
        seconds = Integer.parseInt(value);
        return seconds;
    }

    public static int getSecurityGloablFrequenceLimitTimes () throws Exception {
        int seconds = -1;
        XmlHelper xml = new XmlHelper( Config.getConfigSecurityFile() );
        String value = xml.getValue ( "//config/security/global/frequence-limit/times");
        seconds = Integer.parseInt(value);
        return seconds;
    }

    public static int getSecurityGloablFrequenceLimitAverage () throws Exception {
        int seconds = -1;
        XmlHelper xml = new XmlHelper( Config.getConfigSecurityFile() );
        String value = xml.getValue ( "//config/security/global/frequence-limit/average");
        seconds = Integer.parseInt(value);
        return seconds;
    }

    public static String getSecurityGloablFrequenceLimitSwitch () throws Exception {
        String switchValue = "";
        XmlHelper xml = new XmlHelper( Config.getConfigSecurityFile() );
        switchValue = xml.getValue ( "//config/security/global/frequence-limit/switch");
        return switchValue;
    }


    /**
     * 获得执行器停止时间
     * @return
     * @throws Exception
     */
    public static String getTimeRunnerStopTime () throws Exception {
        String time = "";
        XmlHelper xml = new XmlHelper ( Config.getConfigDefaultFileStream() );
        Element looperElement = xml.getRoot ().element ( "timerunner" ).element ( "looper-config" ).element ("stoptime" );
        time = looperElement.getStringValue ();
        System.out.println ( "Config.getTimeRunnerStopTime(): " + time );
        return time;
    }

}
