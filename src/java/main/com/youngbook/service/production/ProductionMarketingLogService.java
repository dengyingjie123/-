package com.youngbook.service.production;

import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.MyException;
import com.youngbook.common.ReturnObjectCode;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.production.IProductionDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.production.ProductionMarketingLogPO;
import com.youngbook.entity.po.production.ProductionPO;
import com.youngbook.entity.po.production.ProductionStatus;
import com.youngbook.entity.vo.production.ProductionMarketingLogVO;
import com.youngbook.service.customer.SalemanOuterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Component("productionMarketingLogService")
public class ProductionMarketingLogService {

    @Autowired
    IProductionDao productionDao;

    /**
     * 营销产品
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年3月21日
     *
     * @param productionId
     * @param salemanOuterId
     * @return
     * @throws Exception
     */
    public Integer marketing(String productionId, String salemanOuterId, Connection conn) throws Exception {

        Integer count = 0;


        // 检测产品是否存在
        ProductionPO production = productionDao.loadProductionById(productionId, conn);
        if(production == null) {
            MyException.newInstance(ReturnObjectCode.PRODUCTION_NOT_EXISTENT, "产品不存在").throwException();
        }

        // 检测产品是否已过期
        long remainSeconds = TimeUtils.getSecondDifference(TimeUtils.getNow(), production.getExpiringDate(), TimeUtils.Format_YYYY_MM_DD_HH_M_S);
        if (remainSeconds < 0) {
            MyException.newInstance(ReturnObjectCode.PRODUCTION_EXPIRED, "产品已过期").throwException();
        }

        // 检测产品状态是否为在售
        if(production.getStatus() != ProductionStatus.Sale) {
            MyException.newInstance(ReturnObjectCode.PRODUCTION_NOT_SALING, "产品已售罄或已过期").throwException();
        }

        // 检测对外销售人员（是否存在、状态是否为正常）
        SalemanOuterService salemanOuterService = new SalemanOuterService();
        UserPO salemanOuterPO =  salemanOuterService.loadSalemanById(salemanOuterId, conn);
        if(salemanOuterPO == null) {
            MyException.newInstance(ReturnObjectCode.SALEMAN_OUTER_NOT_EXISTENT, "理财师尚未注册").throwException();
        }
        if(!salemanOuterPO.getStatus().equals("9662")) {
            MyException.newInstance(ReturnObjectCode.SALEMAN_OUTER_STATUS_EXCEPTION, "理财师状态异常").throwException();
        }

        // 记录营销
        ProductionMarketingLogPO marketingLogPO = new ProductionMarketingLogPO();
        marketingLogPO.setProductionId(productionId);
        marketingLogPO.setSalemanOuterId(salemanOuterId);
        count = MySQLDao.insertOrUpdate(marketingLogPO, conn);

        return count;

    }

    /**
     * 获取某个产品的营销统计
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年3月21日
     *
     * @param productionId
     * @return
     * @throws Exception
     */
    public Integer countMarketingTimes(String productionId) throws Exception {

        // 定义 SQL
        String sql = "select l.id as productionTotalMarketing from crm_productionmarketinglog l where l.state = 0 and l.productionId = ? ";

        // 定义参数
        List<KVObject> params = new ArrayList<KVObject>();
        params = Database.addQueryKVObject(1, productionId);

        // 进行查询
        List<ProductionMarketingLogVO> list = MySQLDao.search(sql, params, ProductionMarketingLogVO.class, new ArrayList<KVObject>());

        return list == null ? 0 : list.size();

    }

}
