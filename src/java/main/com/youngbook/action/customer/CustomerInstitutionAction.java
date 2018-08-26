package com.youngbook.action.customer;

import com.youngbook.action.BaseAction;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.customer.CustomerInstitutionPO;
import com.youngbook.entity.vo.customer.CustomerInstitutionVO;
import com.youngbook.service.customer.CustomerInstitutionService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class CustomerInstitutionAction extends BaseAction{

    private CustomerInstitutionPO institution = new CustomerInstitutionPO();
    private CustomerInstitutionVO customerInstitutionVO = new CustomerInstitutionVO();

    @Autowired
    CustomerInstitutionService customerInstitutionService;

    /**
     * 添加或修改
     * @return
     * @throws Exception
     */
    public String insertOrUpdate() throws Exception {
        customerInstitutionService.insertOrUpdate(institution, getLoginUser(), getConnection());
        return SUCCESS;
    }

    /**
     * 删除
     * @return
     * @throws Exception
     */
    public String delete() throws Exception {
        customerInstitutionService.delete(institution, getLoginUser().getId(), getConnection());
        return SUCCESS;
    }

    /**
     * 读取
     * @return
     * @throws Exception
     */
    public String load() throws Exception {
        institution.setState(Config.STATE_CURRENT);
        institution = MySQLDao.load(institution, CustomerInstitutionPO.class);
        getResult().setReturnValue(institution.toJsonObject4Form());
        return SUCCESS;
    }

    /**
     * 获得机构客户编号
     * @return
     * @throws Exception
     */
    public String add()throws Exception{
        int sequence=MySQLDao.getSequence("system");
        //补位
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumIntegerDigits(4);
        formatter.setGroupingUsed(false);
        String s = formatter.format(sequence);
        JSONArray array=new JSONArray();
        JSONObject json=new JSONObject();
        json.element("institutionNumber", "HW1" + s);
        json.element("creatTime", TimeUtils.getNow());
        array.add(json);
        getResult().setReturnValue(array);
        return SUCCESS;
    }

    /**
     * 密码修改
     * @return
     * @throws Exception
     */
    public String passwordUpdate() throws Exception {
        customerInstitutionService.passwordUpdate(institution, getLoginUser(), getConnection());
        return SUCCESS;
    }

    /**
     * 原密码验证
     * @return
     * @throws Exception
     */
    public String checkPassword() throws Exception{
        String beforePassword= StringUtils.md5(institution.getPassword());
        CustomerInstitutionPO temp =new CustomerInstitutionPO();
        temp.setSid(institution.getSid());
        temp =MySQLDao.load(temp, CustomerInstitutionPO.class);

        JSONArray array=new JSONArray();
        JSONObject json=new JSONObject();
        if(beforePassword.equals(temp.getPassword())){
            json.element("successful", "1");
        }else {
            json.element("successful", "0");
        }
        array.add(json);
        getResult().setReturnValue(array);
        return SUCCESS;
    }

    /**
     * 列出所有数据
     * @return
     * @throws Exception
     */
    public String list() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<KVObject> conditions = new ArrayList<KVObject>();

        Pager pager = Pager.getInstance(request);

        if (getPermission().has("客户管理_机构客户管理_管理员查看")) {
            pager = customerInstitutionService.listCustomers4DistributionToManagedSaleGroup(customerInstitutionVO, conditions, pager.getCurrentPage(), pager.getShowRowCount(), getLoginUser().getId(), getConnection());
        }
        else if (getPermission().has("客户管理_机构客户管理_销售人员查看")) {
            pager = customerInstitutionService.listCustomers4DistributionToMe(customerInstitutionVO, conditions, pager.getCurrentPage(), pager.getShowRowCount(), getLoginUser().getId(), getConnection());
        }

        getResult().setReturnValue(pager.toJsonObject());
        return SUCCESS;
    }


    /**
     * 初始化融资机构tree
     * @return
     * @throws Exception
     */
    public String financeInstitutionList() throws Exception {
        //从service中取出数据
        List<CustomerInstitutionVO> list= customerInstitutionService.listFinanceInstitution(getConnection());

        //将结果拼成json模式传递给前台
        JSONArray array=new JSONArray();
        for(CustomerInstitutionVO c:list){
            array.add(c.toJsonObject4Form());
        }
        getResult().setReturnValue(array);
        return SUCCESS;
    }




    public CustomerInstitutionPO getInstitution() {
        return institution;
    }
    public void setInstitution(CustomerInstitutionPO institution) {
        this.institution = institution;
    }

    public CustomerInstitutionService getCustomerInstitutionService() {
        return customerInstitutionService;
    }

    public void setCustomerInstitutionService(CustomerInstitutionService customerInstitutionService) {
        this.customerInstitutionService = customerInstitutionService;
    }

    public CustomerInstitutionVO getCustomerInstitutionVO() {
        return customerInstitutionVO;
    }
    public void setCustomerInstitutionVO(CustomerInstitutionVO customerInstitutionVO) {this.customerInstitutionVO = customerInstitutionVO;}
}
