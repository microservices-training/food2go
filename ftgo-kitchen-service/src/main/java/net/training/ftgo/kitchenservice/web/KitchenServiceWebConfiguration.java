package net.training.ftgo.kitchenservice.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import net.training.ftgo.kitchenservice.domain.KitchenDomainConfiguration;

@Configuration
@Import(KitchenDomainConfiguration.class)
@ComponentScan
public class KitchenServiceWebConfiguration {


}
