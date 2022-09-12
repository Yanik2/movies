package com.test.movie.controller;

import com.test.movie.dto.MovieDto;
import com.test.movie.exceptions.NotFoundException;
import com.test.movie.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

public class MovieControllerTest {
    private MovieController movieController;
    private MovieService movieServiceMock;

    @BeforeEach
    public void setUp() {
        movieServiceMock = Mockito.mock(MovieService.class);
        movieController = new MovieController(movieServiceMock);
    }

    @Test
    public void shouldCallMovieServiceAllMovies() {
        var search = "search";

        movieController.getMovies(search, 1, 2);

        verify(movieServiceMock).findAllMovies(search, 1, 2);
    }

    @Test
    public void shouldCallMovieServiceFindById() {
        movieController.getMovieById(1);

        verify(movieServiceMock).findById(1);
    }

    @Test
    public void shouldCallMovieServiceCreateMovie() {
        var movieDto = new MovieDto();

        movieController.createMovie(movieDto);

        verify(movieServiceMock).createMovie(movieDto);
    }

    @Test
    public void shouldCallMovieServiceUpdateMovie() {
        var movieDto = new MovieDto();

        movieController.updateMovie(movieDto);

        verify(movieServiceMock).updateMovie(movieDto);
    }

    @Test
    public void shouldCallMovieServiceDelete() {
        movieController.deleteMovie(1);

        verify(movieServiceMock).deleteMovie(1);
    }

    @Test
    public void shouldReturnApiError() {
        var ex = new NotFoundException("Message");

        var actualResult = movieController.handleException(ex);

        assertEquals("Message", ex.getMessage());
        assertNotNull(actualResult.getTimestamp());
    }
}
