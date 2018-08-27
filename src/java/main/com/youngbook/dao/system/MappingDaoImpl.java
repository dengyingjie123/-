package com.youngbook.dao.system;

import com.youngbook.common.utils.StringUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.system.MappingPO;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.List;

/**
 * Created by leevits on 7/22/2018.
 */
@Component("mappingDao")
public class MappingDaoImpl implements IMappingDao {

    public MappingPO updateBasedOnBiz01(String biz01, String biz02, String type, Connection conn) throws Exception {

        List<MappingPO> search = search(biz01, null, type, conn);

        for (int i = 0; search != null && i < search.size(); i++) {
            MappingPO mappingPO = search.get(i);

            MySQLDao.remove(mappingPO, conn);
        }


        MappingPO mappingPO = newMapping(biz01, biz02, type, conn);

        return mappingPO;
    }


    public MappingPO updateBasedOnBiz02(String biz01, String biz02, String type, Connection conn) throws Exception {

        List<MappingPO> search = search(null, biz02, type, conn);

        for (int i = 0; search != null && i < search.size(); i++) {
            MappingPO mappingPO = search.get(i);

            MySQLDao.remove(mappingPO, conn);
        }


        MappingPO mappingPO = newMapping(biz01, biz02, type, conn);

        return mappingPO;
    }

    public MappingPO newMapping(String biz01, String biz02, String type, Connection conn) throws Exception {

        StringUtils.checkIsEmpty(biz01, "业务编号01");
        StringUtils.checkIsEmpty(biz02, "业务编号02");
        StringUtils.checkIsEmpty(type, "映射类型");

        MappingPO mappingPO = load(biz01, biz02, type, conn);

        if (mappingPO != null) {
            return mappingPO;
        }

        mappingPO = new MappingPO();
        mappingPO.setBizId01(biz01);
        mappingPO.setBizId02(biz02);
        mappingPO.setType(type);

        MySQLDao.insertOrUpdate(mappingPO, conn);

        return mappingPO;

    }

    public List<MappingPO> search(String biz01, String biz02, String type, Connection conn) throws Exception {

        MappingPO mappingPO = new MappingPO();
        mappingPO.setBizId01(biz01);
        mappingPO.setBizId02(biz02);
        mappingPO.setType(type);

        List<MappingPO> search = MySQLDao.search(mappingPO, MappingPO.class, conn);

        return search;
    }

    public MappingPO load(String biz01, String biz02, String type, Connection conn) throws Exception {

        List<MappingPO> search = search(biz01, biz02, type, conn);

        if (search != null && search.size() == 1) {
            return search.get(0);
        }

        return null;
    }

}
