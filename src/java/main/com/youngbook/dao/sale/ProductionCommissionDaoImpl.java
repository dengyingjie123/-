package com.youngbook.dao.sale;

import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.sale.ProductionCommissionPO;
import com.youngbook.entity.vo.Sale.ProductionCommissionVO;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

/**
 * Created by yux on 2016/6/12.
 */
@Component("productionCommissionDao")
public class ProductionCommissionDaoImpl implements IProductionCommissionDao {
    @Override
    public ProductionCommissionPO loadProductionCommissionPOById(String id, Connection conn) throws Exception {
        DatabaseSQL dbSQL = DatabaseSQL.getInstance("select * from sale_ProductionCommission p where p.state=0 and p.id =? ").addParameter(1, id);
        List<ProductionCommissionPO> productionCommissions = MySQLDao.search(dbSQL, ProductionCommissionPO.class, conn);

        if (productionCommissions != null && productionCommissions.size() == 1) {
            return  productionCommissions.get(0);
        }
        return null;
    }

    public List<ProductionCommissionVO> getProductionCommissionVOByProctionId(String productionId, Connection conn) throws Exception {
        DatabaseSQL dbSQL = DatabaseSQL.getInstance("select * from v_sale_productioncommission pc where pc.state=0 and pc.productionId =? ").addParameter(1, productionId);
        List<ProductionCommissionVO> productionCommissions = MySQLDao.search(dbSQL, ProductionCommissionVO.class, conn);

        return productionCommissions;
    }


}


