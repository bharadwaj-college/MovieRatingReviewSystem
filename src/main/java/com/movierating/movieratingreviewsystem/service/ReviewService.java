package com.movierating.movieratingreviewsystem.service;

import com.movierating.movieratingreviewsystem.dao.ReviewDAO;
import com.movierating.movieratingreviewsystem.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewDAO reviewDAO;

    @Autowired
    public ReviewService(ReviewDAO reviewDAO) {
        this.reviewDAO = reviewDAO;
    }

    public List<Review> getAllReviews() {
        return reviewDAO.getAllReviews();
    }

    public List<Review> getReviewsByMovieId(int movieId) {
        return reviewDAO.getReviewsByMovieId(movieId);
    }

    public Review getReviewById(int reviewId) {
        return reviewDAO.getReviewById(reviewId);
    }

    public void addReview(Review review) {
        reviewDAO.addReview(review);
    }

    public void updateReview(Review review) {
        reviewDAO.updateReview(review);
    }

    public void deleteReview(int reviewId) {
        reviewDAO.deleteReview(reviewId);
    }
    public Double getAverageRatingByMovieId(int movieId) {
        return reviewDAO.getAverageRatingByMovieId(movieId);
    }

    public Long getReviewCountByMovieId(int movieId) {
        return reviewDAO.getReviewCountByMovieId(movieId);
    }
}
