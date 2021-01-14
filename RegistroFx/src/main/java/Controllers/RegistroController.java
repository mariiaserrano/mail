package Controllers;

import Model.Usuario;
import Servicios.ServiciosUsers;
import Utils.Constantes;
import Utils.HashPassword;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class RegistroController implements Initializable {

    private Alert alert;
    private PrincipalController principalController;
    @FXML
    private TextField fxUsuario;
    @FXML
    private PasswordField fxPassRegistro;
    @FXML
    private TextField fxCorreo;


    public void setPrincipalController(PrincipalController principalController) {
        this.principalController = principalController;
    }


    @FXML
    private void clickAddUser(ActionEvent actionEvent) {
        HashPassword hs = new HashPassword();
        Usuario newUser = new Usuario(fxUsuario.getText(),hs.hashPassword(fxPassRegistro.getText()),
                fxCorreo.getText(),"codigo2",1);
        String add = addUser(newUser);
        limpiarCajas();
        alert.setContentText(add);
        alert.showAndWait();
    }
    private String addUser(Usuario usuario) {
        ServiciosUsers svUsers = new ServiciosUsers();
        AtomicReference<String> result = new AtomicReference<>();
        svUsers.addUsuario(usuario)
                .peek(usuario1 -> {
                    result.set(Constantes.USUARIO_AÑADIDO_CORRECTAMENTE);
                })
                .peekLeft(s -> {
                    result.set(s );
                });
        return result.get();
    }

    public void limpiarCajas (){
        fxUsuario.clear();
        fxPassRegistro.clear();
        fxCorreo.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alert = new Alert(Alert.AlertType.INFORMATION);
    }
}
