package io.microdrive.app.trip.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveSetOperations;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;

@Configuration
@EnableMongoAuditing
public class TripConfig {

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
    public WebClient pricingWebClient() {
        return WebClient.builder()
                .baseUrl("http://pricing/api/v1")
                .build();
    }
}
