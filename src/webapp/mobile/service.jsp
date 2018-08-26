<%@ page contentType="text/html;charset=UTF-8" import="com.youngbook.common.config.*"
         language="java" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="S" uri="/struts-tags" %>
<jsp:include page="./include/_head.jsp"></jsp:include>
<style type="text/css">
    .pinfo{
        font-size: 1em;
    }
</style>
    
<script type="text/javascript">
    $(function(){
        $('#topHeadText').html('我的客服');
        $('.telephoneName').on('click',function(){
            var telhone=$(this).prev().find('p');
            $('#personPhone').html(telhone.eq(0).text());
            $('#gongPhone').html(telhone.eq(1).text());
            $('#personPhone').attr("href","tel:"+telhone.eq(0).text());
            $('#gongPhone').attr("href","tel:"+telhone.eq(1).text());

        });
    });
</script>
<div data-role="page" id="home" data-add-back-btn="true">
	<jsp:include page="./include/_topNav.jsp"></jsp:include>
	<!-- 首页主要页面加载区 -->
	
	<div role="main" class="ui-content" style="padding:0px;">
		<ul data-role="listview" data-split-icon="phone" data-split-theme="a" data-inset="true" style="margin-top:0px;">
            <s:iterator value="salesmans">
                <li>
                    <a href="#">
                        <img src="<%=Config.getWebRoot()%>/mobile/images/service/album-bb.jpg">
                        <h2><s:property value="name"/></h2>
                        <p><s:property value="mobile"/></p>
                        <p>400-820-8820</p>
                    </a>
                    <a href="#purchase" class="telephoneName" data-rel="popup" data-position-to="window" data-transition="pop"><s:property value="name"/></a>
                </li>
            </s:iterator>
        </ul>
        <div data-role="popup" id="purchase" data-theme="a" data-overlay-theme="b" class="ui-content" style="max-width:340px; padding-bottom:2em;">
            <h3>电话拨打</h3>
            <p>请选择你要拨打的电话号码</p>
                <a  class="ui-shadow ui-btn ui-corner-all  ui-btn-inline ui-mini" id="personPhone"></a>
                <a  class="ui-shadow ui-btn ui-corner-all ui-btn-inline ui-mini" id="gongPhone"></a>
        </div>
	</div>
	<jsp:include page="./include/_panel.jsp"></jsp:include>
</div>