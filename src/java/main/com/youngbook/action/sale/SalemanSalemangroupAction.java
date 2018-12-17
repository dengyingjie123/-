package com.youngbook.action.sale;

import com.youngbook.action.BaseAction;
import com.youngbook.entity.po.sale.SalemanSalemangroupPO;
import com.youngbook.service.sale.SalemanSalemangroupService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SalemanSalemangroupAction extends BaseAction {

    private SalemanSalemangroupPO saleManSaleManGroup = new SalemanSalemangroupPO();

    @Autowired
    SalemanSalemangroupService salemanSalemangroupService;


    /**
     * @description 新增销售分组
     * @author 徐明煜
     * @date 2018/12/14 17:57
     * @param
     * @return java.lang.String
     * @throws Exception
     */
    public String Insert() throws Exception{

        //判断该销售是否已经有分配销售组，如果有则defaultGroup为0（不是默认销售组）没有则defaultGroup为1（是默认销售组）
        List<SalemanSalemangroupPO> salemanSalemangroups = salemanSalemangroupService.listSalemanSalemangroupsPOBySalemanId(saleManSaleManGroup.getSaleManId(), getConnection());
        if (salemanSalemangroups.size() > 0){
            saleManSaleManGroup.setDefaultGroup(0);
        }else {
            saleManSaleManGroup.setDefaultGroup(1);
        }




        //设置销售身份，默认为销售人员
        saleManSaleManGroup.setSaleManStatus(1);
        salemanSalemangroupService.insertOrUpdate(saleManSaleManGroup,getLoginUser().getId(),getConnection());

        return SUCCESS;
    }


    /**
     * @description 逻辑删除销售分组
     * @author 徐明煜
     * @date 2018/12/14 17:57
     * @param
     * @return java.lang.String
     * @throws Exception
     */
    public String delete() throws Exception{

        salemanSalemangroupService.delete(saleManSaleManGroup, getLoginUser().getId(), getConnection());
        return SUCCESS;
    }


    /**
     * @description 逻辑删除销售分组
     * @author 徐明煜
     * @date 2018/12/14 17:57
     * @param
     * @return java.lang.String
     * @throws Exception
     */
    public String load()throws Exception{

        saleManSaleManGroup =salemanSalemangroupService.load(saleManSaleManGroup, getConnection());
        getResult().setReturnValue(saleManSaleManGroup.toJsonObject());

        return SUCCESS;
    }


    /**
     * @description 设置销售的默认销售组
     * @author 徐明煜
     * @date 2018/12/14 18:00
     * @param
     * @return java.lang.String
     * @throws Exception
     */
    public String UpdateDefaultGroup() throws Exception {

        //设置默认销售组为1（是）
        saleManSaleManGroup.setDefaultGroup(1);
        salemanSalemangroupService.insertOrUpdate(saleManSaleManGroup, getLoginUser().getId(), getConnection());

        return SUCCESS;
    }

    /**
     * @description 修改小组成员身份为普通销售成员
     * @author 徐明煜
     * @date 2018/12/14 17:59
     * @param
     * @return java.lang.String
     * @throws Exception
     */
    public String UpdateSaleManStatusFor1() throws Exception {

        saleManSaleManGroup.setSaleManStatus(1);
        salemanSalemangroupService.insertOrUpdate(saleManSaleManGroup, getLoginUser().getId(), getConnection());
        return SUCCESS;
    }


    /**
     * @description 修改小组成员身份为小组负责人
     * @author 徐明煜
     * @date 2018/12/14 17:59
     * @param
     * @return java.lang.String
     * @throws Exception
     */
    public String UpdateSaleManStatusFor2() throws Exception {

        saleManSaleManGroup.setSaleManStatus(2);
        salemanSalemangroupService.insertOrUpdate(saleManSaleManGroup, getLoginUser().getId(), getConnection());

        return SUCCESS;
    }

    public SalemanSalemangroupPO getSaleManSaleManGroup() {
        return saleManSaleManGroup;
    }
    public void setSaleManSaleManGroup(SalemanSalemangroupPO saleManSaleManGroup) {this.saleManSaleManGroup = saleManSaleManGroup;}

}
