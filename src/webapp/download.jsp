<%--
  Created by IntelliJ IDEA.
  User: zq
  Date: 2015/12/30
  Time: 22:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=gbk"%>
<%@ page language="java" import="java.io.*,java.net.*" pageEncoding="gbk"%>
<html>
<head>
    <title>test</title>
</head>
<body>
<%
    response.setContentType("text/html");
    javax.servlet.ServletOutputStream ou = response.getOutputStream();
    String filepath=application.getRealPath("/")+"mobile/";
    String filename=new String("appVersion/hopewealth-last-version.apk".getBytes("ISO8859_1"),"GB2312").toString();
    System.out.println("DownloadFile filepath:" + filepath);
    System.out.println("DownloadFile filename:" + filename);
    java.io.File file = new java.io.File(filepath + filename);
    if (!file.exists()) {
        System.out.println(file.getAbsolutePath() + " �ļ�������!");
        return;
    }
    // ��ȡ�ļ���
    java.io.FileInputStream fileInputStream = new java.io.FileInputStream(file);
    // �����ļ�
    // ������Ӧͷ�����ر�����ļ���
    if (filename != null && filename.length() > 0) {
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-Disposition", "attachment; filename=" + new String(filename.getBytes("gb2312"),"iso8859-1") + "");
        if (fileInputStream != null) {
            int filelen = fileInputStream.available();
            //�ļ�̫��ʱ�ڴ治��һ�ζ���,Ҫѭ��
            byte a[] = new byte[filelen];
            fileInputStream.read(a);
            ou.write(a);
        }
        fileInputStream.close();
        ou.close();
    }
%>
</body>
</html>
