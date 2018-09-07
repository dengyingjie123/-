package com.youngbook.dao.production;

import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.MyException;
import com.youngbook.common.ReturnObject;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.production.ProductionCompositionPO;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 2016/5/28.
 */
@Component("productionCompositionDao")
public class ProductionCompositionDaoImpl implements IProductionCompositionDao {

    /**
     * 根据产品 ID 和价格区间获取产品构成
     * @param productionId
     * @param money
     * @param conn
     * @return
     * @throws Exception
     */
    public ProductionCompositionPO getProductionCompositionPOByProductionIdAndMoney(String productionId, Double money, Connection conn) throws Exception {
        ProductionCompositionPO po = null;
        String sql = "select * from crm_productioncomposition c " +
                "where c.state = 0 " +
                "and c.productionId = '" + Database.encodeSQL(productionId) + "' " +
                "and c.sizeStart <= '" + money + "' " +
                "and c.sizeStop > '" + money + "' ";
        List<ProductionCompositionPO> list = MySQLDao.query(sql, ProductionCompositionPO.class, new ArrayList<KVObject>(), conn);
        // 1 个价格只能存在于 1 个构成区间
        if(list.size() == 1) {
            po = list.get(0);
        }
        return po;
    }


    public ProductionCompositionPO getProductionCompositionPOByProductionIdAndMoney(String productionId, String productioinCompositionId, Double money, Connection conn) throws Exception {

        String sql = "select * from crm_productioncomposition c " +
                "where c.state = 0 " +
                "and c.productionId = ? " +
                "and c.sizeStart <= ? " +
                "and c.sizeStop >= ?  and id=?";

        DatabaseSQL dbSQL = DatabaseSQL.getInstance(sql).addParameter(1, productionId).addParameter(2, money).addParameter(3, money).addParameter(4, productioinCompositionId);

        List<ProductionCompositionPO> list = MySQLDao.search(dbSQL, ProductionCompositionPO.class, conn);

        if (list == null || list.size() != 1) {
            MyException.newInstance("操作失败，请约定投资金额与产品构成相匹配。").throwException();
        }

        return list.get(0);
    }

    /**
     * 通过 构成的 ID 查询产品构成
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年4月20日
     *
     * @param productionId
     * @param conn
     * @return
     * @throws Exception
     */
    public ProductionCompositionPO getByProductionId(String productionId, Connection conn) throws Exception {

        String sql = "select * from crm_productioncomposition c where c.state = 0 and c.id = '" + productionId + "'";
        List<ProductionCompositionPO> compositions = MySQLDao.query(sql, ProductionCompositionPO.class, new ArrayList<KVObject>(), conn);
        if(compositions.size() != 1) {
            MyException.newInstance(ReturnObject.CODE_DB_EXCEPTION, "数据异常").throwException();
        }
        return compositions.get(0);

    }
}
