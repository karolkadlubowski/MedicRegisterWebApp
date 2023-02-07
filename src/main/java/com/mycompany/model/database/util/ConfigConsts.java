package com.mycompany.model.database.util;

/**
 * Class responsible for connection with database
 *
 * @author Karol Kadlubowski
 * @version 1.0
 */
public final class ConfigConsts {

    private ConfigConsts() {
    }
    /**
     * Name of serwer
     */
    public final static String SERVER_NAME = "localhost";
    /**
     * Serwer port
     */
    public final static int SERVER_PORT = 3306;
    /**
     * Username
     */
    public final static String USERNAME = "root";
    /**
     * Password of user
     */
    public final static String PASSWORD = "";
    /**
     * Database name
     */
    public final static String DATABASE_NAME = "clinic";
    /**
     * URL connection to database
     */
    public final static String JDBC_URL
            = "jdbc:mysql://"
            + SERVER_NAME
            + ":" + SERVER_PORT
            + "/" + DATABASE_NAME;

}
