package org.ailab.entityLinking_old.wim.doc;

import org.ailab.tem.DBConfig;
import org.ailab.wimfra.core.*;
import org.ailab.wimfra.util.*;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;


/**
 * 作	者: Lu Tingming
 * 创建日期: 2015-05-07
 * 功能描述：文章业务逻辑
 */
public class DocDAO extends BaseDAO {
	/** 日志工具 */
	private Logger logger = Logger.getLogger(DocDAO.class);

    public DocDAO() {
        this.dbConnectConfig = DBConfig.docDB.getName();
    }

    /**
	 * 得到表名
	 * @return 表名
	 */
	protected String getTableName() {
	    return "doc";
	}


	/**
	 * 得到查询一条记录的SQL
	 * @return 查询一条记录的SQL
	 */
	protected String getSqlForGet() {
		return "select d.*, dl.content from doc d, doc_lob dl where d.id=dl.id and d.id = ?";
	}


	/**
	 * 得到查询多条记录的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForGetList() {
		return "SELECT d.*, NULL content FROM doc d";
	}


	/**
	 * 得到查询记录总数的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForCount() {
		return "SELECT count(*) FROM doc";
	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete(String keys) {
		return "DELETE FROM doc WHERE id in (" + keys + ")";
	}


	/**
	 * 将resultSet中的值设置到dto中
	 * @param res
	 * @return dto
	 * @throws java.sql.SQLException
	 */
	protected BaseDTO resultSetToDto(ResultSet res) throws SQLException {
		Doc dto = new Doc();

		dto.setId(res.getInt("id"));
		dto.setTitle(res.getString("title"));
		dto.setAbst(res.getString("abst"));
		dto.setUrl(res.getString("url"));
		dto.setLink(res.getString("link"));
		dto.setPath(res.getString("path"));
		dto.setPubdate(res.getString("pubdate"));
		dto.setPubTime(res.getString("pubTime"));
		dto.setUpdateTime(res.getString("updateTime"));
		dto.setProviderId(res.getInt("providerId"));
		dto.setRssFeedId(res.getInt("rssFeedId"));
		dto.setSiteFeedId(res.getInt("siteFeedId"));
		dto.setPubCountryId(res.getInt("pubCountryId"));
		dto.setLanguageId(res.getInt("languageId"));
		dto.setIsContentExtracted(res.getBoolean("isContentExtracted"));
		dto.setIsClusterProcessed(res.getBoolean("isClusterProcessed"));
		dto.setIsTopicProcessed(res.getBoolean("isTopicProcessed"));
		dto.setIsEventInstExtracted(res.getBoolean("isEventInstExtracted"));
		dto.setIsEventIntegrated(res.getBoolean("isEventIntegrated"));
		dto.setDocClusterId(res.getInt("docClusterId"));
		dto.setSlDailyTopicId(res.getInt("slDailyTopicId"));
		dto.setDailyTopicId(res.getInt("dailyTopicId"));
		dto.setSlTopicId(res.getInt("slTopicId"));
		dto.setTopicId(res.getInt("topicId"));
		dto.setEventInstId(res.getInt("eventInstId"));
		dto.setUpdaterId(res.getInt("updaterId"));
		dto.setTheme1(res.getInt("theme1"));
		dto.setTheme2(res.getInt("theme2"));
		dto.setTheme3(res.getInt("theme3"));
		dto.setTheme4(res.getInt("theme4"));
		dto.setTheme5(res.getInt("theme5"));
		dto.setAnnoState(res.getInt("annoState"));
		dto.setContent(res.getString("content"));

		return dto;
	}


