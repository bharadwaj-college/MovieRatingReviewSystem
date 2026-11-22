package com.movierating.movieratingreviewsystem.servlet;

import com.movierating.movieratingreviewsystem.entity.Movie;
import com.movierating.movieratingreviewsystem.entity.Review;
import com.movierating.movieratingreviewsystem.entity.User;
import com.movierating.movieratingreviewsystem.listener.AppContextListener;
import com.movierating.movieratingreviewsystem.service.MovieService;
import com.movierating.movieratingreviewsystem.service.ReviewService;
import com.movierating.movieratingreviewsystem.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/reviews")
public class ReviewServlet extends HttpServlet {

    private MovieService movieService;
    private ReviewService reviewService;
    private UserService userService;

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
        userService = context.getBean(UserService.class);
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("loggedInUser") == null) {

            response.sendRedirect(
                request.getContextPath() + "/login"
            );
            return;
        }

        try {

            int movieId = Integer.parseInt(
                request.getParameter("movieId")
            );

            int currentUserId =
                (Integer) session.getAttribute("loggedInUser");

            Movie movie = movieService.getMovieById(movieId);

            if (movie == null) {
                response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Movie not found."
                );
                return;
            }

            request.setAttribute("movie", movie);

            request.setAttribute(
                "reviews",
                reviewService.getReviewsByMovieId(movieId)
            );

            // Load a review for editing, if requested.
            String editReviewId = request.getParameter("editReviewId");

            if (editReviewId != null && !editReviewId.trim().isEmpty()) {

                int reviewId = Integer.parseInt(editReviewId);

                Review editReview =
                        reviewService.getReviewById(reviewId);

                if (editReview == null
                        || editReview.getUser().getUserId() != currentUserId
                        || editReview.getMovie().getMovieId() != movieId) {

                    response.sendError(
                        HttpServletResponse.SC_FORBIDDEN,
                        "You cannot edit this review."
                    );
                    return;
                }

                request.setAttribute("editReview", editReview);
            }

            request.getRequestDispatcher(
                "/WEB-INF/views/reviews.jsp"
            ).forward(request, response);

        } catch (NumberFormatException e) {

            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Invalid movie or review ID."
            );

        } catch (Exception e) {

            getServletContext().log("Review loading error", e);

            response.sendError(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "Unable to load reviews."
            );
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("loggedInUser") == null) {

            response.sendRedirect(
                request.getContextPath() + "/login"
            );
            return;
        }

        int currentUserId =
                (Integer) session.getAttribute("loggedInUser");

        String action = request.getParameter("action");

        if (action == null || action.trim().isEmpty()) {
            action = "add";
        }

        try {

            int movieId = Integer.parseInt(
                request.getParameter("movieId")
            );

            // DELETE REVIEW
            if ("delete".equals(action)) {

                int reviewId = Integer.parseInt(
                    request.getParameter("reviewId")
                );

                Review review =
                        reviewService.getReviewById(reviewId);

                if (review == null
                        || review.getUser().getUserId() != currentUserId
                        || review.getMovie().getMovieId() != movieId) {

                    response.sendError(
                        HttpServletResponse.SC_FORBIDDEN,
                        "You cannot delete this review."
                    );
                    return;
                }

                reviewService.deleteReview(reviewId);

                response.sendRedirect(
                    request.getContextPath()
                    + "/reviews?movieId=" + movieId + "&deleted=1"
                );
                return;
            }

            // ADD OR UPDATE REVIEW
            int rating = Integer.parseInt(
                request.getParameter("rating")
            );

            String reviewText = request.getParameter("reviewText");

            if (rating < 1 || rating > 5) {
                throw new IllegalArgumentException(
                    "Please select a rating between 1 and 5."
                );
            }

            if (reviewText == null
                    || reviewText.trim().isEmpty()
                    || reviewText.trim().length() > 500) {

                throw new IllegalArgumentException(
                    "Review must contain 1 to 500 characters."
                );
            }

            // UPDATE REVIEW
            if ("update".equals(action)) {

                int reviewId = Integer.parseInt(
                    request.getParameter("reviewId")
                );

                Review review =
                        reviewService.getReviewById(reviewId);

                if (review == null
                        || review.getUser().getUserId() != currentUserId
                        || review.getMovie().getMovieId() != movieId) {

                    response.sendError(
                        HttpServletResponse.SC_FORBIDDEN,
                        "You cannot edit this review."
                    );
                    return;
                }

                review.setRating(rating);
                review.setReviewText(reviewText.trim());

                reviewService.updateReview(review);

                response.sendRedirect(
                    request.getContextPath()
                    + "/reviews?movieId=" + movieId + "&updated=1"
                );
                return;
            }

            // ADD NEW REVIEW
            if ("add".equals(action)) {

                User user = userService.getUserById(currentUserId);
                Movie movie = movieService.getMovieById(movieId);

                if (user == null || movie == null) {
                    response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "User or movie not found."
                    );
                    return;
                }

                Review review = new Review();

                review.setUser(user);
                review.setMovie(movie);
                review.setRating(rating);
                review.setReviewText(reviewText.trim());
                review.setReviewDate(LocalDate.now());

                reviewService.addReview(review);

                response.sendRedirect(
                    request.getContextPath()
                    + "/reviews?movieId=" + movieId + "&added=1"
                );
                return;
            }

            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Invalid review action."
            );

        } catch (NumberFormatException e) {

            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Invalid movie, review, or rating value."
            );

        } catch (IllegalArgumentException e) {

            request.setAttribute("error", e.getMessage());

            String movieId = request.getParameter("movieId");

            response.sendRedirect(
                request.getContextPath()
                + "/reviews?movieId=" + movieId
                + "&error=validation"
            );

        } catch (Exception e) {

            getServletContext().log("Review operation error", e);

            response.sendError(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "Unable to process your review."
            );
        }
    }
}
