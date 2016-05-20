package org.ailab.entityLinking_old.wim.linkResult;

import org.ailab.wimfra.core.BaseDTO;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-18
 * 功能描述：连接结果
 */
public class LinkResult extends BaseDTO {
    protected String batchName;
    protected String batchStartTime;

	// 文档ID
	protected int docId;

	// 日期
	protected String pubdate;

	// 实体类型
	protected char entityType;

	// 实体ID
	protected int entityId;

	// 实体名称
	protected String entityTitle;

	// 实体值
	protected String mention;

	// 开始位置
	protected long startIdx;

	// 结束位置
	protected long endIdx;

	// 实验ID
	protected String groupName;
	protected String featureDesc;
	protected String experimentName;
	protected String startTime;
	protected String linkModel;
	protected String classificationModel;
	protected String samplerName;
	protected String stepName;

	// 最大概率
	protected double probability0;

	// 最大概率实体ID
	protected int entityId0;

	// 最大概率实体标题
	protected String entityTitle0;

	// 次大概率
	protected double probability1;

	// 次大概率实体ID
	protected int entityId1;

	// 次大概率实体标题
	protected String entityTitle1;

	// 最大与次大概率之差
	protected double probabilityDiff;

	// 是否正确
	protected int correct;
	protected int hasCorrectCandidate;

	/**
	 * 得到文档ID
	 * @return 文档ID
	 */
	public int getDocId() {
		return docId;
	}

	/**
	 * 设置文档ID
	 * @param docId 文档ID
	 */
	public void setDocId(int docId) {
		this.docId = docId;
	}

	/**
	 * 得到日期
	 * @return 日期
	 */
	public String getPubdate() {
		return pubdate;
	}

	/**
	 * 设置日期
	 * @param pubdate 日期
	 */
	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}

	/**
	 * 得到实体类型
	 * @return 实体类型
	 */
	public char getEntityType() {
		return entityType;
	}

	/**
	 * 设置实体类型
	 * @param entityType 实体类型
	 */
	public void setEntityType(char entityType) {
		this.entityType = entityType;
	}

	/**
	 * 得到实体ID
	 * @return 实体ID
	 */
	public int getEntityId() {
		return entityId;
	}

	/**
	 * 设置实体ID
	 * @param entityId 实体ID
	 */
	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

	/**
	 * 得到实体名称
	 * @return 实体名称
	 */
	public String getEntityTitle() {
		return entityTitle;
	}

	/**
	 * 设置实体名称
	 * @param entityTitle 实体名称
	 */
	public void setEntityTitle(String entityTitle) {
		this.entityTitle = entityTitle;
	}

	/**
	 * 得到实体值
	 * @return 实体值
	 */
	public String getMention() {
		return mention;
	}

	/**
	 * 设置实体值
	 * @param mention 实体值
	 */
	public void setMention(String mention) {
		this.mention = mention;
	}

	/**
	 * 得到开始位置
	 * @return 开始位置
	 */
	public long getStartIdx() {
		return startIdx;
	}

	/**
	 * 设置开始位置
	 * @param startIdx 开始位置
	 */
	public void setStartIdx(long startIdx) {
		this.startIdx = startIdx;
	}

	/**
	 * 得到结束位置
	 * @return 结束位置
	 */
	public long getEndIdx() {
		return endIdx;
	}

	/**
	 * 设置结束位置
	 * @param endIdx 结束位置
	 */
	public void setEndIdx(long endIdx) {
		this.endIdx = endIdx;
	}

	/**
	 * 得到实验ID
	 * @return 实验ID
	 */
	public String getExperimentName() {
		return experimentName;
	}

	/**
	 * 设置实验ID
	 * @param experimentName 实验ID
	 */
	public void setExperimentName(String experimentName) {
		this.experimentName = experimentName;
	}

	/**
	 * 得到最大概率
	 * @return 最大概率
	 */
	public double getProbability0() {
		return probability0;
	}

	/**
	 * 设置最大概率
	 * @param probability0 最大概率
	 */
	public void setProbability0(double probability0) {
		this.probability0 = probability0;
	}

	/**
	 * 得到最大概率实体ID
	 * @return 最大概率实体ID
	 */
	public int getEntityId0() {
		return entityId0;
	}

	/**
	 * 设置最大概率实体ID
	 * @param entityId0 最大概率实体ID
	 */
	public void setEntityId0(int entityId0) {
		this.entityId0 = entityId0;
	}

	/**
	 * 得到最大概率实体标题
	 * @return 最大概率实体标题
	 */
	public String getEntityTitle0() {
		return entityTitle0;
	}

	/**
	 * 设置最大概率实体标题
	 * @param entityTitle0 最大概率实体标题
	 */
	public void setEntityTitle0(String entityTitle0) {
		this.entityTitle0 = entityTitle0;
	}

	/**
	 * 得到次大概率
	 * @return 次大概率
	 */
	public double getProbability1() {
		return probability1;
	}

	/**
	 * 设置次大概率
	 * @param probability1 次大概率
	 */
	public void setProbability1(double probability1) {
		this.probability1 = probability1;
	}

	/**
	 * 得到次大概率实体ID
	 * @return 次大概率实体ID
	 */
	public int getEntityId1() {
		return entityId1;
	}

	/**
	 * 设置次大概率实体ID
	 * @param entityId1 次大概率实体ID
	 */
	public void setEntityId1(int entityId1) {
		this.entityId1 = entityId1;
	}

	/**
	 * 得到次大概率实体标题
	 * @return 次大概率实体标题
	 */
	public String getEntityTitle1() {
		return entityTitle1;
	}

	/**
	 * 设置次大概率实体标题
	 * @param entityTitle1 次大概率实体标题
	 */
	public void setEntityTitle1(String entityTitle1) {
		this.entityTitle1 = entityTitle1;
	}

	/**
	 * 得到最大与次大概率之差
	 * @return 最大与次大概率之差
	 */
	public double getProbabilityDiff() {
		return probabilityDiff;
	}

	/**
	 * 设置最大与次大概率之差
	 * @param probabilityDiff 最大与次大概率之差
	 */
	public void setProbabilityDiff(double probabilityDiff) {
		this.probabilityDiff = probabilityDiff;
	}

	/**
	 * 得到是否正确
	 * @return 是否正确
	 */
	public int getCorrect() {
		return correct;
	}

	/**
	 * 设置是否正确
	 * @param correct 是否正确
	 */
	public void setCorrect(int correct) {
		this.correct = correct;
	}

    public int getHasCorrectCandidate() {
        return hasCorrectCandidate;
    }

    public void setHasCorrectCandidate(int hasCorrectCandidate) {
        this.hasCorrectCandidate = hasCorrectCandidate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getClassificationModel() {
        return classificationModel;
    }

    public void setClassificationModel(String classificationModel) {
        this.classificationModel = classificationModel;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getFeatureDesc() {
        return featureDesc;
    }

    public void setFeatureDesc(String featureDesc) {
        this.featureDesc = featureDesc;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getBatchStartTime() {
        return batchStartTime;
    }

    public void setBatchStartTime(String batchStartTime) {
        this.batchStartTime = batchStartTime;
    }

    public String getLinkModel() {
        return linkModel;
    }

    public void setLinkModel(String linkModel) {
        this.linkModel = linkModel;
    }

    public String getSamplerName() {
        return samplerName;
    }

    public void setSamplerName(String samplerName) {
        this.samplerName = samplerName;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }
}
