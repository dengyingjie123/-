package com.youngbook.common.wf.processdefine;

import java.util.*;
import java.sql.*;
import com.youngbook.common.wf.common.*;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.xpath.XPath;

public class BizData {
    protected SAXBuilder builder;
    protected Document doc;
    protected Element root;
    protected int intWorkflowID = Integer.MAX_VALUE; //工作流编号
    protected String strDataTable = new String(); //数据库中表名
    protected List listBizField = new ArrayList(); //数据库表信息列表

    public BizData() throws Exception {
        builder = new SAXBuilder();
        doc = builder.build(CommonInfo.getXMLPath() + "data.xml");
        root = doc.getRootElement();
    }

    /**
     * 程序：李扬
     * 说明：返回指定编号的业务数据配置定义，如果没有指定编号，返回所有
     * 返回类型：Element 组成的 List
     * @throws Exception
     * @return List
     */
    public List search() throws Exception {
        try {
            List listResult = new ArrayList();
            if (intWorkflowID == Integer.MAX_VALUE) {
                listResult = XPath.selectNodes(root,
                        "/DmisWorkflow_Data/WorkflowBizData");
            }
            else {
                listResult = XPath.selectNodes(root,
                        "/DmisWorkflow_Data/WorkflowBizData[" +
                                "@WorkflowID='" +
                                String.valueOf(intWorkflowID) + "']");

            }
            return listResult;
        }
        catch (Exception e) {
            throw e;
        }
    }

    /**
     * 程序：李扬
     * 说明：返回指定编号的业务数据配置定义，如果没有指定编号，返回所有
     * 返回类型：Object 组成的 List
     * @throws Exception
     * @return List
     */
    public List searchObject() throws Exception {
        try {
            List listResult = new ArrayList();
            List lsElem = search();
            Iterator itElem = lsElem.iterator();
            while (itElem.hasNext()) {
                BizData bizdata = buildObject( (Element) itElem.next());
                listResult.add(bizdata);
            }
            return listResult;
        }
        catch (Exception e) {
            throw e;
        }
    }

    /**
     * 程序：李扬
     * 说明：新增业务数据配置
     * 返回值：0：失败， 1：成功
     * @throws Exception
     * @return int
     */
    public int insert() throws Exception {
        try {
            int intResult = 0;
            if (intWorkflowID == Integer.MAX_VALUE) {
                throw new Exception(
                        "BizData|insert|NoPara|执行BizData.insert()方法时发生参数不足异常，无法获得所需的工作流编号(WorkflowID)。");
            }
            List listResult = search();
            if (listResult != null && listResult.size() > 0) {
                throw new Exception(
                        "BizData|insert|BizLogic|执行BizData.insert()方法时发生业务逻辑异常，指定业务数据表已存在。");
            }
            root.addContent(buildElem());
            Tools.WriteXML("data.xml", doc);
            return intResult;
        }
        catch (Exception e) {
            throw e;
        }
    }

