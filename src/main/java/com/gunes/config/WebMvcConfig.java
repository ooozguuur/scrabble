package com.gunes.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.*;


@Configuration
@ComponentScan("com.gunes.controller")
@EnableWebMvc
@EnableAsync
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/v2/api-docs");
        registry.addRedirectViewController("/scrabble/v2/api-docs", "/v2/api-docs");
        registry.addRedirectViewController("/scrabble/swagger-resources/configuration/ui", "/swagger-resources/configuration/ui");
        registry.addRedirectViewController("/scrabble/swagger-resources/configuration/security", "/swagger-resources/configuration/security");
        registry.addRedirectViewController("/scrabble/swagger-resources", "/swagger-resources");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/scrabble/swagger-ui.html**").addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
        registry.addResourceHandler("/scrabble/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }


}