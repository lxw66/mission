package com.lxw.website.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

//仅仅为了去除警告
@Component
public interface TestDao {

    List<Map> selectTest();

    void insertDate(@Param(value = "action")String action,
                    @Param(value = "data")String data);

}
