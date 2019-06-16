package com.gunes.config;

import com.gunes.aop.RestLoggingAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AspectConfig {

    @Bean
    public RestLoggingAspect restLoggingAspect() {
        return new RestLoggingAspect();
    }
}