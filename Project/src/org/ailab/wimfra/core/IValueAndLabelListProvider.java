package org.ailab.wimfra.core;

import java.util.List;

/**
 * User: Lu Tingming
 * Date: 2012-10-17
 * Time: 2:03:21
 * Desc: “值-文字”列表提供接口
 */
public interface IValueAndLabelListProvider {
    /**
     * 返回“值-文字”列表
     */
    public List<IValueAndLabel> getValueAndTextList() throws Exception;

}
