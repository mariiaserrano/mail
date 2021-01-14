package Servlet;

import Servicios.ServiciosActivar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ServletActivar", urlPatterns = {"/activar"})
public class ServletActivar extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       String codigo =  request.getParameter("codigo");
        ServiciosActivar sv = new ServiciosActivar();
        sv.getArticuloXIDFromJDBC(codigo);

        response.getWriter().print("Usuario activado");
    }
}
