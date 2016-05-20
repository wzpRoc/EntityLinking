package org.ailab.entityLinking_old.wim.doc;

import org.ailab.entityLinking_old.wim.AnnoState;
import org.ailab.entityLinking_old.wim.docEntity.DocEntity;
import org.ailab.nlp.tfidf.IContent;
import org.ailab.tem.wim.user.UserBL;
import org.ailab.wimfra.core.BaseDTO;

import java.util.List;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-07
 * 功能描述：文章
 */
public class Doc extends BaseDTO implements IContent {
	// ID
	protected int id;

	// 标题
	protected String title;

	// 摘要
	protected String abst;

	// URL
	protected String url;

	// Link
	protected String link;

	// 本地路径
	protected String path;

	// 发布日期
	protected String pubdate;

	// 发布时间
	protected String pubTime;

	// 修改时间
	protected String updateTime;

	// 信息提供商ID
	protected int providerId;

	// RSS种子ID
	protected int rssFeedId;

	// 网站种子ID
	protected int siteFeedId;

	// 发布国家ID
	protected int pubCountryId;

	// 语言ID
	protected int languageId;

	// 正文是否已抽取
	protected boolean isContentExtracted;

	// 聚类是否已处理
	protected boolean isClusterProcessed;

	// 话题是否已处理
	protected boolean isTopicProcessed;

	// 事件实例是否已抽取
	protected boolean isEventInstExtracted;

	// 事件是否已集成
	protected boolean isEventIntegrated;

	// 文档聚类ID
	protected int docClusterId;

	// 单语种单日话题ID
	protected int slDailyTopicId;

	// 单日话题ID
	protected int dailyTopicId;

	// 单语种话题ID
	protected int slTopicId;

	// 话题ID
	protected int topicId;

	// 事件实例ID
	protected int eventInstId;

	// 事件ID
	protected int updaterId;

	// 分类
	protected int theme1;

	// 分类
	protected int theme2;

	// 分类
	protected int theme3;

	// 分类
	protected int theme4;

	// 分类
	protected int theme5;

	protected int annoState;

	// 所在域（媒体信息）
	protected char fieldOfMediaInfo;

	// 开始位置（媒体信息）
	protected int startPositionOfMediaInfo;

	// 结束位置（媒体信息）
	protected int endPositionOfMediaInof;

	// 文本（媒体信息）
	protected String textOfMediaInfo;


    protected String content;

    List<DocEntity> docEntityList;

    /**
	 * 得到ID
	 * @return ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * 设置ID
	 * @param id ID
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 得到标题
	 * @return 标题
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置标题
	 * @param title 标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 得到摘要
	 * @return 摘要
	 */
	public String getAbst() {
		return abst;
	}

	/**
	 * 设置摘要
	 * @param abst 摘要
	 */
	public void setAbst(String abst) {
		this.abst = abst;
	}

	/**
	 * 得到URL
	 * @return URL
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置URL
	 * @param url URL
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 得到Link
	 * @return Link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * 设置Link
	 * @param link Link
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * 得到本地路径
	 * @return 本地路径
	 */
	public String getPath() {
		return path;
	}

	/**
	 * 设置本地路径
	 * @param path 本地路径
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * 得到发布日期
	 * @return 发布日期
	 */
	public String getPubdate() {
		return pubdate;
	}

