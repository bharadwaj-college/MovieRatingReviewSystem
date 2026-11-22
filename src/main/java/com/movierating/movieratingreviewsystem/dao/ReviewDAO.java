package com.movierating.movieratingreviewsystem.dao;

import com.movierating.movieratingreviewsystem.entity.Review;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public ReviewDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // Display all reviews
    public List<Review> getAllReviews() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "SELECT r FROM Review r " +
                    "JOIN FETCH r.user " +
                    "JOIN FETCH r.movie m " +
                    "JOIN FETCH m.genre " +
                    "ORDER BY r.reviewId",
                    Review.class
            ).getResultList();
        }
    }

    // Display reviews for a particular movie
    public List<Review> getReviewsByMovieId(int movieId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "SELECT r FROM Review r " +
                    "JOIN FETCH r.user " +
                    "JOIN FETCH r.movie m " +
                    "JOIN FETCH m.genre " +
                    "WHERE m.movieId = :id ORDER BY r.reviewDate DESC",
                    Review.class
            ).setParameter("id", movieId)
             .getResultList();
        }
    }

    // Find review by ID
    public Review getReviewById(int reviewId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "SELECT r FROM Review r " +
                    "JOIN FETCH r.user " +
                    "JOIN FETCH r.movie " +
                    "WHERE r.reviewId = :id",
                    Review.class
            ).setParameter("id", reviewId)
             .uniqueResult();
        }
    }

    // Add review
    public void addReview(Review review) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(review);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    // Update review
    public void updateReview(Review review) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(review);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    // Delete review
    public void deleteReview(int reviewId) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            Review review = session.get(Review.class, reviewId);

            if (review != null) {
                session.remove(review);
            }

            transaction.commit();

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
    public Double getAverageRatingByMovieId(int movieId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                "SELECT AVG(r.rating) FROM Review r WHERE r.movie.movieId = :movieId",
                Double.class
            )
            .setParameter("movieId", movieId)
            .uniqueResult();
        }
    }
    public Long getReviewCountByMovieId(int movieId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                "SELECT COUNT(r.reviewId) FROM Review r WHERE r.movie.movieId = :movieId",
                Long.class
            )
            .setParameter("movieId", movieId)
            .uniqueResult();
        }
    }
}
