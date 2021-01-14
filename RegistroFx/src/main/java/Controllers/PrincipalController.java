package Controllers;

import Model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.net.URL;
import java.util.ResourceBundle;

public class PrincipalController implements Initializable {
    private Alert alert;

    private AnchorPane pantalla_login;
    private LoginController controller_login;

    private AnchorPane pantalla_registro;
    private RegistroController controller_registro;

    private AnchorPane pantalla_bienvenida;
    private BienvenidaController controller_bienvenida;

    @Getter
    @Setter
    private Usuario usuarioSession;

    @FXML
    private BorderPane pantallaPrincipal;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alert = new Alert(Alert.AlertType.INFORMATION);
        cargarLogin();
    }

    @SneakyThrows
    public void cargarLogin() {
        if (pantalla_login == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/loginUser.fxml"));
            pantalla_login = fxmlLoader.load();
            controller_login = fxmlLoader.getController();
            controller_login.setPrincipalController(this);
        }
        pantallaPrincipal.setCenter(pantalla_login);
    }
@SneakyThrows
    public void cargarRegistro() {
        if (pantalla_registro == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/registro.fxml"));
            pantalla_registro = fxmlLoader.load();
            controller_registro = fxmlLoader.getController();
            controller_registro.setPrincipalController(this);
        }
        pantallaPrincipal.setCenter(pantalla_registro);
    }

    @SneakyThrows
    public void cargarBienvenida() {
        if (pantalla_bienvenida == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/pantallaBienvenida.fxml"));
            pantalla_bienvenida = fxmlLoader.load();
            controller_bienvenida = fxmlLoader.getController();
            controller_bienvenida.setPrincipalController(this);
        }
        pantallaPrincipal.setCenter(pantalla_bienvenida);
    }
}

