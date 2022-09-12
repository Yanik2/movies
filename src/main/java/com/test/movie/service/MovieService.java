package com.test.movie.service;

import com.test.movie.dto.MovieDto;
import com.test.movie.model.Movie;

import java.util.List;

public interface MovieService {
    Movie findById(int id);
    List<Movie> findAllMovies(String search, Integer pageNumber, Integer pageSize);
    Movie createMovie(MovieDto movieDto);
    Movie updateMovie(MovieDto movieDto);
    Integer deleteMovie(Integer id);
}
