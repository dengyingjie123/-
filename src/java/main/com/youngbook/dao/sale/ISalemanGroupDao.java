package com.youngbook.dao.sale;

import com.youngbook.common.KVObject;
import com.youngbook.entity.po.sale.SalemanGroupPO;
import com.youngbook.entity.po.sale.SalemanSalemangroupPO;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Lee on 2016/5/28.
 */
public interface ISalemanGroupDao {

    public SalemanGroupPO loadSalemanGroupPO(String id, Connection conn) throws Exception;

    public SalemanGroupPO getDefaultSalemanGroupByUserId(String userId, Connection conn) throws Exception;

    public SalemanGroupPO loadBySaleman(String salemanId, Connection conn) throws Exception;

    public void setDefaultFinanceCircle(String userId, String operatorId, Connection conn) throws Exception;


    /**
     * 徐明煜
     * 2018.12.14
     * 将与 SalemanSalemangroupPO 相关的增删改查方法从action和service统一写到在DAO中
     */
    public SalemanSalemangroupPO loadSalemanSalemangroupPO(SalemanSalemangroupPO saleman_salemangroup, Connection conn) throws Exception;

    public List<SalemanSalemangroupPO> listSalemanSalemangroupPO(String saleManId, Connection conn) throws Exception;

    public SalemanSalemangroupPO deleteSalemanSalemangroupPO(SalemanSalemangroupPO Saleman_salemangroup, String operatorId, Connection conn) throws Exception;

    public SalemanSalemangroupPO insertOrUpdateSalemanSalemangroupPO(SalemanSalemangroupPO Saleman_salemangroup, String operatorId, Connection conn) throws Exception;
}
