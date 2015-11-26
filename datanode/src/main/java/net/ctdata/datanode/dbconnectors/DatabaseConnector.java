package net.ctdata.datanode.dbconnectors;

import java.sql.SQLException;

/**
 * Created by aditi on 14/11/15.
 * This is an interface between the data node and the database
 * Purpose: To insert, select, update and delete data
 */
public interface DatabaseConnector {

    int establishConnection() throws SQLException;

    Object executeQuery(String query, char flag) throws SQLException;

    void closeConnection() throws SQLException;
}
