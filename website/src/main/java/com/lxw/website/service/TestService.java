package com.lxw.website.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LXW
 * @date 2021年04月23日 13:14
 */
public interface TestService {

    List<Map> test();
    void insertDate(String action,String data);
}
