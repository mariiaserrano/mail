package Servlet;

import Servicios.MandarMail;
import utils.codigoRandom;

import javax.mail.MessagingException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet(name = "ServletRegistro", urlPatterns = {"/registro"})
public class ServletRegistro extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        MandarMail mandarMail = new MandarMail();
        String to = request.getParameter("to");
        String msg = request.getParameter("msg");
        String subject = request.getParameter("subject");

        try {
            mandarMail.generateAndSendEmail(to, msg, subject);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
