package com.test.movie.service.impl;

import com.test.movie.dto.MovieDto;
import com.test.movie.exceptions.NotFoundException;
import com.test.movie.model.Movie;
import com.test.movie.repository.MovieRepository;
import com.test.movie.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

public class MovieServiceImplTest {
    private MovieService movieService;
    private MovieRepository movieRepositoryMock;

    @BeforeEach
    public void setUp() {
        movieRepositoryMock = Mockito.mock(MovieRepository.class);
        movieService = new MovieServiceImpl(movieRepositoryMock);
    }

    @Test
    void shouldReturnMovieById() {
        var expectedResult = new Movie();
        when(movieRepositoryMock.findById(1)).thenReturn(Optional.of(expectedResult));

        var actualResult = movieService.findById(1);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldThrowNotFoundExceptionFindById() {
        assertThrows(NotFoundException.class, () -> movieService.findById(1));
    }


    @Test
    public void shouldReturnMovieList() {
        var params = new HashMap<String, String>();
        params.put("pageNumber", "1");
        params.put("pageSize", "3");
        var expectedResult = List.of(new Movie());
        var page = new PageImpl<Movie>(expectedResult);

        when(movieRepositoryMock.findAll(isA(Pageable.class))).thenReturn(page);

        var actualResult = movieService.findAllMovies(params);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnMovieCreate() {
        var movieDto = new MovieDto();
        var expectedResult = new Movie();
        when(movieRepositoryMock.saveAndFlush(isA(Movie.class))).thenReturn(expectedResult);

        var actualResult = movieService.createMovie(movieDto);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnMovieUpdate() {
        var movieDto = new MovieDto();
        var expectedResult = new Movie();
        when(movieRepositoryMock.saveAndFlush(isA(Movie.class))).thenReturn(expectedResult);

        var actualResult = movieService.updateMovie(movieDto);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnIdDelete() {
        var movie = new Movie();
        when(movieRepositoryMock.findById(1)).thenReturn(Optional.of(movie));

        var actualResult = movieService.deleteMovie(1);

        assertEquals(1, actualResult);
    }

    @Test
    public void shouldThrowWhenDeleteMovie() {
        assertThrows(NotFoundException.class, () -> movieService.deleteMovie(1));
    }
}