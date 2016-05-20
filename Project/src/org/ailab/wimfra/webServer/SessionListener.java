package org.ailab.wimfra.webServer;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 13-12-20
 * Time: 下午5:50
 * 会话监听器
 */
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        GlobalSessionMap.add(httpSessionEvent.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        GlobalSessionMap.remove(httpSessionEvent.getSession());
    }
}
