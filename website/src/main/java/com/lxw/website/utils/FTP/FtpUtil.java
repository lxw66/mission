package com.lxw.website.utils.FTP;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

/**
 * @author LXW
 * @date 2021年04月28日 15:14
 */

@Slf4j
@Component
public class FtpUtil {

  private FTPClient ftpClient;

  private OutputStream outputStream;

  private FTPResult ftpResult;

  private static   boolean recent=true;
    /**
     * 
     * @author LXW
     * @date 2021/5/14 14:11
     * @param filePath  文件存储的路径
     * @param file    需要上传的路径
     * @param ftpConfig   ftp的配置路径
     * @return com.lxw.website.utils.FTP.FTPResult
     */
  public FTPResult uploadFile(String filePath, File file, FTPConfig ftpConfig) throws IOException {
      String fileName=file.getName();
      String ftpFileName=null;
      ftpResult=new FTPResult();
      ftpResult.setFileName(fileName);
      boolean connect=connection(ftpConfig);
      //连接上
      if(connect){
          //filePath是否存在  不存在则建文件夹
          if(mkdirDirctory(filePath)){
              if(fileName.lastIndexOf(".")!=-1){
                  ftpFileName= UUID.randomUUID()+fileName.substring(fileName.lastIndexOf("."));
                  ftpResult.setFtpFilename(ftpFileName);
              }
          }
            //InputStream inputStream=new FileInputStream(file);
          //保存文件到ftp上
          boolean isupload=ftpClient.storeFile(ftpFileName,new FileInputStream(file));
          ftpResult.setRecepit(isupload);
          //关闭ftp 连接
          close();

      }

      return ftpResult;

  }
        /**
         * 
         * @author LXW
         * @date 2021/5/14 14:16
         * @param filePath 文件路径
         * @param fileName  文件名
         * @param response   接口的response
         * @param ftpConfig   ftp配置
         */
      public void prviewFile(String filePath, String fileName, HttpServletResponse response,FTPConfig ftpConfig)  {
          //1.首先连接上ftp
          boolean isConnect=connection(ftpConfig);
          InputStream in=null;
          OutputStream ou=null;
          if(isConnect){
              //2.切换到文件目录下
              try{
                  ftpClient.changeWorkingDirectory(filePath);
                  //3.获取文件夹下的文件数组
                  FTPFile[] files=ftpClient.listFiles();
                  for (FTPFile file:files) {
                      //循环获取文件名  判断文件是否存在
                      if(file.getName().equals(fileName)){
                            //ftp返回的是文件输入流，需要输出流接受下
                          in=ftpClient.retrieveFileStream(fileName);
                          //将输出流写入response
                         if(in!=null){
                             ou=response.getOutputStream();
                             int len;
                             byte[] data=new byte[1024];
                             //缓冲区
                             while ((len=in.read(data,0,data.length))!=-1){
                                 ou.write(data,0,len);
                             }
                             ou.flush();
                         }

                      }
                  }
              }catch (IOException e){
                  log.info("读取ftp上的文件失败"+e);
              }finally {
                  try {
                      ou.close();
                      in.close();
                  }catch (IOException e){
                      log.info("读取ftp上的文件失败"+e);
                  }
              }

          }else{
              log.info("未连接FTP");
          }

      }


  public void close(){
      try {
          ftpClient.logout();
          log.info("退出登录！");
      } catch (IOException e) {
          log.error("退出登录失败！======>", e.toString());

      }

    }



  /**
   * ftp  连接
   * @author LXW
   * @date 2021/4/28 16:08
   * @param ftpConfig 
   * @return boolean
   */
  public boolean connection(FTPConfig ftpConfig) {
              ftpClient=new FTPClient();
              try{
                  ftpClient.connect(ftpConfig.getFtpIp(),ftpConfig.getFtpPort());
              }catch (IOException e){
                  log.info("ftp连接失败！===="+e.toString());
                  try {
                      ftpClient.disconnect();
                  } catch (IOException ioException) {
                      log.info("ftp关闭连接失败！===="+e.toString());
                  }
                  return false;
              }

              int code=ftpClient.getReplyCode();
              if(!FTPReply.isPositiveCompletion(code)){
                  log.info("ftp连接失败！====code:"+code);
                  return false;
              }
              try {
                  //ftpClient.login(ftpConfig.getFtpUserName(),ftpConfig.getFtpPassWord());
                  ftpClient.login("anonymous", null);//匿名登录
                  ftpClient.enterLocalPassiveMode();//被动模式
                  //和FTP  配置的编码一致
                  ftpClient.setControlEncoding(ftpConfig.getEncoding());
                  ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                  ftpClient.setControlKeepAliveTimeout(ftpConfig.getDateTimeOut());
                  ftpClient.setControlKeepAliveTimeout(ftpConfig.getDateTimeOut());
              }catch (IOException e){
                  log.info("ftp登录失败！===="+e.toString());
                  return false;
              }
              return true;
  }


  /**
   * 目前 获取ftp目录 循环判断  后期使用cmd  命令
   * @author LXW
   * @date 2021/4/28 16:51
   * @param url 
   * @return boolean
   */
  public  boolean  mkdirDirctory(String url) throws IOException {
      String[] fileArr = url.split("/");

      for (String dirName:fileArr
           ) {
         if(!ftpClient.changeWorkingDirectory(dirName)){
             ftpClient.makeDirectory(dirName);
             ftpClient.changeWorkingDirectory(dirName);
         }
          ftpClient.changeWorkingDirectory(dirName);
      }


      return recent;
  }





}
