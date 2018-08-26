package com.youngbook.service.sale;

import com.youngbook.action.customer.CustomerPersonalAction;
import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.customer.CustomerMoneyPO;
import com.youngbook.entity.po.sale.InvestmentParticipantPO;
import com.youngbook.entity.po.sale.InvestmentPlanPO;
import com.youngbook.entity.vo.Sale.InvestmentParticipantVO;
import com.youngbook.entity.vo.Sale.InvestmentPlanVO;
import com.youngbook.entity.vo.production.OrderVO;
import com.youngbook.entity.wvo.customer.CustomerPersonalWVO;
import com.youngbook.entity.wvo.sale.InvestmentPlanWVO;
import com.youngbook.service.BaseService;
import com.youngbook.service.customer.CustomerMoneyService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-10-14
 * Time: 下午2:02
 * To change this template use File | Settings | File Templates.
 */
public class InvestmentPlanService extends BaseService {
    public Pager list(InvestmentPlanVO investmentPlanVO, List<KVObject> conditions) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        //sql查询语句
        String sql = "SELECT ip.*,u.`name` creatorName,u.`name` checkerName,u.`name` publisherName,u.`name` operatorName" +
                " FROM sale_investmentplan ip,system_user u WHERE 1 = 1 AND ip.State = 0 AND u.state = 0 AND ip.OperatorId = u.Id";
        Pager pager = Pager.query(sql, investmentPlanVO, conditions, request, queryType);
        return pager;
    }

    public int insertOrUpdate(InvestmentPlanPO investmentPlan, UserPO user, Connection conn) throws Exception {
        int count = 0;
        // 新增
        if (investmentPlan.getId().equals("")) {
            investmentPlan.setSid(MySQLDao.getMaxSid("sale_investmentplan", conn));
            investmentPlan.setId(IdUtils.getUUID32());
            investmentPlan.setState(Config.STATE_CURRENT);
            investmentPlan.setOperatorId(user.getId());
            investmentPlan.setCreatorId(user.getId());
            investmentPlan.setCheckerId(user.getId());
            investmentPlan.setPublisherId(user.getId());
            investmentPlan.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(investmentPlan, conn);
        }
        // 更新
        else {
            InvestmentPlanPO temp = new InvestmentPlanPO();
            temp.setSid(investmentPlan.getSid());
            temp = MySQLDao.load(temp, InvestmentPlanPO.class);
            temp.setState(Config.STATE_UPDATE);
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                investmentPlan.setSid(MySQLDao.getMaxSid("sale_investmentplan", conn));
                investmentPlan.setState(Config.STATE_CURRENT);
                investmentPlan.setOperatorId(user.getId());
                investmentPlan.setOperateTime(TimeUtils.getNow());
                count = MySQLDao.insert(investmentPlan, conn);
            }
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    public int delete(InvestmentPlanPO investmentPlan, UserPO user, Connection conn) throws Exception {
        int count = 0;
        investmentPlan = MySQLDao.load(investmentPlan, InvestmentPlanPO.class);
        investmentPlan.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(investmentPlan, conn);
        if (count == 1) {
            investmentPlan.setSid(MySQLDao.getMaxSid("sale_investmentplan", conn));
            investmentPlan.setState(Config.STATE_DELETE);
            investmentPlan.setOperateTime(TimeUtils.getNow());
            investmentPlan.setOperatorId(user.getId());
            count = MySQLDao.insert(investmentPlan, conn);
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    /**
     * 网站：首页广告下的三个模块
     *
     * @param investmentPlanVO
     * @param conditions
     * @return
     * @throws Exception
     * @auther 邓超
     */
    public Pager list4Website(InvestmentPlanWVO investmentPlanVO, List<KVObject> conditions, HttpServletRequest request) throws Exception {
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        // SQL查询语句
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ip.*,u.`name` creatorName,u.`name` checkerName,u.`name` publisherName,u.`name` operatorName" +
                " FROM sale_investmentplan ip,system_user u " +
                " WHERE ip.state = 0 AND u.state = 0 AND ip.OperatorId = u.Id ");
        // 起投金额
        if (request.getParameter("investMoneyMin") != null && !request.getParameter("investMoneyMin").equals("")) {
            //没有最大起投金额
            if(request.getParameter("investMoneyMax")== null || request.getParameter("investMoneyMax").equals("")){

                sql.append(" AND ip.investMoneyMin < " + Database.encodeSQL(request.getParameter("investMoneyMin")));
            }else {
                sql.append(" AND ip.investMoneyMin >= " + Database.encodeSQL(request.getParameter("investMoneyMin")));
            }
        }
        if (request.getParameter("investMoneyMax") != null && !request.getParameter("investMoneyMax").equals("")) {
            /*，没有最小起投金额*/
            if(request.getParameter("investMoneyMin")== null || request.getParameter("investMoneyMin").equals("")){
                sql.append(" AND ip.investMoneyMax >= " +Database.encodeSQL(request.getParameter("investMoneyMax")));
            }else{

            sql.append(" AND ip.investMoneyMax <= " +Database.encodeSQL(request.getParameter("investMoneyMax")));
            }
        }
        // 投资期限
        if (request.getParameter("investTimeMin") != null && !request.getParameter("investTimeMin").equals("")) {
            sql.append(" AND ip.InvestTermView >= " + Integer.parseInt(request.getParameter("investTimeMin")));
        }
        if (request.getParameter("investTimeMax") != null && !request.getParameter("investTimeMax").equals("")) {
            sql.append(" AND ip.InvestTermView <= " +Integer.parseInt(request.getParameter("investTimeMax")));
        }
        // 收益率
        if (request.getParameter("returnRateMin") != null && !request.getParameter("returnRateMin").equals("")) {
            sql.append(" AND ip.returnRateMin >= " + Database.encodeSQL(request.getParameter("returnRateMin")));
        }
        if (request.getParameter("returnRateMax") != null && !request.getParameter("returnRateMax").equals("")) {
           /*没有最小收益率*/
            if(request.getParameter("returnRateMin") == null || request.getParameter("returnRateMin") .equals("")){
                sql.append(" AND ip.returnRateMax >= " + Database.encodeSQL(request.getParameter("returnRateMax")));
            }else{
            sql.append(" AND ip.returnRateMax <= " + Database.encodeSQL(request.getParameter("returnRateMax")));
            }
        }
        /*收益方式
        * */
        Pager pager = Pager.query(sql.toString(), investmentPlanVO, conditions, request, queryType);
        return pager;
    }

    /**
     * 获取投资计划详情
     *
     * @param investmentPlanWVO
     * @return
     * @throws Exception
     */
    public InvestmentPlanWVO showDetail4W(InvestmentPlanWVO investmentPlanWVO,Connection conn) throws Exception {
        String id = investmentPlanWVO.getId();
        InvestmentPlanWVO wvo = null;
        String sql = "select * from sale_investmentplan p where p.id = '" + Database.encodeSQL(investmentPlanWVO.getId() )+ "' and p.state = 0";
        List<InvestmentPlanWVO> list = MySQLDao.query(sql, InvestmentPlanWVO.class, null,conn);
        if (list.size() == 1) {
            wvo = list.get(0);
        }
        return wvo;
    }

    /**
     * 创建人：姚章鹏
     * 内容：查询交易密码是否存在
     */
    public CustomerPersonalWVO getTransactionPasswordWVO(String loginUserId) throws Exception{
        CustomerPersonalWVO wvo = null;
        String sql = "select * from crm_customerpersonal p where p.state = 0  and  id='"+Database.encodeSQL(loginUserId)+"'";
        List<CustomerPersonalWVO> list = MySQLDao.query(sql, CustomerPersonalWVO.class, null);
        if (list.size() == 1) {
            wvo = list.get(0);
        }
        return wvo;

    }

    /**
     * 创建人：刘雪冬
     * 内容：根据参与计划获取参与人
     */
    public Pager investmentParticipantList4Web(InvestmentPlanWVO investmentPlanWVO, List<KVObject> conditions, HttpServletRequest request) throws Exception {

        if (conditions == null) {
            conditions = new ArrayList<KVObject>();
        }
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("SELECT c.loginName, c.`Name` CustomerName, pp.JoinMoney, pp.JoinTime ");
        sbSQL.append("FROM sale_investmentparticipant pp , sale_investmentplan p ,crm_customerpersonal c ");
        sbSQL.append("WHERE pp.InvestmentId = p.Id ");
        sbSQL.append("AND pp.CustomerId = c.id ");
        sbSQL.append("AND pp.State=0 and p.State=0 and c.state=0 ");
        if (!StringUtils.isEmpty(investmentPlanWVO.getId())) {
            sbSQL.append("AND p.id = '" + Database.encodeSQL(investmentPlanWVO.getId()) + "' ");
        }
        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, "pp.JoinTime DESC "));
        Pager pager = Pager.query(sbSQL.toString(), new InvestmentParticipantVO(), conditions, request, queryType);
        return pager;
    }


    /**
     * 网站：通过客户ID查找投资情况
     *
     * @param investmentPlanWVO
     * @param conditions
     * @param customerId
     * @return
     * @throws Exception
     *
     * 修改人：姚章鹏
     * 内容：修改SQL语句  添加了,b.joinTime  获取参与时间
     * 时间：7/29/2015
     */
    public Pager loadByCustomerId4Web(InvestmentPlanWVO investmentPlanWVO, List<KVObject> conditions, String customerId) throws Exception {
        // 获取请求
        HttpServletRequest request = ServletActionContext.getRequest();
        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, "joinTime desc"));
        // 组装 SQL 语句
