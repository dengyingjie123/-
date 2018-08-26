<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.entity.po.customer.CustomerPersonalPO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    CustomerPersonalPO loginUser = null;
    if (request.getSession().getAttribute("loginUser") != null) {
        loginUser = (CustomerPersonalPO) request.getSession().getAttribute("loginUser");
    }
%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv=“X-UA-Compatible” content=“IE=8″>
    <title>点金派</title>
    <link href="../../css/common.css" rel="stylesheet" type="text/css"/>
    <link href="../../css/index/index.css" rel="stylesheet" type="text/css"/>
    <link href="../../css/production/production.css" rel="stylesheet" type="text/css"/>

    <script src="<%=Config.getWebRoot()%>/w2/dist/js/jquery.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/dist/plus/easypiechart/jquery.easypiechart.min.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/dist/plus/formatCurrency/jquery.formatCurrency.js"></script>

    <script src="<%=Config.getWebRoot()%>/w2/js/common/fweb.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/modules/production/productionList.js"></script>
    <script src="<%=Config.getWebRoot()%>/w2/js/common/c.js"></script>
</head>

<body>
<div id="container">
    <jsp:include page="/w2/top.jsp"/>

    <%--导航栏--%>
    <div style="width: 100%;background: #fafafa;" >
        <div id="nav" class="w1200" style="padding-top: 7px;padding-bottom: 7px;">
            <a href="<%=Config.getWebRoot()%>/w2/index/ShowIndex"><img src="<%=Config.getWebRoot()%>/w2/img/logo_Assistor.png" alt="开普乐"/></a>
        <span>
          <a id="param1" href="<%=Config.getWebRoot()%>/w2/index/ShowIndex" class="item" >首页</a>
        <a id="param2" href="<%=Config.getWebRoot()%>/w2/modules/production/productionList.jsp"style="color: #d28d2a;padding-bottom: 28px;border-bottom: 2px solid #d28d2a;" >投资专区</a>

          <a id="param3" href="<%=Config.getWebRoot()%>/w2/modules/about.jsp" >新手指引</a> |
            <a id="param4" href="<%=Config.getWebRoot()%>/w2/customer/IndexShow" >我的账户</a>
        </span>
        </div>
    </div>


    <div class="position r">
        <a href="<%=Config.getWebRoot()%>/w2/index/ShowIndex">首页</a>
        >
        <a href="#">产品列表</a>
    </div>

    <!---------投资选项--------->
<%--    <div class="r fn-clear subs-wrap">


        <dl class="subs-item">
            <dt>起购金额：</dt>
            <dd>
                 <a id="param1" class="current" href="javascript:void(0);"--%>
                   <%--onclick="javascript:investMoney('', '', this);">全部</a>--%>
                <%--<a id="param2" href="javascript:void(0);" onclick="javascript:investMoney(10000,'', this);">1万以下</a>
                <a id="param3" href="javascript:void(0);"
                   onclick="javascript:investMoney(10000, 50000, this);">1万-5万元</a>
                <a id="param4" href="javascript:void(0);"
                   onclick="javascript:investMoney(50000, 100000, this);">5万-10万元</a>
                <a id="param5" href="javascript:void(0);"
                   onclick="javascript:investMoney(100000, 1000000, this);">10万-100万元</a>
                <a id="param6" href="javascript:void(0);"
                   onclick="javascript:investMoney(1000000, '', this);">100万以上</a>
            </dd>
        </dl>
        <dl class="subs-item">
            <dt>投资期限：</dt>
            <dd>
                <a id="param7" class="current" href="javascript:void(0);"
                   onclick="javascript:expiringDay('', '', this);">全部</a>
                <a id="param8" href="javascript:void(0);" onclick="javascript:expiringDay('0', '10', this);">10天以下</a>
                <a id="param9" href="javascript:void(0);"
                   onclick="javascript:expiringDay('10', '90', this);">10天-3个月</a>
                <a id="param10" href="javascript:void(0);"
                   onclick="javascript:expiringDay('90', '180', this);">3个月-6个月</a>
                <a id="param11" href="javascript:void(0);"
                   onclick="javascript:expiringDay('180', '365', this);">6个月-12个月</a>
                <a id="param12" href="javascript:void(0);" onclick="javascript:expiringDay('365', '', this);">12个月以上</a>
            </dd>
        </dl>
        <dl class="subs-item">
            <dt>预期收益率：</dt>
            <dd>
                <a id="param13" class="current" href="javascript:void(0);"
                   onclick="javascript:expectedYield('', '', this);">全部</a>
                <a id="param14" href="javascript:void(0);" onclick="javascript:expectedYield('5', '7', this);">5%-7%</a>
                <a id="param15" href="javascript:void(0);" onclick="javascript:expectedYield('7', '9', this);">7%-9%</a>
                <a id="param16" href="javascript:void(0);" onclick="javascript:expectedYield('', '9', this);">9%以上</a>
            </dd>
        </dl>

        <dl class="subs-item">
            <dt>收益方式：</dt>
            <dd>
                <a id="param17" href="javascript:void(0);" onclick="javascript:investType('', this);" class="current">全部</a>
                <a id="param18" href="javascript:void(0);" onclick="javascript:investType('0', this);">每月等额本息</a>
                <a id="param19" href="javascript:void(0);" onclick="javascript:investType('1', this);">一次性还本付息</a>
            </dd>
        </dl>

    </div>--%>


    <div id="productions" class="w1200">

        <div id="tableTitle">推荐列表</div>
        <div id="tableDetail">
            <table class="w1200" cellpadding="0" cellspacing="0">
                <tr id="firstLine">
                    <td>产品详情</td>
                    <td>预期年化收益率</td>
                    <td>产品总金额（元）</td>
                    <td>投资期限</td>
                    <td>购买进度</td>
                    <td>剩余时间</td>
                    <td>操作</td>
                </tr>
                <tbody id="productionList">
                <%--js加载产品列表--%>
                <tr><td colspan='7' class='otherLine'>
                <div id="load">
                    <div class="icon_load">
                    </div>
                     <label class="load_title">加载中</label>
                 </div>
                </td>
                </tr>
                </tbody>
            </table>
        </div>

    </div>


    <div class="pagelist">
        <nav>
            <ul class="pagination" id="pagination">
                <li><a href='javascript:void(0);' aria-label='Previous' style="color:#000000;"><span aria-hidden='true'>上一页</span></a>
                </li>
                <li class='active'><a href='javascript:void(0);' style="color:#000000;">1</a></li>
                <li><a href='javascript:void(0);' aria-label='Next' style="color:#000000;"><span
                        aria-hidden='true'>下一页</span></a></li>
            </ul>
        </nav>
    </div>
 
    <jsp:include page="/w2/bottom.jsp"/>
</div>


</body>
</html>