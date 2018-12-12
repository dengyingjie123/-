package com.youngbook.action.sale.contract;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObject;
import com.youngbook.common.MyException;
import com.youngbook.common.Pager;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.NumberUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.DepartmentPO;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.production.ProductionPO;
import com.youngbook.entity.po.sale.contract.ContractApplicationPO;
import com.youngbook.entity.po.sale.contract.ContractPO;
import com.youngbook.entity.po.system.UserPositionInfoPO;
import com.youngbook.entity.vo.Sale.contract.ContractApplicationVO;
import com.youngbook.service.production.ProductionService;
import com.youngbook.service.sale.contract.ContractService;
import com.youngbook.service.system.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：zhouhaihong
 * 创建时间：2015/12/23
 * 描述：
 * ContractApplicationAction: 销售合同申请action
 */
public class ContractApplicationAction extends BaseAction {
    //销售合同业务类
    @Autowired
    private ContractService contractService;
    @Autowired
    private ProductionService productionService;
    //销售合同申请对象
    private ContractApplicationPO contractApplicationPO = new ContractApplicationPO();
    private ContractApplicationVO contractApplicationVO = new ContractApplicationVO();

    /**
     * 新建一个销售合同申请记录
     * 获取数据库连接
     * 获取当前操作员
     * 获取销售合同的产品编号
     * 获取销售合同的份数
     * 调用业务类的 newApplication 生成申请记录
     * 返回影响行数。
     *
     * @return
     * @throws Exception
     */
    public String newApplicationContract() throws Exception {

        String beginNumber = getHttpRequestParameter("beginNumber");

        if (StringUtils.isEmpty(beginNumber)) {
            MyException.newInstance("开始编号不能为空").throwException();
        }

        String productionId = contractApplicationPO.getProductionId();
        int number = contractApplicationPO.getCounts();
        Connection conn = getConnection();
        UserPO applicationUser = getLoginUser();
        UserPositionInfoPO userPositionInfoPO = Config.getLoginUserPositionInofInSession(getRequest());
        contractService.newApplication(productionId, number, applicationUser, userPositionInfoPO, conn);

        return SUCCESS;
    }

    /**
     * 更新用户申请数据
     *
     * @return
     * @throws Exception
     */
    public String UpdateApplicationContract() throws Exception {
        Connection conn = getConnection();
        UserPO applicationUser = getLoginUser();
        contractService.updateApplication(contractApplicationPO, applicationUser, conn);
        return SUCCESS;
    }

    /**
     * 删除用户申请数据
     *
     * @return
     * @throws Exception
     */
    public String DeleteApplicationContract() throws Exception {
        Connection conn = getConnection();
        UserPO applicationUser = getLoginUser();
        UserPositionInfoPO userPositionInfoPO = Config.getLoginUserPositionInofInSession(getRequest());
        DepartmentService departmentService = new DepartmentService();
        DepartmentPO departmentPO = departmentService.load(userPositionInfoPO.getDepartmentId(), conn);
        int count = contractService.deleteApplication(contractApplicationPO, applicationUser, departmentPO, conn);
        if (count == 0) {
            getResult().setReturnValue("false");
            MyException.deal(MyException.newInstance("销售合同删除失败"));
        }
        getResult().setReturnValue("true");
        return SUCCESS;
    }

    /**
     * 验证销售合同id，获取销售合同
     *
     * @return
     * @throws Exception
     */
    public String loadApplicationContact() throws Exception {
        String id = contractApplicationPO.getId();
        if (StringUtils.isEmpty(id)) {
            //获取销售合同申请数据失败
            MyException.deal(MyException.newInstance("获取销售合同申请数据失败"));
        }
        contractApplicationPO.setState(Config.STATE_CURRENT);
        contractApplicationPO = MySQLDao.load(contractApplicationPO, ContractApplicationPO.class);
        if (contractApplicationPO == null) {
            MyException.deal(MyException.newInstance("获取销售合同申请数据失败"));
        }
        getResult().setReturnValue(contractApplicationPO.toJsonObject4Form());
        return SUCCESS;
    }


    /**
     * 获取销售和同事申请的列表数据
     * 调用 业务类的 listApplications 获取销售合同列表数据
     *
     * @return
     * @throws Exception
     */
    public String listApplications() throws Exception {
        HttpServletRequest request = getRequest();
        Connection conn = getConnection();
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request, ContractApplicationVO.class);
        //获取当前操作员的部门
        UserPositionInfoPO userPositionInfoPO = Config.getLoginUserPositionInofInSession(getRequest());
        Pager page = contractService.listApplications(contractApplicationVO, userPositionInfoPO.getDepartmentId(), conditions, request, conn);
        getResult().setReturnValue(page.toJsonObject());
        return SUCCESS;
    }


    /**
     * 审批销售合同申请
     * 获取销售合同申请的状态
     * 调用业务类的checkApplication 审批合同
     *
     * @return
     * @throws Exception
     */
    public String checkApplication() throws Exception {

        String beginNumber = getHttpRequestParameter("beginNumber");

        if (StringUtils.isEmpty(beginNumber)) {
            MyException.newInstance("开始编号不能为空").throwException();
        }

        int passOrNot = contractApplicationPO.getCheckState();
        Connection conn = getConnection();
        UserPO checkUser = getLoginUser();


        UserPositionInfoPO userPositionInfoPO = Config.getLoginUserPositionInofInSession(getRequest());
        DepartmentService departmentService = new DepartmentService();
        DepartmentPO departmentPO = departmentService.load(userPositionInfoPO.getDepartmentId(), conn);


        contractService.checkApplication(contractApplicationPO, passOrNot, NumberUtils.parse2Int(beginNumber), checkUser, departmentPO, conn);
        return SUCCESS;
    }


    /**
     * @description 生成合同号
     * 
     * @author 苟熙霖 
     * 
     * @date 2018/12/12 16:34
     * @param 
     * @return java.lang.String
     * @throws Exception
     */
    public String createContractNum() throws Exception {

        String contracts = getHttpRequestParameter("contracts");
        String productionId = getHttpRequestParameter("productionId");
        String counts = getHttpRequestParameter("counts");
        String[] split = contracts.split("-");
        long begin = Long.valueOf(split[0]);


        ArrayList list = new ArrayList();
        Pager pager = new Pager();


        ProductionPO productionPO = productionService.loadProductionById(productionId, getConnection());
        int contractCopies = productionPO.getContractCopies();




        String contractNum ;
        for(int i =0 ; i<=Integer.valueOf(counts) ; i++){

            for(int j = 1 ; j<=contractCopies ; j++){
                contractNum = String.valueOf(begin)+ "-" + String.valueOf(j);
                ContractPO contractPO = new ContractPO();
                contractPO.setContractNo(contractNum);
                list.add(contractPO);
            }
            
            begin++;
        }
        pager.setData(list);



        getResult().setReturnValue(pager);
        return SUCCESS;
    }


    public ContractApplicationPO getContractApplicationPO() {
        return contractApplicationPO;
    }

    public void setContractApplicationPO(ContractApplicationPO contractApplicationPO) {
        this.contractApplicationPO = contractApplicationPO;
    }

    public ContractApplicationVO getContractApplicationVO() {
        return contractApplicationVO;
    }

    public void setContractApplicationVO(ContractApplicationVO contractApplicationVO) {
        this.contractApplicationVO = contractApplicationVO;
    }
}
