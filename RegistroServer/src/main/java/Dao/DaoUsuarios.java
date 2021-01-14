package Dao;

import Model.Usuario;
import Model.UsuarioLogin;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.ejb.DuplicateKeyException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
@Log4j2
public class DaoUsuarios {
    private static final String QUERY_INSERT_USER = "insert into usuarios2 (nombre, password, correo, codigo, activado) values (?,?,?,?,?)";
    private static final String QUERY_SELECT_USUARIO_LOGIN = "select * from usuarios2 where nombre=? and password=?";


    public Either<String, Usuario> addUsuario(Usuario usuario) {
        AtomicReference<Either<String, Usuario>> result = new AtomicReference<>();
        DBConnection dbConnection = new DBConnection();
        ResultSet resultSet = null;
        Connection con = null;
        PreparedStatement stmt = null;
        boolean anadido = false;

        try {
            con = dbConnection.getConnection();

            stmt = con.prepareStatement(QUERY_INSERT_USER);
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getPass());
            stmt.setString(3, usuario.getCorreo());
            stmt.setString(4, usuario.getCodigo());
            stmt.setInt(5,usuario.getActivado());

            int filasAnadidas = -1;
            filasAnadidas = stmt.executeUpdate();


            if (filasAnadidas > 0) {
                anadido = true;
                result.set(Either.right(usuario));
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DaoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DaoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            dbConnection.cerrarConexion(con);
            dbConnection.cerrarResultSet(resultSet);
            dbConnection.cerrarStatement(stmt);

        }

        return result.get();

    }



    public Either<String, Usuario> getUsuarioLogin(UsuarioLogin login) {
        AtomicReference<Either<String, Usuario>> result = new AtomicReference<>();
        DBConnection dbConnection = new DBConnection();
        Usuario user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean active = false;

        try {
            connection = dbConnection.getConnection();
            preparedStatement = connection.prepareStatement(QUERY_SELECT_USUARIO_LOGIN);
            preparedStatement.setString(1, login.getNombre());
            preparedStatement.setString(2, login.getPassword());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new Usuario(
                        resultSet.getString("nombre"),
                        resultSet.getString("password"),
                        resultSet.getString("correo"),
                        resultSet.getString("codigo"),
                        resultSet.getInt("activado")
                );
            }

            if (user != null) {
                if(user.getActivado()==1) {
                    result.set(Either.right(user));
                }
                else {
                    result.set(Either.left("error usuario no activado"));
                }
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            dbConnection.cerrarConexion(connection);
            dbConnection.cerrarResultSet(resultSet);
            dbConnection.cerrarStatement(preparedStatement);
        }
        return result.get();


    }
}
