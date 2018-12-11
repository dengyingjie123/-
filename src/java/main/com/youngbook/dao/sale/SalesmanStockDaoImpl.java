package com.youngbook.dao.sale;

import com.youngbook.common.Pager;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.vo.Sale.ViewOrderVO;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

/**
 *
 * @author: 徐明煜
 * @version:1.1
 * @create: 2018-12-02 19:37
 */
@Component("SalesmanStockDao")
public class SalesmanStockDaoImpl implements ISalesmanStockDao {

    /**
     *
     * @param selectName
     * @param viewOrderVO
     * @param pager
     * @param conn
     * @return
     * @throws Exception
     */
    @Override
    public Pager pagerViewOrderByName(String selectName, ViewOrderVO viewOrderVO, Pager pager, Connection conn) throws Exception {
        DatabaseSQL dbSQL = DatabaseSQL.newInstance("2K81189");
        dbSQL.addParameter4All("selectName", selectName);
        dbSQL.initSQL();
        Pager pagerViewOrderVOByName = MySQLDao.search(dbSQL.getSQL(), dbSQL.getParameters(), viewOrderVO, null, pager.getCurrentPage(), pager.getShowRowCount(), null, conn);
        return pagerViewOrderVOByName;
    }


    /**
     * @description 方法实现说明
     * @author 徐明煜
     * @date 2018/12/2 22:58
     * @param selectName
     * @param clazz
     * @param conn
     * @return java.util.List<com.youngbook.entity.vo.Sale.ViewOrderVO>
     * @throws Exception
     */
    @Override
    public List<ViewOrderVO> listViewOrderbyName(String selectName, Class<ViewOrderVO> clazz, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("2K81189");
        dbSQL.addParameter4All("selectName", selectName);
        dbSQL.initSQL();
        List<ViewOrderVO> viewOrderVOList = MySQLDao.search(dbSQL, clazz, conn);
        return viewOrderVOList;
    }
}
