package com.youngbook.action.sale;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObject;
import com.youngbook.entity.po.sale.SalemanSalemangroupPO;
import com.youngbook.service.sale.SalemanSalemangroupService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class SalemanSalemangroupAction extends BaseAction {

    private SalemanSalemangroupPO saleManSaleManGroup = new SalemanSalemangroupPO();


    @Autowired
    SalemanSalemangroupService salemanSalemangroupService;

    /**
     * 添加数据
     * @return
     * @throws Exception
     */
    public String Insert() throws Exception{
        //判断该销售是否已经有分配销售组，如果有则defaultGroup为0（不是默认销售组）没有则defaultGroup为1（是默认销售组）
        List<SalemanSalemangroupPO> salemanSalemangroups = salemanSalemangroupService.getSalemanSalemangroupsBySalemanId(saleManSaleManGroup.getSaleManId(), getConnection());
        if (salemanSalemangroups.size() > 0){
            saleManSaleManGroup.setDefaultGroup(0);
        }else {
            saleManSaleManGroup.setDefaultGroup(1);
        }

        int count = salemanSalemangroupService.insert(saleManSaleManGroup, getConnection());
        if (count != 1) {
            getResult().setMessage("操作失败");
        }
        return SUCCESS;
    }

    /**
     * 添加数据
     * @return
     * @throws Exception
     */
    public String delete() throws Exception{
        int count = salemanSalemangroupService.delete(saleManSaleManGroup, getConnection());
        if (count != 1) {
            getResult().setMessage("操作失败");
        }
        return SUCCESS;
    }

    /**
     * 获取单条数
     * @return
     * @throws Exception
     */
    public String load()throws Exception{
        List<KVObject> conditions = new ArrayList<KVObject>();
        saleManSaleManGroup = salemanSalemangroupService.load(saleManSaleManGroup,conditions,getConnection());
        getResult().setReturnValue(saleManSaleManGroup.toJsonObject());
        return SUCCESS;
    }

    /**
     * 修改小组成员身份为小组负责人
     * @return
     * @throws Exception
     */
    public String UpdateSaleManStatusFor2() throws Exception {
        saleManSaleManGroup.setSaleManStatus(2);
        int count = salemanSalemangroupService.updateSaleMan(saleManSaleManGroup, getConnection());
        if (count != 1) {
            getResult().setMessage("操作失败");
        }
        return SUCCESS;
    }

    /**
     * 设置销售的默认销售组
     * @return
     * @throws Exception
     */
    public String UpdateDefaultGroup() throws Exception {
        //设置默认销售组为1（是）
        saleManSaleManGroup.setDefaultGroup(1);

        //调用service修改数据
        int count = salemanSalemangroupService.updateDefaultGroup(saleManSaleManGroup, getConnection());
        if(count != 1) {
            getResult().setMessage("设置默认销售组失败");
            return SUCCESS;
        }

        //返回结果
        getResult().setMessage("设置默认销售组成功");
        return SUCCESS;
    }

    /**
     * 修改小组成员身为普通销售人员
     * @return
     * @throws Exception
     */
    public String UpdateSaleManStatusFor1() throws Exception {
        saleManSaleManGroup.setSaleManStatus(1);
        int count = salemanSalemangroupService.updateSaleMan(saleManSaleManGroup, getConnection());
        if (count != 1) {
            getResult().setMessage("操作失败");
        }
        return SUCCESS;
    }

    public SalemanSalemangroupPO getSaleManSaleManGroup() {
        return saleManSaleManGroup;
    }
    public void setSaleManSaleManGroup(SalemanSalemangroupPO saleManSaleManGroup) {this.saleManSaleManGroup = saleManSaleManGroup;}

}
