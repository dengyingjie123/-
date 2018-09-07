package com.youngbook.dao.production;

import com.youngbook.common.Database;
import com.youngbook.common.MyException;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.production.ProductPropertyPO;
import com.youngbook.entity.vo.production.ProductPropertyVO;
import com.youngbook.service.customer.CustomerPersonalService;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Lee on 2016/10/3.
 */
@Component("productPropertyDao")
public class ProductPropertyDaoImpl implements IProductPropertyDao {
    public Pager getPropertiesByProductId(String productId, int currentPage, int showRowCount, Connection conn) throws Exception {

        if (StringUtils.isEmpty(productId)) {
            MyException.newInstance("无法获得产品编号").throwException();
        }

        DatabaseSQL dbSQL = new DatabaseSQL();
        dbSQL.newSQL("getPropertiesByProductId", "ProductPropertyDaoSQL", ProductPropertyDaoImpl.class);
        dbSQL.addParameter4All("productId", productId);
        dbSQL.initSQL();

        StringBuffer sbSQL = new StringBuffer(dbSQL.getSQL());
        sbSQL.insert(0, "select DISTINCT _ft_.* from (").append(" ) _ft_ ");

        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        Pager pager = Pager.search(sbSQL.toString(), dbSQL.getParameters(), new ProductPropertyVO(), null, currentPage, showRowCount, queryType, conn);

        return pager;
    }


    public ProductPropertyPO newProperty(ProductPropertyPO productProperty, Connection conn) throws Exception {

        if (MySQLDao.insertOrUpdate(productProperty, conn) == 1) {
            return productProperty;
        }

        return null;
    }

    public ProductPropertyPO loadById(String productPropertyId, Connection conn) throws Exception {

        ProductPropertyPO productProperty = new ProductPropertyPO();
        productProperty.setId(productPropertyId);
        productProperty.setState(Config.STATE_CURRENT);
        productProperty = MySQLDao.load(productProperty, ProductPropertyPO.class, conn);

        return productProperty;
    }
}
