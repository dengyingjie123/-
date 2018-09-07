package com.youngbook.service.customer;

import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.customer.CustomerPaymentPO;
import com.youngbook.entity.vo.customer.CustomerPaymentVO;
import com.youngbook.service.BaseService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;

/**
 * Created by Administrator on 2015/4/2.
 * 客户支付逻辑类
 */
public class CustomerPaymentService extends BaseService {

    /**
     * 查询列表数据
     *
     * @param customerPaymentVO 对应列表对象
     * @param conditions        查询条件
     * @return
     */
    public Pager list(CustomerPaymentVO customerPaymentVO, List<KVObject> conditions) throws Exception {
        //获取HTTP请求对象
        HttpServletRequest request = ServletActionContext.getRequest();
        //组装SQl语句
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT DISTINCT CCP.sid,CCP.id, CCP.state,CCP.operatorId,CCP.operateTime, ");
        sql.append(" CCP.Money, CCP.FeeRate,CCP.Type, CCP.Status, CCP.Time, CCP.IP, CCP.BizId, ");
        sql.append(" CCP.PaymentAccount, CCP.PaymentType, CCP.PaymentInfo,");
        sql.append("us.`name` operatorName,kv.V typeName ,kv_status.v statusName,kv_paymentType.V paymentTypeName");
        sql.append(" FROM  crm_customerPayment CCP, system_user us, system_kv kv,system_kv kv_status,system_kv kv_paymentType ");
        sql.append("WHERE CCP.state = 0  AND us.state = 0 and kv.groupName='customer_customerPayment_Type'" );
        sql.append("ANd us.OperatorId= CCP.operatorId ANd kv.k=CCP.type");
        sql.append(" and kv_status.k = CCP.status and kv_status.groupName='customer_customerPayment_status'");
        sql.append(" and kv_paymentType.k=CCP.PaymentType and kv_paymentType.groupName='customer_customerPayment_PaymentType'");
        //设置查询类型
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);
        //返回分页对象
        Pager pager = Pager.query(sql.toString(), customerPaymentVO, conditions, request, queryType);

        return pager;
    }

    /**
     * 根据id编号获取对象值
     * @param id 数据编号
     * @return 对应数据的对象
     * @throws Exception
     */
    public CustomerPaymentPO loadCustomerPaymentPO(String id) throws Exception{
       //创建一个对象
        CustomerPaymentPO po = new CustomerPaymentPO();

        po.setId(id);//设置对象ID
        //设置记录准状态
        po.setState(Config.STATE_CURRENT);
        //获取数据对象
        po = MySQLDao.load(po, CustomerPaymentPO.class);
        //返回对象。
        return po;
    }

    /**
     * 添加跟更新方法
     * @param customerPayment 需要的对象
     * @param user 操作人对象
     * @param conn 数据库链接
     * @return
     */
    public int insertOrUpdate(CustomerPaymentPO customerPayment, UserPO user, Connection conn) throws Exception{
        int count = 0;
        // 新增
        if (customerPayment.getId().equals("")) {
            //根据数据列获取数据SID
            customerPayment.setSid(MySQLDao.getMaxSid("CRM_CustomerPayment", conn));
            //设置ID
            customerPayment.setId(IdUtils.getUUID32());
            //设置记录状态
            customerPayment.setState(Config.STATE_CURRENT);
            //设置操作员编号
            customerPayment.setOperatorId(user.getId());
            //设置 操作时间
            customerPayment.setOperateTime(TimeUtils.getNow());
            //添加数据
            count = MySQLDao.insert(customerPayment, conn);
        }
        // 更新
        else {
            //修改原数据
            CustomerPaymentPO temp = new CustomerPaymentPO();
            //根据sid获取数据
            temp.setSid(customerPayment.getSid());
            //后去数据
            temp = MySQLDao.load(temp, CustomerPaymentPO.class);
            //设置数据状态
            temp.setState(Config.STATE_UPDATE);
            //跟新数据
            count = MySQLDao.update(temp, conn);
            //插入新修改的数据
            if (count == 1) {
                //获取属于数为sid值
                customerPayment.setSid(MySQLDao.getMaxSid("CRM_CustomerPayment", conn));
                //设置语句状态为当前状态
                customerPayment.setState(Config.STATE_CURRENT);
                //设置操作员编号
                customerPayment.setOperatorId(user.getId());
                //设置操作时间
                customerPayment.setOperateTime(TimeUtils.getNow());
                //天机数据
                count = MySQLDao.insert(customerPayment, conn);
            }
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    /**
     * 删除记录方法
     * @param customerPayment  数据对象
     * @param user 操作员对象
     * @param conn 数据库链接
     * @return
     * @throws Exception
     */
    public int delete(CustomerPaymentPO customerPayment, UserPO user, Connection conn) throws Exception {
        int count = 0;

        ///获取记录数据
        customerPayment = MySQLDao.load(customerPayment, CustomerPaymentPO.class);
        //将记录数据的状态改为删除状态
        customerPayment.setState(Config.STATE_UPDATE);

        //根据数据范湖影响行数
        count = MySQLDao.update(customerPayment, conn);
        //如果影响函数为一
        if (count == 1) {
            //获取数据的数据量设置SID
            customerPayment.setSid(MySQLDao.getMaxSid("CRM_CustomerPayment", conn));
            //修改数据记录
            customerPayment.setState(Config.STATE_DELETE);
            //添加操作时间
            customerPayment.setOperateTime(TimeUtils.getNow());
            //添加操作员
            customerPayment.setOperatorId(user.getId());
            //天机删除数据
            count = MySQLDao.insert(customerPayment, conn);
        }

        if (count != 1) {
            throw new Exception("删除失败");
        }

        return count;
    }

}

