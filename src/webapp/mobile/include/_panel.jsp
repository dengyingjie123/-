<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<script type="text/javascript">
    $(function(){
        $('#panelClose').on('click',function(){
            $( "#menuManage" ).panel( "close");
        });
    });
</script>
<div data-role="panel" id="menuManage" data-position="right" data-display="push" data-theme="a" class="ui-panel ui-panel-position-right ui-panel-display-push ui-panel-animate ui-panel-closed ui-panel-custom1" style="background-color: F3F3F3;">

            <div class="ui-panel-inner" style="padding:0em;">
                <h3 style="padding-left:1em;">功能菜单</h3>
                <ul data-role="listview" data-inset="true" class="ui-listview ui-listview-inset ui-corner-all ui-shadow">
                    <li style="border-radius: 0px;">
						<a href="mobile/Index_execute.action" class="ui-btn ui-btn-icon-right ui-icon-carat-r ui-btn-b" data-ajax="false" style="background: #522487;border: 1px solid #E6DBF4;font-weight: 100;">首页</a>
                    </li>
					<li>
						<a href="mobile/toMyAsset.action" class="ui-btn ui-btn-icon-right ui-icon-carat-r ui-btn-b" data-ajax="false" style="background: #522487;border: 1px solid #E6DBF4;font-weight: 100;">我的资产</a>
                    </li>
					<li>
                        <a href="mobile/toMyService.action" data-ajax="false" class="ui-btn ui-btn-icon-right ui-icon-carat-r ui-btn-b" style="background: #522487;border: 1px solid #E6DBF4;font-weight: 100;">我的客服</a>
                    </li>
					<li>
                        <a href="#" class="ui-btn ui-btn-icon-right ui-icon-carat-r ui-btn-b"  data-ajax="false" style="display:none;font-weight: 100;">我的账户</a>
                    </li>
					<li>
                        <a href="mobile/toMyInfo.action" class="ui-btn ui-btn-icon-right ui-icon-carat-r ui-btn-b" data-ajax="false" style="background: #522487;border: 1px solid #E6DBF4;font-weight: 100;">我的信息</a>
                    </li>
					<li>
                        <a href="#" class="ui-btn ui-btn-icon-right ui-icon-carat-r ui-btn-b" data-ajax="false" style="display:none;font-weight: 100;">设置</a>
                    </li>
					<li style="border-radius: 0px;">
                        <a href="mobile/About.action" class="ui-btn ui-btn-icon-right ui-icon-carat-r ui-btn-b" data-ajax="false" style="background: #522487;border: 1px solid #522487;font-weight: 100;">关于我们</a>
                    </li>
				</ul>
         </div>
    </div>