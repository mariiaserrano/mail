package Filtros;



import Model.Usuario;
import Model.UsuarioLogin;
import com.google.gson.Gson;
import io.vavr.control.Try;
import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Log4j2
@WebFilter(filterName = "FilterJson", urlPatterns = {"/login", "/add"})
public class FilterJson implements Filter {


    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        AtomicBoolean seguir = new AtomicBoolean(true);
        Gson gson = new Gson();

        //Para trasformar en usuarioLogin lo que viene del servlet
        Optional.ofNullable(req.getParameter("usuarioLogin"))
                .ifPresent(s -> {
                            Try.of(() -> gson.fromJson(s, UsuarioLogin.class))
                                    .onSuccess(usuarioLogin ->
                                            req.setAttribute("usuarioLogin", usuarioLogin))
                                    .onFailure(throwable -> {
                                        log.error(throwable.getMessage(), throwable);
                                        ((HttpServletResponse) resp).setStatus(HttpServletResponse.SC_BAD_REQUEST);
                                        seguir.set(false);
                                    });
                        }
                );

        //Para transformar un usuario
        Optional.ofNullable(req.getParameter("usuario"))
                .ifPresent(s -> {
                            Try.of(() -> gson.fromJson(s, Usuario.class))
                                    .onSuccess(usuario ->
                                            req.setAttribute("usuario", usuario))
                                    .onFailure(throwable -> {
                                        log.error(throwable.getMessage(), throwable);
                                        ((HttpServletResponse) resp).setStatus(HttpServletResponse.SC_BAD_REQUEST);
                                        seguir.set(false);
                                    });
                        }
                );

        if (seguir.get()) {
            chain.doFilter(req, resp);
            AtomicReference<String> respuesta = new AtomicReference<>();
            Optional.ofNullable(
                    req.getAttribute("respuesta"))
                    .ifPresentOrElse(
                            o -> respuesta.set(gson.toJson(o))
                            , () -> {
                                respuesta.set(
                                        Optional.ofNullable(
                                                req.getAttribute("error"))
                                                .map(o -> (String) o)
                                                .orElseGet(() -> null));
                                ((HttpServletResponse) resp).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                            });
            resp.getWriter().print(respuesta.get());
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }
}
