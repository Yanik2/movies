package com.test.movie.service.impl;

import com.test.movie.dao.OrderSpecification;
import com.test.movie.dto.OrderDto;
import com.test.movie.exceptions.NotFoundException;
import com.test.movie.model.Movie;
import com.test.movie.model.Order;
import com.test.movie.dao.MovieRepository;
import com.test.movie.dao.OrderRepository;
import com.test.movie.service.OrderService;
import com.test.movie.util.SearchCriteria;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private MovieRepository movieRepository;

    public OrderServiceImpl(OrderRepository orderRepository, MovieRepository movieRepository) {
        this.orderRepository = orderRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Order> findAllOrders(String search, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        if(search == null)
            return orderRepository.findAll(pageable).getContent();

        SearchCriteria searchCriteria;
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        matcher.find();
        searchCriteria = new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3));
        OrderSpecification spec = new OrderSpecification(searchCriteria);
        return orderRepository.findAll(spec, pageable).getContent();
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
