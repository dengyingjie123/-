package com.youngbook.dao.sale;

import com.youngbook.common.Pager;
import com.youngbook.entity.vo.Sale.ViewOrderVO;

import java.sql.Connection;
import java.util.List;

/**
 * @author 徐明煜
 * @description
 * @data 2018/12/2 19:17
 */
public interface ISalesmanStockDao {

    /**
     * 根据销售人员返回page对象
     * @return Pager
     */
    public Pager pagerViewOrderByName(String selectName, ViewOrderVO viewOrderVO, Pager pager, Connection conn) throws Exception;


    /**
     * 根据销售人员返回list对象
     * @return list
     */
    public List<ViewOrderVO> listViewOrderbyName(String selectName, Class<ViewOrderVO> clazz, Connection conn) throws Exception ;
}
