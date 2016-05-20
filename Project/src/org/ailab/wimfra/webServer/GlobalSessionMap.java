package org.ailab.wimfra.webServer;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 13-12-20
 * Time: 下午5:45
 * 全局会话映射（ID到对象）
 */
public class GlobalSessionMap {
    private static Map<String, HttpSession> map = new HashMap<String, HttpSession>();

    public static HttpSession get(String sessionId) {
        return map.get(sessionId);
    }

    public static void add(HttpSession session) {
        if(session == null) {
            return;
        }
        map.put(session.getId(), session);
    }

    public static boolean remove(String sessionId) {
        return map.remove(sessionId) != null;
    }

    public static boolean remove(HttpSession session) {
        if(session == null) {
            return false;
        }
        return remove(session.getId());
    }

    public static int size() {
        return map.size();
    }
}
