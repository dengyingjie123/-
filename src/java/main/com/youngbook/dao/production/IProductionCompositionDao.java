package com.youngbook.dao.production;

import com.youngbook.entity.po.production.ProductionCompositionPO;

import java.sql.Connection;

/**
 * Created by Lee on 2016/5/28.
 */
public interface IProductionCompositionDao {
    public ProductionCompositionPO getByProductionId(String productionId, Connection conn) throws Exception;
    public ProductionCompositionPO getProductionCompositionPOByProductionIdAndMoney(String productionId, Double money, Connection conn) throws Exception;
    public ProductionCompositionPO getProductionCompositionPOByProductionIdAndMoney(String productionId, String productioinCompositionId, Double money, Connection conn) throws Exception;
}
