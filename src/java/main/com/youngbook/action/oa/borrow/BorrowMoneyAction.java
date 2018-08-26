package com.youngbook.action.oa.borrow;

import com.youngbook.action.BaseAction;
import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.oa.borrow.BorrowMoneyPO;
import com.youngbook.entity.vo.oa.borrow.BorrowMoneyVO;
import com.youngbook.service.oa.borrow.BorrowMoneyService;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 周海鸿 on 2015/9/21.
 * 借款申请 控制器
 */
public class BorrowMoneyAction extends BaseAction {
    //借款申请数据实体类
    private BorrowMoneyPO borrowMoney = new BorrowMoneyPO();
    private BorrowMoneyVO borrowMoneyVO = new BorrowMoneyVO();
    //借款申请逻辑操作类
    private BorrowMoneyService service = new BorrowMoneyService();

    private String sort = new String();//需要排序的列名
    private String order = new String();

    /**
     * 获取申请数据列表
     *
     * @return
     * @throws Exception
     */
    public String list() throws Exception {
        //获取请求对象
        HttpServletRequest request = getRequest();
        //创建查询条件对象
        List<KVObject> conditions = new ArrayList<KVObject>();
        

        //设置查询条件
        conditions = MySQLDao.getQueryDatetimeParameters(request, borrowMoneyVO.getClass(), conditions);
        //整型范围查询
        conditions = MySQLDao.getQueryNumberParameters(request, borrowMoneyVO.getClass(), conditions);

        //设置排序
        if (!getSort().equals("")) {
            conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, getSort() + " " + getOrder()));
        }

        //获取列表数据
        Pager pager = service.list(borrowMoneyVO, getLoginUser(), conditions);

        getResult().setReturnValue(pager.toJsonObject());

        return SUCCESS;
    }

    /**
     * 创建请求对象，创建条件查询对象
     * 设置范围查询条件
     * 获取等待审核数据列表
     *
     * @return
     * @throws Exception
     */
    public String Waitlist() throws Exception {
        //获取请求对象
        HttpServletRequest request = getRequest();
        //创建查询条件对象
        List<KVObject> conditions = new ArrayList<KVObject>();
        

        //设置范围查询条件
        conditions = MySQLDao.getQueryDatetimeParameters(request, borrowMoneyVO.getClass(), conditions);
        //整型范围查询
        conditions = MySQLDao.getQueryNumberParameters(request, borrowMoneyVO.getClass(), conditions);
        //设置排序
        if (!getSort().equals("")) {
            conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, getSort() + " " + getOrder()));
        }

        //获取列表数据
        Pager pager = service.Waitlist(borrowMoneyVO, getLoginUser(), conditions);

        getResult().setReturnValue(pager.toJsonObject());

        return SUCCESS;
    }

    /**
     * 创建请求对象，创建条件查询对象
     * 设置范围查询条件
     * 获取等待审核数据列表
     * 获取已审核数据列表
     *
     * @return
     * @throws Exception
     */
    public String Participantlist() throws Exception {
        //获取请求对象
        HttpServletRequest request = getRequest();
        //创建查询条件对象
        List<KVObject> conditions = new ArrayList<KVObject>();
        

        //范围查询条件
        conditions = MySQLDao.getQueryDatetimeParameters(request, borrowMoneyVO.getClass(), conditions);
        //整型范围查询
        conditions = MySQLDao.getQueryNumberParameters(request, borrowMoneyVO.getClass(), conditions);
        //设置排序
        if (!getSort().equals("")) {
            conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, getSort() + " " + getOrder()));
        }

        //获取列表数据
        Pager pager = service.Participantlist(borrowMoneyVO, getLoginUser(), conditions);

        getResult().setReturnValue(pager.toJsonObject());

        return SUCCESS;
    }

    /**
     * 调用serviece  添加或更新数据
     *单元测试 service 调用的方法是
     *
     //int count = service.insertOrUpdate(borrowMoney,borrowMoneyVO, getLoginUser(), conn);
     * @return
     * @throws Exception
     */
    public String insertOrUpdate() throws Exception {
        Connection conn = getConnection();
        //执行业务操作添加并跟新数据
      int count = service.insertOrUpdate(borrowMoney,borrowMoneyVO, getLoginUser(), conn);


        //返回影响行数是否不等于一
        if (count != 1) {
            getResult().setMessage("操作失败");
        }
        return SUCCESS;
    }


    /**
     * 设置查询id
     * <p/>
     * 获取单条数据
     *
     * @return
     * @throws Exception
     */
    public String load() throws Exception {
        //设置查询id
        borrowMoney = service.loadborrowMoneyPO(borrowMoney.getId());

        //查询返回数据
        getResult().setReturnValue(borrowMoney.toJsonObject4Form());

        return SUCCESS;
    }

    /**
     * 删除数据
     *
     * @return
     * @throws Exception
     */
    public String delete() throws Exception {
        //调用service 删除数据
        service.delete(borrowMoney, getLoginUser(), getConnection());

        return SUCCESS;
    }


    public BorrowMoneyPO getBorrowMoney() {
        return borrowMoney;
    }

    public void setBorrowMoney(BorrowMoneyPO borrowMoney) {
        this.borrowMoney = borrowMoney;
    }

    public BorrowMoneyVO getBorrowMoneyVO() {
        return borrowMoneyVO;
    }

    public void setBorrowMoneyVO(BorrowMoneyVO borrowMoneyVO) {
        this.borrowMoneyVO = borrowMoneyVO;
    }

    public BorrowMoneyService getService() {
        return service;
    }

    public void setService(BorrowMoneyService service) {
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
