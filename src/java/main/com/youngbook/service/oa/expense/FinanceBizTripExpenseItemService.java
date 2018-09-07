package com.youngbook.service.oa.expense;

import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.oa.expense.FinanceBizTripExpenseItemPO;
import com.youngbook.entity.vo.oa.expense.FinanceBizTripExpenseItemVO;
import com.youngbook.service.BaseService;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;

/**
 * Created by 邓超
 * Date 2015-5-19
 */
public class FinanceBizTripExpenseItemService extends BaseService {

    /**
     * 新增或修改
     *
     * @param financeBizTripExpenseItem
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int insertOrUpdate (FinanceBizTripExpenseItemPO financeBizTripExpenseItem, UserPO user, Connection conn) throws Exception {
        int count = 0;
        // 新增
        if ( financeBizTripExpenseItem.getId ().equals ("") ) {
            financeBizTripExpenseItem.setSid (MySQLDao.getMaxSid ("OA_FinanceBizTripExpenseItem", conn));
            financeBizTripExpenseItem.setId (IdUtils.getUUID32 ());
            financeBizTripExpenseItem.setState (Config.STATE_CURRENT);
            financeBizTripExpenseItem.setOperatorId (user.getId ());
            financeBizTripExpenseItem.setOperateTime (TimeUtils.getNow ());

            //添加数据
            count = MySQLDao.insert (financeBizTripExpenseItem, conn);
        }
        // 更新
        else {
            FinanceBizTripExpenseItemPO temp = new FinanceBizTripExpenseItemPO ();
            temp.setSid (financeBizTripExpenseItem.getSid ());
            temp = MySQLDao.load (temp, FinanceBizTripExpenseItemPO.class);
            temp.setState (Config.STATE_UPDATE);

            //更新数据
            count = MySQLDao.update (temp, conn);
            if ( count == 1 ) {
                financeBizTripExpenseItem.setSid (MySQLDao.getMaxSid ("OA_FinanceBizTripExpenseItem", conn));
                financeBizTripExpenseItem.setState (Config.STATE_CURRENT);
                financeBizTripExpenseItem.setOperatorId (user.getId ());
                financeBizTripExpenseItem.setOperateTime (TimeUtils.getNow ());

                //添加数据
                count = MySQLDao.insert (financeBizTripExpenseItem, conn);
            }
        }

        if ( count != 1 ) {
            throw new Exception ("数据库异常");
        }
        return count;
    }

    // 载入数据
    public FinanceBizTripExpenseItemPO loadFinanceBizTripExpenseItemPO (String id) throws Exception {

        //判断id是否为null
        if ( StringUtils.isEmpty (id) ) {
            MyException.newInstance ("差旅费报销id不能为null");
        }

        //设置查询数据
        FinanceBizTripExpenseItemPO po = new FinanceBizTripExpenseItemPO ();
        po.setId (id);
        po.setState (Config.STATE_CURRENT);

        //查询数据
        po = MySQLDao.load (po, FinanceBizTripExpenseItemPO.class);
        return po;
    }

    // 删除
    public int delete (FinanceBizTripExpenseItemPO financeBizTripExpenseItem, UserPO user, Connection conn) throws Exception {
        int count = 0;
        financeBizTripExpenseItem.setState (Config.STATE_CURRENT);
        financeBizTripExpenseItem = MySQLDao.load (financeBizTripExpenseItem, FinanceBizTripExpenseItemPO.class);
        financeBizTripExpenseItem.setState (Config.STATE_UPDATE);

        //更新数据
        count = MySQLDao.update (financeBizTripExpenseItem, conn);

        if ( count == 1 ) {
            financeBizTripExpenseItem.setSid (MySQLDao.getMaxSid ("OA_FinanceBizTripExpenseItem", conn));
            financeBizTripExpenseItem.setState (Config.STATE_DELETE);
            financeBizTripExpenseItem.setOperateTime (TimeUtils.getNow ());
            financeBizTripExpenseItem.setOperatorId (user.getId ());

            //添加数据
            count = MySQLDao.insert (financeBizTripExpenseItem, conn);
        }

        if ( count != 1 ) {
            throw new Exception ("删除失败");
        }
        return count;
    }


    /**
     * 2017-6-17
     * 周海鸿
     * 创建一个根据编号获取总金额的方法
     *
     * @param id FinaceBizTripExpenseWFAPO id
     * @return 差旅费报销总金额
     * @throws Exception
     */
    public String getMoneys (String id) throws Exception {

        if ( StringUtils.isEmpty (id) ) {
            MyException.newInstance ("差旅费报销id不能为null");
        }

        //构建sql语句
        StringBuffer sbSQL = new StringBuffer ();
        sbSQL.append ("");
        sbSQL.append (" SELECT");
        sbSQL.append (" sum(TotalFee) as TotalFee");
        sbSQL.append (" FROM");
        sbSQL.append (" OA_FinanceBizTripExpenseItem");
        sbSQL.append (" WHERE");
        sbSQL.append (" 1=1");
        sbSQL.append (" AND  state =0 ");
        sbSQL.append (" AND  ExpenseId = '" + Database.encodeSQL (id) + "'");

        //查询数据
        List li = MySQLDao.query (sbSQL.toString (), FinanceBizTripExpenseItemPO.class, null);

        String moneys = null;
        if ( li != null && li.size () > 0 ) {
            FinanceBizTripExpenseItemPO p = (FinanceBizTripExpenseItemPO) li.get (0);
            moneys = String.valueOf (p.getTotalFee ());
        }

        return moneys;
    }

    /*
    * 修改：周海鸿
    * 时间：2015-7-17
    * 内容：获取差旅费报销项目打印表*/
    public List<FinanceBizTripExpenseItemPO> getPrintDate (String id) throws Exception {
        //构建sql语句
        String sql = new String ("select * from oa_financebiztripexpenseitem item where item.state = 0 and item.expenseId = '" + Database.encodeSQL (id) + "'");

        //获取数据
        List li = MySQLDao.query (sql, FinanceBizTripExpenseItemPO.class, null);

        return li;
    }

    /**
     * 获取数据列表
     * @param financeBizTripExpenseItem
     * @param conditions
     * @param request
     * @return
     * @throws Exception
     */
    public Pager list (FinanceBizTripExpenseItemVO financeBizTripExpenseItem, List<KVObject> conditions, HttpServletRequest request) throws Exception {
        //构建sql语句
        StringBuffer sbSQL = new StringBuffer ();

        sbSQL.append ("select * from oa_financebiztripexpenseitem item where item.state = 0 and item.expenseId = '" + Database.encodeSQL (financeBizTripExpenseItem.getExpenseId ()) + "'");
        ;

        QueryType queryType = new QueryType (Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        conditions.add (new KVObject (Database.CONDITION_TYPE_ORDERBY, " sid " + Database.ORDERBY_DESC));

        //获取分页对象
        return Pager.query (sbSQL.toString (), financeBizTripExpenseItem, conditions, request, queryType);
    }

}