	/**
	 * 得到insert语句的SQL
	 * @return insert语句的SQL
	 */
	protected String getSqlForInsert() {
		return "INSERT INTO doc (id, title, abst, url, link, path, pubdate, pubTime, updateTime, providerId, rssFeedId, siteFeedId, pubCountryId, languageId, isContentExtracted, isClusterProcessed, isTopicProcessed, isEventInstExtracted, isEventIntegrated, docClusterId, slDailyTopicId, dailyTopicId, slTopicId, topicId, eventInstId, updaterId, theme1, theme2, theme3, theme4, theme5, fieldOfMediaInfo, startPositionOfMediaInfo, endPositionOfMediaInof, textOfMediaInfo)VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	}


	/**
	 * 为insert设置参数
	 */
	protected void setParametersForInsert(List<Object> paramList, BaseDTO baseDTO) {
		Doc dto = (Doc) baseDTO;

		paramList.add(dto.getId());
		paramList.add(dto.getTitle());
		paramList.add(dto.getAbst());
		paramList.add(dto.getUrl());
		paramList.add(dto.getLink());
		paramList.add(dto.getPath());
		paramList.add(dto.getPubdate());
		paramList.add(dto.getPubTime());
		paramList.add(dto.getUpdateTime());
		paramList.add(dto.getProviderId());
		paramList.add(dto.getRssFeedId());
		paramList.add(dto.getSiteFeedId());
		paramList.add(dto.getPubCountryId());
		paramList.add(dto.getLanguageId());
		paramList.add(dto.getIsContentExtracted());
		paramList.add(dto.getIsClusterProcessed());
		paramList.add(dto.getIsTopicProcessed());
		paramList.add(dto.getIsEventInstExtracted());
		paramList.add(dto.getIsEventIntegrated());
		paramList.add(dto.getDocClusterId());
		paramList.add(dto.getSlDailyTopicId());
		paramList.add(dto.getDailyTopicId());
		paramList.add(dto.getSlTopicId());
		paramList.add(dto.getTopicId());
		paramList.add(dto.getEventInstId());
		paramList.add(dto.getUpdaterId());
		paramList.add(dto.getTheme1());
		paramList.add(dto.getTheme2());
		paramList.add(dto.getTheme3());
		paramList.add(dto.getTheme4());
		paramList.add(dto.getTheme5());
		paramList.add(String.valueOf(dto.getFieldOfMediaInfo()));
paramList.add(dto.getFieldOfMediaInfo());
		paramList.add(dto.getStartPositionOfMediaInfo());
		paramList.add(dto.getEndPositionOfMediaInof());
		paramList.add(dto.getTextOfMediaInfo());

	}


	/**
	 * 得到update语句的SQL
	 * @return update语句的SQL
	 */
	protected String getSqlForUpdate() {
		return "UPDATE doc SET title = ?, abst = ?, url = ?, link = ?, path = ?, pubdate = ?, pubTime = ?, updateTime = ?, providerId = ?, rssFeedId = ?, siteFeedId = ?, pubCountryId = ?, languageId = ?, isContentExtracted = ?, isClusterProcessed = ?, isTopicProcessed = ?, isEventInstExtracted = ?, isEventIntegrated = ?, docClusterId = ?, slDailyTopicId = ?, dailyTopicId = ?, slTopicId = ?, topicId = ?, eventInstId = ?, updaterId = ?, theme1 = ?, theme2 = ?, theme3 = ?, theme4 = ?, theme5 = ?, fieldOfMediaInfo = ?, startPositionOfMediaInfo = ?, endPositionOfMediaInof = ?, textOfMediaInfo = ? WHERE id = ?";
	}


	/**
	 * 为update设置参数
	 */
	protected void setParametersForUpdate(List<Object> paramList, BaseDTO baseDTO) {
		Doc dto = (Doc) baseDTO;

		paramList.add(dto.getTitle());
		paramList.add(dto.getAbst());
		paramList.add(dto.getUrl());
		paramList.add(dto.getLink());
		paramList.add(dto.getPath());
		paramList.add(dto.getPubdate());
		paramList.add(dto.getPubTime());
		paramList.add(dto.getUpdateTime());
		paramList.add(dto.getProviderId());
		paramList.add(dto.getRssFeedId());
		paramList.add(dto.getSiteFeedId());
		paramList.add(dto.getPubCountryId());
		paramList.add(dto.getLanguageId());
		paramList.add(dto.getIsContentExtracted());
		paramList.add(dto.getIsClusterProcessed());
		paramList.add(dto.getIsTopicProcessed());
		paramList.add(dto.getIsEventInstExtracted());
		paramList.add(dto.getIsEventIntegrated());
		paramList.add(dto.getDocClusterId());
		paramList.add(dto.getSlDailyTopicId());
		paramList.add(dto.getDailyTopicId());
		paramList.add(dto.getSlTopicId());
		paramList.add(dto.getTopicId());
		paramList.add(dto.getEventInstId());
		paramList.add(dto.getUpdaterId());
		paramList.add(dto.getTheme1());
		paramList.add(dto.getTheme2());
		paramList.add(dto.getTheme3());
		paramList.add(dto.getTheme4());
		paramList.add(dto.getTheme5());
		paramList.add(String.valueOf(dto.getFieldOfMediaInfo()));
paramList.add(dto.getFieldOfMediaInfo());
		paramList.add(dto.getStartPositionOfMediaInfo());
		paramList.add(dto.getEndPositionOfMediaInof());
		paramList.add(dto.getTextOfMediaInfo());

		paramList.add(dto.getId());

	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete() {
		return "DELETE FROM doc WHERE id = ?";
	}


	/**
	 * 由id得到doc
	 * @param id id
	 */
	public Doc get(int id) throws SQLException {
        Doc doc;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(id);
        doc = (Doc) getBySql("select * from doc where id = ?", paramList);

        return doc;
	}


    public int updateAnno(Doc doc) throws SQLException {
        return executeUpdate(
                "update "+getTableName()+" set annoState=?, updaterId=?, updateTime=? where id=?",
                doc.annoState,
                doc.updaterId,
                doc.updateTime,
                doc.id);
    }


    public Set<Integer> getDocIdSetByAnnoState(String annoState) {
        return getIntSet("select id from "+getTableName()+" where annoState in("+annoState+")");
    }
}
