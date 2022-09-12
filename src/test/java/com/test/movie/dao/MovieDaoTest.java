package com.test.movie.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.test.movie.service.MovieService;
import com.test.movie.service.OrderService;
import com.test.movie.service.impl.MovieServiceImpl;
import com.test.movie.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@ContextConfiguration(classes = {JpaTestConfig.class})
public class MovieDaoTest {
    @Autowired
    private MovieService movieService;

    @Autowired
    private OrderService orderService;

    @Test
    public void shouldReturnListMoviesWithSearch() {
        var search = "director:Mark";

        var actualResult = movieService.findAllMovies(search, 0, 2);

        assertFalse(actualResult.isEmpty());
        assertTrue(actualResult.size() == 2);
        assertEquals("Mark", actualResult.get(0).getDirector());
        assertEquals("Mark", actualResult.get(1).getDirector());
    }

    @Test
    public void shouldReturnListMoviesWithoutSearch() {
        var actualResult = movieService.findAllMovies(null,0, 2);

        assertFalse(actualResult.isEmpty());
        assertTrue(actualResult.size() == 2);
        assertEquals("John", actualResult.get(0).getDirector());
        assertEquals("John", actualResult.get(1).getDirector());
    }

    @Test
    public void shouldReturnListOrderWithSearch() {
        var search = "movie:5";

        var actualResult = orderService.findAllOrders(search, 0, 2);

        assertFalse(actualResult.isEmpty());
        assertTrue(actualResult.size() == 2);
        assertEquals("Justice League", actualResult.get(0).getMovie().getName());
        assertEquals("Justice League", actualResult.get(1).getMovie().getName());
    }

    @Test
    public void shouldReturnListOrderWithoutSearch() {
        var actualResult = orderService.findAllOrders(null,0, 2);

        assertFalse(actualResult.isEmpty());
        assertTrue(actualResult.size() == 2);
        assertEquals("Ironman", actualResult.get(0).getMovie().getName());
        assertEquals("Justice League", actualResult.get(1).getMovie().getName());
    }
}

@TestConfiguration
class JpaTestConfig {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Bean
    public MovieService movieService() {
        return new MovieServiceImpl(movieRepository);
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(orderRepository, movieRepository);
    }
}
