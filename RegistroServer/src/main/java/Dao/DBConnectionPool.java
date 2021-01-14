/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.log4j.Log4j2;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Log4j2
public class DBConnectionPool {

    private static DBConnectionPool dbconection = null;

    private HikariDataSource hirakiDatasource = null;

    private DBConnectionPool() {
        hirakiDatasource = getDataSourceHikari();
    }

    public static DBConnectionPool getInstance() {
        if (dbconection == null) {
            dbconection = new DBConnectionPool();
        }

        return dbconection;
    }

    public Connection getConnection() throws Exception {
        Connection connection;
        connection = hirakiDatasource.getConnection();
        return connection;
    }

    private HikariDataSource getDataSourceHikari() {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(ConfigurationSingleton.getInstance().getUrlDB());
        config.setUsername(ConfigurationSingleton.getInstance().getUserDB());
        config.setPassword(ConfigurationSingleton.getInstance().getPassDB());
        config.setMaximumPoolSize(2);

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        HikariDataSource datasource = new HikariDataSource(config);

        return datasource;
    }

    public DataSource getDataSource() {
        return hirakiDatasource;
    }

    public void cerrarConexion(Connection connection) {
        try {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnectionPool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cerrarPool() {
       hirakiDatasource.close();
    }
}
