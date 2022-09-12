package com.test.movie.service;

import com.test.movie.dto.OrderDto;
import com.test.movie.model.Order;

import java.util.List;
import java.util.Map;

public interface OrderService {
    List<Order> findAllOrders(String search, Integer pageSize, Integer pageNumber);
    Order findById(Integer id);
    Order createOrder(OrderDto orderDto);
    Order updateOrder(OrderDto orderDto);
    Integer deleteOrder(Integer id);
}
