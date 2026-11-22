package com.movierating.movieratingreviewsystem.service;

import com.movierating.movieratingreviewsystem.dao.MovieDAO;
import com.movierating.movieratingreviewsystem.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieDAO movieDAO;

    @Autowired
    public MovieService(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    public List<Movie> getAllMovies() {
        return movieDAO.getAllMovies();
    }

    public Movie getMovieById(int movieId) {
        return movieDAO.getMovieById(movieId);
    }

    public void addMovie(Movie movie) {
        movieDAO.addMovie(movie);
    }

    public void updateMovie(Movie movie) {
        movieDAO.updateMovie(movie);
    }

    public boolean deleteMovie(int movieId) {
        return movieDAO.deleteMovie(movieId);
    }
}
