package com.movierating.movieratingreviewsystem.util;

import com.movierating.movieratingreviewsystem.config.SpringConfig;
import com.movierating.movieratingreviewsystem.service.UserService;
import com.movierating.movieratingreviewsystem.service.MovieService;
import com.movierating.movieratingreviewsystem.service.ReviewService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringTest {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = null;

        try {
            System.out.println("Starting Spring...");

            context = new AnnotationConfigApplicationContext(SpringConfig.class);

            UserService userService = context.getBean(UserService.class);
            MovieService movieService = context.getBean(MovieService.class);
            ReviewService reviewService = context.getBean(ReviewService.class);

            System.out.println("Spring Dependency Injection Successful!");

            System.out.println("\n----- DATABASE RECORD COUNTS -----");

            System.out.println("Users   : " + userService.getAllUsers().size());
            System.out.println("Movies  : " + movieService.getAllMovies().size());
            System.out.println("Reviews : " + reviewService.getAllReviews().size());

            System.out.println("\nAll Service Classes Loaded Successfully!");

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (context != null) {
                context.close();
            }

            HibernateUtil.shutdown();
        }
    }
}
