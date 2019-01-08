package com.youngbook.service.task;

import com.youngbook.common.Database;
import com.youngbook.common.MyException;
import com.youngbook.common.config.AesEncrypt;
import com.youngbook.common.config.Config;
import com.youngbook.common.database.DatabaseSQL;
import com.youngbook.common.utils.ExcelUtils;
import com.youngbook.common.utils.IdCardUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.runner.Task;
import com.youngbook.dao.MySQLDao;
import com.youngbook.entity.po.customer.CustomerCatalog;
import com.youngbook.entity.po.customer.CustomerCertificatePO;
import com.youngbook.entity.po.customer.CustomerPersonalPO;
import com.youngbook.entity.po.system.LogPO;
import com.youngbook.service.customer.CustomerPersonalService;
import com.youngbook.service.system.LogService;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 2016/7/2.
 */
public class CustomerTask extends Task {

    CustomerPersonalService customerPersonalService = Config.getBeanByName("customerPersonalService", CustomerPersonalService.class);

    LogService logService = Config.getBeanByName("logService", LogService.class);


    /**
     * 模版保存于 src\webapp\include\exportTemplates\customerTask.xls
     * @param path
     * @throws Exception
     */
    public void doImport(String path) throws Exception {
        HSSFSheet sheet = ExcelUtils.openSheet("Sheet1", path);

        int rowCount = ExcelUtils.getRowCount(sheet);

        List<CustomerPersonalPO> customerPersonalPOs = new ArrayList<CustomerPersonalPO>();

        int offset = 1;

        for (int i = 0; i < rowCount; i++) {
            int index = i + 1 + offset;
            String name = ExcelUtils.getCellStringValue("a" + index, sheet);
            String mobile = ExcelUtils.getCellStringValue("b" + index, sheet);
            String idCard = ExcelUtils.getCellStringValue("c" + index, sheet);
            String comment = ExcelUtils.getCellStringValue("d" + index, sheet);
            String sex = ExcelUtils.getCellStringValue("e" + index, sheet);
            String referralCode = ExcelUtils.getCellStringValue("f" + index, sheet);

            CustomerPersonalPO customerPersonalPO = new CustomerPersonalPO();
            customerPersonalPO.setName(name);
            customerPersonalPO.setMobile(mobile);
            customerPersonalPO.setSex(sex);
            customerPersonalPO.setReferralCode(referralCode);


            // 身份证号
            if (!StringUtils.isEmpty(idCard)) {
                customerPersonalPO.setIdentityCardAddress(idCard);
            }

            if (!StringUtils.isEmpty(comment)) {
                customerPersonalPO.setRemark(comment);
            }




            customerPersonalPOs.add(customerPersonalPO);
        }


        CustomerTask customerTask = new CustomerTask();
        customerTask.importCustomer(customerPersonalPOs);
    }


