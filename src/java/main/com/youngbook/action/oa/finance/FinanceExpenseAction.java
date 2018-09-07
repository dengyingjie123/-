package com.youngbook.action.oa.finance;

import com.youngbook.action.BaseAction;
import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.oa.finance.FinanceExpenseDetailPO;
import com.youngbook.entity.po.wf.FinanceExpensePO;
import com.youngbook.entity.vo.oa.finance.FinanceExpenseVO;
import com.youngbook.service.oa.finance.FinanceExpenseService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Ivan
 * Date: 15-4-2
 * Time: 上午9:31
 * To change this template use File | Settings | File Templates.
 * 修改：周海鸿
 * 内容：完成详细注释
 * 时间：2015年 6月17日 14:24:00
 */
public class FinanceExpenseAction extends BaseAction {

    private FinanceExpensePO financeExpense = new FinanceExpensePO ();
    private FinanceExpenseDetailPO expenseDetail = new FinanceExpenseDetailPO ();
    private FinanceExpenseVO expenseVO = new FinanceExpenseVO ();

    private FinanceExpenseService service = new FinanceExpenseService ();

    /**
     * 添加或修改方法
     *
     * @return
     * @throws Exception
     */
    public String insertOrUpdate () throws Exception {
        //添加并更新数据
        service.insertOrUpdate (financeExpense, expenseVO, getLoginUser (), getConnection ());

        return SUCCESS;
    }

    /**
     * 2015-6-09
     * 海鸿
     * 创建一个获取消费金额的方法
     *
     * @return
     * @throws Exception
     */
    public String getFinanceExpenseMoney () throws Exception {
        //获取消费金额
        String money = service.getFinanceExpenseMoney (financeExpense.getExpenseId ());

        getResult ().setReturnValue (money);

        return SUCCESS;
    }


    /**
     * 将数据状态修改为删除状态
     *
     * @return
     * @throws Exception
     */
    public String delete () throws Exception {
        //删除数据
        service.delete (financeExpense, getLoginUser (), getConnection ());

        return SUCCESS;
    }

    /**
     * 根据指定条件获取数据
     *
     * @return
     * @throws Exception
     */
    public String load () throws Exception {

        financeExpense.setState (Config.STATE_CURRENT);
        //获取数据
        financeExpense = MySQLDao.load (financeExpense, FinanceExpensePO.class);

        getResult ().setReturnValue (financeExpense.toJsonObject4Form ());

        return SUCCESS;
    }

    /**
     * 添加或修改项目
     *
     * @return
     * @throws Exception
     */
    public String insertOrUpdateDetail () throws Exception {

        //添加或修改数据
        service.insertOrUpdateDetail (expenseDetail, getLoginUser (), getConnection ());

        return SUCCESS;
    }

    /**
     * 获取指定的项目
     *
     * @return 单一项目数据JSON对象
     * @throws Exception
     */
    public String loadDetail () throws Exception {
        expenseDetail.setState (Config.STATE_CURRENT);
        expenseDetail = MySQLDao.load (expenseDetail, FinanceExpenseDetailPO.class);
        getResult ().setReturnValue (expenseDetail.toJsonObject4Form ());

        return SUCCESS;
    }

    /**
     * 修改制定项目的状态为删除状态
     *
     * @return
     * @throws Exception
     */
    public String deleteDetail () throws Exception {

        service.deleteDetail (expenseDetail, getLoginUser (), getConnection ());

        return SUCCESS;
    }

    /**
     * 获取费用报销申请列表数据
     *
     * @return
     * @throws Exception
     */

    public String list () throws Exception {

        HttpServletRequest request = ServletActionContext.getRequest ();
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters (request, FinanceExpenseVO.class);

        
        String departments = getRequest ().getParameter ("Departments");

        Pager pager = service.list (expenseVO, getLoginUser (), conditions, departments, financeExpense);
        getResult ().setReturnValue (pager.toJsonObject ());

        return SUCCESS;
    }

    /**
     * 获取费用报销等待审批列表数据
     *
     * @return
     * @throws Exception
     * 修该人：周海鸿
     * 时间：2015-7-8
     */

    public String waitlist () throws Exception {

        //获取请求对象
        HttpServletRequest request = getRequest ();
        //创建查询条件对象
        List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters (request, FinanceExpenseVO.class);

        
        //获取请求参数
        String departments = getRequest ().getParameter ("Departments");

        //获取数据
        Pager pager = service.wsitlist (expenseVO, getLoginUser (), conditions, departments, financeExpense);

        getResult ().setReturnValue (pager.toJsonObject ());

        return SUCCESS;
    }

    /**
     * 获取费用报销已完成列表数据
     *
     * @return
     * @throws Exception 修该人：周海鸿
     *                   时间：2015-7-8
     */

    public String participantlist () throws Exception {

        //获取请求对象
        HttpServletRequest request = getRequest ();
        //创建查询条件对象
         List<KVObject> conditions = MySQLDao.getQueryDatetimeParameters (request, FinanceExpenseVO.class);

        
        //获取请求参数
        String departments = getRequest ().getParameter ("Departments");

        //获取数据
        Pager pager = service.participantlist (expenseVO, getLoginUser (), conditions, departments, financeExpense);

        getResult ().setReturnValue (pager.toJsonObject ());

        return SUCCESS;
    }

    /**
     * *
     * 获取费用报销项目列表数据
     *
     * @return
     * @throws Exception
     */
    public String listDetail () throws Exception {
        //获取请求对象
        HttpServletRequest request = getRequest ();

        //获取数据列表
        Pager pager = service.listDetail (expenseDetail);

        getResult ().setReturnValue (pager.toJsonObject ());

        return SUCCESS;
    }


    /**
     * 获取用户的部门 返回数据编号ID与部门名称
     *
     * @return
     * @throws Exception
     */
    public String add () throws Exception {
        List<Map<String, Object>> list = MySQLDao.query ("SELECT po.DepartmentName FROM system_positionuser pu,system_position po where po.id=pu.positionId AND pu.states = 1 and pu.userId='" + Database.encodeSQL (financeExpense.getSubmitterId ()) + "'");
        //一个人员对多个部门时用
//        String departmrnt="";
//        for(Map<String,Object> map:list){
//            for (String k : map.keySet())
//            {
//                System.out.println(k + " : " + map.get(k));
//                departmrnt += map.get(k);
//                departmrnt += ",";
//            }
//        }
//        System.out.println("司法局了发韩剧的合法离："+list.get(0).get("DepartmentName"));
        JSONArray array = new JSONArray ();
        JSONObject json = new JSONObject ();
        json.element ("departmentName", list.get (0).get ("DepartmentName"));
        json.element ("expenseId", IdUtils.getUUID32 ());
        json.element ("time", TimeUtils.getNow ());
        array.add (json);
        getResult ().setReturnValue (array);
        return SUCCESS;
    }


    public FinanceExpensePO getFinanceExpense () {
        return financeExpense;
    }

    public void setFinanceExpense (FinanceExpensePO financeExpense) {
        this.financeExpense = financeExpense;
    }

    public FinanceExpenseVO getExpenseVO () {
        return expenseVO;
    }

    public void setExpenseVO (FinanceExpenseVO expenseVO) {
        this.expenseVO = expenseVO;
    }

    public FinanceExpenseService getService () {
        return service;
    }

    public void setService (FinanceExpenseService service) {
        this.service = service;
    }

    public FinanceExpenseDetailPO getExpenseDetail () {
        return expenseDetail;
    }

    public void setExpenseDetail (FinanceExpenseDetailPO expenseDetail) {
        this.expenseDetail = expenseDetail;
    }
}
