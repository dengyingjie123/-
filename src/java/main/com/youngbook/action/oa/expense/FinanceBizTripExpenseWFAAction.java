package com.youngbook.action.oa.expense;

import com.youngbook.action.BaseAction;
import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.oa.expense.FinanceBizTripExpenseWFAPO;
import com.youngbook.entity.vo.oa.expense.FinanceBizTripExpenseWFAVO;
import com.youngbook.service.oa.expense.FinanceBizTripExpenseWFAService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class FinanceBizTripExpenseWFAAction extends BaseAction {

    private FinanceBizTripExpenseWFAPO financeBizTripExpenseWFA = new FinanceBizTripExpenseWFAPO ();
    private FinanceBizTripExpenseWFAVO financeBizTripExpenseWFAVO = new FinanceBizTripExpenseWFAVO ();

    private FinanceBizTripExpenseWFAService service = new FinanceBizTripExpenseWFAService ();

    /**
     * 查询出所有的数据
     * 把所有数据查询出来，在后台管理的 datagrid 组件中分页展示
     * 用法：前台的 URL 指向 /core/oa/FinanceBizExpenseWFA_list.action，如未成功，请检查 struts 配置
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     * <p/>
     * 作者：周海鸿
     * 内容：创建申请SQL语句
     * 时间：2015-7-10
     *
     * @return 适用于 easyui 的 JSON
     * @throws Exception
     * @author 邓超
     */
    public String list () throws Exception {

        //获取请求对象
        HttpServletRequest request = getRequest ();

        //创建查询条件对象
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters (request, financeBizTripExpenseWFAVO.getClass ());

        
        QueryType queryType = new QueryType (Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        //获取分页数据
        Pager pager = service.list (financeBizTripExpenseWFAVO, request, getLoginUser (), conditions);

        getResult ().setReturnValue (pager.toJsonObject ());

        return SUCCESS;
    }

     /* 作者：周海鸿
     * 内容：创建等待审批SQL语句
     * 时间：2015-7-10*/


    public String Waitlist () throws Exception {

        //获取请求对象
        HttpServletRequest request = getRequest ();

        //创建查询条件对象
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters (request, financeBizTripExpenseWFAVO.getClass ());

        //获取数据
        Pager pager = service.Waitlist (financeBizTripExpenseWFAVO, request, getLoginUser (), conditions);

        getResult ().setReturnValue (pager.toJsonObject ());

        return SUCCESS;
    }

    /**
     * 作者：周海鸿
     * 内容：创建已完成审批SQL语句
     * 时间：2015-7-10
     */

    public String Participantlist () throws Exception {

        //获取请求对象
        HttpServletRequest request = getRequest ();

        //创建查询条件对象
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters (request, financeBizTripExpenseWFAVO.getClass ());


        //获取数据
        Pager pager = service.Participantlist (financeBizTripExpenseWFAVO, request, getLoginUser (), conditions);

        getResult ().setReturnValue (pager.toJsonObject ());

        return SUCCESS;
    }

    /**
     * 新增或修改验证码的数据
     * 把页面请求过来的数据进行持久化，如果存在 ID，则修改，否则会新增一条记录
     * 修改是把原来的数据状态修改为 update 状态的代号，再新增一条状态为当前的新记录
     * 用法：前台的 URL 指向 /core/oa/FinanceBizTripWFA_insertOrUpdate.action，如未成功，请检查 struts 配置
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @return 适用于 easyui 的 JSON，类似 {code:100, message:'操作成功'}
     * @throws Exception
     * @author 邓超
     */
    public String insertOrUpdate () throws Exception {
        int count = service.insertOrUpdate (financeBizTripExpenseWFA, financeBizTripExpenseWFAVO, getLoginUser (), getConnection ());
        if ( count != 1 ) {
            getResult ().setMessage ("操作失败");
        }
        return SUCCESS;
    }

    /**
     * 查询单条数据
     * 通过 ID 或 SID，查询出一条记录
     * 用法：前台的 URL 指向 /core/oa/FinanceBizTripWFA_load.action，如未成功，请检查 struts 配置
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @return 适用于 easyui 的 JSON
     * @throws Exception
     * @author 邓超
     */
    public String load () throws Exception {
        financeBizTripExpenseWFA = service.loadFinanceBizTripExpenseWFAPO (financeBizTripExpenseWFA.getId ());
        getResult ().setReturnValue (financeBizTripExpenseWFA.toJsonObject4Form ());
        return SUCCESS;
    }

    /**
     * 删除一条数据
     * 通过 ID 或 SID，把查询出来的记录状态设置为已删除，再新增一条状态为当前的新记录
     * 用法：前台的 URL 指向 /core/oa/FinanceBizTripExpenseWFA_delete.action，如未成功，请检查 struts 配置
     * <p/>
     * 作者：邓超
     * 内容：创建代码
     * 时间：2015-6-3
     *
     * @return 适用于 easyui 的 JSON，类似 {code:100, message:'操作成功'}
     * @throws Exception
     * @author 邓超
     */
    public String delete () throws Exception {
        service.delete (financeBizTripExpenseWFA, getLoginUser (), getConnection ());
        return SUCCESS;
    }

    public FinanceBizTripExpenseWFAVO getFinanceBizTripExpenseWFAVO () {
        return financeBizTripExpenseWFAVO;
    }

    public void setFinanceBizTripExpenseWFAVO (FinanceBizTripExpenseWFAVO financeBizTripExpenseWFAVO) {
        this.financeBizTripExpenseWFAVO = financeBizTripExpenseWFAVO;
    }

    public FinanceBizTripExpenseWFAPO getFinanceBizTripExpenseWFA () {
        return financeBizTripExpenseWFA;
    }

    public void setFinanceBizTripExpenseWFA (FinanceBizTripExpenseWFAPO financeBizTripExpenseWFA) {
        this.financeBizTripExpenseWFA = financeBizTripExpenseWFA;
    }

    public FinanceBizTripExpenseWFAService getService () {
        return service;
    }

    public void setService (FinanceBizTripExpenseWFAService service) {
        this.service = service;
    }

}