package com.test.movie.service.impl;

import com.test.movie.util.SearchCriteria;
import com.test.movie.dto.MovieDto;
import com.test.movie.model.Movie;
import com.test.movie.exceptions.NotFoundException;
import com.test.movie.dao.MovieRepository;
import com.test.movie.dao.MovieSpecification;
import com.test.movie.service.MovieService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    public List<Movie> findAllMovies(String search, Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        if (search == null) {
            return movieRepository.findAll(page).getContent();
        }

        SearchCriteria searchCriteria;
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        matcher.find();
        searchCriteria = new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3));
        MovieSpecification spec = new MovieSpecification(searchCriteria);
        return movieRepository.findAll(spec, page).getContent();
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
