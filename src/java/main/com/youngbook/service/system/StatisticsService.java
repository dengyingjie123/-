package com.youngbook.service.system;

import com.youngbook.common.Database;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.system.StatisticsPO;
import com.youngbook.entity.vo.system.StatisticsForWebVO;
import com.youngbook.entity.vo.system.StatisticsVO;
import com.youngbook.service.BaseService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;

/**
 * Created by Administrator on 2015/4/3.
 */
public class StatisticsService extends BaseService {

    @Autowired
    UserService userService;

    /**
     * 获取列表对象。
     *
     * @param statisticsVO 实体类对象
     * @return
     * @throws Exception
     */
    public Pager list(StatisticsVO statisticsVO) throws Exception {

        //获取请求
        HttpServletRequest request = ServletActionContext.getRequest();
        //创建查询类型对象
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        //组装SQL语句;
        StringBuffer sbSQL = new StringBuffer();

        sbSQL.append("SELECT SS.sid,SS.id,SS.state,SS.operatorId, SS.operateTime, SS.`Name`,");
        sbSQL.append("SS.Tag, SS.V, us.name operatorName  ");
        sbSQL.append("  FROM system_statistics SS,  system_user us");
        sbSQL.append(" where us.operatorId = SS.operatorId");
        sbSQL.append(" ANd us.state = 0");
        sbSQL.append(" ANd SS.state = 0");
        sbSQL.append(" order by SS.sid DESC");
        //查询数据 返回分页对象
        Pager pager = Pager.query(sbSQL.toString(), statisticsVO, null, request, queryType);
        return pager;

    }

    /**
     * 根据ID获取单独数据的
     *
     * @param id 参数ID
     * @return
     * @throws Exception
     */
    public StatisticsPO loadStatisticsPO(String id) throws Exception {
        //创建一个实体对象
        StatisticsPO po = new StatisticsPO();
        //设置ID
        po.setId(id);
        //设置数据状态为当前
        po.setState(Config.STATE_CURRENT);
        //获取对象数据
        po = MySQLDao.load(po, StatisticsPO.class);

        return po;
    }

    /**
     * 删除数据的逻辑操作类
     *
     * @param statistics 对于应得数据对象
     * @param user       操作员对象
     * @param conn       数据库链接
     * @return
     * @throws Exception
     */
    public int delete(StatisticsPO statistics, UserPO user, Connection conn) throws Exception {
        int count = 0;
        //将当前数据的状态修改为更新状态
        statistics.setState(Config.STATE_CURRENT);
        //获取数据
        statistics = MySQLDao.load(statistics, StatisticsPO.class);
        //设置数据状态
        statistics.setState(Config.STATE_UPDATE);
        //更新数据
        count = MySQLDao.update(statistics, conn);
        //重新插入一条被删除的数据
        if (count == 1) {
            statistics.setSid(MySQLDao.getMaxSid("System_Statistics", conn));
            statistics.setState(Config.STATE_DELETE);
            //获取系统时间
            statistics.setOperateTime(TimeUtils.getNow());
            //数组操作员ID
            statistics.setOperatorId(user.getId());

            //插入数据
            count = MySQLDao.insert(statistics, conn);
        }

        if (count != 1) {
            throw new Exception("删除失败");
        }

        return count;
    }

