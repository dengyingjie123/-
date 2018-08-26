package com.youngbook.common.wf.processdefine;
import java.util.*;
import java.io.*;
import java.sql.*;

import com.youngbook.common.MyException;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;
import org.jdom.xpath.XPath;
import com.youngbook.common.wf.common.*;
import com.youngbook.common.wf.admin.*;
/**
 * <p>Title: </p>
 * <p>Description: 工作流节点类，定义工作流节点信息</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class Node {
    public Node() throws Exception {
        builder = new SAXBuilder();
        doc = builder.build(CommonInfo.getXMLPath() + "process.xml");
        root = doc.getRootElement();
    }
    Element root;
    protected SAXBuilder builder;
    protected Document doc;
    protected int intWorkflowID=Integer.MAX_VALUE;//工作流编号
    protected int intID=Integer.MAX_VALUE;//节点编号
    protected String strName=new String();//节点名称
    protected String strStatu=new String();//节点状态描述
    protected int intType=Integer.MAX_VALUE;//节点类型 0：开始节点 1：中间节点 2：结束节点
    protected List lsTransition=new ArrayList();//可转向的节点列表
    protected String strCondition_In=new String();//进入节点条件
    protected String strCondition_Out=new String();//离开节点条件
    protected List lsBizField=new ArrayList();//节点状态

    /**
     * 程序：李扬
     * 时间：2004-11-15
     * 说明：销毁Node这个类，在Session Bean中应用
     * @throws Exception
     */
    public void destroy() throws Exception {
        builder = new SAXBuilder();
        doc = builder.build(CommonInfo.getXMLPath() + "process.xml");
        root = doc.getRootElement();
    }
    /**
     * 获得工作流编号
     * @return int
     */
    public int getWorkflowID(){
        return intWorkflowID;
    }

    /**
     * 获得节点编号
     * @return int
     */
    public int getID(){
        return intID;
    }

    /**
     * 获得节点名称
     * @return String
     */
    public String getName(){
        return strName;
    }

    /**
     * 获得节点状态
     * @return String
     */
    public String getStatu(){
        return strStatu;
    }

    /**
     * 获得节点类型
     * @return int
     */
    public int getType(){
        return intType;
    }

    /**
     * 获得该节点可以流向的节点
     * @return List
     */
    public List getTransition(){
        return lsTransition;
    }

    /**
     * 获得该节点的进入条件
     * @return String
     */
    public String getCondition_In(){
        return strCondition_In;
    }

    /**
     * 获得该节点的离开条件
     * @return String
     */
    public String getCondition_Out(){
        return strCondition_Out;
    }

    /**
     * 获得节点可编辑字段
     * @return List
     */
    public List getBizField(){
        return lsBizField;
    }

    /**
     * 设置工作流编号
     * @param WorkflowID int
     */
    public void setWorkflowID(int WorkflowID){
        intWorkflowID=WorkflowID;
    }

    /**
     * 设置节点编号
     * @param ID int
     */
    public void setID(int ID){
        intID=ID;
    }

    /**
     * 设置节电名称
     * @param Name String
     */
    public void setName(String Name){
        strName=Name;
    }

    /**
     * 设置节点状态
     * @param Statu String
     */
    public void setStatu(String Statu){
        strStatu=Statu;
    }

    /**
     * 设置节点类型
     * @param Type int
     */
    public void setType(int Type){
        intType=Type;
    }

    /**
     * 摄制该节点可以流向的节点
     * @param Transition List
     */
    public void setTransition(List Transition){
        lsTransition=Transition;
    }

    /**
     * 设置节点的进入条件
     * @param Condition_In String
     */
    public void setCondition_In(String Condition_In){
        strCondition_In=Condition_In;
    }

    /**
     * 设置节点的离开条件
     * @param Condition_Out String
     */
    public void setCondition_Out(String Condition_Out){
        strCondition_Out=Condition_Out;
    }

    /**
     * 设置节点可编辑的字段
     * @param BizField List
     */
    public void setBizField(List BizField){
        lsBizField=BizField;
    }

    /**
     * 保存该节点信息到XML文件中
     * @throws Exception
     */
    private void Save() throws Exception {
        Tools.WriteXML("process.xml", doc);
    }
    /**
     * 程序：
     * 说明：新增工作流节点
     * @return 0:失败，1:成功
     */
    public int insert() throws Exception {
        boolean bolWorkflowEx=true;
        if (intWorkflowID==Integer.MAX_VALUE||intID==Integer.MAX_VALUE){
            throw new Exception("Node|insert|NoPara|执行Node.insert()方法时发生参数不足异常，无法获得所需的工作流编号(WorkflowID)或是节点编号(NodeID)。");
        }
        if (intID == 0) {
            throw new Exception("Node|insert|BizLogic|执行Node.insert()方法时发生业务逻辑异常，新增节点编号不能为0。");
        }
        List lsex=XPath.selectNodes(root,"/DmisWorkflow_Proce/WorkflowProcess[@WorkflowID='"+String.valueOf(intWorkflowID)+"']/Node[@ID='"+String.valueOf(intID)+"']");
        if (lsex!=null&&lsex.size()>0){
            System.out.println("工作"+lsex.size());
            throw new Exception("Node|insert|BizLogic|执行Node.insert()方法时发生业务逻辑异常，指定的编号已存在，请更改节点编号。");
        }
        int result=1;
        Element elem;
        //判断工作流元素是否存在
        List lsworkflow=XPath.selectNodes(root,"/DmisWorkflow_Proce/WorkflowProcess[@WorkflowID='"+String.valueOf(intWorkflowID)+"']");
        if (lsworkflow!=null&&lsworkflow.size()>0)
        {//工作流元素存在时
            elem=(Element)lsworkflow.get(0);
        }
        else
        {//工作流元素不存在时
            bolWorkflowEx=false;
            elem=new Element("WorkflowProcess");
            elem.setAttribute("WorkflowID",String.valueOf(intWorkflowID));
        }
        //结束判断
        // 设置各种属性
        Element elemNode=new Element("Node");
        elemNode.setAttribute("ID",String.valueOf(intID));
        elemNode.setAttribute("Name",Tools.Encode(strName));
        elemNode.setAttribute("Statu",Tools.Encode(strStatu));
        elemNode.setAttribute("Type",String.valueOf(intType));
        //设置可转向节点
        Iterator itTrans=lsTransition.iterator();
        while (itTrans.hasNext()){
            Element elemTrans=new Element("Transition");
            elemTrans.setAttribute("To",(String)itTrans.next());
            elemNode.addContent(elemTrans);
        }
        //设置可转向节点结束
        Element elemCondition_In=new Element("Condition_In");//进入条件
        elemCondition_In.setText(Tools.Encode(strCondition_In));
        elemNode.addContent(elemCondition_In);
        Element elemCondition_Out=new Element("Condition_Out");//离开条件
        elemCondition_Out.setText(Tools.Encode(strCondition_Out));
        elemNode.addContent(elemCondition_Out);
        //设置业务数据字段状态
        Element elemBizField=new Element("FieldStatu");
        Iterator itBizField=lsBizField.iterator();
        while (itBizField.hasNext()){
            BizField bizfield=(BizField)itBizField.next();
            Element elemfield=new Element("Field");
            elemfield.setAttribute("Name",bizfield.getName());
            elemfield.setAttribute("Statu",String.valueOf(bizfield.getState()));
            elemBizField.addContent(elemfield);
        }
        elemNode.addContent(elemBizField);
        //设置业务数据字段状态结束
        elem.addContent(elemNode);
        if (!bolWorkflowEx){
            root.addContent(elem);
        }
        Save();
        return result;
    }

    /**
     * 程序：
     * 说明：修改工作流节点
     * @return 0:失败，1:成功
     */

    public int update() throws Exception {
        int result=0;
        int delresult=updateDel();
        if (delresult == 0) {
            throw new Exception("Node|update|DbFail|执行Node.update()方法时发生数据库操作失败异常，修改失败。");
        }
        int insertresult=insert();
        if (delresult==1&&insertresult==1){
            result=1;
        }
        return result;
    }
    /**
     * 程序：
     * 说明：查找工作流节点信息
     * @return XML节点的Element
     * @throws java.lang.Exception
     */
    public List searchNodeElem() throws Exception {
        if (intWorkflowID == Integer.MAX_VALUE) {
            throw new Exception("Node|searchNodeElem|NoPara|执行Node.searchNodeElem()方法时发生参数不足异常，无法获得所需的工作流编号(WorkflowID)。");
        }
        List reList=new ArrayList();
        if (intID==Integer.MAX_VALUE){ //不指定节点编号，返回所有节点
            reList = XPath.selectNodes(root, "/DmisWorkflow_Proce/WorkflowProcess[@WorkflowID='"+String.valueOf(intWorkflowID)+"']/Node");
        }else{//指定节点编号，返回某节点
            reList = XPath.selectNodes(root, "/DmisWorkflow_Proce/WorkflowProcess[@WorkflowID='"+String.valueOf(intWorkflowID)+"']/Node[@ID='"+String.valueOf(intID)+"']");
        }
        //排序
        int size=reList==null?0:reList.size();
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                Element ele = (Element) reList.get(i);
                Element ele1 = (Element) reList.get(j);
                if (Integer.parseInt(ele.getAttribute("ID").getValue()) >
                        Integer.parseInt(ele1.getAttribute("ID").getValue())) {
                    reList.set(i, ele1);
                    reList.set(j, ele);
                }
            }
        }

        return reList;
    }
    /**
     * 程序：
     * 说明：查找工作流节点信息
     * @return Node对象
     * @throws java.lang.Exception
     */
    public List searchNodeObject() throws Exception {
        if (intWorkflowID == Integer.MAX_VALUE) {
            throw new Exception("Node|searchNodeObject|NoPara|执行Node.searchNodeObject()方法时发生参数不足异常，无法获得所需的工作流编号(WorkflowID)。");
        }
        List lsElem=searchNodeElem();
        Iterator it=lsElem.iterator();
        List reList=new ArrayList();
        while (it.hasNext()){
            Node node=BuildObject((Element)it.next());
            reList.add(node);
        }
        return reList;
    }

    /**
     * 根据Attrubute获取指定 process.xml 中 Transition 中的节点
     * @param Attrubute
     * @return
     * @throws Exception
     */
    public  List searchNodeObject(String Attrubute)  throws Exception {
        if (intWorkflowID == Integer.MAX_VALUE) {
            throw new Exception("Node|searchNodeObject|NoPara|执行Node.searchNodeObject()方法时发生参数不足异常，无法获得所需的工作流编号(WorkflowID)。");
        }
        List lsElem=searchNodeElem();
        Iterator it=lsElem.iterator();
        List reList=new ArrayList();
        while (it.hasNext()){
            Node node=BuildObject((Element)it.next(),Attrubute);
            reList.add(node);
        }
        return reList;
    }

    public static Node searchNodeObject(int workflowID, int ID,String Attrubute) throws Exception {
        try {
            Node node = new Node();
            node.setWorkflowID(workflowID);
            node.setID(ID);
            List listNode = node.searchNodeObject(Attrubute);
            if (listNode != null && listNode.size() > 0) {
                for (int i = 0; i < listNode.size(); i++) {
                    Node nodeTemp = (Node)listNode.get(i);
                    if (nodeTemp.getID() == node.getID()) {
                        node = nodeTemp;
                        break;
                    }
                }
            }
            return node;
        }
        catch (Exception e) {
            throw e;
        }
    }


    public static Node searchNodeObject(int workflowID, int ID) throws Exception {
        try {
            Node node = new Node();
            node.setWorkflowID(workflowID);
            node.setID(ID);
            List listNode = node.searchNodeObject();
            if (listNode != null && listNode.size() > 0) {
                for (int i = 0; i < listNode.size(); i++) {
                    Node nodeTemp = (Node)listNode.get(i);
                    if (nodeTemp.getID() == node.getID()) {
                        node = nodeTemp;
                        break;
                    }
                }
            }
            return node;
        }
        catch (Exception e) {
            throw e;
        }
    }


    /**
     * 程序：
     * 说明：根据Node Element来构造Node对象
     * @param elem Element信息
     * @return Node对象
     * @throws java.lang.Exception
     */
    public Node BuildObject(Element elem) throws Exception {
        Node node=new Node();
        node.setWorkflowID(intWorkflowID);
        node.setID(Integer.parseInt(elem.getAttribute("ID").getValue()));
        node.setName(Tools.Decode(elem.getAttribute("Name").getValue()));
        node.setStatu(Tools.Decode(elem.getAttribute("Statu").getValue()));
        node.setType(Integer.parseInt(elem.getAttribute("Type").getValue()));
        node.setCondition_In(Tools.Decode(elem.getChild("Condition_In").getText()));
        node.setCondition_Out(Tools.Decode(elem.getChild("Condition_Out").getText()));
        //取出可转向节点
        List lsTrans=elem.getChildren("Transition");//从xml中读出的list
        Iterator itTrans=lsTrans.iterator();
        List lsreTrans=new ArrayList();//作为属性的list
        while (itTrans.hasNext()){
                lsreTrans.add(((Element) itTrans.next()).getAttribute("To").getValue());
        }
        node.setTransition(lsreTrans);
        //可转向节点完成
        //取出业务数据字段状态
        List lsField=elem.getChild("FieldStatu").getChildren();
        Iterator itField=lsField.iterator();
        List lsreField=new ArrayList();

        while (itField.hasNext()){
            BizField bizfield=new BizField();
            Element elemField=(Element)itField.next();
            bizfield.setName(elemField.getAttribute("Name").getValue());
            bizfield.setState(Integer.parseInt(elemField.getAttribute("Statu").getValue()));
            lsreField.add(bizfield);
        }

        node.setBizField(lsreField);
        //取出业务数据字段结束
        return node;
    }

    /**
     *自动转发：获取自动转发 节点为Attribute = Attrubute 的转发节点
     * @param elem Element信息
     * @param Attrubute Attrubute
     * @return Node对象
     * @throws Exception
     */
    public Node BuildObject(Element elem,String Attrubute) throws Exception {
        Node node=new Node();
        node.setWorkflowID(intWorkflowID);
        node.setID(Integer.parseInt(elem.getAttribute("ID").getValue()));
        node.setName(Tools.Decode(elem.getAttribute("Name").getValue()));
        node.setStatu(Tools.Decode(elem.getAttribute("Statu").getValue()));
        node.setType(Integer.parseInt(elem.getAttribute("Type").getValue()));
        node.setCondition_In(Tools.Decode(elem.getChild("Condition_In").getText()));
        node.setCondition_Out(Tools.Decode(elem.getChild("Condition_Out").getText()));
        //取出可转向节点
        List lsTrans=elem.getChildren("Transition");//从xml中读出的list
        Iterator itTrans=lsTrans.iterator();
        List lsreTrans=new ArrayList();//作为属性的list
        while (itTrans.hasNext()){
                Element element = (Element)itTrans.next();
            //获取节点属性
            String AttributeValue = element.getAttribute("Attribute").getValue();

            //验证字符的合法性
            if(!AttributeValue.equals("Dw") && !AttributeValue.equals("Up") ){
                MyException.deal(new Exception("当前文件：process.xml ,WorkflowID="+intWorkflowID+"配置错误；Attribute 配置错误 应为：'Dw' 或 ‘Up’"));
            }
            //获取所有可以向下转发的节点
            if(AttributeValue.equals(Attrubute) || AttributeValue == Attrubute ) {
                lsreTrans.add(element.getAttribute("To").getValue());
            }
        }
        node.setTransition(lsreTrans);
        //可转向节点完成
        //取出业务数据字段状态
        List lsField=elem.getChild("FieldStatu").getChildren();
        Iterator itField=lsField.iterator();
        List lsreField=new ArrayList();

        while (itField.hasNext()){
            BizField bizfield=new BizField();
            Element elemField=(Element)itField.next();
            bizfield.setName(elemField.getAttribute("Name").getValue());
            bizfield.setState(Integer.parseInt(elemField.getAttribute("Statu").getValue()));
            lsreField.add(bizfield);
        }

        node.setBizField(lsreField);
        //取出业务数据字段结束
        return node;
    }


    /**
     * 程序：
     * 说明：删除工作流节点
     * @return 0:失败，1:成功
     */

    public int delete() throws Exception {
        Connection conn = Tools.getDBConn();
        try {
            conn.setAutoCommit(false);
            List lsdel = searchNodeElem();
            if (lsdel == null || lsdel.size() != 1) {
                throw new Exception("Node|Delete|DbFail|执行Node.Delete()方法时发生数据库操作失败异常，未找到需要删除的节点。");
            }

            //判断该节点所属的工作流是否在数据库表Workflow_RouteList中有记录
            //如果有记录则不允许用户删除
            RouteList routelist = new RouteList();
            routelist.setWorkflowID(intWorkflowID);
            List listResult = routelist.query(conn);
            if (listResult != null && listResult.size() > 0) {
                throw new Exception("Node|Delete|BizLogic|执行Node.Delete()方法时发生业务逻辑异常，数据库其他表中含有与该节点纪录相关的信息，禁止删除该节点。");
            }

            //删除workflow_Participant数据表中相关记录
            Participant pt = new Participant();
            pt.setWorkflowID(intWorkflowID);
            pt.setNodeID(intID);
            pt.Delete(conn);

            //删除DmisWorkflow_Proce.xml文件中相关内容
            //主要是节点转向信息
            SAXBuilder builderDel = new SAXBuilder();
            Document docDel = builderDel.build(CommonInfo.getXMLPath() +
                    "DmisWorkflow_Proce.xml");
            Element rootDel = docDel.getRootElement();
            List listTran = XPath.selectNodes(rootDel,
                    "/DmisWorkflow_Proce/WorkflowProcess[@WorkflowID='" +
                            String.valueOf(intWorkflowID) +
                            "']/Node/Transition[@To='" +
                            String.valueOf(intID) + "']");
            try {
                for (int i = 0; listTran != null && i < listTran.size(); i++) {
                    Element elemTran = (Element) listTran.get(i);
                    elemTran.getParent().removeContent(elemTran);
                }
                Tools.WriteXML("process.xml", docDel);
            }
            catch (Exception e) {
                throw e;
            }

            //删除DmisWorkflow_Proce.xml中的节点
            int intResult = 1;
            Element delelem = (Element) lsdel.get(0);
            intResult = delelem.getParent().removeContent(delelem) ? 1 : 0;
            Save();

            conn.commit();
            /**
             * 已经实现
             * 1、删除process.xml文件中相关内容  实现
             * 3、删除workflow_Participant数据表中相关记录   实现
             * 4、删除workflow_RouteList数据表中相关记录     实现
             * 5、删除workflow_Action数据表中相关记录    不需要完成
             * 6、删除workflow_History数据表中相关记录   不需要完成
             */

            return intResult;
        }
        catch (Exception e) {
            conn.rollback();
            throw e;
        }
        finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public int updateDel() throws Exception {
        try {
            List lsdel = searchNodeElem();
            if (lsdel == null || lsdel.size() != 1) {
                throw new Exception("Node|updateDel|DbFail|执行Node.updateDel()方法时发生数据库操作失败异常，未找到需要删除的节点。");
            }

            //删除process.xml中的节点
            int intResult = 1;
            Element delelem = (Element) lsdel.get(0);
            intResult = delelem.getParent().removeContent(delelem) ? 1 : 0;
            Save();
            return intResult;
        }
        catch (Exception e) {
            throw e;
        }
    }


    /**
     * 程序：李扬
     * 时间：2004-11-5
     * 说明：根据工作流编号和节点编号获得该节点的名称
     * @param intWorkflowID int
     * @param intNodeID int
     * @throws Exception
     * @return String 节电名称
     */
    public static String getNodeName(int intWorkflowID, int intNodeID) throws Exception {
        Node node = Node.searchNodeObject(intWorkflowID, intNodeID);
        return node.getName();
    }

    /**
     * 程序：李升华
     * 时间：2004-11-16
     * 说明：根据工作流编号获得最大的节点编号
     * @param intWorkflowID  工作流编号
     * @return MaxID最大的节点编号
     */
    public static int getMaxNodeID(int intWorkflowID)throws Exception {
        Node node = new Node();
        node.setWorkflowID(intWorkflowID);
        List listNode = node.searchNodeObject();
        int intID = 0;
        int MaxID = 0;
        if (listNode != null && listNode.size() > 0) {
            for(int i=0;i<listNode.size();i++){
                Node nodeTemp = (Node)listNode.get(i);
                intID = nodeTemp.getID();
                if(intID > MaxID){
                    MaxID = intID;
                }
            }
        }
        return MaxID;
    }
}
