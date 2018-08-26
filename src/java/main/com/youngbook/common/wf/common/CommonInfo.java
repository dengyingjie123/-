package com.youngbook.common.wf.common;

import com.youngbook.common.wf.admin.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class CommonInfo {
    protected static CommonInfo ci; //单子
    protected static String strDBAddr; //数据库地址
    protected static String strDBUserName; //数据库用户名
    protected static String strDBPwd; //数据库密码
    protected static String strSid; //SID
    protected static String strXMLPath; //XML文件存放路径
    protected static String strUserTable; //用户表
    protected static String strUserName; //用户名
    protected static String strTrueName; //真实姓名
    private CommonInfo() {
    }

    /**
     * 程序：
     * 说明：得到XML文件存放路径
     *
     * @return
     */
    public static String getXMLPath() {
        if (ci == null) {
            ci = new CommonInfo();
        }
        if (ci.strXMLPath == null) {
            String strClassName = ci.getClass().getResource(ci.getClass().getName().
                    substring(ci.getClass().
                            getName().lastIndexOf(".") + 1) + ".class").toString();
            ci.strXMLPath = (strClassName.toString().substring(0,
                    strClassName.
                            lastIndexOf("classes/com/youngbook/common/wf/common/CommonInfo.class")) +
                    "config/wf/").replaceFirst("file:/", "");
        }
        return ci.strXMLPath;
    }

    /**
     * 程序：
     * 说明：得到数据库地址
     * @return
     */
    public static String getDBAddr() throws Exception {
        if (ci == null) {
            ci = new CommonInfo();
        }
        if (ci.strDBAddr == null) {
            Config conf = new Config();
            conf.Read();
            ci.strDBAddr = conf.getDBAddr();
        }
        return ci.strDBAddr;
    }

    /**
     * 程序：
     * 说明：得到数据库SID
     * @return
     */
    public static String getSid() throws Exception {
        if (ci == null) {
            ci = new CommonInfo();
        }
        if (ci.strSid == null) {
            Config conf = new Config();
            conf.Read();
            ci.strSid = conf.getSid();
        }
        return ci.strSid;
    }

    /**
     * 程序：
     * 说明：得到数据库用户名
     * @return
     */
    public static String getDBUserName() throws Exception {
        if (ci == null) {
            ci = new CommonInfo();
        }
        if (ci.strDBUserName == null) {
            Config conf = new Config();
            conf.Read();
            ci.strDBUserName = conf.getDBUserName();
        }
        return ci.strDBUserName;
    }

    /**
     * 程序：
     * 说明：得到数据库密码
     * @return
     */
    public static String getDBPwd() throws Exception {
        if (ci == null) {
            ci = new CommonInfo();
        }
        if (ci.strDBPwd == null) {
            Config conf = new Config();
            conf.Read();
            ci.strDBPwd = conf.getDBPwd();
        }
        return ci.strDBPwd;
    }

    /**
     * 程序：
     * 说明：得到数据库用户表
     * @return
     */
    public static String getUserTable() throws Exception {
        if (ci == null) {
            ci = new CommonInfo();
        }
        if (ci.strUserTable == null) {
            Config conf = new Config();
            conf.Read();
            ci.strUserTable = conf.getUserTable();
        }
        return ci.strUserTable;
    }

    /**
     * 程序：
     * 说明：得到数据库用户表用户名字段
     * @return
     */
    public static String getUserName() throws Exception {
        if (ci == null) {
            ci = new CommonInfo();
        }
        if (ci.strUserName == null) {
            Config conf = new Config();
            conf.Read();
            ci.strUserName = conf.getUserName();
        }
        return ci.strUserName;
    }

    /**
     * 程序：
     * 说明：得到数据库用户表真实姓名
     * @return
     */
    public static String getTrueName() throws Exception {
        if (ci == null) {
            ci = new CommonInfo();
        }
        if (ci.strTrueName == null) {
            Config conf = new Config();
            conf.Read();
            ci.strTrueName = conf.getTrueName();
        }
        return ci.strTrueName;
    }

}
