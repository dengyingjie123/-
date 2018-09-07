<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%@ page import="com.youngbook.common.wf.admin.*" %>
<%@ page import="com.ydtf.dmis.jxp.*" %>

<%
NewJxp newjxp=new NewJxp();
List lsjxp=newjxp.getLZSPJxp();

Fsbg fsbg = new Fsbg();
List lsfsbg = fsbg.getFsbg(2,8);
%>
<html>
<head>
<meta http-equiv='pragma' content='no-cache'>
<STYLE type=text/css>    A:link {text-decoration:none;}     A:visited {text-decoration:none;}     A:active {text-decoration:none;}     A:hover {text-decoration:underline;}       A.a01:link {text-decoration:underline;}     A.a01:visited {text-decoration:underline;}     A.a01:active {text-decoration:underline;}     A.a01:hover {text-decoration:underline;}      border-right-style: 1px solid rgb(0,0,0);  border-top-style: 1px solid rgb(200,200,255);  border-bottom-style: 1px solid rgb(0,0,0) }    table{font-size:9pt;} tr{font-size:9pt;line-height:1.5;} td{font-size:9pt;font-family:"宋体";line-height:1.5;}    .p9 {font-size:9pt}  .p10 {font-size:10pt;} body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
</STYLE><title>检修管理</title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8"></head>
<body text="#000000" bgcolor="#FFFFFF">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><jsp:include page="title.jsp" flush="false" /></td>
  </tr>
</table>
<Table border=0 cellpadding=2 cellspacing=1 width="100%" style="font-family: 宋体;">
  <tr>
	  <td colspan=9 align=center style="line-height:1"><font color=blue size=3>方式变更单流转一览表</font></td>
	</tr>
	<tr bgcolor="#C0C0C0" style="font-family: 宋体;color:green">
	  <td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>改变方式时间</td>
		<td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>改变方式原因</td>
		<td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>营 运</td>
		<td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>营运审核</td>
		<td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>保 护</td>
		<td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>保护审核</td>
		<td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>自动化</td>
		<td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>领 导</td>
		<td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>调度审核</td>
  </tr>
      <%
      for (int i = 0; lsfsbg != null && i < lsfsbg.size(); i++) {
        fsbg = (Fsbg)lsfsbg.get(i);
        List listRL = ClientApplications.getRouteListByYWIDAndNodeIDAndStatu(3,Integer.MAX_VALUE,fsbg.getYWID(),"ACTIVE");
        RouteList rl = (RouteList)listRL.get(0);
        int nodeID = rl.getCurrentNode();
        %>
        <tr bgcolor="#F2F2F2">
          <td align=center width=10%><a href="fsbg_lzsp.jsp?YWID=<%=fsbg.getYWID()%>"><font color=black><%=fsbg.getGBFSSJ()%></font></a></td>
          <td align=center width=60%><a href="fsbg_lzsp.jsp?YWID=<%=fsbg.getYWID()%>"><font color=black><%=fsbg.getGBFSYY() %></font></a></td>
          <td align=center width=5% nowrap><a href="fsbg_lzsp.jsp?YWID=<%=fsbg.getYWID()%>"><%=nodeID==2?"<font color='blue'>待办</font>":!fsbg.isEmptyYYKYJ()?"<font color='green'>已办</font>":"<font color='red'>未转</font>"%></a></td>
          <td align=center width=5% nowrap><a href="fsbg_lzsp.jsp?YWID=<%=fsbg.getYWID()%>"><%=nodeID==3?"<font color='blue'>待办</font>":!fsbg.isEmptyYYKSHYJ()?"<font color='green'>已办</font>":"<font color='red'>未转</font>"%></a></td>
          <td align=center width=5% nowrap><a href="fsbg_lzsp.jsp?YWID=<%=fsbg.getYWID()%>"><%=nodeID==4?"<font color='blue'>待办</font>":!fsbg.isEmptyJDKYJ()?"<font color='green'>已办</font>":"<font color='red'>未转</font>"%></a></td>
          <td align=center width=5% nowrap><a href="fsbg_lzsp.jsp?YWID=<%=fsbg.getYWID()%>"><%=nodeID==5?"<font color='blue'>待办</font>":!fsbg.isEmptyJDKSHYJ()?"<font color='green'>已办</font>":"<font color='red'>未转</font>"%></a></td>
          <td align=center width=5% nowrap><a href="fsbg_lzsp.jsp?YWID=<%=fsbg.getYWID()%>"><font color=red>未转</font></a></td>
          <td align=center width=5% nowrap><a href="fsbg_lzsp.jsp?YWID=<%=fsbg.getYWID()%>"><%=nodeID==7?"<font color='blue'>待办</font>":!fsbg.isEmptyLDYJ()?"<font color='green'>已办</font>":"<font color='red'>未转</font>"%></a></td>
          <td align=center width=5% nowrap><a href="fsbg_lzsp.jsp?YWID=<%=fsbg.getYWID()%>"><%=nodeID==8?"<font color='blue'>待办</font>":!fsbg.isEmptyDDKYJ()?"<font color='green'>已办</font>":"<font color='red'>未转"%></a></td>
        </tr>
        <%
        }
      %>
	<tr>
	  <td align=right colspan=9><font color=black>共<font color=blue> <%=lsfsbg.size()%> </font>行</font></td>
	</tr>
