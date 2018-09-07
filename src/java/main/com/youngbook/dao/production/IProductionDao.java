package com.youngbook.dao.production;

import com.youngbook.entity.po.production.ProductionPO;
import com.youngbook.entity.vo.production.ProductionVO;
import com.youngbook.entity.wvo.production.ProductionWVO;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Lee on 2016/5/28.
 */
public interface IProductionDao {

    public List<ProductionWVO> getListProductByProductionIdAndMoney(String productionId, Connection conn) throws Exception;
    public List<ProductionPO> listProductionPOByProductionNameOrProductionNO(String productionName, String productionNO, Connection conn) throws Exception;

    public List<ProductionVO> getListProductionVO(ProductionVO productionVO,  Connection conn) throws Exception;
//    public  boolean checkIsProductionBelongOnlineProject(String productionId, Connection conn) throws Exception;

//    public boolean checkIsProductionBelongProject(String productionId, String projectId, Connection conn) throws Exception;
    public ProductionPO getProductionById(String id, Connection conn) throws Exception;
    public ProductionWVO getProductByProductionIdAndMoney(String id, double money, Connection conn) throws Exception;
}
