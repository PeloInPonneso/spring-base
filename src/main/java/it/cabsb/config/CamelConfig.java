package it.cabsb.config;

import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Order(7)
@Configuration
public class CamelConfig extends CamelConfiguration {}
