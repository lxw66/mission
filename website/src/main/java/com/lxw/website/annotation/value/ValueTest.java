package com.lxw.website.annotation.value;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *@value  用法
 * @author LXW
 * @date 2021/6/17
 * @param
 * @return null
 */
@Service
public class ValueTest {

    //1.字量面
    @Value("1+2")
    private  String num;
    //
    @Value("decom_${Comment.here}")
    private String comment;
    //${x1:x2}  若配置文件取不到x1的值  默认值为x2
    @Value("${Comment.here2:YES}")
    private String comment_no;


    //2.SpEL 表达式
    /**
     * 字符串
     */
    @Value("#{'abcd'}")
    private String spelStr;

    /**
     * 基本计算
     */
    @Value("#{1 + 2}")
    private String spelVal3;

    /**
     * 列表
     */
    @Value("#{{1, 2, 3}}")
    private List<Integer> spelList;

    /**
     * map
     */
    @Value("#{{a: '123', b: 'cde'}}")
    private Map spelMap;

    /**
     * 调用静态方法
     * Md5Utils.getMd5  为静态方法  可与Comment.here配置文件结合
     */
    @Value("#{T(com.lxw.website.utils.Md5Utils).getMd5('${Comment.here}')}")
    private String spelStaticMethod;
    /**
     *
     * @service  修饰md5Utils
     */
    @Value("#{md5Utils.randUid()}")
    private String spelService;

    public String getComment_no() {
        return comment_no;
    }

    public void setComment_no(String comment_no) {
        this.comment_no = comment_no;
    }

    public String getSpelService() {
        return spelService;
    }

    public void setSpelService(String spelService) {
        this.spelService = spelService;
    }

    public String getSpelStr() {
        return spelStr;
    }

    public void setSpelStr(String spelStr) {
        this.spelStr = spelStr;
    }

    public String getSpelVal3() {
        return spelVal3;
    }

    public void setSpelVal3(String spelVal3) {
        this.spelVal3 = spelVal3;
    }

    public List<Integer> getSpelList() {
        return spelList;
    }

    public void setSpelList(List<Integer> spelList) {
        this.spelList = spelList;
    }

    public Map getSpelMap() {
        return spelMap;
    }

    public void setSpelMap(Map spelMap) {
        this.spelMap = spelMap;
    }

    public String getSpelStaticMethod() {
        return spelStaticMethod;
    }

    public void setSpelStaticMethod(String spelStaticMethod) {
        this.spelStaticMethod = spelStaticMethod;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
