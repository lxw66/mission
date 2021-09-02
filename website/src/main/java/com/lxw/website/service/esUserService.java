package com.lxw.website.service;

import com.lxw.website.domain.PageResult;
import com.lxw.website.domain.esuser;

import java.util.List;

public interface esUserService {
    List<esuser> findAll();

    void deleteAll();

    void deleteUser(esuser esuser);

    long getAllCount();

    void deleteUserByuserName(String  userName);

    List<esuser> findMaxuserId();

    List<esuser> findByUsername(String username);

    void save(esuser esuser);

    void saveAll(List<esuser> esuser);

    void deletebyId(String id);

    PageResult getDate(Integer currentPage, Integer limit);

    PageResult getgetweightsortpageDate(Integer currentPage, Integer limit);
}
