package com.gunes.config;

import com.gunes.dao.base.impl.GenericJpaDao;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

@Aspect
public class RestLoggingAspect {

    private static final Logger log = LoggerFactory.logger(GenericJpaDao.class);

    private Environment env;

    @Autowired
    public RestLoggingAspect(Environment env) {
        this.env = env;
    }
}