package Filtros;

import Model.Usuario;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "FilterLogin", urlPatterns = {""})
public class FilterLogin implements Filter {
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        Usuario usuarioLogin = (Usuario) ((HttpServletRequest) req).getSession().getAttribute("usuarioLogin");
        if (usuarioLogin != null) {
            chain.doFilter(req, resp);
        } else {
            req.setAttribute("error", "No se ha iniciado sesión");
            ((HttpServletResponse) resp).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }


}
