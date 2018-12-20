<%@ page import="com.youngbook.common.utils.HttpUtils" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="com.youngbook.common.utils.NumberUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<%
    String type = com.youngbook.common.utils.HttpUtils.getParameter(request, "type");
    String loginCustomerId = Config.getLoginCustomerInSession(request).getId();
%>

<div data-page="company-info" class="page">

    <div class="page-content hide-bars-on-scroll">

        <div class="header_bar2">
            <a href="#" class="back link"><div class="header_back"><span></span></div></a>
            <div class="header_new_title">关于开普乐</div>
        </div>

        <div class="content-block">
            <span class="txt-20">德恒普惠介绍</span>
            <br><br>
            &nbsp;&nbsp;&nbsp;&nbsp;德恒普惠，专注于政信项目金融产品投资交易，是以投资、转让为核心业务的综合服务平台，并致力于为特定机构及个人持有的资产提供优质的综合服务。<br>
            <br>
            &nbsp;&nbsp;&nbsp;&nbsp;政信项目指的是政府通过其下属的地方融资平台公司，就某个特定的基础设施建设项目向金融机构融资。该项目由当地政府承诺还款，以地方政府的债权作为标的，通过引入政府相关文件、土地或应收账款等抵质押物、国有企业无限连带责任担保等多重增信措施，加之地方政府财政收入逐年增长的态势，必要时还可通过发行地方政府债券等作为还款来源，保障按时足额还本付息。<br>
            <br>
            &nbsp;&nbsp;&nbsp;&nbsp;德恒普惠与其它金融机构合作，面向整个政信金融市场、全国各级地方政府项目，筛选债权、应收账款收益权、投资收益权及项目投资计划等优质、稳健的政信产品，所推介的政信通产品的相关基础资产合法合规，并均已在金融资产交易中心备案。<br>
            <br>
            &nbsp;&nbsp;&nbsp;&nbsp;德恒普惠在保障项目品质不降低的情况下，通过互联网金融创新，有效降低会员投资人投资政府项目的门槛，并有效降低了会员投资的风险敞口，确保会员投资人权益，并使得优质的政信债权收益产品成为平民化理财产品。同时在交易过程的资金流转由第三方支付机构托管，无论是投资或资产转让，资金流实行封闭式操作，交易过程实行资金流程监管。<br>
            <br>
            &nbsp;&nbsp;&nbsp;&nbsp;德恒普惠拥有严格的客户信息管理制度和流程，对用户的隐私信息进行严格保护。系统采用先进的加密技术与存储机制，将任何可以识别会员投资人个人身份的资料加密存储，杜绝信息泄露隐患。
        </div>

        <div class="content-block">
            &nbsp;
        </div>
        <div class="content-block">
            &nbsp;
        </div>

    </div>

</div>