    /**
     * 程序：李扬
     * 说明：删除指定的业务数据配置
     * 返回值： 0：失败， 1：成功
     * @throws Exception
     * @return int
     */
    public int Delete() throws Exception {
        Connection conn = null;
        Statement statement = null;
        try {
            int intResult = 0;
            if (intWorkflowID == Integer.MAX_VALUE) {
                throw new Exception(
                        "BizData|Delete|NoPara|执行BizData.Delete()方法时发生参数不足异常，无法获得所需的工作流编号(WorkflowID)。");
            }
            List listResult = search();
            if (listResult != null && listResult.size() > 0) {
                Element elem = (Element) listResult.get(0);
                intResult = root.removeContent(elem) ? 1 : 0;
            }

            Tools.WriteXML("data.xml", doc);

            return intResult;
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * 程序：李扬
     * 编写时间：2004-10-21
     * 说明：修改指定编号的业务数据配置
     * 返回值：0： 失败， 1：成功
     * @throws Exception
     * @return int
     */
    public int update() throws Exception {
        try {
            int intResult = 0;
            if (intWorkflowID == Integer.MAX_VALUE) {
                throw new Exception(
                        "BizData|update|NoPara|执行BizData.update()方法时发生参数不足异常，无法获得所需的工作流编号(WorkflowID)。");
            }
            List listResult = search();
            //找不到对应的业务数据配置，则插入新的数据配置
            if (listResult == null || listResult.size() == 0) {
                insert();
            }
            //能找到对应的业务数据配置，则更新该配置
            if (listResult != null && listResult.size() > 0) {
                Delete();
                insert();
            }
            return intResult;

        }
        catch (Exception e) {
            throw e;
        }
    }

    /**
     * 程序：李扬
     * 时间：2004-11-17
     * 说明：根据工作流编号，构造BizData
     * @param WorkflowID int
     * @return BizData
     * @throws Exception
     */
    public static BizData buildObject(int WorkflowID) throws Exception {
        BizData bizdata = new BizData();
        bizdata.setWorkflowID(WorkflowID);
        List listBD = new ArrayList();
        listBD = bizdata.searchObject();
        if (listBD != null && listBD.size() == 1) {
            bizdata = (BizData) listBD.get(0);
        }
        else {
            throw new Exception("BizData|buildObject|NoPara|执行BizData.buildObject(int WorkflowID)时发生参数不足异常， 无法根据所得该工作流");
        }
        return bizdata;
    }

    /**
     * 程序：李扬
     * 编写时间：2004-10-21
     * 说明：根据 XML Element来构造一个BizData对象
     * @param elem Element
     * @throws Exception
     * @return BizData
     */
    public static BizData buildObject(Element elem) throws Exception {
        try {
            BizData bd = new BizData();
            bd.setWorkflowID(Integer.parseInt(elem.getAttribute("WorkflowID").
                    getValue()));
            bd.setDataTable(elem.getAttribute("DataTable").getValue());
            List listField = (List) elem.getChildren("Field");
            Iterator itField = listField.iterator();
            List listBizField_temp = new ArrayList();
            while (itField.hasNext()) {
                Element elemField = (Element) itField.next();
                BizField ti = new BizField();
                ti.setIndex(Integer.parseInt(elemField.getAttribute("Index").getValue()));
                ti.setName(elemField.getAttribute("Name").getValue());
                ti.setTitle(elemField.getAttribute("Title").getValue());
                ti.setType(elemField.getAttribute("Type").getValue());
                listBizField_temp.add(ti);
            }
            bd.setBizField(listBizField_temp);
            return bd;
        }
        catch (Exception e) {
            throw e;
        }
    }

    /**
     * 程序：李扬
     * 编写时间：2004-10-21
     * 说明：根据BizData对象构造XML Element
     * @throws Exception
     * @return Element
     */
    public Element buildElem() throws Exception {
        try {
            Element newElem = new Element("WorkflowBizData");
            newElem.setAttribute("WorkflowID", String.valueOf(intWorkflowID));
            newElem.setAttribute("DataTable", strDataTable);
            Iterator itTableInfo = listBizField.iterator();
            while (itTableInfo.hasNext()) {
                BizField ti = (BizField) itTableInfo.next();
                Element elemField = new Element("Field");
                elemField.setAttribute("Index", String.valueOf(ti.getIndex()));
                elemField.setAttribute("Name", ti.getName());
                elemField.setAttribute("Title", Tools.Encode(ti.getTitle()));
                elemField.setAttribute("Type", ti.getType());
                newElem.addContent(elemField);
            }
            return newElem;
        }
        catch (Exception e) {
            throw e;
        }
    }

    public void setWorkflowID(int WorkflowID) {
        intWorkflowID = WorkflowID;
    }

    public void setDataTable(String dataTable) {
        strDataTable = dataTable;
    }

    public void setBizField(List BizField) {
        listBizField = BizField;
    }

    /**
     * 程序：李扬
     * 编写时间：2004-10-21
     * 说明：从数据库中查找表结构，组织一个BizField
     * @throws Exception
     */
    public void initBizField() throws Exception {
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            if (strDataTable != null && !strDataTable.equals("")) {
                String strSQL = "SELECT * FROM " + strDataTable + " limit 1";
                // 后台打印查询语句
                System.out.println("BizData.initBizField(): " + strSQL);
                conn = Tools.getDBConn();
                statement = conn.createStatement();
                try {
                    rs = statement.executeQuery(strSQL);
                }
                catch (Exception e) {
                    throw new Exception("BizData|initBizField|DbFial|执行BizData.initBizField()发生数据库操作异常，请确定表或视图 "+strDataTable+" 是否存在。");
                }
                if (rs != null) {
                    listBizField = new ArrayList();
                    ResultSetMetaData rsma = rs.getMetaData();
                    for (int i = 1; i <= rsma.getColumnCount(); i++) {
                        BizField bf = new BizField();
                        //设置字段序号，从0开始
                        bf.setIndex(i - 1);
                        //设置字段名
                        bf.setName(rsma.getColumnName(i).toUpperCase());
                        //设置字段显示名
                        bf.setTitle(rsma.getColumnLabel(i));
                        //设置字段类型，数据库中定义的类型（NUMBER, VARCHAR2）
                        bf.setType(rsma.getColumnTypeName(i));
                        //设置字段的范围， 例如 NUMBER(10,2)，此处的值为10
                        bf.setPrecision(rsma.getPrecision(i));
                        //设置字段的精度， 例如 NUMBER(10,2)，此处的值为2
                        bf.setScale(rsma.getScale(i));
                        listBizField.add(bf);
                    }
                    //初始化BizField其余部分的值，用于自动生成代码
                    for (int i = 0; listBizField != null && i < listBizField.size(); i++) {
                        BizField bf = (BizField) listBizField.get(i);
                        if (bf.getType().equalsIgnoreCase("VARCHAR") ||
                                bf.getType().equalsIgnoreCase("DATE") ||
                                bf.getType().equalsIgnoreCase("CHAR")) {
                            bf.setCodeType("String");
                            bf.getCodeName().append("str");
                            bf.setInitValue("new String()");
                        }
                        else if (bf.getType().equalsIgnoreCase("int")) {
                            if (bf.getScale() == 0) {
                                bf.setCodeType("int");
                                bf.getCodeName().append("int");
                                bf.setInitValue("Integer.MAX_VALUE");
                            }
                            else {
                                bf.setCodeType("double");
                                bf.getCodeName().append("double");
                                bf.setInitValue("Double.MAX_VALUE");
                            }
                        }
                        //将字段名全部变为大写
                        bf.getCodeName().append(bf.getName().toUpperCase());
                    }
                }
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (rs != null) {
                rs.close();
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
     * 程序：李扬
     * 时间：2004-11-22
     * 说明：根据XML文件中配置的字段显示名，来初始化从数据库中查出的字段显示名
     * @throws Exception
     */
    public void initFieldTitle() throws Exception {
        BizData bd = BizData.buildObject(intWorkflowID);
        for (int i = 0; bd.getBizField() != null && listBizField != null &&
                listBizField.size() == bd.getBizField().size() && i < bd.getBizField().size(); i++) {
            BizField bf = (BizField)listBizField.get(i);
            BizField bfTemp = (BizField)bd.getBizField().get(i);
            bf.setTitle(bfTemp.getTitle());
        }
    }

    /**
     * 程序：李扬
     * 时间：2004-11-5
     * 说明：根据工作流号和字段，获得字段的名称
     * @param intWorkflowID int
     * @param strName String
     * @throws Exception
     * @return String ：字段的名称
     */
    public static String getFieldTitle(int intWorkflowID, String strName) throws
            Exception {
        try {
            String strTitle = new String();
            BizData bd = BizData.buildObject(intWorkflowID);
//      bd.initBizField();
//      bd.initFieldTitle();
            List listBF = bd.getBizField();
            for (int i = 0; listBF != null && i < listBF.size(); i++) {
                BizField bf = (BizField) listBF.get(i);
                if (bf.getName().equals(strName)) {
                    strTitle = bf.getTitle();
                    break;
                }
            }
            return strTitle;
        }
        catch (Exception e) {
            throw e;
        }
    }

    public int getWorkflowID() {
        return intWorkflowID;
    }

    public List getBizField() {
        return listBizField;
    }

    public String getDataTable() {
        return strDataTable;
    }

    public Element getRoot() {
        return root;
    }
}
