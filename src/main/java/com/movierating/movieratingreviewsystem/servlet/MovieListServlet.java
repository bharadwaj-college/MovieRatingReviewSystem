package com.movierating.movieratingreviewsystem.servlet;

import com.movierating.movieratingreviewsystem.entity.Movie;
import com.movierating.movieratingreviewsystem.listener.AppContextListener;
import com.movierating.movieratingreviewsystem.service.MovieService;
import com.movierating.movieratingreviewsystem.service.ReviewService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.List;

@WebServlet("/movies")
public class MovieListServlet extends HttpServlet {

    private MovieService movieService;
    private ReviewService reviewService;

    @Override
    public void init() throws ServletException {

        AnnotationConfigApplicationContext context =
            (AnnotationConfigApplicationContext)
            getServletContext().getAttribute(
                AppContextListener.SPRING_CONTEXT
            );

        if (context == null) {
            throw new ServletException("Spring context not available.");
        }

        movieService = context.getBean(MovieService.class);
        reviewService = context.getBean(ReviewService.class);
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loggedInUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            List<Movie> movies = movieService.getAllMovies();

            for (Movie movie : movies) {
                int movieId = movie.getMovieId();

                Double averageRating =
                        reviewService.getAverageRatingByMovieId(movieId);

                Long reviewCount =
                        reviewService.getReviewCountByMovieId(movieId);

                request.setAttribute("rating_" + movieId,
                        averageRating == null ? 0.0 : averageRating);

                request.setAttribute("count_" + movieId,
                        reviewCount == null ? 0L : reviewCount);
            }

            request.setAttribute("movies", movies);

            request.getRequestDispatcher("/WEB-INF/views/movies.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );
        }
    }
}
