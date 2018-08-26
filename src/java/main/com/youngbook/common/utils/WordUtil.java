package com.youngbook.common.utils;

import com.lowagie.text.Document;
import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.youngbook.common.MyException;
import com.youngbook.common.config.Config;
import com.youngbook.entity.po.production.ProductionAgreementPO;
import com.youngbook.entity.po.sale.PaymentPlanPO;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlToken;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;
import java.util.List;

public class WordUtil {

    public static final String IMAGE_ = "IMAGE_";
    private static final String EL_LEFT = "<";
    private static final String EL_RIGHT = ">";
    private XWPFDocument document = null;
    private List<String> noSNTable = null;

    /**
     * Read and write word by template
     * @param templatePath The path of the word template
     * @param map The rule that is how to read and write the word for example:
     * e.g string value replace: The map's key is the text which is ready to
     *     repalce in the word and value of the map is a text insert into word.
     * e.g image insert: The map's key must start with 'IMAGE_' and value of
     *     the map must be put a path of a image.
     * e.g list value insert: The map's key is the name of a table in word.
     *     The value of the map must be a instance of calss List and the members
     *     of list is a instance of calss Map and this map as the string value replace map
     * @return
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException
     */
//    public String generateWordFromTemplate(String templatePath,
//                                            Map<String, Object> map) throws FileNotFoundException,
//                                                                            IOException,
//                                                                            InvalidFormatException {
//
//
//        document = new XWPFDocument(POIXMLDocument.openPackage(templatePath));
//        return readWordDocument(map);
//    }

    /**
     * Read and write word by template in inputStream
     * @param inputStream
     * @param map
     * @return
     * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
//    public String generateWordFromTemplate(java.io.InputStream inputStream,
//                                            Map<String, Object> map) throws InvalidFormatException,
//                                                                            IOException {
//        document = new XWPFDocument(inputStream);
//        return readWordDocument(map);
//    }

//    private String readWordDocument(Map<String, Object> map) throws InvalidFormatException,
//                                                               FileNotFoundException,
//                                                               IOException {
//        // replace the text of header
//        if (document.getHeaderList() != null &&
//            document.getHeaderList().size() > 0) {
//            XWPFHeader header = document.getHeaderArray(0);
//            List<XWPFParagraph> listHeader = header.getParagraphs();
//            for (XWPFParagraph paragraph : listHeader) {
//                replaceWordText(paragraph, map);
//            }
//        }
//        // replace the text of body
//        Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();
//        while (itPara.hasNext()) {
//            XWPFParagraph paragraph = itPara.next();
//            replaceWordText(paragraph, map);
//            //                wordReadRule.addPictureInWord(document);
//        }
//        // replace the text of table
//        Iterator<XWPFTable> itTable = document.getTablesIterator();
//        while (itTable.hasNext()) {
//            XWPFTable table = itTable.next();
//            readWordTableRangeRule(table, map);
//
//        }
//        XWPFWordExtractor extractor = new XWPFWordExtractor(document);
//        String text = extractor.getText();
//        return text;
//    }

    /**
     * Out Word Table Range Rule
     * @param outputPath
     */
    public FileOutputStream outWordDocument(String outputPath) throws IOException {

        FileOutputStream fos = new FileOutputStream(new File(outputPath));

        return fos;
    }

