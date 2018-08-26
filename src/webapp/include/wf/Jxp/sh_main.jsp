<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../script/jxp.js"></script>
<script src="../script/common.js"></script>
<link href="../css/style.css" rel="stylesheet" type="text/css">
</head>

<body>
<p align="center" class="TitleStyle">玉溪供电局配网停电检修申请表</p>
<form action="" method="post" name="form" id="form">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" >
    <tr>
      <td height="30"><div align="right">
        <table width="100%" border="0" cellpadding="0" cellspacing="0" >
          <tr>
            <td width="80%" height="30">&nbsp;</td>
            <td class="BoldStyle"><div align="left">NO:12345</div></td>
          </tr>
        </table>
      </div></td>
    </tr>
    <tr>
      <td><table width="100%" border="0" cellpadding="2" cellspacing="1" class="TableStyle" >
        <tr>
          <td width="54" height="30"><div align="center">申请单位</div></td>
          <td colspan="3">&nbsp;</td>
          <td width="74"><div align="center">申请人</div></td>
          <td width="67">&nbsp;</td>
          <td width="61"><div align="center">受理人</div></td>
          <td width="189">&nbsp;</td>
        </tr>
        <tr>
          <td height="30"><div align="center">停电设<br>
            备名称</div></td>
          <td colspan="7"><p>&nbsp;
          </p>
            <p>&nbsp;</p></td>
          </tr>
        <tr>
          <td height="30"><div align="center">安全措施</div></td>
          <td colspan="7"><p>&nbsp;</p>
            <p>&nbsp;</p></td>
          </tr>
        <tr>
          <td height="30"><div align="center">停电范围</div></td>
          <td colspan="7"><p>&nbsp;</p>
            <p>&nbsp;</p></td>
          </tr>
        <tr>
          <td height="30" rowspan="3"><div align="center">计划<br>
              工作<br>
            时间</div></td>
          <td width="74" height="30"><div align="center">计划时间</div></td>
          <td colspan="6"><table width="100%" border="0" cellpadding="0" cellspacing="0" >
            <tr>
              <td width="350">
                <jsp:include flush="false" page="../../Common/TimeYMDHM.jsp">
                <jsp:param name="ID" value="JHSJQ"></jsp:param>
                </jsp:include>
              </td>
              <td><div align="center">至</div></td>
              <td width="350">
                <jsp:include flush="false" page="../../Common/TimeYMDHM.jsp">
                <jsp:param name="ID" value="JHSJZ"></jsp:param>
              </jsp:include></td>
            </tr>
          </table></td>
          </tr>
        <tr>
          <td height="30"><div align="center">更改时间</div></td>
          <td colspan="6"><table width="100%" border="0" cellpadding="0" cellspacing="0" >
            <tr>
              <td width="350">
                <jsp:include flush="false" page="../../Common/TimeYMDHM.jsp">
                <jsp:param name="ID" value="GGSJQ"></jsp:param>
                </jsp:include>
              </td>
              <td><div align="center">至</div></td>
              <td width="350">
                <jsp:include flush="false" page="../../Common/TimeYMDHM.jsp">
                <jsp:param name="ID" value="GGSJZ"></jsp:param>
              </jsp:include></td>
            </tr>
          </table></td>
          </tr>
        <tr>
          <td height="30"><div align="center">延期时间</div></td>
          <td colspan="6"><table width="100%" border="0" cellpadding="0" cellspacing="0" >
            <tr>
              <td width="350">
                <jsp:include flush="false" page="../../Common/TimeYMDHM.jsp">
                <jsp:param name="ID" value="YQSJQ"></jsp:param>
                </jsp:include>
              </td>
              <td><div align="center">至</div></td>
              <td width="350">
                <jsp:include flush="false" page="../../Common/TimeYMDHM.jsp">
                <jsp:param name="ID" value="YQSJZ"></jsp:param>
              </jsp:include></td>
            </tr>
          </table></td>
          </tr>
        <tr>
          <td height="30" colspan="2"><div align="center">停复电联系方法</div></td>
          <td colspan="6">&nbsp;</td>
          </tr>
        <tr>
          <td height="30"><div align="center"><a href="javascript:openYj('YXB')">营销部</a>*</div></td>
          <td colspan="7" id="YXBYJtd">&nbsp;</td>
        </tr>
        <tr>
          <td height="30"><div align="center"><a href="javascript:openYj('PDS')">配电所</a></div></td>
          <td colspan="7" id="PDSYJtd">&nbsp;</td>
        </tr>
        <tr>
          <td height="30"><div align="center"><a href="javascript:openYj('SJB')">生技部</a>*</div></td>
          <td colspan="7" id="SJBYJtd">&nbsp;</td>
        </tr>
        <tr>
          <td height="30"><div align="center"><a href="javascript:openYj('JLD')">局领导</a>*</div></td>
          <td colspan="7" id="JLDYJtd">&nbsp;</td>
        </tr>
        <tr>
          <td><div align="center"><a href="javascript:openYj('FSPF')">方式批复</a></div>            
          <div align="center"></div></td>
          <td colspan="7">            
					  <table width="100%" border="0" cellpadding="0" cellspacing="0" ><tr>
					    <td colspan="5" id="FSPFYJtd">&nbsp;</td>
					  </tr>
              <tr>
                <td width="49%" height="30">&nbsp;</td>
                <td width="15%"><div align="right"><a href="javascript:openPz('FSPFY','FSPFSJ','FSPFPZ')">方式员</a>：</div></td>
                <td width="17%" id="FSPFYtd">&nbsp;</td>
                <td width="10%"><div align="right"><a href="javascript:openPz('FSPFJH','FSPFJHSJ','FSPFPZ')">校核</a>：</div></td>
                <td width="9%" id="FSPFJHtd">&nbsp;</td>
              </tr>
          </table></td></tr>
        <tr>
          <td><div align="center"><a href="javascript:openYj('JDPF')">继电批复</a></div>            
          <div align="center"></div></td>
          <td colspan="7">            
					  <table width="100%" border="0" cellpadding="0" cellspacing="0" >
						  <tr><td colspan="5" id="JDPFYJtd"><p>&nbsp;</p>
              </td>
              </tr>
              <tr>
                <td width="49%" height="30">&nbsp;</td>
                <td width="15%"><div align="right"><a href="javascript:openPz('JDPFY','JDPFSJ','JDPFPZ')">继电员</a>：</div></td>
                <td width="17%" id="JDPFYtd">&nbsp;</td>
                <td width="10%"><div align="right"><a href="javascript:openPz('JDPFJH','JDPFJHSJ','JDPFJHPZ')">校核</a>：</div></td>
                <td width="9%" id="JDPFJHtd">&nbsp;</td>
              </tr>
          </table></td></tr>
        <tr>
          <td height="30"><div align="center"><a href="javascript:openYj('DDS')">调度所</a></div></td>
          <td colspan="7" id="DDSYJtd"><p>&nbsp;
            </p>            </td>
          </tr>
        <tr>
          <td height="30"><div align="center"><a href="javascript:openYj('DDZ')">调度组</a></div></td>
          <td colspan="7" id="DDZYJtd"><p>&nbsp;
          </p>
            <p>&nbsp;</p></td>
          </tr>
        <tr>
          <td height="30" rowspan="2"><div align="center">时间</div></td>
          <td width="100" height="30"><div align="center"><a href="javascript:openDdzx('YG')">预告</a></div></td>
          <td width="80"><div align="center"><a href="javascript:openDdzx('XL')">下令</a></div></td>
          <td width="80"><div align="center"><a href="javascript:openDdzx('TD')">停电</a></div></td>
          <td><div align="center"><a href="javascript:openDdzx('KG')">开工</a></div></td>
          <td width="67"><div align="center"><a href="javascript:openDdzx('WG')">完工</a></div></td>
          <td width="61"><div align="center"><a href="javascript:openDdzx('FD')">复电</a></div></td>
          <td><div align="center"><a href="javascript:openBz('BZ')">备注</a></div></td>
        </tr>
        <tr>
          <td height="30"><div align="center" id="YGSJtd">选择时间</div></td>
          <td><div align="center" id="XLSJtd">选择时间</div></td>
          <td><div align="center" id="TDSJtd">选择时间</div></td>
          <td><div align="center" id="KGSJtd">选择时间</div></td>
          <td><div align="center" id="WGSJtd">选择时间</div></td>
          <td><div align="center" id="FDSJtd">选择时间</div></td>
          <td rowspan="3" valign="top" id="BZtd">&nbsp;</td>
        </tr>
        <tr>
          <td height="30"><div align="center">对方负责人</div></td>
					<td id="YGFZRtd" align="center">&nbsp;</td>
          <td id="XLFZRtd" align="center">&nbsp;</td>
          <td id="TDFZRtd" align="center">&nbsp;</td>
          <td id="KGFZRtd" align="center">&nbsp;</td>
          <td id="WGFZRtd" align="center">&nbsp;</td>
          <td id="FDFZRtd" align="center">&nbsp;</td>          
          </tr>
        <tr>
          <td height="30"><div align="center">当值调度员</div></td>
					<td id="YGDDYtd" align="center">&nbsp;</td>
          <td id="XLDDYtd" align="center">&nbsp;</td>
          <td id="TDDDYtd" align="center">&nbsp;</td>
          <td id="KGDDYtd" align="center">&nbsp;</td>
          <td id="WGDDYtd" align="center">&nbsp;</td>
          <td id="FDDDYtd" align="center">&nbsp;</td>          
          </tr>
      </table>
        <div align="center"><br>
          <input type="button" name="Button" value="发送" onClick="openLz(0)">
          <input type="button" name="Button" value="保存">
          <input type="button" name="Button" value="作废" onClick="openZf(0)">
          <input type="button" name="Button" value="取消">
      </div></td>
    </tr>
  </table>
</form>
</body>
</html>
