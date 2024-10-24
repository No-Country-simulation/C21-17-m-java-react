package com.microservice.gateway.microservice_gateway.filter;

import com.microservice.gateway.microservice_gateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component // Aplicamos el filtro en Gateway para saber que microservicio levantar ante una peticion
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator routeValidator;

//    @Autowired
//    private RestTemplate template;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> { // Debemos indicar a cual ENDPOINT indica la validacion
           if (routeValidator.isSecured.test(exchange.getRequest())){
               // token encabezado
               if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){ // En caso de que la peticion no contenga en el header la autorizacion
                    throw new RuntimeException("No se encuentra la autorizacion en el encabezado");
               }

               String autheaders = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
               if (autheaders != null && autheaders.startsWith("Bearer ")){
                   autheaders = autheaders.substring(7); // autheaders contiene el token
               }

               try{
//                    template.getForObject("http://MICROSERVICE-USER/validate?token" + autheaders, String.class);
                   jwtUtil.validateToken(autheaders);
               } catch (Exception e){
                   System.out.println("invalid access..");
                   throw new RuntimeException("acceso no autorizado");
               }
           }
            return chain.filter(exchange);
        }));
    }

    public static class Config{

    }
}
