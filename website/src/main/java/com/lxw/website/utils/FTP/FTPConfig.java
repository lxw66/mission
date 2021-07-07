package com.lxw.website.utils.FTP;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author LXW
 * @date 2021年04月28日 15:35
 */
@Component
public class FTPConfig {

    @Value("${FTP.ip}")
    private String ftpIp;
    @Value("${FTP.port}")
    private int ftpPort;
    @Value("${FTP.userName}")
    private String ftpUserName;
    @Value("${FTP.passWord}")
    private String ftpPassWord;
    @Value("${FTP.encoding}")
    private String encoding;
    @Value("${FTP.connectiontime}")
    private long connectiontime;
    @Value("${FTP.connectiontime}")
    private long dateTimeOut;


    public String getFtpIp() {
        return ftpIp;
    }

    public void setFtpIp(String ftpIp) {
        this.ftpIp = ftpIp;
    }

    public int getFtpPort() {
        return ftpPort;
    }

    public void setFtpPort(int ftpPort) {
        this.ftpPort = ftpPort;
    }

    public String getFtpUserName() {
        return ftpUserName;
    }

    public void setFtpUserName(String ftpUserName) {
        this.ftpUserName = ftpUserName;
    }

    public String getFtpPassWord() {
        return ftpPassWord;
    }

    public void setFtpPassWord(String ftpPassWord) {
        this.ftpPassWord = ftpPassWord;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public long getConnectiontime() {
        return connectiontime;
    }

    public void setConnectiontime(long connectiontime) {
        this.connectiontime = connectiontime;
    }

    public long getDateTimeOut() {
        return dateTimeOut;
    }

    public void setDateTimeOut(long dateTimeOut) {
        this.dateTimeOut = dateTimeOut;
    }


}
