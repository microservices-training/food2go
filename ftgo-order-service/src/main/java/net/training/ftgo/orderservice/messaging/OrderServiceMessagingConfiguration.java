package net.training.ftgo.orderservice.messaging;

import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.messaging.consumer.MessageConsumer;
import net.training.ftgo.orderservice.domain.OrderService;
import net.training.ftgo.orderservice.domain.OrderServiceWithRepositoriesConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({OrderServiceWithRepositoriesConfiguration.class})
public class OrderServiceMessagingConfiguration {

  @Bean
  public OrderEventConsumer orderEventConsumer(OrderService orderService) {
    return new OrderEventConsumer(orderService);
  }

  @Bean
  public DomainEventDispatcher domainEventDispatcher(OrderEventConsumer orderEventConsumer, MessageConsumer messageConsumer) {
    return new DomainEventDispatcher("orderServiceEvents", orderEventConsumer.domainEventHandlers(), messageConsumer); // @Autowire
  }

}
