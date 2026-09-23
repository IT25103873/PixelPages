package com.PixelPages.BookStore.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.PixelPages.BookStore.repository")
public class JpaConfig {
}