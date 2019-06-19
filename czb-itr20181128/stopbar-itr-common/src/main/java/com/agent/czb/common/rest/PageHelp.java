package com.agent.czb.common.rest;

import org.apache.commons.collections4.MapUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageHelp implements Serializable {
    private static final long serialVersionUID = 3158534812777296349L;

    /**
     * 当前页数
     * 0:表示不分页
     */
    private Integer pageNum = 1;
    /**
     * 每页大小
     */
    private Integer pageSize = 10;
    private Object data;
    private Long total;

    public PageHelp() {
    }

    public PageHelp(Integer pageNum, Integer pageSize) {
        this();
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public boolean isPage() {
        return pageNum != null && pageNum > 0;
    }

    public long getCurrentPage() {
        if (isPage()) {
            return (pageNum - 1) * pageSize;
        }
        return 0;
    }

    public void addPagerLimit(Map map) {
        if (isPage()) {
            map.put("pagerLimit", " limit " + this.getCurrentPage() + "," + this.getPageSize());
        }
        String sort = map.get("sort").toString();
        if (sort != null && !"".equals(sort.trim())) {
            String orderBy = "order by " + sort;
            String order = map.get("order").toString();
            if (order != null && !"".equals(order.trim())) {
                orderBy = orderBy + " " + order;
            }
            map.put("pagerOrder", orderBy);
        }
    }

    public void page(PageService service, Map map) {
        addPagerLimit(map);
        List<?> list = service.pageList(map);
        this.data = list;
        if (isPage()) {
            this.total = service.pageCount(map);
        } else {
            this.total = (long) list.size();
        }
    }

    @Override
    public String toString() {
        return "PageHelp{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", data='" + data + '\'' +
                ", total='" + total + '\'' +
                '}';
    }

    private Map paramMap;

    public static PageHelp newPageHelp(HttpServletRequest request) {
        Map<String, String> map = RestUtils.request2map(request);
        return newPageHelp(map);
    }

    public static PageHelp newPageHelp(Map<String, String> map) {
        PageHelp pageHelp = new PageHelp();
        pageHelp.paramMap = map;
        // 当前页数
        {
            String page = map.get("page");
            if (page != null && !"".equals(page.trim())) {
                pageHelp.pageNum = Integer.valueOf(page);
            } else {
                pageHelp.pageNum = MapUtils.getInteger(map, "pageNum", pageHelp.pageNum);
            }
        }
        // 总页数
        {
            String rows = map.get("rows");
            if (rows != null && !"".equals(rows.trim())) {
                pageHelp.pageSize = Integer.valueOf(rows);
            } else {
                pageHelp.pageSize = MapUtils.getInteger(map, "pageSize", pageHelp.pageSize);
            }
        }
        // 分页
        if (pageHelp.isPage()) {
            pageHelp.paramMap.put("pagerLimit", " limit " + pageHelp.getCurrentPage() + "," + pageHelp.getPageSize());
        }
        // 排序
        String sort = map.get("sort");
        if (sort != null && !"".equals(sort.trim())) {
            String orderBy = "order by " + sort;
            String order = map.get("order");
            if (order != null && !"".equals(order.trim())) {
                orderBy = orderBy + " " + order;
            }
            map.put("pagerOrder", orderBy);
        }
        return pageHelp;
    }

    public static PageHelp newPageHelp(Integer pageNum, Integer pageSize) {
        return new PageHelp(pageNum, pageSize);
    }

    public Map params() {
        return paramMap;
    }

    public PageHelp addParam(String key, Object val) {
        if (paramMap == null) {
            synchronized (this) {
                if (paramMap == null) {
                    paramMap = new HashMap();
                }
            }
        }
        paramMap.put(key, val);
        return this;
    }

    public PageHelp page(PageService service) {
        List<?> list;
        if (isPage()) {
        	System.out.println("参数："+paramMap);
            list = service.pageList(paramMap);
            this.total = service.pageCount(paramMap);
        } else {
            list = service.pageList(paramMap);
            this.total = (long) list.size();
        }
        this.data = list;
        return this;
    }

    public ResultHelp toResultHelp() {
        return ResultHelp.newResult(this);
    }
}
