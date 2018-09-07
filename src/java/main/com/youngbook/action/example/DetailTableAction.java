package com.youngbook.action.example;

import com.youngbook.action.BaseAction;
import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.entity.po.example.DetailTablePO;
import com.youngbook.entity.vo.example.DetailTableVO;
import com.youngbook.service.example.DetailTableService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class DetailTableAction extends BaseAction {

    private DetailTablePO detailTable = new DetailTablePO();
    private DetailTableVO detailTableVO = new DetailTableVO();
    private DetailTableService service = new DetailTableService();

    /**
     * 获取
     * @return
     * @throws Exception
     */
    public String list() throws Exception {
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("SELECT ed.* FROM example_detailtable ed,example_maintable em WHERE 1=1 AND ed.State=0 AND em.State = 0 AND em.Id = ed.MainId");
        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions =new ArrayList<KVObject>();
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        Pager pager = Pager.query(sbSQL.toString(), detailTableVO, conditions, request, queryType);
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 加载
     * @return
     * @throws Exception
     */
    public String load() throws Exception {
        detailTable = service.loadDetailTablePO(detailTable.getId());
        getResult().setReturnValue(detailTable.toJsonObject4Form());
        return SUCCESS;
    }

    /**
     * 新增或更新
     * @return
     * @throws Exception
     */
    public String insertOrUpdate() throws Exception {
        int count = service.insertOrUpdate(detailTable, getLoginUser(), getConnection());
        if (count != 1) {
            getResult().setMessage("操作失败");
        }
        return SUCCESS;
    }

    /**
     * 删除
     * @return
     * @throws Exception
     */
    public String delete() throws Exception {
        service.delete(detailTable, getLoginUser(), getConnection());
        return SUCCESS;
    }

    public DetailTableVO getDetailTableVO() {
        return detailTableVO;
    }
    public void setDetailTableVO(DetailTableVO detailTableVO) {
        this.detailTableVO = detailTableVO;
    }

    public DetailTablePO getDetailTable() {
        return detailTable;
    }
    public void setDetailTable(DetailTablePO detailTable) {
        this.detailTable = detailTable;
    }

    public DetailTableService getService() {
        return service;
    }
    public void setService(DetailTableService service) {
        this.service = service;
    }

}
