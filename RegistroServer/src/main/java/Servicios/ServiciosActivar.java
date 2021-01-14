package Servicios;

import Dao.DaoActivar;

public class ServiciosActivar {
    DaoActivar dao = new DaoActivar();

    public String getArticuloXIDFromJDBC(String codigo) {
        return dao.getArticuloXIDFromJDBC(codigo);
    }
}
