package com.gunes.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() { return new Class[] {JPAConfiguration.class, MongoDBConfiguration.class, AspectConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {WebMvcConfig.class, SwaggerConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

    @Override
    protected ApplicationContextInitializer<?>[] getRootApplicationContextInitializers() {
        initializeAppEnvironment();
        return super.getRootApplicationContextInitializers();
    }

    private static void initializeAppEnvironment() {
        String env = System.getenv("env");
        if (env == null) {
            System.setProperty("env", "dev");
        }
    }

}