    private void importCustomer(List<CustomerPersonalPO> customerPersonalPOs) throws Exception {

        Connection conn = Config.getConnection();


        try {
            conn.setAutoCommit(false);
            for (int i = 0; customerPersonalPOs != null && i < customerPersonalPOs.size(); i++) {
                String parameters = "";
                try {
                    LogPO logPO = new LogPO();
                    logPO.setName("导入客户");


                    CustomerPersonalPO customerPersonalPO = customerPersonalPOs.get(i);
                    parameters = customerPersonalPO.toJsonObject().toString();


                    if (StringUtils.isEmpty(customerPersonalPO.getMobile())) {
                        System.out.println("Exception : Mobile is null");
                        continue;
                    }


                    customerPersonalPO.setPassword("123456");

                    CustomerPersonalPO tempCustomer = customerPersonalService.registerCustomer(customerPersonalPO, customerPersonalPO.getReferralCode(), conn);


                    tempCustomer.setRemark(customerPersonalPO.getRemark());

                    if (!StringUtils.isEmpty(customerPersonalPO.getSex())) {
                        tempCustomer.setSex(customerPersonalPO.getSex());
                    }


                    if (!StringUtils.isEmpty(customerPersonalPO.getIdentityCardAddress())) {

                        String idCard = AesEncrypt.decrypt(customerPersonalPO.getIdentityCardAddress());
                        String birthday = IdCardUtils.getBirthByIdCard(idCard);
                        tempCustomer.setBirthday(birthday);



                        CustomerCertificatePO customerCertificatePO = new CustomerCertificatePO();
                        customerCertificatePO.setCustomerId(tempCustomer.getId());
                        customerCertificatePO.setName("98");
                        customerCertificatePO.setNumber(customerPersonalPO.getIdentityCardAddress());

                        MySQLDao.insertOrUpdate(customerCertificatePO, conn);
                    }

                    MySQLDao.insertOrUpdate(tempCustomer, conn);


                    logPO.setMachineMessage(parameters);

                    logService.save(logPO, conn);
                }
                catch (Exception ex) {
                    LogPO logException = new LogPO();
                    logException.setName("导入客户失败");
                    logException.setMachineMessage(parameters);
                    logException.setPeopleMessage(ex.getMessage());
                    logService.save(logException);

                    throw ex;
                }

            }

            conn.commit();
        }
        catch (Exception e) {
            conn.rollback();
            throw e;
        }
        finally {
            Database.close(conn);
        }


    }

    public void test() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("d:/temp-1.xls");
        HSSFWorkbook wb = new HSSFWorkbook(fileInputStream);
        HSSFSheet sheet = wb.getSheet("龙虎榜");

        HSSFSheet sheetStyle = wb.getSheet("模版");

        short rowStyleDefaultHeight = 600;
        sheet.setDefaultRowHeight(rowStyleDefaultHeight);


        HSSFRow row2Style = ExcelUtils.getRow(3, sheetStyle);

        int offset = 3;
        for (int i = 0; i < 10; i++) {

            int index = offset + i;

            String name = "name" + i;

            ExcelUtils.newRow(sheet, index, row2Style);

            ExcelUtils.setCellValue("b" + index, name, sheet);

            ExcelUtils.addMergedRegion("b3", "f3", sheet);
        }

        FileOutputStream out = new FileOutputStream("d:/temp-2.xls");
        wb.write(out);
        out.flush();
        out.close();
    }

    public void task() throws Exception {

        String [] sheetNames = new String[] {"新富1号","恒晟水电","岔河水电","岔河水电2号","瑞富一号","金橙1号","金橙3号","金猴1号","金橙4号"};

        FileInputStream fileInputStream = new FileInputStream("d:/sheets.xls");
        HSSFWorkbook wb = new HSSFWorkbook(fileInputStream);

        Connection conn = Config.getConnection();

        try {


            for (String sheetName : sheetNames) {
                HSSFSheet sheet = wb.getSheet(sheetName);

                int index = 2;
                while (true) {


                    String customerName = ExcelUtils.getCellStringValue("b" + index, sheet);


                    if (StringUtils.isEmpty(customerName)) {
                        break;
                    }



                    DatabaseSQL dbSQL = DatabaseSQL.getInstance("select * from crm_customercertificate cc where cc.state=0 and cc.CustomerId=(select id from crm_customerpersonal where state=0 and name=? limit 1)").addParameter(1, customerName);

                    List<CustomerCertificatePO> list = MySQLDao.search(dbSQL, CustomerCertificatePO.class, conn);

                    if (list == null || list.size() != 1) {
                        index++;
                        continue;
                    }

                    CustomerCertificatePO cc = list.get(0);

                    String code =  AesEncrypt.decrypt(cc.getNumber());

                    System.out.println(customerName + " " + code);

                    ExcelUtils.setCellValue("f" + index, code, sheet);
                    ExcelUtils.setCellValue("e" + index, "身份证", sheet);
                    index++;
                }


            }

            FileOutputStream out = new FileOutputStream("d:/sheet-2.xls");
            wb.write(out);
            out.flush();
            out.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            Database.close(conn);
        }



    }
}
