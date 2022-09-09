package com.test.movie.service.impl;

import com.test.movie.dto.OrderDto;
import com.test.movie.exceptions.NotFoundException;
import com.test.movie.model.Movie;
import com.test.movie.model.Order;
import com.test.movie.repository.MovieRepository;
import com.test.movie.repository.OrderRepository;
import com.test.movie.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderServiceImplTest {
    private final static String EXCEPTION_MESSAGE_ORDER = "Entity order with id: 1 not found";
    private final static String EXCEPTION_MESSAGE_MOVIE = "Entity movie with id: 2 not found";
    private OrderService orderService;
    private OrderRepository orderRepositoryMock;
    private MovieRepository movieRepositoryMock;

    @BeforeEach
    public void setUp() {
        orderRepositoryMock = mock(OrderRepository.class);
        movieRepositoryMock = mock(MovieRepository.class);
        orderService = new OrderServiceImpl(orderRepositoryMock, movieRepositoryMock);
    }

    @Test
    public void shouldReturnListOrder() {
        var params = new HashMap<String, String>();
        params.put("pageNumber", "1");
        params.put("pageSize", "3");
        var expectedResult = List.of(new Order());
        var page = new PageImpl<>(expectedResult);

        when(orderRepositoryMock.findAll(isA(Pageable.class))).thenReturn(page);

        var actualResult = orderService.findAllOrders(params);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnOrderById() {
        var expectedResult = new Order();
        when(orderRepositoryMock.findById(1)).thenReturn(Optional.of(expectedResult));

        var actualResult = orderService.findById(1);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldThrowWhenFindById() {
        assertThrows(NotFoundException.class, () -> orderService.findById(1));
    }

    @Test
    public void shouldReturnOrderWhenCreate() {
        var orderDto = new OrderDto();
        var movie = new Movie();
        var expectedResult = new Order();
        orderDto.setMovieId(1);
        when(movieRepositoryMock.findById(1)).thenReturn(Optional.of(movie));
        when(orderRepositoryMock.saveAndFlush(isA(Order.class))).thenReturn(expectedResult);

        var actualResult = orderService.createOrder(orderDto);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldThrowWhenCreate() {
        var orderDto = mock(OrderDto.class);

        assertThrows(NotFoundException.class, () -> orderService.createOrder(orderDto));
    }

    @Test
    public void shouldReturnOrderWhenUpdate() {
        var orderDto = new OrderDto();
        orderDto.setId(1);
        orderDto.setMovieId(2);
        var order = new Order();
        var movie = new Movie();
        when(orderRepositoryMock.findById(1)).thenReturn(Optional.of(order));
        when(movieRepositoryMock.findById(2)).thenReturn(Optional.of(movie));
        when(orderRepositoryMock.saveAndFlush(order)).thenReturn(order);

        var actualResult = orderService.updateOrder(orderDto);

        assertEquals(order, actualResult);
    }

    @Test
    public void shouldThrowWhenUpdateOrderIsMissing() {
        var orderDto = new OrderDto();
        orderDto.setId(1);

        var ex =
                assertThrows(NotFoundException.class, () -> orderService.updateOrder(orderDto));
        assertEquals(EXCEPTION_MESSAGE_ORDER, ex.getMessage());
    }

    @Test
    public void shouldThrowWhenUpdateMovieIsMissing() {
        var orderDto = new OrderDto();
        var order = new Order();
        orderDto.setId(1);
        orderDto.setMovieId(2);

        when(orderRepositoryMock.findById(1)).thenReturn(Optional.of(order));

        var ex =
                assertThrows(NotFoundException.class, () -> orderService.updateOrder(orderDto));
        assertEquals(EXCEPTION_MESSAGE_MOVIE, ex.getMessage());
    }

    @Test
    public void shouldReturnIdWhenDelete() {
        var order = new Order();
        when(orderRepositoryMock.findById(1)).thenReturn(Optional.of(order));

        var actualResult = orderService.deleteOrder(1);

        assertEquals(1, actualResult);
    }

    @Test
    public void shouldThrowWhenDelete() {
        assertThrows(NotFoundException.class, () -> orderService.deleteOrder(1));
    }
}