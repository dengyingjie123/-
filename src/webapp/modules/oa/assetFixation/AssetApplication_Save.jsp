/**
* Created by Jepson on 2015/6/5.
*/
<%@ page contentType='text/html; charset=utf-8' language='java' errorPage='' import='com.youngbook.common.config.*' %>
<%
    String token = request.getParameter("token");
%>
<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>
<html xmlns='http://www.w3.org/1999/xhtml'>
<head>
    <meta http-equiv='Content-Type' content='text/html; charset=utf-8'/>
    <title>Untitled Document</title>
</head>
<body>
<div class='easyui-layout' fit='true'>
    <div region='center' border='false' style='padding:10px;background:#fff;border:0px solid #ccc;'>
        <form id='formAssetApplication<%=token %>' name='formAssetApplication' action='' method='post'>
            <table border='0' cellspacing='5' cellpadding='0'>
                <tr>
                    <td align="right">公司名称：</td>
                    <td colspan="3"><input type="text" class="easyui-combotree" data-options="required:'true'" id="controlString1<%=token %>"
                               name="assetApplicationVO.controlString1" style="width:500px"/></td>
                </tr>
                <tr>
                    <td align="right">部门名称：</td>
                    <td colspan="3"><input type="text" class="easyui-combotree" data-options="required:'true'" id="controlString2<%=token %>"
                               name="assetApplicationVO.controlString2" style="width:500px"/></td>
                </tr>
                <tr>
                    <td align="right">请购人</td>
                    <td><input type="text" readonly="readonly" id="applicant<%=token %>" name="applicantName"
                                style='width:150px'/></td>
                    <td align='right'>总金额</td>
                    <td><input type='text' id='moneys<%=token %>'
                               name='assetApplication.moneys' readonly="readonly" style='width:150px'/>
                        元</td>
                </tr>
                <tr>
                    <td align="right">名称</td>
                    <td><input type="text" id="productName<%=token %>" name="assetApplication.productName"
                               class='easyui-validatebox' data-options='required:true' style='width:150px'/></td>
                    <td align="right">资产类型</td>
                    <td><input type="text" class="easyui-combotree" id="assetTypeId<%=token %>"
                               name="assetApplication.assetTypeId"
                               class='easyui-validatebox' data-options='required:true' style='width:150px'/></td>
                </tr>
                <tr>
                    <td align="right">申请原因</td>
                    <td colspan="3"><textarea type="text" id="purpose<%=token %>" name="assetApplication.purpose"
                                              class='easyui-validatebox' data-options='required:true'
                                              style='resize:none;width:500px;height:50px'/></td>
                </tr>
            </table>
            <div style='height: 220px' id="assetTablePrj<%=token%>">
                <div class='easyui-tabs' fit='true' border='true' style='overflow:auto;'>
                    <div title='资产项目' style='padding:5px'>
                        <table id='assetItem<%=token%>'></table>
                    </div>
                </div>
            </div>
            <table border='0' cellspacing='5' cellpadding='0' id="assetTableCommon<%=token%>">
                <tr>
                    <td align="right">部门负责人</td>
                    <td colspan="3">
                        <textarea class='easyui-validatebox' type="text" id="departmentHeaderCommentType<%=token %>"
                                  name="assetApplication.departmentHeaderCommentType" style='width:630px;height: 30px'/>
                    </td>
                </tr>
                <tr>
                    <td align="right"> 姓名</td>
                    <td><input type="text" class='easyui-validatebox' id="departmentHeadrId<%=token %>"
                               name="assetApplication.departmentHeadrId" style='width:295px'/></td>
                    <td align="right"> 时间</td>
                    <td><input type="text" class='easyui-datetimebox' class='easyui-datetimebox' editable="false"
                               id="departmentHeaderTime<%=token %>" name="assetApplication.departmentHeaderTime"
                               editable='false' style='width:300px'/></td>
                </tr>
                <tr>
                    <td align="right">部门领导人</td>
                    <td colspan="3"><textarea class='easyui-validatebox' type="text"
                                              id="departmentLeaderCommentType<%=token %>"
                                              name="assetApplication.departmentLeaderCommentType"
                                              style='width:630px;height: 30px'/></td>
                </tr>
                <tr>
                    <td align="right"> 姓名</td>
                    <td><input type="text" class='easyui-validatebox' id="departmentLeaderId<%=token %>"
                               name="assetApplication.departmentLeaderId" style='width:295px'/></td>
                    <td align="right"> 时间</td>
                    <td><input type="text" class='easyui-datetimebox' id="departmentLeaderTime<%=token %>"
                               name="assetApplication.departmentLeaderTime" editable='false' style='width:300px'/></td>
                </tr>
                <tr>
                    <td align="right">行政部门负责人</td>
                    <td colspan="3"><textarea class='easyui-validatebox' type="text"
                                              id="administrativeDepartmentHeadCommentType<%=token %>"
                                              name="assetApplication.administrativeDepartmentHeadCommentType"
                                              style='width:630px;height: 30px'/></td>
                </tr>
                <tr>
                    <td align="right"> 姓名</td>
                    <td><input type="text" class='easyui-validatebox' id="administrativeDepartmentHeadId<%=token %>"
                               name="assetApplication.administrativeDepartmentHeadId" style='width:295px'/></td>

                    <td align="right"> 时间</td>
                    <td><input type="text" class='easyui-datetimebox' id="administrativeDepartmentHeadTime<%=token %>"
                               name="assetApplication.administrativeDepartmentHeadTime" editable='false'
                               style='width:300px'/></td>
                </tr>

                <tr>
                    <td align="right">财务部门负责人</td>
                    <td colspan="3"><textarea class='easyui-validatebox' editable="false" type="text"
                                              id="financeDepartmentHeadCommentType<%=token %>"
                                              name="assetApplication.financeDepartmentHeadCommentType"
                                              style='width:630px;height: 30px'/></td>
                </tr>
                <tr>
                    <td align="right"> 姓名</td>
                    <td><input type="text" class='easyui-validatebox' id="financeDepartmentHeadId<%=token %>"
                               name="assetApplication.financeDepartmentHeadId" style='width:295px'/></td>
                    <td align="right"> 时间</td>
                    <td><input type="text" class='easyui-datetimebox' id="financeDepartmentHeadTime<%=token %>"
                               name="assetApplication.financeDepartmentHeadTime" editable='false' style='width:300px'/>
                    </td>
                </tr>
                <tr>

                    <td align="right"> 部门验收时间</td>
                    <td colspan="2"><input type="text" class='easyui-datetimebox'
                                           id="technicalDepartmentHeadTime<%=token %>"
                                           name="assetApplication.technicalDepartmentHeadTime" editable='false'
                                           style='width:300px'/></td>
                </tr>
            </table>
            <input type='hidden' id='sid<%=token %>' name='assetApplication.sid' style='width:200px'/>
            <input type='hidden' id='id<%=token %>' name='assetApplication.id' style='width:200px'/>
            <input type='hidden' id='state<%=token %>' name='assetApplication.state' style='width:200px'/>
            <input type='hidden' id='operatorId<%=token %>' name='assetApplication.operatorId' style='width:200px'/>
            <input type='hidden' id='departmentId<%=token %>' name='assetApplication.departmentId' style='width:200px'/>
            <input type='hidden' id='applicantId<%=token %>' name='assetApplication.applicantId' style='width:200px'/>
            <input type='hidden' id='assApplicantTime<%=token %>'
                   name='assetApplication.assApplicantTime' style='width:200px'/>
            <input type="hidden"  id="applicationDepartmentId<%=token %>"
                   name="assetApplication.applicationDepartmentId" style='width:150px'/>
            <input type="hidden"  id="controlString3<%=token %>"
                   name="assetApplicationVO.controlString3" style="width:300px"/></td>
        </form>

    </div>
    <div region='south' border='false' style='text-align:right;padding:5px;background:#F4F4F4'>
        <a id='btnAssetApplicationSubmit<%=token %>' class='easyui-linkbutton' iconCls='icon-ok'
           href='javascript:void(0)'>确定</a>
        <a id='btnAssetApplicationSubmit_start<%=token %>' class='easyui-linkbutton'
           iconCls='icon-ok' href='javascript:void(0)'>业务审批</a>
        <a id='btnAssetApplicationSubmit_applay<%=token %>' class='easyui-linkbutton'
           iconCls='icon-ok' href='javascript:void(0)'>业务申请</a>
        <a class='easyui-linkbutton' iconCls='icon-cancel' href='javascript:void(0)'
           onClick="fwCloseWindow('assetApplicationWindow<%=token%>')">取消</a>
    </div>
</div>
</body>
</html>
