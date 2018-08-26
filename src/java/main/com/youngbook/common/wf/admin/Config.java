package com.youngbook.common.wf.admin;


import com.youngbook.common.wf.common.CommonInfo;
import com.youngbook.common.wf.common.Tools;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;


/**
 * <p>Title: 工作流运行环境配置</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class Config {

    private static final String xmlPath = CommonInfo.getXMLPath() + "general.xml";

    public Config() throws Exception {
    }

    protected String strDBAddr = new String(); //数据库地址
    protected String strDBUserName = new String(); //数据库用户名
    protected String strDBPwd = new String(); //数据库密码
    protected String strUserTable = new String(); //用户表
    protected String strUserName = new String(); //用户名字段
    protected String strTrueName = new String(); //真实姓名
    protected String strSid = new String(); //SID
    protected String strAdminPwd = new String();  //工作流管理员密码

    /**
     * 说明:工作流运行环境参数写入XML中
     * @throws Exception
     */
    public void Write() throws Exception {
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(xmlPath);
        Element root = doc.getRootElement();

        Element DBElem = root.getChild("DataBase");
        Element DBAddrElem = DBElem.getChild("Addr");
        DBAddrElem.setText(Tools.Encode(strDBAddr));
        Element DBUserNameElem = DBElem.getChild("User");
        DBUserNameElem.setText(Tools.Encode(strDBUserName));
        Element DBPwdElem = DBElem.getChild("Pwd");
        DBPwdElem.setText(Tools.Encode(strDBPwd));
        Element SidElem = DBElem.getChild("Sid");
        SidElem.setText(Tools.Encode(strSid));

        Element UserTableElement = root.getChild("UserTable");
        Element TableNameElem = UserTableElement.getChild("TableName");
        TableNameElem.setText(Tools.Encode(strUserTable));
        Element UserNameElem = UserTableElement.getChild("UserName");
        UserNameElem.setText(Tools.Encode(strUserName));
        Element TrueNameElem = UserTableElement.getChild("TrueName");
        TrueNameElem.setText(Tools.Encode(strTrueName));

        Element AdminPwdElem = root.getChild("Admin");
        AdminPwdElem.setText(Tools.Encode(strAdminPwd));
        Tools.WriteXML("general.xml", doc);
    }

    /**
     * 说明：从XML文件中读取工作流运行环境参数
     * @throws Exception
     */
    public void Read() throws Exception {
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(xmlPath);
        Element reElem = doc.getRootElement();
        setDBAddr(Tools.Decode(reElem.getChild("DataBase").getChild("Addr").getText()));
        setDBUserName(Tools.Decode(reElem.getChild("DataBase").getChild("User").
                getText()));
        setDBPwd(Tools.Decode(reElem.getChild("DataBase").getChild("Pwd").getText()));
        setSid(Tools.Decode(reElem.getChild("DataBase").getChild("Sid").getText()));
        setUserTable(Tools.Decode(reElem.getChild("UserTable").getChild("TableName").
                getText()));
        setUserName(Tools.Decode(reElem.getChild("UserTable").getChild("UserName").
                getText()));
        setTrueName(Tools.Decode(reElem.getChild("UserTable").getChild("TrueName").
                getText()));
        setAdminPwd(Tools.Decode(reElem.getChild("Admin").getText()));
    }

    /**
     * 获得数据库地址
     * @return String
     */
    public String getDBAddr() {
        return strDBAddr;
    }

    /**
     * 获得数据库用户名
     * @return String
     */
    public String getDBUserName() {
        return strDBUserName;
    }

    /**
     * 获得数据库密码
     * @return String
     */
    public String getDBPwd() {
        return strDBPwd;
    }

    /**
     * 获得SID
     * @return String
     */
    public String getSid() {
        return strSid;
    }

    /**
     * 获得用户表
     * @return String
     */
    public String getUserTable() {
        return strUserTable;
    }

    /**
     * 获得用户名字段
     * @return String
     */
    public String getUserName() {
        return strUserName;
    }

    /**
     * 获得用户真实姓名字段
     * @return String
     */
    public String getTrueName() {
        return strTrueName;
    }

    /**
     * 获得工作流管理员密码
     * @return String
     */
    public String getAdminPwd() {
        return strAdminPwd;
    }
    /**
     * 设置数据库地址
     * @param DBAddr String
     */
    public void setDBAddr(String DBAddr) {
        strDBAddr = DBAddr;
    }

    /**
     * 设置数据库用户名
     * @param DBUserName String
     */
    public void setDBUserName(String DBUserName) {
        strDBUserName = DBUserName;
    }

    /**
     * 设置用户密码
     * @param DBPwd String
     */
    public void setDBPwd(String DBPwd) {
        strDBPwd = DBPwd;
    }

    /**
     * 设置SID
     * @param Sid String
     */
    public void setSid(String Sid) {
        strSid = Sid;
    }

    /**
     * 设置用户表
     * @param UserTable String
     */
    public void setUserTable(String UserTable) {
        strUserTable = UserTable;
    }

    /**
     * 设置用户名字段
     * @param UserName String
     */
    public void setUserName(String UserName) {
        strUserName = UserName;
    }

    /**
     * 设置用户真实姓名字段
     * @param TrueName String
     */
    public void setTrueName(String TrueName) {
        strTrueName = TrueName;
    }

    /**
     * 设置工作流管理员密码
     * @param AdminPwd String
     */
    public void setAdminPwd(String AdminPwd) {
        strAdminPwd = AdminPwd;
    }
}