    /**
     * Read Word Table Range Rule
     * @param table
     * @param mapProperties
     */
    private void readWordTableRangeRule(XWPFTable table,
                                        Map<String, Object> mapProperties) {
        List<Map<String, String>> hasCellContant =
            new ArrayList<Map<String, String>>();
        int rcount = table.getNumberOfRows();
        boolean isListValue = false;
        boolean noSN = false;
        for (int i = 0; i < rcount; i++) {
            XWPFTableRow row = table.getRow(i);
            List<XWPFTableCell> cells = row.getTableCells();
            for (int j = 0; j < cells.size(); j++) {
                XWPFTableCell cell = cells.get(j);
                String cellTextString = cell.getText();
                for (Map.Entry<String, Object> entry :
                     mapProperties.entrySet()) {
                    String paramName = EL_LEFT + entry.getKey() + EL_RIGHT;
                    if (cellTextString.contains(paramName)) {
                        String replaceText = "";
                        if (entry.getValue() instanceof List) {
                            if (getNoSNTable().contains(entry.getKey())) {
                                noSN = true;
                            }
                            List<Map<String, String>> list =
                                (List<Map<String, String>>)entry.getValue();
                            hasCellContant.addAll(list);
                            isListValue = true;
                        } else {
                            if (null != entry.getValue()) {
                                replaceText = String.valueOf(entry.getValue());
                            }
                        }
                        repalceText(cell, paramName, replaceText);
                        if (isListValue)
                            break;
                    }
                }
                if (isListValue)
                    break;
            }
            if (isListValue)
                break;
        }
        if (isListValue)
            addListValueInTable(hasCellContant, table, noSN);
    }

    private void addListValueInTable(List<Map<String, String>> hasCellContant,
                                     XWPFTable table, boolean noSN) {
        //add text of list values in table
        Map<Integer, List<String>> insertTextIndex =
            new HashMap<Integer, List<String>>();
        if (hasCellContant.size() > 0) {
            for (int i = 0; i < hasCellContant.size(); i++) {
                XWPFTableRow row = null;
                List<XWPFTableCell> cells = null;
                Map<String, String> map = hasCellContant.get(i);
                if (i == 0) {
                    row = table.getRow(1);
                } else {
                    row = table.createRow();
                }
                cells = row.getTableCells();
                for (int j = 0; j < cells.size(); j++) {
                    XWPFTableCell cell = cells.get(j);
                    String text = cell.getText();
                    if (j == 0 && i > 0) {
                        if (!noSN) {
                            text = String.valueOf(i + 1);
                        }
                    } else if (j != 0) {
                        List<String> listParamNames = new ArrayList<String>();
                        for (Map.Entry<String, String> entry :
                             map.entrySet()) {
                            String paramName =
                                EL_LEFT + entry.getKey() + EL_RIGHT;
                            if (i == 0) {
                                if (text.contains(paramName)) {
                                    text =
text.replace(paramName, entry.getValue());
                                    listParamNames.add(entry.getKey());
                                }
                            } else {
                                listParamNames = insertTextIndex.get(j);
                                for (String name : listParamNames) {
                                    if (name.equals(entry.getKey())) {
                                        text += entry.getValue();
                                    }
                                }
                            }
                        }
                        if (i == 0) {
                            insertTextIndex.put(j, listParamNames);
                        }
                    }
                    if (i == 0 && j != 0) {
                        cell.removeParagraph(0);
                    }
                    if (!(i == 0 && j == 0)) {
                        cell.setText(text);
                    }
                }
            }
        }
    }

