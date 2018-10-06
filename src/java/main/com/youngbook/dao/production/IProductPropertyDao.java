package com.youngbook.dao.production;

import com.youngbook.common.Pager;
import com.youngbook.entity.po.production.ProductPropertyPO;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Lee on 2016/10/3.
 */
public interface IProductPropertyDao {

    public Pager getPropertiesByProductId(String productId, int currentPage, int showRowCount, Connection conn) throws Exception;
    public ProductPropertyPO newProperty(ProductPropertyPO productProperty, Connection conn) throws Exception;
    public ProductPropertyPO loadById(String productPropertyId, Connection conn) throws Exception;
    public ProductPropertyPO loadProductPropertyPO(String productionHomeId, String productPropertyTypeId, Connection conn) throws Exception;
}
