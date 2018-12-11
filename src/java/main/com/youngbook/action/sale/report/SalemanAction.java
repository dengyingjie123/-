package com.youngbook.action.sale.report;

import com.youngbook.action.BaseAction;
import com.youngbook.common.Pager;
import com.youngbook.common.config.Config;
import com.youngbook.common.utils.ExcelUtils;
import com.youngbook.common.utils.StringUtils;
import com.youngbook.common.utils.TimeUtils;
import com.youngbook.entity.vo.Sale.ViewOrderVO;
import com.youngbook.service.sale.report.SalesmanStockService;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.util.List;

/**
 *
 * @author: 徐明煜
 * @version:1.1
 * @create: 2018-11-28 16:12
 */
public class SalemanAction extends BaseAction {

    @Autowired
    private SalesmanStockService salesmanStockService;

    /**
     * @description 向前台发送特定销售人员的存量
     * @author 徐明煜
     * @date 2018/12/2 9:36
     * @param
     * @return java.lang.String
     * @throws Exception
     */
    public String specficSalemanStockReport() throws Exception {

        /**
         * 获取查询的销售人员姓名，获得查询sql语句，未传时查询全部
         */
        String selectName = getHttpRequestParameter("selectName");
        if(selectName != null){
            selectName = new String(selectName.getBytes(StringUtils.Encode_ISO_8859_1),StringUtils.Encode_UTF_8);
        }
        Pager pager = Pager.getInstance(getRequest());
        ViewOrderVO viewOrderVO = new ViewOrderVO();
        Pager pagerViewOrderVOByName = salesmanStockService.pagerViewOrderVoByName(selectName, viewOrderVO, pager, getConnection());



        getResult().setReturnValue(pagerViewOrderVOByName.toJsonObject());
        return SUCCESS;
    }


    /**
     * @description 导出选中销售人员的客户存量
     * @author 徐明煜
     * @date 2018/12/2 16:37
     * @param
     * @return java.lang.String
     * @throws Exception
     */
    public String exportSalemanStock() throws Exception {

        /**
         *  从前台获得销售人员名称，转换编码格式组成导出文件名称
         */
        String selectName = getHttpRequestParameter("selectName");
        selectName = StringUtils.httpEncodeConversion(selectName, StringUtils.Encode_ISO_8859_1);
        String fileName = selectName + TimeUtils.getNowDate();
        fileName = StringUtils.httpEncodeConversion(selectName, StringUtils.Encode_UTF_8);




        /**
         *  以流的形式下载文件,可设置多个格式
         */
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/octet-stream");

        //设置Excel导出文件名称
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");

        //获取输出流
        ServletOutputStream out = response.getOutputStream();


        /**
         * 获取选择的销售人员的客户存量，导出到excel中
         */
        try {
            FileInputStream fileInputStream = new FileInputStream(Config.getSystemConfig("specificSalemanStock"));
            HSSFWorkbook wb = new HSSFWorkbook(fileInputStream);

            String sheetName = "Sheet1";
            HSSFSheet sheet = wb.getSheet(sheetName);
            HSSFRow templateRow = ExcelUtils.getRow(1, sheet);

            // ExcelUtils.addMergedRegion("a1", "f1", sheet);
            ExcelUtils.setCellValue("a1", selectName + "的客户存量", sheet);


            List<ViewOrderVO> viewOrderVOList = salesmanStockService.listViewOrderVOByName(selectName, ViewOrderVO.class, getConnection());

            int offset = 3;
            for(int i = 0; viewOrderVOList != null && i < viewOrderVOList.size(); i++){
                ExcelUtils.setCellValue("a" + (offset + i), viewOrderVOList.get(i).getOrderNum(), sheet);
                ExcelUtils.setCellValue("b" + (offset + i), viewOrderVOList.get(i).getCustomerName(), sheet);
                ExcelUtils.setCellValue("c" + (offset + i), viewOrderVOList.get(i).getProductionName(), sheet);
                ExcelUtils.setCellValue("d" + (offset + i), viewOrderVOList.get(i).getMoney(), sheet);
                ExcelUtils.setCellValue("f" + (offset + i), viewOrderVOList.get(i).getPaymentPlanLastTime(), sheet);
                ExcelUtils.setCellValue("f" + (offset + i), viewOrderVOList.get(i).getPayTime(), sheet);
            }

            ExcelUtils.removeSheetsExcept(wb, sheetName);
            wb.write(out);
            out.flush();
            out.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            out.flush();
            out.close();
        }

        return SUCCESS;
    }
}
