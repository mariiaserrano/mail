package Dao;

import lombok.extern.log4j.Log4j2;
import Dao.ConfigurationSingleton;

import javax.sql.DataSource;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


@Log4j2
public class DBConnection {


    public Connection getConnection() throws Exception {
        Connection connection = null;

        connection = DriverManager.getConnection(
                ConfigurationSingleton.getInstance().getUrlDB(),
                ConfigurationSingleton.getInstance().getUserDB(),
                ConfigurationSingleton.getInstance().getPassDB());
        return connection;
    }

    public DataSource getDataSource() {
        return DBConnectionPool.getInstance().getDataSource();
    }

    public void cerrarPool() {
        DBConnectionPool.getInstance().cerrarPool();
    }

    public void cerrarConexion(Connection connection) {
        try {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        } catch (SQLException ex) {
            log.error("No se ha podido cerrar Conexion", ex);
        }
    }

    public void cerrarStatement(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException ex) {
            log.error("No se ha podido cerrar el Statement", ex);
        }
    }

    public void cerrarResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, "No se ha podido cerrar el ResultSet", ex);
        }
    }

    public void rollbackCon(Connection con) {
        try {
            if (con != null)
                con.rollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
