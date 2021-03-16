package com.debanik.warehouse.service;


import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Enabling the component scan and entity scan of classes in the below mentioned
 * "com.debanik.warehouse.service" and "com.debanik.warehouse.service.entity"
 * packages respectively.
 */
@Configuration
@ComponentScan("com.debanik.warehouse.service")
@EntityScan("com.debanik.warehouse.service.entity")
public class ServiceConfiguration {
}
