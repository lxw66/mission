package com.lxw.website.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.util.Date;

/**
 * @author mission
 * @version 1.0
 * @description: TODO
 * @date 2021/8/2 13:55
 */
@Document(indexName = "es_user")
public class esuser {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Id
    private String id;
    private  int userid;
    private String username;
    private String email;
    private String gender;
    private String createtime;
    private int age;
    private String  text;

    public int getUser_id() {
        return userid;
    }

    public void setUser_id(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public esuser(int user_id, String username, String email, String gender, String createtime, int age, String text) {
        this.userid = user_id;
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.createtime = createtime;
        this.age = age;
        this.text = text;
    }

    public esuser() {
    }
}
