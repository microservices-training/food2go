package net.training.ftgo.kitchenservice.main;

import io.eventuate.jdbckafka.TramJdbcKafkaConfiguration;
import io.eventuate.tram.commands.common.ChannelMapping;
import io.eventuate.tram.commands.common.DefaultChannelMapping;
import net.training.eventstore.examples.customersandorders.commonswagger.CommonSwaggerConfiguration;
import net.training.ftgo.kitchenservice.messagehandlers.KitchenServiceMessageHandlersConfiguration;
import net.training.ftgo.kitchenservice.web.KitchenServiceWebConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({KitchenServiceWebConfiguration.class, KitchenServiceMessageHandlersConfiguration.class,
        TramJdbcKafkaConfiguration.class,
        CommonSwaggerConfiguration.class})
public class KitchenServiceMain {

  @Bean
  public ChannelMapping channelMapping() {
    return new DefaultChannelMapping.DefaultChannelMappingBuilder().build();
  }

  public static void main(String[] args) {
    SpringApplication.run(KitchenServiceMain.class, args);
  }
}
