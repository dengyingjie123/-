package com.youngbook.service.system;

import com.youngbook.common.Pager;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.system.IPositionUserDao;
import com.youngbook.entity.po.system.PositionUserPO;
import com.youngbook.entity.vo.system.PositionUserVO;
import com.youngbook.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;

@Component("positionUserService")
public class PositionUserService extends BaseService {

    @Autowired
    IPositionUserDao positionUserDao;

    public void setDefaultFinanceCircle(String userId, Connection conn) throws Exception {
        positionUserDao.setDefaultFinanceCircle(userId, conn);
    }

    public PositionUserPO loadPositionUserPO(String id) throws Exception{

        PositionUserPO po = new PositionUserPO();
        po.setId(id);
        po = MySQLDao.load(po, PositionUserPO.class);

        return po;
    }

    
    /**
     * @description 获取用户部门归属列表
     * 
     * @author 胡超怡 
     * 
     * @date 2018/12/12 15:15 
     * @param positionUserVO
     * @param currentPage
     * @param showRowCount
     * @param conn 
     * @return com.youngbook.common.Pager 
     * @throws Exception
     */
    public Pager showList(PositionUserVO positionUserVO, int currentPage, int showRowCount, Connection conn) throws Exception{


        /**
         * positionUserVO判断是否为空
         */
        return positionUserDao.showList(positionUserVO, currentPage, showRowCount, conn);
    }
}
