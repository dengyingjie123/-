<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 15-1-19
  Time: 下午9:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" import="com.youngbook.common.config.*" language="java" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/framework/themes/gray/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/framework/themes/icon.css">
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/jquery.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/scripts/callcenter/callcenter.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/frameworkplus.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/style/callcenter/style.css">
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/scripts/callcenter/callcenter.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/locale/easyui-lang-zh_CN.jsj"></script>
    <script type="text/javascript">
        var WEB_ROOT = "/core";
		var callnumber = "";
		callnumber = this.opener.callnumber;
        customertype = "";

        function Load(){
            if(callnumber.length > 0){
                $("#incomingcalldisplay_txt_incomingcallnumber").val(callnumber);
				getCustomerByCallNumber();
                var customerid = "";
                if(customertype.toString() == "personal"){
                    customerid = $("#incomingcalldisplay_lb_personalid").html();
                }
                else{
                    customerid = $("#incomingcalldisplay_lb_institutionid").html();
                }
                bindingCustomerIncomingCallsRegister(customerid);
            }
        }
		
		function getCustomerByCallNumber(){
			$.ajax({
                    type: "POST",
                    url: WEB_ROOT + "/callcenter/getCustomerByCallNumber.action",
                    cache: false,
                    data: { 'callnumber': '' + callnumber + '' },
                    success: function (data) {
                        if(checkNull(data)==false){
                            return;
                        }
                     var flag = $.parseJSON(data);
                     var obj = fw.dealReturnObject(data);
                        if(flag.message == "personal"){
                            customertype = "personal";
							$("#incomingcalldisplay_tabs").tabs('select',"个人客户");
                            setPersonalForm(obj);
                        }
                        else{
                            customertype = "institution";
							$("#incomingcalldisplay_tabs").tabs('select',"机构客户");
						}
                    }
                });
		}

        /**
         * 绑定个人客户信息
         * */
		function setPersonalForm(obj){
			$("#incomingcalldisplay_lb_personalid").html(obj["id"]);
            $("#incomingcalldisplay_lb_personalname").html(obj["name"]);
            $("#incomingcalldisplay_lb_personalsex").html(obj["sex"]);
            $("#incomingcalldisplay_lb_personalbirthday").html(obj["birthday"]);
            $("#incomingcalldisplay_lb_personalmobile").html(obj["mobile"]);
            $("#incomingcalldisplay_lb_personalmobile2").html(obj["mobile2"]);
            $("#incomingcalldisplay_lb_personalmobile3").html(obj["mobile3"]);
            $("#incomingcalldisplay_lb_personalmobile4").html(obj["mobile4"]);
            $("#incomingcalldisplay_lb_personalmobile5").html(obj["mobile5"]);
            $("#incomingcalldisplay_lb_personalphone").html(obj["phone"]);
            $("#incomingcalldisplay_lb_personalphone2").html(obj["phone2"]);
            $("#incomingcalldisplay_lb_personalphone3").html(obj["phone3"]);
            $("#incomingcalldisplay_lb_personalemail").html(obj["email"]);
            $("#incomingcalldisplay_lb_personalemail2").html(obj["email2"]);
            $("#incomingcalldisplay_lb_personalemail3").html(obj["email3"]);
            $("#incomingcalldisplay_lb_personalemail4").html(obj["email4"]);
            $("#incomingcalldisplay_lb_personalemail5").html(obj["email5"]);
            $("#incomingcalldisplay_lb_personaladdress").html(obj["address"]);
            $("#incomingcalldisplay_lb_personalcreatedate").html(obj["creattime"]);
            $("#incomingcalldisplay_lb_personalsalemanname").html(obj["salemanname"]);
		}

        /**
         *绑定机构客户信息
         **/
		function setInstitutionForm(){
            $("#incomingcalldisplay_lb_institutionid").html(obj["id"]);
            $("#incomingcalldisplay_lb_institutionname").html(obj["name"]);
            $("#incomingcalldisplay_lb_institutionmobile").html(obj["mobile"]);
            $("#incomingcalldisplay_lb_institutionmobile2").html(obj["mobile2"]);
            $("#incomingcalldisplay_lb_institutionmobile3").html(obj["mobile3"]);
            $("#incomingcalldisplay_lb_institutionmobile4").html(obj["mobile4"]);
            $("#incomingcalldisplay_lb_institutionmobile5").html(obj["mobile5"]);
            $("#incomingcalldisplay_lb_institutionphone").html(obj["phone"]);
            $("#incomingcalldisplay_lb_institutionphone2").html(obj["phone2"]);
            $("#incomingcalldisplay_lb_institutionphone3").html(obj["phone3"]);
            $("#incomingcalldisplay_lb_institutionemail").html(obj["email"]);
            $("#incomingcalldisplay_lb_institutionemail2").html(obj["email2"]);
            $("#incomingcalldisplay_lb_institutionemail3").html(obj["email3"]);
            $("#incomingcalldisplay_lb_institutionemail4").html(obj["email4"]);
            $("#incomingcalldisplay_lb_institutionemail5").html(obj["email5"]);
            $("#incomingcalldisplay_lb_institutionaddress").html(obj["address"]);
            $("#incomingcalldisplay_lb_institutioncreatedate").html(obj["creattime"]);
            $("#incomingcalldisplay_lb_institutionsalemanname").html(obj["salemanname"]);
		}

        /**
         * 绑定客户的历史来电记录
         * */
        function bindingCustomerIncomingCallsRegister(customerid){
            try{
                $("#incomingcalldisplay_datagrid_callsregister").datagrid({
                    method: 'post',
                    url: WEB_ROOT + "/callcenter/getIncomingCallRegister.action",
                    queryParams: {
                        customerid: customerid
                    },
                    loadMsg: '正在加载数据，请稍等...',
                    striped: true,
                    singleSelect: true,
                    checkOnSelect: false,
                    selectOnCheck: false,
                    fitColumns: false,
                    pagination: true,
                    pageSize: 20,
                    columns: [[
                        { field: 'sid', width: 50, align: 'center', title: '序号',hidden:true },
                        { field: 'id', width: 50, align: 'center', title: '编号',hidden:true },
                        { field: 'OptName', width: 100, align: 'center', title:'接线员' },
                        { field: 'IncomingCalls_Number', width: 100, align: 'center', title:'来电号码' },
                        { field: 'IncomingCalls_Datetime', width: 120, align: 'center', title:'时间' },
                        { field: 'IncomingCalls_Category', width: 80, align: 'center', title:'类型' },
                    ]],
                    loadFilter: function (data) {
                        return data = fw.dealReturnObject(data);
                    }
                });
            }
            catch(ex){
                alert(ex);
            }
        }

    </script>
