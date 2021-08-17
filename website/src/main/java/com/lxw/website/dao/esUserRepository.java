package com.lxw.website.dao;

import com.lxw.website.domain.esuser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author mission
 * @version 1.0
 * @description: TODO
 * @date 2021/8/2 14:06
 */
public interface esUserRepository  extends ElasticsearchRepository<esuser,String> {
    List<esuser> findAll();

    List<esuser> findByUsername(String username);

    List<esuser> findByUsernameStartingWith(String username);

}
