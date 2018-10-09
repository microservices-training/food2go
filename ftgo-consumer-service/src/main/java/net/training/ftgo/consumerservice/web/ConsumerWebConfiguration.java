package net.training.ftgo.consumerservice.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import net.training.ftgo.consumerservice.domain.ConsumerServiceConfiguration;

@Configuration
@ComponentScan
@Import(ConsumerServiceConfiguration.class)
public class ConsumerWebConfiguration {
}
