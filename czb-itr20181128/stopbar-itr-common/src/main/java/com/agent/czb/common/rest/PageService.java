package com.agent.czb.common.rest;

import java.util.List;
import java.util.Map;

public abstract class PageService {
    abstract public Long pageCount(Map map);
    abstract public List<?> pageList(Map map);
}
