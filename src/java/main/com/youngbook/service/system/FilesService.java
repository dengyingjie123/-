package com.youngbook.service.system;

import com.youngbook.common.*;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.system.IFilesDao;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.system.FilesPO;
import com.youngbook.entity.vo.system.FilesVO;
import com.youngbook.service.BaseService;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015/4/3.
 */
@Component("filesService")
public class FilesService extends BaseService {

    @Autowired
    IFilesDao filesDao;

    public String getMaskOfPath() throws Exception {
        return Config.getSystemConfig("system.uploadPath.mask");
    }


    public static String getRealPath() throws Exception {

        return null;
    }

    public FilesPO loadById(String filesId, Connection conn) throws Exception {

        if (!StringUtils.isEmpty(filesId)) {

            FilesPO filesPO = new FilesPO();
            filesPO.setId(filesId);
            filesPO.setState(Config.STATE_CURRENT);

            filesPO = MySQLDao.load(filesPO, FilesPO.class, conn);

            return filesPO;

        }

        return null;
    }

    public FilesPO loadByModuleBizId(String moduleId, String bizId, Connection conn) throws Exception {
        return filesDao.loadByModuleBizId(moduleId, bizId, conn);
    }

    public List<FilesPO> getListFiles(String bizId, String moduleId, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("getListFiles", this);
        dbSQL.addParameter4All("moduleId", moduleId);
        dbSQL.addParameter4All("bizId", bizId);
        dbSQL.initSQL();

        List<FilesPO> list = MySQLDao.search(dbSQL, FilesPO.class, conn);

        return list;
    }


    public String getRootPathOfUpload() throws Exception {

        return Config.getSystemConfig("system.uploadPath");

    }

    public String getFullPathOfDownload(String filePath) throws Exception {


        return null;
    }

    /**
     * 将数据库中的文件保存路径改为相对路径，根据正则匹配来删去路径前缀
     *
     * @param conn
     * @throws Exception
     */
    public void changeFilePath2Relative(Connection conn) throws Exception {

        // 当前在system_files表中的路径名有5种
        //--D:/work/03_deploy/webapp/app.hope-wealth.com_80/core/upload/...
        //--D:/work/03_deploy/webapp/hopewealth_master/core/upload/...
        //--d:/work/11_upload/webapp/...
        //--D:/work/11_upload/webapp/hopewealth_master/...
        //--V:/work/02_server/apache/apache-tomcat-6.0.41_sz/webapps/core/upload/...
        //相对路径都以/yyyy/mm/dd/开始
        DatabaseSQL dbSQL = DatabaseSQL.getInstance("select * from System_Files where state=0");

        List<FilesPO> list = MySQLDao.search(dbSQL, FilesPO.class, conn);

        int cntEmpty = 0;
        int cntIrregular = 0;
        int cntValid = 0;
        Set<String> prefixSet = new HashSet<String>();
        String regex = "/201[0-9]/[0|1][0-9]/[0-3][0-9]/";
        Pattern pattern = Pattern.compile(regex);
        for (FilesPO f : list) {
            String oldPath = f.getPath();
            if (StringUtils.isEmpty(oldPath)) {
                cntEmpty++;
                continue;
            }

            Matcher matcher = pattern.matcher(oldPath);
            if (!matcher.find()) {
                cntIrregular++;
                continue;
            }

            cntValid++;
            int idx = matcher.start(0);
            prefixSet.add(oldPath.substring(0, idx));
            System.out.println(oldPath.substring(idx));

            String newPath = oldPath.substring(idx);
            f.setPath(newPath);
            if (MySQLDao.insertOrUpdate(f, conn) != 1) {
                MyException.newInstance("保存信息失败").throwException();
            }
        }
//        System.out.println(" 空路径：" + cntEmpty);
//        System.out.println(" 不规则：" + cntIrregular);
//        System.out.println(" 规则：" + cntValid);
//        System.out.println();
//        System.out.println("绝对路径类型: " + prefixSet.size());
//        for (String prefix: prefixSet) {
//            System.out.println("  " + prefix);
//        }
    }


    public void changeFilePath(Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.getInstance("select * from System_Files where state=0");

        List<FilesPO> list = MySQLDao.search(dbSQL, FilesPO.class, conn);

        int count = 0;
        for (FilesPO f : list) {

            // upload/2015
            // upload/2016
            // webapp/2016

            String newPath = "";

            String path = f.getPath();
            String pathPrefix = "upload/2015";
            if (path.indexOf(pathPrefix) != -1) {
                int begin = path.indexOf(pathPrefix);
                newPath = path.substring(begin + 7);
                System.out.println(newPath);
                count++;
            }

            pathPrefix = "upload/2016";
            if (path.indexOf(pathPrefix) != -1) {
                int begin = path.indexOf(pathPrefix);
                newPath = path.substring(begin + 7);
                System.out.println(newPath);
                count++;
            }

            pathPrefix = "webapp/2016";
            if (path.indexOf(pathPrefix) != -1) {
                int begin = path.indexOf(pathPrefix);
                newPath = path.substring(begin + 7);
                System.out.println(newPath);
                count++;
            }

            f.setPath(newPath);

            if (MySQLDao.insertOrUpdate(f, conn) != 1) {
                MyException.newInstance("保存信息失败").throwException();
            }

        }

        System.out.println(count);

    }


    //获取数据集合的方法
    public Pager list(FilesVO filesVO, List<KVObject> conditions) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        QueryType queryType = new QueryType(Database.QUERY_FUZZY, Database.NUMBER_EQUAL);

        //sql查询语句
        StringBuffer SQL = new StringBuffer();
//        SQL.append("SELECT distinct f.*, u.`name` operatorName");
//        SQL.append(" FROM System_Files f,system_user u ");
//        SQL.append(" WHERE 1 = 1 AND f.state = 0 AND u.state = 0 AND f.operatorId = u.Id");

        SQL.append(" SELECT distinct f.*, u.`name` operatorName ,");
        SQL.append(" kv_Modul.k as ModuleIdNAME,kv_Modul.v as moduleDescriptionName");
        SQL.append(" FROM System_Files f,system_user u ,system_kv kv_Modul");
        SQL.append(" WHERE 1 = 1 AND f.state = 0 AND u.state = 0 AND f.operatorId = u.Id");
        SQL.append(" AND kv_Modul.GroupName='ModuleGroup'");
        SQL.append(" AND kv_Modul.k=f.ModuleId");

        conditions.add(new KVObject(Database.CONDITION_TYPE_ORDERBY, "createTime " + Database.ORDERBY_DESC));
        Pager pager = Pager.query(SQL.toString(), filesVO, conditions, request, queryType);

        return pager;
    }


    //删除的方法
    public int delete(FilesPO files, UserPO user, Connection conn) throws Exception {
        int count = 0;

        files = MySQLDao.load(files, FilesPO.class);
        files.setState(Config.STATE_UPDATE);
        count = MySQLDao.update(files, conn);
        if (count == 1) {
            files.setSid(MySQLDao.getMaxSid("System_Files", conn));
            files.setState(Config.STATE_DELETE);
            files.setOperateTime(TimeUtils.getNow());
            files.setOperatorId(user.getId());
            count = MySQLDao.insert(files, conn);
        }

        if (count != 1) {
            throw new Exception("数据库异常");
        }

        return count;
    }
}
