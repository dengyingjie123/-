package com.youngbook.common.wf.processdefine;

/**
 *
 * <p>Title: 工作流页面自动生成类</p>
 *
 * <p>Description: 根据工作流编号，自动生成该工作流的流转页面</p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * 程序：李扬
 * 时间：2004-11-16
 */
import java.util.*;
import javax.servlet.jsp.JspWriter;
import com.youngbook.common.wf.common.BizField;
import com.youngbook.common.wf.common.Tools;
import com.youngbook.common.wf.processdefine.BizData;
public class PageMaker {

    protected String strPageName = new String();  //页面名称
    protected String strDir = new String();  //页面所在文件夹名
    protected String strPackage = new String();  //所属业务类的包结构
    protected String strClassName = new String();  //类名
    protected String strClassInstance = new String();  //类的实例
    protected int intWorkflowID = Integer.MAX_VALUE;  //工作流编号
    protected List listField = new ArrayList();  //保存字段信息
    protected BizData bizdata = new BizData();  //保存业务数据配置
    protected String strPrimaryKey = new String(); //记录了关键字的

    /**
     * 构造函数
     * @throws Exception
     */
    public PageMaker() throws Exception {
    }

    /**
     * 说明：设置包结构
     * @param Package String
     */
    public void setPackage(String Package) {
        strPackage = Package;
    }

    /**
     * 设置主键
     * @param PrimaryKey String
     */
    public void setPrimaryKey(String PrimaryKey) {
        strPrimaryKey = PrimaryKey;
    }

    /**
     * 设置工作流编号
     * @param WorkflowID int
     */
    public void setWorkflowID(int WorkflowID) {
        intWorkflowID = WorkflowID;
    }

    /**
     * 设置类名
     * @param ClassName String
     */
    public void setClassName(String ClassName) {
        strClassName = ClassName;
        strClassInstance = "obj" + ClassName;
    }

    /**
     * 设置页面存放目录
     * @param Dir String
     */
    public void setDir(String Dir) {
        strDir = Dir;
    }

    /**
     * 构造函数
     * @param WorkflowID int
     * @param PrimaryKey String
     * @throws Exception
     */
    public PageMaker(int WorkflowID, String PrimaryKey) throws Exception {
        bizdata = BizData.buildObject(WorkflowID);
        bizdata.initBizField();
        listField = bizdata.getBizField();
        strPrimaryKey = PrimaryKey;
    }

