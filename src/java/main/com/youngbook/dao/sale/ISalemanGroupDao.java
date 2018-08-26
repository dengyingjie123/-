package com.youngbook.dao.sale;

import com.youngbook.entity.po.sale.SalemanGroupPO;

import java.sql.Connection;

/**
 * Created by Lee on 2016/5/28.
 */
public interface ISalemanGroupDao {

    public SalemanGroupPO loadSalemanGroupPO(String id, Connection conn) throws Exception;

    public SalemanGroupPO getDefaultSalemanGroupByUserId(String userId, Connection conn) throws Exception;

    public SalemanGroupPO loadBySaleman(String salemanId, Connection conn) throws Exception;

    public void setDefaultFinanceCircle(String userId, Connection conn) throws Exception;
}
