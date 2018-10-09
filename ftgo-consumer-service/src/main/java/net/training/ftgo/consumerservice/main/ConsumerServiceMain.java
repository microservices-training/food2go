package net.training.ftgo.consumerservice.main;

import io.eventuate.jdbckafka.TramJdbcKafkaConfiguration;
import net.training.eventstore.examples.customersandorders.commonswagger.CommonSwaggerConfiguration;
import net.training.ftgo.consumerservice.web.ConsumerWebConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ConsumerWebConfiguration.class, TramJdbcKafkaConfiguration.class, CommonSwaggerConfiguration.class})
public class ConsumerServiceMain {

  public static void main(String[] args) {
    SpringApplication.run(ConsumerServiceMain.class, args);
  }
}
