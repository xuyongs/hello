package com.agent.czb.web.system.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.agent.czb.web.system.mapper.DataDictMapper;

@Service("dataDictService")
public class DataDictService<T> extends BaseService<T> {
    private final static Logger log= Logger.getLogger(DataDictService.class);

    @Autowired
    private DataDictMapper<T> mapper;


    public DataDictMapper<T> getMapper() {
        return mapper;
    }
}
