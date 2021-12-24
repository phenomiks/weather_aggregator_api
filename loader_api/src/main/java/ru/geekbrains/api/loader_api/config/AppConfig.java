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
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.Duration;

@Configuration
@EnableSwagger2
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

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Loader Api Documentation")
                .description("Documentation of Loader API module included in Weather Aggregator API project")
                .version("1.0")
                .build();
    }
}
