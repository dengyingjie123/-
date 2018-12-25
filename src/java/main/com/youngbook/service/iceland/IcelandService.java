package com.youngbook.service.iceland;

import com.youngbook.common.Database;
import com.youngbook.common.config.AesEncrypt;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.ExcelUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.dao.MySQLDao;
import com.youngbook.dao.customer.ICustomerPersonalDao;
import com.youngbook.dao.system.IUserDao;
import com.youngbook.dao.system.UserDaoImpl;
import com.youngbook.entity.iceland.CallCommentPO;
import com.youngbook.entity.iceland.CommonSentencePO;
import com.youngbook.entity.iceland.CustomerOrderReviewPO;
import com.youngbook.entity.iceland.CustomerPO;
import com.youngbook.entity.po.UserPO;
import com.youngbook.entity.po.customer.CustomerFeedbackPO;
import com.youngbook.entity.po.customer.CustomerPersonalPO;
import com.youngbook.entity.vo.customer.CustomerVO;
import com.youngbook.service.BaseService;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.sql.Connection;
import java.util.List;

/**
 * Created by leevits on 3/7/2017.
 */
@Component("icelandService")
public class IcelandService extends BaseService {

    @Autowired
    ICustomerPersonalDao customerPersonalDao;

    public List<CustomerVO> getListCustomerVO(String salesmanId, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("32DE1802");
        dbSQL.addParameter4All("salesmanId", salesmanId);
        dbSQL.initSQL();

        List<CustomerVO> list = MySQLDao.search(dbSQL, CustomerVO.class, conn);

        return list;
    }

    public CallCommentPO loadCallCommentPO(String callCommentId, Connection conn) throws Exception {

        List<CallCommentPO> list = getListCallCommentPO(callCommentId, null, null, conn);

        if (list != null && list.size() == 1) {
            return list.get(0);
        }

        return null;
    }

    public List<CommonSentencePO> getListCommonSentencePO(String commonSentenceId, String salesmanId, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("066A1802");
        dbSQL.addParameter4All("salesmanId", salesmanId);
        dbSQL.addParameter4All("commonSentenceId", commonSentenceId);
        dbSQL.initSQL();

        List<CommonSentencePO> list = MySQLDao.search(dbSQL, CommonSentencePO.class, conn);
        return list;
    }

    public CommonSentencePO loadCommonSentencePO(String CommonSentenceId, Connection conn) throws Exception {

        List<CommonSentencePO> listCommonSentencePO = getListCommonSentencePO(CommonSentenceId, null, conn);

        if (listCommonSentencePO != null && listCommonSentencePO.size() == 1) {
            return listCommonSentencePO.get(0);
        }

        return null;
    }

    public List<CallCommentPO> getListCallCommentPO(String callCommentId, String comment, String bizId, Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("FC661802");
        dbSQL.addParameter4All("callCommentId", callCommentId);
        dbSQL.addParameter4All("comment", comment);
        dbSQL.addParameter4All("bizId", bizId);
        dbSQL.initSQL();

        List<CallCommentPO> list = MySQLDao.search(dbSQL, CallCommentPO.class, conn);

        return list;
    }

    public CustomerFeedbackPO saveNewCallComment(String comment, String bizId, String customerId, String userId, Connection conn) throws Exception {

        CustomerPersonalPO customerPersonalPO = customerPersonalDao.loadByCustomerPersonalId(customerId, conn);

        CustomerFeedbackPO customerFeedbackPO = new CustomerFeedbackPO();
        customerFeedbackPO.setContent(comment);
        customerFeedbackPO.setCustomerId(customerId);
        customerFeedbackPO.setCustomerName(customerPersonalPO.getName());
        customerFeedbackPO.setFeedbackManId(userId);
        customerFeedbackPO.setBizId(bizId);
        customerFeedbackPO.setTime(TimeUtils.getNow());

        MySQLDao.insertOrUpdate(customerFeedbackPO, conn);

        return customerFeedbackPO;

    }

    public List<CustomerOrderReviewPO> getListCustomerOrderReview(Connection conn) throws Exception {

        DatabaseSQL dbSQL = DatabaseSQL.newInstance("getListCustomerOrderReview", this);
        dbSQL.initSQL();

        List<CustomerOrderReviewPO> list = MySQLDao.search(dbSQL, CustomerOrderReviewPO.class, conn);


        for (int i = 0; list != null && i < list.size(); i++) {
            CustomerOrderReviewPO customerOrderReviewPO = list.get(i);

            String idCard = customerOrderReviewPO.getIdCard();

            if (!StringUtils.isEmpty(idCard)) {
                idCard = AesEncrypt.decrypt(idCard);

                idCard = StringUtils.lastString(idCard, 4);

                customerOrderReviewPO.setIdCard(idCard);
            }
        }

        return list;
    }




    public static void main(String [] args) throws Exception {


    }


    public void importCustomer(String path) throws Exception {


        FileInputStream fileInputStream = new FileInputStream(path);
        HSSFWorkbook wb = new HSSFWorkbook(fileInputStream);
        HSSFSheet sheet = wb.getSheet("Sheet1");

        int rowCount = ExcelUtils.getRowCount(sheet);

        System.out.println(rowCount);

        IUserDao userDao = Config.getBeanByName("userDao", UserDaoImpl.class);

        Connection conn = Config.getConnection();
        try {
            for (int i = 0; i < rowCount; i++) {
                String name = ExcelUtils.getCellStringValue("A" + (i + 1), sheet);
                String mobile = ExcelUtils.getCellStringValue("b" + (i + 1), sheet);
                String salesmanName = ExcelUtils.getCellStringValue("c" + (i + 1), sheet);

                UserPO userPO = userDao.loadUserByName(salesmanName, conn);

                CustomerPO customerPO = new CustomerPO();
                customerPO.setCustomerName(name);
                customerPO.setMobile(mobile);
                customerPO.setManagerId(userPO.getId());

                MySQLDao.insertOrUpdate(customerPO, conn);
            }
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            Database.close(conn);
        }


    }
}