	/**
	 * 设置发布日期
	 * @param pubdate 发布日期
	 */
	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}

	/**
	 * 得到发布时间
	 * @return 发布时间
	 */
	public String getPubTime() {
		return pubTime;
	}

	/**
	 * 设置发布时间
	 * @param pubTime 发布时间
	 */
	public void setPubTime(String pubTime) {
		this.pubTime = pubTime;
	}

	/**
	 * 得到修改时间
	 * @return 修改时间
	 */
	public String getUpdateTime() {
		return updateTime;
	}

	public String getUpdateShortTime() {
        if(updateTime!=null && updateTime.length()>16) {
            return updateTime.substring(0, 16);
        } else {
            return updateTime;
        }
	}

	/**
	 * 设置修改时间
	 * @param updateTime 修改时间
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 得到信息提供商ID
	 * @return 信息提供商ID
	 */
	public int getProviderId() {
		return providerId;
	}

	/**
	 * 设置信息提供商ID
	 * @param providerId 信息提供商ID
	 */
	public void setProviderId(int providerId) {
		this.providerId = providerId;
	}

	/**
	 * 得到RSS种子ID
	 * @return RSS种子ID
	 */
	public int getRssFeedId() {
		return rssFeedId;
	}

	/**
	 * 设置RSS种子ID
	 * @param rssFeedId RSS种子ID
	 */
	public void setRssFeedId(int rssFeedId) {
		this.rssFeedId = rssFeedId;
	}

	/**
	 * 得到网站种子ID
	 * @return 网站种子ID
	 */
	public int getSiteFeedId() {
		return siteFeedId;
	}

	/**
	 * 设置网站种子ID
	 * @param siteFeedId 网站种子ID
	 */
	public void setSiteFeedId(int siteFeedId) {
		this.siteFeedId = siteFeedId;
	}

	/**
	 * 得到发布国家ID
	 * @return 发布国家ID
	 */
	public int getPubCountryId() {
		return pubCountryId;
	}

	/**
	 * 设置发布国家ID
	 * @param pubCountryId 发布国家ID
	 */
	public void setPubCountryId(int pubCountryId) {
		this.pubCountryId = pubCountryId;
	}

	/**
	 * 得到语言ID
	 * @return 语言ID
	 */
	public int getLanguageId() {
		return languageId;
	}

	/**
	 * 设置语言ID
	 * @param languageId 语言ID
	 */
	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}

	/**
	 * 得到正文是否已抽取
	 * @return 正文是否已抽取
	 */
	public boolean getIsContentExtracted() {
		return isContentExtracted;
	}

	/**
	 * 设置正文是否已抽取
	 * @param isContentExtracted 正文是否已抽取
	 */
	public void setIsContentExtracted(boolean isContentExtracted) {
		this.isContentExtracted = isContentExtracted;
	}

	/**
	 * 得到聚类是否已处理
	 * @return 聚类是否已处理
	 */
	public boolean getIsClusterProcessed() {
		return isClusterProcessed;
	}

	/**
	 * 设置聚类是否已处理
	 * @param isClusterProcessed 聚类是否已处理
	 */
	public void setIsClusterProcessed(boolean isClusterProcessed) {
		this.isClusterProcessed = isClusterProcessed;
	}

	/**
	 * 得到话题是否已处理
	 * @return 话题是否已处理
	 */
	public boolean getIsTopicProcessed() {
		return isTopicProcessed;
	}

	/**
	 * 设置话题是否已处理
	 * @param isTopicProcessed 话题是否已处理
	 */
	public void setIsTopicProcessed(boolean isTopicProcessed) {
		this.isTopicProcessed = isTopicProcessed;
	}

	/**
	 * 得到事件实例是否已抽取
	 * @return 事件实例是否已抽取
	 */
	public boolean getIsEventInstExtracted() {
		return isEventInstExtracted;
	}

	/**
	 * 设置事件实例是否已抽取
	 * @param isEventInstExtracted 事件实例是否已抽取
	 */
	public void setIsEventInstExtracted(boolean isEventInstExtracted) {
		this.isEventInstExtracted = isEventInstExtracted;
	}

	/**
	 * 得到事件是否已集成
	 * @return 事件是否已集成
	 */
	public boolean getIsEventIntegrated() {
		return isEventIntegrated;
	}

	/**
	 * 设置事件是否已集成
	 * @param isEventIntegrated 事件是否已集成
	 */
	public void setIsEventIntegrated(boolean isEventIntegrated) {
		this.isEventIntegrated = isEventIntegrated;
	}

	/**
	 * 得到文档聚类ID
	 * @return 文档聚类ID
	 */
	public int getDocClusterId() {
		return docClusterId;
	}

	/**
	 * 设置文档聚类ID
	 * @param docClusterId 文档聚类ID
	 */
	public void setDocClusterId(int docClusterId) {
		this.docClusterId = docClusterId;
	}

	/**
	 * 得到单语种单日话题ID
	 * @return 单语种单日话题ID
	 */
	public int getSlDailyTopicId() {
		return slDailyTopicId;
	}

	/**
	 * 设置单语种单日话题ID
	 * @param slDailyTopicId 单语种单日话题ID
	 */
	public void setSlDailyTopicId(int slDailyTopicId) {
		this.slDailyTopicId = slDailyTopicId;
	}

	/**
	 * 得到单日话题ID
	 * @return 单日话题ID
	 */
	public int getDailyTopicId() {
		return dailyTopicId;
	}

	/**
	 * 设置单日话题ID
	 * @param dailyTopicId 单日话题ID
	 */
	public void setDailyTopicId(int dailyTopicId) {
		this.dailyTopicId = dailyTopicId;
	}

	/**
	 * 得到单语种话题ID
	 * @return 单语种话题ID
	 */
	public int getSlTopicId() {
		return slTopicId;
	}

	/**
	 * 设置单语种话题ID
	 * @param slTopicId 单语种话题ID
	 */
	public void setSlTopicId(int slTopicId) {
		this.slTopicId = slTopicId;
	}

	/**
	 * 得到话题ID
	 * @return 话题ID
	 */
	public int getTopicId() {
		return topicId;
	}

	/**
	 * 设置话题ID
	 * @param topicId 话题ID
	 */
	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	/**
	 * 得到事件实例ID
	 * @return 事件实例ID
	 */
	public int getEventInstId() {
		return eventInstId;
	}

	/**
	 * 设置事件实例ID
	 * @param eventInstId 事件实例ID
	 */
	public void setEventInstId(int eventInstId) {
		this.eventInstId = eventInstId;
	}

	/**
	 * 得到事件ID
	 * @return 事件ID
	 */
	public int getUpdaterId() {
		return updaterId;
	}

	public String getUpdaterName() throws NoSuchFieldException, IllegalAccessException {
		return UserBL.getRealNameFromCache(getUpdaterId());
	}

	/**
	 * 设置事件ID
	 * @param updaterId 事件ID
	 */
	public void setUpdaterId(int updaterId) {
		this.updaterId = updaterId;
	}

	/**
	 * 得到分类
	 * @return 分类
	 */
	public int getTheme1() {
		return theme1;
	}

	/**
	 * 设置分类
	 * @param theme1 分类
	 */
	public void setTheme1(int theme1) {
		this.theme1 = theme1;
	}

	/**
	 * 得到分类
	 * @return 分类
	 */
	public int getTheme2() {
		return theme2;
	}

	/**
	 * 设置分类
	 * @param theme2 分类
	 */
	public void setTheme2(int theme2) {
		this.theme2 = theme2;
	}

	/**
	 * 得到分类
	 * @return 分类
	 */
	public int getTheme3() {
		return theme3;
	}

	/**
	 * 设置分类
	 * @param theme3 分类
	 */
	public void setTheme3(int theme3) {
		this.theme3 = theme3;
	}

	/**
	 * 得到分类
	 * @return 分类
	 */
	public int getTheme4() {
		return theme4;
	}

	/**
	 * 设置分类
	 * @param theme4 分类
	 */
	public void setTheme4(int theme4) {
		this.theme4 = theme4;
	}

	/**
	 * 得到分类
	 * @return 分类
	 */
	public int getTheme5() {
		return theme5;
	}

	/**
	 * 设置分类
	 * @param theme5 分类
	 */
	public void setTheme5(int theme5) {
		this.theme5 = theme5;
	}

	/**
	 * 得到所在域（媒体信息）
	 * @return 所在域（媒体信息）
	 */
	public char getFieldOfMediaInfo() {
		return fieldOfMediaInfo;
	}

	/**
	 * 设置所在域（媒体信息）
	 * @param fieldOfMediaInfo 所在域（媒体信息）
	 */
	public void setFieldOfMediaInfo(char fieldOfMediaInfo) {
		this.fieldOfMediaInfo = fieldOfMediaInfo;
	}

	/**
	 * 得到开始位置（媒体信息）
	 * @return 开始位置（媒体信息）
	 */
	public int getStartPositionOfMediaInfo() {
		return startPositionOfMediaInfo;
	}

	/**
	 * 设置开始位置（媒体信息）
	 * @param startPositionOfMediaInfo 开始位置（媒体信息）
	 */
	public void setStartPositionOfMediaInfo(int startPositionOfMediaInfo) {
		this.startPositionOfMediaInfo = startPositionOfMediaInfo;
	}

	/**
	 * 得到结束位置（媒体信息）
	 * @return 结束位置（媒体信息）
	 */
	public int getEndPositionOfMediaInof() {
		return endPositionOfMediaInof;
	}

	/**
	 * 设置结束位置（媒体信息）
	 * @param endPositionOfMediaInof 结束位置（媒体信息）
	 */
	public void setEndPositionOfMediaInof(int endPositionOfMediaInof) {
		this.endPositionOfMediaInof = endPositionOfMediaInof;
	}

	/**
	 * 得到文本（媒体信息）
	 * @return 文本（媒体信息）
	 */
	public String getTextOfMediaInfo() {
		return textOfMediaInfo;
	}

	/**
	 * 设置文本（媒体信息）
	 * @param textOfMediaInfo 文本（媒体信息）
	 */
	public void setTextOfMediaInfo(String textOfMediaInfo) {
		this.textOfMediaInfo = textOfMediaInfo;
	}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAnnoState() {
        return annoState;
    }

    public String getAnnoStateName() {
        return AnnoState.getNameByValue(annoState);
    }

    public String getAnnoStateAbbr() {
        return AnnoState.getAbbrByValue(annoState);
    }

    public void setAnnoState(int annoState) {
        this.annoState = annoState;
    }

    public List<DocEntity> getDocEntityList() {
        return docEntityList;
    }

    public void setDocEntityList(List<DocEntity> docEntityList) {
        this.docEntityList = docEntityList;
    }

}
