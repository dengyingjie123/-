<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="../../info.jsp" %>
<%
    // 判断用户是否登陆
    CustomerPersonalPO loginUser = null;
    if (request.getSession().getAttribute("loginUser") != null) {
        loginUser = (CustomerPersonalPO) request.getSession().getAttribute("loginUser");
    } else {
        String url = Config.getWebRoot() + Config.Web_URL_Login;
        response.sendRedirect(url);
        return;
    }
%>
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv=“X-UA-Compatible” content=“IE=8″>
    <title>点金派</title>
    <link href="<%=Config.getWebRoot()%>/w2/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/member.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/index/index.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/production/production.css" rel="stylesheet" type="text/css"/>
    <link href="<%=Config.getWebRoot()%>/w2/css/customer/accountValidate.css" rel="stylesheet" type="text/css"/>

    <script src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/dist/js/bootstrap.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/c.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/fweb.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/dist/plus/formatCurrency/jquery.formatCurrency.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/framework/HopeAlert.js"></script>

    <script src="<%=Config.getWebRoot()%>/w2/js/modules/customer/mobileValidate.js"></script>
    <script src="<%=Config.getWebRoot()%>/include/extensions/md5.js"></script>

</head>

<body>
<div id="container">

    <%--top--%>
    <jsp:include page="/w2/top.jsp"/>
    <%--top 结束--%>
    <div style="width: 100%;background: #fafafa;" >
        <div id="nav" class="w1200" style="padding-top: 7px;padding-bottom: 7px;">
            <a href="<%=Config.getWebRoot()%>/w2/index/ShowIndex"><img src="<%=Config.getWebRoot()%>/w2/img/logo_Assistor.png" alt="开普乐"/></a>
        <span>
          <a id="param1" href="<%=Config.getWebRoot()%>/w2/index/ShowIndex" class="item" >首页</a>
        <a id="param2" href="<%=Config.getWebRoot()%>/w2/modules/production/productionList.jsp" class="item" >投资专区</a>

          <a id="param3" href="<%=Config.getWebRoot()%>/w2/modules/about.jsp" >新手指引</a> |
            <a id="param4" href="<%=Config.getWebRoot()%>/w2/customer/IndexShow" style="color:#d28d2a">我的账户</a>
        </span>
        </div>
    </div>
    <div class="r fn-clear">

        <div class="mobile-intro">
            <div class="ht">绑定新手机号</div>
            <div class="content">

                <form class="form-horizontal" id="mobileForm" action="<%=Config.getWebRoot()%>/w2/customer/saveMobileAuth"
                      method="post">
                    <table cellpadding="8" style="font-size:16px; margin-left:30px; color:#000000">
                        <tr>
                            <td  class="td-right">手机号</td>
                            <td colspan="2"><input type="text"   placeholder="请输入手机号"
                                                   class="form-control"  name="mobile"
                                                   id="mobile"></td>

                        </tr>
                        <tr>
                            <td  class="td-right">手机动态码</td>
                            <td  colspan="2">


                                <div class="input-group">
                                    <input type="text" style="  width: 193px;" class="form-control" name="mobiValidateCode"
                                           id="mobiValidateCode" placeholder="请输入动态码" >
                            <span class="input-group-btn">
                                <input type="button" style="  width: 102px; height: 35px;" class="btn btn-default" id="code" value="获取动态码" onclick="getMobiCode('mobcodevalue')" />
                            </span>
                                </div><!-- /input-group -->
                                <span  id="mobcodevalue" style="font-weight: bold"></span>
                            </td>
                        </tr>

                        <tr>
                            <td/>

                            <td align="left"><input type="button"    class="btns btns-primary"  id="iponeSubmmtButton"
                                                    onclick="javascript:iponeSubmitSecond()" value="下一步" ></td>
                        </tr>
                    </table>
                </form>
            </div>

        </div>
    </div>







    <jsp:include page="/w2/bottom.jsp"/>
</div>


</body>
</html>