package com.youngbook.common.utils;


import com.youngbook.common.Pager;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTRst;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Lee on 2016/1/10.
 */
public class ExcelUtils {


    public static void main(String [] args) throws Exception {
        String path = "C:\\Users\\Lee\\Dropbox\\doc\\CindyLily\\001.xlsx";
//        XSSFSheet sheet = ExcelUtils.openSheetX("Sheet1", "C:\\Users\\Lee\\Dropbox\\doc\\CindyLily\\001.xlsx");

        XSSFSheet sheet = ExcelUtils.openSheetX("Schedule", path);




        int rowCount = ExcelUtils.getRowCountX(sheet);

        for (int i = 0; i < rowCount; i++) {
            int index = i + 1;
            XSSFCell cell = ExcelUtils.getCellX("c" + index, sheet);
////            cell.getRawValue()
//            System.out.println(cell.getRawValue());

            try {
                System.out.println(index + " : " + cell.getStringCellValue());
            }
            catch (Exception e) {
                System.out.println(index + " ERROR");

            }

//            System.out.println(index);
        }




    }

    public static int getRowCount(HSSFSheet sheet) throws Exception {
        if (sheet != null && sheet.getLastRowNum() > 1) {
            return sheet.getLastRowNum();
        }
        return 0;
    }

    public static int getRowCountX(XSSFSheet sheet) throws Exception {
        if (sheet != null && sheet.getLastRowNum() > 1) {
            return sheet.getLastRowNum();
        }
        return 0;
    }

