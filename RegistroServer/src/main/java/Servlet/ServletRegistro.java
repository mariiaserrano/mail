package Servlet;

import Model.Mail;
import Servicios.MandarMail;
import lombok.SneakyThrows;

import javax.mail.MessagingException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ServletRegistro", urlPatterns = {"/registro"})
public class ServletRegistro extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
  /* MandarMail mandarMail = new MandarMail();
        Mail mail = new Mail();

            mandarMail.generateAndSendEmail(mail)
                    .peek(mails -> {
                        request.setAttribute("respuesta", mail);
                        response.setStatus(HttpServletResponse.SC_OK);

                    })
                    .peekLeft(s -> {
                        request.setAttribute("error", s);
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    });
*/
    }
}