    /**
     * replace text and images
     * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException
     * @throws java.io.FileNotFoundException
     */
//    private void replaceWordText(XWPFParagraph paragraph,
//                                 Map<String, Object> mapProperties) throws InvalidFormatException,
//                                                                           FileNotFoundException {
//        List<XWPFRun> runs = paragraph.getRuns();
//        for (int i = 0; i < runs.size(); i++) {
//            XWPFRun run = runs.get(i);
//            String oneparaString = run.getText(run.getTextPosition());
//            if (null != oneparaString) {
//                for (Map.Entry<String, Object> entry :
//                     mapProperties.entrySet()) {
//                    boolean isImage = entry.getKey().startsWith(IMAGE_);
//                    String paramName = EL_LEFT + entry.getKey() + EL_RIGHT;
//                    if (!isImage) {
//                        if (oneparaString.contains(paramName)) {
//                            String replaceText = "";
//                            if (null != entry.getValue()) {
//                                replaceText = String.valueOf(entry.getValue());
//                            }
//                            oneparaString =
//                                    oneparaString.replace(paramName, replaceText);
//                        }
//
//                    } else if (isImage) {
//                        if (oneparaString.contains(paramName.replace(IMAGE_,
//                                                                     ""))) {
//                            oneparaString = "";
//                            CTInline inline =
//                                run.getCTR().addNewDrawing().addNewInline();
//                            insertPicture(document,
//                                          String.valueOf(entry.getValue()),
//                                          inline, 100, 100);
//                        }
//                    }
//                }
//                run.setText(oneparaString, 0);
//            }
//        }
//    }

//    /**
//     * insert Picture
//     * @param document
//     * @param filePath
//     * @param inline
//     * @param width
//     * @param height
//     * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException
//     * @throws java.io.FileNotFoundException
//     */
//    private void insertPicture(XWPFDocument document, String filePath,
//                               CTInline inline, int width,
//                               int height) throws InvalidFormatException,
//                                                  FileNotFoundException {
//        document.addPictureData(new FileInputStream(filePath),
//                                XWPFDocument.PICTURE_TYPE_PNG);
//        int id = document.getAllPictures().size() - 1;
//        final int EMU = 9525;
//        width *= EMU;
//        height *= EMU;
//        String blipId =
//            document.getAllPictures().get(id).getPackageRelationship().getId();
//        String picXml = getPicXml(blipId, width, height);
//        XmlToken xmlToken = null;
//        try {
//            xmlToken = XmlToken.Factory.parse(picXml);
//        } catch (XmlException xe) {
//            xe.printStackTrace();
//        }
//        inline.set(xmlToken);
//        inline.setDistT(0);
//        inline.setDistB(0);
//        inline.setDistL(0);
//        inline.setDistR(0);
//        CTPositiveSize2D extent = inline.addNewExtent();
//        extent.setCx(width);
//        extent.setCy(height);
//        CTNonVisualDrawingProps docPr = inline.addNewDocPr();
//        docPr.setId(id);
//        docPr.setName("IMG_" + id);
//        docPr.setDescr("IMG_" + id);
//    }

    private void repalceText(XWPFTableCell cell, String paramName,
                             String replaceText) {
        if (null != cell.getParagraphs()) {
            int lengthOld = cell.getParagraphs().size();
            for (int i = 0; i < lengthOld; i++) {
                XWPFParagraph xWPFParagraph = cell.getParagraphs().get(i);
                for (int j = 0; j < xWPFParagraph.getCTP().getRArray().length;
                     j++) {
                    CTR run = xWPFParagraph.getCTP().getRArray()[j];
                    for (int k = 0; k < run.getTArray().length; k++) {
                        CTText text = run.getTArray()[k];
                        String stringValue = text.getStringValue();
                        if (stringValue.contains(paramName)) {
                            stringValue =
                                    stringValue.replace(paramName, replaceText);
                            text.setStringValue(stringValue);
                        }
                    }
                }
            }
        }
    }

    /**
     * get the xml of the picture
     * @param blipId
     * @param width
     * @param height
     * @return
     */
    private String getPicXml(String blipId, int width, int height) {
        String picXml =
            "" + "<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
            "   <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">" +
            "      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">" +
            "         <pic:nvPicPr>" + "            <pic:cNvPr id=\"" + 0 +
            "\" name=\"Generated\"/>" + "            <pic:cNvPicPr/>" +
            "         </pic:nvPicPr>" + "         <pic:blipFill>" +
            "            <a:blip r:embed=\"" + blipId +
            "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>" +
            "            <a:stretch>" + "               <a:fillRect/>" +
            "            </a:stretch>" + "         </pic:blipFill>" +
            "         <pic:spPr>" + "            <a:xfrm>" +
            "               <a:off x=\"0\" y=\"0\"/>" +
            "               <a:ext cx=\"" + width + "\" cy=\"" + height +
            "\"/>" + "            </a:xfrm>" +
            "            <a:prstGeom prst=\"rect\">" +
            "               <a:avLst/>" + "            </a:prstGeom>" +
            "         </pic:spPr>" + "      </pic:pic>" +
            "   </a:graphicData>" + "</a:graphic>";
        return picXml;
    }

