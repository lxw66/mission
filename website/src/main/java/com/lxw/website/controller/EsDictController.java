package com.lxw.website.controller;

import com.lxw.website.domain.PageResult;
import com.lxw.website.domain.esuser;
import com.lxw.website.service.esUserService;
import jdk.internal.instrumentation.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mission
 * @version 1.0
 * @description: TODO
 * @date 2021/7/16 13:37
 */
@RestController
@RequestMapping("/esDictController")
public class EsDictController {

    @Autowired
    private com.lxw.website.service.esUserService esUserService;

   @RequestMapping(value="/getCustomDict.json")
    public void getCustomDict(HttpServletRequest request, HttpServletResponse response){

        try {
            // 读取字典文件
            String path = "D:\\mydic.txt";
            File file = new File(path);
            String content = "";
            if(file.exists()){
                // 读取文件内容
                FileInputStream fi = new FileInputStream(file);
                byte[] buffer = new byte[(int) file.length()];
                int offset = 0, numRead = 0;
                while (offset < buffer.length && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
                    offset += numRead;
                }
                fi.close();
                content = new String(buffer, "UTF-8");
            }
            // 返回数据
            OutputStream out= response.getOutputStream();
            response.setHeader("Last-Modified", String.valueOf(content.length()));
            response.setHeader("ETag",String.valueOf(content.length()));
            response.setContentType("text/plain; charset=utf-8");
            out.write(content.getBytes("utf-8"));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value="/getDate.json")
    private Object getDate(){
        return esUserService.findAll();
    }

    @RequestMapping(value="delteAll.json")
    private void delteAll(){
       esUserService.deleteAll();
    }

    @RequestMapping(value="getAllCount.json")
    private Object getAllCount(){
        return esUserService.getAllCount();
    }

    @RequestMapping(value="getUserIdAfter.json")
    private Object getAllCount(String  username){
        return esUserService.findByUsername(username);
       // return esUserService.findByUser_id(userID);
    }

    @RequestMapping(value="save.json")
    private void save(){
        //return esUserService.findByUsername(username);
        // return esUserService.findByUser_id(userID);
        List<esuser> list=new ArrayList<>();
        esuser esuser=new esuser();
        //esuser esuser1=new esuser(12121212,"lxw1","39349695@qq.com","男","2021-08-09 16:13:12",25,"asdddddddddddddd");
        //esuser esuser2=new esuser(12121212,"lxw2","39349695@qq.com","男","2021-08-09 16:13:12",25,"asdddddddddddddd");
        //list.add(esuser2); list.add(esuser); list.add(esuser1);
        esUserService.save(esuser);
        //esUserService.saveAll(list);
    }
    //分页查询
    @RequestMapping(value="/getpage.json")
    private Object getpage(Integer currentPage, Integer limit){
        if (currentPage == null || currentPage < 0 || limit == null || limit <= 0) {
            return new String ("请输入合法的分页参数");
        }
        PageResult pageResult=esUserService.getDate(currentPage,limit);
        return pageResult;
    }

    //复合条件查询
    @RequestMapping(value="/getweightsortpage.json")
    private Object getweightsortpage(Integer currentPage, Integer limit){
        if (currentPage == null || currentPage < 0 || limit == null || limit <= 0) {
            return new String ("请输入合法的分页参数");
        }
        PageResult pageResult=esUserService.getgetweightsortpageDate(currentPage,limit);
        return pageResult;
    }

}
