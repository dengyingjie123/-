<%--
  Created by IntelliJ IDEA.
  User: Ivan
  Date: 15-4-7
  Time: 下午10:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ page import="com.youngbook.entity.po.UserPO" %>
<%
    String token = request.getParameter("token");
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Untitled Document</title>
</head>

<body>
<div class="easyui-layout" fit="true">
    <div region="center" border="false" style="padding:10px;background:#fff;border:0px solid #ccc;">
        <form id="formsealUsageWFA2<%=token %>" name="sealUsageWFA2.formsealUsageWFA2" action="" method="post">
            <table border="0" cellspacing="5" cellpadding="0" width="100%">
                <tr>
                    <td align="right">公司名称：</td>
                    <td><input type="text" class="easyui-combotree" data-options="required:'true'" id="controlString1<%=token %>"
                               name="sealUsageWFA2VO.controlString1" style="width:455px"/></td>
                </tr>
                <tr>
                    <td align="right">部门名称：</td>

                    <td><input type="text" class="easyui-combotree" data-options="required:'true'" id="controlString2<%=token %>"
                               name="sealUsageWFA2VO.controlString2" style="width:455px"/></td>
                </tr>
                <tr>
                    <td align="right">申请人</td>
                    <td><input readonly="readonly" name="sealUsageWFA2.applicantName" type="text" id="applicantName<%=token %>"
                               style="width:450px"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">申请时间</td>
                    <td><input class="easyui-datetimebox" style="width:455px" editable="false" type="text"
                               id="applicationTime<%=token %>" editable="false" readonly="readonly" name="sealUsageWFA2.applicationTime"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">发往处</td>
                    <td><input class="easyui-validatebox" data-options="required:true" style="width:455px"  type="text"
                               id="sentto<%=token %>"  name="sealUsageWFA2.sentto"/>
                    </td>
                </tr>
                <tr>
                    <td valign="top" align="center">申请用途</td>

                    <td>
                        <textarea class="easyui-validatebox" style="width:450px;height:50px;" id="applicationPurpose<%=token %>"
                                  name="sealUsageWFA2.applicationPurpose" required="true"
                                  missingmessage="必须填写"></textarea>
                    </td>
                </tr>
                <!-- 附件上传开始 -->
                <tr id="uploadTR<%=token%>">
                    <td align="right">附件上传</td>
                    <td><a id="btnUpload<%=token %>" class="easyui-linkbutton" iconCls="icon-ok"
                           href="javascript:void(0)">正在查询上传记录...</a></td>
                </tr>

            </table>
            <div>
                <div style='height: 250px' id="sealUsageItem2<%=token%>">
                    <div class='easyui-tabs' fit='true' border='true' style='overflow:auto;'>
                        <div title='用章类型' style='padding:5px'>
                            <table id='SealUsageItem2Table<%=token%>'></table>
                        </div>
                    </div>
                </div>

            </div>
            <input type="hidden" id="operatorId<%=token %>" name="sealUsageWFA2.operatorId" style="width:200px"/>
            <input type="hidden" id="sid<%=token %>" name="sealUsageWFA2.sid" style="width:200px"/>
            <input type="hidden" id="id<%=token %>" name="sealUsageWFA2.id" style="width:200px"/>
            <input type="hidden" id="state<%=token %>" name="sealUsageWFA2.state" style="width:200px"/>
            <input type="hidden" id="operateTime<%=token %>" name="sealUsageWFA2.operateTime" style="width:200px"/>
            <input type="hidden" id="applicantId<%=token %>" name="sealUsageWFA2.applicantId" style="width:200px"/>
            <%--是否外带--%>
            <input type="hidden" id="isOut<%=token %>" name="sealUsageWFA2.isOut" style="width:200px"/>
            <input type="hidden"  id="controlString3<%=token %>"
                   name="sealUsageWFA2VO.controlString3" style="width:300px"/></td>

        </form>
    </div>
    <div region="south" border="false" style="text-align:right;padding:5px;background:#F4F4F4">
        <a id="btnsealUsageWFA2Submit<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)">确定</a>
        <a id="btnsealUsageWFA2Submit_start<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)">业务审批</a>
        <a id="btnsealUsageWFA2Submit_applay<%=token %>" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)">业务申请</a>
        <a class="easyui-linkbutton" iconCls="icon-cancel" href="javascript:void(0)"
           onClick="fwCloseWindow('SealUsageWFA2Window<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>