package com.nearbysocialevents.nearby975.MySql;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by BalaPC on 06-Jun-16.
 */
public interface MySqlRunnable {
    public void run(ResultSet result) throws SQLException;
}