    /**
     * 添加修改数据操作
     *
     * @param statistics 对应的数据对象
     * @param conn       数据库链接
     * @return 返回影响行数
     * @throws Exception
     */
    public int insertOrUpdate(StatisticsPO statistics, Connection conn) throws Exception {
        int count = 0;
        // 新增
        if (StringUtils.isEmpty(statistics.getId())) {
            //根据数据总数设置SID
            statistics.setSid(MySQLDao.getMaxSid("System_Statistics", conn));
            //根据UUID设置ID
            statistics.setId(IdUtils.getUUID32());
            //设置当前数据的记录状态
            statistics.setState(Config.STATE_CURRENT);
            //设置操作员编号
            statistics.setOperatorId(Config.Admin);
            //设置操作时间
            statistics.setOperateTime(TimeUtils.getNow());
            //添加数据
            count = MySQLDao.insert(statistics, conn);
        }
        // 更新
        else {
            //创建一个新对象
            StatisticsPO temp = new StatisticsPO();
            //获取sID
            temp.setSid(statistics.getSid());
            //根据sid查询数据
            temp = MySQLDao.load(temp, StatisticsPO.class);
            //修改数据状态为更新状态
            temp.setState(Config.STATE_UPDATE);
            //更新数据
            count = MySQLDao.update(temp, conn);
            if (count == 1) {
                //设置sid
                statistics.setSid(MySQLDao.getMaxSid("System_Statistics", conn));
                //修改数据状态
                statistics.setState(Config.STATE_CURRENT);
                //设置操作员编号
                statistics.setOperatorId(Config.Admin);
                //添加操作时间
                statistics.setOperateTime(TimeUtils.getNow());
                //添加新数据
                count = MySQLDao.insert(statistics, conn);
            }
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    /**
     * 更新系统数据
     */
    public void updateSystemStatisticsData() throws Exception {
        Connection conn = Config.getConnection();

        try {
            //创建系统统计辅助对象
            StatisticsForWebVO statisticsForWebVO = new StatisticsForWebVO();

            //组装SQL语句，查询出投资人数
            String us_SQL = "select count(*) as us_Count from crm_customerpersonal where state=0";
            // 组装sql语句查询出目前所有客户的金额
            StringBuffer sqlDB = new StringBuffer();
            sqlDB.append(" SELECT ");
            sqlDB.append("     SUM(availableMoney) availableMoney, ");
            sqlDB.append("     SUM(frozenMoney) frozenMoney, ");
            sqlDB.append("     SUM(investedMoney) investedMoney, ");
            sqlDB.append("     SUM(totalProfitMoney) totalProfitMoney ");
            sqlDB.append(" FROM ");
            sqlDB.append("     crm_customermoney ");
            sqlDB.append(" WHERE ");
            sqlDB.append("     state = 0 ");
            int us_count = 0;
            List<StatisticsForWebVO> ls = MySQLDao.query(us_SQL, StatisticsForWebVO.class, null, conn);
            if (ls.size() > 0) {
                statisticsForWebVO = ls.get(0);
                us_count = statisticsForWebVO.getUs_Count();
            }
            ls = MySQLDao.query(sqlDB.toString(), StatisticsForWebVO.class, null, conn);
            if (ls.size() > 0) {
                statisticsForWebVO = ls.get(0);
            }
            //管理资金对金额进行操作
            double money = statisticsForWebVO.getAvailableMoney() + statisticsForWebVO.getFrozenMoney();

            //创建操作员
            UserPO user = userService.loadUserByUserId(Config.getSystemConfig("web.default.operatorId"),conn);

            //获取SID
            StatisticsPO statistics = new StatisticsPO();
            statistics.setTag("投资人数");
            //更新投资人数
            SetUpdateDate(statistics,user,String.valueOf(us_count),conn);

            //更新管理资金
            statistics.setTag("管理资金");
            SetUpdateDate(statistics,user,String.valueOf((int)money),conn);

            //更新累计金额
            statistics.setTag("累计盈利");
            SetUpdateDate(statistics,user,String.valueOf((int)statisticsForWebVO.getTotalProfitMoney()),conn);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }
    }


    /**
     * 更新指定数据
     * @param Statistics 初始数据
     * @param user 操作人
     * @param value  更改的值
     * @param conn 数据库链接
     * @return
     * @throws Exception
     */
    private int SetUpdateDate(StatisticsPO Statistics, UserPO user,String value,Connection conn ) throws Exception {
        Statistics.setState(Config.STATE_CURRENT);
        //获取数据库对应的数据对象
        Statistics = MySQLDao.load(Statistics, StatisticsPO.class);
        //设置对应的值
        Statistics.setV(value);
        //更新数据
        return   this.insertOrUpdate(Statistics, conn);
    }

    /**
     * web前台首页统计数据
     */
    public JSONObject listForWeb(Connection conn) throws Exception {
        HttpServletRequest request=ServletActionContext.getRequest();
        StatisticsPO statistics = new StatisticsPO();
        String sql = "SELECT Tag,V FROM system_statistics WHERE state=0";
        List<StatisticsPO> statisticsList = MySQLDao.query(sql, StatisticsPO.class, null, conn);

        //创建一个Json对象
        JSONObject jsonObject=new JSONObject();
        for(int i=0;i<statisticsList.size() && statisticsList.size()>0;i++){
            StatisticsPO sp=statisticsList.get(i);

            if(sp.getTag().equals("投资人数")){
                jsonObject.element("Investment",sp.getV());
            } if(sp.getTag().equals("管理资金")){
                jsonObject.element("Money",sp.getV());
            } if(sp.getTag().equals("累计盈利")){
                jsonObject.element("Profit",sp.getV());
            }
        }
        return jsonObject;
    }
}
