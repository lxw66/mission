package com.lxw.website.controller;

import com.lxw.website.http.HttpApiService;
import com.lxw.website.service.TestService;
import com.lxw.website.utils.FTP.FTPConfig;
import com.lxw.website.utils.FTP.FtpUtil;
import com.lxw.website.utils.FTP.FTPResult;
import com.lxw.website.utils.TCPUDP.TcpClient;
import com.lxw.website.utils.TCPUDP.udpClient;
import com.lxw.website.utils.XMLUtils.XmlUtils;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * @author LXW
 * @date 2021年04月23日 13:17
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    TestService testService;

    @Autowired
    HttpApiService httpApiService;

    @Autowired
    FTPConfig ftpConfig;

    @Autowired
    FtpUtil ftpUtil;

    @Autowired
    com.lxw.website.utils.TCPUDP.udpClient udpClient;

    @Autowired
    TcpClient tcpClient;

    @Resource
    XmlUtils xmlUtils;



    @RequestMapping(value = "/insert.json",method = RequestMethod.POST)
    public void  insert(@RequestParam(value = "action",defaultValue = "",required = true)String action,
                          @RequestParam(value = "date",defaultValue = "",required = true)String date){

         testService.insertDate(action,date);
    }


    @RequestMapping(value = "/test.json",method = RequestMethod.POST)
    public Object  test() throws IOException {
       // String str=httpApiService.doGet("https://www.baidu.com/");
        return ftpConfig.toString();
    }



    @RequestMapping(value = "/ftp.json",method = RequestMethod.POST)
    public Object  ftp() throws IOException {
        File file=new File("E:\\test\\lAbPDg7mQnLILuzOY235z85jbfnP.mp4");
        FTPResult ftpResult=ftpUtil.uploadFile("lxw/test",file,ftpConfig);
        return ftpResult.toString();
    }


    @RequestMapping(value = "/udp.json",method = RequestMethod.POST)
    public Object  udp(@RequestParam(value = "msg",defaultValue = "",required = true)String msg) throws IOException {
        udpClient.sendMassage(msg);
        return true;
    }


    @RequestMapping(value = "/tcp.json",method = RequestMethod.POST)
    public Object  tcp(@RequestParam(value = "msg",defaultValue = "",required = true)String msg) throws IOException {
        tcpClient.sendTcpMessage(msg);
        return true;
    }

    //图片的预览  和视屏的播放
    @RequestMapping(value = "/videofile.json",method = RequestMethod.POST)
    public void  videofile(HttpServletRequest request, HttpServletResponse response) throws IOException {
       String fileName="9dceb84d-be02-4aa6-aa02-f8a14bdc76fe.png";
       String  filePath="/lxw/test";
       ftpUtil.prviewFile(filePath,fileName,response,ftpConfig);
    }


    //图片的预览  和视屏的播放
    @RequestMapping(value = "/xml.json",method = RequestMethod.POST)
    public void  xmlfile() throws Exception {
        File file=new File("E:/test.xml");
        //xmlUtils.dom4jxml(file);
        xmlUtils.dom4jxml(file);
    }


}
