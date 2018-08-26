package com.youngbook.action.sale;

import com.youngbook.action.BaseAction;
import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.sale.LeaderboardPO;
import com.youngbook.entity.po.sale.SalesmanPO;
import com.youngbook.entity.vo.Sale.SalesManVO;
import com.youngbook.service.sale.SalesmanService;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-10-14
 * Time: 下午2:01
 * To change this template use File | Settings | File Templates.
 */
public class SalesmanAction extends BaseAction{

    private ReturnObject result;
    private SalesManVO salesManVO = new SalesManVO();



    private SalesmanPO salesman= new SalesmanPO();
    private UserPO user=new UserPO();


    @Autowired
    SalesmanService salesmanService;

    public String listPagerSalesmanGroup() throws Exception {

        SalesManVO salesManVO = new SalesManVO();
        String userName = getHttpRequestParameter("userName");
        String groupName = getHttpRequestParameter("groupName");

        salesManVO.setUserName(userName);
        salesManVO.setGroupName(groupName);

        Pager pager = Pager.getInstance(getRequest());
        pager = salesmanService.listPagerSalesmanGroup(salesManVO, pager.getCurrentPage(), pager.getShowRowCount(), getConnection());

        getResult().setReturnValue(pager.toJsonObject());

        return SUCCESS;
    }

    /**
     * 获取销售人员的龙虎榜
     *
     * 作者：邓超
     * 内容：创建代码
     * 时间：2016年5月25日
     *
     * @return
     * @throws Exception
     */
    public String getLeaderboards() throws Exception {

        // 获取请求对象
        HttpServletRequest request = this.getRequest();
        // 获取数据库连接
        Connection conn = this.getConnection();

        // 获取参数
        String dateType = HttpUtils.getParameter(request, "dateType");
        String currentDate = TimeUtils.getNowDate();
        String lastDate = "";
        String nextDate = "";

        if("date".equals(dateType)) {
            lastDate = currentDate;
            nextDate = TimeUtils.getTime(currentDate, 1, "DATE");
        }
        else if ("month".equals(dateType)) {
            lastDate = currentDate.substring(0, 7);
            nextDate = TimeUtils.getTime(currentDate, 1, "MONTH").substring(0, 7);
        }
        else {
            MyException.newInstance(ReturnObjectCode.PUBLIC_WEBCODE_NOT_CORRECT, "参数不正确").throwException();
        }

        List<LeaderboardPO> list = salesmanService.getLeaderboards(lastDate, nextDate, conn);
        this.getResult().setReturnValue(list);

        return SUCCESS;

    }

    public String load() throws Exception {
        result = new ReturnObject();
        JSONArray array=new JSONArray();
        try {
            user.setState(Config.STATE_CURRENT);
            user = MySQLDao.load(user, UserPO.class);
            String sql="select * from crm_saleman where state=0 and userId ='"+ Database.encodeSQL(user.getId()) + "'";
            List<SalesmanPO> list=MySQLDao.query(sql,SalesmanPO.class,null);
            if(list!=null&&list.size()>0){
                salesman=list.get(0);
                array.add(salesman.toJsonObject());
            }
            result.setMessage("操作成功");
            result.setCode(ReturnObject.CODE_SUCCESS);
            array.add(user.toJsonObject4Form());
            result.setReturnValue(user.toJsonObject4Form());

        }
        catch (Exception e) {
            result.setCode(ReturnObject.CODE_EXCEPTION);
            result.setMessage("操作失败");
            result.setException(e);
        }
        return SUCCESS;
    }

    public String loadUser() throws Exception {
        salesman.setState(Config.STATE_CURRENT);
        salesman = MySQLDao.load(salesman, SalesmanPO.class);
        result.setMessage("操作成功");
        result.setCode(ReturnObject.CODE_SUCCESS);
        result.setReturnValue(salesman.toJsonObject4Form());

        return SUCCESS;
    }


    public String update() throws Exception {
        salesmanService.update(salesman, getLoginUser(), getConnection());
        return SUCCESS;
    }

    public String list()throws Exception{
        HttpServletRequest request= ServletActionContext.getRequest();
        List<KVObject> conditions=this.getCondition(request);
        Pager pager = salesmanService.getSalesmanList(salesManVO,conditions,getRequest());
        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }

    /**
     * 通过添加用户所选择的销售岗位对销售进行添加
     * @return
     * @throws Exception
     */
    public String insertSalesman() throws Exception{
        salesmanService.insertSalesman(user,getConnection());
        return SUCCESS;
    }

    private List<KVObject> getCondition( HttpServletRequest request){

        String name=request.getParameter("name");
        String mobile=request.getParameter("mobile");
        String idnumber=request.getParameter("idnumber");
        List<KVObject> conditions =new ArrayList<KVObject>();
        if(name!=null&&!name.equals("")){
            KVObject kvProject = new KVObject("name", " like '%"+Database.encodeSQL(name)+"%'" );
            conditions.add(kvProject);
        }
        if(mobile!=null&&!mobile.equals("")){
            KVObject kvProject = new KVObject("mobile", " like '%"+Database.encodeSQL(mobile)+"%'" );
            conditions.add(kvProject);
        }
        if(idnumber!=null&&!idnumber.equals("")){
            KVObject kvProject = new KVObject("idnumber", " like '%"+Database.encodeSQL(idnumber)+"%'" );
            conditions.add(kvProject);
        }
        return conditions;
    }

    public SalesmanPO getSalesmanPO() {
        return salesman;
    }

    public void setSalesmanPO(SalesmanPO salesmanPO) {
        this.salesman = salesmanPO;
    }

    public SalesManVO getSalesManVO() {
        return salesManVO;
    }

    public void setSalesManVO(SalesManVO salesManVO) {
        this.salesManVO = salesManVO;
    }

    public UserPO getUser() {
        return user;
    }

    public void setUser(UserPO user) {
        this.user = user;
    }

    public ReturnObject getResult() {
        return result;
    }

    public void setResult(ReturnObject result) {
        this.result = result;
    }


    public SalesmanPO getSalesman() {
        return salesman;
    }

    public void setSalesman(SalesmanPO salesman) {
        this.salesman = salesman;
    }
}
