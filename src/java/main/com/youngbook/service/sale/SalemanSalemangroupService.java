package com.youngbook.service.sale;

import com.youngbook.common.KVObject;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.sale.SalemanGroupDaoImpl;
import com.youngbook.entity.po.sale.SalemanSalemangroupPO;
import com.youngbook.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/5/20.
 *
 * @author <a href="http://c.hopewealth.net/pages/viewpage.action?pageId=27066425
 *         " target="_blank">Zhouhaihong</a>
 */
@Component("salemanSalemangroupService")
public class SalemanSalemangroupService extends BaseService {

    @Autowired
    private SalemanGroupDaoImpl salemanGroupDao;


    /**
     * @description 逻辑删除销售人员销售组关系
     * @author 徐明煜
     * @date 2018/12/14 15:26
     * @param Saleman_salemangroup
     * @param operatorId
     * @param conn
     * @return com.youngbook.entity.po.sale.SalemanSalemangroupPO
     * @throws Exception
     */
    public SalemanSalemangroupPO delete(SalemanSalemangroupPO Saleman_salemangroup, String operatorId, Connection conn) throws Exception {

        salemanGroupDao.deleteSalemanSalemangroupPO(Saleman_salemangroup, operatorId, conn);
        return Saleman_salemangroup;
    }


    /**
     * @description 加载一个SalemanSalemangroupPO对象
     * @author 徐明煜
     * @date 2018/12/14 15:37
     * @param saleman_salemangroup
     * @param conn
     * @return com.youngbook.entity.po.sale.SalemanSalemangroupPO
     * @throws Exception
     */
    public SalemanSalemangroupPO load(SalemanSalemangroupPO saleman_salemangroup, Connection conn) throws Exception {

        SalemanSalemangroupPO salemanSalemangroupPO = salemanGroupDao.loadSalemanSalemangroupPO(saleman_salemangroup,conn);
        return salemanSalemangroupPO;
    }


    /**
     * @description 对新增SalemanSalemangroupPO或修改后的SalemanSalemangroupPO对象进行保存
     * @author 徐明煜
     * @date 2018/12/14 15:55
     * @param SalemanSalemangroup
     * @param operatorId
     * @param conn
     * @return com.youngbook.entity.po.sale.SalemanSalemangroupPO
     * @throws Exception
     */
    public SalemanSalemangroupPO insertOrUpdate(SalemanSalemangroupPO SalemanSalemangroup, String operatorId, Connection conn) throws Exception{

        salemanGroupDao.insertOrUpdateSalemanSalemangroupPO(SalemanSalemangroup, operatorId, conn);
        return SalemanSalemangroup;
    }


    /**
     * @description 根据销售人员 id 查找销售分组关系
     * @author 徐明煜
     * @date 2018/12/14 16:02
     * @param saleManId
     * @param conn
     * @return java.util.List<com.youngbook.entity.po.sale.SalemanSalemangroupPO>
     * @throws Exception
     */
    public List<SalemanSalemangroupPO> listSalemanSalemangroupsPOBySalemanId(String saleManId, Connection conn) throws Exception {

        List<SalemanSalemangroupPO> list = salemanGroupDao.listSalemanSalemangroupPO(saleManId, conn);
        return list;

    }

}
