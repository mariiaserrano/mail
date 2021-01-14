package Dao;

import Model.Mail;
import Model.Usuario;
import Model.UsuarioLogin;
import com.google.gson.Gson;
import config.ConfigurationSingleton;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.log4j.Log4j2;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.net.ConnectException;
import java.util.concurrent.atomic.AtomicReference;

@Log4j2
public class DaoMails {
    private Gson gson = new Gson();

    public Either<String, Mail> getMail(Mail mail) {
            AtomicReference<Either<String, Mail>> resultado = new AtomicReference<>();
            OkHttpClient clientOK = ConfigurationSingleton_OkHttpClient.getInstance();
            String url = ConfigurationSingleton.getInstance().getRuta() + "registro";
            HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
            urlBuilder
                    .addQueryParameter("mail", gson.toJson(mail));
            String urlConParams = urlBuilder.build().toString();
            Request request = new Request.Builder()
                    .url(urlConParams)
                    .build();
            Try.of(() -> clientOK.newCall(request).execute())
                    .onSuccess(response -> {
                        if (response.isSuccessful()) {
                            Try.of(() -> response.body().string())
                                    .onSuccess(mails ->
                                            Try.of(() -> gson.fromJson(mails, Mail.class))
                                                    .onSuccess(o -> resultado.set(Either.right(o)))
                                                    .onFailure(throwable -> resultado.set(Either.left("Problemas de parseo"))))
                                    .onFailure(throwable -> resultado.set(Either.left("Error de comunicacion")));
                        } else {
                            Try.of(() -> response.body().string())
                                    .onSuccess(s ->
                                            resultado.set(Either.left(s)))
                                    .onFailure(throwable -> resultado.set(Either.left("Error de comunicacion")));
                        }
                    })
                    .onFailure(ConnectException.class, throwable -> {
                        log.error(throwable.getMessage(), throwable);
                        resultado.set(Either.left("Error de conexion"));
                    });
            return resultado.get();
        }
}
