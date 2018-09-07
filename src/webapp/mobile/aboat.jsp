<%@ page contentType="text/html;charset=UTF-8" import="com.youngbook.common.config.*"
         language="java" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<jsp:include page="./include/_head.jsp"></jsp:include>
<style type="text/css">
    .pinfo{
        font-size: 1em;
    }
</style>
<script type="text/javascript">
    $(function(){
        $('#topHeadText').html('关于我们');
    });
</script>
<div data-role="page" id="about" data-add-back-btn="true">
	<jsp:include page="./include/_topNav.jsp"></jsp:include>	
	<div role="main" class="ui-content">
       <div class="ui-grid-a">
            <div class="ui-block-a">
                <img src="<%=Config.getWebRoot()%>/mobile/images/about/333.jpg" alt="公司logo" style="width:100%;">
            </div>
            <div class="ui-block-b">
                <ul data-role="listview">
                    <li>
                        <p>电话：<span>400 871 0708</span></p>
                    </li>
                    <li>
                        <p>地址：<span>昆明市南亚第一城 C4-2-1205</span></p>
                    </li>
                    <li>
                        <p>传真：<span>0871-64315920</span></p>
                    </li>
                </ul>
            </div>
        </div>
        <div>
            <p style="text-indent:2em;;letter-spacing: 0.2em;word-spacing:8px;">&nbsp;</p>
            <p style="text-indent:2em;;letter-spacing: 0.2em;word-spacing:8px;">深圳市厚币财富投资管理有限公司（简称：“厚币财富”）是一家专注于高端人群财富管理的第三方理财顾问机构，依托强大的股东背景和行业资源，在产品采集、风险控制、资产管理等方面均具有领先优势，能够为中国高端人士提供独立、客观、专业、全方位的财富管理服务，是高净值客户的“财富管家”。</p>
            <p style="text-indent:2em;;letter-spacing: 0.2em;word-spacing:8px;"> 厚币财富作为独立的第三方理财顾问机构，拥有一支专业、资深的理财规划师团队，大部分理财顾问具有银行、证券、保险等金融机构从业经验和专业背景。他们将以专注的工作态度，专业的胜任精神，专情的服务方式，坚持以客户需求为导向，保护客户利益为最高原则，通过严格的风险控制流程，筛选优质理财产品结合客户的财务状况和风险偏好，为客户量身定制个性化的理财方案，帮助客户实现稳定、安全的财富增值。                </p>
            <p style="text-indent:2em;;letter-spacing: 0.2em;word-spacing:8px;">厚币财富的未来目标是利用品牌、产品、研究、人才和资金优势，创建国内一流国际知名的中国财富管理服务机构，打造业内领先的财富管理服务平台，提供专业严谨的财富管理解决方案，服务高端私人和机构客户，运用信托计划、证券基金、私募股权、和投连保险等全金融市场领域的产品，协助客户合理配置资产、设计理财方案及防范各种风险，实现财富的保值和增值。</p>
            <p style="text-indent:2em;;letter-spacing: 0.2em;word-spacing:8px;"> 厚币财富秉承“诚信、创新、专业、服务”的理念，志在成为高端客户综合理财方案的设计者，领先财富管理服务品牌的创造者。“厚币财富，让您的财富厚起来！”</p>
        </div>
	</div>
	<jsp:include page="./include/_panel.jsp"></jsp:include>
</div>