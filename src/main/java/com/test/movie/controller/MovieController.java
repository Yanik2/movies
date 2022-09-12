package com.test.movie.controller;

import com.test.movie.dto.MovieDto;
import com.test.movie.model.ApiError;
import com.test.movie.model.Movie;
import com.test.movie.exceptions.NotFoundException;
import com.test.movie.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<Movie> getMovies(@RequestParam(required = false) String search,
                                 @RequestParam Integer pageNumber,
                                 @RequestParam Integer pageSize) {
        return movieService.findAllMovies(search, pageNumber, pageSize);
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable Integer id) {
        return movieService.findById(id);
    }

    @PostMapping
    public Movie createMovie(@RequestBody MovieDto movieDto) {
        return movieService.createMovie(movieDto);
    }

    @PutMapping
    public Movie updateMovie(@RequestBody MovieDto movieDto) {
        return movieService.updateMovie(movieDto);
    }

    @DeleteMapping("/{id}")
    public Integer deleteMovie(@PathVariable Integer id) {
        return movieService.deleteMovie(id);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ApiError handleException(NotFoundException ex) {
        ApiError apiError = new ApiError();
        apiError.setMessage(ex.getMessage());
        apiError.setTimestamp(LocalDateTime.now());
        return apiError;
    }
}
