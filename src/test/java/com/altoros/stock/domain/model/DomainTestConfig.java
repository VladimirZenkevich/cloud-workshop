package com.altoros.stock.domain.model;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by uladzimir.ziankevich on 12/8/2015.
 */
@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = "com.altoros.stock.domain.model")
@EntityScan(basePackages = "com.altoros.stock.domain.model")
public class DomainTestConfig {
}
