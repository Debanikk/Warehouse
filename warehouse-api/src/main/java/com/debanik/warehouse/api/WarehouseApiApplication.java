package com.debanik.warehouse.api;

import com.debanik.warehouse.service.ServiceConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * A Configuration class that can declare one or more @Bean methods and trigger auto-configuration and component scanning.
 * This class launches a Spring Application from Java main method.
 */
@SpringBootApplication
@Import(ServiceConfiguration.class)
public class WarehouseApiApplication {
    public static void main(String[] args) {

        //Sample code Check in Test for Development Branch of Warehouse Application
        SpringApplication.run(WarehouseApiApplication.class, args);
		//Testing the git pull
    }
}

