package com.acguglielmo.accesslogmonitor.gateway.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.acguglielmo.accesslogmonitor.util.PropertiesHolder;

public class ConnectionFactory {

    private String dbConnectionUrl;
    private String dbUser;
    private String dbPassword;

    private volatile static ConnectionFactory instance;

    private ConnectionFactory() {
        final PropertiesHolder propertiesHolder = PropertiesHolder.getInstance();
        dbConnectionUrl = propertiesHolder.getProperty(PropertiesHolder.DB_CONNECTION_URL);
        dbUser = propertiesHolder.getProperty(PropertiesHolder.DB_CONNECTION_USERNAME);
        dbPassword = propertiesHolder.getProperty(PropertiesHolder.DB_CONNECTION_PASSWORD);
    }

    public static ConnectionFactory getInstance() {
        if (instance == null) {
            synchronized (ConnectionFactory.class) {
                if (instance == null)
                    instance = new ConnectionFactory();
            }
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                dbConnectionUrl.contains("mysql") ? dbConnectionUrl.concat("?useTimezone=true&serverTimezone=UTC&useSSL=false")
                : dbConnectionUrl, dbUser, dbPassword);
    }
}
