package com.test.movie;

import com.test.movie.dao.MovieRepository;
import com.test.movie.dao.OrderRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan("com.test.movie")
public class TestConfig {

    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private MovieRepository movieRepository;
}
