package com.youngbook.action.system;

import com.youngbook.action.BaseAction;
import com.youngbook.common.*;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.HttpUtils;
import com.youngbook.common.utils.IdUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.PositionUserPO;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.vo.system.PositionUserVO;
import com.youngbook.service.system.PositionUserService;
import com.youngbook.service.system.UserService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class PositionUserAction extends BaseAction {
    private ReturnObject result;
    private PositionUserPO positionUser = new PositionUserPO();
    private com.youngbook.entity.po.system.PositionUserPO positionUserPO = new com.youngbook.entity.po.system.PositionUserPO();
    private PositionUserVO positionUserVO = new PositionUserVO();

    @Autowired
    private PositionUserService positionUserService;

    private List<Map<String,Object>> list  = new ArrayList<Map<String, Object>>();

    @Autowired
    private UserService userService;


    public String insertOrUpdate() throws Exception {

        PositionUserPO positionUser = HttpUtils.getInstanceFromRequest(getRequest(), "positionUser", PositionUserPO.class);

        if (positionUser.getId().equals("")) {
            positionUser.setId(IdUtils.getUUID32());
            MySQLDao.insert(positionUser);
        } else {
            MySQLDao.update(positionUser);
        }

        return SUCCESS;
    }
    public String load() {
        result = new ReturnObject();
        try {
            positionUser = MySQLDao.load(positionUser, PositionUserPO.class);
            result.setCode(ReturnObject.CODE_SUCCESS);
            result.setMessage("操作成功");
            result.setReturnValue(positionUser.toJsonObject4Form());
        } catch (Exception e) {
            result.setCode(ReturnObject.CODE_EXCEPTION);
            result.setMessage("操作失败");
            result.setException(e);
        }
        return SUCCESS;
    }

    public String list() {
        result = new ReturnObject();
        try {
            QueryType queryType = new QueryType(Database.QUERY_EXACTLY, Database.NUMBER_EQUAL);

            List<PositionUserPO> positionUserList = MySQLDao.query(positionUser, PositionUserPO.class, null, queryType);
            JSONArray arrayJson = new JSONArray();
            for (int i = 0; i < positionUserList.size(); i++) {
                PositionUserPO temp = positionUserList.get(i);
                arrayJson.add(temp.toJsonObject());
            }
            String json = "";
            if (arrayJson != null && arrayJson.size() > 0) {
                json = arrayJson.toString();
            }
            result.setCode(ReturnObject.CODE_SUCCESS);
            result.setMessage("操作成功");
            result.setReturnValue(json);

        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(ReturnObject.CODE_DB_EXCEPTION);
            result.setMessage("操作失败");
            result.setException(e);
        }
        return SUCCESS;
    }

    public String listUsers() throws Exception {

        String positionId = getHttpRequestParameter("positionUser.positionId");

        Pager pager = Pager.getInstance(getRequest());
        pager = userService.listPagerUserPOs(positionId, pager.getCurrentPage(), pager.getShowRowCount(), getConnection());
        getResult().setReturnValue(pager.toJsonObject());


        return SUCCESS;
    }

    public String delete() {
        result = new ReturnObject();
        try {
            int count = MySQLDao.deletePhysically(positionUser);
            if (count >= 1) {
                result.setMessage("操作成功");
                result.setCode(ReturnObject.CODE_SUCCESS);
            } else {
                result.setMessage("删除失失败");
                result.setCode(ReturnObject.CODE_EXCEPTION);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(ReturnObject.CODE_EXCEPTION);
            result.setMessage("操作失败");
            result.setException(e);
        }
        return SUCCESS;
    }

    public String deleteByIds() {
        result = new ReturnObject();
        try {
            String sql = "delete from system_positionuser where positionId='" +Database.encodeSQL( positionUser.getPositionId())
                    + "' and userId='" +Database.encodeSQL( positionUser.getUserId()) + "'";
            int count = MySQLDao.update(sql);
            if (count >= 1) {
                result.setMessage("操作成功");
                result.setCode(ReturnObject.CODE_SUCCESS);
            } else {
                result.setMessage("删除失失败");
                result.setCode(ReturnObject.CODE_EXCEPTION);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(ReturnObject.CODE_EXCEPTION);
            result.setMessage("操作失败");
            result.setException(e);
        }
        return SUCCESS;
    }


    /**
     * @description 获取用户部门归属列表
     * 增加过滤条件，使得排除已经在department里删除的部门,增加了手机号搜索
     * @author 胡超怡
     *
     * @date 2018/12/12 14:56
     * @return java.lang.String
     * @throws Exception
     */
    public String showList() throws Exception {

        Pager pager = Pager.getInstance(getRequest());

        pager = positionUserService.showList(positionUserVO, pager.getCurrentPage(), pager.getShowRowCount(), getConnection());
        //返回数据
        getResult().setReturnValue(pager.toJsonObject());

        return SUCCESS;
    }

    /**
     * 获取单条数据
     *
     * @return
     * @throws Exception
     */
    public String loadPositionUser() throws Exception {
        positionUserPO = positionUserService.loadPositionUserPO(positionUserPO.getId());
        getResult().setReturnValue(positionUserPO.toJsonObject4Form());

        return SUCCESS;
    }


    public String updatePositionUser() throws Exception {
        if (positionUserPO.getStates() == 0){
            getResult().setCode(32537);
            return SUCCESS;
        }else {
            String show = "SELECT * FROM system_positionuser WHERE userId = '" +Database.encodeSQL( positionUserPO.getUserId()) + "'";
            List ls = MySQLDao.query(show, positionUserPO.getClass(), null);
            if (ls.size() > 0) {
                String update = "UPDATE system_positionuser SET states = 0 WHERE userId = '" +Database.encodeSQL( positionUserPO.getUserId()) + "'";
                MySQLDao.update(update);
                update = "UPDATE system_positionuser SET states = 1 WHERE userId = '"+Database.encodeSQL(positionUserPO.getUserId())+"' AND id = '"+Database.encodeSQL(positionUserPO.getId())+"'";
                MySQLDao.update(update);
            }
            return SUCCESS;
        }
    }



    public ReturnObject getResult() {
        return result;
    }

    public void setResult(ReturnObject result) {
        this.result = result;
    }

    public PositionUserPO getPositionUser() {
        return positionUser;
    }

    public void setPositionUser(PositionUserPO positionUser) {
        this.positionUser = positionUser;
    }

    public PositionUserVO getPositionUserVO() {
        return positionUserVO;
    }

    public void setPositionUserVO(PositionUserVO positionUserVO) {
        this.positionUserVO = positionUserVO;
    }

    public com.youngbook.entity.po.system.PositionUserPO getPositionUserPO() {
        return positionUserPO;
    }

    public void setPositionUserPO(com.youngbook.entity.po.system.PositionUserPO positionUserPO) {
        this.positionUserPO = positionUserPO;
    }
}
