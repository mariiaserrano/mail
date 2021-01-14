package Servlet;

import Model.Usuario;
import Model.UsuarioLogin;
import Servicios.ServiciosUsuarios;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ServletLogin", urlPatterns = {"/login"})
public class ServletLogin extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiciosUsuarios sv_usuarios = new ServiciosUsuarios();
        UsuarioLogin usuarioLogin = (UsuarioLogin) request.getAttribute("usuarioLogin");
        sv_usuarios.getUsuarioLogin(usuarioLogin)
                .peek(usuario -> {
                    request.setAttribute("respuesta", usuario);
                    request.getSession().setAttribute("usuarioLogin", usuario);
                    response.setStatus(HttpServletResponse.SC_OK);
                })
                .peekLeft(s -> {
                    request.setAttribute("error", s);
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                });

    }
}
