package org.ailab.tem.wim.user;

import org.ailab.wimfra.core.IValueAndLabelListProvider;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lutingming
 * Date: 13-12-17
 * Time: 下午12:49
 */
public class AnnotatorProvider implements IValueAndLabelListProvider {
    @Override
    public List getValueAndTextList() throws Exception {
        return UserBL.annotatorList;
    }
}
