package com.lxw.website.utils.FTP;

/**
 * @author LXW
 * @date 2021年04月28日 16:23
 */
public class FTPResult {

    //原始文件名
    private String fileName;
    //ftp服务器上的文件名
    private String ftpFilename;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFtpFilename() {
        return ftpFilename;
    }

    public void setFtpFilename(String ftpFilename) {
        this.ftpFilename = ftpFilename;
    }

    public String getFtpUrl() {
        return ftpUrl;
    }

    public void setFtpUrl(String ftpUrl) {
        this.ftpUrl = ftpUrl;
    }

    public boolean isRecepit() {
        return recepit;
    }

    public void setRecepit(boolean recepit) {
        this.recepit = recepit;
    }

    //ftp服务器上的路径
    private String ftpUrl;
    //操作回执
    private boolean  recepit;

    @Override
    public String toString() {
        return "FTPResult [fileName=" + fileName + ", ftpFilename=" + ftpFilename + ", ftpUrl=" + ftpUrl + "recepit ="+recepit+"]";
    }


}
