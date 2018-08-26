package com.youngbook.action.sale;

import com.youngbook.action.BaseAction;
import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.entity.po.sale.ProductionTransferPO;
import com.youngbook.entity.vo.Sale.ProductionTransferVO;
import com.youngbook.service.sale.ProductionTransferService;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class ProductionTransferAction extends BaseAction {
    private ProductionTransferPO productionTransfer = new ProductionTransferPO();
    private ProductionTransferVO productionTransferVO = new ProductionTransferVO();

    @Autowired
    ProductionTransferService productionTransferService;


    public String delete() throws Exception {
        productionTransferService.delete(productionTransfer, getLoginUser(), getConnection());
        return SUCCESS;
    }

    public String insertOrUpdate() throws Exception {
        int count = productionTransferService.insertOrUpdate(productionTransfer, getLoginUser(), getConnection());
        if (count != 1) {
            getResult().setMessage("操作失败");
        }
        return SUCCESS;
    }

    public String load() throws Exception {
        productionTransfer = productionTransferService.loadProductionTransferPO(productionTransfer.getId());
        getResult().setReturnValue(productionTransfer.toJsonObject4Form());
        return SUCCESS;
    }

    public String list() throws Exception {
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT pf.*,o.OrderNum OriginalOrderNum,p.productId OriginalProductionNum,cp.`Name` OriginalCustumerName,p.productId CurrentProductionNum,cp.`Name` CurrentCustomerName,u.`name` OperatorName,u.`name` CheckerName,");
        sbSQL.append("(CASE pf.TransferSatues WHEN 0 THEN '未转让' ELSE '已转让' END) AS Transfer_Satues ");
        sbSQL.append(" FROM sale_productiontransfer pf,crm_order o,crm_production p,crm_customerpersonal cp,system_user u ");
        sbSQL.append(" WHERE 1 = 1 AND pf.State = 0 AND o.state = 0 AND p.state = 0 AND cp.state = 0 AND u.state = 0 AND pf.OriginalOrderId = o.id AND pf.OriginalProductionId = p.id AND pf.OriginalCustumerId = cp.id AND pf.OperatorId = u.Id AND pf.CheckerId = u.Id");
        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions =new ArrayList<KVObject>();
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        Pager pager = Pager.query(sbSQL.toString(),productionTransferVO,conditions,request,queryType);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 获取JSON数组
     * @return
     */
    public String StatusTree() throws Exception{
        getResult().setReturnValue(productionTransferService.getStatusTree());
        return SUCCESS;
    }

    public ProductionTransferPO getProductionTransfer() {
        return productionTransfer;
    }
    public void setProductionTransfer(ProductionTransferPO productionTransfer) {this.productionTransfer = productionTransfer;}

    public ProductionTransferVO getProductionTransferVO() {
        return productionTransferVO;
    }
    public void setProductionTransferVO(ProductionTransferVO productionTransferVO) {this.productionTransferVO = productionTransferVO;}
}
