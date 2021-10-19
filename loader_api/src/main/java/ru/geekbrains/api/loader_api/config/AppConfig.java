package ru.geekbrains.api.loader_api.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.time.Duration;

@Configuration
@EnableCaching
public class AppConfig {

    private Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);
    private final static String BEAN_INITIALIZING = "Initializing bean: ";

    @Value("${loader.resttemplate.connectTimeout}")
    long connectTimeout;

    @Value("${loader.resttemplate.readTimeout}")
    long readTimeout;

    @Bean
    public CustomRestTemplateCustomizer customRestTemplateCustomizer() {
        return new CustomRestTemplateCustomizer();
    }

    @Bean
    @DependsOn(value = {"customRestTemplateCustomizer"})
    public RestTemplateBuilder restTemplateBuilder() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder(customRestTemplateCustomizer())
                .setConnectTimeout(Duration.ofSeconds(connectTimeout))
                .setReadTimeout(Duration.ofSeconds(readTimeout));
        LOGGER.info(BEAN_INITIALIZING + restTemplateBuilder.toString());
        return restTemplateBuilder;
    }

    @Bean
    public CacheManager cacheManager() {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager("cities");
        LOGGER.info(BEAN_INITIALIZING + cacheManager);
        LOGGER.info("Cache managers names: " + cacheManager.getCacheNames());
        return cacheManager;
    }
}
