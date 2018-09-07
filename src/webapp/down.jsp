<%@ page contentType="text/html;charset=gbk"%>
<%@ page language="java" import="java.io.*,java.net.*" pageEncoding="gbk"%>
<%@ page import="java.util.zip.ZipOutputStream" %>
<%@ page import="com.youngbook.common.utils.ZipUtils" %>
<%@ page import="java.util.zip.ZipEntry" %>
<%@ page import="com.youngbook.common.Database" %>
<%@ page import="com.youngbook.common.config.Config" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="com.youngbook.service.system.FilesService" %>
<%@ page import="com.youngbook.entity.po.system.FilesPO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.youngbook.common.utils.StringUtils" %>
<%@ page import="com.youngbook.common.utils.TimeUtils" %>
<%
    // module: 18833

    String bizId = request.getParameter("bizId");
    String moduleId = request.getParameter("moduleId");
    String zipName = request.getParameter("zipName");

    if (!Config.checkLoginedUniversal(request)) {
        out.println("请首先登录");
        return;
    }

    if (StringUtils.isEmptyAny(bizId, moduleId)) {
        out.println("所需参数不完整，请检查");
        return;
    }






    Connection conn = Config.getConnection();



    try {


        FilesService filesService = Config.getBeanByName("filesService", FilesService.class);
        List<FilesPO> list = filesService.getListFiles(bizId, moduleId, conn);

        if (list == null || list.size() == 0) {
            out.println("没有附件信息");
            return;
        }

        if (StringUtils.isEmpty(zipName)) {
            zipName = TimeUtils.getSimpleTimestamp() + ".zip";
        }

        ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());

        response.setHeader("Expires", "0");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + zipName + "\"");
        response.setHeader("Content-Type", "application/zip");

        for (int i = 0; list != null && i < list.size(); i++) {
            FilesPO filesPO = list.get(i);

            String path = filesService.getRootPathOfUpload() + "/" + filesPO.getPath();
            String fileName = filesPO.getFileName() + "-" + filesPO.getSid() + filesPO.getExtensionName();

            ZipUtils.writeToZipFile(path, fileName, zipOut);
        }

        zipOut.close();
    }
    catch (Exception e) {
        throw e;
    }
    finally {
        Database.close(conn);
    }


    response.getOutputStream().flush();
    response.flushBuffer();

%>