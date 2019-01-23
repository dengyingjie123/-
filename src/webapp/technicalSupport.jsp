<%@ page contentType="text/html;charset=UTF-8" import="com.youngbook.common.config.*"
         language="java" %>
<%@ page import="jdk.nashorn.internal.parser.Token" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title><%=Config.APP_NAME%>
    </title>
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/framework/themes/gray/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/framework/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/style/default.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/style/buttons.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/framework/fullcalendar-2.2.2/fullcalendar.css">
    <link rel="stylesheet" type="text/css" href="<%=Config.getWebRoot() %>/include/framework/fullcalendar-2.2.2/fullcalendar.print.css" media='print'>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/jquery.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/JSON-to-Table-1.0.0.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/third-party/accounting.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/easyloader.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/scripts/system/ConfigClass.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/scripts/boot.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/hashMap.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/frameworkplus.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/treeplus.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/md5.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/functions.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/extensions/validator.js"></script>
    <script type="text/javascript" src="<%=Config.getWebRoot() %>/include/framework/highcharts4/js/highcharts.js"></script>
    <style type="text/css">
        <!--
        #logo_hw {
            background: url("include/images/kepler logo 2.0.png") no-repeat;
            background-size:cover ;
            width: 150px;
            height: 150px;
            margin: -40px auto -80px auto;
        }

        #background {
            margin: 0px auto;;
            width: 529px;
            height: 310px;;
            z-index: 1;
            margin-top: 128px;
        }

        #panel {
            position: relative;
            margin-top: 70px;
        }

        .tip {
            position: absolute;
            top: 360px;
            left: 195px;
            width:424px;
            height: 29px;
        }

        .intro {
            margin-bottom:30px ;
            margin-left:45px ;
            width:408px;
            height: 5px;
        }



        -->
    </style>
</head>

<body>
<div id="logo_hw"></div>
<div id="background">
    <div class="intro">
        <a class="" style="font-size: 12px; "  target="_blank">欢迎访问开普乐大富翁资讯支持页面，请填写下面表单提交您所需要的技术支持内容，或者致电028-62708665，与我们取得联系，我们将竭诚为您服务。</a>
    </div>
    <form id="technicalSupportFrom" name="technicalSupportFrom" method="post" action="">
        <div id="panel">
            <table id="technicalSupportTable">
                <tr>
                    <td align="right">用户名：</td>
                    <td><input class="easyui-validatebox"  type="text" id="username" name="username" data-options="required:true" required="true" missingmessage="必须填写" style="width:400px" /></td>
                </tr>

                <tr>
                    <td align="right">邮&emsp;箱：</td>
                    <td><input class="easyui-validatebox" type="text" id="email" name="email" data-options="required:true" required="true" missingmessage="必须填写" style="width:400px" /></td>
                </tr>

                <tr>
                    <td align="right">描&emsp;述：</td>
                    <td><textarea id="description" name="description"  style="width: 400px;height: 150px;" ></textarea></td>
                </tr>

            </table>

            <div class="tip">
                <a class="a_doc" style="font-size: 12px" href="http://www.miitbeian.gov.cn" target="_blank">粤ICP备16072634号</a>
            </div>

        </div>

    </form>

    <div region="south" border="false" style="padding:10px; text-align: right">
        <a id="btnTechnicalSupportSubmit" class="easyui-linkbutton" iconCls="icon-ok" href="javascript:void(0)" style="margin-right: 55px" onclick="onClickTechnicalSupportSubmit()" >提交</a>
    </div>
</div>
</body>
<script type="text/javascript">

    function onClickTechnicalSupportSubmit() {
        if ($("#technicalSupportFrom").form('validate')) {




            var username = fw.getFormValueByName("username");
            var email = fw.getFormValueByName("email");
            var description = $("#description").val();
            var formValue = username + "_" + email + "_" + description;




            var url = WEB_ROOT + "/cms/saveTechnicalSupport";
            fw.post(url, "formValue=" + formValue, function (data) {
            }, null);




            fw.alert("提示","提交成功");
        }
    }
</script>
</html>