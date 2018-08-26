package com.youngbook.service.production;

import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.wf.Database;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.vo.production.ProductionSaleStatisticsVO;
import com.youngbook.service.BaseService;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Lee on 2016/5/20.
 */
public class ProductionSaleStatisticsService extends BaseService {

    public Pager getProductionSaleStatistics(String customerId, String productionId, String orderId, int currentPage, int showRowCount, Connection conn) throws Exception {

        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("listCustomrs4DistributionToManagedSaleGroup", "CustomerPersonalServiceSQL", ProductionSaleStatisticsVO.class);
        dbSQL.addParameter4All("customerId", customerId).addParameter4All("productionId", productionId).addParameter4All("orderId", orderId);
        dbSQL.initSQL();

        StringBuffer sbSQL = new StringBuffer(dbSQL.getSQL());
        sbSQL.insert(0, "select DISTINCT _ft_.* from (").append(" ) _ft_ ");

        ProductionSaleStatisticsVO productionSaleStatistics = new ProductionSaleStatisticsVO();

        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        Pager pager = Pager.search(sbSQL.toString(), dbSQL.getParameters(), productionSaleStatistics, null, currentPage, showRowCount, queryType, conn);

        return pager;
    }


    public List<ProductionSaleStatisticsVO> getProductionSaleStatistics(String customerId, String productionId, String orderId, Connection conn) throws Exception {

        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("getProductionSaleStatistics", "ProductionSaleStatisticsServiceSQL", ProductionSaleStatisticsService.class);
        dbSQL.addParameter4All("customerId", customerId).addParameter4All("productionId", productionId).addParameter4All("orderId", orderId);
        dbSQL.initSQL();

        StringBuffer sbSQL = new StringBuffer(dbSQL.getSQL());
        sbSQL.insert(0, "select DISTINCT _ft_.* from (").append(" ) _ft_ ");

        List<ProductionSaleStatisticsVO> list = MySQLDao.search(sbSQL.toString(), dbSQL.getParameters(), ProductionSaleStatisticsVO.class, null, conn);

        return list;
    }
}
