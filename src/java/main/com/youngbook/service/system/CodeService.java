package com.youngbook.service.system;

import com.youngbook.common.Database;
import com.youngbook.common.KVObject;
import com.youngbook.common.Pager;
import com.youngbook.common.QueryType;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.system.CodePO;
import com.youngbook.entity.vo.system.CodeVO;
import com.youngbook.service.BaseService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;

/**
 * Created by Administrator on 2015/4/22.
 * 系统管理
 */
public class CodeService extends BaseService {
    /**
     * 添加跟新数据
     *
     * @param code
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int insertOrUpdate(CodePO code, UserPO user, Connection conn) throws Exception {
        int count = 0;
        // 新增
        if (code.getId().equals("")) {
            code.setId(IdUtils.getUUID32());
            count = MySQLDao.insert(code, conn);
        }
        // 更新
        else {
            count = MySQLDao.update(code, conn);
        }
        if (count != 1) {
            throw new Exception("数据库异常");
        }
        return count;
    }

    /**
     * 获取单条数据
     *
     * @param id
     * @return
     * @throws Exception
     */
    public CodePO loadCodePO(String id) throws Exception {
        CodePO po = new CodePO();
        po.setId(id);
        po = MySQLDao.load(po, CodePO.class);

        return po;
    }

    /**
     * 将数据修改成删除状态
     *
     * @param code
     * @param user
     * @param conn
     * @return
     * @throws Exception
     */
    public int delete(CodePO code, UserPO user, Connection conn) throws Exception {
        int count = MySQLDao.deletePhysically(code, conn);
        if (count != 1) {
            throw new Exception("删除失败");
        }

        return count;
    }

    /**
     * 获取数据列表
     *
     * @param codevo
     * @param conditions
     * @return
     * @throws Exception
     */
    public Pager list(CodeVO codevo, List<KVObject> conditions) throws Exception {


        StringBuffer SQL = new StringBuffer();
        SQL.append(" SELECT sc.id,sc.`code`,sc.AvailableTime,sc.ExpiredTime, ");
        SQL.append("sc.UsedTime,sc.UserId,sc.IP,su.`name` as userName,sc.CreateTime,");
        SQL.append("sc.Type ,case sc.Type when '0' then '类型一' when '1' then '类型二'");
        SQL.append("else  '其他类型' end as typeName FROM");
        SQL.append(" system_code sc , system_user su");
        SQL.append(" where  su.state=0 AND su.id=sc.UserId");
        HttpServletRequest request = ServletActionContext.getRequest();
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        Pager pager = Pager.query(SQL.toString(), codevo, conditions, request, queryType);
        return pager;
    }
}
