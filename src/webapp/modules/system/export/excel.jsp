<%@ page import="java.util.List" %>
<%@ page import="com.youngbook.common.ExcelExporter" %>
<%@ page contentType="application/vnd.ms-excel; charset=GBK" %>
<%@ page language="java" pageEncoding="GBK"%>
<%
    //  需要的传入参数
    String fileName = (String)request.getAttribute("fileName");
    if (fileName == null) {
        fileName = "Excel";
    }
    String title = (String)request.getAttribute("title");
    if (title == null) {
        title = "Excel";
    }
    List list = (List)request.getAttribute("list");
    Class clazz = (Class)request.getAttribute("clazz");


    String filename = new String((fileName).getBytes("GBK"),"ISO-8859-1");
    response.addHeader("Content-Disposition", "filename=" + filename + ".xls");

    response.getWriter().write("<html>");
    response.getWriter().write("    <head>");
    response.getWriter().write("        <meta name=\"Generator\" content=\"Microsoft Excel 11\"/>");
    response.getWriter().write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>");
    response.getWriter().write("    </head>");
    response.getWriter().write("    <body >");
    response.getWriter().write("        <center><b>"+title+"</b></center><br/>");
    String html = ExcelExporter.getHtml(list, clazz);
    //System.out.println(html);
    response.getWriter().write(html);
    response.getWriter().write("    </body>");
    response.getWriter().write("</html>");

%>