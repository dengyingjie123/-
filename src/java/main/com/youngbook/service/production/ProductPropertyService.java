package com.youngbook.service.production;

import com.youngbook.common.Pager;
import com.youngbook.dao.production.IProductPropertyDao;
import com.youngbook.entity.po.production.ProductPropertyPO;
import com.youngbook.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;

/**
 * Created by Lee on 2016/10/3.
 */
@Component("productPropertyService")
public class ProductPropertyService extends BaseService {

    @Autowired
    IProductPropertyDao productPropertyDao;

    public Pager getPropertiesByProductId(String productId, int currentPage, int showRowCount, Connection conn) throws Exception {
        return productPropertyDao.getPropertiesByProductId(productId, currentPage, showRowCount, conn);
    }

    public ProductPropertyPO newProperty(ProductPropertyPO productProperty, Connection conn) throws Exception {

        return productPropertyDao.newProperty(productProperty, conn);
    }

    public ProductPropertyPO loadById(String productPropertyId, Connection conn) throws Exception {
        return productPropertyDao.loadById(productPropertyId, conn);
    }
}
