package com.youngbook.action.sale;

import com.youngbook.action.BaseAction;
import com.youngbook.annotation.Permission;
import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.DepartmentPO;
import com.youngbook.entity.po.sale.SalemanGroupPO;
import com.youngbook.entity.vo.Sale.SalemanGroupVO;
import com.youngbook.entity.vo.Sale.SalesManVO;
import com.youngbook.service.sale.SalemanGroupService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.bouncycastle.ocsp.Req;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SalemanGroupAction extends BaseAction {

    //实例化PO、VO、Servlet 类对象
    private SalemanGroupPO salemanGroup = new SalemanGroupPO();
    private SalemanGroupVO salemanGroupVO = new SalemanGroupVO();

    @Autowired
    SalemanGroupService salemanGroupService;

    //创建一个部门对下岗
    private DepartmentPO department = new DepartmentPO();

    //销售类
    private SalesManVO salemanVO = new SalesManVO();

    private String sort = new String();//需要排序的列名
    private String order = new String();

    /**
     * 获取数据列表
     *
     * @return
     * @throws Exception
     */
    public String list() throws Exception {
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT ");
        sbSQL.append("	cr.Sid,");
        sbSQL.append("	cr.Id,");
        sbSQL.append("	cr.State,");
        sbSQL.append("	cr.OperatorId,");
        sbSQL.append("	cr.OperateTime,");
        sbSQL.append("	cr.`Name`,");
        sbSQL.append("	cr.Description,");
        sbSQL.append("	cr.DepartmentId,");
        sbSQL.append("	cr.areaId,");
        sbSQL.append("	kvArea.V as  groupArea,");
        sbSQL.append("	ad.`name` AS departmentName");
        sbSQL.append(" FROM");
        sbSQL.append("	system_department ad,");
        sbSQL.append("	crm_salemangroup cr");
        sbSQL.append(" LEFT JOIN system_kv kvArea ON kvArea.k = cr.areaId and kvArea.GroupName='salesgroup_area' ");
        sbSQL.append(" WHERE");
        sbSQL.append("	1 = 1");
        sbSQL.append(" AND cr.state = 0");
        sbSQL.append(" AND ad.id = cr.DepartmentId");
        HttpServletRequest request = getRequest();
        List<KVObject> conditions = new ArrayList<KVObject>();
        conditions = MySQLDao.getQueryDatetimeParameters(request, salemanGroupVO.getClass());
        //整型范围查询
        conditions = MySQLDao.getQueryNumberParameters(request, salemanGroupVO.getClass(), conditions);
        //设置排序
        if (!getSort().equals("")) {
            conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, getSort() + " " + getOrder()));
        }
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        //获取分页对象
        Pager pager = Pager.query(sbSQL.toString(), salemanGroupVO, conditions, request, queryType);
        //返回数据
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 获取知道部门的小组下拉列表
     *
     * @return
     * @throws Exception
     */
    public String getDepartmentgroup() throws Exception {
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT ");
        sbSQL.append("	cr.Sid,");
        sbSQL.append("	cr.Id,");
        sbSQL.append("	cr.State,");
        sbSQL.append("	cr.OperatorId,");
        sbSQL.append("	cr.OperateTime,");
        sbSQL.append("	cr.`Name`,");
        sbSQL.append("	cr.Description,");
        sbSQL.append("	cr.DepartmentId,");
        sbSQL.append("	ad.`name` AS departmentName");
        sbSQL.append(" FROM");
        sbSQL.append("	crm_salemangroup cr,");
        sbSQL.append("	system_department ad");
        sbSQL.append(" WHERE");
        sbSQL.append("	1 = 1");
        sbSQL.append(" AND cr.state = 0");
        sbSQL.append(" AND ad.id = cr.DepartmentId");
        sbSQL.append(" AND cr.DepartmentId='" + salemanGroupVO.getDepartmentId() + "'");

        //获取数据集合
        List list = MySQLDao.query(sbSQL.toString(), salemanGroupVO.getClass(), null);
        //迭代数据
        Iterator it = list.iterator();
        JSONArray array = new JSONArray();
        JSONObject jobj = new JSONObject();
        while (it.hasNext()) {
            salemanGroupVO = (SalemanGroupVO) it.next();
            jobj.element("id", salemanGroupVO.getId());
            jobj.element("text", salemanGroupVO.getName());
            array.add(jobj);
        }
        getResult().setReturnValue(array);
        return SUCCESS;
    }

    /**
     * 根据销售小组获取小组的人员下拉列表
     *
     * @return
     * @throws Exception
     */
    public String getDepartmentSaleman() throws Exception {
        //获取小组列表
        List list = salemanGroupService.saleManList(salemanGroup.getId(), getConnection());
        //迭代数据
        Iterator it = list.iterator();
        JSONArray array = new JSONArray();
        JSONObject jobj = new JSONObject();
        while (it.hasNext()) {
            salemanVO = (SalesManVO) it.next();
            jobj.element("id", salemanVO.getUserId());
            jobj.element("text", salemanVO.getUserName());
            array.add(jobj);

        }
        getResult().setReturnValue(array);

        return SUCCESS;
    }

    /**
     * 添加过更新数据
     *
     * @return
     * @throws Exception
     */
    @Permission(require = "系统管理-销售组管理-新增")
    public String insertOrUpdate() throws Exception {
        //判断小组数据是否为null
        if (StringUtils.isEmpty(salemanGroup.getDepartmentId())) {
            getResult().setMessage("销售小组数据错误");
            MyException.deal(new Exception("销售小组数据错误"));
        }


        //地址销售小组 编号
        department.setId(salemanGroup.getDepartmentId());
        //判断用户选择的部门是否可以添加销售小组
        if (!isInstallSalemanGroups(department)) {

            getResult().setMessage("所选部门无法添加销售小组,添加失败");
            MyException.deal(new Exception("所选部门无法添加销售小组"));
        }
        int count = salemanGroupService.insertOrUpdate(salemanGroup, getLoginUser(), getConnection());
        if (count != 1) {
            getResult().setMessage("操作失败");
        }
        return SUCCESS;
    }

    /**
     * 添加小组成员
     *
     * @return
     * @throws Exception
     */
    public String insertSaleMan() throws Exception {
        int count = salemanGroupService.insertOrUpdate(salemanGroup, getLoginUser(), getConnection());
        if (count != 1) {
            getResult().setMessage("操作失败");
        }
        return SUCCESS;
    }


    /**
     * 获取单条数据
     *
     * @return
     * @throws Exception
     */
    @Permission(require = "系统管理-销售组管理-修改")
    public String load() throws Exception {
        salemanGroup = salemanGroupService.loadSalemanGroupPO(salemanGroup.getId(), getConnection());
        getResult().setReturnValue(salemanGroup.toJsonObject4Form());
        return SUCCESS;
    }

    /**
     * 删除数据
     *
     * @return
     * @throws Exception
     */
    @Permission(require = "系统管理-销售组管理-删除")
    public String delete() throws Exception {
        Connection conn = getConnection();
        //判数据是否能删除
        if (salemanGroupService.isDelete(salemanGroup, conn)) {
            salemanGroupService.delete(salemanGroup, getLoginUser(), conn);
        }else{
          getResult().setMessage("该小组无法删除");
        }

        return SUCCESS;
    }

    /**
     * 获取销售成员列表数据
     *
     * @return
     */
    public String getSaleMans() throws Exception {
        //判断小组ID有效性
        if (StringUtils.isEmpty(salemanGroup.getId())) {
            MyException.newInstance("销售小组不存在").throwException();
        }

        //获取小组列表
        Pager pager = salemanGroupService.saleManPager(salemanGroup.getId(), getConnection());
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }


    /**
     * 获取该小租所不包含的人员
     *
     * @return
     */
    public String getSaleManNoInList() throws Exception {

        SalemanGroupPO salemanGroup = HttpUtils.getInstanceFromRequest(getRequest(), "salemanGroup", SalemanGroupPO.class);

        String salesmanName = getHttpRequestParameter("salemanVO.userName");

        //判断数据有效性
        if (StringUtils.isEmpty(salemanGroup.getId())) {
            MyException.newInstance("销售小组不存在").throwException();
        }

        Pager pager = Pager.getInstance(getRequest());

        pager = salemanGroupService.saleManPager(salemanGroup.getId(), salesmanName, pager.getCurrentPage(), pager.getShowRowCount(), getConnection());
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 判断选中的部门是否可以添加销售小组
     *
     * @return
     * @throws Exception
     */
    public String isInstalSalemanGroup() throws Exception {
        boolean temp = isInstallSalemanGroups(department);
        getResult().setReturnValue(temp);
        return SUCCESS;
    }

    /**
     * 根据部门判断该部门是否可以添加销售小组
     *
     * @param department
     * @return
     * @throws Exception
     */
    public boolean isInstallSalemanGroups(DepartmentPO department) throws Exception {
        //判断返回的数据是否为null
        if (department == null) {
            throw new Exception("获取部门组织错误");
        }
        //获取部门组织对象
        department = MySQLDao.load(department, DepartmentPO.class);

        //判断返回的数据是否为null
        if (department == null) {
            throw new Exception("获取部门组织错误");
        }

        //获取允许添加销售小组的
        List<String> list = Config.getermissionSaleManGroup();
        boolean temp = false;

        //循环判断是否存在可以添加的部门编号
        for (int i = 0; i < list.size(); i++) {

            //判断集合中是否存对应的编号
            if (list.get(i).equals(department.getTypeID())) {
                temp = true;
                break;
            }
        }
        return temp;
    }


    public SalemanGroupPO getSalemanGroup() {
        return salemanGroup;
    }

    public void setSalemanGroup(SalemanGroupPO salemanGroup) {
        this.salemanGroup = salemanGroup;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public SalesManVO getSalemanVO() {
        return salemanVO;
    }

    public void setSalemanVO(SalesManVO salemanVO) {
        this.salemanVO = salemanVO;
    }

    public SalemanGroupVO getSalemanGroupVO() {

        return salemanGroupVO;
    }

    public void setSalemanGroupVO(SalemanGroupVO salemanGroupVO) {
        this.salemanGroupVO = salemanGroupVO;
    }

    public DepartmentPO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentPO department) {
        this.department = department;
    }
}
