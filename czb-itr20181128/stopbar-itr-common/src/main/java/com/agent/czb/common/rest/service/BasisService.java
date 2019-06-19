package com.agent.czb.common.rest.service;

import com.agent.czb.common.rest.PageService;
import com.agent.czb.common.rest.mapper.BasisMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public abstract class BasisService<M extends BasisMapper<T>, T> extends PageService {
    @Autowired
    protected M mapper;

    public M getMapper() {
        return mapper;
    }

    public int delete(Object id) {
        return mapper.delete(id);
    }

    public int delete(Object[] id) {
        int count = 0;
        for (Object obj : id) {
            int num = mapper.delete(obj);
            count += num;
        }
        return count;
    }

    public int insert(T t) {
        return mapper.insert(t);
    }

    public T selectById(Object id) {
        return mapper.selectById(id);
    }

    public int updateBySelective(T t) {
        return mapper.updateBySelective(t);
    }

    public int update(T t) {
        return mapper.update(t);
    }

    public Long pageCount(Map map) {
        return mapper.pageCount(map);
    }

    public List<T> pageList(Map map) {
        return mapper.pageList(map);
    }
}
