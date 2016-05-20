package org.ailab.common.config;





import org.ailab.tem.DBConfig;
import org.ailab.wimfra.core.BaseDAO;
import org.ailab.wimfra.core.BaseDTO;
import org.apache.log4j.Logger;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

/**
 * 作	者: Lu Tingming
 * 创建日期: 2012-10-13
 * 功能描述：配置业务逻辑
 */
public class ConfigDAO extends BaseDAO {
	/** 日志工具 */
	protected Logger logger = Logger.getLogger(ConfigDAO.class);

    public ConfigDAO() {
        this.dbConnectConfig = DBConfig.adminDB.getName();
    }

    /**
	 * 得到表名
	 * @return 表名
	 */
	protected String getTableName() {
	    return "config";
	}


	/**
	 * 得到查询一条记录的SQL
	 * @return 查询一条记录的SQL
	 */
	protected String getSqlForGet() {
		return "select * from config where id = ?";
	}


	/**
	 * 得到查询多条记录的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForGetList() {
		return "SELECT * FROM config";
	}


	/**
	 * 得到查询记录总数的SQL
	 * @return 查询多条记录的SQL
	 */
	protected String getSqlForCount() {
		return "SELECT count(*) FROM config";
	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete(String keys) {
		return "DELETE FROM config WHERE id in (" + keys + ")";
	}


	/**
	 * 将resultSet中的值设置到dto中
	 * @param res
	 * @return dto
	 * @throws java.sql.SQLException
	 */
	protected BaseDTO resultSetToDto(ResultSet res) throws SQLException {
		Config dto = new Config();

		dto.setId(res.getInt("id"));
		dto.setModule(res.getString("module"));
		dto.setName(res.getString("name"));
		dto.setValue(res.getString("value"));
		dto.setDescription(res.getString("description"));


		return dto;
	}


	/**
	 * 得到insert语句的SQL
	 * @return insert语句的SQL
	 */
	protected String getSqlForInsert() {
		return "INSERT INTO config (id, module, name, value, description)VALUES(?, ?, ?, ?, ?)";
	}


	/**
	 * 为insert设置参数
	 * @param vector
	 * @param BaseDTO
	 */
	protected void setParametersForInsert(List<Object> vector, BaseDTO BaseDTO) {
		Config dto = (Config) BaseDTO;

		vector.add(dto.getId());
		vector.add(dto.getModule());
		vector.add(dto.getName());
		vector.add(dto.getValue());
		vector.add(dto.getDescription());

	}


	/**
	 * 得到update语句的SQL
	 * @return update语句的SQL
	 */
	protected String getSqlForUpdate() {
		return "UPDATE config SET module = ?, name = ?, value = ?, description = ? WHERE id = ?";
	}


	/**
	 * 为update设置参数
	 * @param vector
	 * @param BaseDTO
	 */
	protected void setParametersForUpdate(List<Object> vector, BaseDTO BaseDTO) {
		Config dto = (Config) BaseDTO;

		vector.add(dto.getModule());
		vector.add(dto.getName());
		vector.add(dto.getValue());
		vector.add(dto.getDescription());

		vector.add(dto.getId());

	}


	/**
	 * 得到delete语句的SQL
	 * @return delete语句的SQL
	 */
	protected String getSqlForDelete() {
		return "DELETE FROM config WHERE id = ?";
	}


	/**
	 * 由id得到config
	 * @param id id
	 */
	public Config get(int id) throws SQLException {
        Config config;
        Vector<Object> vector = new Vector<Object>();
        vector.add(id);
        config = (Config) getBySql("select * from config where id = ?", vector);

        return config;
	}


    /**
     * 修改一条记录
     */
    public int updateValueByModuleAndName(String module, String name, String value) throws SQLException {
        return executeUpdate(
                "update config set value = ? where module = ? and name = ?",
                value, module, name
        );
    }



    /**
     * 修改一条记录
     */
    public int updateValueByName(String name, String value) throws SQLException {
        return executeUpdate(
                "update config set value = ? where name = ?",
                value, name
        );
    }




    /**
     * 根据classification得到映射表（name->value）
     * 如果有异常，返回null
     */
    public List<Config> getListByModule(String module) throws SQLException {
        return getListByCondition("module=?", module);
    }


    /**
     */
    public String getValue(String module, String name) throws SQLException {
        return getString("SELECT value FROM config WHERE module=? AND name=?", module, name);
    }


    /**
     */
    public String getValue(String name) throws SQLException {
        return getString("SELECT value FROM config WHERE name=?", name);
    }



}
