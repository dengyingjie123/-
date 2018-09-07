package com.youngbook.action.production;

import com.youngbook.action.BaseAction;
import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.production.ProductionInfoPO;
import com.youngbook.entity.vo.production.ProductionInfoVO;
import com.youngbook.service.production.ProductionInfoService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;

public class ProductionInfoAction extends BaseAction {

    ProductionInfoPO productionInfo = new ProductionInfoPO();
    ProductionInfoVO productionInfoVO = new ProductionInfoVO();
    @Autowired
    ProductionInfoService productionInfoService;

    private String title=new String();
    private String prod_description=new String();
    private int isDisplay=Integer.MAX_VALUE;
    private String desType=new String();

    // 获取数据的list方法
    public String list() throws Exception{

        productionInfoVO = HttpUtils.getInstanceFromRequest(getRequest(), "productionInfoVO", ProductionInfoVO.class);

        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request, ProductionInfoVO.class);
        Pager pager = productionInfoService.list(productionInfoVO,conditions);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    // 显示详情的load方法
    public String load() throws Exception{
        productionInfo = HttpUtils.getInstanceFromRequest(getRequest(), "productionInfo", ProductionInfoPO.class);

        productionInfo.setState(Config.STATE_CURRENT);
        productionInfo = MySQLDao.load(productionInfo, ProductionInfoPO.class);
        if (productionInfo != null) {
            getResult().setReturnValue(productionInfo.toJsonObject4Form());
            return SUCCESS;
        }

        return SUCCESS;
    }

    // 新增和更新的方法
    public String insertOrUpdate() throws Exception {

        productionInfo = HttpUtils.getInstanceFromRequest(getRequest(), "productionInfo", ProductionInfoPO.class);

        if (StringUtils.isEmpty(productionInfo.getTitle1())) {
            productionInfo.setTitle1(null);
        }

        if (StringUtils.isEmpty(productionInfo.getContent1())) {
            productionInfo.setContent1(null);
        }

        if (StringUtils.isEmpty(productionInfo.getIsDisplay1())) {
            productionInfo.setIsDisplay1(null);
        }

        if (StringUtils.isEmpty(productionInfo.getTitle2())) {
            productionInfo.setTitle2(null);
        }

        if (StringUtils.isEmpty(productionInfo.getContent2())) {
            productionInfo.setContent2(null);
        }

        if (StringUtils.isEmpty(productionInfo.getIsDisplay2())) {
            productionInfo.setIsDisplay2(null);
        }


        if (StringUtils.isEmpty(productionInfo.getTitle3())) {
            productionInfo.setTitle3(null);
        }

        if (StringUtils.isEmpty(productionInfo.getContent3())) {
            productionInfo.setContent3(null);
        }

        if (StringUtils.isEmpty(productionInfo.getIsDisplay3())) {
            productionInfo.setIsDisplay3(null);
        }


        if (StringUtils.isEmpty(productionInfo.getTitle4())) {
            productionInfo.setTitle4(null);
        }

        if (StringUtils.isEmpty(productionInfo.getContent4())) {
            productionInfo.setContent4(null);
        }

        if (StringUtils.isEmpty(productionInfo.getIsDisplay4())) {
            productionInfo.setIsDisplay4(null);
        }

        if (StringUtils.isEmpty(productionInfo.getTitle5())) {
            productionInfo.setTitle5(null);
        }

        if (StringUtils.isEmpty(productionInfo.getContent5())) {
            productionInfo.setContent5(null);
        }

        if (StringUtils.isEmpty(productionInfo.getIsDisplay5())) {
            productionInfo.setIsDisplay5(null);
        }
        if (StringUtils.isEmpty(productionInfo.getTitle6())) {
            productionInfo.setTitle6(null);
        }

        if (StringUtils.isEmpty(productionInfo.getContent6())) {
            productionInfo.setContent6(null);
        }

        if (StringUtils.isEmpty(productionInfo.getIsDisplay6())) {
            productionInfo.setIsDisplay6(null);
        }

        if (StringUtils.isEmpty(productionInfo.getTitle7())) {
            productionInfo.setTitle7(null);
        }

        if (StringUtils.isEmpty(productionInfo.getContent7())) {
            productionInfo.setContent7(null);
        }

        if (StringUtils.isEmpty(productionInfo.getIsDisplay7())) {
            productionInfo.setIsDisplay7(null);
        }


        if (StringUtils.isEmpty(productionInfo.getTitle8())) {
            productionInfo.setTitle8(null);
        }

        if (StringUtils.isEmpty(productionInfo.getContent8())) {
            productionInfo.setContent8(null);
        }

        if (StringUtils.isEmpty(productionInfo.getIsDisplay8())) {
            productionInfo.setIsDisplay8(null);
        }

        if (StringUtils.isEmpty(productionInfo.getTitle9())) {
            productionInfo.setTitle9(null);
        }

        if (StringUtils.isEmpty(productionInfo.getContent9())) {
            productionInfo.setContent9(null);
        }

        if (StringUtils.isEmpty(productionInfo.getIsDisplay9())) {
            productionInfo.setIsDisplay9(null);
        }
        if (StringUtils.isEmpty(productionInfo.getTitle10())) {
            productionInfo.setTitle10(null);
        }

        if (StringUtils.isEmpty(productionInfo.getContent10())) {
            productionInfo.setContent10(null);
        }

        if (StringUtils.isEmpty(productionInfo.getIsDisplay10())) {
            productionInfo.setIsDisplay10(null);
        }

        int count = productionInfoService.insertOrUpdate(productionInfo, getLoginUser(), getConnection());
        if (count != 1) {
            getResult().setMessage("操作失败");
        }
        return SUCCESS;
    }



