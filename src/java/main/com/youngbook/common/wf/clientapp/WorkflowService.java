package com.youngbook.common.wf.clientapp;

import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.wf.engines.*;
import com.youngbook.common.wf.common.*;
import com.youngbook.common.wf.processdefine.*;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.Connection;
import java.util.*;


/**
 * <p>Title: </p>
 * <p>Description: 工作流服务Servlet</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class WorkflowService extends HttpServlet {

    protected String strTargetURL; //处理后转向的路径
    protected String strServiceType; //需要进行服务的类型

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
     *                   只会退至第一个转发至节点3的源节点)
     */
    private int intWorkflowID = Integer.MAX_VALUE; //工作流编号
    private String strBizDaoName; //用户相关业务的BizDao类名称
    private int intCurrentNode = Integer.MAX_VALUE; //当前节点编号
    private int intRouteListID = 0; //当前业务路由记录编号，初始化时编号为0，表示业务第一次开始流转
    private ProcessInfo pi; //工作流对象
    private String strParticipant = new String(); //参与者


    public WorkflowService() {
    }

    /**
     * 程序：
     * 说明：Servlet init
     * @param Config
     * @throws ServletException
     */
    public void init(ServletConfig Config) throws ServletException {
        try {
            super.init(Config);
        }
        catch (Exception E) {
        }
    }

    /**
     * 程序：
     * 说明：Servlet Service
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void service(HttpServletRequest request, HttpServletResponse response, Connection conn) throws
            ServletException, IOException {
        try {
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
            if (request.getParameter("CurrentNode") == null ||
                    request.getParameter("CurrentNode").equals("")) {
                throw new Exception("未指明工作流当前节点编号，表单项名称为：CurrentNode。");
            }
            else {
                intCurrentNode = Integer.parseInt(request.getParameter("CurrentNode"));
            }
            if (request.getParameter("WorkflowID") == null ||
                    request.getParameter("WorkflowID").equals("")) {
                throw new Exception("未指明工作流编号，表单项名称为：WorkflowID。");
            }
            else { //读出工作流对象
                intWorkflowID = Integer.parseInt(request.getParameter("WorkflowID"));
                pi = new ProcessInfo();
                pi.setID(intWorkflowID);
                List lspi = pi.searchObject();
                if (lspi == null || lspi.size() < 1) {
                    throw new Exception("无法找到编号为：" + intWorkflowID + "的工作流。");
                }
                pi = (ProcessInfo) lspi.get(0);
            }
            if (! (request.getParameter("RouteListID") == null ||
                    request.getParameter("RouteListID").equals(""))) { //取得当前路由编号
                intRouteListID = Integer.parseInt(request.getParameter("RouteListID"));
            }
            //服务类型为：保存并转发
            if (strServiceType.toUpperCase().equals("SAVEFORWARD")) {
                saveForward(request, conn);
                request.setAttribute("Result", "1");
            }//服务类型为：中止
            else if (strServiceType.toUpperCase().equals("CANCEL")) {
                cancel(request, conn);
                request.setAttribute("Result", "1");
            }
            //服务类型为：结束业务
            else if (strServiceType.toUpperCase().equals("OVER")) {
                over(request, conn);
                request.setAttribute("Result", "1");
            }
            //服务类型为：自动转发
            else if (strServiceType.toUpperCase().equals("AUTOFORWARD")){
                autoForward(request);
                request.setAttribute("Result","1");
            }
            //服务类型：退回
            else if (strServiceType.toUpperCase().equals("REJECT")){
                reject(request, conn);
                request.setAttribute("Result","1");
            }
            //服务类型：只保存
            else if (strServiceType.toUpperCase().equals("SAVEONLY")) {
                this.saveOnly(request, conn);
                request.setAttribute("Result","1");
            }
            else {
                throw new ServletException("未找到适合的服务类型时行处理，表单项名称为：ServiceType");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("Result", "0");
            request.setAttribute("Exception", e);
        }
        finally {
            forward(strTargetURL, request, response);
        }
    }
    /**
     * 程序：
     * 说明：退回功能
     * 注意：只有在串行业务处理时能确保正确被退回，
     *       如是并行业务，如：从节点1、节点2转发于节点3，
     *       节点3使用退回业务时，不能完全退回至节点1和节点2，
     *       只会退至第一个转发至节点3的源节点)
     * @param request HttpServletRequest
     * @return int 1成功，非1失败
     * @throws Exception
     */
    public int reject(HttpServletRequest request, Connection conn) throws Exception {

        Class cBizDao = Class.forName(strBizDaoName);
        IBizDao bizdao = (IBizDao)cBizDao.newInstance();
        bizdao = HttpUtils.getInstanceFromRequest(request, "", bizdao.getClass());
        Route route = new Route();
        route.setBizDao(bizdao);
        route.setParticipant(strParticipant);
        route.setWorkflowID(intWorkflowID);
        route.setpi(pi);
        route.setServiceType(ServiceType.REJECT);
        route.setCurrentNode(intCurrentNode);
        route.setRouteListID(intRouteListID);

        request.setAttribute("RouteList",route.reject(conn));
        return 1;
    }

    /**
     * 程序：
     * 说明：保存并转发业务流
     * @param request
     * @return 1成功，非1，失败
     * @throws java.lang.Exception
     */
    public int saveForward(HttpServletRequest request, Connection conn) throws Exception {
        String strNextNode[] = request.getParameterValues("NextNode");
        if (strNextNode == null || strNextNode.length == 0) {
            throw new Exception("未指定需要转发的目标节点，本业务流程不能被转发");
        }

        Class cBizDao = Class.forName(strBizDaoName);
        IBizDao bizdao = (IBizDao) cBizDao.newInstance();

        bizdao = HttpUtils.getInstanceFromRequest(request, "", bizdao.getClass());
        Route route = new Route();

        route.setBizDao(bizdao);
        route.setNextNode(strNextNode);
        route.setParticipant(strParticipant);
        route.setWorkflowID(intWorkflowID);
        route.setpi(pi);
        route.setServiceType(ServiceType.SAVE_FORWARD);
        route.setCurrentNode(intCurrentNode);
        route.setRouteListID(intRouteListID);
        request.setAttribute("RouteList", route.saveForward(conn));
        return 1;
    }

    /**
     * 程序：李扬
     * 时间：2005-1-7
     * 说明：保存功能，只保存业务数据和记下操作记录，不进行流转
     * @param request HttpServletRequest
     * @return int
     * @throws Exception
     */
    public int saveOnly(HttpServletRequest request, Connection conn) throws Exception {

        Class cBizDao = Class.forName(strBizDaoName);
        IBizDao bizdao = (IBizDao)cBizDao.newInstance();

        bizdao = HttpUtils.getInstanceFromRequest(request, "", bizdao.getClass());
        Route route = new Route();

        route.setBizDao(bizdao);
        route.setParticipant(strParticipant);
        route.setWorkflowID(intWorkflowID);
        route.setpi(pi);
        route.setCurrentNode(intCurrentNode);
        route.setServiceType(ServiceType.SAVE_ONLY);
        route.setRouteListID(intRouteListID);
        request.setAttribute("RouteList", route.saveOnly(conn));
        return 1;
    }

    /**
     * 程序：
     * 说明：说明：自动转发功能 自动转发至可达节点，自动转发后，不满足进入条件的节点，在业务数据发生改变时，不会被再次评估转发
     * @param request HttpServletRequest
     * @return int
     * @throws Exception
     */
    public int autoForward(HttpServletRequest request) throws Exception {
        Class cBizDao = Class.forName(strBizDaoName);
        IBizDao bizdao = (IBizDao) cBizDao.newInstance();
        bizdao = HttpUtils.getInstanceFromRequest(request, "", bizdao.getClass());
        Route route = new Route();
        route.setBizDao(bizdao);
        route.setParticipant(strParticipant);
        route.setWorkflowID(intWorkflowID);
        route.setpi(pi);
        route.setServiceType(ServiceType.AUTO_FORWARD);
        route.setCurrentNode(intCurrentNode);
        route.setRouteListID(intRouteListID);
        request.setAttribute("RouteList", route.autoForward());
        return 1;

    }
    /**
     * 程序：李扬
     * 时间：2004-11-4
     * 说明：中止业务流
     * @param request HttpServletRequest
     * @throws Exception
     * @return 1成功，非1，失败
     */
    public int cancel(HttpServletRequest request, Connection conn) throws Exception {
        //状态：4 中止
        Class cBizDao = Class.forName(strBizDaoName);
        IBizDao bizdao = (IBizDao)cBizDao.newInstance();

        bizdao = HttpUtils.getInstanceFromRequest(request, "", bizdao.getClass());
        Route route = new Route();
        route.setBizDao(bizdao);
        route.setParticipant(strParticipant);
        route.setWorkflowID(intWorkflowID);
        route.setpi(pi);
        route.setServiceType(ServiceType.CANCEL);
        route.setCurrentNode(intCurrentNode);
        route.setRouteListID(intRouteListID);
        request.setAttribute("RouteList", route.cancel(conn));
        return 1;
    }


    /**
     * 程序：李扬
     * 时间：2004-11-4
     * 说明：完成业务流
     * @param request HttpServletRequest
     * @throws Exception
     * @return 1成功，非1，失败
     */
    public int over(HttpServletRequest request, Connection conn) throws Exception {
        //状态：5 完成
        Class cBizDao = Class.forName(strBizDaoName);
        IBizDao bizdao = (IBizDao)cBizDao.newInstance();

        bizdao = HttpUtils.getInstanceFromRequest(request, "", bizdao.getClass());
        Route route = new Route();
        route.setBizDao(bizdao);
        route.setParticipant(strParticipant);
        route.setWorkflowID(intWorkflowID);
        route.setpi(pi);
        route.setServiceType(ServiceType.OVER);
        route.setCurrentNode(intCurrentNode);
        route.setRouteListID(intRouteListID);
        request.setAttribute("RouteList", route.over(conn));
        return 1;
    }

    /**
     * 程序：
     * 说明：转发
     * @param strURL
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void forward(String strURL, HttpServletRequest request,
                         HttpServletResponse response) throws ServletException,
            IOException {
        getServletContext().getRequestDispatcher(strURL).forward(request, response);
    }
}
