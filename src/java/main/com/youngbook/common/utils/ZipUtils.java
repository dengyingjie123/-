package com.youngbook.common.utils;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Lee on 6/7/2017.
 */
public class ZipUtils {

    public static void writeToZipFile(List<File> files, File zipFile) throws Exception {

        FileOutputStream fos = new FileOutputStream(zipFile);
        ZipOutputStream zipOut = new ZipOutputStream(fos);

        for (int i = 0; files != null && i < files.size(); i++) {
            File file = files.get(i);
            ZipUtils.writeToZipFile(file.getPath(), file.getName(), zipOut);
        }


        zipOut.close();
        fos.close();

    }

    public static void writeToZipFile(String path, String fileNameWithExtension, ZipOutputStream zipStream) throws IOException {
        System.out.println("Writing file : '" + path + "' to zip file");
        File aFile = new File(path);
        FileInputStream fis = new FileInputStream(aFile);
        ZipEntry zipEntry = new ZipEntry(new ZipEntry(fileNameWithExtension));

        zipStream.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;

        while ((length = fis.read(bytes)) >= 0) {
            zipStream.write(bytes, 0, length);
        }
        zipStream.closeEntry();
        fis.close();
    }


    public static byte[] zipBytes(String filename, byte[] input) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        ZipEntry entry = new ZipEntry(filename);
        entry.setSize(input.length);
        zos.putNextEntry(entry);
        zos.write(input);
        zos.closeEntry();
        zos.close();
        return baos.toByteArray();
    }
}
