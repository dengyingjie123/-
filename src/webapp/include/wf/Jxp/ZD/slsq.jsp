<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.youngbook.common.wf.common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.youngbook.common.wf.clientapp.*" %>
<%@ page import="com.youngbook.common.wf.admin.*" %>
<%@ page import="com.ydtf.dmis.jxp.*" %>
<html>
<head>
<meta http-equiv='pragma' content='no-cache'>
<STYLE type=text/css>    A:link {text-decoration:none;}     A:visited {text-decoration:none;}     A:active {text-decoration:none;}     A:hover {text-decoration:underline;}       A.a01:link {text-decoration:underline;}     A.a01:visited {text-decoration:underline;}     A.a01:active {text-decoration:underline;}     A.a01:hover {text-decoration:underline;}      border-right-style: 1px solid rgb(0,0,0);  border-top-style: 1px solid rgb(200,200,255);  border-bottom-style: 1px solid rgb(0,0,0) }    table{font-size:9pt;} tr{font-size:9pt;line-height:1.5;} td{font-size:9pt;font-family:"宋体";line-height:1.5;}    .p9 {font-size:9pt}  .p10 {font-size:10pt;} </STYLE><title>检修管理</title>
<%
String strOp=request.getParameter("op");
int intCurrNodeID = Integer.MAX_VALUE;
List lsjxp=new ArrayList();
if (strOp==null||strOp.equals("")){
  strOp="yykap";
}
if (strOp.equals("yykap"))
{
  List lsrl=ClientApplications.getRouteListByNodeIDAndStatu(1,4,"ACTIVE");
  Iterator itrl=lsrl.iterator();
  while (itrl.hasNext()){
    RouteList rl=(RouteList)itrl.next();
    int intYWID=Integer.parseInt(rl.getYWID());
    NewJxp newjxp=new NewJxp();
    newjxp.setID(intYWID);
    newjxp=newjxp.BuildObject();
    lsjxp.add(newjxp);

  }
}
if (strOp.equals("dslsq"))
{
  List lsrl=ClientApplications.getRouteListByNodeIDAndStatu(1,3,"ACTIVE");
  Iterator itrl=lsrl.iterator();
  while (itrl.hasNext()){
    RouteList rl=(RouteList)itrl.next();
    int intYWID=Integer.parseInt(rl.getYWID());
    NewJxp newjxp=new NewJxp();
    newjxp.setID(intYWID);
    newjxp=newjxp.BuildObject();
    lsjxp.add(newjxp);

  }
}
%>

<script language="javascript" type="text/javascript">
function setPH(jxpID) {
  window.open("setPH.jsp?YWID="+jxpID,"","width=550,height=300,scrollbars=yes");
}
</script>
</head>
<body text="#000000" bgcolor="#FFFFFF" topmargin=0 leftmargin=0>

<form method="post" action="/DMIS/JXP.nsf/Home?OpenForm&amp;Seq=1" name="_Home">
<input type="hidden" name="__Click" value="0">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><jsp:include page="title.jsp" flush="false" /></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr valign="top"><td style="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" width="100%"><table border=0><tr>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0"><a href=../index.jsp>&nbsp;<font color=blue>退出</font>&nbsp;</a></td>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0">&nbsp;
  <a href=slsq.jsp?op=yykap>
	<font color=blue>营 运 科 安 排</font></a>&nbsp;</td>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0">&nbsp;
  <a href=slsq.jsp?op=dslsq>
	<font color=blue>待 受 理 申 请</font></a>&nbsp;</td>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0">&nbsp;
  <a href=slsq.jsp?op=thxg>
	<font color=blue>退 回 修 改</font></a>&nbsp;</td>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0">&nbsp;
  <a href=slsq.jsp?op=xgdwsh>
	<font color=blue>相 关 单 位 审 核</font></a>&nbsp;</td>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0">&nbsp;
  <a href=fsbg_sq.jsp>
	<font color=green>新建方式变更单</font></a>&nbsp;</td>
<td STYLE="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" bgcolor="9F9FE0">&nbsp;
  <a href=lzsp.jsp>
	<font color=yellow>检修票流转一览表</font></a>&nbsp;</td>
