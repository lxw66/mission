package com.lxw.website.controller;

import com.lxw.website.utils.ES.Esutil;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author mission
 * @version 1.0
 * @description: TODO
 * @date 2021/8/11 15:25
 */

@RestController
@RequestMapping("/esRestHighLevelController")
public class EsRestHighLevelController {


    @Autowired
    Esutil esutil;



    @RequestMapping(value="/getIndexIsExit.json")
    private boolean getIndexIsExit(@RequestParam(value = "indexName")String indexName) throws IOException {
        boolean isexit=esutil.isIndexExit(indexName);
        return isexit;
    }

    @RequestMapping(value="/getIndexDate.json")
    private Object getIndexDate(@RequestParam(value = "indexName")String indexName) throws IOException {
        return esutil.getIndexDate(indexName);
    }

    @RequestMapping(value="/addDate.json")
    private Object addDate() throws IOException {
        return esutil.addDate();
    }

    @RequestMapping(value="/addDateAll.json")
    private Object addDateAll() throws IOException {
        return esutil.addDates();
    }

    @RequestMapping(value="/deleteDateByType.json")
    private Object deleteDateByType() throws IOException {
        return esutil.delateDate();
    }

    @RequestMapping(value="/updateDate.json")
    private Object updateDate() throws IOException {
        return esutil.update();
    }



}
