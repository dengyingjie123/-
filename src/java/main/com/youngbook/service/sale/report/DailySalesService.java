package com.youngbook.service.sale.report;

import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.wvo.sale.report.DailySalesWVO;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Component("dailySalesService")
public class DailySalesService {

    /**
     * 获取打印数据
     *
     * 1、直接使用 SQL 查询数据并返回
     * 2、使用方法： List<DailySalesWVO> data = new DailySalesService().getPrintData4W(beginDate, endDate, conn);
     *
     * 作者：邓超
     * 内容：创建方法
     * 时间：2015-8-30
     *
     * @param beginDate
     * @param endDate
     * @param conn
     * @return List<DailySalesWVO>
     * @throws Exception
     */
    public List<DailySalesWVO> getPrintData4W(String beginDate, String endDate, Connection conn) throws Exception {

        String sql = DailySalesService.getSQL(beginDate, endDate);
        return MySQLDao.query(sql, DailySalesWVO.class, new ArrayList<KVObject>(), conn);

    }

    /**
     * 查询数据
     *
     * 1、直接使用 SQL 查询数据并返回
     * 2、使用方法： pager pager = new DailySalesService().list(beginDate, endDate, request);
     *
     * 作者：邓超
     * 内容：创建方法
     * 时间：2015-8-30
     *
     * @param beginDate
     * @param endDate
     * @param request
     * @return List<DailySalesWVO>
     * @throws Exception
     */
    public Pager list(String beginDate, String endDate, HttpServletRequest request) throws Exception {

        // 查询类型
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        // 获取 SQL 并查询数据
        String sql = DailySalesService.getSQL(beginDate, endDate);
        return Pager.query(sql, new DailySalesWVO(), new ArrayList<KVObject>(), request, queryType);

    }

    /**
     * 获取 SQL
     *
     * 1、直接使用 SQL 查询数据并返回
     * 2、使用方法： String sql = DailySalesService.getSQL("", "");
     *
     * 作者：邓超
     * 内容：创建方法
     * 时间：2015-8-30
     *
     * @param beginDate
     * @param endDate
     * @return List<DailySalesWVO>
     * @throws Exception
     */
    public static String getSQL(String beginDate, String endDate) throws Exception {

        // 组装 SQL
        StringBuffer sql = new StringBuffer();

        sql.append("SELECT ");
        sql.append("	p. NAME AS 'productionName', ");
        sql.append("	sum(o.money) AS 'saleMoney', ");
        sql.append("	p.size AS 'size', ");
        sql.append("	round(sum(o.money) / p.size * 100, 2) AS 'daySalesRatio', ");
        sql.append("	round(( ");
        sql.append("		SELECT ");
        sql.append("			sum(o1.money) ");
        sql.append("		FROM ");
        sql.append("			crm_production p1, ");
        sql.append("			crm_order o1 ");
        sql.append("		WHERE ");
        sql.append("			p1.state = 0 ");
        sql.append("		AND o1.state = 0 ");
        sql.append("		AND o1.status = 1 ");
        sql.append("		AND o1.productionId = p1.id ");
        sql.append("		AND p1.id = p.id ");
        sql.append("	), 2) AS 'totalSalesRatio' ");
        sql.append("FROM ");
        sql.append("	crm_production p, ");
        sql.append("	crm_order o ");
        sql.append("WHERE ");
        sql.append("	p.state = 0 ");
        sql.append("AND o.state = 0 ");
        sql.append("AND o.status = 1 ");
        sql.append("AND o.productionId = p.id ");
        sql.append("AND o.createTime >= '" + Database.encodeSQL(beginDate) + "' ");
        sql.append("AND o.createTime <= '" + Database.encodeSQL(endDate) + "' ");
        sql.append("GROUP BY ");
        sql.append("	p.NAME ");

        return sql.toString();
    }

}