//        String sql = "select a.*, b.joinMoney,b.joinTime from sale_investmentplan a, sale_investmentparticipant b " +
//                "where a.id = b.investmentId " +
//                "and b.customerId = '" +Database.encodeSQL( customerId) + "' " +
//                "and a.state = 0 " +
//                "and b.state = 0";
        String sql ="select a.*, joinMoney,joinTime from sale_investmentplan a,("+
                "SELECT b.investmentId investmentId,SUM(b.JoinMoney) joinMoney,MAX(b.JoinTime) joinTime from sale_investmentparticipant b "+
                "where b.state = 0 and b.customerId = '" +Database.encodeSQL(customerId) + "' " +
                "GROUP BY b.InvestmentId ) b where a.id = b.investmentId and a.state = 0  ";
        //设置查询类型
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        Pager pager = Pager.query(sql, investmentPlanWVO, conditions, request, queryType);
        return pager;
    }
    /**
     * 添加
     *
     * 修改人：张舜清
     * 时间：7/27/2015
     * 内容增加web的投资加入时间
     */
    public int investmentParticipantSave(InvestmentParticipantPO inves,Connection conn, CustomerMoneyPO customerMoney) throws Exception {
        int count = 0;
        UserPO  user = new UserPO();
        user.setId(Config.getSystemConfig("web.default.operatorId"));
        inves.setSid(MySQLDao.getMaxSid("sale_investmentparticipant", conn));
        inves.setId(IdUtils.getUUID32());
        inves.setState(Config.STATE_CURRENT);
        inves.setOperatorId(Config.getSystemConfig("web.default.operatorId"));
        inves.setOperateTime(TimeUtils.getNow());
        inves.setJoinStatus("1403");//参与状态
        inves.setJoinType("1405");//参与类型
        // 增加加入时间-----张舜清
        inves.setJoinTime(TimeUtils.getNow());
        count = MySQLDao.insert(inves, conn);

        if (count == 1) {
            updateSystemStatisticsData(customerMoney,user,conn,inves.getJoinMoney());
        }else{
            throw new Exception("投资参与失败");
        }
        return count;
    }

    /**
     * 修改用户资金
     * @param customerMoney
     * @param user
     * @param money
     */
    public void updateSystemStatisticsData(CustomerMoneyPO customerMoney,UserPO user,Connection conn,Double money) throws Exception {

        CustomerMoneyService customerMoneyService = new CustomerMoneyService();

        Double frozenMoney = customerMoney.getFrozenMoney();//冻结金额
        double customerMoneyAvailableMoney = customerMoney.getAvailableMoney();//可用金额
        //修改数据
        customerMoney.setAvailableMoney(customerMoneyAvailableMoney - money);
        customerMoney.setFrozenMoney(frozenMoney + money);
        //更新数据
        int count = customerMoneyService.insertOrUpdate(customerMoney,user, conn);
        if(count <= 0){
            throw new Exception("修改用户资金失败");
        }
    }
}
