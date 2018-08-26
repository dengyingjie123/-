package com.youngbook.dao.sale;

import com.youngbook.entity.po.sale.ProductionCommissionPO;
import com.youngbook.entity.vo.Sale.ProductionCommissionVO;

import java.sql.Connection;
import java.util.List;

/**
 * Created by yux on 2016/6/12.
 */
public interface IProductionCommissionDao {
    ProductionCommissionPO loadProductionCommissionPOById(String id, Connection conn) throws Exception;
    public List<ProductionCommissionVO> getProductionCommissionVOByProctionId(String productionId, Connection conn) throws Exception;
}