</table>
<Table border=0 cellpadding=2 cellspacing=1 width="100%" style="font-family: 宋体;"><tr>
<td colspan=7 align=right style="line-height:1"><font color=blue size=3>申请票流转一览表<font><font color=green size=2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font>
</td><td colspan=7>
<table border=0><tr>
<td>&nbsp;</td>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="1B8AC9">&nbsp;<font color=white>按本人待办查看</font>&nbsp;</td>
</tr></table></td></tr>
<tr bgcolor=#C0C0C0 style="font-family: 宋体;color:green">
  <td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>
	申请单位</td>
  <td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>
	票号</td>
  <td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>
	检修设备名称</td>
  <td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>
	工作内容</td>
  <td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>
	停电范围</td>
  <td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>
	批准开工</td>
  <td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>
	批准完工</td>
  <td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>
	营 运</td>
  <td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>
	营运审核</td>
  <td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>
	保 护</td>
  <td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>
	保护审核</td>
  <td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>
	自动化</td>
  <td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>
	领 导</td>
  <td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>
	调度审核</td>
  </tr>

  <%
  Iterator itjxp=lsjxp.iterator();
  while (itjxp.hasNext())
  {
	boolean yyk=false;
	boolean yyksh=false;
	boolean bh=false;
	boolean bhsh=false;
	boolean zdh=false;
	boolean ld=false;
	boolean ddsh=false;
	NewJxp njxp=(NewJxp)itjxp.next();
	List lsrl=ClientApplications.getActiveYW(String.valueOf(njxp.getID()),1);
	Iterator itrl=lsrl.iterator();
        while (itrl.hasNext()){
          RouteList rl=(RouteList)itrl.next();
          int intCurrentNode=rl.getCurrentNode();
          switch (intCurrentNode){
            case 4: yyk=true; break;
            case 5: yyksh=true; break;
            case 6: bh=true; break;
            case 7: bhsh=true; break;
            case 8: zdh=true; break;
            case 9: ld=true; break;
            case 10:ddsh=true;break;
          }


	}
  %>

  <tr bgcolor=#F2F2F2><td align=center width=5%><%=njxp.getSQDW()%></td>
	<td align=center width=5%><%=njxp.getPH()%></td>
	<td align=center width=20%>
	  <font color=black>
		<%=njxp.getSBMC()%>
	  </font>
	</td>
	<td width=25%>
	<font color=black>
		<%=njxp.getGZNR()%>
	  </font>
	</td>
	<td width=15%><font color=black>
	  <%=njxp.getTDFW()%>
	</font>
  </td>
  <td width=5% nowrap><font color=black>
	<%=njxp.getPZTDSJ()%>
  </font></td>
  <td width=5% nowrap><font color=black>
   <%=njxp.getPZFDSJ()%></font></td>
  <td align=center width=5% nowrap><font color=blue>
	<%=yyk?"<font color=green><a href=yykap.jsp?YWID="+njxp.getYWID()+"&CurrNodeID=4>待办</a></font>":njxp.getYYKPZ().equals("1")?"已办":"<font color=red>未转</font>"%></font></td>
  <td align=center width=5% nowrap><font color=blue>
	<%=yyksh?"<font color=green><a href=yyksh.jsp?YWID="+njxp.getYWID()+"&CurrNodeID=5>待办</a></font>":njxp.getYYKSHPZ().equals("1")?"已办":"<font color=red>未转</font>"%></font></td>
  <td align=center width=5% nowrap><font color=blue>
	<%=bh?"<font color=green><a href=jdk.jsp?YWID="+njxp.getYWID()+"&CurrNodeID=6>待办</a></font>":njxp.getJDKPZ().equals("1")?"已办":"<font color=red>未转</font>"%></font></td>
  <td align=center width=5% nowrap><font color=blue>
	<%=bhsh?"<font color=green><a href=jdksh.jsp?YWID="+njxp.getYWID()+"&CurrNodeID=7>待办</a></font>":njxp.getJDKSHPZ().equals("1")?"已办":"<font color=red>未转</font>"%></font></td>
  <td align=center width=5% nowrap>
	<%=zdh?"<font color=green><a href=zdhk.jsp?YWID="+njxp.getYWID()+"&CurrNodeID=8>待办</a></font>":njxp.getZDHKYJ().equals("")?"<font color=red>未转</font>":"已办"%></td>
  <td align=center width=5% nowrap><font color=blue>
	<%=ld?"<font color=green><a href=ld.jsp?YWID="+njxp.getYWID()+"&CurrNodeID=9>待办</a></font>":njxp.getLDPZ().equals("1")?"已办":"<font color=red>未转</font>"%></font></td>
  <td align=center width=5% nowrap><font color=blue>
	<%=ddsh?"<font color=green><a href=ddksh.jsp?YWID="+njxp.getYWID()+"&CurrNodeID=10>待办</a></font>":njxp.getDDKPZ().equals("1")?"已办":"<font color=red>未转</font>"%></font></td>
  </tr>
  <%}%>
  <tr><td align=right colspan=14><font color=black>共<font color=blue> <%=lsjxp!=null?String.valueOf(lsjxp.size()):"0"%> </font>行</font></td></tr></table>
</body>
</html>
