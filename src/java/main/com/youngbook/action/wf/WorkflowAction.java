package com.youngbook.action.wf;

import com.youngbook.action.BaseAction;
import com.youngbook.common.ReturnObject;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.wf.admin.Action;
import com.youngbook.common.wf.admin.RouteList;
import com.youngbook.common.wf.clientapp.ClientApplications;
import com.youngbook.common.wf.common.RouteListStatus;
import com.youngbook.common.wf.common.ServiceType;
import com.youngbook.common.wf.common.Tools;
import com.youngbook.common.wf.common.WorkflowReturnObject;
import com.youngbook.common.wf.engines.IBizDao;
import com.youngbook.common.wf.engines.Route;
import com.youngbook.common.wf.processdefine.ProcessInfo;
import com.youngbook.dao.MySQLDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;


public class WorkflowAction extends BaseAction {
    protected String strTargetURL; //处理后转向的路径
    protected String strServiceType; //需要进行服务的类型
    protected int intServiceType;

    private WorkflowReturnObject workflowReturnObject = new WorkflowReturnObject();
    /**
     * 对于strServiceType，HTML页面上的ServiceType字串的说明：
     * 1、SaveOnly：只保存，不转发业务流程（注：不是工作流，是业务流，保留，尚未实现）
     * 2、SaveForward：保存并转发业务流程数据项
     * 3、AutoForward：自动转发至可达节点 自动转发后，不满足进入条件的节点，在业务数据发生改变时，不会被再次评估转发
     * 4、Cancel：中止业务流
     * 5、Over：完成业务（只有处于节点类型为：结束节点时才有效）
     * 6、Reject：退回业务(只有在串行业务处理时能确保正确被退回，
     *                   如是并行业务，如：从节点1、节点2转发于节点3，
     *                   节点3使用退回业务时，不能完全退回至节点1和节点2，
     *                   只会退至第一个转发至节点3的源节点)退回到
     */
    private int intWorkflowID = Integer.MAX_VALUE; //工作流编号
    private String strBizDaoName; //用户相关业务的BizDao类名称
    private int intCurrentNode = Integer.MAX_VALUE; //当前节点编号
    private int intRouteListID = 0; //当前业务路由记录编号，初始化时编号为0，表示业务第一次开始流转
    private ProcessInfo pi; //工作流对象
    private String strParticipant = new String(); //参与者