    public void setNoSNTable(List<String> noSNTable) {
        this.noSNTable = noSNTable;
    }

    public List<String> getNoSNTable() {
        if (null == noSNTable) {
            noSNTable = new ArrayList<String>();
        }
        return noSNTable;
    }


    public static final String WORD_TEMPLATE = "/ProductionAgreementTemplate.ftl";
    public static final String TEMPLATE_PATH = "/include/exportTemplates";
    public static final String PREVIEW_DOC = "/ProductionAgreement.doc";
    public static final String PREVIEW_PDF = "/中文.PDF";
    public static final String PREVIEW_PDFMODE = "/productionTempMode.txt";
    public static final String TEMP_IMGMODE = "/w2/img/gongdaPropertySeal.png";
    public static final String TEMP_HOPECOREMODE = "/w2/img/hopecore.png";

    public static Template configTemplate(HttpServletRequest request, String temp) throws IOException {
        Configuration config = new Configuration();
        ServletContext sc = request.getSession().getServletContext();
        config.setDirectoryForTemplateLoading(new File(sc.getRealPath(TEMPLATE_PATH)));
        config.setObjectWrapper(new DefaultObjectWrapper());
        Template template = config.getTemplate(temp, "UTF-8");
        return template;
    }

    public static void toPreview(HttpServletRequest request, String temp, Map<?, ?> root) {
        try {
            String previewPath = request.getSession().getServletContext().getRealPath("") + PREVIEW_DOC;
            Template template = configTemplate(request, temp);
            template.setEncoding("UTF-8");
            FileOutputStream fos = new FileOutputStream(previewPath);
            Writer out = new OutputStreamWriter(fos, "UTF-8");
            template.process(root, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param pro  产品信息
     * @param paymentPlans  兑付信息列表
     * @param tempPath
     * @param tempTxTModePath
     * @param ImgModePath
     * @throws IOException
     * @throws DocumentException
     */
    public static void toPDF_Mode(ProductionAgreementPO pro, List<PaymentPlanPO> paymentPlans, String tempPath,String tempTxTModePath,String ImgModePath,String hopewcoreImgPath) throws Exception {
        Document document = new Document(PageSize.A4);
        String font = Config.getSystemConfig("system.font");
        if (StringUtils.isEmpty(font)) {
            MyException.newInstance("无法获得打印字体").throwException();
        }
        BaseFont baseFontChinese = BaseFont.createFont(font, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font fontBold= new Font(baseFontChinese, 12, Font.BOLD);
        Font fontCN =  new Font(baseFontChinese , 9 , Font.NORMAL);
        PdfWriter.getInstance(document, new FileOutputStream(tempPath));
         document.open();

        Paragraph Title = new Paragraph(new Paragraph("产品收益权转让及服务协议", new Font(fontBold)));
        Title.setAlignment(Element.ALIGN_CENTER);
        document.add(Title);
        inspect(document, tempTxTModePath, fontCN,pro.getCustomer_name(),pro.getNumber(),pro.getDateTime());

        Image img = Image.getInstance(ImgModePath);
        img.setAbsolutePosition(180, 420);
        img.scaleAbsolute(160,140);
        document.add(img);

        Image hopecoreImg = Image.getInstance(hopewcoreImgPath);
        hopecoreImg.setAbsolutePosition(380, 447);
        hopecoreImg.scaleAbsolute(100,100);
        document.add(hopecoreImg);


        PdfPCell cell = null;
        // 创建需要填入文档的元素
        PdfPTable table = new PdfPTable(4);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.setWidthPercentage(100);
        //第一行
        cell = new PdfPCell(new Paragraph(
                new Paragraph("《产品收益权转让清单》（附件一）", new Font(fontBold))));
        cell.setColspan(4);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setMinimumHeight(30);

        table.addCell(cell);

        //第二行
        cell = new PdfPCell(new Paragraph(
                new Paragraph("产品信息", new Font(fontCN))));
        cell.setColspan(4);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setMinimumHeight(30);
        table.addCell(cell);

        //第三行
        cell = new PdfPCell(new Paragraph(
                new Paragraph("产品名称", new Font(fontBold))));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setMinimumHeight(30);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(
                new Paragraph(pro.getProduction_name(), new Font(fontCN))));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setColspan(3);
        table.addCell(cell);




        /**
         * 投资期限
         */
        cell = new PdfPCell(new Paragraph(
                new Paragraph("投资期限", new Font(fontBold))));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setMinimumHeight(30);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(
                new Paragraph(pro.getInvestTermView(), new Font(fontCN))));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setColspan(3);
        table.addCell(cell);




        //第四行
        cell = new PdfPCell(new Paragraph(
                new Paragraph("所属项目", new Font(fontBold))));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setMinimumHeight(30);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(
                new Paragraph("定期债权类", new Font(fontCN))));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(
                new Paragraph("预期年化收益率", new Font(fontBold))));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setMinimumHeight(30);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(
                new Paragraph(pro.getExpectedYiel()+"%", new Font(fontCN))));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        //第五行
        cell = new PdfPCell(new Paragraph(
                new Paragraph("客户信息", new Font(fontCN))));
        cell.setColspan(4);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setMinimumHeight(30);
        table.addCell(cell);

