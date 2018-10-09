package net.training.ftgo.restaurantservice.lambda;

import io.eventuate.tram.messaging.producer.jdbc.TramMessageProducerJdbcConfiguration;
import net.training.ftgo.restaurantservice.domain.RestaurantServiceDomainConfiguration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({RestaurantServiceDomainConfiguration.class, TramMessageProducerJdbcConfiguration.class})
@EnableAutoConfiguration
public class RestaurantServiceLambdaConfiguration {
}
