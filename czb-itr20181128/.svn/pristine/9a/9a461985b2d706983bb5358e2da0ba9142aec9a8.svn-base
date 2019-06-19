package com.agent.czb.web.system.service;

import com.agent.czb.web.system.bean.SiteColumn;
import com.agent.czb.web.system.bean.SiteMain;
import com.agent.czb.web.system.bean.SiteType;
import com.agent.czb.web.system.bean.SysMenu;
import org.apache.commons.collections.ListUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/spring-config.xml"})
public class SiteMainServiceTest {

    @Resource
    private SiteMainService<SiteMain> siteMainService;
    //	UserMainService<UserMain> userMainService;
    @Resource
    private SiteTypeService<SiteType> siteTypeService;
    @Resource
    private SiteColumnService<SiteColumn> siteColumnService;
    @Resource
    private SysMenuService<SysMenu> sysMenuService;

    @Test
    public void testAddTypeRel() throws Exception {
        List<SysMenu> rootMenus = sysMenuService.getRootMenu(null);// 查询所有根节点
        List<SysMenu> childMenus = sysMenuService.getChildMenu();//查询所有子节点
        List<SysMenu> newMenus = ListUtils.sum(rootMenus, childMenus);
        System.out.println("rootMenus:"+rootMenus.size() +" childMenus:"+childMenus.size()+" newMenus:"+newMenus.size());
        for(SysMenu menu: newMenus){
            System.out.println(menu.getUrl());
        }
    }

    @Test
    public void testGetTypesBySiteId() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {

    }

    @Test
    public void testGetMapper() throws Exception {

    }
}