package com.youngbook.action.production;

import com.youngbook.action.BaseAction;
import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.production.ProductionCompositionPO;
import com.youngbook.entity.po.sale.ProductionCommissionType;
import com.youngbook.entity.vo.production.ProductionCompositionVO;
import com.youngbook.service.production.ProductionCompositionService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


//todo 文件大小写问题
public class ProductionCompositionAction extends BaseAction {

    private ProductionCompositionPO productionComposition = new ProductionCompositionPO();
    private ProductionCompositionVO productionCompositionVO = new ProductionCompositionVO();
    @Autowired
    ProductionCompositionService productionCompositionService;
   // private ProductioncompositionService service = new ProductioncompositionService();

    //添加，修改
    public String insertOrUpdate() throws Exception {
        productionComposition = HttpUtils.getInstanceFromRequest(getRequest(), "productionComposition", ProductionCompositionPO.class);

        productionCompositionService.insertOrUpdate(productionComposition, getLoginUser(), getConnection());
        return SUCCESS;
    }

    // 删除
    public String delete() throws Exception {

        productionComposition = HttpUtils.getInstanceFromRequest(getRequest(), "productionComposition", ProductionCompositionPO.class);

        productionCompositionService.delete(productionComposition, getLoginUser(), getConnection());
        return SUCCESS;
    }

    // 读取
    public String load() throws Exception {

        productionComposition = HttpUtils.getInstanceFromRequest(getRequest(), "productionComposition", ProductionCompositionPO.class);

        productionComposition.setState(Config.STATE_CURRENT);
        productionComposition = MySQLDao.load(productionComposition, ProductionCompositionPO.class);
        getResult().setReturnValue(productionComposition.toJsonObject4Form());
        return SUCCESS;
    }

    /**
     * HOPEWEALTH-1299<br/>
     * 根据产品ID来获取产品构成的接口（包含了最大返佣率）<br/>
     * HOPEWEALTH-1319<br/>
     * 修改获取最高返佣的方式<br/>
     * test url<br/>
     * http://localhost:8080/core/api/production/Productioncomposition_getCompositions?productionId=09711FDC753B4F84A67A658FFFCACACE&&commissionType=1&&currentPage=1&&showRowCount=20
     *
     * @return
     * @throws Exception
     */
    //@Security(needToken = true)
    public String getCompositions() throws Exception {
        Connection conn = this.getConnection();
        HttpServletRequest request = this.getRequest();
        String productionId = HttpUtils.getParameter(request, "productionId");
        String commissionType = HttpUtils.getParameter(request, "commissionType");
        String currentPage = HttpUtils.getParameter(request, "currentPage");
        String showRowCount = HttpUtils.getParameter(request, "showRowCount");

        // 校验参数合法性
        if (StringUtils.isEmpty(productionId)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "缺少产品ID").throwException();
        }
        if (StringUtils.isEmpty(commissionType)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "缺少返佣类型").throwException();
        }
        if (StringUtils.isEmpty(currentPage) || StringUtils.isEmpty(showRowCount)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "缺少分页条件").throwException();
        }
        Integer commissionTypeInt = Integer.parseInt(commissionType);
        Integer currentPageInt = Integer.parseInt(currentPage);
        Integer showRowCountInt = Integer.parseInt(showRowCount);
        if (commissionTypeInt != ProductionCommissionType.STANDARD && commissionTypeInt != ProductionCommissionType.ADJUSTED) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_CORRECT, "参数不正确").throwException();
        }

        List<ProductionCompositionVO> list = productionCompositionService.listCompositions(productionId, currentPageInt, showRowCountInt, conn);

        getResult().setReturnValue(list);
        return SUCCESS;
    }

    /**
     * 获取产品构成接口
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年3月16日
     *
     * @return
     * @throws Exception
     */
    public String listCompositions() throws Exception {


        productionCompositionVO = HttpUtils.getInstanceFromRequest(getRequest(), "productionCompositionVO", ProductionCompositionVO.class);

        // 获取请求对象
        HttpServletRequest request = this.getRequest();
        // 获取数据库连接
        Connection conn = getConnection();

        // 获取参数
        String productionId = HttpUtils.getParameter(request, "productionId");
        // 校验参数合法性
        if(StringUtils.isEmpty(productionId)) {
            MyException.newInstance(ReturnObjectCode.PUBLIC_PARAMETER_NOT_COMPLETE, "参数不完整").throwException();
        }

        // 构建查询实体
        List<KVObject> conditions = new ArrayList<KVObject>();
        conditions.add(new KVObject("productionId", " in ('"+ Database.encodeSQL(productionId)+ "')"));

        Pager pager = Pager.getInstance(request);

        // 查询
        pager = productionCompositionService.list(productionCompositionVO, conditions, pager.getCurrentPage(), pager.getShowRowCount(), conn);
        getResult().setReturnValue(pager.toJsonObject());

        return SUCCESS;
    }

    // 列出数据
    public String list() throws Exception {
        HttpServletRequest request = getRequest();
        Connection conn = getConnection();
        List<KVObject> conditions =new ArrayList<KVObject>();

        productionComposition = HttpUtils.getInstanceFromRequest(getRequest(), "productionComposition", ProductionCompositionPO.class);

        conditions.add(new KVObject("productionId", " in ('"+ Database.encodeSQL(productionComposition.getProductionId() )+ "')"));

        Pager pager = Pager.getInstance(request);
        pager = productionCompositionService.list(productionCompositionVO, conditions, pager.getCurrentPage(), pager.getShowRowCount(), conn);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    public ProductionCompositionPO getProductionComposition() {
        return productionComposition;
    }

    public void setProductionComposition(ProductionCompositionPO productionComposition) {
        this.productionComposition = productionComposition;
    }

    public ProductionCompositionVO getProductionCompositionVO() {
        return productionCompositionVO;
    }

    public void setProductionCompositionVO(ProductionCompositionVO productionCompositionVO) {
        this.productionCompositionVO = productionCompositionVO;
    }

    public ProductionCompositionService getProductionCompositionService() {
        return productionCompositionService;
    }

    public void setProductionCompositionService(ProductionCompositionService productionCompositionService) {
        this.productionCompositionService = productionCompositionService;
    }
}
