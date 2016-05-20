package org.ailab.wimfra.core;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: Lu Tingming
 * Date: 2012-1-16
 * Time: 16:19:18
 * Desc:
 */
public abstract class ResultSetToDtoAdapter {
    protected abstract BaseDTO resultSetToDto(ResultSet res) throws SQLException;
}
