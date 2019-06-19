package com.agent.czb.server.listener;



import com.agent.czb.server.ChatServer;
import com.agent.czb.server.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author linkai
 * @since 16/8/9
 */
public class ServerListener implements ServletContextListener {
    private static Logger log = LoggerFactory.getLogger(ServerListener.class);
    private ChatServer server;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        server = new ChatServer(Constants.SERVER_HOME, Constants.SERVER_PORT);
        server.start();
        log.info("socket server start!");
        Constants.server = server;

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        server.stop();
        log.info("socket server end!");
    }
}
