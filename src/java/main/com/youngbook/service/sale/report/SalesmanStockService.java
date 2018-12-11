package com.youngbook.service.sale.report;

import com.youngbook.common.Pager;
import com.youngbook.dao.sale.SalesmanStockDaoImpl;
import com.youngbook.entity.vo.Sale.ViewOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

/**
 * @author: 徐明煜
 * @version:1.1
 * @create: 2018-12-02 22:59
 */
@Component("SalesmanStockService")
public class SalesmanStockService {

    @Autowired
    private SalesmanStockDaoImpl salesmanStockDao;

    public Pager pagerViewOrderVoByName(String selectName, ViewOrderVO viewOrderVO, Pager pager, Connection conn) throws Exception {
        return salesmanStockDao.pagerViewOrderByName(selectName, viewOrderVO, pager, conn);
    }

    public List<ViewOrderVO> listViewOrderVOByName(String selectName, Class<ViewOrderVO> clazz, Connection conn) throws Exception {
        return salesmanStockDao.listViewOrderbyName(selectName, clazz, conn );
    }
}
