package com.youngbook.common.utils;

import com.youngbook.common.MyException;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;

/**
 * Created by Lee on 2/13/2017.
 */
public class FTPUtils {

    FTPClient ftp = null;

    public FTPUtils(String host, String user, String pwd) throws Exception{
        ftp = new FTPClient();
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        int reply;
        ftp.connect(host);
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new Exception("Exception in connecting to FTP Server");
        }
        ftp.login(user, pwd);
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        ftp.enterLocalPassiveMode();
    }
    public void uploadFile(String localFileFullName, String fileName, String hostDir)
            throws Exception {
        InputStream input = null;

        try {
            input = new FileInputStream(new File(localFileFullName));
            this.ftp.storeFile(hostDir + fileName, input);
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (input != null) {
                input.close();
            }
        }
    }

    public void disconnect(){
        if (this.ftp.isConnected()) {
            try {
                this.ftp.logout();
                this.ftp.disconnect();
            } catch (IOException f) {
                // do nothing as file is already saved to server
            }
        }
    }

    public static void main(String [] args) throws Exception {


        String server = "hk002.youngbook.net";
        int port = 21;
        String user = "y7wwesd3Wc";
        String password = "51Mq88OLIIx0reWYtJzT";
        System.out.println("Start");

        FTPUtils ftpUploader = new FTPUtils(server, user, password);

        ftpUploader.uploadFile("D:\\abc.rar", "abc.rar", "/");
        ftpUploader.disconnect();
        System.out.println("Done");
    }

}