    //删除的方法
    public String delete() throws Exception {

        productionInfo = HttpUtils.getInstanceFromRequest(getRequest(), "productionInfo", ProductionInfoPO.class);

        productionInfoService.delete(productionInfo, getLoginUser(), getConnection());
        return SUCCESS;
    }

    /**
     * 供移动端使用，获取大额产品的介绍内容(6-8)，包括title和content
     * @return
     * @throws Exception
     */
    public String getProductionInfoContent() throws Exception {
        Connection conn = getConnection();
        HttpServletRequest request = ServletActionContext.getRequest();
        String productionId = HttpUtils.getParameter(request, "productionId");
        // 校验产品ID合法性
        if (StringUtils.isEmpty(productionId)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }

        JSONObject jsonObject = productionInfoService.getProductionInfoContent(productionId, getConnection());

        getResult().setReturnValue(jsonObject);

        return SUCCESS;
    }

    /**
     * HOPEWEALTH-1301<br/>
     * 根据产品ID来获取产品信息的productionId和title，供销客移动端使用<br/>
     * test url
     * http://localhost:8080/core/api/production/ProductionInfo_getProductionInfoTitle?productionId=09711FDC753B4F84A67A658FFFCACACE
     *
     * 作者：曾威恺
     * 内容：创建代码
     * 时间：2016年03月23日
     *
     * @return
     * @throws Exception
     */
    public String getProductionInfoTitle() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        String productionId = HttpUtils.getParameter(request, "productionId");
        // 校验参数合法性
        if (StringUtils.isEmpty(productionId)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }
        JSONObject jsonObject = productionInfoService.getProductionInfoTitle(productionId, getConnection());

        getResult().setReturnValue(jsonObject);

        return SUCCESS;
    }

    /**
     * 根据产品获取产品详细对象
     * @return
     * @throws Exception
     */
    public String loadDescription() throws Exception {
        String sql="select * from crm_productioninfo where state =0 AND ProductionId ='"+ Database.encodeSQL(productionInfo.getProductionId()) + "'";
        List li =  MySQLDao.query(sql,productionInfo.getClass(),null,getConnection());
        if(li.size()>0) {
            productionInfo=(ProductionInfoPO)li.get(0);
        }
        getResult().setReturnValue(productionInfo.toJsonObject4Form());
        return SUCCESS;
    }

    public String getDesType() {
        return desType;
    }

    public void setDesType(String desType) {
        this.desType = desType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProd_description() {
        return prod_description;
    }

    public void setProd_description(String prod_description) {
        this.prod_description = prod_description;
    }

    public int getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(int isDisplay) {
        this.isDisplay = isDisplay;
    }

    public ProductionInfoPO getProductionInfo() {
        return productionInfo;
    }
    public void setProductionInfo(ProductionInfoPO productionInfo) {
        this.productionInfo = productionInfo;
    }

    public ProductionInfoVO getProductionInfoVO() {
        return productionInfoVO;
    }
    public void setProductionInfoVO(ProductionInfoVO productionInfoVO) {
        this.productionInfoVO = productionInfoVO;
    }

    public ProductionInfoService getProductionInfoService() {
        return productionInfoService;
    }

    public void setProductionInfoService(ProductionInfoService productionInfoService) {
        this.productionInfoService = productionInfoService;
    }
}
