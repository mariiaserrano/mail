package Dao;

import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

@Log4j2
public class DaoActivar {

    public String getArticuloXIDFromJDBC(String codigo) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String resultado="";
        DBConnection db = new DBConnection();
        var rows = 0;
        try {
            con = db.getConnection();
            preparedStatement = con.prepareStatement
                    ("update usuarios2 set activado = ? where codigo = ?",
                            Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, codigo);
            ;
            rows = preparedStatement.executeUpdate();

            if (rows == 1) {
                resultado = "bien";
            } else {
                resultado = "mal";

            }
        } catch (Exception e) {
            resultado = "Error al activar periodico";
        } finally {
            db.cerrarResultSet(resultSet);
            db.cerrarStatement(preparedStatement);
            db.cerrarConexion(con);
        }
        return resultado;
    }
}
