package com.microservice.gateway.microservice_gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator { // Clase para implementacion de rutas posteriormente a validar...

    public static final List<String> openApiEndpoints = List.of(
            "/users/register",
            "/users/token",
            "/users/validate",
            "/eureka"
    );

    public Predicate<ServerHttpRequest> isSecured =  // Este predicado verifica si la URI de la solicitud no coincide con ninguno de los puntos finales "abiertos" o públicos. Si la URI de la solicitud no contiene ningún URI en openApiEndpoints, entonces se considera que la solicitud está "asegurada" y isSecured devolverá true. Si contiene uno de esos URIs, devolverá false, lo que significa que no es una solicitud asegurada.
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