    /**
     * 初始化表结构信息
     * @throws Exception
     */
    public void initField() throws Exception {
        //根据工作流编号获得业务数据配置
        bizdata = BizData.buildObject(intWorkflowID);
        //初始化字段信息
        bizdata.initBizField();
        //从XML中获得字段显示信息
        bizdata.initFieldTitle();
        listField = bizdata.getBizField();
    }
    /**
     * 程序：李扬
     * 时间：2004-11-16
     * 说明：生成主业务页面
     * @param out JspWriter
     * @return int
     * @throws Exception
     */
    public int makeMainPage(JspWriter out) throws Exception {
        //保存主键字段的信息
        BizField bfPK = new BizField();
        //循环获得主键字段
        for (int i = 0; listField != null && i < listField.size(); i++) {
            BizField bf = (BizField)listField.get(i);
            if (bf.getName().equalsIgnoreCase(strPrimaryKey)) {
                bfPK = bf;
                break;
            }
        }
        //生成页面
        out.println("<%@ page contentType=\"text/html; charset=utf-8\" %>");
        out.println("<%");
        out.println("/**");
        out.println(" * 标题：主页面");
        out.println(" * 描述：工作流主页面");
        out.println(" * 时间：" + Tools.getTime());
        out.println(" * 版权：");
        out.println(" */");
        out.println("%>");
        out.println("<%@ page import=\"com.youngbook.common.wf.clientapp.*\" %>");
        out.println("<%@ page import=\"com.youngbook.common.wf.engines.*\" %>");
        out.println("<%@ page import=\"com.youngbook.common.wf.common.*\" %>");
        out.println("<%@ page import=\""+strPackage+"."+strClassName+"\" %>");
        out.println("<%@ page import=\"com.youngbook.common.wf.processdefine.*\" %>");
        out.println("<%@ page import=\"java.util.*;\" %>");
        out.println("<%");
        out.println("int intWorkflowID = "+intWorkflowID+";  //获得工作流编号");
        out.println("int intCurrNodeID = Integer.parseInt(request.getParameter(\"CurrNodeID\"));  //获得当前节点编号");
        out.println("int intRouteListID = Integer.parseInt(request.getParameter(\"RouteListID\"));  //获得RouteList编号");
        out.println("String strYWID = request.getParameter(\"YWID\");  //获得业务编号");
        out.println(""+strClassName+" "+strClassInstance+" = new "+strClassName+"();");
        //根据用户定义不同的主键，生成设置业务编号的方法
        out.println("if (strYWID != null && !strYWID.equals(\"\")) {");
        if (bfPK.getCodeType().equalsIgnoreCase("int")) {
            //主键是int型
            out.println("  " + strClassInstance + ".set" + bfPK.getName() +
                    "(Integer.parseInt(strYWID));");
        }
        else if (bfPK.getCodeType().equalsIgnoreCase("Double")) {
            //主键是double型
            out.println("  " + strClassInstance + ".set" + bfPK.getName() +
                    "(Double.parseDouble(strYWID));");
        }
        else if (bfPK.getCodeType().equalsIgnoreCase("String")) {
            //主键是String型
            out.println("  " + strClassInstance + ".set" + bfPK.getName() + "(strYWID);");
        }
        out.println("  List list"+strClassName+" = "+strClassInstance+".queryExact();");
        out.println("  "+strClassInstance+" = ("+strClassName+")list"+strClassName+".get(0);");
        out.println("}");
        out.println("%>");
        out.println();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>工作流主页面</title>");
        out.println("</head>");
        out.println("<script language=\"javascript\">");
        out.println("function submitForm(form, target) {");
        out.println("  if (target == \"SaveOnly\") {");
        out.println("    form.TargetURL.value = \"/"+strDir+"/Done.jsp\";");
        out.println("    form.action = \"/workflow/WorkflowService\";");
        out.println("    form.target = \"_self\";");
        out.println("    form.ServiceType.value = \"SaveOnly\";");
        out.println("    form.submit();");
        out.println("  }");
        out.println("  else if (target == \"SaveForward\") {");
        out.println("    form.TargetURL.value = \"/"+strDir+"/Done.jsp\";");
        out.println("    form.action = \"/workflow/WorkflowService\";");
        out.println("    form.target = \"_self\";");
        out.println("    form.ServiceType.value = \"SaveForward\";");
        out.println("    form.submit();");
        out.println("  }");
        out.println("  else if (target == \"Cancel\") {");
        out.println("    form.TargetURL.value = \"/"+strDir+"/Done.jsp\";");
        out.println("    form.action = \"/workflow/WorkflowService\";");
        out.println("    form.target = \"_self\";");
        out.println("    form.ServiceType.value = \"Cancel\";");
        out.println("    form.submit();");
        out.println("  }");
        out.println("  else if (target==\"Reject\"){");
        out.println("	form.TargetURL.value=\"/"+strDir+"/Done.jsp\";");
        out.println("    form.action = \"/workflow/WorkflowService\";");
        out.println("	form.target=\"_self\";");
        out.println("	form.ServiceType.value=\"Reject\";");
        out.println("	form.submit();");
        out.println("  }");
        out.println("    else if (target==\"AutoForward\"){");
        out.println("	form.TargetURL.value=\"/"+strDir+"/Done.jsp\";");
        out.println("    form.action = \"/workflow/WorkflowService\";");
        out.println("	form.target=\"_self\";");
        out.println("	form.ServiceType.value=\"AutoForward\";");
        out.println("	form.submit();");
        out.println("  }");
        out.println("  else if (target == \"Over\") {");
        out.println("    form.TargetURL.value = \"/"+strDir+"/Done.jsp\";");
        out.println("    form.action = \"/workflow/WorkflowService\";");
        out.println("    form.target = \"_self\";");
        out.println("    form.ServiceType.value = \"Over\";");
        out.println("    form.submit();");
        out.println("  }");
        out.println("  else if (target == \"ActionReport\") {");
        out.println("    form.target = \"_blank\";");
        out.println("    form.YWID.value = form."+strPrimaryKey+".value;");
        out.println("    form.action = \"ActionReport.jsp\";");
        out.println("    form.submit();");
        out.println("  }");
        out.println("}");
        out.println("</script>");

        out.println("");
        out.println("<body>");
        out.println("<form action=\"/workflow/WorkflowService\" method=\"post\" name=\"form\" id=\"form\">");
        out.println("<table width=\"100%\"  border=\"1\">");
        for (int i = 0; listField != null && i < listField.size(); i++) {
            BizField bf = (BizField)listField.get(i);
            out.println("  <tr>");
            out.print("    <td align='right'>");
            out.print("<%= ClientApplications.getFieldTitle("+intWorkflowID+",\""+bf.getName()+"\") %>"+"：");
            out.println("    </td>");
            out.print("    <td>");

            if (bf.getCodeType().equalsIgnoreCase("String")) {
                out.print("<input type = \"text\" name=\"" + bf.getName() + "\"");
                if (bf.getName().equals(strPrimaryKey)) {
                    //如果该字段是关键字，则输出readonly属性
                    out.print(
                            " <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, \"" +
                                    bf.getName() + "\")  == 0 ? \"\" : \"readonly\"%> ");
                }
                else {
                    //如果该字段是不关键字，则输出disabled属性
                    out.print(
                            " <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, \"" +
                                    bf.getName() + "\")  == 0 ? \"\" : \"disabled\"%> ");

                }
                out.print(" value=\"<%=" + strClassInstance + ".isEmpty"+bf.getName()+"()? \"\" : " + strClassInstance + ".get" + bf.getName() +
                        "()%>\" >");
            }
            else {
                out.print("<input type = \"text\" name=\"" + bf.getName() + "\"");
                if (bf.getName().equals(strPrimaryKey)) {
                    //如果该字段是关键字，则输出readonly属性
                    out.print(
                            " <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, \"" +
                                    bf.getName() + "\")  == 0 ? \"\" : \"readonly\"%> ");
                }
                else {
                    //如果该字段是不关键字，则输出disabled属性
                    out.print(
                            " <%=ClientApplications.getFieldStatu(intWorkflowID, intCurrNodeID, \"" +
                                    bf.getName() + "\")  == 0 ? \"\" : \"disabled\"%> ");

                }
                out.print(" value=\"<%=" + strClassInstance + ".isEmpty" + bf.getName() +
                        "()? \"\" : String.valueOf(" + strClassInstance + ".get" +
                        bf.getName() +
                        "())%>\" >");
            }
            out.println("    </td>");
            out.println("  </tr>");
        }

        out.println("<tr>");
        out.println("  <td align='right'>转发目的地：</td>");
        out.println("  <td><select name=\"NextNode\" size=\"6\" multiple id=\"NextNode\" style=\"width:115pt\">");
        out.println("    <%");
        out.println("    List listNode = ClientApplications.getNextNode(intWorkflowID, intCurrNodeID);");
        out.println("    Iterator itNode = listNode.iterator();");
        out.println("    while (itNode.hasNext()) {");
        out.println("      Node node = (Node)itNode.next();");
        out.println("      String strNodeName = node.getName();");
        out.println("      int intNextNodeID = node.getID();");
        out.println("      %>");
        out.println(
                "      <option value=\"<%=intNextNodeID%>\"><%=strNodeName %></option>");
        out.println("      <%");
        out.println("      }");
        out.println("    %>");
        out.println("  </select></td>");
        out.println("</tr>");
        out.println("");

        out.println("</table>");
        out.println("<div align=\"center\">");
        out.println("    <input name=\"TargetURL\" type=\"hidden\" id=\"TargetURL\">");
        out.println(
                "    <input name=\"ServiceType\" type=\"hidden\" id=\"ServiceType\">");
        out.println("    <input name=\"BizDaoName\" type=\"hidden\" id=\"BizDaoName\" value=\""+strPackage+"."+strClassName+"\">");
        out.println("    <input name=\"CurrentNode\" type=\"hidden\" id=\"CurrentNode\" value=\"<%=intCurrNodeID%>\">");
        out.println("    <input name=\"WorkflowID\" type=\"hidden\" id=\"WorkflowID\" value=\"<%=intWorkflowID%>\">");
        out.println("    <input name=\"YWID\" type=\"hidden\" value=\"\"/>");
        out.println("    <input name=\"RouteListID\" type=\"hidden\" id=\"RouteListID\" value=\"<%=intRouteListID%>\">");
        out.println("    <input name=\"Participant\" type=\"hidden\" value=<%=Tools.toUTF8(request.getParameter(\"UserID\"))%> id=\"Participant\">");
        out.println("<br>");
        out.println("    <input type=\"button\" name=\"Button\" value=\"保存\" onClick=\"submitForm(this.form, 'SaveOnly')\">");
        out.println("    <input type=\"button\" name=\"Button\" value=\"转发\" onClick=\"submitForm(this.form, 'SaveForward')\">");
        out.println("    <input type=\"button\" name=\"Button\" value=\"退回\" onClick=\"submitForm(this.form, 'Reject')\"/>");
        out.println("    <input type=\"button\" name=\"Button\" value=\"自动转发\" onClick=\"submitForm(this.form, 'AutoForward')\"/>");
        out.println("    <input type=\"button\" name=\"Button\" value=\"查看流转记录\" onClick=\"submitForm(this.form, 'ActionReport')\">");
        out.println("    <input type=\"button\" name=\"Button\" value=\"中止工作流\" onClick=\"submitForm(this.form, 'Cancel')\">");
        out.println("	<%");
        out.println("	Node node=Node.searchNodeObject(intWorkflowID,intCurrNodeID);");
        out.println("	if (node.getType()==2){%>");
        out.println("      <input type=\"button\" name=\"Button\" value=\"完成工作流\" onclick=\"submitForm(this.form, 'Over')\">");
        out.println("	<%");
        out.println("	}");
        out.println("	%>");
        out.println("    <input type=\"reset\" name=\"Reset\" value=\"重写\">");
        out.println("  </div>");
        out.println("");

        out.println("</form>");
        out.println("</body>");
        out.println("</html>");

        return 1;
    }