</tr></table></td></tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr valign="top"><td style="border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0)" height=400 width="100%">
<font size="2" color="#0021bf">
  <Table border=0 cellpadding=2 cellspacing=1 width="100%" style="font-family: 宋体;">
	<%
        //================  方式变更单  开始 ===========================
        if (strOp.equals("yykap")) {
          List listRL = new ArrayList();
          listRL = ClientApplications.getRouteListByNodeIDAndStatu(3, 2, "ACTIVE");
          %>
	<tr>
	  <td colspan=3 align=center style="line-height:1">
		<font color=blue size=3>营 运 科 安 排 - 方 式 变 更 单</font>
	  </td></tr>
	  <tr bgcolor=#C0C0C0 style="font-family: 宋体;color:green">
            <td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>
              改变方式时间
            </td>
            <td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>
              改变方式原因
            </td>
            <td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>
              已 审 批
            </td>
          </tr>
          <%
          for (int i = 0; listRL != null && i < listRL.size(); i++) {
            RouteList rlFsbg = (RouteList)listRL.get(i);
            Fsbg fsbg = new Fsbg();
            fsbg.setYWID(rlFsbg.getYWID());
            fsbg = fsbg.BuildObject();
            %>
            <tr bgcolor=#F2F2F2>
              <td align=center width=10% nowrap><a href="fsbg_lzsp.jsp?YWID=<%=fsbg.getYWID()%>"><%=fsbg.getGBFSSJ() %></a></td>
              <td align=center width=80%><a href="fsbg_lzsp.jsp?YWID=<%=fsbg.getYWID()%>"><%=fsbg.getGBFSYY() %></a></td>
              <td align=center width=10%>&nbsp</td>
            </tr>
            <%
            }
          %>
        </table><Table border=0 cellpadding=2 cellspacing=1 width="100%" style="font-family: 宋体;">
          <%
        }
        //============== 方式变更单 完 ========================
        %>
        <tr>
					  <td colspan=8 align=center style="line-height:1">
						<font color=blue size=3><%
						String strUrl = new String();
						if (strOp.equals("yykap"))
						{
						  out.print("营 运 科 安 排");
							intCurrNodeID = 4;
							strUrl = "yykap.jsp";
						}
						if (strOp.equals("dslsq")){
						  out.print("待 受 理 申 请");
							intCurrNodeID = 3;
							strUrl = "dslsq.jsp";
						}
						if (strOp.equals("thxg")){
						  out.print("退 回 修 改");
						}
						%></font>
					  </td>
					</tr>

					<tr bgcolor=#C0C0C0 style="font-family: 宋体;color:green"><td width="35" align=center nowrap STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 '>序号</td><td width="51" align=center nowrap STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 '>票号</td><td width="50" align=center nowrap STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 '>申请时间</td><td width="66" align=center nowrap STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 '>申请单位</td><td width="98" align=center nowrap STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 '>检修设备名称</td><td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>工作内容</td><td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>停电范围</td><td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>申请开工↓</td><td align=center STYLE='border-left: 1px solid rgb(200,200,255); border-right: 1px solid rgb(0,0,0); border-top: 1px solid rgb(200,200,255); border-bottom: 1px solid rgb(0,0,0);line-height:1 ' nowrap>申请完工</td></tr>
					  <%
					  Iterator itjxp=lsjxp.iterator();
					  int i=1;
					  while (itjxp.hasNext())
					  {
						NewJxp jxpt=(NewJxp)itjxp.next();
					  %>
					  <tr bgcolor=#F2F2F2>
						<td align=center ><%=i%></td>
						<td align=center ><%=strOp.equals("yykap")?"<a href=javascript:setPH('"+jxpt.getYWID()+"')>"+(jxpt.isEmptyPH()?"设置票号":String.valueOf(jxpt.getPH()))+"</a>":jxpt.getPH()%></td>
						<td align=center >
						  <a href="<%=strUrl%>?CurrNodeID=<%=intCurrNodeID%>&YWID=<%=jxpt.getYWID()%>">
							<font color=black><%=jxpt.getSQSJ()%></font></a></td>
						<td align=center ><a href="<%=strUrl%>?CurrNodeID=<%=intCurrNodeID%>&YWID=<%=jxpt.getYWID()%>">
						  <font color=black><%=jxpt.getSQDW()%></font></a></td>
						<td align=left><a href="<%=strUrl%>?CurrNodeID=<%=intCurrNodeID%>&YWID=<%=jxpt.getYWID()%>">
						  <font color=black><%=jxpt.getSBMC()%></font></a></td>
						<td align=left width=200><a href="<%=strUrl%>?CurrNodeID=<%=intCurrNodeID%>&YWID=<%=jxpt.getYWID()%>">
						  <font color=black><%=jxpt.getGZNR()%></font></a></td>
						<td width=60 nowrap><a href="<%=strUrl%>?CurrNodeID=<%=intCurrNodeID%>&YWID=<%=jxpt.getYWID()%>">
						  <font color=black><%=jxpt.getTDFW()%></font></a></td>
						<td width=62 nowrap><a href="d<%=strUrl%>?CurrNodeID=<%=intCurrNodeID%>&YWID=<%=jxpt.getYWID()%>">
						  <font color=black><%=jxpt.getSQTDSJ()%></font></a></td>
						<td width=60 nowrap><a href="<%=strUrl%>?CurrNodeID=<%=intCurrNodeID%>&YWID=<%=jxpt.getYWID()%>">
						  <font color=black><%=jxpt.getSQFDSJ()%></font></a></td>
						</tr>
					  <%
					i++;
					}%>

					  <tr>
						<td align=right colspan=9><font color=black>共<font color=blue> <%=lsjxp!=null?String.valueOf(lsjxp.size()):"0"%> </font>行</font></td></tr>
					  </table><br>
<br>
</font><font size="2" color="#ff0000"></font><font size="2" color="#ff0000"></font><font size="2" color="#ff0000"></font><font size="2" color="#ff0000"></font><font size="2" color="#ff0000"></font><font size="2" color="#ff0000"></font><font size="2" color="#ff0000"></font><font size="2" color="#ff0000"></font><font size="2" color="#ff0000"></font><font size="2" color="#ff0000"></font></td></tr>
</table>
&nbsp
</form>
</body>
</html>
