package Controllers;

public class BienvenidaController {
    private PrincipalController principalController;

    public void setPrincipalController(PrincipalController principalController) {
        this.principalController = principalController;
    }

    public void cerrarSesion(){
        principalController.cargarLogin();
    }
}
