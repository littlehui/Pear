package com.pear.commons.tools.spring.domain;

/**
 * User: littlehui
 * Date: 15-5-4
 * Time: 下午5:48
 */
public class PagedOperator {
    public static <T> Paged<T> createPagedForParam(PageQueryParam pageQueryParam) {
        Paged<T> paged = new Paged<T>();
        paged.setTotalSize(pageQueryParam.getTotalCount());
        paged.setPageNo(pageQueryParam.getPageNo());
        paged.setTotalPage(pageQueryParam.getTotalPage());
        paged.setPageSize(pageQueryParam.getPageSize());
        return paged;
    }
}
