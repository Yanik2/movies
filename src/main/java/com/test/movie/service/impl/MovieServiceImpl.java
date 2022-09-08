package com.test.movie.service.impl;

import com.test.movie.dto.MovieDto;
import com.test.movie.model.Movie;
import com.test.movie.exceptions.NotFoundException;
import com.test.movie.repository.MovieRepository;
import com.test.movie.service.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {
    private MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie findById(int id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Entity with id: " + id + " not found"));
    }

    public List<Movie> findAllMovies(Map<String, String> map) {
        Integer pageNumber = Integer.parseInt(map.get("pageNumber"));
        Integer pageSize = Integer.parseInt(map.get("pageSize"));
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<Movie> movies = movieRepository.findAll(page);
        return movies.getContent();
    }

    public Movie createMovie(MovieDto movieDto) {
        Movie movie = new Movie();
        movie.setName(movieDto.getName());
        movie.setDirector(movieDto.getDirector());
        return movieRepository.saveAndFlush(movie);
    }

    public Movie updateMovie(MovieDto movieDto) {
        Movie movie = new Movie();
        movie.setId(movieDto.getId());
        movie.setDirector(movieDto.getDirector());
        movie.setName(movieDto.getName());
        return movieRepository.saveAndFlush(movie);
    }

    @Transactional
    public Integer deleteMovie(Integer id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Entity with id: " + id + " not found"));
        movieRepository.delete(movie);
        return id;
    }
}
