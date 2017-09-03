package com.liangyt.common.repository;

import java.util.List;

/**
 * 描述：分页
 *
 * @author tony
 * @创建时间 2017-08-27 21:43
 */
@SuppressWarnings("unused")
public class Pageable<T> {
    private int pageNum;
    private int pageSize;
    private Sort sort;

    private List<T> rows;
    private long total;

    public Pageable(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public Pageable(int pageNum, int pageSize, Sort sort) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.sort = sort;
    }

    public Pageable(List<T> rows, long total) {
        this.rows = rows;
        this.total = total;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
