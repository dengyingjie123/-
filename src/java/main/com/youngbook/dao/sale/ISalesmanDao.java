package com.youngbook.dao.sale;

import com.youngbook.common.Pager;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.sale.SalesmanPO;
import com.youngbook.entity.vo.Sale.SalesManVO;

import java.sql.Connection;

/**
 * Created by Lee on 2016/8/10.
 */
public interface ISalesmanDao {

    public Pager listPagerSalesmanGroup(SalesManVO salesManVO, int currentPage, int showRowCount, Connection conn) throws Exception;

    public SalesmanPO insertSalesman(UserPO user, Connection conn) throws Exception;
}