        //第六行
        cell = new PdfPCell(new Paragraph(
                new Paragraph("名称", new Font(fontBold))));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setMinimumHeight(30);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(
                new Paragraph(pro.getCustomer_name(), new Font(fontCN))));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(
                new Paragraph("属性", new Font(fontBold))));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setMinimumHeight(30);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(
                new Paragraph(pro.getCustomer_Attribute(), new Font(fontCN))));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);


        //第七行
        cell = new PdfPCell(new Paragraph(
                new Paragraph("订单信息", new Font(fontCN))));
        cell.setColspan(4);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setMinimumHeight(30);
        table.addCell(cell);

        //第八行
        cell = new PdfPCell(new Paragraph(
                new Paragraph("订单号", new Font(fontBold))));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setMinimumHeight(30);
        table.addCell(cell);


        cell = new PdfPCell(new Paragraph(
                new Paragraph(pro.getOrder_Num(), new Font(fontCN))));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setColspan(3);
        table.addCell(cell);

//        第九行
        cell = new PdfPCell(new Paragraph(
                new Paragraph("投资金额", new Font(fontBold))));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setMinimumHeight(30);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(
                new Paragraph(pro.getInvest_Money(), new Font(fontCN))));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(
                new Paragraph("支付金额", new Font(fontBold))));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setMinimumHeight(30);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(
                new Paragraph(pro.getInvest_Money(), new Font(fontCN))));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);


        //第十行
        cell = new PdfPCell(new Paragraph(
                new Paragraph("投资时间", new Font(fontBold))));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setMinimumHeight(30);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(
                new Paragraph(pro.getInvest_Date(), new Font(fontCN))));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(
                new Paragraph("起息日", new Font(fontBold))));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setMinimumHeight(30);
        table.addCell(cell);
        cell = new PdfPCell(new Paragraph(
                new Paragraph(pro.getValue_Date(), new Font(fontCN))));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);


        /**
         *
         * 下面是兑付信息
         * 此时暂时不打印
         *
         */

