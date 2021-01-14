package Servicios;

import Dao.DaoUsers;
import Model.Usuario;
import Model.UsuarioLogin;
import Utils.Producers;
import io.vavr.control.Either;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;
import javax.validation.metadata.BeanDescriptor;
import java.util.Set;
import java.util.stream.Collectors;

public class ServiciosUsers {
    Producers prod = new Producers();
    @Inject
    private Validator validator =  prod.createValidator();
    @Inject
    private DaoUsers daoUsers = new DaoUsers();

    public Either<String, Usuario> addUsuario(Usuario usuario) {
        Either<String, Usuario> result;
        String error = validator.validate(usuario).stream()
                .map(ConstraintViolation::getMessage).collect(Collectors.joining("\n"));
        if (error.isEmpty()) {
            result = daoUsers.addUsuario(usuario);
        } else {
            result = Either.left(error);
        }
        return result;
    }

    public Either<String, Usuario> getUsuarioLogin(UsuarioLogin login) {
        return daoUsers.getUsuarioLogin(login);
    }
}
