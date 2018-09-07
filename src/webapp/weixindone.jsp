<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.utils.*" %>
<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page import="net.sf.json.JSONObject" %>
<%@ page import="net.sf.json.JSONFunction" %>
<%@ page import="com.youngbook.service.wechat.WeChatService" %>
<%@ page import="com.youngbook.entity.po.wechat.UserInfoPO" %>
<%@ page import="com.youngbook.common.ReturnObject" %>
<%@ page import="com.youngbook.common.Database" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page import="com.youngbook.service.customer.CustomerPersonalService" %>
<%@ page import="com.youngbook.service.system.UserService" %>
<%@ page import="com.youngbook.entity.po.UserPO" %>
<%@ page import="com.youngbook.common.MyException" %>
<%
    String code = request.getParameter("code");
    String state = request.getParameter("state");
    response.addHeader("Access-Control-Allow-Origin", "*");

    WeChatService weChatService = Config.getBeanByName("weChatService", WeChatService.class);

    String userType = "customer";
        /**
         * 处理返回state
         */
    if (!StringUtils.isEmpty(state)) {
        String stateJsonString = StringUtils.urlDecode(state);
        JSONObject json = JSONObject.fromObject(stateJsonString);

        userType = json.get("userType").toString();
    }

    Connection conn = Config.getConnection();

    try {

        UserInfoPO userInfoPO = weChatService.getUserInfo(code, conn);

        userInfoPO = weChatService.insertOrUpdate(userInfoPO, userType, conn);



        // 客户微信登录
        if (userType.equals("customer")) {

            CustomerPersonalPO customerPersonalPO = weChatService.getCustomerPersonalPO(userInfoPO.getId(), conn);

            // 尚未绑定用户
            if (customerPersonalPO == null) {

                String url = Config.getModernCustomerManagementPages() + "/customer/customer_bind_wechat.jsp?userInfoId="+userInfoPO.getId();

                response.sendRedirect(url);
            }
            // 已绑定用户
            else {

                CustomerPersonalService customerPersonalService = Config.getBeanByName("customerPersonalService", CustomerPersonalService.class);

                customerPersonalService.login(customerPersonalPO.getId(), conn);

                customerPersonalService.loginFinish(customerPersonalPO, session);

                response.sendRedirect(Config.getModernCustomerManagementPages() + "/production/production_list.jsp");
            }



        }
        // 销售微信登录
        else if (userType.equals("user")) {

            UserPO userPO = weChatService.getUserPO(userInfoPO.getId(), conn);

            // 尚未绑定用户
            if (userPO == null) {

                String url = Config.getModernSaleManagementPages() + "/user/user_bind_wechat.jsp?userInfoId="+userInfoPO.getId();

                response.sendRedirect(url);
            }
            // 已绑定用户
            else {

                UserService userService = Config.getBeanByName("userService", UserService.class);

                userService.login(userPO.getId(), conn);

                // 查询用户权限
                String permissionString = Config.getUserPermissions(userPO.getId(), conn);

                if (StringUtils.isEmpty(permissionString)) {
                    MyException.newInstance("此用户没有任何权限，请与管理员联系", "{'loginName':'" + userPO.getName() + "'}").throwException();
                }


                userService.loginFinish(userPO, permissionString, session);

                response.sendRedirect(Config.getModernSaleManagementPages() + "/production/production_list.jsp");
            }


        }



    }
    catch (Exception e) {
        throw e;
    }
    finally {
        Database.close(conn);
    }


%>
