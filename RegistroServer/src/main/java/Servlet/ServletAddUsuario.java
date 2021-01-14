package Servlet;

import Model.Usuario;
import Servicios.ServiciosUsuarios;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ServletUsuario",urlPatterns = {"/add"})
public class ServletAddUsuario extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        addUser(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiciosUsuarios servUser = new ServiciosUsuarios();
        Usuario user = (Usuario) request.getAttribute("usuario");
        servUser.addUser(user)
                .peek(usuario1 -> {
                    request.setAttribute("respuesta", usuario1);
                    response.setStatus(HttpServletResponse.SC_OK);
                })
                .peekLeft(s -> {
                    request.setAttribute("error", s);
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                });
    }
}
