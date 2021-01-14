package Controllers;

import Model.Usuario;
import Servicios.ServiciosUsers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class RegistroController implements Initializable {

    private Alert alert;
    private PrincipalController principalController;
   // @FXML
    //private TextField textUsuario;
    //@FXML
    //private PasswordField fxPassRegistro;
    //@FXML
    //private TextField fxCorreo;
    @FXML
    private Button fxBotonRegistro;


    public void setPrincipalController(PrincipalController principalController) {
        this.principalController = principalController;
    }


    @FXML
    private void clickAddUser(ActionEvent actionEvent) {
        Usuario newUser = new Usuario("Maria","1234","correo@correo.es"
        ,"codigo",0);
        String add = addUser(newUser);
        alert.setContentText(add);
        alert.showAndWait();
    }
    private String addUser(Usuario usuario) {
        ServiciosUsers svUsers = new ServiciosUsers();
        AtomicReference<String> result = new AtomicReference<>();
        svUsers.addUsuario(usuario)
                .peek(usuario1 -> {
                    result.set("añandido correctamente");
                })
                .peekLeft(s -> {
                    result.set("que no va "+s );
                });
        return result.get();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alert = new Alert(Alert.AlertType.INFORMATION);
    }
}
