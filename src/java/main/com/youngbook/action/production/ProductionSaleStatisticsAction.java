package com.youngbook.action.production;

import com.youngbook.action.BaseAction;
import com.youngbook.common.MyException;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.entity.vo.production.ProductionSaleStatisticsVO;
import com.youngbook.service.production.ProductionSaleStatisticsService;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Lee on 2016/5/20.
 */
public class ProductionSaleStatisticsAction extends BaseAction {

    private ProductionSaleStatisticsService service = new ProductionSaleStatisticsService();

    public String getProductionSaleStatisticsByProductionId() throws Exception {

        String productionId = HttpUtils.getParameter(getRequest(), "productionId");

        if (StringUtils.isEmpty(productionId)) {
            MyException.newInstance("无法获得产品编号").throwException();
        }

        Connection conn = getConnection();

        List<ProductionSaleStatisticsVO> list = service.getProductionSaleStatistics(null, productionId,null, conn);

        ProductionSaleStatisticsVO statistics = new ProductionSaleStatisticsVO();
        statistics.setTotalPaybackMoney(0);
        statistics.setTotalTransferMoney(0);
        statistics.setTotalRemainMoney(0);
        statistics.setTotalSaleMoney(0);

        if (list != null) {
            for (ProductionSaleStatisticsVO vo : list) {
                double saleMoney = statistics.getTotalSaleMoney() + vo.getTotalSaleMoney();
                statistics.setTotalSaleMoney(saleMoney);

                double transferMoney = statistics.getTotalTransferMoney() + vo.getTotalTransferMoney();
                statistics.setTotalTransferMoney(transferMoney);

                double paybackMoney = statistics.getTotalPaybackMoney() + vo.getTotalPaybackMoney();
                statistics.setTotalPaybackMoney(paybackMoney);

                double remainMoney = statistics.getTotalRemainMoney() + vo.getTotalRemainMoney();
                statistics.setTotalRemainMoney(remainMoney);
            }
        }


        getResult().setReturnValue(statistics.toJsonObject());


        return SUCCESS;
    }
}
