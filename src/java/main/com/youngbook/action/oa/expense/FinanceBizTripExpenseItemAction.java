package com.youngbook.action.oa.expense;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.entity.po.oa.expense.FinanceBizTripExpenseItemPO;
import com.youngbook.entity.vo.oa.expense.FinanceBizTripExpenseItemVO;
import com.youngbook.service.oa.expense.FinanceBizTripExpenseItemService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class FinanceBizTripExpenseItemAction extends BaseAction {

    private FinanceBizTripExpenseItemPO financeBizTripExpenseItem = new FinanceBizTripExpenseItemPO();
    private FinanceBizTripExpenseItemVO financeBizTripExpenseItemVO = new FinanceBizTripExpenseItemVO();

    private FinanceBizTripExpenseItemService service = new FinanceBizTripExpenseItemService();

    /**
     * 查询出所有的数据
     * 把所有数据查询出来，在后台管理的 datagrid 组件中分页展示
     * 用法：前台的 URL 指向 /core/oa/FinanceBizTripExpenseItem_list.action，如未成功，请检查 struts 配置
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @author 邓超
     * @return 适用于 easyui 的 JSON
     * @throws Exception
     */
    public String list() throws Exception {
        //获取请求对象
        HttpServletRequest request = getRequest ();
        //创建查询条件对象
        List<KVObject> conditions = new ArrayList<KVObject> ();
        

        //获取数据猎豹
        Pager pager = service.list (financeBizTripExpenseItemVO, conditions, request);

        getResult().setReturnValue(pager.toJsonObject());

        return SUCCESS;
    }

    /**
     * 新增或修改数据
     * 把页面请求过来的数据进行持久化，如果存在 ID，则修改，否则会新增一条记录
     * 修改是把原来的数据状态修改为 update 状态的代号，再新增一条状态为当前的新记录
     * 用法：前台的 URL 指向 /core/oa/FinanceBizTripExpenseItem_insertOrUpdate.action，如未成功，请检查 struts 配置
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @author 邓超
     * @return 适用于 easyui 的 JSON，类似 {code:100, message:'操作成功'}
     * @throws Exception
     */
    public String insertOrUpdate() throws Exception {
        int count = service.insertOrUpdate(financeBizTripExpenseItem, getLoginUser(), getConnection());
        if (count != 1) {
            getResult().setMessage("操作失败");
        }
        return SUCCESS;
    }

    /**
     * 查询单条数据
     * 通过 ID 或 SID，查询出一条记录
     * 用法：前台的 URL 指向 /core/oa/FinanceBizTripExpenseItem_load.action，如未成功，请检查 struts 配置
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @author 邓超
     * @return 适用于 easyui 的 JSON
     * @throws Exception
     */
    public String load() throws Exception {
        financeBizTripExpenseItem = service.loadFinanceBizTripExpenseItemPO(financeBizTripExpenseItem.getId());
        getResult().setReturnValue(financeBizTripExpenseItem.toJsonObject4Form());
        return SUCCESS;
    }

    /**
     * 2015-6-17
     * 周海鸿
     * 创建一个获取指定编号的费用报销的总金额事件
     * @return
     * @throws Exception
     */
    public String getMoneys () throws Exception{
        //使用类数据编号传送
        getResult().setReturnValue(service.getMoneys(financeBizTripExpenseItem.getExpenseId()));

        return SUCCESS;
    }

    /**
     * 删除一条数据
     * 通过 ID 或 SID，把查询出来的记录状态设置为已删除，再新增一条状态为当前的新记录
     * 用法：前台的 URL 指向 /core/oa/FinanceBizTripExpenseItem.action，如未成功，请检查 struts 配置
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @author 邓超
     * @return 适用于 easyui 的 JSON，类似 {code:100, message:'操作成功'}
     * @throws Exception
     */
    public String delete() throws Exception {
        //删除数据
        service.delete(financeBizTripExpenseItem, getLoginUser(), getConnection());
        return SUCCESS;
    }

    public FinanceBizTripExpenseItemVO getFinanceBizTripExpenseItemVO() {
        return financeBizTripExpenseItemVO;
    }
    public void setFinanceBizTripExpenseItemVO(FinanceBizTripExpenseItemVO financeBizTripExpenseItemVO) {this.financeBizTripExpenseItemVO = financeBizTripExpenseItemVO;}

    public FinanceBizTripExpenseItemPO getFinanceBizTripExpenseItem() {
        return financeBizTripExpenseItem;
    }
    public void setFinanceBizTripExpenseItem(FinanceBizTripExpenseItemPO financeBizTripExpenseItem) {this.financeBizTripExpenseItem = financeBizTripExpenseItem;}

    public FinanceBizTripExpenseItemService getService() {
        return service;
    }
    public void setService(FinanceBizTripExpenseItemService service) {
        this.service = service;
    }

}
