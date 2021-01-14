package Dao;

import Model.Usuario;
import Model.UsuarioLogin;
import Utils.Producers;
import com.google.gson.Gson;
import config.ConfigurationSingleton;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.log4j.Log4j2;
import okhttp3.*;

import javax.inject.Inject;
import java.net.ConnectException;
import java.util.concurrent.atomic.AtomicReference;
@Log4j2
public class DaoUsers {
    @Inject
    private Gson gson = new Gson();

    public Either<String, Usuario> addUsuario(Usuario usuario) {
        AtomicReference<Either<String, Usuario>> resultado = new AtomicReference<>();
        OkHttpClient clientOK = ConfigurationSingleton_OkHttpClient.getInstance();
        String url = ConfigurationSingleton.getInstance().getRuta() + "add";
        FormBody.Builder b = new FormBody.Builder();
        b.add("usuario", gson.toJson(usuario));
        RequestBody formBody = b.build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Try.of(() -> clientOK.newCall(request).execute())
                .onSuccess(response -> {
                    if (response.isSuccessful()) {
                        Try.of(() -> response.body().string())
                                .onSuccess(tipo ->
                                        Try.of(() -> gson.fromJson(tipo, Usuario.class))
                                                .onSuccess(o -> resultado.set(Either.right(o)))
                                                .onFailure(throwable -> resultado.set(Either.left("Problemas con el parseo"))))
                                .onFailure(throwable -> resultado.set(Either.left("error de comunicacion")));
                    } else {
                        Try.of(() -> response.body().string())
                                .onSuccess(s ->
                                        resultado.set(Either.left(s)))
                                .onFailure(throwable -> resultado.set(Either.left("error de comunicacion")));
                    }
                })
                .onFailure(ConnectException.class, throwable -> {
                    log.error(throwable.getMessage(), throwable);
                    resultado.set(Either.left("Error de conexion"));
                });
        return resultado.get();
    }


    public Either<String, Usuario> getUsuarioLogin(UsuarioLogin login) {
        AtomicReference<Either<String, Usuario>> resultado = new AtomicReference<>();
        OkHttpClient clientOK = ConfigurationSingleton_OkHttpClient.getInstance();
        String url = "";
        url = ConfigurationSingleton.getInstance().getRuta() + "login";
        System.out.println(url);

        //Como vamos a extraer un dato hacemos una llamada por GET

        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        urlBuilder
                .addQueryParameter("usuarioLogin", gson.toJson(login));
        String urlConParams = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(urlConParams)
                .build();

        //Hacemos los distintos Try para parsear el objeto y obtener sino mensajes error
        Try.of(() -> clientOK.newCall(request).execute())
                .onSuccess(response -> {
                    if (response.isSuccessful()) {
                        Try.of(() -> response.body().string())
                                .onSuccess(uLogin ->
                                        Try.of(() -> gson.fromJson(uLogin, Usuario.class))
                                                .onSuccess(o -> resultado.set(Either.right(o)))
                                                .onFailure(throwable -> resultado.set(Either.left("problemas de parseo"))))
                                .onFailure(throwable -> resultado.set(Either.left("error de comunicacion")));
                    } else {
                        Try.of(() -> response.body().string())
                                .onSuccess(s ->
                                        resultado.set(Either.left(s)))
                                .onFailure(throwable -> resultado.set(Either.left("error de comunicacione")));
                    }
                })
                .onFailure(ConnectException.class, throwable -> {
                    log.error(throwable.getMessage(), throwable);
                    resultado.set(Either.left("error conexion"));
                });

        return resultado.get();
    }
}
