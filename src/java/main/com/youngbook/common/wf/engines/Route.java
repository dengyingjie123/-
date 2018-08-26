package com.youngbook.common.wf.engines;

import java.util.*;
import java.sql.Connection;

import com.youngbook.common.MyException;
import com.youngbook.common.config.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.wf.clientapp.ClientApplications;
import com.youngbook.common.wf.processdefine.*;
import com.youngbook.common.wf.engines.*;
import com.youngbook.common.wf.admin.*;
import com.youngbook.common.wf.common.*;

/**
 * <p>Title: </p>
 * <p>Description:工作流路由类 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class Route {
    public Route() {
    }

    private int intServiceType = Integer.MAX_VALUE;

    /**
     * 对于strServiceType，HTML页面上的ServiceType字串的说明：
     * 1、SaveOnly：只保存，不转发业务流程（注：不是工作流，是业务流，保留，尚未实现）
     * 2、SaveForward：保存并转发业务流程数据项
     * 3、AutoForward：自动转发至可达节点，自动转发后，不满足进入条件的节点，在业务数据发生改变时，不会被再次评估转发
     * 4、Cancel：中止业务流
     * 5、Over：完成业务（只有处于节点类型为：结束节点时才有效）
     * 6、Reject：退回业务(只有在串行业务处理时能确保正确被退回，
     *                   如是并行业务，如：从节点1、节点2转发于节点3，
     *                   节点3使用退回业务时，不能完全退回至节点1和节点2，
     *                   只会退至第一个转发至节点3的源节点)
     */
    protected int intWorkflowID = Integer.MAX_VALUE; //工作流编号
    protected int intCurrentNode = Integer.MAX_VALUE; //当前节点编号
    protected int intRouteListID = 0; //当前业务路由记录编号，初始化时编号为0，表示业务第一次开始流转
    protected ProcessInfo pi; //工作流对象
    protected String strParticipant = new String(); //参与者
    protected String strNextNode[] = null; //下一转向节点
    protected IBizDao bizdao; //业务流数据操作对象

    public void setServiceType(int ServiceType) {
        intServiceType = ServiceType;
    }

    public int getServiceType() {
        return intServiceType;
    }

    public void setRouteListID(int RouteListID) {
        intRouteListID = RouteListID;
    }

    public int getRouteListID() {
        return intRouteListID;
    }

    public void setCurrentNode(int CurrentNode) {
        intCurrentNode = CurrentNode;
    }

    public int getCurrentNode() {
        return intCurrentNode;
    }

    public void setParticipant(String Participant) {
        strParticipant = Participant;
    }

    public String getParticipant() {
        return strParticipant;
    }

    public void setWorkflowID(int WorkflowID) {
        intWorkflowID = WorkflowID;
    }

    public int getWorkflowID() {
        return intWorkflowID;
    }

    public void setpi(ProcessInfo Pi) {
        pi = Pi;
    }

    public ProcessInfo getpi() {
        return pi;
    }

    public void setBizDao(IBizDao Bizdao) {
        bizdao = Bizdao;
    }

    public IBizDao getBizDao() {
        return bizdao;
    }

    public void setNextNode(String[] NextNode) {
        strNextNode = NextNode;
    }

    public String[] getNextNode() {
        return strNextNode;
    }

    /**
     * 程序：
     * 说明：检查业务数据完整性
     * @throws java.lang.Exception
     */
    public void checkData() throws Exception {
        if (intWorkflowID == Integer.MAX_VALUE) {
            throw new Exception("Route|CheckDate|NoPara|执行Route.CheckDate()方法时发生参数不足异常，无法获得所需的工作流编号(WorkflowID)。");
        }
        if (intCurrentNode == Integer.MAX_VALUE) {
            throw new Exception("Route|CheckDate|NoPara|执行Route.CheckDate()方法时发生参数不足异常，无法获得所需的工作流当前节点编号(CurrentNode)。");
        }
        if (bizdao == null) {
            throw new Exception("Route|CheckDate|NoPara|执行Route.CheckDate()方法时发生参数不足异常，无法获得所需的业务流数据操作对象(BizDao)。");
        }
        if (intServiceType == Integer.MAX_VALUE) {
            throw new Exception("Route|CheckDate|NoPara|执行Route.CheckDate()方法时发生参数不足异常，无法获得所需的服务类型(ServiceType)。");
        }
        if (pi == null) {
            throw new Exception("Route|CheckDate|NoPara|执行Route.CheckDate()方法时发生参数不足异常，无法获得所需的工作流信息对象(ProcessInfo)。");
        }

    }

    /**
     * 程序：
     * 说明：保存并转发业务流程
     * @throws java.lang.Exception
     * @return RouteList对象
     */
    public RouteList saveForward(Connection conn) throws Exception {
        if (strNextNode == null || strNextNode.length == 0) {
            throw new Exception("Route|SaveForward|NoPara|执行Route.SaveForward()方法时发生参数不足异常，无法获得所需的转发的目标节点(NextNode)。");
        }
        checkData();
        //取出原有业务数据项数据快照
        HashMap hmHistory = new HashMap();
        if (pi.getSaveHistory()) {
            hmHistory = bizdao.dataSnapShot(conn);
        }
        //检查此业务流状态
        RouteList rl = new RouteList();
        rl.setID(intRouteListID);
        rl.setWorkflowID(intWorkflowID);
        rl.setYWID(bizdao.getYWID());
        Node node = Node.searchNodeObject(intWorkflowID, intCurrentNode);
        int intBizStatus = rl.queryStatus(conn);


        // 没有此业务，创建业务流程
        if (intBizStatus == RouteListStatus.NONE) {
            //Active
            if (bizdao.update(conn) < 1) {
                throw new Exception("Route|SaveForward|DbFail|执行Route.SaveForward()方法时发生数据库操作失败异常，用户工作流业务数据操作对象修改记录失败。");
            }

            createBiz(bizdao, rl, node, conn); //新增业务路由记录
            if (!conditionEstimate(node, bizdao, 1, conn)) {
                //是否符合离开条件
                throw new Exception("Route|SaveForward|Condition_Out|执行Route.SaveForward()方法时发生节点离开条件不满足异常，此业务流程数据不满足离开此节点的条件，不能被转发。");
            }

            //转发至一下节点

            transitionNextNode(strNextNode, rl, conn);
            //记录历史记录及动作
            saveActionAndHistory(rl, hmHistory, bizdao, conn);

            return rl;
        }


        if (intBizStatus != RouteListStatus.ACTIVE) {
            MyException.newInstance("当前业务不是活动状态").throwException();
        }


        if (bizdao.update(conn) < 1) {
            throw new Exception("Route|SaveForward|DbFail|执行Route.SaveForward()方法时发生数据库操作失败异常，用户工作流业务数据操作对象修改记录失败。");
        }

        //判断是否符合离开条件
        if (!conditionEstimate(node, bizdao, 1, conn)) {
            throw new Exception("Route|SaveForward|Condition_Out|执行Route.SaveForward()方法时发生节点离开条件不满足异常，此业务流程数据不满足离开此节点的条件，不能被转发。");
        }


        rl.setCurrentNode(intCurrentNode);

        rl.buildself();
        //转发至一下节点
        transitionNextNode(strNextNode, rl, conn);
        //记录历史记录及动作
        saveActionAndHistory(rl, hmHistory, bizdao, conn);
        //处理本业务流其他状态为：Wait的路由记录
        intServiceType = 2; //改变ServiceType为保存转发。当自动转发调用时，需要改为保存转发，以便TransitionNextNode方法，能正确记录未转发节点
        RouteList rlwait = new RouteList();
        rlwait.setWorkflowID(rl.getWorkflowID());
        rlwait.setYWID(rl.getYWID());
        rlwait.setStatus(RouteListStatus.WAIT);
        List lswait = rlwait.queryExact(conn);
        Iterator itwait = lswait.iterator();
        while (itwait.hasNext()) {
            RouteList rlwait_temp = (RouteList) itwait.next();
            String strNextNode = rlwait_temp.getNextNode();
            String[] strarryNextNode = strNextNode.split("\\|");
            transitionNextNode(strarryNextNode, rlwait_temp, conn);
        }

        return rl;
    }

    /**
     * 程序：李扬
     * 时间：2004-1-7
     * 说明：保存功能，只保存业务数据和记下操作记录，不进行流转
     * @return RouteList
     * @throws Exception
     */
    public RouteList saveOnly(Connection conn) throws Exception {
        RouteList rl = new RouteList();
        this.checkData();
        //1 获得数据快照
        HashMap hmHistory = new HashMap();
        if (pi.getSaveHistory()) {
            hmHistory = bizdao.dataSnapShot(conn);
        }
        //2 获得RouteList
        rl.setID(intRouteListID);
        rl.setWorkflowID(intWorkflowID);
        rl.setYWID(bizdao.getYWID());
        //3 保存Action
        int intBizStatu = rl.queryStatus(conn);
        if (intBizStatu == 1) {
            try {
                //更新bizdao
                if (bizdao.update(conn) < 1) {
                    throw new Exception("Route|SaveForward|DbFail|执行Route.SaveForward()方法时发生数据库操作失败异常，用户工作流业务数据操作对象修改记录失败。");
                }
                //保存Action
                rl.buildself();
                //记录历史记录及动作
                saveActionAndHistory(rl, hmHistory, bizdao, conn);
            }
            catch (Exception e) {
                MyException.deal(e);
            }
        }
        else if (intBizStatu == 4) {
            throw new Exception("Route|SaveOnly|BizLogic|执行Route.SaveOnly()方法时发生业务逻辑异常，此业务流程已被完成，不能被保存。");
        }
        else if (intBizStatu == 3) { //业务被中止
            throw new Exception("Route|SaveOnly|BizLogic|执行Route.SaveOnly()方法时发生业务逻辑异常，此业务流程已被中止，不能被保存。");
        }
        else if (intBizStatu == 5) { //业务已结束
            throw new Exception("Route|SaveOnly|BizLogic|执行Route.SaveOnly()方法时发生业务逻辑异常，此业务流程已结束，不能被保存。");
        }
        else if (intBizStatu == 6) { //业务不存在
            throw new Exception("Route|SaveOnly|BizLogic|执行Route.SaveOnly()方法时发生业务逻辑异常，此业务流程已结束，不能被保存。");
        }

        return rl;
    }

    /**
     * 程序：
     * 说明：自动转发功能 自动转发至可达节点，自动转发后，不满足进入条件的节点，在业务数据发生改变时，不会被再次评估转发
     * @return RouteList
     * @throws Exception
     */
    public RouteList autoForward(Connection conn ,String Attribute) throws Exception {
        Node node = Node.searchNodeObject(intWorkflowID, intCurrentNode,Attribute);
        List listTransition = node.getTransition();
        if (listTransition == null || listTransition.size() < 1) {
            throw new Exception("Route|AutoForward|NoPara|执行Route.AutoForward()方法时发生参数不足异常，无法获得所需的转发的目标节点(NextNode)。");
        }
        //获取所有的下一个节点
        strNextNode = new String[listTransition.size()];
        for (int i = 0; i < listTransition.size(); i++) {
            int intNextNodeID = Integer.parseInt((String) listTransition.get(i));
            strNextNode[i] = String.valueOf(intNextNodeID);
        }
        return saveForward(conn);
    }
    public RouteList autoForward() throws Exception {
        Node node = Node.searchNodeObject(intWorkflowID, intCurrentNode);
        List listTransition = node.getTransition();
        if (listTransition == null || listTransition.size() < 1) {
            throw new Exception("Route|AutoForward|NoPara|执行Route.AutoForward()方法时发生参数不足异常，无法获得所需的转发的目标节点(NextNode)。");
        }
        strNextNode = new String[listTransition.size()];
        for (int i = 0; i < listTransition.size(); i++) {
            int intNextNodeID = Integer.parseInt((String) listTransition.get(i));
            strNextNode[i] = String.valueOf(intNextNodeID);
        }
        return saveForward(null);
    }

    /**
     * 回退业务
     * @param conn
     * @return
     * @throws Exception
     */
    public RouteList reject(Connection conn) throws Exception {
        try {
            checkData();
            RouteList rl = new RouteList();
            rl.setID(intRouteListID);
            rl.setWorkflowID(intWorkflowID);
            rl.setYWID(bizdao.getYWID());
            int intBizStatu = rl.queryStatus(conn);
//      List lsrl = rl.query(); //查询出此业务流路由记录
            List lsrl = rl.queryExact(conn); //查询出此业务流路由记录
            if (lsrl == null || lsrl.size() < 1) {
                throw new Exception(
                        "Route|Reject|BizLogic|执行Route.Reject()方法时发生业务逻辑异常，无法找到当前工作流路由信息，不能被退回");
            }
            RouteList rltemp = (RouteList) lsrl.get(0);
            rl.buildself();
            int intPreNode = rltemp.getPreNode();

            if (intPreNode == 0) {
                throw new Exception(
                        "Route|Reject|BizLogic|执行Route.Reject()方法时发生业务逻辑异常，当前节点是业务流的开始节点，不能被退回");
            }
            if (intBizStatu == 4) {
                throw new Exception(
                        "Route|Reject|BizLogic|执行Route.Reject()方法时发生业务逻辑异常，此业务流程已完成，不能被退回。");
            }
            if (intBizStatu == 3) {
                throw new Exception(
                        "Route|Reject|BizLogic|执行Route.Reject()方法时发生业务逻辑异常，此业务流程已中止，不能被退回。");
            }
            if (intBizStatu == 5) {
                throw new Exception(
                        "Route|Reject|BizLogic|执行Route.Reject()方法时发生业务逻辑异常，此业务流程已完成，不能被退回。");
            }
            //取出原有业务数据项数据快照
            HashMap hmHistory = new HashMap();
            if (pi.getSaveHistory()) {
                hmHistory = bizdao.dataSnapShot(conn);
            }
            if (bizdao.update(conn) < 1) { //更新业务数据
                throw new Exception(
                        "Route|Over|DbFail|执行Route.Over()方法时发生数据库操作失败异常，用户工作流业务数据操作对象修改记录失败。");
            }
            String strNextNode[] = new String[1];
            strNextNode[0] = String.valueOf(intPreNode);

            transitionNextNode(strNextNode, rl, conn);

            saveActionAndHistory(rl, hmHistory, bizdao, conn);

            return rltemp;
        }
        catch (Exception e) {
            MyException.deal(e);
        }
        return null;
    }

    /**
     * 程序：李扬
     * 说明：结束业务流程
     * @return RouteList
     * @throws Exception
     */
    public RouteList over(Connection conn) throws Exception {
        checkData();

        //检查此业务流状态
        RouteList rl = new RouteList();
        rl.setID(intRouteListID);
        rl.setWorkflowID(intWorkflowID);
        rl.setYWID(bizdao.getYWID());
        Node node = Node.searchNodeObject(intWorkflowID, intCurrentNode);
        if (node.getType() != 2) { //如果当前节点不是结束结点，则不能结束业务，修改人： 2004-11-15
            throw new Exception(
                    "Route|Over|BizLogic|执行Route.Over()方法时发生业务逻辑异常，此当前结节类型不是结束节点，不能被完成。");
        }
        int intBizStatu = rl.queryStatus(conn);
        if (intBizStatu == 3) {
            throw new Exception(
                    "Route|Over|BizLogic|执行Route.Over()方法时发生业务逻辑异常，此业务流程已中止，不能被完成。");
        }
        if (intBizStatu == 5) {
            throw new Exception(
                    "Route|Over|BizLogic|执行Route.Over()方法时发生业务逻辑异常，此业务流程已完成，不能被再次完成。");
        }
        //取出原有业务数据项数据快照
        HashMap hmHistory = new HashMap();
        if (pi.getSaveHistory()) {
            hmHistory = bizdao.dataSnapShot(conn);
        }
        if (bizdao.update(conn) < 1) {
            throw new Exception(
                    "Route|Over|DbFail|执行Route.Over()方法时发生数据库操作失败异常，用户工作流业务数据操作对象修改记录失败。");
        }
        //判断是否符合该节点的离开条件
        if (!conditionEstimate(node, bizdao, 1, conn)) {
            throw new Exception("Route|Over|Condition_Out|执行Route.Over()方法时发生节点离开条件不满足异常，此业务流程数据不满足离开此节点的条件，不能被完成。");
        }



        //更新RouteList，设置其状态为5：完成
        rl.setStatus(RouteListStatus.OVER);
        rl.updateStatu(conn);
        rl.setID(intRouteListID);
        rl.buildself();
        this.saveActionAndHistory(rl, hmHistory, bizdao, conn);
        return rl;
    }

    /**
     * 程序：李扬
     * 说明：中止业务流程
     * @throws Exception
     * @return RouteList
     */
    public RouteList cancel(Connection conn) throws Exception {
        try {
            //检查数据
            checkData();
            //将workflow_routelist表中， ywid相同的记录中statu字值段改为：3
            RouteList rl = new RouteList();
            rl.setWorkflowID(intWorkflowID);
            rl.setYWID(bizdao.getYWID());
            int intBizStatu = rl.queryStatus(conn);
            if (intBizStatu == 5) {
                throw new Exception("Route|Cancel|BizLogic|执行Route.Cancel()方法时发生业务逻辑异常，此业务流程已完成，不能被中止。");
            }
            if (intBizStatu == 3) {
                throw new Exception("Route|Cancel|BizLogic|执行Route.Cancel()方法时发生业务逻辑异常，此业务流程已中止，不能被中止。");
            }
            //取出原有业务数据项数据快照
            HashMap hmHistory = new HashMap();
            if (pi.getSaveHistory()) {
                hmHistory = bizdao.dataSnapShot(conn);
            }
            if (bizdao.update(conn) < 1) { //更新业务数据，修改人： 2004-11-15
                throw new Exception("Route|Cancel|DbFail|执行Route.Cancel()方法时发生数据库操作失败异常，用户工作流业务数据操作对象修改记录失败。");
            }
            rl.setID(intRouteListID);
            rl.setStatus(RouteListStatus.CANCEL); //设置业务状态为中止
            rl.updateStatu(conn);
            rl.buildself();
            this.saveActionAndHistory(rl, hmHistory, bizdao, conn);
            return rl;
        }
        catch (Exception e) {
            throw e;
        }
    }

    /**
     * 程序：
     * 说明：转向下一节点
     * @param strNextNode 下一节点字串数组
     * @param rl RouteList
     * @param conn Connection
     * @throws java.lang.Exception
     */
    public void transitionNextNode(String[] strNextNode, RouteList rl,
                                   Connection conn) throws Exception {

        boolean bolSatisfyAll = true; //是否全部满足所有下一节点进入条件
        boolean bolNoSatisfyAll = true; //是否全部不满足所有下一节点进入条件
        String strNextNodea = "|"; //下一节点信息
        String strForwarded = rl.getForwarded() == null || rl.getForwarded().equals("") ? "|" : rl.getForwarded(); //已发节点
        for (int i = 0; i < strNextNode.length; i++) {


            if (strNextNode[i] == null || strNextNode[i].equals("")) {
                continue;
            }



            Node nextNode = Node.searchNodeObject(intWorkflowID, Integer.parseInt(strNextNode[i]));

            if (conditionEstimate(nextNode, bizdao, 0, conn)) { //满足条件
                strForwarded += strNextNode[i] + "|";
                bolNoSatisfyAll = false;
                //新建或是修改一下节点路由信息
                //修改的目的是为了确保同一业务流在同一节点只会出现一条记录。
                RouteList rlexsit = new RouteList();
                rlexsit.setYWID(rl.getYWID());
                rlexsit.setWorkflowID(rl.getWorkflowID());
                rlexsit.setCurrentNode(Integer.parseInt(strNextNode[i])); //将下一节点作为当前节点
                List lsexsit = rlexsit.queryNoFinish(conn); //注：只查找状态为：非4、finish的记录

                if (lsexsit != null && lsexsit.size() > 0) { //如果存在，则将状态从：2、wait 改为 1、Active
                    RouteList rlexsit_temp = (RouteList) lsexsit.get(0);
                    rlexsit_temp.setStatus(RouteListStatus.ACTIVE);
                    rlexsit_temp.update(conn);

                    // 发送短信
                    ClientApplications.sendMessage(rlexsit_temp, conn);
                }
                else { //如果不存在，则新建一条路由记录
                    insertRouteList(rl, Integer.parseInt(strNextNode[i]), conn);
                    // 发送短信
                    ClientApplications.sendMessage(rl, conn);
                }

            }
            else { //不满足条件
                bolSatisfyAll = false; //不能满足所有下一节点进入条件
                strNextNodea += strNextNode[i] + "|";
            }
        }
        System.out.println("是否满足所有节点进入条件:" + bolSatisfyAll + ",是否不满足所有节点进入条件:" +
                bolNoSatisfyAll);
        //修改此路由记录的NextNode，Forwarded，Statu值
        if (!bolSatisfyAll && bolNoSatisfyAll) { //下一节点进入条件全部不能满足
            conn.rollback();
            throw new Exception("Route|TransitionNextNode|Condition_In|执行Route.TransitionNextNode()方法时发生节点进入条件不满足异常，此业务流程数据不满足进入下一节点的条件，不能被转发。");
        }
        else if (bolSatisfyAll && !bolNoSatisfyAll) { //如果全部能转向下一节点，则本路由记录的状态将改为:4、Finish
            rl.setStatus(RouteListStatus.FINISH);
        }
        else if (!bolSatisfyAll && !bolNoSatisfyAll) { //如果只有部份能转向，则本路由记录的状态将被改为：2、Wait
            rl.setStatus(RouteListStatus.WAIT);
        }

        rl.setNextNode(strNextNodea);
        rl.setForwarded(strForwarded);
        if (intServiceType == ServiceType.AUTO_FORWARD) { //自动转发，不记录未转发成功的下一节点
            rl.setStatus(RouteListStatus.FINISH);
            rl.setNextNode("|");
        }

        rl.update(conn);
    }

    /**
     * 程序：
     * 说明：新建业务流
     * @return 1:成功，非1:失败
     * @throws java.lang.Exception
     */
    protected int createBiz(IBizDao bizdao, RouteList rl, Node node, Connection conn) throws Exception {
        try {
            if (node.getType() != 0) {
                throw new Exception("Route|CreateBiz|BizLogic|执行Route.CreateBiz()方法时发生业务逻辑异常，此节点不是业务流的开始节点，不能创建新的业务流。");
            }
            if (bizdao.insert(conn) < 1) {
                throw new Exception("Route|CreateBiz|BizLogic|执行Route.CreateBiz()方法时发生数据库操作失败异常，用户工作流业务数据操作对象新增记录失败。");
            }

            rl.setPreID(0); //在新增业务路由时，上一路由编号为0
            rl.setPreNode(0); //在新增业务路由时，上一节点编号为0
            rl.setCurrentNode(intCurrentNode); //当前节点编号
            rl.setID(rl.getMaxID(conn)); //得到编号
            rl.setStatus(RouteListStatus.ACTIVE); //状态为：1、Active

            if (rl.insert(conn) < 1) {
                throw new Exception("Route|CreateBiz|BizLogic|执行Route.CreateBiz()方法时发生数据库操作失败异常，新增业务流转路由记录失败。");
            }
            return 1;
        }
        catch (Exception E) {
            conn.rollback();
            throw E;
        }

    }

    /**
     * 程序：
     * 说明：判断当前业务路由记录是否符合节点条件
     * @param inOrOut 0:in ,1:out
     * @return 1：符合，非1：不符合
     * @throws java.lang.Exception
     */
    public boolean conditionEstimate(Node node, IBizDao bizDao, int inOrOut, Connection conn) throws Exception {

        String strConditionOut = inOrOut == 0 ? node.getCondition_In() :  node.getCondition_Out();

        return bizDao.satisfyForwardCondition(strConditionOut, conn);
    }

    /**
     * 程序：
     * 说明：保存动作记录以及历史数据
     * @param rl
     * @param hmHistory
     * @param bizdao
     * @param conn
     * @return
     * @throws java.lang.Exception
     */
    public int saveActionAndHistory(RouteList rl, HashMap hmHistory, IBizDao bizdao, Connection conn) throws Exception {

        if (pi.getSaveAction()) { //需要保存动作记录
            Action action = new Action();
            action.setYWID(rl.getYWID());
            action.setWorkflowID(rl.getWorkflowID());
            action.setID(action.getMaxID(conn));
            action.setRouteID(rl.getID());
            action.setParticipant(strParticipant);
            action.setActionTime(Tools.getTime());
            action.setActionType(intServiceType);
            action.setPreNode(rl.getPreNode());
            action.setNextNode(rl.getNextNode());
            action.insert(conn);
            if (pi.getSaveHistory()) {
                //写历史数据
                HashMap hmnew = bizdao.dataSnapShot(conn);
                Iterator itnew = hmnew.keySet().iterator();
                while (itnew.hasNext()) {
                    String strOldVal = new String();
                    String strNewVal = new String();
                    String keyname = (String) itnew.next();
                    if (hmHistory.containsKey(keyname)) { //旧值中存在
                        strOldVal = (String) hmHistory.get(keyname);
                    }
                    strNewVal = (String) hmnew.get(keyname);
                    strOldVal = strOldVal == null ? "" : strOldVal;
                    strNewVal = strNewVal == null ? "" : strNewVal;
                    if (!strOldVal.equals(strNewVal)) { //新旧值不一致
                        HistoryData hd = new HistoryData();
                        hd.setYWID(rl.getYWID());
                        hd.setWorkflowID(rl.getWorkflowID());
                        hd.setActionID(action.getID());
                        hd.setName(keyname);
                        hd.setOldVal(strOldVal);
                        hd.setNewVal(strNewVal);
                        hd.insert(conn);
                    }
                }
            }
        }
        return 1;
    }

    /**
     * 程序：
     * 说明：新建路由记录信息
     * @param prerl 上一路由记录
     * @param intCurrentNode 当前节点
     * @return
     */
    public int insertRouteList(RouteList prerl, int intCurrentNode,
                               Connection conn) throws Exception {
        RouteList rl = new RouteList();
        rl.setID(rl.getMaxID(conn)); //得到编号
        rl.setWorkflowID(prerl.getWorkflowID()); //从上一路由记录中取中工作流编号
        rl.setYWID(prerl.getYWID()); //从上一路由记录中取出业务编号
        rl.setPreID(prerl.getID()); //将上一路由记录的编号做为此记录的上一编号
        rl.setPreNode(prerl.getCurrentNode()); //将上一路由记录的当前节点做为此记录的上一节点
        rl.setCurrentNode(intCurrentNode); //当前节点
        rl.setStatus(RouteListStatus.ACTIVE); //设置状态为1：Active
        return rl.insert(conn);
    }

}