    public String service() throws Exception {
        HttpServletRequest request = getRequest();

        try {

            Connection conn =  getConnection();
            strTargetURL = request.getParameter("TargetURL");
            strServiceType = request.getParameter("ServiceType");
            strBizDaoName = request.getParameter("BizDaoName");
            strParticipant = Tools.toUTF8(request.getParameter("Participant"));

            if (strTargetURL == null || strTargetURL.equals("")) {
                throw new Exception("未指定完成处理后的转向URL，表单项名称为：TargetURL。");
            }

            if (strServiceType == null || strServiceType.equals("")) {
                throw new Exception("未指定需要服务的类型，表单项名称为：ServiceType。");
            }
            if (strBizDaoName == null || strBizDaoName.equals("")) {
                throw new Exception("未指明用户工作流业务数据操作对象的类名，表单项名称为：BizDaoName。");
            }
            if (request.getParameter("CurrentNode") == null || request.getParameter("CurrentNode").equals("")) {
                throw new Exception("未指明工作流当前节点编号，表单项名称为：CurrentNode。");
            }

            if (request.getParameter("WorkflowID") == null || request.getParameter("WorkflowID").equals("")) {
                throw new Exception("未指明工作流编号，表单项名称为：WorkflowID。");
            }

            intCurrentNode = Integer.parseInt(request.getParameter("CurrentNode"));

            intWorkflowID = Integer.parseInt(request.getParameter("WorkflowID"));
            pi = new ProcessInfo();
            pi.setID(intWorkflowID);


            List lspi = pi.searchObject();
            if (lspi == null || lspi.size() < 1) {
                throw new Exception("无法找到编号为：" + intWorkflowID + "的工作流。");
            }
            pi = (ProcessInfo) lspi.get(0);


            if (!StringUtils.isEmpty(request.getParameter("RouteListID"))) {
                //取得当前路由编号
                intRouteListID = Integer.parseInt(request.getParameter("RouteListID"));
            }


            // 判断是否有控制的权限
            String userId = Config.getLoginUserInSession(request).getId();
            boolean canOperable = ClientApplications.checkOperableNodeByUserId(intWorkflowID, userId, intCurrentNode);
            if (!canOperable) {
//                getResult().setCode(ReturnObject.CODE_WF_EXCEPTION);
//                getResult().setMessage("您没有权限操作此流程");
                throw new Exception("用户没有权限操作此节点");
            }

            Class cBizDao = Class.forName(strBizDaoName);
            IBizDao bizDao = (IBizDao) cBizDao.newInstance();

            String strJsonPrefix = request.getParameter("JsonPrefix");
            bizDao = HttpUtils.getInstanceFromRequest(request, strJsonPrefix, bizDao.getClass());

            //服务类型为：保存并转发
            if (strServiceType.toUpperCase().equals(ServiceType.SAVE_FORWARD_STRING)) {
                String strNextNode[] = request.getParameterValues("NextNode");
                saveForward(bizDao, strNextNode, workflowReturnObject, conn);
            }
            //服务类型为：中止
            else if (strServiceType.toUpperCase().equals(ServiceType.CANCEL_STRING)) {
                cancel(bizDao, workflowReturnObject, conn);
            }
            //服务类型为：结束业务
            else if (strServiceType.toUpperCase().equals(ServiceType.OVER_STRING)) {
                over(bizDao, workflowReturnObject, conn);
            }
            //服务类型为：自动转发
            else if (strServiceType.toUpperCase().equals(ServiceType.AUTO_FORWARD_STRING)){
                autoForward(bizDao, workflowReturnObject, conn);
            }
            //服务类型：退回
            else if (strServiceType.toUpperCase().equals(ServiceType.REJECT_STRING)){
                reject(bizDao, workflowReturnObject, conn);
            }
            //服务类型：只保存
            else if (strServiceType.toUpperCase().equals(ServiceType.SAVE_ONLY_STRING)) {
                saveOnly(bizDao, workflowReturnObject, conn);
            }
            else {
                throw new ServletException("未找到适合的服务类型时行处理，表单项名称为：ServiceType");
            }

            getResult().setReturnValue(1);
            request.setAttribute("Result","1");
        }
        catch (Exception e) {
            getResult().setCode(ReturnObject.CODE_WF_DATA_EXCEPTION);
            getResult().setMessage(e.getMessage());
            e.printStackTrace();
            request.setAttribute("Result", "0");
            request.setAttribute("Exception", e);
        }

        return SUCCESS;
    }
    /**
     * 程序：
     * 说明：退回功能
     * 注意：只有在串行业务处理时能确保正确被退回，
     *       如是并行业务，如：从节点1、节点2转发于节点3，
     *       节点3使用退回业务时，不能完全退回至节点1和节点2，
     *       只会退至第一个转发至节点3的源节点)
     * @return int 1成功，非1失败
     * @throws Exception
     */
    public int reject(IBizDao bizDao, WorkflowReturnObject workflowReturnObject, Connection conn) throws Exception {

        Route route=new Route();
        route.setBizDao(bizDao);
        route.setParticipant(strParticipant);
        route.setWorkflowID(intWorkflowID);
        route.setpi(pi);
        route.setServiceType(ServiceType.REJECT);
        route.setCurrentNode(intCurrentNode);
        route.setRouteListID(intRouteListID);


        RouteList rl = route.reject(conn);
        workflowReturnObject.setRouteList(rl);

        return 1;
    }

    /**
     * 程序：
     * 说明：保存并转发业务流
     * @return 1成功，非1，失败
     * @throws java.lang.Exception
     */
    public int saveForward(IBizDao bizDao, String[] strNextNode,
                           WorkflowReturnObject workflowReturnObject, Connection conn) throws Exception {
        if (strNextNode == null || strNextNode.length == 0) {

            getResult().setCode(ReturnObject.CODE_WF_EXCEPTION);
            getResult().setMessage("未指定需要转发的目的地，本业务流程不能被转发！");
            throw new Exception("未指定需要转发的目标节点，本业务流程不能被转发");
        }

        // 创建YWDI
        if (StringUtils.isEmpty(bizDao.getYWID())) {
            bizDao.setYWID(IdUtils.getUUID32());
        }

        Route route = new Route();

        route.setBizDao(bizDao);
        route.setNextNode(strNextNode);
        route.setParticipant(strParticipant);
        route.setWorkflowID(intWorkflowID);
        route.setpi(pi);
        route.setServiceType(ServiceType.SAVE_FORWARD);
        route.setCurrentNode(intCurrentNode);
        route.setRouteListID(intRouteListID);

        RouteList routeList = route.saveForward(conn);
        workflowReturnObject.setRouteList(routeList);

        //执行工作流结束后的业务事件
        Action action = new Action();
        action.setParticipant(strParticipant);
        bizDao.afterOver(routeList, action, conn);

        return 1;
    }

