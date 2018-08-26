package com.youngbook.action.example;

import com.youngbook.action.BaseAction;
import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.example.MainTablePO;
import com.youngbook.entity.vo.example.MainTableVO;
import com.youngbook.service.example.MainTableService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class MainTableAction extends BaseAction {

    private MainTablePO mainTable = new MainTablePO();
    private MainTableVO mainTableVO = new MainTableVO();
    private MainTableService service = new MainTableService();

    private String sort=new String();//需要排序的列名
    private String order = new String();

    /**
     * 获取数据列表
     * @return
     * @throws Exception
     */
    public String list() throws Exception {
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("select * from Example_MainTable");
        sbSQL.append(" where 1=1");
        sbSQL.append(" and state=0");
        HttpServletRequest request = getRequest();
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters(request,mainTableVO.getClass());
        //整型范围查询
        List<KVObject> numberConditions = MySQLDao.getQueryNumberParameters(request, mainTable.getClass(),conditions);
        for (int i = 0; i < numberConditions.size(); i++) {
            conditions.add(numberConditions.get(i));
        }
        //设置排序
        if(!getSort().equals("")) {
            conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY,getSort()+" "+getOrder()));
        }
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        //获取分页对象
        Pager pager = Pager.query(sbSQL.toString(), mainTableVO, conditions, request, queryType);
        //返回数据
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 添加过更新数据
     * @return
     * @throws Exception
     */
    public String insertOrUpdate() throws Exception {
        int count = service.insertOrUpdate(mainTable, getLoginUser(), getConnection());
        if (count != 1) {
            getResult().setMessage("操作失败");
        }
        return SUCCESS;
    }

    /**
     * 获取单条数据
     * @return
     * @throws Exception
     */
    public String load() throws Exception {
        mainTable = service.loadMainTablePO(mainTable.getId());
        getResult().setReturnValue(mainTable.toJsonObject4Form());
        return SUCCESS;
    }

    /**
     * 删除数据
     * @return
     * @throws Exception
     */
    public String delete() throws Exception {
        service.delete(mainTable, getLoginUser(), getConnection());
        return SUCCESS;
    }

    /**
     * GETSET
     */
    public MainTableVO getMainTableVO() {
        return mainTableVO;
    }
    public void setMainTableVO(MainTableVO mainTableVO) {
        this.mainTableVO = mainTableVO;
    }

    public MainTablePO getMainTable() {
        return mainTable;
    }
    public void setMainTable(MainTablePO mainTable) {
        this.mainTable = mainTable;
    }

    public MainTableService getService() {
        return service;
    }
    public void setService(MainTableService service) {
        this.service = service;
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

}
