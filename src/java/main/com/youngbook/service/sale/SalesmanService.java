package com.youngbook.service.sale;

import com.youngbook.action.BaseAction;
import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.sale.ISalesmanDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.sale.LeaderboardPO;
import com.youngbook.entity.po.sale.SalesmanPO;
import com.youngbook.entity.po.system.UserPositionType;
import com.youngbook.entity.vo.Sale.SalesManVO;
import com.youngbook.entity.vo.system.MenuVO;
import com.youngbook.service.BaseService;
import com.youngbook.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-10-14
 * Time: 下午2:02
 * To change this template use File | Settings | File Templates.
 */
@Component("salesmanService")
public class SalesmanService extends BaseService {

    @Autowired
    ISalesmanDao salesmanDao;


    public Pager listPagerSalesmanGroup(SalesManVO salesManVO, int currentPage, int showRowCount, Connection conn) throws Exception {
        return salesmanDao.listPagerSalesmanGroup(salesManVO, currentPage, showRowCount, conn);
    }

    /**
     * 获取指定时间内的龙虎榜排名
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年5月25日
     *
     * @param startDate
     * @param stopDate
     * @param conn
     * @return
     * @throws Exception
     */
    public List getLeaderboards(String startDate, String stopDate, Connection conn) throws Exception {

        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("calcLeaderboard", "SalesmanServiceSQL", SalesmanService.class);
        dbSQL.addParameter4All("startDate", startDate).addParameter4All("stopDate", stopDate);
        dbSQL.initSQL();

        List<LeaderboardPO> list = MySQLDao.search(dbSQL, LeaderboardPO.class, conn);
        return list;

    }

    public Pager getSalesmanList(SalesManVO salesManVO, List<KVObject> conditions,HttpServletRequest request) throws Exception{

        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT");
        sbSQL.append("     u.`name` userName,");
        sbSQL.append("     u.gender,");
        sbSQL.append("     u.staffCode,");
        sbSQL.append("     u.mobile,");
        sbSQL.append("     u.idnumber,");
        sbSQL.append("     kvGender.V genderName,");
        sbSQL.append("     kvSalesLevel.V salesLevel,");
        sbSQL.append("     s.*");
        sbSQL.append(" FROM");
        sbSQL.append("     system_user u");
        sbSQL.append(" LEFT JOIN crm_saleman s ON S.UserId = u.id");
        sbSQL.append(" LEFT JOIN system_kv kvGender ON kvGender.K = u.gender and kvGender.GroupName='Sex'");
        sbSQL.append(" LEFT JOIN system_kv kvSalesLevel ON kvSalesLevel.K = s.Sales_levelId and kvSalesLevel.GroupName='sales_level'");
        sbSQL.append(" WHERE");
        sbSQL.append("     1 = 1");
        sbSQL.append(" AND u.state = 0");
        sbSQL.append(" AND u.PositionTypeId = '"+ UserPositionType.SaleMan+"'");
        sbSQL.append(" AND s.State = 0  ORDER BY u.name,u.staffCode asc");

        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        Pager pager = Pager.search(sbSQL.toString(), null, salesManVO,conditions,request,queryType);
        return pager;
    }



    public int deleteSaleMan(UserPO user,UserPO loginUser,Connection conn) throws Exception {
        int count = 0;
        SalesmanPO salesman = new SalesmanPO();
        salesman.setUserId(user.getId());
        salesman.setState(Config.STATE_CURRENT);
        salesman = MySQLDao.load(salesman,SalesmanPO.class,conn);
        salesman.setOperatorId(loginUser.getId());
        salesman.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(salesman,conn);
        if (count > 0){
            BaseAction baseAction = new BaseAction();
            salesman.setSid(MySQLDao.getMaxSid("crm_saleman"));
            salesman.setId(IdUtils.getUUID32());
            salesman.setState(Config.STATE_DELETE);
            salesman.setOperatorId(loginUser.getId());
            salesman.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(salesman,conn);
        }else {
            count = 0;
        }
        return count;
    }


    //获取第一个销售类别的K
    private String getLevelK() throws Exception{
        MenuVO menu= new MenuVO();
        String sql="select K id,V text from system_kv where GroupName='sales_level'";
        List<MenuVO> list= MySQLDao.query(sql, MenuVO.class, null);
        if(list!=null&&list.size()>0){
            menu=list.get(0);
        }
        return menu.getId();
    }

    /**
     * 更新销售员的等级
     * @param salesmanPO
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int update(SalesmanPO salesmanPO, UserPO user, Connection conn) throws Exception{
        int count=0;
        SalesmanPO temp = new SalesmanPO();
        temp.setId(salesmanPO.getId());
        temp.setState(Config.STATE_CURRENT);
        temp = MySQLDao.load(temp, SalesmanPO.class);
        temp.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(temp, conn);
        if (count == 1) {
            salesmanPO.setSid(MySQLDao.getMaxSid("crm_saleman", conn));
            salesmanPO.setState(Config.STATE_CURRENT);
            salesmanPO.setOperatorId(user.getId());
            salesmanPO.setOperateTime(TimeUtils.getNow());
            count = MySQLDao.insert(salesmanPO, conn);
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    public SalesmanPO insertSalesman(UserPO user, Connection conn) throws Exception {
        return salesmanDao.insertSalesman(user, conn);
    }
}
