package net.training.ftgo.orderservice.main;

import io.eventuate.jdbckafka.TramJdbcKafkaConfiguration;
import io.eventuate.tram.commands.common.ChannelMapping;
import io.eventuate.tram.commands.common.DefaultChannelMapping;
import net.training.eventstore.examples.customersandorders.commonswagger.CommonSwaggerConfiguration;
import net.training.ftgo.orderservice.messaging.OrderServiceMessagingConfiguration;
import net.training.ftgo.orderservice.service.OrderCommandHandlersConfiguration;
import net.training.ftgo.orderservice.web.OrderWebConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({OrderWebConfiguration.class, OrderCommandHandlersConfiguration.class,  OrderServiceMessagingConfiguration.class,
        TramJdbcKafkaConfiguration.class, CommonSwaggerConfiguration.class})
public class OrderServiceMain {

  @Bean
  public ChannelMapping channelMapping() {
    return new DefaultChannelMapping.DefaultChannelMappingBuilder().build();
  }

  public static void main(String[] args) {
    SpringApplication.run(OrderServiceMain.class, args);
  }
}
