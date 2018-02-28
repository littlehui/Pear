package com.pear.commons.tools.spring.domain;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @param <T>
 */
public class Paged<T> implements Serializable {

    private static final long serialVersionUID = 5246627934455392469L;

    /**
     * 每页默认10条数据
     */
    protected int pageSize = 10;
    /**
     * 当前页
     */
    protected int pageNo = 1;
    /**
     * 总页数
     */
    protected int totalPage = 0;
    /**
     * 总数据数
     */
    protected long totalCount = 0;
    /**
     * 数据
     */
    private List<T> listData;


    public Paged(long totalCount, int pageNo, int pageSize, List<T> list) {
        this.totalCount = totalCount;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.listData = list;
        this.totalPage = (int) ((totalCount % pageSize == 0) ? (totalCount / pageSize) : (totalCount / pageSize + 1));
    }

    public Paged() {

    }
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getTotalPage() {
        return this.totalPage = (int) ((totalCount % pageSize == 0) ? (totalCount / pageSize) : (totalCount / pageSize + 1));
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getListData() {
        return this.listData;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalSize(long totalSize) {
        this.totalCount = totalSize;
    }

    public void setList(List<T> list) {
        this.listData =  list;
    }
}