    /**
     * 程序：李扬
     * 时间：2004-11-16
     * 说明：生成用户登陆页面
     * @param out JspWriter
     * @return 1：成功
     * @throws Exception
     */
    public int makeLoginPage(JspWriter out) throws Exception {
        out.println("<%@ page contentType=\"text/html; charset=utf-8\" %>");
        out.println("<%@ page import=\""+strPackage+"."+strClassName+"\" %>");
        out.println("<%");
        out.println("/**");
        out.println(" * 标题：登陆页面");
        out.println(" * 描述：用于用户登陆");
        out.println(" * 时间：" + Tools.getTime());
        out.println(" * 版权：");
        out.println(" */");
        out.println("%>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title></title>");
        out.println("</head>");
        out.println();
        out.println("<body>");
        out.println("<form action=\"List.jsp\" method=\"post\" name=\"form\" id=\"form\">");
        out.println("  <input name=\"WorkflowID\" type=\"hidden\" value=\""+intWorkflowID+"\">");
        out.println("  <input name=\"UserID\" type=\"text\" id=\"UserID\">");
        out.println("  <input type=\"submit\" name=\"Submit\" value=\"登陆\">");
        out.println("  <input type=\"reset\" name=\"Reset\" value=\"重写\">");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
        return 1;
    }

