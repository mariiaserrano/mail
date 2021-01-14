package Controllers;

import Model.Usuario;
import Model.UsuarioLogin;
import Servicios.ServiciosUsers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import lombok.SneakyThrows;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private PrincipalController principalController;
    private Alert alert;
    private Usuario usuarioLogin;

    @FXML
    private TextField fxUser;
    @FXML
    private PasswordField fxPass;
    @FXML
    private Button fxBotonLogin;
    @FXML
    private Hyperlink fxBotonRegistro;

    public void setPrincipalController(PrincipalController principalController) {
        this.principalController = principalController;
    }

    public void iniciarRegistro(){
        principalController.cargarRegistro();
    }

    @FXML
    private void iniciarSesion(ActionEvent actionEvent) {
        ServiciosUsers svusers = new ServiciosUsers();
        if (!fxUser.getText().isEmpty() && !fxPass.getText().isEmpty()) {
            UsuarioLogin login = new UsuarioLogin(fxUser.getText(), fxPass.getText());
            svusers.getUsuarioLogin(login)
                    .peek(usuario -> {
                        usuarioLogin = usuario;
                        principalController.setUsuarioSession(usuarioLogin);
                        principalController.cargarBienvenida();
                    })
                    .peekLeft(s -> {
                        alert.setContentText(s);
                        alert.showAndWait();
                    });
        } else {
            alert.setContentText("rellenar todos los campos");
            alert.showAndWait();
        }


    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alert = new Alert(Alert.AlertType.INFORMATION);
    }
}
