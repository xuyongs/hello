package com.agent.czb.web.system.service;

import com.agent.czb.web.system.bean.SiteColumn;
import com.agent.czb.web.system.model.BaseModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/spring-config.xml"})
public class SiteColumnServiceTest {
    @Resource
    private SiteColumnService<SiteColumn> siteColumnService;
    
    @Test
    public void testQueryBySiteId() throws Exception {
        SiteColumn bean = new SiteColumn();
        bean.setSiteId(3);
        bean.setName("MOKO!美空  展示");
        bean.setLink("http://www.moko.cc/moko/post/1.html");
        bean.setPic("http://img1.moko.cc/users/0/9/2805/post/a2/img1_cover_6971997.jpg");
        siteColumnService.add(bean);


        bean = new SiteColumn();
        bean.setSiteId(3);
        bean.setName("MOKO!美空  模特儿");
        bean.setLink("http://www.moko.cc/channels/post/23/1.html");
        bean.setPic("http://img1.moko.cc/users/14/4322/1296697/post/31/img1_mokoshow_6973444.jpg");
        siteColumnService.add(bean);
    }

    @Test
    public void testGetMapper() throws Exception {
        int i = siteColumnService.getMapper().queryByCount(new BaseModel());
    }
}