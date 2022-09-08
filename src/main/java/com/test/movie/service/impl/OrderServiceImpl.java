package com.test.movie.service.impl;

import com.test.movie.dto.OrderDto;
import com.test.movie.exceptions.NotFoundException;
import com.test.movie.model.Movie;
import com.test.movie.model.Order;
import com.test.movie.repository.MovieRepository;
import com.test.movie.repository.OrderRepository;
import com.test.movie.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private MovieRepository movieRepository;

    public OrderServiceImpl(OrderRepository orderRepository, MovieRepository movieRepository) {
        this.orderRepository = orderRepository;
        this.movieRepository = movieRepository;
    }

    public List<Order> findAllOrders(Map<String, String> map) {
        Integer pageNumber = Integer.parseInt(map.get("pageNumber"));
        Integer pageSize = Integer.parseInt(map.get("pageSize"));
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<Order> orders = orderRepository.findAll(page);
        return orders.getContent();
    }

    public Order findById(Integer id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Entity with id: " + id + " not found"));
    }

    public Order createOrder(OrderDto orderDto) {
        Integer id = orderDto.getMovieId();
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Entity with id: " + id + " not found"));
        Order order = new Order(movie, orderDto.getTime());
        return orderRepository.saveAndFlush(order);
    }

    public Order updateOrder(OrderDto orderDto) {
        Integer orderId = orderDto.getId();
        Integer movieId = orderDto.getMovieId();
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Entity order with id: " + orderId + " not found"));
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new NotFoundException("Entity movie with id: " + movieId + " not found"));
        order.setTime(orderDto.getTime());
        order.setMovie(movie);
        return orderRepository.saveAndFlush(order);
    }

    @Transactional
    public Integer deleteOrder(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Entity with id: " + id + " not found"));
        orderRepository.delete(order);
        return id;
    }
}
