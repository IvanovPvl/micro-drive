package io.microdrive.edge.accounts;

import io.microdrive.core.types.accounts.CheckPasswordRequest;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

@Configuration
@EnableConfigurationProperties(AccountsConfigurationProperties.class)
public class AccountsConfiguration {
    @Bean
    public RouteLocator accountsProxyRouting(
        RouteLocatorBuilder builder,
        AccountsConfigurationProperties configuration
    ) {
        return builder.routes()
            .route(r -> r.path("/api/v1/accounts").and().method("POST").uri(configuration.getServiceUrl()))
            .build();
    }

    @Bean
    public RouterFunction<ServerResponse> checkPassword(AccountsProxy accountsProxy, TokenService tokenService) {
        // TODO: handle errors from accounts service, return 401 if error occurs
        return RouterFunctions.route(POST("/api/v1/accounts/token"), request ->
            request.bodyToMono(CheckPasswordRequest.class)
                .flatMap(accountsProxy::checkPassword)
                .flatMap(response -> {
                    var token = tokenService.create(response);
                    return status(HttpStatus.OK).bodyValue(token);
                }));
    }
}
