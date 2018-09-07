package com.youngbook.common.poi;

import org.apache.poi.xwpf.usermodel.LineSpacingRule;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class WordHelper {

    /**
     * 拷贝文件并替换其中内容
     * @param source
     * @param target
     * @param replaceWords 保存替换的内容，key定义为r0001, r0002等，若是中文或标点会被截断，无法匹配。
     * @throws Exception
     */
    public static void copyAndReplace(String source, String target, Map<String, String> replaceWords) throws Exception {
        FileOutputStream fos = new FileOutputStream(new File(target));
        XWPFDocument doc = new XWPFDocument(new FileInputStream(new File(source)));
        XWPFDocument docBlank = new XWPFDocument();

        try {
            for(XWPFParagraph p:doc.getParagraphs()) {
                for(XWPFRun r : p.getRuns()) {

                    String t = r.getText(0);
                    //System.out.println("Text:" + t);

                    for(CTText ct : r.getCTR().getTList()){
                        String str = ct.getStringValue();
                        // 循环获得需要替换的内容
                        Set<String> setKeys = replaceWords.keySet();
                        Iterator<String> itKeys = setKeys.iterator();
                        while (itKeys.hasNext()) {

                            // 定位需要替换的内容
                            String key = itKeys.next();


                            // 替换的最终值
                            String value = replaceWords.get(key);

                            System.out.println("key:" + key + " value:" + value);
                            System.out.println("text:" + str);

                            if(str.contains(key)){
                                str = str.replaceAll(key, value);
                                ct.setStringValue(str);
                            }
                        }

                    }
                }
                XWPFParagraph pp = docBlank.createParagraph();
                WordHelper.copyAllRunsToAnotherParagraph(p, pp);
            }

            docBlank.write(fos);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (fos != null) {
                fos.close();
            }
        }
    }

    /**
     * 将源文件内容拷贝到目标文件上
     * @param source
     * @param target
     * @throws Exception
     */
    public static void copy(String source, String target) throws Exception {
        copyWithTimes(source, target, 1);
    }

    /**
     * 将源文件拷贝到目标文件中，并且重复拷贝不同份数
     * @param source
     * @param target
     * @param times
     * @throws Exception
     */
    public static void copyWithTimes(String source, String target, int times) throws Exception {
        FileOutputStream fos = new FileOutputStream(new File(target));
        XWPFDocument doc = new XWPFDocument(new FileInputStream(new File(source)));
        XWPFDocument docBlank = new  XWPFDocument();

        try {
            for (int i = 0; i < times; i++) {
                for(XWPFParagraph p:doc.getParagraphs()) {
                    for(XWPFRun r:p.getRuns()) {
                        for(CTText ct:r.getCTR().getTList()){
                            String str = ct.getStringValue();
                            if(str.contains("NAME")){
                                str = str.replace("NAME", "Java Dev");
                                ct.setStringValue(str);
                            }
                        }
                    }
                    XWPFParagraph pp = docBlank.createParagraph();
                    WordHelper.copyAllRunsToAnotherParagraph(p, pp);
                }
            }

            docBlank.write(fos);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (fos != null) {
                fos.close();
            }
        }
    }

    /**
     * Copy all runs from one paragraph to another, keeping the style unchanged
     * 对于一些特殊的地方需要注意
     *
     * 未实现特性：
     * 行间距
     * 分页符
     *
     * 行间距：poi设置不了行间距，所以行间距数据会丢失
     *
     * 对于没有文字，只有字体大小的行，poi拷贝以后的显示仍是比较小的，可以在源上将此行加上一个下划线，然后将下划线设置为白色即可
     *
     * @param oldPar
     * @param newPar
     */
    public static void copyAllRunsToAnotherParagraph(XWPFParagraph oldPar, XWPFParagraph newPar) {
        final int DEFAULT_FONT_SIZE = 12;

        for (XWPFRun run : oldPar.getRuns()) {
            String textInRun = run.getText(0);
            if (textInRun == null) {
                //System.out.println("text in run is null");
                continue;
            }

            int fontSize = run.getFontSize();
            // System.out.println("run text = '" + textInRun + "' , fontSize = " + fontSize);

            XWPFRun newRun = newPar.createRun();

            // Copy text
            newRun.setText(textInRun);

            // Apply the same style
            newRun.setFontSize( ( fontSize == -1) ? DEFAULT_FONT_SIZE : run.getFontSize() );
            newRun.setFontFamily( run.getFontFamily() );
            newRun.setBold( run.isBold() );
            newRun.setItalic( run.isItalic() );
            newRun.setStrike( run.isStrike() );
            newRun.setColor( run.getColor() );
            newRun.setUnderline(run.getUnderline());
        }

        // 设置对齐
        newPar.setAlignment(oldPar.getAlignment());
        newPar.setVerticalAlignment(oldPar.getVerticalAlignment());

        // 设置分页
        newPar.setPageBreak(oldPar.isPageBreak());
        //System.out.println("分页");


        // 设置行间距
        newPar.setSpacingLineRule(LineSpacingRule.AUTO);

        newPar.setStyle(oldPar.getStyle());

    }
}
