package com.youngbook.service.sale;


import com.youngbook.common.MyException;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.sale.ProductionFoWorkflowPO;
import com.youngbook.service.BaseService;
import com.youngbook.service.wf.BizRouteService;

import java.sql.Connection;
import java.util.List;

/**
 * Service
 * 产品兑付计划与工作流实习类
 * Created by Administrator on 2015/3/25.
 */
public class ProductionFoWorkflowService extends BaseService {


    /**
     * 插入数据
     *
     * @param productionFoWorkflow
     * @param user
     * @param conn
     * @return 返回新增数据id 或者返回null
     * @throws Exception
     */
    public String insertOf(ProductionFoWorkflowPO productionFoWorkflow, UserPO user, Connection conn) throws Exception {
        //根据田间判断该数据是否存在
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append("	select DISTINCT * from  Sale_ProductionFoWorkflow");
        sbSQL.append("	where 1=1");
        sbSQL.append("	and state = 0");
        sbSQL.append("	and productId = '"+productionFoWorkflow.getProductId()+"'");
        sbSQL.append("	and PaymentTime ='" + productionFoWorkflow.getPaymentTime() + "'");
        //查询数据
       List<ProductionFoWorkflowPO> productionFoWorkflowPOs =  MySQLDao.query(sbSQL.toString(),ProductionFoWorkflowPO.class,null,conn);

        if(productionFoWorkflowPOs.size() == 0) {

            int count = 0;
            // 设置Sid
            productionFoWorkflow.setSid(MySQLDao.getMaxSid("Sale_ProductionFoWorkflow", conn));
            //设置id
            productionFoWorkflow.setId(IdUtils.getUUID32());
            //设置 数据的状态
            productionFoWorkflow.setState(Config.STATE_CURRENT);
            //设置操作员的ID
            productionFoWorkflow.setOperatorId(user.getId());
            //什么操作时间
            productionFoWorkflow.setOperateTime(TimeUtils.getNow());
            //添加数据
            count = MySQLDao.insert(productionFoWorkflow, conn);


            if (count != 1) {
                throw new Exception("数据库异常");
            }

            //向工作流数据表添加基本数据

            if (count == 1) {
                count = BizRouteService.insertOrUpdate(productionFoWorkflow.getId(),"","","",
                        Integer.parseInt(Config.getSystemVariable("WORKFLOWID.PaymentPlam.Check")), true, user, conn);
            }
            //添加成功返回生成的id
            return productionFoWorkflow.getId();
        }else if(productionFoWorkflowPOs.size() == 1){
            //返回已存在的id
            return productionFoWorkflowPOs.get(0).getId();
        }else{
            return null;
        }
    }


    /*获取产品兑付辅助表数据
     */
    public ProductionFoWorkflowPO load(String id,Connection conn) throws  Exception {
        ProductionFoWorkflowPO p = new ProductionFoWorkflowPO();
        p.setId(id);
        //根据id获取数据
        ProductionFoWorkflowPO p2 = MySQLDao.load(p, ProductionFoWorkflowPO.class,conn);

        //返回的数据是否为null
        if(p2 ==  null){
           throw MyException.newInstance("获取产品兑付工作流辅助数据失败");
       }
        return p2;
    }
}
