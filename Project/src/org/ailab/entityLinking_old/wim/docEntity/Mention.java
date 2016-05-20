package org.ailab.entityLinking_old.wim.docEntity;

import org.ailab.wimfra.core.BaseDTO;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-07
 * 功能描述：文档实体
 */
public class Mention extends BaseDTO {
    // 提及
    protected String mention;

	// 开始位置
	protected long startIdx;

	// 结束位置
	protected long endIdx;

    public String getMention() {
        return mention;
    }

    public void setMention(String mention) {
        this.mention = mention;
    }

    public long getStartIdx() {
        return startIdx;
    }

    public void setStartIdx(long startIdx) {
        this.startIdx = startIdx;
    }

    public long getEndIdx() {
        return endIdx;
    }

    public void setEndIdx(long endIdx) {
        this.endIdx = endIdx;
    }
}