</head>

<body onLoad="Load();">
	<table style="width:99%; overflow:auto;">
    <tr>
    	<td>
        	<input type="text" id="incomingcalldisplay_txt_incomingcallnumber" style="width:300px;font-size:15pt;color:#F00;" />
        </td>
    </tr>
    <tr>
    <td>
    <!--基础信息-->
    <div style="float:left;width:60%;border:1px solid silver;">
    <!--tabs start-->
    <div id="incomingcalldisplay_tabs" class="easyui-tabs">

        <!--个人客户-->
        <div title="个人客户">
            <table style="width:100%;">
            	<tr style="display:none;">
                	<td>编号</td>
                    <td>
                    	<span id="incomingcalldisplay_lb_personalid" name="customerpersonal.id"></span>
                    </td>
                </tr>
                <tr>
                	<td class="form_th_title">姓名</td>
                    <td class="form_th_content">
                    	<span id="incomingcalldisplay_lb_personalname" name="customerpersonal.name"></span>
                    </td>
                </tr>
                <tr>
                	<td class="form_td_title">性别</td>
                    <td class="form_td_content">
                    	<span id="incomingcalldisplay_lb_personalsex" name="customerpersonal.sex"></span>
                    </td>
                </tr>
                <tr>
                	<td class="form_td_title">生日</td>
                    <td class="form_td_content">
                    	<span id="incomingcalldisplay_lb_personalbirthday" name="customerpersonal.birthday"></span>
                    </td>
                </tr>
                <tr>
                	<td class="form_td_title">移动电话</td>
                    <td class="form_td_content">
                    	<span id="incomingcalldisplay_lb_personalmobile" name="customerpersonal.mobile"></span>
                    </td>
                </tr>
                <tr>
                	<td class="form_td_title">移动电话</td>
                    <td class="form_td_content">
                    	<span id="incomingcalldisplay_lb_personalmobile2" name="customerpersonal.mobile2"></span>
                    </td>
                </tr>
                <tr>
                	<td class="form_td_title">移动电话</td>
                    <td class="form_td_content">
                    	<span id="incomingcalldisplay_lb_personalmobile3" name="customerpersonal.mobile3"></span>
                    </td>
                </tr>
                <tr>
                	<td class="form_td_title">移动电话</td>
                    <td class="form_td_content">
                    	<span id="incomingcalldisplay_lb_personalmobile4" name="customerpersonal.mobile4"></span>
                    </td>
                </tr>
                <tr>
                	<td class="form_td_title">移动电话</td>
                    <td class="form_td_content">
                    	<span id="incomingcalldisplay_lb_personalmobile5" name="customerpersonal.mobile5"></span>
                    </td>
                </tr>
                <tr>
                	<td class="form_td_title">固定电话</td>
                    <td class="form_td_content">
                    	<span id="incomingcalldisplay_lb_personalphone" name="customerpersonal.phone"></span>
                    </td>
                </tr>
                <tr>
                	<td class="form_td_title">固定电话</td>
                    <td class="form_td_content">
                    	<span id="incomingcalldisplay_lb_personalphone2" name="customerpersonal.phone2"></span>
                    </td>
                </tr>
                <tr>
                	<td class="form_td_title">固定电话</td>
                    <td class="form_td_content">
                    	<span id="incomingcalldisplay_lb_personalphone3" name="customerpersonal.phone3"></span>
                    </td>
                </tr>
                 <tr>
                	<td class="form_td_title">电子邮件</td>
                    <td class="form_td_content">
                    	<span id="incomingcalldisplay_lb_personalemail" name="customerpersonal.email"></span>
                    </td>
                </tr>
                <tr>
                	<td class="form_td_title">电子邮件</td>
                    <td class="form_td_content">
                    	<span id="incomingcalldisplay_lb_personalemail2" name="customerpersonal.email2"></span>
                    </td>
                </tr>
                <tr>
                	<td class="form_td_title">电子邮件</td>
                    <td class="form_td_content">
                    	<span id="incomingcalldisplay_lb_personalemail3" name="customerpersonal.email3"></span>
                    </td>
                </tr>
                <tr>
                	<td class="form_td_title">电子邮件</td>
                    <td class="form_td_content">
                    	<span id="incomingcalldisplay_lb_personalemail4" name="customerpersonal.email4"></span>
                    </td>
                </tr>
                <tr>
                	<td class="form_td_title">电子邮件</td>
                    <td class="form_td_content">
                    	<span id="incomingcalldisplay_lb_personalemail5" name="customerpersonal.email5"></span>
                    </td>
                </tr>
                <tr>
                	<td class="form_td_title">地址</td>
                    <td class="form_td_content">
                    	<span id="incomingcalldisplay_lb_personaladdress" name="customerpersonal.address"></span>
                    </td>
                </tr>
                <tr>
                	<td class="form_td_title">创建时间</td>
                    <td class="form_td_content">
                    	<span id="incomingcalldisplay_lb_personalcreatedate" name="customerpersonal.createdate"></span>
                    </td>
                </tr>
                <tr>
                	<td class="form_td_title">业务员</td>
                    <td class="form_td_content">
                    	<span id="incomingcalldisplay_lb_personalsalemanname" name="customerpersonal.salemanname"></span>
                    </td>
                </tr>
            </table>
        </div>
        <!--个人客户-->

        <!--机构客户-->
        <div title="机构客户">
        <table style="width: 100%;">
            	<tr style="display:none;">
                	<td>编号</td>
                    <td>
                    	<span id="incomingcalldisplay_lb_institutionid" name="customerinstitution.id"></span>
                    </td>
                </tr>
                <tr>
                	<td class="form_th_title">姓名</td>
                    <td class="form_th_content">
                    	<span id="incomingcalldisplay_lb_institutionname" name="customerinstitution.name"></span>
                    </td>
                </tr>
                <tr>
                	<td class="form_td_title">移动电话</td>
                    <td class="form_td_content">
                    	<span id="incomingcalldisplay_lb_institutionmobile" name="customerinstitution.mobile"></span>
                    </td>
                </tr>
                <tr>
                	<td class="form_td_title">移动电话</td>
                    <td class="form_td_content">
                    	<span id="incomingcalldisplay_lb_institutionmobile2" name="customerinstitution.mobile2"></span>
                    </td>
                </tr>
                <tr>
                	<td class="form_td_title">移动电话</td>
                    <td class="form_td_content">
                    	<span id="incomingcalldisplay_lb_institutionmobile3" name="customerinstitution.mobile3"></span>
                    </td>
                </tr>
                <tr>
                	<td class="form_td_title">移动电话</td>
                    <td class="form_td_content">
                    	<span id="incomingcalldisplay_lb_institutionmobile4" name="customerinstitution.mobile4"></span>
                    </td>
                </tr>
                <tr>
                	<td class="form_td_title">移动电话</td>
                    <td class="form_td_content">
                    	<span id="incomingcalldisplay_lb_institutionmobile5" name="customerinstitution.mobile5"></span>
                    </td>
                </tr>
                <tr>
                	<td class="form_td_title">固定电话</td>
                    <td class="form_td_content">
                    	<span id="incomingcalldisplay_lb_institutionphone" name="customerinstitution.phone"></span>
                    </td>
                </tr>
                <tr>
                	<td class="form_td_title">固定电话</td>
                    <td class="form_td_content">
                    	<span id="incomingcalldisplay_lb_institutionphone2" name="customerinstitution.phone2"></span>
                    </td>
                </tr>
                <tr>
                	<td class="form_td_title">固定电话</td>
                    <td class="form_td_content">
                    	<span id="incomingcalldisplay_lb_institutionphone3" name="customerinstitution.phone3"></span>
                    </td>
                </tr>
                 <tr>
                	<td class="form_td_title">电子邮件</td>
                    <td class="form_td_content">
                    	<span id="incomingcalldisplay_lb_institutionemail" name="customerinstitution.email"></span>
                    </td>
                </tr>
                <tr>
                	<td class="form_td_title">电子邮件</td>
                    <td class="form_td_content">
                    	<span id="incomingcalldisplay_lb_institutionemail2" name="customerinstitution.email2"></span>
                    </td>
                </tr>
                <tr>
                	<td class="form_td_title">电子邮件</td>
                    <td class="form_td_content">
                    	<span id="incomingcalldisplay_lb_institutionemail3" name="customerinstitution.email3"></span>
                    </td>
                </tr>
                <tr>
                	<td class="form_td_title">电子邮件</td>
                    <td class="form_td_content">
                    	<span id="incomingcalldisplay_lb_institutionemail4" name="customerinstitution.email4"></span>
                    </td>
                </tr>
                <tr>
                	<td class="form_td_title">电子邮件</td>
                    <td class="form_td_content">
                    	<span id="incomingcalldisplay_lb_institutionemail5" name="customerinstitution.email5"></span>
                    </td>
                </tr>
                <tr>
                	<td class="form_td_title">地址</td>
                    <td class="form_td_content">
                    	<span id="incomingcalldisplay_lb_institutionaddress" name="customerinstitution.address"></span>
                    </td>
                </tr>
                <tr>
                	<td class="form_td_title">创建时间</td>
                    <td class="form_td_content">
                    	<span id="incomingcalldisplay_lb_institutioncreatedate" name="customerinstitution.createdate"></span>
                    </td>
                </tr>
                <tr>
                	<td class="form_td_title">业务员</td>
                    <td class="form_td_content">
                    	<span id="incomingcalldisplay_lb_institutionsalemanname" name="customerinstitution.salemanname"></span>
                    </td>
                </tr>
            </table>
        </div>
        <!--机构客户-->
    </div>
    <!--tabs end-->
    </div>
    <!--基础信息-->
    
    <!--历史来电-->
    <div style="float:left;border:1px solid silver;width:39%;margin-left: 5px;">
    	<div class="easyui-panel">
    	<table title="来电历史" id="incomingcalldisplay_datagrid_callsregister"></table>
        </div>
    </div>
    <!--历史来电-->
    
    <!--其他信息- ->
    <div class="easyui-panel">
        <table id="incomingcalldisplay_datagrid_personalorder"></table>
    </div>
    <! --其他信息-->
    </td>
    </tr>
	<!--新增订单表单-- >
    <tr>
    	<td>
        <span id="createorder_lb_customerid" style="display:none;"></span>

        <span id="createorder_lb_customername" style="display:none;"></span>
        <div id="toolbar">
            <a class="easyui-linkbutton button" data-options="iconCls:'icon-add',plain:true" onClick="">添  加</a>
        </div>
        <table id="createorder_table_product" class="easyui-datagrid" title="产品" data-options="toolbar:'#toolbar'">
            <thead>
            <tr>
                <th field="id" hidden="true" data-options="width:80">编号</th>
                <th field="name" hidden="true" data-options="width:200">名称</th>
            </tr>
            </thead>
        </table>
        <div class="div_button">
          <a class="easyui-linkbutton button" data-options="iconCls:'icon-save'" onClick="" style="margin-right:10px;">确  定</a>
          <a class="easyui-linkbutton button" data-options="iconCls:'icon-no'" onClick="">取  消</a>
        </div>
      </div>
      </td>
    </tr>
    <!- -新增订单表单-->
	</table>
</body>
</html>