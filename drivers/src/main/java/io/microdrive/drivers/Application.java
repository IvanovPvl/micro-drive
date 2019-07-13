package io.microdrive.drivers;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveSetOperations;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@EnableDiscoveryClient
@SpringBootApplication
@RequiredArgsConstructor
public class Application {

    private final ApiHandler apiHandler;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    ReactiveSetOperations<String, String> reactiveSetOperations(ReactiveRedisConnectionFactory cf) {
        RedisSerializationContext<String, String> context = RedisSerializationContext
                .<String, String>newSerializationContext(new StringRedisSerializer())
                .key(new StringRedisSerializer())
                .value(new StringRedisSerializer())
                .hashKey(new StringRedisSerializer())
                .hashKey(new StringRedisSerializer())
                .build();

        return new ReactiveRedisTemplate<>(cf, context).opsForSet();
    }

    @Bean
    RouterFunction<ServerResponse> routes() {
        return route()
                .POST("/request", apiHandler::request)
                .POST("/release", accept(APPLICATION_JSON), apiHandler::release)
                .build();
    }

    @Bean
    public WebClient accountsWebClient() {
        return WebClient.builder()
                .baseUrl("accounts")
                .build();
    }

}