    /**
     * 程序：李扬
     * 时间：2004-11-22
     * 说明：生成业务列表页面
     * @param out JspWriter
     * @return int
     * @throws Exception
     */
    public int makeListPage(JspWriter out) throws Exception {
        out.println("<%@ page contentType=\"text/html; charset=utf-8\" %>");
        out.println("<%");
        out.println("/**");
        out.println(" * 标题：工作流业务列表");
        out.println(" * 描述：根据用户名，列出该用户所能处理的业务");
        out.println(" * 时间：" + Tools.getTime());
        out.println(" * 版权：");
        out.println(" */");
        out.println("%>");
        out.println("<%@ page import=\"com.youngbook.common.wf.admin.*\" %>");
        out.println("<%@ page import=\"com.youngbook.common.wf.clientapp.*\" %>");
        out.println("<%@ page import=\"com.youngbook.common.wf.common.*\" %>");
        out.println("<%@ page import=\"com.youngbook.common.wf.engines.*\" %>");
        out.println("<%@ page import=\""+strPackage+"."+strClassName+"\" %>");
        out.println("<%@ page import=\"com.youngbook.common.wf.processdefine.*\" %>");
        out.println("<%@ page import=\"java.sql.*\" %>");
        out.println("<%@ page import=\"java.util.*;\" %>");

        out.println("<%");
        out.println(
                "int intWorkflowID = Integer.parseInt(request.getParameter(\"WorkflowID\"));");
        out.println("String strUserID = Tools.toUTF8(request.getParameter(\"UserID\"));");
        out.println("int intNodeID = Integer.MAX_VALUE;");
        out.println("List listNode = ClientApplications.getOperableNodebyUserID(intWorkflowID, strUserID, intNodeID);");
        out.println("%>");
        out.println("<html>");
        out.println("<head>");
        out.println("<script language=\"javascript\" type=\"text/javascript\">");
        out.println("function submitForm(form, target) {");
        out.println("  if (form.YWID.value != \"\") {");
        out.println("    if (target == \"location\") {");
        out.println("      form.action = \"Location.jsp\";");
        out.println("      form.submit();");
        out.println("    }");
        out.println("    else if (target == \"AP\") {");
        out.println("      form.action = \"ActionReport.jsp\";");
        out.println("      form.actions.value = \"AP\";");
        out.println("      form.submit();");
        out.println("    }");
        out.println("  }");
        out.println("  else {");
        out.println("    window.alert(\"请输入索要查询的业务编号！\");");
        out.println("  }");
        out.println("}");
        out.println("</script>");
        out.println(
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
        out.println("<title>业务列表</title>");
        out.println("</head>");
        out.println("<body>");

        out.println("<a href=Main.jsp?UserID='<%=strUserID%>'&RouteListID=0&CurrNodeID=1>申请</a>");
        out.println("<form action=\"\" method=\"post\" name=\"form\" target=\"_blank\">");
        out.println("  <p class=\"TextStyle\">根据业务编号，查找该业务所处位置");
        out.println("    <input name=\"YWID\" type=\"text\" class=\"TextFieldStyle\" id=\"YWID\" size=\"4\">");
        out.println("    <input name=\"WorkflowID\" type=\"hidden\" value=\"<%=intWorkflowID%>\"/>");
        out.println("    <input name=\"UserID\" type=\"hidden\" value=\"<%=strUserID%>\"/>");
        out.println("    <input type=\"button\" name=\"Button\" value=\"查询\" onClick=\"submitForm(this.form, 'location')\">");
        out.println("    <input type=\"button\" name=\"Button\" value=\"查看流转记录\" onClick=\"submitForm(this.form, 'AP')\">");
        out.println("    <input type=\"reset\" name=\"Reset\" value=\"重写\">");
        out.println("    <input name=\"actions\" type=\"hidden\" id=\"actions\">");
        out.println("<br>");
        out.println("</p>");
        out.println("</form>");
        out.println("<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
        out.println("  <tr>");
        out.println("    <td><table width=\"100%\"  border=\"1\">");
        out.println("      <tr>");
        out.println("        <td width=\"13%\"><div align=\"center\">节点名称</div></td>");
        out.println("        <td width=\"6%\"><div align=\"center\">状态</div></td>");
        out.println("        <td width=\"81%\"><div align=\"center\">业务号</div></td>");
        out.println("      </tr>");
        out.println("      <%");
        out.println("      for (int i = 0; listNode != null && i < listNode.size(); i++) {");
        out.println("        Node node = (Node)listNode.get(i);");
        out.println("        RouteList rlActive = new RouteList();");
        out.println("        RouteList rlFinish = new RouteList();");
        out.println("        rlActive.setWorkflowID(intWorkflowID);");
        out.println("        rlActive.setCurrentNode(node.getID());");
        out.println("        rlActive.setStatu(1);");
        out.println("        rlFinish.setCurrentNode(node.getID());");
        out.println("        rlFinish.setWorkflowID(intWorkflowID);");
        out.println("        rlFinish.setStatu(4);");
        out.println("        List listRLActive = rlActive.queryExact();");
        out.println("        List listRLFinish = rlFinish.queryExact();");
        out.println("        %>");
        out.println("        <tr>");
        out.println("          <td rowspan=\"2\"><div align=\"center\"><%=node.getName()%></div></td>");
        out.println("          <td><div align=\"center\">等待</div></td>");
        out.println("          <td>");
        out.println("          <%");
        out.println("          for (int m = 0; listRLActive != null && m < listRLActive.size(); m++) {");
        out.println("            rlActive = (RouteList)listRLActive.get(m);");
        out.println("            out.print(\"<a href='Main.jsp?UserID=\"+strUserID+\"&CurrNodeID=\"+node.getID()+");
        out.println("                  \"&RouteListID=\"+rlActive.getID()+\"&YWID=\"+rlActive.getYWID()+\"'>\"+");
        out.println("                  rlActive.getYWID()+\"</a> \");");
        out.println("          }");
        out.println("          %>");
        out.println("          &nbsp;");
        out.println("          </td>");
        out.println("        </tr>");
        out.println("        <tr>");
        out.println("          <td><div align=\"center\">完成</div></td>");
        out.println("          <td>");
        out.println("          <%");
        out.println("          for (int n = 0; listRLFinish != null && n < listRLFinish.size(); n++) {");
        out.println("            rlFinish = (RouteList)listRLFinish.get(n);");
//    out.println("            out.print(\"<a href='main.jsp?UserID=\"+strUserID+\"&CurrNodeID=\"+node.getID()+");
//    out.println("                  \"&RouteListID=\"+rlFinish.getID()+\"&YWID=\"+rlFinish.getYWID()+\"'>\"+");
//    out.println("                  rlFinish.getYWID()+\"</a> \");");

        out.println("            out.print(rlFinish.getYWID()+\" \");");

        out.println("          }");
        out.println("          %>");
        out.println("          &nbsp;");
        out.println("          </td>");
        out.println("        </tr>");
        out.println("        <%");
        out.println("        }");
        out.println("        %>");
        out.println("    </table></td>");
        out.println("  </tr>");
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
        return 1;
    }

    /**
     * 程序：李扬
     * 时间：2004-11-22
     * 说明：生成流转信息查询页面
     * @param out JspWriter
     * @return int
     * @throws Exception
     */
    public int makeActionReport(JspWriter out) throws Exception {
        out.println("<%@ page contentType=\"text/html; charset=utf-8\" %>");
        out.println("<%");
        out.println("/**");
        out.println(" * 标题：工作流流转信息查看页面");
        out.println(" * 时间：" + Tools.getTime());
        out.println(" * 描述：根据工作流编号(WorkflowID)，业务编号(YWID)查出该业务的流转信息");
        out.println(" * 版权：");
        out.println(" */");
        out.println("%>");
        out.println("<%@ page import=\"com.youngbook.common.wf.admin.*\" %>");
        out.println("<%@ page import=\""+strPackage+"."+strClassName+"\" %>");
        out.println("<%@ page import=\"com.youngbook.common.wf.processdefine.*\" %>");
        out.println("<%@ page import=\"com.youngbook.common.wf.clientapp.*\" %>");
        out.println("<%@ page import = \"java.util.*\" %>");
        out.println("<%");
        out.println("List listAP = new ArrayList();  //记录流转信息");
        out.println("int intWorkflowID = Integer.parseInt(request.getParameter(\"WorkflowID\"));  //获得工作流编号");
        out.println("String strYWID = request.getParameter(\"YWID\");  //获得业务编号");
        out.println(
                "listAP = ClientApplications.getActionReport(intWorkflowID, strYWID);  //查找出流转信息");
        out.println("%>");
        out.println("<html>");
        out.println("<head>");
        out.println(
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
        out.println("<title>流转记录</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<table width=\"100%\"  border=\"1\" cellpadding=\"0\">");
        out.println("  <tr>");
        out.println("    <td align='center'>序号</td>");
        out.println("    <td align='center'>时间</td>");
        out.println("    <td align='center'>操作人</td>");
        out.println("    <td align='center'>当前节点</td>");
        out.println("    <td align='center'>成功转发节点</td>");
        out.println("    <td align='center'>未成功转发节点</td>");
        out.println("    <td align='center'>操作</td>");
        out.println("    <td align='center'>查看历史数据</td>");
        out.println("  </tr>");
        out.println("  <%");
        out.println("  int intCount = 0;  //计数器，记录序号");
        out.println("  Iterator itAP = listAP.iterator();");
        out.println("  while (itAP.hasNext()) {");
        out.println("    //循环获得流转信息");
        out.println("    intCount++;");
        out.println("    String strPreNodeName = new String();  //前一节点名称");
        out.println("    String strCurrentNodeName = new String();  //当前节点名称");

        out.println("    String[] strForward = null;  //记录成功转发节点编号");
        out.println("    String [] strForwardName = null;  //记录成功转发节点名称（数组形式）");
        out.println("    StringBuffer sbForwardName = new StringBuffer();  //记录成功转发节点名称（XXX,XX 形式）");

        out.println("    String[] strNext = null;  //记录转发不成功节点编号");
        out.println("    String[] strNextName = null; //记录转发不成功节点名称（数组形式）");
        out.println("    StringBuffer sbNextName = new StringBuffer(); //记录转发不成功节点名称（XXX,XX 形式）");

        out.println("    ActionReport ap = (ActionReport)itAP.next();");
        out.println("    //获得前一节点名称");
        out.println(
                "    strPreNodeName = Node.getNodeName(ap.getWorkflowID(), ap.getPreNode());");
        out.println("    //获得当前节点名称");
        out.println("    strCurrentNodeName = Node.getNodeName(ap.getWorkflowID(), ap.getCurrentNode());");
        out.println("    //获得转发成功节点编号（数组形式）");
        out.println("    strForward = ap.getForwarded().split(\"\\\\|\");");
        out.println("    if (strForward != null && strForward.length > 0) {");
        out.println("      //将获得的转发节点成功编号转换为节点名称（XXX,XX,XX 形式）");
        out.println("      strForwardName = new String[strForward.length - 1];");
        out.println("      for (int i = 0; strForwardName != null && i < strForwardName.length; i++) {");
        out.println("        strForwardName[i] = Node.getNodeName(ap.getWorkflowID(), Integer.parseInt(strForward[i+1]));");
        out.println("        sbForwardName.append(strForwardName[i]);");
        out.println("        sbForwardName.append(\"，\");");
        out.println("      }");
        out.println("    }");
        out.println("    //去除掉最后一个\"，\"");
        out.println("    if (sbForwardName.length() > 0) {");
        out.println("      sbForwardName.delete(sbForwardName.length() - 1, sbForwardName.length());");
        out.println("    }");

        out.println("    //获得转发不成功节点编号（数组形式）");
        out.println("    strNext = ap.getNextNode().split(\"\\\\|\");");
        out.println("    if (strNext != null && strNext.length > 0) {");
        out.println("      //将转发不成功节点编号转换成节点名称（XX,XXX形式）");
        out.println("      strNextName = new String[strNext.length - 1];");
        out.println("      for (int i = 0; strNextName != null && i < strNextName.length; i++) {");
        out.println("        strNextName[i] = Node.getNodeName(ap.getWorkflowID(), Integer.parseInt(strNext[i+1]));");
        out.println("        sbNextName.append(strNextName[i]);");
        out.println("        sbNextName.append(\",\");");
        out.println("      }");
        out.println("    }");
        out.println("    //去掉最后一个\",\"");
        out.println("    if (sbNextName.length() > 0) {");
        out.println("      sbNextName.delete(sbNextName.length() - 1, sbNextName.length());");
        out.println("    }");
        out.println("  %>");
        out.println("  <tr>");
        out.println("    <td align='center'><%=intCount%></td>");
        out.println("    <td align='center'><%=ap.getActionTime()%></td>");
        out.println("    <td align='center'><%=ap.getParticipant()%></td>");
        out.println("    <td align='center'><%=strCurrentNodeName%></td>");
        out.println("    <td align='center'><%=sbForwardName.toString() + \"&nbsp\"%></td>");
        out.println("    <td align='center'><%=sbNextName.toString() + \"&nbsp\"%></td>");
        out.println("    <%");
        out.println("    String strActionType = new String();");
        out.println("    switch(ap.getActionType()) {");
        out.println("      case 1:");
        out.println("        strActionType = \"只保存\";");
        out.println("        break;");
        out.println("      case 2:");
        out.println("        strActionType = \"转发\";");
        out.println("        break;");
        out.println("      case 3:");
        out.println("        strActionType = \"自动转发\";");
        out.println("        break;");
        out.println("      case 4:");
        out.println("        strActionType = \"中止业务\";");
        out.println("        break;");
        out.println("      case 5:");
        out.println("        strActionType = \"完成业务\";");
        out.println("        break;");
        out.println("      case 6:");
        out.println("        strActionType = \"退回业务\";");
        out.println("        break;");
        out.println("      default : break;");
        out.println("    }");
        out.println("    %>");
        out.println("    <td align='center'><%=strActionType%> &nbsp</td>");
        out.println("    <td align='center'><a href=\"HistoryData.jsp?WorkflowID=<%=ap.getWorkflowID()%>&YWID=<%=ap.getYWID()%>&ActionID=<%=ap.getID()%>\" target=\"_blank\">查看数据变化</a></td>");
        out.println("  <%");
        out.println("  }");
        out.println("  %>");
        out.println("  </tr>");
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");

        return 1;
    }

    /**
     * 程序：李扬
     * 时间：2004-11-22
     * 说明：生成业务位置查询页面
     * @param out JspWriter
     * @return int
     * @throws Exception
     */
    public int makeLocation(JspWriter out) throws Exception {
        out.println("<%@ page contentType=\"text/html; charset=utf-8\" %>");
        out.println("<%");
        out.println("/**");
        out.println(" * 标题：业务位置查询页面");
        out.println(" * 时间：" + Tools.getTime());
        out.println(" * 描述：查询业务所处位置");
        out.println(" * 版权：");
        out.println(" */");
        out.println("%>");
        out.println("<%@ page import=\"com.youngbook.common.wf.admin.*\" %>");
        out.println("<%@ page import=\"com.youngbook.common.wf.clientapp.*\" %>");
        out.println("<%@ page import=\"com.youngbook.common.wf.common.*\" %>");
        out.println("<%@ page import=\""+strPackage+"."+strClassName+"\" %>");
        out.println("<%@ page import=\"com.youngbook.common.wf.engines.*\" %>");
        out.println("<%@ page import=\"com.youngbook.common.wf.processdefine.*\" %>");
        out.println("<%@ page import=\"java.sql.*\" %>");
        out.println("<%@ page import=\"java.util.*;\" %>");
        out.println("<%");
        out.println("int intWorkflowID = Integer.parseInt(request.getParameter(\"WorkflowID\"));");
        out.println("int intStatu = 1;");
        out.println("String strYWID = request.getParameter(\"YWID\");");
        out.println(
                "List listRL = ClientApplications.getActiveYW(strYWID, intWorkflowID);");
        out.println("%>");
        out.println("<html>");
        out.println("<head>");
        out.println(
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
        out.println("<title>业务位置查询页面</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<table width=\"100%\"  border=\"1\">");
        out.println("  <tr>");
        out.println("    <td align='center'>业务号</td>");
        out.println("    <td align='center'>当前节点</td>");
        out.println("    <td align='center'>编号</td>");
        out.println("  </tr>");
        out.println("  <%");
        out.println("  for (int i = 0; listRL != null && i < listRL.size(); i++) {");
        out.println("    RouteList rl = (RouteList)listRL.get(i);");
        out.println(
                "    Node node = Node.searchNodeObject(intWorkflowID, rl.getCurrentNode());");
        out.println("  %>");
        out.println("  <tr>");
        out.println("    <td align='center'><%=strYWID%></td>");
        out.println(
                "    <td align='center'><%=rl.getCurrentNode()+\"(\"+node.getName()+\")\"%></td>");
        out.println("    <td align='center'><%=rl.getID()%></td>");
        out.println("  </tr>");
        out.println("  <%");
        out.println("  }");
        out.println("  %>");
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");

        return 1;
    }

    /**
     * 程序：李扬
     * 时间：2004-11-22
     * 说明：生成历史数据查询页面
     * @param out JspWriter
     * @return int
     * @throws Exception
     */
    public int makeHistoryDataReport(JspWriter out) throws Exception {
        out.println("<%@ page contentType=\"text/html; charset=utf-8\" %>");
        out.println("<%");
        out.println("/**");
        out.println(" * 标题：历史数据查询页面");
        out.println(" * 时间：" + Tools.getTime());
        out.println(" * 描述：根据工作流编号、业务编号和行为编号，查找出历史数据的变化");
        out.println(" * 版权：");
        out.println(" */");
        out.println("%>");
        out.println("<%@ page import=\"com.youngbook.common.wf.admin.*\" %>");
        out.println("<%@ page import=\"com.youngbook.common.wf.processdefine.*\" %>");
        out.println("<%@ page import=\"com.youngbook.common.wf.clientapp.*\" %>");
        out.println("<%@ page import=\""+strPackage+"."+strClassName+"\" %>");
        out.println("<%@ page import = \"java.util.*\" %>");
        out.println("<%");
        out.println("String strWorkflowID = request.getParameter(\"WorkflowID\");");
        out.println("String strYWID = request.getParameter(\"YWID\");");
        out.println("String strActionID = request.getParameter(\"ActionID\");");
        out.println(
                "if (strWorkflowID  == null || strYWID == null || strActionID == null) {");
        out.println("  throw new Exception(\"无法获得所要查询的工作流号、业务号或操作号！\");");
        out.println("}");
        out.println("int intWorkflowID = Integer.parseInt(strWorkflowID);");
        out.println("int intActionID = Integer.parseInt(strActionID);");
        out.println("List listHD = new ArrayList();  //历史数据记录");
        out.println("listHD = ClientApplications.getHistoryData(intWorkflowID, strYWID, intActionID);");
        out.println("Iterator itHD = listHD.iterator();");
        out.println("%>");
        out.println("<html>");
        out.println("<head>");
        out.println(
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
        out.println("<title>查看数据</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<table width=\"400\" align='center'  border=\"1\">");
        out.println("  <tr>");
        out.println("    <td align='center'>字段名</td>");
        out.println("    <td align='center'>原值</td>");
        out.println("    <td align='center'>新值</td>");
        out.println("  </tr>");
        out.println("  <%");
        out.println("  while (itHD.hasNext()) {");
        out.println("    String strFieldTitle = new String();");
        out.println("    HistoryData hdTemp = (HistoryData)itHD.next();");
        out.println("    strFieldTitle = BizData.getFieldTitle(Integer.parseInt(strWorkflowID),hdTemp.getName());");
        out.println("  %>");
        out.println("  <tr>");
        out.println("    <td align='center'><%=hdTemp.getName()+\"(\"+hdTemp.getTitle()+\")\"%></td>");
        out.println("    <td align='center'><%=hdTemp.getOldVal()%> &nbsp;</td>");
        out.println("    <td align='center'><%=hdTemp.getNewVal()%></td>");
        out.println("  </tr>");
        out.println("  <%");
        out.println("  }");
        out.println("  %>");
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");

        return 1;
    }

    /**
     * 程序：李扬
     * 时间：2004-11-22
     * 说明：生成业务操作完成页面
     * @param out JspWriter
     * @return int
     * @throws Exception
     */
    public int makeDonePage(JspWriter out) throws Exception {
        out.println("<%@ page contentType=\"text/html; charset=utf-8\" %>");
        out.println("<%");
        out.println("/**");
        out.println(" * 标题：业务完成页面");
        out.println(" * 时间：" + Tools.getTime());
        out.println(" * 描述：显示业务完成信息");
        out.println(" * 版权：");
        out.println(" */");
        out.println("%>");
        out.println("<%@ page import=\"com.youngbook.common.wf.admin.RouteList\"%>");
        out.println("<%@ page import=\""+strPackage+"."+strClassName+"\" %>");
        out.println("<%@ page import=\"com.youngbook.common.wf.processdefine.*\"%>");
        out.println("<html>");
        out.println("<head>");
        out.println(
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
        out.println("<title></title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>");
        out.println("操作结果页");
        out.println("</h1>");
        out.println("<br>");
        out.println("操作结果如下：(request.getAttribute(\"Result\"))<br>");
        out.println("<%");
        out.println("String strResult=(String)request.getAttribute(\"Result\");");
        out.println("if (strResult.equals(\"1\")){");
        out.println("	out.print(\"成功<br>\");");
        out.println(
                "	out.print(\"操作信息如下：RouteList rl=(RouteList)request.getAttribute(\\\"RouteList \\\")<br>\");");
        out.println("	RouteList rl=(RouteList)request.getAttribute(\"RouteList\");");
        out.println("	out.print(\"操作结果：int intStatu=rl.getStatu();<br>\");");
        out.println("	int intStatu=rl.getStatu();");
        out.println("	if (intStatu==4){");
        out.println("		out.print(\"所有目的地都转发完成<br>\");");
        out.println("	}");
        out.println("	if (intStatu==2){");
        out.println("		out.print(\"只转发到了部份目的地<br>\");");
        out.println("	}");
        out.println("	String strForWarded=rl.getForwarded();");
        out.println("	String strNextNode=rl.getNextNode();");
        out.println("	String arrForWarded[]=strForWarded.split(\"\\\\|\");");
        out.println("	out.print(\"完成了以下节点的转发：\");");
        out.println("	for (int i=0;arrForWarded!=null&&i<arrForWarded.length;i++){");
        out.println("		if (arrForWarded[i]!=null&&!arrForWarded[i].equals(\"\")){");
        out.println("			Node node=Node.searchNodeObject(rl.getWorkflowID(),Integer.parseInt(arrForWarded[i]));");
        out.println("  			out.print(node.getName() + \" \");");
        out.println("		}");
        out.println("	}");
        out.println("	String arrNextNode[]=strNextNode.split(\"\\\\|\");");
        out.println("	out.print(\"<br>未完成以下节点的转发：\");");
        out.println("	for (int i=0;arrNextNode!=null&&i<arrNextNode.length;i++){");
        out.println("		if (arrNextNode[i]!=null&&!arrNextNode[i].equals(\"\")){");
        out.println("			Node node=Node.searchNodeObject(rl.getWorkflowID(),Integer.parseInt(arrNextNode[i]));");
        out.println("  			out.print(node.getName() + \" \");");
        out.println("		}");
        out.println("	}");
        out.println("}");
        out.println("else");
        out.println("{");
        out.println("	out.print(\"失败<br>\");");
        out.println("	out.print(\"原因如下：(Exception)request.getAttribute(\\\"Exception \\\")<br>\");");
        out.println("	Exception e=(Exception)request.getAttribute(\"Exception\");");
        out.println("	out.print(e.getMessage());");
        out.println("e.printStackTrace();");
        out.println("}");
        out.println("%>");
        out.println("</body>");
        out.println("</html>");

        return 1;
    }
}
