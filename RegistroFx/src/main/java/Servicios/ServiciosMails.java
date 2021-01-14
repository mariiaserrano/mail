package Servicios;

import Dao.DaoMails;
import Model.Mail;
import io.vavr.control.Either;

public class ServiciosMails {
DaoMails dao = new DaoMails();

    public Either<String, Mail> getMail(Mail mail) {
        return dao.getMail(mail);
    }
}
