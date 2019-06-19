package com.agent.czb.client.listener;

/**
 * @author linkai
 * @since 16/8/9
 */
public class ClienListener /*implements ServletContextListener*/ {
    /*private static Logger log = LoggerFactory.getLogger(ClienListener.class);
    private ChatClient client;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // 设置jnl路径
        System.setProperty("java.library.path", "C:\\Program Files (x86)\\EParkingManage\\x64");

        // 启动客户端
        client = new ChatClient(Constants.CLIENT_URI);
        try {
            client.connect();
            log.info("socket client start!");
        } catch (URISyntaxException e) {
            log.error("socket client start error!", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        client.disconnect();
        log.info("socket client end!");
    }*/
}
