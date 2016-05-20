package org.ailab.tem;

import org.apache.log4j.Logger;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * User: Lu Tingming
 * Date: 2012-11-10
 * Time: 16:51:47
 * Desc: 服务监听器
 */
public class ServerListener implements ServletContextListener {
    Logger logger = Logger.getLogger(this.getClass());

    public void contextInitialized(ServletContextEvent event) {
        logger.info("contextInitialized begin");

        logger.info("contextInitialized finished");
    }

    public void contextDestroyed(ServletContextEvent event) {
        logger.info("contextDestroyed begin");

        logger.info("contextDestroyed finished");
    }
}
