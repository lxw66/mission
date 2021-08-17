package com.lxw.website.domain;

import java.util.List;

/**
 * @author mission
 * @version 1.0
 * @description: TODO
 * @date 2021/8/10 10:13
 */
public class PageResult {

    /**
     * 总页码
     */
    int totalPage;
    /**
     * 总页数
     */
    int totalCount;
    /**
     * 当前页码
     */
    int currentPage;

    /**
     *每页数量
     */
    int limit;
    /**
     * 当前数据
     */
    List<?> dataList;


    public int getTotalPage() {
        return totalCount%limit==0?(totalCount/limit):(totalCount/limit)+1;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<?> getDataList() {
        return dataList;
    }

    public void setDataList(List<?> dataList) {
        this.dataList = dataList;
    }

    public PageResult() {
    }

    public PageResult( int totalCount, int currentPage, int limit, List<?> dataList) {
        this.totalCount = totalCount;
        this.currentPage = currentPage;
        this.limit = limit;
        this.dataList = dataList;
        this.totalPage = getTotalPage();
    }
}