    public static HSSFSheet openSheet(String sheetName, String excelFile) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(excelFile);
        HSSFWorkbook wb = new HSSFWorkbook(fileInputStream);
        HSSFSheet sheet = wb.getSheet(sheetName);
        return sheet;
    }

    public static XSSFSheet openSheetX(String sheetName, String excelFile) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(excelFile);
        XSSFWorkbook wb = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = wb.getSheet(sheetName);
        return sheet;
    }

    public static void addMergedRegion(String begin, String end, HSSFSheet sheet) {
        int beginCellNumber = fromNumberSystem26(getFromNumberSystem26Letters(begin)) - 1;
        int beginRowNumber = getFromNumberSystem26Number(begin) - 1;

        int endCellNumber = fromNumberSystem26(getFromNumberSystem26Letters(end)) - 1;
        int endRowNumber = getFromNumberSystem26Number(end) - 1;

        CellRangeAddress cellRangeAddress = new CellRangeAddress(beginRowNumber, endRowNumber, beginCellNumber, endCellNumber);
        sheet.addMergedRegion(cellRangeAddress);
    }

    private static String getFromNumberSystem26Letters(String s) {

        s = s.toUpperCase();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c < 'A' || c > 'Z') {
                return s.substring(0, i);
            }
        }

        return null;
    }

    private static int getFromNumberSystem26Number(String s) {

        s = s.toUpperCase();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c < 'A' || c > 'Z') {
                return Integer.parseInt(s.substring(i, s.length()));
            }
        }

        return Integer.MAX_VALUE;
    }

    private static int fromNumberSystem26(String s){
        if (StringUtils.isEmpty(s)) {
            return 0;
        }

        s = s.toUpperCase();
        int n = 0;
        for (int i = s.length() - 1, j = 1; i >= 0; i--, j *= 26){
            char c = s.charAt(i);
            if (c < 'A' || c > 'Z') return 0;
            n += ((int)c - 64) * j;
        }
        return n;
    }

    public static void setCellValue(String letter, int number, Object value, HSSFSheet sheet) throws Exception {

        HSSFCell cell = ExcelUtils.getCell(letter, number, sheet);

        if (value == null) {
            return;
        }

        if (value.getClass().isAssignableFrom(String.class)) {
            cell.setCellValue((String)value);
        }
        else {
            throw new Exception("赋值类型不支持");
        }
    }

    public static HSSFRow newRow(HSSFSheet sheet, int number, HSSFRow template) throws Exception {
        HSSFRow row = sheet.createRow(number - 1);
        row.setHeight(template.getHeight());
        for (int i = 0; template != null && i < template.getPhysicalNumberOfCells(); i++) {
            HSSFCell cellTemplate = template.getCell(i);
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(cellTemplate.getCellStyle());
        }
        return row;
    }

    public static HSSFRow newRow(HSSFSheet sheet, int number) throws Exception {
        HSSFRow row = sheet.createRow(number - 1);

        return row;
    }

    public static void setCellValue(String letterAndNumber, Object value, HSSFSheet sheet) throws Exception {
        setCellValue(letterAndNumber, value, sheet, 0);
    }


    /**
     *
     * @param letterAndNumber
     * @param value
     * @param sheet
     * @param cellValueType 1：富文本;Other：其他
     * @throws Exception
     */
    public static void setCellValue(String letterAndNumber, Object value, HSSFSheet sheet, int cellValueType) throws Exception {

        String letter = ExcelUtils.getFromNumberSystem26Letters(letterAndNumber);
        int number = ExcelUtils.getFromNumberSystem26Number(letterAndNumber);

        HSSFCell cell = ExcelUtils.getCell(letter, number, sheet);


        if (cell == null) {
//            throw new Exception("找不到对应的元素");
        }

        if (value == null) {
            return;
        }

        if (value.getClass().isAssignableFrom(String.class)) {

            if (cellValueType == 1) {
                cell.setCellValue(new HSSFRichTextString((String)value));
            }
            else {
                cell.setCellValue((String)value);
            }

        }
        else if (value.getClass().isAssignableFrom(Double.class)) {
            cell.setCellValue(Double.parseDouble(value.toString()));
        }
        else if (value.getClass().isAssignableFrom(Integer.class)) {
            cell.setCellValue(Integer.parseInt(value.toString()));
        }
        else {
            throw new Exception("赋值类型不支持");
        }
    }

    public static String getCellStringValue(String letterAndNumber, HSSFSheet sheet) throws Exception {

        String letter = ExcelUtils.getFromNumberSystem26Letters(letterAndNumber);
        int number = ExcelUtils.getFromNumberSystem26Number(letterAndNumber);

        HSSFCell cell = ExcelUtils.getCell(letter, number, sheet);

        if (cell == null) {
            return "";
        }

        // 数字类型
        if (cell.getCellType() == 0) {
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }

        return cell.getStringCellValue();


    }

    public static HSSFRow getRow(int number, HSSFSheet sheet) {
        HSSFRow row = sheet.getRow(number - 1);
        return row;
    }

    public static XSSFRow getRowX(int number, XSSFSheet sheet) {
        XSSFRow row = sheet.getRow(number - 1);
        return row;
    }

    public static HSSFCell getCell(String letter, HSSFRow row) {

        int cellInex = ExcelUtils.fromNumberSystem26(letter) - 1;

        HSSFCell cell = row.getCell(cellInex);

        if (cell == null) {
            cell = row.createCell(cellInex);
        }


        return cell;
    }

    public static XSSFCell getCellX(String letter, XSSFRow row) {

        int cellindex = ExcelUtils.fromNumberSystem26(letter) - 1;

        XSSFCell cell = row.getCell(cellindex);


        return cell;
    }

    public static HSSFCell getCell(String letterAndNumber, HSSFSheet sheet) throws Exception {
        String letter = ExcelUtils.getFromNumberSystem26Letters(letterAndNumber);
        int number = ExcelUtils.getFromNumberSystem26Number(letterAndNumber);

        HSSFCell cell = ExcelUtils.getCell(letter, number, sheet);

        return cell;
    }

    public static XSSFCell getCellX(String letterAndNumber, XSSFSheet sheet) {
        String letter = ExcelUtils.getFromNumberSystem26Letters(letterAndNumber);
        int number = ExcelUtils.getFromNumberSystem26Number(letterAndNumber);

        XSSFCell cell = ExcelUtils.getCellX(letter, number, sheet);

        return cell;
    }

    public static HSSFCell getCell(String letter, int number, HSSFSheet sheet) throws Exception {

        //HSSFRow row = sheet.getRow(number - 1);

        HSSFRow row = ExcelUtils.getRow(number, sheet);

        if (row == null) {
            row = ExcelUtils.newRow(sheet, number);
        }

        if (row == null) {
            return null;
        }

        HSSFCell cell = ExcelUtils.getCell(letter, row);

        return cell;
    }

    public static XSSFCell getCellX(String letter, int number, XSSFSheet sheet) {

        //HSSFRow row = sheet.getRow(number - 1);

        XSSFRow row = ExcelUtils.getRowX(number, sheet);

        if (row == null) {
            return null;
        }

        XSSFCell cell = ExcelUtils.getCellX(letter, row);

        return cell;
    }

    public static HSSFCell getCell(String letter, int number, HSSFSheet sheet, HSSFSheet sheetStyle, int offset) throws Exception{

        HSSFRow row = sheet.getRow(number - 1);

        if (row == null) {
            HSSFRow templateRow = ExcelUtils.getRow(offset, sheetStyle);
            row = ExcelUtils.newRow(sheet, (number - 1), templateRow);
        }

        if (row == null) {
            return null;
        }

        HSSFCell cell = ExcelUtils.getCell(letter, row);

        return cell;
    }

    public static void removeSheetsExcept( HSSFWorkbook wb, String sheetName) {
        int i = 0;
        while (wb.getNumberOfSheets() > 1) {
            if (!wb.getSheetName(i).equals(sheetName)) {
                wb.removeSheetAt(i);
                continue;
            }
            i++;
        }
    }
}
