package org.ailab.tool.wikiProcess.p3_abst;

import org.ailab.wimfra.util.cache.Cache;

/**
 * User: lutingming
 * Date: 16-1-16
 * Time: 下午2:22
 * Desc: 维基页面摘要缓存
 */
public class PageAbstCache extends Cache<Integer, String> {
    protected PageAbstDAO pageAbstDAO;

    public PageAbstCache(int maxSize) {
        super(maxSize);
        this.pageAbstDAO = new PageAbstDAO();
    }

    @Override
    protected String query(Integer entityId) {
        PageAbst pageAbst = pageAbstDAO.getByEntityId(entityId);
        if(pageAbst == null) {
            return null;
        } else {
            return pageAbst.getAbst();
        }
    }
}
