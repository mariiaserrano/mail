package Servicios;

import Dao.DaoMails;
import Model.Mail;
import io.vavr.control.Either;

public class ServiciosMails {
DaoMails dao = new DaoMails();

    public String getMail(String mensaje, String asunto, String destinatario) {
        return dao.getMail(mensaje, asunto, destinatario);
    }
}
