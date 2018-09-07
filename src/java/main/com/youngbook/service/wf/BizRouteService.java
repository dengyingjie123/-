package com.youngbook.service.wf;

import com.youngbook.common.MyException;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.DepartmentPO;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.wf.BizRoutePO;
import com.youngbook.service.BaseService;

import java.sql.Connection;

/**
 * Created by zhouhaihong on 2015/9/2.
 */
public class BizRouteService extends BaseService {

    private static BizRoutePO biz = new BizRoutePO();

    /**
     * 添加业务数据
     *
     * @param id              数据id
     * @param companyId  公司
     * @param departmentId 部门
     * @param user            操作用户
     * @param flagOfInsertOrUpdate            用判断是添加还是更新 true 添加，flose 更新
     * @param workflowId      业务id
     * @param conn            数据连接
     * @return
     * @throws Exception
     */
    public static int insertOrUpdate(String id,String modelsStringId, String companyId, String departmentId, int workflowId, boolean flagOfInsertOrUpdate, UserPO user, Connection conn) throws Exception {

        int count = 0;
        String tempid  =  "";


        //判断是否应该添加数据
        if (!Config.getSystemVariable("insertOrUpdateBizRoute").equals("true")) {
            return 1;
        }

        //业务ID
        biz.setId_ywid(id);
        //经手人
        biz.setSubmitterId(user.getId());

        biz.setSubmitterTime(TimeUtils.getNow());
        biz.setSubmitterName(user.getName());
        //申请人为当前用户
        biz.setApplicantId(user.getId());
        biz.setApplicantName(user.getName());
        // biz.setApplicantComment();
        biz.setApplicantTime(TimeUtils.getNow());

        //设置公司于部门
        biz.setControlString1(companyId);
        biz.setControlString2(departmentId);
        //设置业务编号
        biz.setWorkflowId(workflowId);

        //判断传入进来的公司名是否为null
        if (!StringUtils.isEmpty(companyId)) {
            // 获得OA流程的标识，仅仅用于人工查看和比对，无实际意义
            tempid = getModelsStringId(companyId,modelsStringId,workflowId, flagOfInsertOrUpdate, conn);
            biz.setControlString3(tempid);
        }


        //判断是更新还是添加
        if (flagOfInsertOrUpdate) {
            //添加数据返回回影响行数
            count = MySQLDao.insert(biz, conn);
        }
        else {
            //修改标识号的前缀

            //更新数据返回影响行数;
            count = MySQLDao.update(biz, conn);
        }
        return count;
    }

    /**
     * 获取标识号
     * 获得OA流程的标识，仅仅用于人工查看和比对，无实际系统控制意义
     * 例如生成信息批送：XXPS2015001
     *
     * @param companyId 公司编号
     * @param modelsStringId 已经生成的编号
     * @param workflowId 业务编号
     * @param flag 更新/添加
     * @param conn 数据库连接
     * @return
     * @throws Exception
     */
    public static String getModelsStringId(String companyId,String modelsStringId,int workflowId,boolean flag, Connection conn ) throws Exception {

        String prefix = Config.getWorkFlow4OA(workflowId);


        if (StringUtils.isEmpty(prefix)) {
            // 没有标识的定义，说明不需要生成标识，直接返回
            return prefix;
        }

        //根据公司标号获取公司全称
        DepartmentPO  department  = new DepartmentPO();
        department.setId(companyId); //设置编号
        //获取数据
        department = MySQLDao.load(department,DepartmentPO.class, conn);

        //判断公司名称是否为null
        if(!StringUtils.isEmpty(department.getFromName())) {

            //根据公司名字获取公司模块字符串
            String tempid = Config.getDepartmentPrefix4OA(department.getFromName());


            //判断是否是更新数据
            if(!StringUtils.isEmpty(modelsStringId) && ! flag) {
                //获取传入的序号中的公司模块名是否与新查询的模块名相同
                String modestr = modelsStringId.substring(0, modelsStringId.indexOf("("));
                //判断产生的公司名模块是相同
                if (modestr.equals(tempid) || modestr == tempid) {
                    return modelsStringId;
                }
            }


            //根据业务id 获取业务的模块缩写
            tempid += "(" + prefix + ")";

            //获取OA序列
            int oaid = Config.getOASequence(conn);
            //判断idd长度
            if (oaid < 10) {
                tempid += "0000" + oaid;
            } else if (oaid < 100) {
                tempid += "000" + oaid;
            } else if (oaid < 1000) {
                tempid += "00" + oaid;
            } else if (oaid < 10000) {
                tempid += "0" + oaid;
            }
            else if (oaid < 100000) {
                tempid += "" + oaid;
            }

            //加如列表中
            //设置标识id
            return tempid;
        }

        return null;
    }
}
