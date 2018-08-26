package com.youngbook.common.wf.processdefine;

import com.youngbook.common.wf.common.*;
import com.youngbook.common.wf.admin.*;
import java.util.*;
import java.io.*;
import java.sql.*;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;
import org.jdom.xpath.XPath;

/**
 * <p>Title: 工作流基本信息类</p>
 * <p>Description: 实现工作流编号、名称、说明、创建日期等信息的维护。
 *                 将从DmisWorkflow_Def.xml文件中对以上信息的插入、修改、新增等操作。
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ProcessInfo {
    public ProcessInfo() throws Exception {
        builder = new SAXBuilder();
        doc = builder.build(CommonInfo.getXMLPath() + "define.xml");
        root = doc.getRootElement();
    }

    Element root;
    protected SAXBuilder builder;
    protected Document doc;
    protected int intID = Integer.MAX_VALUE; //工作流编号
    protected String strName=new String(); //工作流名称
    protected String strInfo=new String(); //描述信息
    protected String strCreateDate=new String(); //创建日期
    protected boolean bolSaveAction = false; //是否保存动作
    protected boolean bolSaveHistory = false; //是否保存历史记录

    /**
     * 获得工作流编号
     * @return int
     */
    public int getID() {
        return intID;
    }

    /**
     * 获得工作流名称
     * @return String
     */
    public String getName() {
        return strName;
    }

    public String getInfo() {
        return strInfo;
    }

    public String getCreateDate() {
        return strCreateDate;
    }

    public boolean getSaveAction() {
        return bolSaveAction;
    }

    public boolean getSaveHistory() {
        return bolSaveHistory;
    }

    public void setID(int ID) {
        intID = ID;
    }

    public void setName(String Name) {
        strName = Name;
    }

    public void setInfo(String Info) {
        strInfo = Info;
    }

    public void setCreateDate(String CreateDate) {
        strCreateDate = CreateDate;
    }

    public void setSaveAction(boolean SaveAction) {
        bolSaveAction = SaveAction;
    }

    public void setSaveHistory(boolean SaveHistory) {
        bolSaveHistory = SaveHistory;
    }

    /**
     * 程序：
     * 说明：返回编号指定的工作流定义，如不指定编号，则返回所有
     * @return
     */
    public List search() throws Exception {
        List relist = new ArrayList();
        if (intID == Integer.MAX_VALUE) { //编号不指定，返回所有
            relist = XPath.selectNodes(root, "/DmisWorkflow_Def/Workflow");
        }
        else { //指定编号，返回指定
            relist = XPath.selectNodes(root,
                    "/DmisWorkflow_Def/Workflow[@ID='" +
                            String.valueOf(intID) + "']");
        }
        return relist;
    }

    /**
     * 程序：
     * 说明：返回查找后的ProcessInfo对象
     * @return
     * @throws java.lang.Exception
     */
    public List searchObject() throws Exception {
        List list = search();
        List relist = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ProcessInfo pi = buildobject( (Element) it.next());
            relist.add(pi);
        }
        return relist;
    }

    /**
     * 程序：
     * 描述：新增工作流定义
     * @return 0:失败 1:成功
     */
    public int insert() throws Exception {
        int intResult = 1;
        if (intID == Integer.MAX_VALUE) {
            throw new Exception("ProcessInfo|insert|NoPara|执行ProcessInfo.insert()方法时发生参数不足异常，无法获得所需的编号(ID)。");
        }
        List listsearch = search();
        if (listsearch != null && listsearch.size() > 0) {
            throw new Exception("ProcessInfo|insert|BizLogic|执行ProcessInfo.insert()方法时发生业务逻辑异常，指定的编号已存在。");
        }
        root.addContent(buildElem());
        Tools.WriteXML("define.xml", doc);
        return intResult;
    }

    /**
     * 程序：
     * 描述：修改工作流定义
     * @return 0:失败 1:成功
     */
    public int update() throws Exception {
        if (intID == Integer.MAX_VALUE) {
            throw new Exception("ProcessInfo|update|NoPara|执行ProcessInfo.update()方法时发生参数不足异常，无法获得所需的编号(ID)。");
        }
        int intResult = 0;
        List list = search();
        if (list != null && list.size() > 0) {
            Element chelem = (Element) list.get(0);
            chelem.getAttribute("ID").setValue(String.valueOf(intID));
            chelem.getChild("Name").setText(Tools.Encode(strName));
            chelem.getChild("Info").setText(Tools.Encode(strInfo));
            chelem.getChild("CreateDate").setText(Tools.Encode(strCreateDate));
            chelem.getChild("SaveAction").setText(String.valueOf(bolSaveAction));
            chelem.getChild("SaveHistory").setText(String.valueOf(bolSaveHistory));
            Tools.WriteXML("define.xml", doc);
            intResult = 1;
        }
        return intResult;
    }

    /**
     * 程序：
     * 说明：删除工作流定义
     * @return 0:失败 1:成功
     */
    public int Delete() throws Exception {
        Connection conn = Tools.getDBConn();
        Statement statement = null;
        ResultSet resultset = null;
        try {
            if (intID == Integer.MAX_VALUE) {
                throw new Exception("ProcessInfo|Delete|NoPara|执行ProcessInfo.Delete()方法时发生参数不足异常，无法获得所需的编号(ID)。");
            }
            int intResult = 0;
            //1、删除DmisWorkflow_Proce.xml文件中相关内容
            List list = XPath.selectNodes(root,
                    "/DmisWorkflow_Def/Workflow[@ID='" +
                            String.valueOf(intID) + "']");
            if (list != null && list.size() > 0) {
                Element ele = (Element) list.get(0);
                intResult = root.removeContent(ele) ? 1 : 0;
                Tools.WriteXML("define.xml", doc);
                System.out.println("1.删除DmisWorkflow_Def.xml中编号为： " + intID +
                        " 的工作流成功！");
                if (intResult == 0) {
                    throw new Exception("ProcessInfo|Delete|DbFail|执行ProcessInfo.Delete()方法时发生数据库操作异常，无法删除此工作流信息。");
                }
            }

            //2、删除DmisWorkflow_Proce.xml文件中相关内容
            SAXBuilder builderDel = new SAXBuilder();
            Document docDel = new Document();
            docDel = builderDel.build(CommonInfo.getXMLPath() +
                    "DmisWorkflow_Proce.xml");
            Element rootDel = docDel.getRootElement();
            List listDel = XPath.selectNodes(rootDel,
                    "/DmisWorkflow_Proce/WorkflowProcess[@WorkflowID='" +
                            String.valueOf(intID) + "']");
            try {
                if (listDel != null && listDel.size() > 0) {
                    Element elem = (Element) listDel.get(0);
                    if (elem != null) {
                        rootDel.removeContent(elem);
                        //写入XML文件中
                        Tools.WriteXML("DmisWorkflow_Proce.xml", docDel);
                        System.out.println("2.删除DmisWorkflow_Proce.xml中编号为： " + intID +
                                " 的工作流成功！");
                    }
                }
            }
            catch (Exception e) {
                throw e;
            }

            //3、删除DmisWorkflow_Data.xml文件中相关内容
            BizData bd = new BizData();
            bd.setWorkflowID(intID);
            bd.Delete();

            //4.删除workflow_Participant数据表中相关记录
            Participant pt = new Participant();
            pt.setWorkflowID(intID);
            pt.Delete(conn);

            //5、删除workflow_RouteList数据表中相关记录
            RouteList routelist = new RouteList();
            routelist.setWorkflowID(intID);
            intResult = routelist.delete(conn);
            if (! (intResult >= 0)) {
                throw new Exception("ProcessInfo|Delete|DbFail|执行ProcessInfo.Delete()方法时发生数据库操作异常，无法删除数据库表WORKFLOW_ROUTELIST中工作流信息。");
            }

            //6、删除workflow_Action数据表中相关记录
            Action action = new Action();
            action.setWorkflowID(intID);
            action.delete(conn);

            //7、删除workflow_History数据表中相关记录
            HistoryData hd = new HistoryData();
            hd.setWorkflowID(intID);
            hd.delete(conn);

            intResult = 1;
            return intResult;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (resultset != null) {
                resultset.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * 程序：
     * 说明：跟据XML Element来构造一个ProcessInfo
     * @return
     */
    public static ProcessInfo buildobject(Element elem) throws Exception {
        ProcessInfo pi = new ProcessInfo();
        pi.setID(Integer.parseInt(elem.getAttribute("ID").getValue()));
        pi.setName(Tools.Decode(elem.getChild("Name").getText()));
        pi.setInfo(Tools.Decode(elem.getChild("Info").getText()));
        pi.setCreateDate(Tools.Decode(elem.getChild("CreateDate").getText()));
        pi.setSaveAction(Boolean.valueOf(elem.getChild("SaveAction").getText()).
                booleanValue());
        pi.setSaveHistory(Boolean.valueOf(elem.getChild("SaveHistory").getText()).
                booleanValue());
        return pi;
    }

    /**
     * 程序：
     * 说明：根据对象生成一个XML的Element
     * @return
     */
    public Element buildElem() {
        Element newElem, NameElem, InfoElem, CreateDateElem, SaveActionElem,
                SaveHistoryElem;
        newElem = new Element("Workflow");
        newElem.setAttribute("ID", String.valueOf(intID));
        NameElem = new Element("Name").setText(Tools.Encode(strName));
        InfoElem = new Element("Info").setText(Tools.Encode(strInfo));
        CreateDateElem = new Element("CreateDate").setText(Tools.Encode(
                strCreateDate));
        SaveActionElem = new Element("SaveAction").setText(Tools.Encode(String.
                valueOf(bolSaveAction)));
        SaveHistoryElem = new Element("SaveHistory").setText(Tools.Encode(String.
                valueOf(bolSaveHistory)));
        newElem.addContent(NameElem);
        newElem.addContent(InfoElem);
        newElem.addContent(CreateDateElem);
        newElem.addContent(SaveActionElem);
        newElem.addContent(SaveHistoryElem);
        return newElem;
    }


    /**
     * 程序：李扬
     * 时间：2004-11-5
     * 说明：根据工作流编号查询工作流名称
     * @param intWorkflowID int
     * @throws Exception
     * @return String： 工作流名称
     */
    public static String getWorkflwoName(int intWorkflowID) throws Exception {
        String strWorkflowName = new String();
        ProcessInfo pi = new ProcessInfo();
        pi.setID(intWorkflowID);
        List listPI = pi.searchObject();
        if (listPI != null && listPI.size() > 0) {
            pi = (ProcessInfo)listPI.get(0);
            strWorkflowName = pi.getName();
        }
        else {
            throw new Exception("无法获得工作流名称");
        }
        return strWorkflowName;
    }

    /**
     * 程序：李扬
     * 时间：2004-11-26
     * 说明：从XML中获取最大的工作流编号
     * @return int
     * @throws Exception
     */
    public static int getMaxWorkflowID() throws Exception {
        int intWorkflowID = -1;
        ProcessInfo pi = new ProcessInfo();
        List listPI = new ArrayList();
        listPI = pi.searchObject();
        //循环比较获得最大的工作流编号
        for (int i = 0; listPI != null && i < listPI.size(); i++) {
            pi = (ProcessInfo)listPI.get(i);
            if (pi.getID() > intWorkflowID) {
                intWorkflowID = pi.getID();
            }
        }
        //如果intWorkflowID仍然等于-1，那么说明XML中没有工作流定义，则返回0作为最大的工作流编号
        if (intWorkflowID == -1) {
            intWorkflowID = 0;
        }
        return intWorkflowID;
    }
}
