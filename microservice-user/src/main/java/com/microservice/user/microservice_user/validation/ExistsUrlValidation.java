package com.microservice.user.microservice_user.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ExistsUrlValidation implements ConstraintValidator<ExistsUrl,String> {

    @Override
    public boolean isValid(String url, ConstraintValidatorContext context) {
        if (url == null || url.isEmpty()) { // para evitar en NullPointer, la url puede ser null, es opcional tener imagen para el usuario
            return true;
        }

        HttpURLConnection connection = null;
        try {
            URL urlSearch = new URL(url);
            connection = (HttpURLConnection) urlSearch.openConnection();
            connection.setRequestMethod("HEAD"); // Solo verificar el encabezado, no descargar el contenido
            connection.connect();
            int responseCode = connection.getResponseCode();
            return (responseCode >= 200 && responseCode < 400); // Se considera válido si la respuesta es 2xx o 3xx
        } catch (MalformedURLException e) {
            System.err.println("URL no válida: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error al conectar: " + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect(); // cerramos la conexion
            }
        }
        return false;
    }
}
