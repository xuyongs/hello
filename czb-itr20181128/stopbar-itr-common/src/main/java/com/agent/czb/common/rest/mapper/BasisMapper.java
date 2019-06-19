package com.agent.czb.common.rest.mapper;

import java.util.List;
import java.util.Map;

public interface BasisMapper<T> {
    int insert(T t);

    int update(T t);

    int updateBySelective(T t);

    int delete(Object id);

    T selectById(Object id);

    Long pageCount(Map map);

    List<T> pageList(Map map);
}