    /**
     * 程序：李扬
     * 时间：2005-1-7
     * 说明：保存功能，只保存业务数据和记下操作记录，不进行流转
     * @return int
     * @throws Exception
     */
    public int saveOnly(IBizDao bizDao, WorkflowReturnObject workflowReturnObject,Connection conn) throws Exception {
        intServiceType = 1;

        Route route = new Route();

        route.setBizDao(bizDao);
        route.setParticipant(strParticipant);
        route.setWorkflowID(intWorkflowID);
        route.setpi(pi);
        route.setCurrentNode(intCurrentNode);
        route.setServiceType(ServiceType.SAVE_ONLY);
        route.setRouteListID(intRouteListID);
        RouteList rl = route.saveOnly(conn);
        workflowReturnObject.setRouteList(rl);

        return 1;
    }

    /**
     * 程序：
     * 说明：说明：自动转发功能 自动转发至可达节点，自动转发后，不满足进入条件的节点，在业务数据发生改变时，不会被再次评估转发
     * @return int
     * @throws Exception
     */
    public int autoForward(IBizDao bizDao, WorkflowReturnObject workflowReturnObject, Connection conn) throws Exception {
        Route route = new Route();
        route.setBizDao(bizDao);
        route.setParticipant(strParticipant);
        route.setWorkflowID(intWorkflowID);
        route.setpi(pi);
        route.setServiceType(ServiceType.AUTO_FORWARD);
        route.setCurrentNode(intCurrentNode);
        route.setRouteListID(intRouteListID);
        RouteList routeList =  route.autoForward(conn, Config.getSystemVariable("AutomForward.Transition.Attribute.Dw"));
        workflowReturnObject.setRouteList(routeList);

        //执行工作流结束后的业务事件
        Action action = new Action();
        action.setParticipant(strParticipant);
        bizDao.afterOver(routeList, action, conn);

        return 1;
    }
    /**
     * 程序：李扬
     * 时间：2004-11-4
     * 说明：中止业务流
     * @throws Exception
     * @return 1成功，非1，失败
     */
    public int cancel(IBizDao bizDao, WorkflowReturnObject workflowReturnObject, Connection conn) throws Exception {

        Route route = new Route();
        route.setBizDao(bizDao);
        route.setParticipant(strParticipant);
        route.setWorkflowID(intWorkflowID);
        route.setpi(pi);
        route.setServiceType(ServiceType.CANCEL);
        route.setCurrentNode(intCurrentNode);
        route.setRouteListID(intRouteListID);

        RouteList rl = route.cancel(conn);
        workflowReturnObject.setRouteList(rl);

        //执行工作流结束后的业务事件
        Action action = new Action();
        rl.setStatus(RouteListStatus.CANCEL);
        action.setParticipant(strParticipant);
        bizDao.afterOver(rl, action, conn);

        return 1;
    }


    /**
     * 程序：李扬
     * 时间：2004-11-4
     * 说明：完成业务流
     * @throws Exception
     * @return 1成功，非1，失败
     */
    public int over(IBizDao bizDao, WorkflowReturnObject workflowReturnObject, Connection conn) throws Exception {

        Route route = new Route();
        route.setBizDao(bizDao);
        route.setParticipant(strParticipant);
        route.setWorkflowID(intWorkflowID);
        route.setpi(pi);
        route.setServiceType(ServiceType.OVER);
        route.setCurrentNode(intCurrentNode);
        route.setRouteListID(intRouteListID);
        bizDao.update(conn);

        RouteList rl = route.over(conn);
        workflowReturnObject.setRouteList(rl);

        //执行工作流结束后的业务事件
        Action action = new Action();
        rl.setStatus(RouteListStatus.OVER);
        action.setParticipant(strParticipant);
        bizDao.afterOver(rl, action, conn);

        return 1;
    }


    public WorkflowReturnObject getWorkflowReturnObject() {
        return workflowReturnObject;
    }

    public void setWorkflowReturnObject(WorkflowReturnObject workflowReturnObject) {
        this.workflowReturnObject = workflowReturnObject;
    }

}
