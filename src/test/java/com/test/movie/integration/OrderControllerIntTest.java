package com.test.movie.integration;

import com.test.movie.TestConfig;
import com.test.movie.model.Movie;
import com.test.movie.model.Order;
import com.test.movie.repository.MovieRepository;
import com.test.movie.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {TestConfig.class})
public class OrderControllerIntTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void shouldReturnListOrders() throws Exception {
        var movie = new Movie(1, "Ironman", "Favro");
        var expectedResult = List.of(new Order(movie, "time1"), new Order(movie, "time2"));
        var params = new LinkedMultiValueMap<String, String>();
        params.put("pageNumber", List.of("1"));
        params.put("pageSize", List.of("2"));

        when(orderRepository.findAll(isA(Pageable.class))).thenReturn(new PageImpl<>(expectedResult));

        var result =
                mockMvc.perform(get("/order").params(params)).andExpect(status().isOk()).andReturn();
        var response = result.getResponse().getContentAsString();

        assertTrue(response.contains("Ironman"));
        assertTrue(response.contains("Favro"));
    }

    @Test
    public void shouldReturnOrderById() throws Exception {
        var movie = new Movie(1, "Ironman", "Favro");
        var order = new Order(movie, "time");
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        var result = mockMvc.perform(get("/order/{id}", 1))
                .andExpect(status().isOk())
                .andReturn();
        var response = result.getResponse().getContentAsString();

        assertTrue(response.contains("Ironman"));
        assertTrue(response.contains("time"));
    }

    @Test
    public void shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/order/{id}", 1)).andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldReturnCreateOrder() throws Exception {
        var movie = new Movie(1, "Ironman", "Favro");
        var order = new Order(movie, "time");
        when(movieRepository.findById(1)).thenReturn(Optional.of(movie));
        when(orderRepository.saveAndFlush(isA(Order.class))).thenReturn(order);

        var result = mockMvc.perform(post("/order")
                        .content(readFile("create_order.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        var response = result.getResponse().getContentAsString();

        assertTrue(response.contains("Ironman"));
        assertTrue(response.contains("time"));
    }

    @Test
    public void shouldReturnOrderWhenUpdate() throws Exception {
        var movie = new Movie(1, "Ironman", "Favro");
        var order = new Order(movie, "time");
        when(movieRepository.findById(1)).thenReturn(Optional.of(movie));
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));
        when(orderRepository.saveAndFlush(order)).thenReturn(order);

        var result = mockMvc.perform(put("/order")
                        .content(readFile("update_order.json"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        var response = result.getResponse().getContentAsString();

        assertTrue(response.contains("Ironman"));
        assertTrue(response.contains("time"));
        assertTrue(response.contains("time"));
    }

    @Test
    public void shouldReturnIdWhenDelete() throws Exception {
        var order = new Order();
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        var result = mockMvc.perform(delete("/order/{id}", 1))
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
