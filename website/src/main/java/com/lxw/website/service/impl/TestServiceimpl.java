package com.lxw.website.service.impl;

import com.lxw.website.dao.TestDao;
import com.lxw.website.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author LXW
 * @date 2021年04月23日 13:15
 */
@Service
public class TestServiceimpl implements TestService {

    @Autowired
    TestDao testDao;

    @Override
    public List<Map> test() {

        return testDao.selectTest();
    }

    @Override
    public void insertDate(String action, String data) {
        testDao.insertDate(action,data);
    }

}
