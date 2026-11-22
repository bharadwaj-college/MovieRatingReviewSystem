package com.movierating.movieratingreviewsystem.listener;

import com.movierating.movieratingreviewsystem.config.SpringConfig;
import com.movierating.movieratingreviewsystem.util.HibernateUtil;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebListener
public class AppContextListener implements ServletContextListener {

    public static final String SPRING_CONTEXT =
            "springApplicationContext";

    private AnnotationConfigApplicationContext springContext;

    @Override
    public void contextInitialized(ServletContextEvent event) {

        System.out.println("Starting Spring Application Context...");

        springContext =
                new AnnotationConfigApplicationContext(SpringConfig.class);

        ServletContext servletContext = event.getServletContext();

        servletContext.setAttribute(SPRING_CONTEXT, springContext);

        System.out.println("Spring Application Context Started Successfully!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {

        System.out.println("Closing Spring Application Context...");

        if (springContext != null) {
            springContext.close();
        }

        HibernateUtil.shutdown();

        System.out.println("Application Context Closed Successfully!");
    }
}
