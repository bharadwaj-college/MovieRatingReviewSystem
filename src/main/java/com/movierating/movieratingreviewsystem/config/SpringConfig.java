package com.movierating.movieratingreviewsystem.config;

import com.movierating.movieratingreviewsystem.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;

@Configuration
@ComponentScan(basePackages = "com.movierating.movieratingreviewsystem")
public class SpringConfig {

    @Bean
    public SessionFactory sessionFactory() {
        return HibernateUtil.getSessionFactory();
    }
}
