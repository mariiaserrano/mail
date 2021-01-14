package Servicios;

import Dao.DaoUsuarios;
import Model.Usuario;
import Model.UsuarioLogin;
import io.vavr.control.Either;
import utils.HashPassword;

public class ServiciosUsuarios {


    public Either<String, Usuario> addUser(Usuario usuario) {
        DaoUsuarios dao = new DaoUsuarios();
        return dao.addUsuario(usuario);
    }

    public Either<String, Usuario> getUsuarioLogin(UsuarioLogin login) {
        HashPassword hashP = new HashPassword();
        DaoUsuarios dao = new DaoUsuarios();
        String password = hashP.hashPassword(login.getPassword());
        login.setPassword(password);
        return dao.getUsuarioLogin(login);
    }
}
