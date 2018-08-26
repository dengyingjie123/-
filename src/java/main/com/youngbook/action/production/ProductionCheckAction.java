package com.youngbook.action.production;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.production.ProductionCheckPO;
import com.youngbook.entity.vo.production.ProductionCheckVO;
import com.youngbook.service.production.ProductionCheckService;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ProductionCheckAction extends BaseAction {

    /**
     * 产品验证的PO、VO类，Service逻辑类对象
     */
    private ProductionCheckVO productionCheckVO = new ProductionCheckVO();
    private ProductionCheckPO productionCheck = new ProductionCheckPO();

    @Autowired
    ProductionCheckService productionCheckService;

    /**
     * 该方法用啦获取数据列表
     * @return
     * @throws Exception
     */
    public String list() throws Exception {

        productionCheckVO = HttpUtils.getInstanceFromRequest(getRequest(), "productionCheckVO", ProductionCheckVO.class);

        HttpServletRequest request = ServletActionContext.getRequest();
        //时间段查询
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request,ProductionCheckPO.class);
        //查询数据
        Pager pager = productionCheckService.list(productionCheckVO,conditions);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 添加并跟新数据
     * @return
     * @throws Exception
     */
    public String insertOrUpdate() throws Exception {

        productionCheck = HttpUtils.getInstanceFromRequest(getRequest(), "productionCheck", ProductionCheckPO.class);

        //调用逻辑类
        int count = productionCheckService.insertOrUpdate(productionCheck, getLoginUser(), getConnection());
        if (count != 1) {
            getResult().setMessage("操作失败");
        }
        return SUCCESS;
    }

    /**
     * 获取单独数据的数据
     * @return
     * @throws Exception
     */
    public String load() throws Exception {
        productionCheck = HttpUtils.getInstanceFromRequest(getRequest(), "productionCheck", ProductionCheckPO.class);

        productionCheck = productionCheckService.loadProductionCheckPO(productionCheck.getId());
        getResult().setReturnValue(productionCheck.toJsonObject4Form());
        return SUCCESS;
    }

    /**
     * 操作请求方法
     * @return
     * @throws Exception
     */
    public String delete() throws Exception {
        productionCheck = HttpUtils.getInstanceFromRequest(getRequest(), "productionCheck", ProductionCheckPO.class);
        productionCheckService.delete(productionCheck, getLoginUser(), getConnection());
        return SUCCESS;
    }

    public ProductionCheckVO getProductionCheckVO() {
        return productionCheckVO;
    }
    public void setProductionCheckVO(ProductionCheckVO productionCheckVO) {this.productionCheckVO = productionCheckVO;}

    public ProductionCheckPO getProductionCheck() {
        return productionCheck;
    }
    public void setProductionCheck(ProductionCheckPO productionCheck) {
        this.productionCheck = productionCheck;
    }

    public ProductionCheckService getProductionCheckService() {
        return productionCheckService;
    }

    public void setProductionCheckService(ProductionCheckService productionCheckService) {
        this.productionCheckService = productionCheckService;
    }
}
