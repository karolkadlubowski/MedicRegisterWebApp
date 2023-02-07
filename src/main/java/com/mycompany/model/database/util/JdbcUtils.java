package com.mycompany.model.database.util;

import com.mycompany.model.exceptions.DatabaseException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * Class responsible for constructing the database
 *
 * @author Karol Kadlubowski
 * @version 1.0
 */

public class JdbcUtils {

    private static final DataSource dataSource=null;


    static {
        /*final MysqlDataSource mysqlds = new MysqlDataSource();
        mysqlds.setServerName(ConfigConsts.SERVER_NAME);
        mysqlds.setPort(ConfigConsts.SERVER_PORT);
        mysqlds.setDatabaseName(ConfigConsts.DATABASE_NAME);
        mysqlds.setUser(ConfigConsts.USERNAME);
        mysqlds.setPassword(ConfigConsts.PASSWORD);
        dataSource = mysqlds;*/
    }

    /**
     * Method responsible for getting connection to the database
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {


        //final Connection connection = dataSource.getConnection();
        final Connection connection = DriverManager.getConnection(ConfigConsts.JDBC_URL,ConfigConsts.USERNAME,ConfigConsts.PASSWORD);
        connection.setAutoCommit(false);
        return connection;
    }

    /**
     * Method responsible to recreate database if there is not any
     * @param schemaDef
     */
    public static void restoreDbSchema(DbSchemaDef schemaDef) throws DatabaseException {
        Connection con = null;
        Statement stmt = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            String sql = "CREATE DATABASE IF NOT EXISTS CLINIC";
            stmt.executeUpdate(sql);
            //System.out.println("Database created successfully...");


/*
            for (String tableName : schemaDef.getTablesNames()) {
                stmt.executeUpdate("DROP TABLE IF EXISTS " + tableName);
            }
*/

            for (String tableDef : schemaDef.getTablesDef()) {
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableDef);
            }


        } catch (SQLException ex) {
            handleSqlException(ex);
            throw new DatabaseException(ex.getMessage());
        } finally {
           closeSilently(stmt, con);
        }
    }

    /**
     * Method to shut down the connection after executing query
     * @param stmt
     * @param con
     */
    public static void closeSilently(Statement stmt, Connection con) throws DatabaseException {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            handleSqlException(ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    public static void handleSqlException(SQLException ex) {
        ex.printStackTrace();
    }

    /**
     * Method deletes all tables in database
     * @param schemaDef
     * @throws SQLException
     */

    public static void dropTables(DbSchemaDef schemaDef) throws SQLException, DatabaseException {
        Connection con = null;
        Statement stmt = null;
        try {
            con = getConnection();
            stmt = con.createStatement();

            for (String tableName : schemaDef.getTablesNames()) {
                stmt.executeUpdate("DROP TABLE IF EXISTS " + tableName);
            }

        } catch (SQLException ex) {
            handleSqlException(ex);
            throw new DatabaseException(ex.getMessage());
        } finally {
            closeSilently(stmt, con);
        }
    }

}