//
//        //第十一行
//        cell = new PdfPCell(new Paragraph(
//                new Paragraph("兑付信息", new Font(fontCN))));
//        cell.setColspan(4);
//        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//        cell.setMinimumHeight(30);
//        table.addCell(cell);
//
//
//        PaymentPlanPO paymentPlanDisplay = paymentPlans.get(0);
//        int totalInstallment = paymentPlanDisplay.getTotalInstallment();
//        String type = paymentPlanDisplay.getType();
//
//        //第十二行
//        cell = new PdfPCell(new Paragraph(
//                new Paragraph("兑付类型", new Font(fontBold))));
//        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//        cell.setMinimumHeight(30);
//        table.addCell(cell);
//
//        cell = new PdfPCell(new Paragraph(
//                new Paragraph(type, new Font(fontCN))));
//        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//        table.addCell(cell);
//        cell = new PdfPCell(new Paragraph(
//                new Paragraph("总期数", new Font(fontBold))));
//        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//        cell.setMinimumHeight(30);
//        table.addCell(cell);
//        cell = new PdfPCell(new Paragraph(
//                new Paragraph(String.valueOf(totalInstallment), new Font(fontCN))));
//        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//        table.addCell(cell);
//
//
//        for (int i = 0; i < totalInstallment; i++) {
//            PaymentPlanPO paymentPlan = paymentPlans.get(i);
//            String  interestTitle = "预计第"+(i+1)+"期收益";
//            if (totalInstallment == 1) {
//                interestTitle = "预计到期收益";
//            }
//            String paymentTime = TimeUtils.format(paymentPlan.getPaymentTime(), TimeUtils.Format_YYYY_MM_DD_HH_M_S, TimeUtils.Format_YYYY_MM_DD);
//
//            //第十三行
//            cell = new PdfPCell(new Paragraph(
//                    new Paragraph(interestTitle, new Font(fontBold))));
//            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            cell.setMinimumHeight(30);
//            table.addCell(cell);
//            cell = new PdfPCell(new Paragraph(
//                    new Paragraph(MoneyUtils.format2String(paymentPlan.getTotalProfitMoney()), new Font(fontCN))));
//            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            table.addCell(cell);
//
//            cell = new PdfPCell(new Paragraph(
//                    new Paragraph("预计到期日", new Font(fontBold))));
//            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            cell.setMinimumHeight(30);
//            table.addCell(cell);
//
//            cell = new PdfPCell(new Paragraph(
//                    new Paragraph(paymentTime, new Font(fontCN))));
//            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            table.addCell(cell);
//        }




        cell = new PdfPCell(new Paragraph(
                new Paragraph("备注:\r\n\r\n1.上述币种均指人民币；\r\n\r\n2.本清单所指年度为365个自然日。", new Font(fontCN))));
        cell.setColspan(4);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setMinimumHeight(70);
        table.addCell(cell);
        document.add(table);
        document.close();
    }

    /**
     *
     * @throws IOException

     */

    public static void inspect(Document document, String tempPath, Font fontCN, String customer_name, String number, String dateTime) throws IOException, DocumentException {


            InputStreamReader read = new InputStreamReader(
                    new FileInputStream(tempPath),"UTF-8");//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while((lineTxt = bufferedReader.readLine()) != null){
               if(lineTxt.contains("replace1")){
                   lineTxt=lineTxt.replace("replace1",customer_name);
               }
               if(lineTxt.contains("replace2")){
                   lineTxt=lineTxt.replace("replace2",number);
               }
               if(lineTxt.contains("replace3")){
                   lineTxt=lineTxt.replace("replace3",customer_name);
               }
               if(lineTxt.contains("replace4")){
                   lineTxt=lineTxt.replace("replace4",dateTime);
               }

                Paragraph Title=new Paragraph(new Paragraph("          "+lineTxt, new Font(fontCN)));

                Title.setIndentationLeft(40);

                document.add(Title);//写入文件内容
            }
        read.close();

    }

}
