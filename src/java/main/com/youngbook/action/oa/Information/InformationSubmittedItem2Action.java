package com.youngbook.action.oa.Information;

import com.youngbook.action.BaseAction;
import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.MyException;
import com.youngbook.common.Pager;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.entity.po.oa.Information.InformationSubmittedItem2PO;
import com.youngbook.entity.vo.oa.Information.InformationSubmittedItem2VO;
import com.youngbook.service.oa.Information.InformationSubmittedItem2Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建一个SealUsageItemAction类，继承BaseAction累
 *
 * @author Codemaker
 */

public class InformationSubmittedItem2Action extends BaseAction {
    //实例化PO、VO、Servlet 类对象
    private InformationSubmittedItem2PO informationSubmittedItem2 = new InformationSubmittedItem2PO();
    private InformationSubmittedItem2VO informationSubmittedItem2VO = new InformationSubmittedItem2VO();
    private InformationSubmittedItem2Service service = new InformationSubmittedItem2Service();

    private String sort = new String();//需要排序的列名
    private String order = new String();

    /**
     * 判断是否有用着数据的id
     * 设置查询条件
     * 调用service 的list 方法获取列表数据
     * 海鸿
     * 2015-8-18
     * 获取用着类型列表数据
     *
     * @return
     * @throws Exception
     */
    public String list() throws Exception {
        if (StringUtils.isEmpty(informationSubmittedItem2.getApplicationId())) {
            getResult().setException(new Exception("资料申请数据出错 请联系管理员"));
            throw new Exception("资料申请数据出错 请联系管理员");
        }


        //获取请求对象
        HttpServletRequest request = getRequest();

        //创建查询条件对象
        List<KVObject> conditions = new ArrayList<KVObject>();

        //设置排序
        if (!getSort().equals("")) {
            conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, getSort() + " " + getOrder()));
        }
        String applicaiton =informationSubmittedItem2.getApplicationId();
      //  informationSubmittedItem2.setApplicationId("");
        //获取类型列表数据
        Pager pager = service.list(informationSubmittedItem2VO,applicaiton , conditions, request, getConnection());

        //返回数据
        getResult().setReturnValue(pager.toJsonObject());

        return SUCCESS;
    }

    /**
     * 调用serviece  添加或更新数据
     *
     * @return
     * @throws Exception
     */
    public String insertOrUpdate() throws Exception {

        //添加或更新数据
        int count = service.insertOrUpdate(informationSubmittedItem2, getLoginUser(), getConnection());

        //判断影响行数是否为一条
        if (count != 1) {
            getResult().setMessage("操作失败");
        }

        return SUCCESS;
    }

    /**
     * 根据类型id获取用着类型数据
     * 获取单条数据
     *
     * @return
     * @throws Exception
     */
    public String load() throws Exception {

        //根据id获取单个 资料类型的数据
        informationSubmittedItem2 = service.loadInformationSubmittedItem2PO(informationSubmittedItem2.getId());

        //将输返回页面
        getResult().setReturnValue(informationSubmittedItem2.toJsonObject4Form());

        return SUCCESS;
    }


    /**
     * 删除数据
     *
     * @return
     * @throws Exception
     */
    public String delete() throws Exception {

        //根据service 删除数据
        service.delete(informationSubmittedItem2, getLoginUser(), getConnection());

        return SUCCESS;
    }

    /**
     * 批量接收印章
     *
     * @return
     * @throws Exception
     */
    public String updateReceive() throws Exception {
        //验证申请编号是否存在
        if (StringUtils.isEmpty(informationSubmittedItem2.getApplicationId())) {
            getResult().setReturnValue("0");
            MyException.deal(MyException.newInstance("申请编号不存在"));
            return SUCCESS;
        }
        //是都有啊需要更改的编号
        if (StringUtils.isEmpty(informationSubmittedItem2.getId())) {
            getResult().setReturnValue("0");
            MyException.deal(MyException.newInstance("修改编号不存在"));
            return SUCCESS;
        }
        //批量接收印章
        int count = service.updateReceive(informationSubmittedItem2, getLoginUser(), getConnection());

        //更新失败
        if (count != 1) {
            getResult().setReturnValue("0");
            MyException.deal(MyException.newInstance("接收失败"));
            return SUCCESS;
        }
        //成功
        getResult().setReturnValue("1");
        return SUCCESS;
    }

    /**
     * 批量归还印章
     *
     * @return
     * @throws Exception
     */
    public String updateOutBack() throws Exception {
        //验证申请编号是否存在
        if (StringUtils.isEmpty(informationSubmittedItem2.getApplicationId())) {
            getResult().setReturnValue("0");
            MyException.deal(MyException.newInstance("申请编号不存在"));
            return SUCCESS;
        }
        //是都有啊需要更改的编号
        if (StringUtils.isEmpty(informationSubmittedItem2.getId())) {
            getResult().setReturnValue("0");
            MyException.deal(MyException.newInstance("修改编号不存在"));
            return SUCCESS;
        }
        //批量接收印章
        int count = service.updateOutBack(informationSubmittedItem2, getLoginUser(), getConnection());
        //更新失败
        if (count != 1) {
            getResult().setReturnValue("0");
            MyException.deal(MyException.newInstance("归还失败"));
            return SUCCESS;
        }
        //成功
        getResult().setReturnValue("1");
        return SUCCESS;
    }

    public InformationSubmittedItem2PO getInformationSubmittedItem2() {
        return informationSubmittedItem2;
    }

    public void setInformationSubmittedItem2(InformationSubmittedItem2PO informationSubmittedItem2) {
        this.informationSubmittedItem2 = informationSubmittedItem2;
    }

    public InformationSubmittedItem2Service getService() {
        return service;
    }

    public void setService(InformationSubmittedItem2Service service) {
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
