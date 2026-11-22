package com.movierating.movieratingreviewsystem.dao;

import com.movierating.movieratingreviewsystem.entity.Movie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MovieDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public MovieDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // Display all movies with their genres
    public List<Movie> getAllMovies() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "SELECT m FROM Movie m JOIN FETCH m.genre ORDER BY m.movieId",
                    Movie.class
            ).getResultList();
        }
    }

    // Find movie by ID
    public Movie getMovieById(int movieId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "SELECT m FROM Movie m JOIN FETCH m.genre WHERE m.movieId = :id",
                    Movie.class
            ).setParameter("id", movieId)
             .uniqueResult();
        }
    }

    // Add movie
    public void addMovie(Movie movie) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(movie);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    // Update movie
    public void updateMovie(Movie movie) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(movie);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    // Delete movie only when it has no reviews
    public boolean deleteMovie(int movieId) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            Movie movie = session.get(Movie.class, movieId);

            if (movie == null) {
                transaction.commit();
                return false;
            }

            Long reviewCount = session.createQuery(
                    "SELECT COUNT(r) FROM Review r WHERE r.movie.movieId = :id",
                    Long.class
            ).setParameter("id", movieId)
             .getSingleResult();

            if (reviewCount > 0) {
                transaction.commit();
                return false;
            }

            session.remove(movie);
            transaction.commit();
            return true;

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
