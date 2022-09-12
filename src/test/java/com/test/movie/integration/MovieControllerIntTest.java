package com.test.movie.integration;

import com.test.movie.TestConfig;
import com.test.movie.controller.MovieController;
import com.test.movie.model.Movie;
import com.test.movie.dao.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
@ContextConfiguration(classes = {TestConfig.class})
public class MovieControllerIntTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MovieRepository movieRepository;


    @Test
    public void shouldReturnListMovies() throws Exception {
        var movie1 = new Movie(1, "Ironman", "Favro");
        var movie2 = new Movie(2, "Ugly 8", "Tarantino");
        var expectedList = List.of(movie1, movie2);
        when(movieRepository.findAll(isA(Pageable.class))).thenReturn(new PageImpl<>(expectedList));
        var params = new LinkedMultiValueMap<String, String>();
        params.put("pageNumber", List.of("1"));
        params.put("pageSize", List.of("2"));

        var result = mockMvc
                .perform(get("/movies").params(params))
                .andExpect(status().isOk()).andReturn();

        var response = result.getResponse().getContentAsString();

        assertTrue(response.contains("Ironman"));
        assertTrue(response.contains("Ugly 8"));
    }

    @Test
    public void shouldReturnListMoviesWithSearch() throws Exception {
        var movie1 = new Movie(1, "Ironman", "Favro");
        var movie2 = new Movie(2, "Ugly 8", "Tarantino");
        var expectedList = List.of(movie1, movie2);
        when(movieRepository.findAll(isA(Specification.class), isA(Pageable.class))).thenReturn(new PageImpl<>(expectedList));

        var params = new LinkedMultiValueMap<String, String>();
        params.put("search", List.of("director:Favro"));
        params.put("pageNumber", List.of("1"));
        params.put("pageSize", List.of("2"));

        var result = mockMvc
            .perform(get("/movies").params(params))
            .andExpect(status().isOk()).andReturn();

        var response = result.getResponse().getContentAsString();

        assertTrue(response.contains("Ironman"));
        assertTrue(response.contains("Ugly 8"));
    }

    @Test
    public void shouldReturnMovieById() throws Exception {
        var movie = new Movie(1, "Ironman", "Favro");
        when(movieRepository.findById(1)).thenReturn(Optional.of(movie));

        var result =
                mockMvc.perform(get("/movies/{id}", 1))
                        .andExpect(status().isOk())
                        .andReturn();

        var response = result.getResponse().getContentAsString();

        assertTrue(response.contains("Ironman"));
        assertTrue(response.contains("Favro"));
    }

    @Test
    public void shouldThrowWhenMovieIsMissing() throws Exception {
        mockMvc.perform(get("/movies/{id}", 1))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldReturnMovieWhenCreateMovie() throws Exception {
        var expectedResult = new Movie(1, "Ironman", "Favro");

        when(movieRepository.saveAndFlush(isA(Movie.class))).thenReturn(expectedResult);

        var result = mockMvc.perform(post("/movies")
                        .content(readFile("create_movie.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        var response = result.getResponse().getContentAsString();
        assertTrue(response.contains("Ironman"));
        assertTrue(response.contains("Favro"));
    }

    @Test
    public void shouldReturnMovieWhenUpdateMovie() throws Exception {
        var expectedResult = new Movie(1, "Ironman", "Favro");

        when(movieRepository.saveAndFlush(isA(Movie.class))).thenReturn(expectedResult);

        var result = mockMvc.perform(put("/movies")
                        .content(readFile("update_movie.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        var response = result.getResponse().getContentAsString();
        assertTrue(response.contains("Ironman"));
        assertTrue(response.contains("Favro"));
    }

    @Test
    public void shouldReturnIdWhenDeleteMovie() throws Exception {
        var movie = new Movie(1, "Ironman", "Favro");
        when(movieRepository.findById(1)).thenReturn(Optional.of(movie));

        var result =
                mockMvc.perform(delete("/movies/{id}", 1))
                        .andExpect(status().isOk())
                        .andReturn();

        var response = result.getResponse().getContentAsString();

        assertEquals(1, Integer.valueOf(response));
    }

    private String readFile(String path) throws Exception {
        var file = Files.readAllBytes(Paths.get(this.getClass()
                .getClassLoader()
                .getResource(path)
                .toURI()));
        return new String(file);
    }
}
